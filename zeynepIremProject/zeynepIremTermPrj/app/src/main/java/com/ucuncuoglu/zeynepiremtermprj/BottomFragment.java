package com.ucuncuoglu.zeynepiremtermprj;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class BottomFragment extends Fragment {

    ImageView frgBottomImg;
    int[] imgIds = new int[]{R.drawable.cat1, R.drawable.cat2, R.drawable.cat3};

    public BottomFragment() {

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_bottom, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        frgBottomImg = view.findViewById(R.id.frgBottomImg);

        int pos = getArguments().getInt("position");

        frgBottomImg.setImageResource(imgIds[pos]);


    }


    void changeCityImage(int position){
        frgBottomImg.setImageResource(imgIds[position]);
    }
}