package com.ucuncuoglu.zeynepiremtermprj;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;


import com.ucuncuoglu.zeynepiremtermprj.recyclerviewadapters.MyRecyclerViewAdapter;

public class MenuActivity extends AppCompatActivity {

    RecyclerView menuRecycler;
    LinearLayoutManager layoutManager;
    MyRecyclerViewAdapter mRecyclerAdapter;
    MediaPlayer player;

    // JSON related
    private String jsonStr;

    private JSONArray menus;
    private JSONObject menuJSONObject;

    private ArrayList<Menu> mArrayList;

    public static final String TAG_MENUS = "menus";
    public static final String TAG_FIELD = "field";
    public static final String TAG_INFO = "info";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        getSupportActionBar().hide();
        menuRecycler = findViewById(R.id.menuRecycler);

        layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        menuRecycler.setLayoutManager(layoutManager);

        mArrayList = new ArrayList<Menu>();

        //Reading the JSON file from the assets folder and storing it in a String
        jsonStr = loadFileFromAssets("menus.json");
        Log.d("TAG", "\n" + jsonStr);

        // Call to AsyncTask
        new MenuActivity.GetMenus().execute();

    }

    public void onClick(View view) {
        if(view.getId() == R.id.btnStop){
            if(player != null){
                player.release();
                player = null;
                Toast.makeText(this, "Media Released", Toast.LENGTH_SHORT).show();
            }
        }else if(view.getId() == R.id.btnSTART){
            player = MediaPlayer.create(this, R.raw.sound);
            player.setLooping(true);
            player.start();
        }
    }


    private class GetMenus extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Creating and showing the progress Dialog
            //mProgressBar.setVisibility(View.VISIBLE);
        }

        // Main job should be done here
        @Override
        protected Void doInBackground(Void... params) {
            //Log.d("TAG", "HERE.....");

            if (jsonStr != null) {
                try {
                    menuJSONObject = new JSONObject(jsonStr);
                    // Getting JSON Array
                    menus = menuJSONObject.getJSONArray(TAG_MENUS);


                    // looping through all books
                    for (int i = 0; i < menus.length(); i++) {

                        JSONObject jsonObj = menus.getJSONObject(i);

                        //Thread.sleep(2000);//This is here only to simulate parsing json takes time so that ProgressBar execution can be displayed better

                        String field = jsonObj.getString(TAG_FIELD);
                        String info = jsonObj.getString(TAG_INFO);



                        Menu menu = new Menu(field, info);

                        Log.d("KEY", menu+" "+field);
                        mArrayList.add(menu);
                    }
                } catch (JSONException ee) {
                    ee.printStackTrace();
                }
            }

            return null;
        }



        // What do you want to do after doInBackground() finishes
        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            // Dismiss the progress dialog
            //mProgressBar.setVisibility(View.INVISIBLE);

            if (mArrayList != null) {
                mRecyclerAdapter = new MyRecyclerViewAdapter(MenuActivity.this, mArrayList);
                menuRecycler.setAdapter(mRecyclerAdapter);
            } else
                Toast.makeText(MenuActivity.this, "Not Found", Toast.LENGTH_LONG).show();
        }

    }

    private String loadFileFromAssets(String fileName) {
        String fileContent = null;
        try {
            InputStream is = getBaseContext().getAssets().open(fileName);

            int size = is.available();
            byte[] buffer = new byte[size];

            is.read(buffer);
            is.close();

            fileContent = new String(buffer, "UTF-8");

        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return fileContent;
    }


}