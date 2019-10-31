package com.example.mymallx;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import static com.example.mymallx.RegesterActivity.onResetPasswordFragmnet;

public class SigninFragment extends Fragment {
    FirebaseAuth firebaseAuth;

    public SigninFragment() {
        // Required empty public constructor
    }

    private TextView dontHaveAAcount, forgetPassword;
    private FrameLayout ParentframeLayout;
    private EditText emailSignin, passwordSignin;
    Button signInbtn;
    ProgressBar progressBar;
    ImageButton closeImageButton;
    FrameLayout frameLayout;
    private String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+.[a-z]+";

    public static boolean disableCloseBtn = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_signin, container, false);
        dontHaveAAcount = view.findViewById(R.id.dontalreadyHaveAcountsignInbtn);

        emailSignin = view.findViewById(R.id.signInEmailId);
        passwordSignin = view.findViewById(R.id.signinPasswordId);
        signInbtn = view.findViewById(R.id.signBtnId);
        progressBar = view.findViewById(R.id.signInprogressBarId);
        closeImageButton = view.findViewById(R.id.signInCloseBtnId);

        forgetPassword = view.findViewById(R.id.signinForgetPasswordId);

        firebaseAuth = FirebaseAuth.getInstance();

//        fragment ar bahira frameLayout ar jonno aivaba getActivity() diya find kira laga
        ParentframeLayout = getActivity().findViewById(R.id.RegesterFrameLayoutId);

        closeImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainIntent();
            }
        });


        if (disableCloseBtn) {
            closeImageButton.setVisibility(View.GONE);
        } else {
            closeImageButton.setVisibility(View.VISIBLE);
        }
        return view;
    }


    //    when click dont have a account
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        //SignUp page fragment
        dontHaveAAcount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                fregment set function
                setFragment(new SignUpFragment());
            }
        });

//        check all input filed is valid or filledup
        emailSignin.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                //check function
                checkInputs();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        passwordSignin.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                //check function
                checkInputs();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        //signIn button
        signInbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkEmailAndePassword();
            }
        });

        //forget password btn
        forgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                onResetPasswordFragmnet = true;
                setFragmentforget(new ResetPasswordFragment());
            }
        });

    }


    //    check email validation and password
//    sen data on firebase and user login
    private void checkEmailAndePassword() {
        final Drawable customeErrorIcon = getResources().getDrawable(R.mipmap.custom_error_icon);
        customeErrorIcon.setBounds(0, 0, customeErrorIcon.getIntrinsicWidth(), customeErrorIcon.getIntrinsicHeight());
        if (emailSignin.getText().toString().matches(emailPattern)) {
            if (passwordSignin.length() >= 8) {
                progressBar.setVisibility(View.VISIBLE);
//               when click button data comunication is start so signup button must be deisable
                signInbtn.setEnabled(false);
                signInbtn.setTextColor(Color.argb(50, 255, 255, 255));
                firebaseAuth.signInWithEmailAndPassword(emailSignin.getText().toString(), passwordSignin.getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    mainIntent();
                                } else {
                                    progressBar.setVisibility(View.INVISIBLE);
                                    signInbtn.setEnabled(true);
                                    passwordSignin.setText("");
                                    passwordSignin.setError("you Password is incorect", customeErrorIcon);
                                    signInbtn.setTextColor(Color.rgb(255, 255, 255));
                                    String error = task.getException().getMessage();
                                    Toast.makeText(getActivity(), error, Toast.LENGTH_LONG).show();
                                }
                            }
                        });
            } else {
                passwordSignin.setError("you Password is incorect", customeErrorIcon);
            }
        } else {
            emailSignin.setError("you email is incorect", customeErrorIcon);
        }
    }


    //    check input fileds
    private void checkInputs() {

        if (!TextUtils.isEmpty(emailSignin.getText())) {
            if (!TextUtils.isEmpty(passwordSignin.getText())) {
                signInbtn.setEnabled(true);
                signInbtn.setTextColor(Color.rgb(255, 255, 255));
            } else {
                signInbtn.setEnabled(false);
                signInbtn.setTextColor(Color.argb(50, 255, 255, 255));
            }
        } else {
            signInbtn.setEnabled(false);
            signInbtn.setTextColor(Color.argb(50, 255, 255, 255));
        }

    }

    //    fragment set korar jonno function
    private void setFragment(Fragment fragment) {

        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.slide_from_right, R.anim.slideout_from_left);
        fragmentTransaction.replace(ParentframeLayout.getId(), fragment);
        fragmentTransaction.commit();
    }

    //forget password fragment
    private void setFragmentforget(Fragment fragment) {

        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.slide_from_right, R.anim.slideout_from_left);
        fragmentTransaction.replace(ParentframeLayout.getId(), fragment);
        fragmentTransaction.commit();
    }


    //main intent
    private void mainIntent() {

        if (disableCloseBtn)
        {
            disableCloseBtn=false;
        }else {
            Intent mainIntent = new Intent(getActivity(), MainActivity.class);
            startActivity(mainIntent);
        }
        getActivity().finish();

    }
}
