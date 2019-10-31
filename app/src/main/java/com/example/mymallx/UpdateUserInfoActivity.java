package com.example.mymallx;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.widget.FrameLayout;

import com.google.android.material.tabs.TabLayout;

public class UpdateUserInfoActivity extends AppCompatActivity {
   private TabLayout tabLayout;
   private FrameLayout frameLayout;
   private UpdateInfoFragment updateInfoFragment;
   private UpdatePasswordFragment updatePasswordFragment;
   String name,email,photo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_user_info);

        tabLayout=findViewById(R.id.tabLayoutrId);
        frameLayout=findViewById(R.id.frameLayoutId);

        updateInfoFragment=new UpdateInfoFragment();
        updatePasswordFragment=new UpdatePasswordFragment();

        //get data frome my acounnt activity
        name=getIntent().getStringExtra("Name");
         email=getIntent().getStringExtra("Email");
         photo=getIntent().getStringExtra("Photo");

        //tablayout set selection listiner for change fragment
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition()==0)
                {
                    setFragment(updateInfoFragment,true);
                }
                if (tab.getPosition()==1)
                {
                    setFragment(updatePasswordFragment,false);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        tabLayout.getTabAt(0).select();//by default first fragment selected
        setFragment(updateInfoFragment,true);//by default first fragment selected


    }

    private void setFragment(Fragment fragment,boolean setbundel)
    {
        FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
        if (setbundel)
        {
            Bundle bundle=new Bundle();
            bundle.putString("Name",name);
            bundle.putString("Email",email);
            bundle.putString("Photo",photo);
            fragment.setArguments(bundle);
        }else
        {
            Bundle bundle=new Bundle();
            bundle.putString("Email",email);
            fragment.setArguments(bundle);
        }

        fragmentTransaction.replace(frameLayout.getId(),fragment);
        fragmentTransaction.commit();
    }

}
