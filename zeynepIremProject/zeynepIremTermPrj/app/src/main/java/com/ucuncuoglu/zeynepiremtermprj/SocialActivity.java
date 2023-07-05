package com.ucuncuoglu.zeynepiremtermprj;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;

import com.ucuncuoglu.zeynepiremtermprj.databasehelpers.SocialDBHelper;
import com.ucuncuoglu.zeynepiremtermprj.databasetables.SocialTable;
import com.ucuncuoglu.zeynepiremtermprj.recyclerviewadapters.MyRecyclerViewSocialAdapter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SocialActivity extends AppCompatActivity {

    @BindView(R.id.socRecyc) RecyclerView recyclerViewSoc;
    @BindView(R.id.edtvSocTopic) EditText edtvSocTopic;
    @BindView(R.id.edtvSocDue) EditText edtvSocDueDate;
    @BindView(R.id.edtvSocID) EditText edtvsocID;

    Intent serviceIntent;
    SocialDBHelper dbHelper;
    ArrayList<Activity> socialList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_social);
        getSupportActionBar().hide();
        ButterKnife.bind(this);


        try {
            String fileToDatabase = "/data/data/" + getPackageName() + "/databases/"+ SocialDBHelper.DATABASE_NAME;
            File file = new File(fileToDatabase);
            File pathToDatabasesFolder = new File("/data/data/" + getPackageName() + "/databases/");
            if (!file.exists()) {
                pathToDatabasesFolder.mkdirs();
                Log.d("BURDA", "BURDA");
                CopyDB( getResources().getAssets().open(SocialDBHelper.DATABASE_NAME+".db"),
                        new FileOutputStream(fileToDatabase));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        dbHelper = new SocialDBHelper(this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerViewSoc.setLayoutManager(layoutManager);

        socialList = SocialTable.getAllSocials(dbHelper);

        MyRecyclerViewSocialAdapter adapter = new MyRecyclerViewSocialAdapter(this, socialList);
        recyclerViewSoc.setAdapter(adapter);
    }

    public void CopyDB(InputStream inputStream, OutputStream outputStream) throws IOException {
        // Copy 1K bytes at a time
        byte[] buffer = new byte[1024];
        int length;
        Log.d("BURDA", "BURDA2");

        while ((length = inputStream.read(buffer)) > 0) {
            outputStream.write(buffer, 0, length);
            Log.d("BURDA", "BURDA3");
        }
        inputStream.close();
        outputStream.close();
    }

    @OnClick(R.id.btnSocAdd) public void add(){
        boolean res;
        String socTopic = edtvSocTopic.getText().toString();
        String socDueDate = edtvSocDueDate.getText().toString();
        int socID = Integer.parseInt(edtvsocID.getText().toString());

        serviceIntent = new Intent(this, ToDoListIntentService.class);


        res = SocialTable.insert(dbHelper, socID, socTopic, socDueDate);
        if(res) {
            serviceIntent.putExtra("notification", "You have new activity");
            startService(serviceIntent);
        }else{
            serviceIntent.putExtra("notification", "insert can not be done");
            startService(serviceIntent);
        }

    }

    @OnClick(R.id.btnSocBack) public void back(){
        Intent intent = new Intent(SocialActivity.this, MenuActivity.class);
        startActivity(intent);
    }

}