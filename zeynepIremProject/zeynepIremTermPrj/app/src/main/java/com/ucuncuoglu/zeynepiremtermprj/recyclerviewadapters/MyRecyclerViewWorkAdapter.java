package com.ucuncuoglu.zeynepiremtermprj.recyclerviewadapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.ucuncuoglu.zeynepiremtermprj.Activity;
import com.ucuncuoglu.zeynepiremtermprj.R;
import com.ucuncuoglu.zeynepiremtermprj.databasetables.WorkTable;
import com.ucuncuoglu.zeynepiremtermprj.databasehelpers.WorkDBHelper;

import java.util.ArrayList;

public class MyRecyclerViewWorkAdapter extends RecyclerView.Adapter<MyRecyclerViewWorkAdapter.MyRecyclerViewItemHolder> {
    private Context context;
    private ArrayList<Activity> recyclerItemValues;
    WorkDBHelper workdbHelper;

    public MyRecyclerViewWorkAdapter(Context context, ArrayList<Activity> values){
        this.context = context;
        this.recyclerItemValues = values;
        workdbHelper = new WorkDBHelper(context);
    }

    @NonNull
    @Override
    public MyRecyclerViewItemHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflator = LayoutInflater.from(viewGroup.getContext());

        View itemView = inflator.inflate(R.layout.recycleractivity_item, viewGroup, false);

        MyRecyclerViewItemHolder mViewHolder = new MyRecyclerViewItemHolder(itemView);
        return mViewHolder;
    }

    public void openOtherActivity( Intent intent){
        context.startActivity(intent);
    }


    @Override
    public void onBindViewHolder(@NonNull MyRecyclerViewItemHolder myRecyclerViewItemHolder, int i) {

        final Activity sp= recyclerItemValues.get(i);

        myRecyclerViewItemHolder.topicTv.setText(sp.getTopic());
        myRecyclerViewItemHolder.dueDateTv.setText(sp.getDuedate());

        myRecyclerViewItemHolder.btnDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean res = WorkTable.delete(workdbHelper, sp.getId()+"");

                if(res) {
                    Toast.makeText(context, "Wooww, You completed your task!", Toast.LENGTH_SHORT).show();
                    refreshMyAdapterAfterDelete(i);
                }else{
                    Toast.makeText(context, "delete can not be done", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void refreshMyAdapterAfterDelete(int position){

        recyclerItemValues.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public int getItemCount() {
        return recyclerItemValues.size();
    }

     class MyRecyclerViewItemHolder extends  RecyclerView.ViewHolder{
        TextView topicTv;
        TextView dueDateTv;
        Button btnDel;
        ConstraintLayout parentLayout;
        public MyRecyclerViewItemHolder(@NonNull View itemView) {
            super(itemView);
            topicTv = itemView.findViewById(R.id.tvField);
            dueDateTv = itemView.findViewById(R.id.dueDateEt);
            btnDel = itemView.findViewById(R.id.btnDel);
            parentLayout = itemView.findViewById(R.id.itemConstraintLayout);
        }
    }

}
