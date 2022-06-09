package com.example.androidtodolist;
import java.util.Comparator;

public class CustomComparator implements Comparator<Task>
{
    @Override
    public int compare(Task o1, Task o2) {
        return o1.getToDoDateD().compareTo(o2.getToDoDateD());
    }
}