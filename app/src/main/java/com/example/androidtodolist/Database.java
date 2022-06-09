package com.example.androidtodolist;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

public class Database extends SQLiteOpenHelper
{
    private static final String DATABASE_NAME = "tasks.db";
    private static final int DATABASE_VERSION = 1;
    ArrayList<String> categories;

    // // // //
    // // // //
    // // // //

    public Database(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

        initalizeCategories();

        SQLiteDatabase db = this.getWritableDatabase();
        if (db != null)
        {
            String count = "SELECT count(*) FROM CATEGORIES";
            Cursor mcursor = db.rawQuery(count, null);
            mcursor.moveToFirst();
            int icount = mcursor.getInt(0);

            if (icount == 0)
            {
                databaseSetUp();
            }
        }
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        String query = "CREATE TABLE CATEGORIES (ID INTEGER PRIMARY KEY AUTOINCREMENT, CATEGORY_NAME TEXT)";
        db.execSQL(query);

        query = "CREATE TABLE TASKS (ID INTEGER PRIMARY KEY AUTOINCREMENT, CATEGORY_ID REFERENCES CATEGORIES(ID)," +
                " TITLE TEXT, DESCRIPTION TEXT, DONE TEXT," +
                "CREATED DATE, DATEDONE DATE, UUID TEXT," +
                "DATETODO DATE, NOTIFY TEXT, HIDDEN TEXT)";
        db.execSQL(query);
    }

    public void initalizeCategories()
    {
        categories = new ArrayList<>();
        categories.add("Urgent");
        categories.add("Home");
        categories.add("Work");
        categories.add("Study");
        categories.add("Other");
    }

    public void databaseDestroy()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DROP TABLE IF EXISTS CATEGORIES";
        db.execSQL(query);

        query = "DROP TABLE IF EXISTS TASKS";
        db.execSQL(query);
    }

    public void databaseSetUp()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        databaseDestroy();

        String query = "CREATE TABLE CATEGORIES (ID INTEGER PRIMARY KEY AUTOINCREMENT, CATEGORY_NAME TEXT)";
        db.execSQL(query);

        query = "CREATE TABLE TASKS (ID INTEGER PRIMARY KEY AUTOINCREMENT, CATEGORY_ID REFERENCES CATEGORIES(ID)," +
                " TITLE TEXT, DESCRIPTION TEXT, DONE TEXT," +
                "CREATED DATE, DATEDONE DATE, UUID TEXT," +
                "DATETODO DATE, NOTIFY TEXT, HIDDEN TEXT)";
        db.execSQL(query);

        upgradeCategories();
    }

    public void upgradeCategories()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        initalizeCategories();
        Collections.sort(categories);

        for (String cat : categories)
        {
            contentValues.put("CATEGORY_NAME", cat);
            db.insert("CATEGORIES", null, contentValues);
        }
    }

    public ArrayList<Task> getTasksByCategoryName(String categoryName)
    {
        String countQuery = "SELECT * FROM TASKS";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        String title = "";
        String description = "";
        String done = "";
        Date created;
        Date dateDone;
        String UUID = "";
        Date toDoDate;
        String notify ="";
        String hidden = "";

        Cursor categoryCursor;

        ArrayList<Task> taskList = new ArrayList<Task>();
        if (cursor != null && cursor.moveToFirst())
        {
            do
            {
                int categoryId = cursor.getInt(1);
                countQuery = "SELECT * FROM CATEGORIES WHERE ID = '" + categoryId + "'";
                String queryCategoryName = "";
                categoryCursor = db.rawQuery(countQuery, null);
                if (categoryCursor != null && categoryCursor.moveToFirst())
                {
                    queryCategoryName = categoryCursor.getString(1);
                }
                if (queryCategoryName.equals(categoryName))
                {
                    title = cursor.getString(2);
                    description = cursor.getString(3);
                    done = cursor.getString(4);
                    created = new Date(cursor.getString(5));
                    dateDone = new Date(cursor.getString(6));
                    UUID = cursor.getString(7);
                    toDoDate = new Date(cursor.getString(8));
                    notify = cursor.getString(9);
                    hidden = cursor.getString(10);

                    Task task = new Task(UUID,title,description,categoryName,created,dateDone,toDoDate,Boolean.parseBoolean(done),Boolean.parseBoolean(notify),Boolean.parseBoolean(hidden));
                    taskList.add(task);
                }
            } while (cursor.moveToNext());
        }
        return taskList;
    }

    public ArrayList<Task> getTasks() throws ParseException
    {
        String countQuery = "SELECT * FROM TASKS";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        String title = "";
        String description = "";
        String done = "";
        String created = "";
        String dateDone = "";
        String UUID = "";
        String toDoDate ="";
        String notify ="";
        String hidden = "";

        Cursor categoryCursor;
        String categoryName = "";

        ArrayList<Task> taskList = new ArrayList<Task>();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm dd/MM/yyyy");

        if (cursor != null && cursor.moveToFirst())
        {
            do
            {
                title = cursor.getString(2);
                description = cursor.getString(3);
                done = cursor.getString(4);
                created = cursor.getString(5);
                dateDone = cursor.getString(6);
                UUID = cursor.getString(0);
                toDoDate = cursor.getString(8);
                notify = cursor.getString(9);

                int categoryId = cursor.getInt(1);

                countQuery = "SELECT * FROM CATEGORIES WHERE ID = '" + categoryId + "'";
                categoryCursor = db.rawQuery(countQuery, null);
                if (categoryCursor != null && categoryCursor.moveToFirst())
                {
                    categoryName = categoryCursor.getString(1);
                }
                Task task = new Task();
                task.setTitle(title);
                task.setCategory(categoryName);
                task.setDescription(description);
                task.setDone(Boolean.parseBoolean(done));



                task.setCreated(sdf.parse(created));
                if( Boolean.parseBoolean(done))
                    task.setDoneDate(sdf.parse(dateDone));
                task.setId(UUID);
                task.setToDoDate(sdf.parse(toDoDate));
                task.setNotificationStatus(Boolean.parseBoolean(notify));
                taskList.add(task);
            } while (cursor.moveToNext());
        }
        return taskList;
    }

    public void addTask(Task task)
    {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        String query = "SELECT ID FROM CATEGORIES WHERE CATEGORY_NAME LIKE '" + task.getCategory() + "'";
        Cursor getCategoryId = db.rawQuery(query, null);
        int catID = 0;
        if (getCategoryId != null && getCategoryId.moveToFirst())
        {
            catID = getCategoryId.getInt(0);
        }

        contentValues.put("CATEGORY_ID", catID);
        contentValues.put("TITLE", task.getTitle());
        contentValues.put("DESCRIPTION", task.getDescription());
        contentValues.put("DONE", task.getDone().toString());
        contentValues.put("CREATED",task.getCreated());
        if( task.getDone() )
            contentValues.put("DATEDONE", task.getDoneDate());
        else
            contentValues.putNull("DATEDONE");
        contentValues.put("UUID", task.getId());
        contentValues.put("DATETODO", task.getToDoDate());
        contentValues.put("NOTIFY", task.getNotificationStatus().toString());
        db.insert("TASKS", null, contentValues);
    }


    public void deleteTask(Task task)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("TASKS", "ID=?", new String[]{task.getId()});
    }

    // // // //
    // // // //
    // // // //

    public void editTask(Task task)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        String query = "SELECT ID FROM CATEGORIES WHERE CATEGORY_NAME LIKE '" + task.getCategory() + "'";
        Cursor getCategoryId = db.rawQuery(query, null);
        int catID = 0;
        if (getCategoryId != null && getCategoryId.moveToFirst())
        {
            catID = getCategoryId.getInt(0);
        }

        contentValues.put("CATEGORY_ID", catID);
        contentValues.put("TITLE", task.getTitle());
        contentValues.put("DESCRIPTION", task.getDescription());
        contentValues.put("DONE", task.getDone().toString());
        contentValues.put("CREATED", task.getCreated());
        if( task.getDone() )
            contentValues.put("DATEDONE", task.getDoneDate());
        else
            contentValues.putNull("DATEDONE");
        contentValues.put("UUID", task.getId());
        contentValues.put("DATETODO", task.getToDoDate());
        contentValues.put("NOTIFY", task.getNotificationStatus().toString());
        // Update database:
        db.update("TASKS",contentValues,"ID=?",
                new String[]{task.getId()});
    }



    public void displayTasks()
    {
        String queryString = "SELECT * FROM TASKS";

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(queryString, null);

        if (cursor.moveToFirst())
        {
            do
            {
                System.out.println(cursor.getString(1) + " " + cursor.getString(2) + " " + cursor.getString(3) + " " + cursor.getString(4)
                        + " " + cursor.getString(5) + " " + cursor.getString(6) + " " + cursor.getString(7)
                        + " " + cursor.getString(8)+ " " + cursor.getString(9));
                System.out.println("- - - - - - - - - - - -");

            }
            while (cursor.moveToNext());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1)
    {

    }
}