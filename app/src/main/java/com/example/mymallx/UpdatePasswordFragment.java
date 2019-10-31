package com.example.mymallx;


import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class UpdatePasswordFragment extends Fragment {

    EditText oldPassword,newPassword,confirmPassword;
    Button updatepassBtn;
    Dialog loadingDialog;
    Dialog passwordDialog;
    EditText dialogPass;
    Button dialogDoneBtn;
    String email;
    public UpdatePasswordFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_update_password, container, false);
        oldPassword=view.findViewById(R.id.oldPasId);
        newPassword=view.findViewById(R.id.newPasId);
        confirmPassword=view.findViewById(R.id.confirmPasId);
        updatepassBtn=view.findViewById(R.id.updatePassBtnId);

        //loading dialog
        loadingDialog = new Dialog(getContext());
        loadingDialog.setContentView(R.layout.loading_progress_dilog);
        loadingDialog.setCancelable(false);
        loadingDialog.getWindow().setBackgroundDrawable(getResources().getDrawable(R.drawable.slider_background));
        loadingDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        //end dialog/
        // /passwordDialog dialog
        passwordDialog = new Dialog(getContext());
        passwordDialog.setContentView(R.layout.password_confirm_dialog);
        passwordDialog.setCancelable(true);
        passwordDialog.getWindow().setBackgroundDrawable(getResources().getDrawable(R.drawable.slider_background));
        passwordDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        dialogPass = passwordDialog.findViewById(R.id.dialogPassId);
        dialogDoneBtn = passwordDialog.findViewById(R.id.dialogDoneBtnId);
        //end dialog

        //check validation for enable signup button
        oldPassword.addTextChangedListener(new TextWatcher() {
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

        newPassword.addTextChangedListener(new TextWatcher() {
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

        confirmPassword.addTextChangedListener(new TextWatcher() {
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

        //loading dialog
        loadingDialog = new Dialog(getContext());
        loadingDialog.setContentView(R.layout.loading_progress_dilog);
        loadingDialog.setCancelable(false);
        loadingDialog.getWindow().setBackgroundDrawable(getResources().getDrawable(R.drawable.slider_background));
        loadingDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        //end dialog/
        // /passwordDialog dialog
        passwordDialog = new Dialog(getContext());
        passwordDialog.setContentView(R.layout.password_confirm_dialog);
        passwordDialog.setCancelable(true);
        passwordDialog.getWindow().setBackgroundDrawable(getResources().getDrawable(R.drawable.slider_background));
        passwordDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        dialogPass=passwordDialog.findViewById(R.id.dialogPassId);
        dialogDoneBtn=passwordDialog.findViewById(R.id.dialogDoneBtnId);
        //end dialog
                                             email =FirebaseAuth.getInstance().getCurrentUser().getEmail();

        updatepassBtn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                checkEmailAndePassword();
            }
        });
        return view;
    }

    //    check validation for all input fileds
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void checkInputs() {
        if (!TextUtils.isEmpty(oldPassword.getText())) {
            if (!TextUtils.isEmpty(newPassword.getText()) && newPassword.length() >= 8) {
                if (!TextUtils.isEmpty(confirmPassword.getText()) && confirmPassword.length() >= 8) {
                        updatepassBtn.setEnabled(true);
                    updatepassBtn.setTextColor(Color.rgb(255, 255, 255));
                } else {
                    updatepassBtn.setEnabled(false);
                    updatepassBtn.setTextColor(Color.argb(50, 255, 255, 255));
                }

            } else {
                updatepassBtn.setEnabled(false);
                updatepassBtn.setTextColor(Color.argb(50, 255, 255, 255));
            }

        } else {
            updatepassBtn.setEnabled(false);
            updatepassBtn.setTextColor(Color.argb(50, 255, 255, 255));
        }
    }

    //    when signUp button click check validatyed email adress and password  and confirmPassword check
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void checkEmailAndePassword() {
        Drawable customeErrorIcon = getResources().getDrawable(R.mipmap.custom_error_icon);
        customeErrorIcon.setBounds(0, 0, customeErrorIcon.getIntrinsicWidth(), customeErrorIcon.getIntrinsicHeight());
            if (newPassword.getText().toString().equals(confirmPassword.getText().toString())) {
                loadingDialog.show();
                final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                AuthCredential credential = EmailAuthProvider.getCredential(email, oldPassword.getText().toString());
                user.reauthenticate(credential)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    user.updatePassword(newPassword.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful())
                                            {
                                               oldPassword.setText("");
                                               newPassword.setText("");
                                               confirmPassword.setText("");
                                               getActivity().finish();
                                                Toast.makeText(getContext(), "Password updated successfully", Toast.LENGTH_LONG).show();

                                            }else
                                            {
                                                String error = task.getException().getMessage();
                                                Toast.makeText(getContext(), error, Toast.LENGTH_LONG).show();
                                            }
                                            loadingDialog.dismiss();
                                        }
                                    });
                                } else {
                                    loadingDialog.dismiss();
                                    String error = task.getException().getMessage();
                                    Toast.makeText(getContext(), error, Toast.LENGTH_LONG).show();
                                }
                            }
                        });

            } else {
                confirmPassword.setError("your password doesn't matched", customeErrorIcon);
            }
    }
}
