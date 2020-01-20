package com.example.restro.my_fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.restro.R;
import com.example.restro.activity.MenuListActivity;
import com.example.restro.my_adapter.GalleryAdapter;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;


public class FirstFloorFragment extends Fragment {
    private Button btnServe;
    private ArrayList<String> tableList=new ArrayList<>();
    private GridView gridView;
    private ImageView img;
    private int selectedItem=-1;
    private String mytext,floor;
    private LinearLayout linearLayout;
    private GalleryAdapter galleryAdapter;
    private SharedPreferences preferences;
    public FirstFloorFragment(){

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.first_floor_screen,container,false);
        floor="First Floor";
        gridView=view.findViewById(R.id.grdImages);
        tableList.clear();
        tableList.add("A1");
        tableList.add("A2");
        tableList.add("A3");
        tableList.add("A5");
        tableList.add("A6");
        tableList.add("A7");
        tableList.add("A8");
        tableList.add("A9");
        tableList.add("A10");
        tableList.add("A11");
        tableList.add("A12");
        preferences= getActivity().getSharedPreferences("user_details",MODE_PRIVATE);

        galleryAdapter = new GalleryAdapter(getActivity(),tableList);
        gridView.setAdapter(galleryAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                linearLayout=view.findViewById(R.id.mybg);
                img=view.findViewById(R.id.table_img);
                linearLayout.setBackgroundColor(Color.parseColor("#ffbd69"));
                mytext= (String) parent.getItemAtPosition(position);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("selected",mytext);

                editor.commit();
                Intent intent=new Intent(getActivity(), MenuListActivity.class);
                intent.putExtra("tablename",mytext);
                intent.putExtra("floor",floor);
                intent.putExtra("tableList",tableList);
                startActivity(intent);
                getActivity().finish();

            }

        });

        return view;
    }



}
