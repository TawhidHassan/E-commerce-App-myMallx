package com.example.mymallx;


import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.ImageViewTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.android.gms.auth.api.signin.internal.Storage;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;


/**
 * A simple {@link Fragment} subclass.
 */
public class UpdateInfoFragment extends Fragment {

    CircleImageView userProfileImg;
    Button changePhotBtn, removePhotoBtn, updatedataBtn;
    EditText nameEditeText, emailEditText;
    private String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+.[a-z]+";
    Dialog loadingDialog;
    Dialog passwordDialog;
    EditText dialogPass;
    Button dialogDoneBtn;
    String name;
    String photo;
    String email;
    Uri imageUri;
    private boolean updatePhoto=false;

    public UpdateInfoFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_update_info, container, false);
        userProfileImg = view.findViewById(R.id.userProfileImgId);
        changePhotBtn = view.findViewById(R.id.cghangePhotoBtnId);
        removePhotoBtn = view.findViewById(R.id.removwBtnId);
        updatedataBtn = view.findViewById(R.id.updateBtnId);
        nameEditeText = view.findViewById(R.id.userNameId);
        emailEditText = view.findViewById(R.id.userEmailId);

        name = getArguments().getString("Name");
        photo = getArguments().getString("Photo");
        email = getArguments().getString("Email");

        Glide.with(getContext()).load(photo).into(userProfileImg);
        nameEditeText.setText(name);
        emailEditText.setText(email);

        //image part start
        changePhotBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                 * for select image into gallery
                 */
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)//for version marsmellow
                {
                    if (getContext().checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                        Intent galleryIntent = new Intent(Intent.ACTION_PICK);
                        galleryIntent.setType("image/*");
                        startActivityForResult(galleryIntent, 1);
                    } else {
                        getActivity().requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 2);
                    }
                } else {
                    Intent galleryIntent = new Intent(Intent.ACTION_PICK);
                    galleryIntent.setType("image/*");
                    startActivityForResult(galleryIntent, 1);
                }
            }
        });

        removePhotoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageUri=null;
                updatePhoto=true;
                Glide.with(getContext()).load(R.mipmap.profile_placeholder).into(userProfileImg);
            }
        });
        //image part end

        emailEditText.addTextChangedListener(new TextWatcher() {
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
        nameEditeText.addTextChangedListener(new TextWatcher() {
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

        dialogPass = passwordDialog.findViewById(R.id.dialogPassId);
        dialogDoneBtn = passwordDialog.findViewById(R.id.dialogDoneBtnId);
        //end dialog
        updatedataBtn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                checkEmailAndePassword();
            }
        });


        return view;
    }


    //image part start
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 2) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent galleryIntent = new Intent(Intent.ACTION_PICK);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent, 1);
            } else {
                Toast.makeText(getContext(), "Permison denited", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == getActivity().RESULT_OK) {
                if (data != null) {
                    imageUri = data.getData();
                    updatePhoto=true;
                    Glide.with(getContext()).load(imageUri).into(userProfileImg);
                } else {
                    Toast.makeText(getContext(), "Iamge not found", Toast.LENGTH_LONG).show();
                }
            }
        }
    }
    //image part end

    //    check validation for all input fileds
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void checkInputs() {
        if (!TextUtils.isEmpty(emailEditText.getText())) {
            if (!TextUtils.isEmpty(nameEditeText.getText())) {
                updatedataBtn.setEnabled(true);
                updatedataBtn.setTextColor(Color.rgb(255, 255, 255));
            } else {
                updatedataBtn.setEnabled(false);
                updatedataBtn.setTextColor(Color.argb(50, 255, 255, 255));
            }
        } else {
            updatedataBtn.setEnabled(false);
            updatedataBtn.setTextColor(Color.argb(50, 255, 255, 255));
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void checkEmailAndePassword() {
        Drawable customeErrorIcon = getResources().getDrawable(R.mipmap.custom_error_icon);
        customeErrorIcon.setBounds(0, 0, customeErrorIcon.getIntrinsicWidth(), customeErrorIcon.getIntrinsicHeight());
        if (emailEditText.getText().toString().matches(emailPattern)) {
            final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            if (emailEditText.getText().toString().toLowerCase().trim().equals(email.toLowerCase().trim()))//donot update email thats because same email
            {
                loadingDialog.show();
                updatePhoto(user);
            } else//update email
            {
                passwordDialog.show();
                dialogDoneBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        loadingDialog.show();

                        String userPassword = dialogPass.getText().toString();

                        passwordDialog.dismiss();
                        AuthCredential credential = EmailAuthProvider.getCredential(email, userPassword);
                        user.reauthenticate(credential)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            user.updateEmail(emailEditText.getText().toString())
                                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {
                                                            if (task.isSuccessful()) {
                                                                updatePhoto(user);//coustome function
                                                            } else {
                                                                loadingDialog.dismiss();
                                                                String error = task.getException().getMessage();
                                                                Toast.makeText(getContext(), error, Toast.LENGTH_LONG).show();
                                                            }
                                                        }
                                                    });
                                        } else {
                                            loadingDialog.dismiss();
                                            String error = task.getException().getMessage();
                                            Toast.makeText(getContext(), error, Toast.LENGTH_LONG).show();
                                        }
                                    }
                                });
                    }
                });
            }


        } else {
            emailEditText.setError("you email doesn't validated", customeErrorIcon);
        }

    }

    private void updatePhoto(final FirebaseUser user)
    {
        //update user photo
        if (updatePhoto)
        {
            final StorageReference storageReference= FirebaseStorage.getInstance().getReference().child("profile/"+user.getUid()+".jpg");
            if (imageUri!=null)
            {
                Glide.with(getContext()).asBitmap().load(imageUri).circleCrop().into(new ImageViewTarget<Bitmap>(userProfileImg) {

                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        resource.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                        byte[] data = baos.toByteArray();

                        UploadTask uploadTask = storageReference.putBytes(data);
                        uploadTask.addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                                if (task.isSuccessful())
                                {

                                    //find the image download url
                                    storageReference.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Uri> task) {
                                            if (task.isSuccessful())
                                            {
                                                imageUri=task.getResult();
                                                String updatedPhotoUrl=task.getResult().toString();
                                                DBqueries.profile=updatedPhotoUrl;
                                                Glide.with(getActivity()).load(DBqueries.profile).into(userProfileImg);

                                                Map<String ,Object> updateData=new HashMap<>();
                                                updateData.put("email",emailEditText.getText().toString());
                                                updateData.put("fullName",nameEditeText.getText().toString());
                                                updateData.put("profile",DBqueries.profile);
                                                updatefileds(user,updateData);//coustome function
                                            }else
                                            {
                                                loadingDialog.dismiss();
                                                DBqueries.profile="";
                                                String error = task.getException().getMessage();
                                                Toast.makeText(getContext(), error, Toast.LENGTH_LONG).show();
                                            }
                                        }
                                    });
                                    //find the image download url

                                }else
                                {
                                    loadingDialog.dismiss();
                                    String error = task.getException().getMessage();
                                    Toast.makeText(getContext(), error, Toast.LENGTH_LONG).show();
                                }
                            }
                        });


                        return;
                    }

                    @Override
                    protected void setResource(@Nullable Bitmap resource) {
                        userProfileImg.setImageResource(R.mipmap.profile_placeholder);
                    }
                });

            }else {//remove /delete photo frome dtabase
                storageReference.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful())
                        {
                            DBqueries.profile="";

                            Map<String ,Object> updateData=new HashMap<>();
                            updateData.put("email",emailEditText.getText().toString());
                            updateData.put("fullName",nameEditeText.getText().toString());
                            updateData.put("profile","");
                            updatefileds(user,updateData);//coustome function
                        }else
                        {
                            String error = task.getException().getMessage();
                            Toast.makeText(getContext(), error, Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        }else
        {
            Map<String ,Object> updateData=new HashMap<>();
            updateData.put("fullName",nameEditeText.getText().toString());
            updatefileds(user,updateData);
        }
        //update user photo
    }

    private void updatefileds(FirebaseUser user, final Map<String ,Object> updateData)
    {
        FirebaseFirestore.getInstance().collection("USERS").document(user.getUid())
                .update(updateData).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful())
                {
                    if (updateData.size()>1)
                    {
                        DBqueries.fullName=nameEditeText.getText().toString().trim();
                        DBqueries.email=emailEditText.getText().toString().trim();
                    }else
                    {
                        DBqueries.fullName=nameEditeText.getText().toString().trim();

                    }
                    getActivity().finish();
                    Toast.makeText(getContext(), "Updated Successfully", Toast.LENGTH_LONG).show();
                }else
                {
                    String error = task.getException().getMessage();
                    Toast.makeText(getContext(), error, Toast.LENGTH_LONG).show();
                }
                loadingDialog.dismiss();
            }
        });
    }


}
