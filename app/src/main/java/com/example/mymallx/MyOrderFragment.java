package com.example.mymallx;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import static com.example.mymallx.MainActivity.HOME_FRAGMENT;
import static com.example.mymallx.MainActivity.MY_ORDER_FRAGMENT;
import static com.example.mymallx.MainActivity.currentFragment;


/**
 * A simple {@link Fragment} subclass.
 */
public class MyOrderFragment extends Fragment {

    RecyclerView myorderRecyclerView;
    public MyOrderFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        currentFragment=MY_ORDER_FRAGMENT;

        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_my_order, container, false);
        myorderRecyclerView=view.findViewById(R.id.myOrderRecyclerView_id);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        myorderRecyclerView.setLayoutManager(linearLayoutManager);

        List<MyOrderItemModel> myOrderItemModelList=new ArrayList<>();
        myOrderItemModelList.add(new MyOrderItemModel(R.mipmap.mobile,0,"pixel Xl (3gb Ram)","Delivered on Mon,15th Jan \n" +
                "2015"));
        myOrderItemModelList.add(new MyOrderItemModel(R.mipmap.mobile,3,"pixel x(3gb Ram)","Delivered on Mon,15th Jan \n" +
                "2018"));
        myOrderItemModelList.add(new MyOrderItemModel(R.mipmap.mobile,2,"pixel 5(3gb Ram)","cancelled"));
        myOrderItemModelList.add(new MyOrderItemModel(R.mipmap.mobile,0,"pixel 3(3gb Ram)","cancelled"));

        MyOrderAdapter myOrderAdapter=new MyOrderAdapter(myOrderItemModelList);
        myorderRecyclerView.setAdapter(myOrderAdapter);
        myOrderAdapter.notifyDataSetChanged();

        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);


    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        if (currentFragment==HOME_FRAGMENT) {
            super.onCreateOptionsMenu(menu, inflater);
        }

    }

}
