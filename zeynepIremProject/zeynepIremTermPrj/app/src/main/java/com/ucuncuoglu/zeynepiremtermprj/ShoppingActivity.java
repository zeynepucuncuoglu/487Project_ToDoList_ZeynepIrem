package com.ucuncuoglu.zeynepiremtermprj;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

import com.ucuncuoglu.zeynepiremtermprj.databasehelpers.ShoppingDBHelper;
import com.ucuncuoglu.zeynepiremtermprj.databasetables.ShoppingTable;
import com.ucuncuoglu.zeynepiremtermprj.recyclerviewadapters.MyRecyclerViewShoppingAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ShoppingActivity extends AppCompatActivity {

    @BindView(R.id.shopRecyc) RecyclerView recyclerViewShopping;
    @BindView(R.id.edtvShopDue) EditText EdtvShopDueDate;
    @BindView(R.id.edtvShopTopic) EditText edtvShopTopic;
    @BindView(R.id.edtvID) EditText edtvID;

    Intent serviceIntent;
    ShoppingDBHelper dbHelper;
    ArrayList<Activity> shopList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping);
        getSupportActionBar().hide();
        ButterKnife.bind(this);

        dbHelper = new ShoppingDBHelper(this);
        shopList = ShoppingTable.getAllShops(dbHelper);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerViewShopping.setLayoutManager(layoutManager);
        MyRecyclerViewShoppingAdapter adapter = new MyRecyclerViewShoppingAdapter(this, shopList);
        recyclerViewShopping.setAdapter(adapter);
    }

    @OnClick(R.id.btnShopAdd)public void add(){
        boolean res;
        String shopTopic = edtvShopTopic.getText().toString();
        String shopDueDate = EdtvShopDueDate.getText().toString();
        int shopID = Integer.parseInt(edtvID.getText().toString());

        serviceIntent = new Intent(this, ToDoListIntentService.class);

        res = ShoppingTable.insert(dbHelper, shopID, shopTopic, shopDueDate);
        if(res) {
            serviceIntent.putExtra("notification", "You have new activity");
            startService(serviceIntent);
        }else{
            serviceIntent.putExtra("notification", "insert can not be done");
            startService(serviceIntent);
        }
    }

    @OnClick(R.id.btnShopBack) public void back(){
        Intent intent = new Intent(ShoppingActivity.this, MenuActivity.class);
        startActivity(intent);
    }

}