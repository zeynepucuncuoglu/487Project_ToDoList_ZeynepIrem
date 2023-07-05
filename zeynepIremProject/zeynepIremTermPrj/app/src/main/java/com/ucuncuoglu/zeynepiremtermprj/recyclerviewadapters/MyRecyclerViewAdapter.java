package com.ucuncuoglu.zeynepiremtermprj.recyclerviewadapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.ucuncuoglu.zeynepiremtermprj.Menu;
import com.ucuncuoglu.zeynepiremtermprj.R;
import com.ucuncuoglu.zeynepiremtermprj.ShoppingActivity;
import com.ucuncuoglu.zeynepiremtermprj.SocialActivity;
import com.ucuncuoglu.zeynepiremtermprj.workActivity;

import java.util.ArrayList;

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.MyRecyclerViewItemHolder> {
    private Context context;
    private ArrayList<Menu> recyclerItemValues;

    private int[] ImgIds = {R.drawable.work, R.drawable.shopping, R.drawable.social};

    public MyRecyclerViewAdapter(Context context, ArrayList<Menu> values){
        this.context = context;
        this.recyclerItemValues = values;
    }

    @NonNull
    @Override
    public MyRecyclerViewItemHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflator = LayoutInflater.from(viewGroup.getContext());

        View itemView = inflator.inflate(R.layout.recycler_item, viewGroup, false);

        MyRecyclerViewItemHolder mViewHolder = new MyRecyclerViewItemHolder(itemView);
        return mViewHolder;
    }

    public void openOtherActivity( Intent intent){
        context.startActivity(intent);
    }


    @Override
    public void onBindViewHolder(@NonNull MyRecyclerViewItemHolder myRecyclerViewItemHolder, int i) {

        final Menu mn= recyclerItemValues.get(i);

        Intent intent ;
        myRecyclerViewItemHolder.tvField.setText(mn.getField());
        myRecyclerViewItemHolder.tvInfo.setText(mn.getInfo());
        myRecyclerViewItemHolder.img.setImageResource(ImgIds[i]);

        if(i == 0) {
            intent = new Intent(context, workActivity.class);
        } else if(i == 1){
            intent = new Intent(context, ShoppingActivity.class);
        }else{
            intent = new Intent(context, SocialActivity.class);
        }

        myRecyclerViewItemHolder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openOtherActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return recyclerItemValues.size();
    }

     class MyRecyclerViewItemHolder extends  RecyclerView.ViewHolder{
        TextView tvInfo, tvField;
        ImageView img;
        ConstraintLayout parentLayout;
        public MyRecyclerViewItemHolder(@NonNull View itemView) {
            super(itemView);
            tvInfo = itemView.findViewById(R.id.tvInfo);
            tvField = itemView.findViewById(R.id.tvField);
            img = itemView.findViewById(R.id.imgItem);
            parentLayout = itemView.findViewById(R.id.itemConstraintLayout);
        }
    }

}
