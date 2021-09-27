package com.example.pokemonapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    Button btn_showAllMoves, btn_searchPokemon;
    EditText et_pokemonName;
    TextView tv_nameDisplay, tv_name, tv_weightDisplay, tv_weight, tv_heightDisplay, tv_height, tv_typeDisplay, tv_type;
    List<String> savedMoves;
    ImageView iv_pokemonImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn_showAllMoves = findViewById(R.id.btn_seeMoves);
        btn_searchPokemon = findViewById(R.id.btn_searchPokemon);
        et_pokemonName = findViewById(R.id.et_pokemonNameInput);
        tv_name = findViewById(R.id.tv_name);        tv_height = findViewById(R.id.tv_height);
        tv_weight = findViewById(R.id.tv_weight);
        tv_type = findViewById(R.id.tv_type);
        tv_nameDisplay = findViewById(R.id.tv_nameDisplay);
        tv_heightDisplay = findViewById(R.id.tv_heightDisplay);
        tv_weightDisplay = findViewById(R.id.tv_weightDisplay);
        tv_typeDisplay = findViewById(R.id.tv_typeDisplay);
        iv_pokemonImage = findViewById(R.id.iv_pokemonImage);

        //iv_pokemonImage.setImageResource(R.drawable.pokeball);
        btn_searchPokemon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Instantiate the RequestQueue.
                RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
                String url = "https://pokeapi.co/api/v2/pokemon/" + et_pokemonName.getText().toString().toLowerCase();

                MyApplication myApplication = (MyApplication) getApplicationContext();
                savedMoves = myApplication.getMovesList();
                savedMoves.clear();

                JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {

                            JSONObject pokemonImages = response.getJSONObject("sprites");
                            String pokemonImage = pokemonImages.getString("front_default");
                            Glide.with(MainActivity.this).load(pokemonImage).into(iv_pokemonImage);


                            String pokemonName = response.getString("name");
                            tv_name.setText(pokemonName.toUpperCase());

                            String weight = response.getString("weight");
                            tv_weight.setText(weight.toUpperCase() + " hectogram");

                            String height = response.getString("height");
                            tv_height.setText(height.toUpperCase() + " decimeter");

                            JSONArray types = response.getJSONArray("types");
                            JSONObject firstType = types.getJSONObject(0);
                            JSONObject secondType = firstType.getJSONObject("type");
                            String typeName = secondType.getString("name");
                            tv_type.setText(typeName.toUpperCase());

                            JSONArray moves = response.getJSONArray("moves");
                            myApplication.setMovesList(savedMoves);
                            for (int i = 0; i < moves.length(); i++) {
                                JSONObject moveGroup = moves.getJSONObject(i);
                                JSONObject move = moveGroup.getJSONObject("move");
                                String moveName = move.getString("name");
                                savedMoves.add(moveName);
                                //Toast.makeText(MainActivity.this, moveName, Toast.LENGTH_SHORT).show();
                                myApplication.setMovesList(savedMoves);
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        //Toast.makeText(MainActivity.this, response.toString(), Toast.LENGTH_LONG).show();

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this, "Something Wrong", Toast.LENGTH_SHORT).show();
                    }
                });

                queue.add(request);
//                // Request a string response from the provided URL.
//                StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
//                        new Response.Listener<String>() {
//                            @Override
//                            public void onResponse(String response) {
//                                Toast.makeText(MainActivity.this, response, Toast.LENGTH_SHORT).show();
//                            }
//                        }, new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();
//                    }
//                });

                // Add the request to the RequestQueue.

            }
        });

        btn_showAllMoves.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(MainActivity.this, ShowMovesList.class);
                startActivity(i);
                Toast.makeText(MainActivity.this, "Searching moves for pokemon: " + et_pokemonName.getText().toString(), Toast.LENGTH_SHORT).show();
            }
        });


    }
}