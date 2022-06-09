package com.example.androidtodolist;

public class CategoriesModel {

    private boolean isSelected;
    private String name;

    public CategoriesModel(boolean isSelected, String name) {
        this.isSelected = isSelected;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setAnimal(String name) {
        this.name = name;
    }

    public boolean getSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public void changeSelected()
    {
        isSelected = !isSelected;
    }
}

