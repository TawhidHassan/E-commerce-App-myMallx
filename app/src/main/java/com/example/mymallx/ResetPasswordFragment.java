package com.example.mymallx;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.transition.TransitionManager;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;


public class ResetPasswordFragment extends Fragment {

    FirebaseAuth firebaseAuth;
    public ResetPasswordFragment() {
        // Required empty public constructor

    }

    EditText resterEmail;
    Button resetPsBtn;
    TextView goback,forgetPsInbox;
    ImageView inboxIcon;
    ProgressBar progressBar;
    FrameLayout PrentframeLayout;
    ViewGroup emailIconStatasContainer;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_reset_password, container, false);
        resterEmail=view.findViewById(R.id.forgetPasswordEmailId);
        resetPsBtn=view.findViewById(R.id.forgetbuttonId);
        goback=view.findViewById(R.id.forgetGoBackId);
        forgetPsInbox=view.findViewById(R.id.forgetPsInboxId);
        inboxIcon=view.findViewById(R.id.forgpsinboxImgId);
        progressBar=view.findViewById(R.id.forgpsStatsprogressBarId);

        emailIconStatasContainer=view.findViewById(R.id.forgetpslinearLayoutId);

        //        fragment ar bahira frameLayout ar jonno aivaba getActivity() diya find kira laga
        PrentframeLayout=getActivity().findViewById(R.id.RegesterFrameLayoutId);

        firebaseAuth=FirebaseAuth.getInstance();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //email check for btn enable
        resterEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                checkInputs();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        //reset email send btn
        resetPsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                when click btn inbox icon visiable and animation
                TransitionManager.beginDelayedTransition(emailIconStatasContainer);
                inboxIcon.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.VISIBLE);

                //btn will be disable
                resetPsBtn.setEnabled(false);
                resetPsBtn.setTextColor(Color.argb(50,255,255,255));


                firebaseAuth.sendPasswordResetEmail(resterEmail.getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful())
                                {
                                    ///ANIMATION CODE STARTS HERE

                                    ScaleAnimation scaleAnimation = new ScaleAnimation(1,0,1,0,inboxIcon.getWidth()/2,inboxIcon.getHeight()/2);
                                    scaleAnimation.setDuration(100);
                                    scaleAnimation.setInterpolator(new AccelerateInterpolator());
                                    scaleAnimation.setRepeatMode(Animation.REVERSE);
                                    scaleAnimation.setRepeatCount(1);

                                    scaleAnimation.setAnimationListener(new Animation.AnimationListener(){
                                        @Override
                                        public void onAnimationStart(Animation animation) {

                                        }

                                        @Override
                                        public void onAnimationEnd(Animation animation) {
                                            forgetPsInbox.setText("Recovery email sent successfully ! check your inbox");
                                            forgetPsInbox.setTextColor(getResources().getColor(R.color.green));
                                            forgetPsInbox.setVisibility(View.VISIBLE);
                                            TransitionManager.beginDelayedTransition(emailIconStatasContainer);
                                            inboxIcon.setVisibility(View.VISIBLE);
                                        }

                                        @Override
                                        public void onAnimationRepeat(Animation animation) {
                                            inboxIcon.setImageResource(R.mipmap.ic_mail_outline_green_24px);
                                        }

                                    });

                                    inboxIcon.startAnimation(scaleAnimation);
                                    resterEmail.setText("");
                                    resetPsBtn.setEnabled(false);
                                    resetPsBtn.setTextColor(Color.argb(50,255,255,255));

                                }else {

//                                  if show error then it will show notification and show error type with notification
                                    String error=task.getException().getMessage();
                                    forgetPsInbox.setText(error);
                                    forgetPsInbox.setTextColor(getResources().getColor(R.color.btnRed));
                                    TransitionManager.beginDelayedTransition(emailIconStatasContainer);
                                    forgetPsInbox.setVisibility(View.VISIBLE);
                                    resetPsBtn.setEnabled(true);
                                    resetPsBtn.setTextColor(Color.rgb(255,255,255));


                                }
                                progressBar.setVisibility(View.GONE);

                            }
                        });
            }
        });

        //goback btn
        goback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                fregment set function
                setFragment(new SigninFragment());
            }
        });

    }


    //chech inputs for email to enable btn
    private void checkInputs() {
        if (TextUtils.isEmpty(resterEmail.getText()))
        {
            resetPsBtn.setEnabled(false);
            resetPsBtn.setTextColor(Color.argb(50,255,255,255));
        }else {
            resetPsBtn.setEnabled(true);
            resetPsBtn.setTextColor(Color.rgb(255,255,255));
        }
    }

    //    fragment set korar jonno function
    private void setFragment(Fragment fragment) {

        FragmentTransaction fragmentTransaction=getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.slide_from_left,R.anim.slideout_from_right);
        fragmentTransaction.replace(PrentframeLayout.getId(),fragment);
        fragmentTransaction.commit();
    }
}
