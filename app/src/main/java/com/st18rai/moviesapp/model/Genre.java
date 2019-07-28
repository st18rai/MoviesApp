package com.st18rai.moviesapp.model;

public class Genre {
    private int id;
    private String name;
    private boolean selected;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
