package com.example.pokemonapp;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class ShowMovesList extends AppCompatActivity {

    ListView lv_savedMoves;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_moves_list);
        lv_savedMoves = findViewById(R.id.lv_movesList);
        //tv_name = findViewById(R.id.tv_name);

        MyApplication myApplication = (MyApplication) getApplicationContext();
        List<String> savedMoves = myApplication.getMovesList();


        lv_savedMoves.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, savedMoves));

    }
}