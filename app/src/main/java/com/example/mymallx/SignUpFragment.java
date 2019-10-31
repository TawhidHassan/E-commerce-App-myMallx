package com.example.mymallx;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
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
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class SignUpFragment extends Fragment {
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;

    public SignUpFragment() {
        // Required empty public constructor
    }

    private TextView AlreadyHaveAAcount;
    private FrameLayout ParentframeLayout;
    private EditText emailText, fullNameText, passwordText, confirmPasswordtext;
    private Button signUpbtn;
    private ImageButton closeBtn;
    private ProgressBar progressBar;
    private String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+.[a-z]+";

    public static boolean disablecloseBtn = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

//        fragment ar m oddha tai view dia sob kiso access kora lagba
        View view = inflater.inflate(R.layout.fragment_sign_up, container, false);
        AlreadyHaveAAcount = view.findViewById(R.id.alreadyHaveAcountsignInbtn);

//        fragment ar bahira frameLayout ar jonno aivaba getActivity() diya find kira laga
        ParentframeLayout = getActivity().findViewById(R.id.RegesterFrameLayoutId);

        emailText = view.findViewById(R.id.signUpEmailId);
        fullNameText = view.findViewById(R.id.signUpNameId);
        passwordText = view.findViewById(R.id.signUpPasswordId);
        confirmPasswordtext = view.findViewById(R.id.signUpConfirmPassid);

        signUpbtn = view.findViewById(R.id.signUpBtnId);
        closeBtn = view.findViewById(R.id.signUpCloseBtnId);

        progressBar = view.findViewById(R.id.signUpprogressBarId);

        if (disablecloseBtn) {
            closeBtn.setVisibility(View.GONE);
        } else {
            closeBtn.setVisibility(View.VISIBLE);
        }
        return view;
    }


    //    when click dont have a account
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        //already have Acount Button
        AlreadyHaveAAcount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                fregment set function
                setFragment(new SigninFragment());
            }
        });

        //check validation for enable signup button
        emailText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

//                when use typing then onTextChanged is called
                checkInputs();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        fullNameText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

//                when use typing then onTextChanged is called
                checkInputs();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        passwordText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

//                when use typing then onTextChanged is called
                checkInputs();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        confirmPasswordtext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

//                when use typing then onTextChanged is called
                checkInputs();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


//        SignUp button
        signUpbtn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {

                checkEmailAndePassword();
            }
        });

        //close btn
        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainIntent();
            }
        });

    }


    //    when signUp button click check validatyed email adress and password  and confirmPassword check
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void checkEmailAndePassword() {

        Drawable customeErrorIcon = getResources().getDrawable(R.mipmap.custom_error_icon);
        customeErrorIcon.setBounds(0, 0, customeErrorIcon.getIntrinsicWidth(), customeErrorIcon.getIntrinsicHeight());
        if (emailText.getText().toString().matches(emailPattern)) {
            if (passwordText.getText().toString().equals(confirmPasswordtext.getText().toString())) {
                progressBar.setVisibility(View.VISIBLE);
//               when click button data comunication is start so signup button must be deisable
                signUpbtn.setEnabled(false);
                signUpbtn.setTextColor(Color.argb(50, 255, 255, 255));

//               data send to firebase
                firebaseAuth.createUserWithEmailAndPassword(emailText.getText().toString(), passwordText.getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    //save user full name
                                    Map<String , Object> userdata = new HashMap<>();
                                    userdata.put("fullName", fullNameText.getText().toString());
                                    userdata.put("email", emailText.getText().toString());
                                    userdata.put("profileImg","");
                                    firebaseFirestore.collection("USERS").document(firebaseAuth.getUid())
                                            .set(userdata)
                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isSuccessful()) {
                                                        CollectionReference userDataRefarence=firebaseFirestore.collection("USERS").document(firebaseAuth.getUid()).collection("USER_DATA");

                                                        //Maps
                                                        Map<String, Object> WishListMap = new HashMap<>();
                                                        WishListMap.put("list_size", (long) 0);
                                                        //Maps
                                                        Map<String, Object> RatingsListMap = new HashMap<>();
                                                        RatingsListMap.put("list_size", (long) 0);
                                                        //Maps
                                                        Map<String, Object> CartListMap = new HashMap<>();
                                                        CartListMap.put("list_size", (long) 0);
                                                        //Maps
                                                        Map<String, Object> MyAddresstMap = new HashMap<>();
                                                        MyAddresstMap.put("list_size", (long) 0);


                                                        final List<String >documentName=new ArrayList<>();
                                                        documentName.add("MY_WISHLIST");
                                                        documentName.add("MY_RATING");
                                                        documentName.add("MY_CART");
                                                        documentName.add("MY_ADDRESS");

                                                        List< Map<String, Object>>documentFields=new ArrayList<>();
                                                        documentFields.add(WishListMap);
                                                        documentFields.add(RatingsListMap);
                                                        documentFields.add(CartListMap);
                                                        documentFields.add(MyAddresstMap);

                                                        for (int x=0;x<documentName.size();x++)
                                                        {
                                                            final int finalX = x;
                                                            userDataRefarence.document(documentName.get(x))
                                                                    .set(documentFields.get(x)).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                @Override
                                                                public void onComplete(@NonNull Task<Void> task) {
                                                                    if (task.isSuccessful()) {
                                                                        if (finalX ==documentName.size()-1)
                                                                        {
                                                                            mainIntent();
                                                                        }

                                                                    } else {
                                                                        progressBar.setVisibility(View.INVISIBLE);
                                                                        signUpbtn.setEnabled(true);
                                                                        signUpbtn.setTextColor(Color.rgb(255, 255, 255));
                                                                        Toast.makeText(getActivity(), task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                                                    }
                                                                }
                                                            });
                                                        }
                                                    } else {
                                                        Toast.makeText(getActivity(), task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                                    }
                                                }
                                            });
                                } else {
                                    Toast.makeText(getActivity(), task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                }
                            }
                        });

            } else {
                confirmPasswordtext.setError("your password doesn't matched", customeErrorIcon);
            }

        } else {
            emailText.setError("you email doesn't validated", customeErrorIcon);
        }

    }


    //    check validation for all input fileds
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void checkInputs() {
        if (!TextUtils.isEmpty(emailText.getText())) {
            if (!TextUtils.isEmpty(fullNameText.getText())) {
                if (!TextUtils.isEmpty(passwordText.getText()) && passwordText.length() >= 8) {
                    if (!TextUtils.isEmpty(confirmPasswordtext.getText())) {
                        signUpbtn.setEnabled(true);
                        signUpbtn.setTextColor(Color.rgb(255, 255, 255));
                    } else {

                        signUpbtn.setEnabled(false);
                        signUpbtn.setTextColor(Color.argb(50, 255, 255, 255));
                    }

                } else {
                    signUpbtn.setEnabled(false);
                    signUpbtn.setTextColor(Color.argb(50, 255, 255, 255));
                }

            } else {
                signUpbtn.setEnabled(false);
                signUpbtn.setTextColor(Color.argb(50, 255, 255, 255));
            }

        } else {
            signUpbtn.setEnabled(false);
            signUpbtn.setTextColor(Color.argb(50, 255, 255, 255));
        }
    }

    //    fragment set korar jonno function
    private void setFragment(Fragment fragment) {

        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.slide_from_left, R.anim.slideout_from_right);
        fragmentTransaction.replace(ParentframeLayout.getId(), fragment);
        fragmentTransaction.commit();
    }

    private void mainIntent() {

        if (disablecloseBtn) {
            disablecloseBtn = false;
        } else {
            Intent mainIntent = new Intent(getActivity(), MainActivity.class);
            startActivity(mainIntent);
        }
        getActivity().finish();
    }

}
