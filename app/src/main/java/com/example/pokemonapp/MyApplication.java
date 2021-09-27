package com.example.pokemonapp;

import android.app.Application;

import java.util.ArrayList;
import java.util.List;

public class MyApplication extends Application {
    private static MyApplication singleton;
    private List<String> movesList;

    public List<String> getMovesList() {
        return movesList;
    }

    public void setMovesList(List<String> movesList) {
        this.movesList = movesList;
    }

    public MyApplication getInstance() {
        return singleton;
    }

    public void onCreate() {
        super.onCreate();
        singleton = this;
        movesList = new ArrayList<>();
    }
}
