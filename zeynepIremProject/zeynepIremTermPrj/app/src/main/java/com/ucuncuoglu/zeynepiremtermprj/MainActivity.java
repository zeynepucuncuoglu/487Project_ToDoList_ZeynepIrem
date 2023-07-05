package com.ucuncuoglu.zeynepiremtermprj;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GestureDetectorCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements  TopFragment.TopFragmentInterface{

    TopFragment topFragment;
    BottomFragment bottomFragment;
    private GestureDetectorCompat mDetector;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        mDetector = new GestureDetectorCompat(this, new MyGestureListener());

        topFragment = (TopFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentTop);
        /*****************************************/

        bottomFragment = new BottomFragment();
        Bundle b = new Bundle();
        b.putInt("position",2);
        bottomFragment.setArguments(b);

        FragmentManager fragmentManager = this.getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.dynamicFragmentLayout, bottomFragment);
        fragmentTransaction.commit();

    }
    @Override
    public boolean onTouchEvent(MotionEvent event){
        mDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    class MyGestureListener extends GestureDetector.SimpleOnGestureListener {
        Intent intent = new Intent(MainActivity.this, MenuActivity.class);
        Bundle bundle = new Bundle();
        public boolean onDoubleTap(MotionEvent event) {

            bundle.putInt("key1", 1);
            intent.putExtras(bundle);
            startActivity(intent);
            return true;
        }

        public void onLongPress(MotionEvent motionEvent) {
            bundle.putInt("key1", 2);
            intent.putExtras(bundle);
            startActivity(intent);
        }
    }

    public void changeImage(int position) {
        bottomFragment.changeCityImage(position);
    }

}