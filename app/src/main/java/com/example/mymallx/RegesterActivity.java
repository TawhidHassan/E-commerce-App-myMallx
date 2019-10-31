package com.example.mymallx;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.FrameLayout;

public class RegesterActivity extends AppCompatActivity {

    FrameLayout frameLayout;
    public static Boolean setSignUpFragment=false;

    //    for mobuke backbutton
    public static Boolean onResetPasswordFragmnet=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regester);

        frameLayout=findViewById(R.id.RegesterFrameLayoutId);
        if(setSignUpFragment)
        {
            setSignUpFragment=false;
            setFragment(new SignUpFragment());
        }else
        {
            setFragment(new SigninFragment());
        }


    }


    //for set fragment in frame layout
    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(frameLayout.getId(),fragment);
        fragmentTransaction.commit();
    }

    //function for mobile back button-> for backbutton click then app not get out
//    work on reset password fragment
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode==KeyEvent.KEYCODE_BACK)
        {
            SignUpFragment.disablecloseBtn=false;
            SigninFragment.disableCloseBtn=false;
            if (onResetPasswordFragmnet)
            {
                onResetPasswordFragmnet=false;
                setFragmentreset(new SigninFragment());
                return  false;
            }
        }

        return super.onKeyDown(keyCode, event);
    }

    //    fragment set korar jonno function
    private void setFragmentreset(Fragment fragment) {

        FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.slide_from_left,R.anim.slideout_from_right);
        fragmentTransaction.replace(frameLayout.getId(),fragment);
        fragmentTransaction.commit();
    }
}
