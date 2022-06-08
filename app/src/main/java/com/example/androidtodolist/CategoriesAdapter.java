package com.example.androidtodolist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;

public class CategoriesAdapter extends ArrayAdapter<CategoriesModel> {

    private static CustomAdapter.ItemClickListener mClickListener;

    public CategoriesAdapter(Context context, ArrayList<CategoriesModel> users,CustomAdapter.ItemClickListener mClickListener) {
        super(context, 0, users);
        CategoriesAdapter.mClickListener = mClickListener;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        CategoriesModel categoriesModel = getItem(position);
        if (convertView == null) {

            convertView = LayoutInflater.from(getContext()).inflate(R.layout.category_chose_list, parent, false);

        }
        TextView tvName = (TextView) convertView.findViewById(R.id.category_in_list);
        CheckBox checkBox =  convertView.findViewById(R.id.cb);

        tvName.setText(categoriesModel.getName());
        checkBox.setChecked(categoriesModel.getSelected());

        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mClickListener.onItemClick(view,position);
            }
        });
        // Return the completed view to render on screen

        return convertView;

    }


}