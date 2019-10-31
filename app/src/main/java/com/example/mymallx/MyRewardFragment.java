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
import static com.example.mymallx.MainActivity.MY_REWARD_FRAGMENT;
import static com.example.mymallx.MainActivity.currentFragment;


/**
 * A simple {@link Fragment} subclass.
 */
public class MyRewardFragment extends Fragment {

    RecyclerView rewardRecyclerView;

    public MyRewardFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        currentFragment=MY_REWARD_FRAGMENT;

        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_my_reward, container, false);
        rewardRecyclerView=view.findViewById(R.id.my_reward_recycler_id);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        rewardRecyclerView.setLayoutManager(linearLayoutManager);

        List<RewardModel>rewardModelList=new ArrayList<>();
        rewardModelList.add(new RewardModel("Eid Offer","till 3rd, August 2018","GET 20% OFF on any product above Rs.500/- and below Rs.2500/-"));
        rewardModelList.add(new RewardModel("New Year Offer","till 3rd, August 2019","GET 20% OFF on any 3 product above Rs.400/- and below Rs.2200/-"));
        rewardModelList.add(new RewardModel("New Year Offer","till 3rd, August 2019","GET 20% OFF on any 3 product above Rs.400/- and below Rs.2200/-"));
        rewardModelList.add(new RewardModel("New Year Offer","till 3rd, August 2019","GET 20% OFF on any 3 product above Rs.400/- and below Rs.2200/-"));

        MyRewardAdapter myRewardAdapter=new MyRewardAdapter(rewardModelList,false);
        rewardRecyclerView.setAdapter(myRewardAdapter);
        myRewardAdapter.notifyDataSetChanged();
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
