package com.ucuncuoglu.zeynepiremtermprj;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class TopFragment extends Fragment {


    Spinner frgTopSpinnerCity;
    boolean isDefaultSelection=true;


    TopFragmentInterface topFragmentInterfaceListener;

    interface  TopFragmentInterface{
        public void changeImage(int position);
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof TopFragmentInterface )
            topFragmentInterfaceListener = (TopFragmentInterface) context;

    }

    /*****************************************/

    public TopFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_top, container, false) ;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        frgTopSpinnerCity = view.findViewById(R.id.frgTopSpinnerCity);

        frgTopSpinnerCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(isDefaultSelection)
                    isDefaultSelection = false;
                else{
                    String str = frgTopSpinnerCity.getSelectedItem().toString();
                    Toast.makeText(getActivity(), str+" is selected from Top Fragement",Toast.LENGTH_SHORT).show();

                    topFragmentInterfaceListener.changeImage(position);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        Toast.makeText(getActivity(), "Top Fragment loaded",Toast.LENGTH_SHORT).show();
    }
}