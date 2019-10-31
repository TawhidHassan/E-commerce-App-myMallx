package com.example.mymallx;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.example.mymallx.MainActivity.HOME_FRAGMENT;
import static com.example.mymallx.MainActivity.currentFragment;


/**
 * A simple {@link Fragment} subclass.
 */
public class MyAccountFragment extends Fragment {

   Button profile_viewAll;
   CircleImageView profileImage;
   TextView name,email;
   FloatingActionButton settingBtn;
   LinearLayout layoutContainer;


   //my address ar jonno
   public static final int MANAGE_ADDRESS=1;
    //my address ar jonno

    public MyAccountFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_my_account, container, false);
        profile_viewAll=view.findViewById(R.id.profile_viewAll_id);

        profile_viewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent MyaddressInten=new Intent(getContext(),MyAddressActivity.class);
                MyaddressInten.putExtra("MODE",MANAGE_ADDRESS);
                getActivity().startActivity(MyaddressInten);
            }
        });

        profileImage=view.findViewById(R.id.profile_image);
        name=view.findViewById(R.id.userName_id);
        email=view.findViewById(R.id.usereEmail_id);
        settingBtn=view.findViewById(R.id.settingBtn_id);
        layoutContainer=view.findViewById(R.id.layoutContainerID);

        name.setText(DBqueries.fullName);
        email.setText(DBqueries.email);
        if (!DBqueries.profile.equals(""))
        {
            Glide.with(getContext()).load(DBqueries.profile).apply(new RequestOptions().placeholder(R.mipmap.profile_placeholder)).into(profileImage);
        }

        settingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent updateUserInfoIntent=new Intent(getContext(),UpdateUserInfoActivity.class);
                updateUserInfoIntent.putExtra("Name",name.getText());
                updateUserInfoIntent.putExtra("Email",email.getText());
                updateUserInfoIntent.putExtra("Photo",DBqueries.profile);
                startActivity(updateUserInfoIntent);
            }
        });
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
