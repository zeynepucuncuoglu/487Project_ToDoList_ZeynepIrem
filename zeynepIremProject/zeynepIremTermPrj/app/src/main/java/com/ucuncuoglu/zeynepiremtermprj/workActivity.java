package com.ucuncuoglu.zeynepiremtermprj;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;

import com.ucuncuoglu.zeynepiremtermprj.databasehelpers.WorkDBHelper;
import com.ucuncuoglu.zeynepiremtermprj.databasetables.WorkTable;
import com.ucuncuoglu.zeynepiremtermprj.recyclerviewadapters.MyRecyclerViewWorkAdapter;

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

public class workActivity extends AppCompatActivity {

    @BindView(R.id.workRecyc) RecyclerView recyclerViewWork;
    @BindView(R.id.edtvWorkTopic) EditText edtvWorkTopic;
    @BindView(R.id.edtvWorkDue) EditText edtvWorkDueDate;
    @BindView(R.id.edtvWorkID) EditText edtvWorkID;

    Intent serviceIntent;
    WorkDBHelper dbHelper;
    ArrayList<Activity> workList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work);
        getSupportActionBar().hide();
        ButterKnife.bind(this);


        try {
            String fileToDatabase = "/data/data/" + getPackageName() + "/databases/"+ WorkDBHelper.DATABASE_NAME;
            File file = new File(fileToDatabase);
            File pathToDatabasesFolder = new File("/data/data/" + getPackageName() + "/databases/");
            if (!file.exists()) {
                pathToDatabasesFolder.mkdirs();
                Log.d("BURDA", "BURDA");
                CopyDB( getResources().getAssets().open(WorkDBHelper.DATABASE_NAME+".db"),
                        new FileOutputStream(fileToDatabase));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        dbHelper = new WorkDBHelper(this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerViewWork.setLayoutManager(layoutManager);

        workList = WorkTable.getAllWorks(dbHelper);

        MyRecyclerViewWorkAdapter adapter = new MyRecyclerViewWorkAdapter(this, workList);
        recyclerViewWork.setAdapter(adapter);
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

    @OnClick(R.id.btnWorkAdd) public void add(){
        boolean res;
        String workTopic = edtvWorkTopic.getText().toString();
        String workDueDate = edtvWorkDueDate.getText().toString();
        int workID = Integer.parseInt(edtvWorkID.getText().toString());

        serviceIntent = new Intent(this, ToDoListIntentService.class);

        res = WorkTable.insert(dbHelper, workID, workTopic, workDueDate);
        if(res) {
            serviceIntent.putExtra("notification", "You have new activity");
            startService(serviceIntent);
        }else{
            serviceIntent.putExtra("notification", "insert can not be done");
            startService(serviceIntent);
        }
    }

    @OnClick(R.id.btnWorkBack) public void back(){
        Intent intent = new Intent(workActivity.this, MenuActivity.class);
        startActivity(intent);
    }
}