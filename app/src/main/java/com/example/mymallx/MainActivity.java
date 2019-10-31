package com.example.mymallx;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.mymallx.ui.home.HomeFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;


import static com.example.mymallx.RegesterActivity.setSignUpFragment;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private AppBarConfiguration mAppBarConfiguration;
    NavigationView navigationView;

    public static final int HOME_FRAGMENT = 0;
    public static final int CART_FRAGMENT = 1;
    public static final int MY_ORDER_FRAGMENT = 2;
    public static final int MY_WISH_LIST_FRAGMENT = 3;
    public static final int MY_REWARD_FRAGMENT = 4;
    public static final int MY_ACCOUNT_FRAGMENT = 5;
    public static Boolean show_cart = false;


    //for internet connection check
    ConnectivityManager connMgr;
    NetworkInfo networkInfo;

    private Window window;

    public static int currentFragment = -1;

    Fragment mainFragment;
    FrameLayout frameLayout;
    public static DrawerLayout drawer;
    NavController navController;
    Toolbar toolbar;

    ImageView myMallLogoActionBar;

    Dialog signindialog;

    FirebaseAuth firebaseAuth;
    FirebaseUser currentUser;

   ImageView profileImage;
   ImageView profileImageAdd;
   TextView userName,userEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //for change Reward Page Action Bar Color
        window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//for change Reward Page Action Bar Color

        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        frameLayout = findViewById(R.id.main_frameLayoutId);

        profileImage=navigationView.getHeaderView(0).findViewById(R.id.main_profile_id);
        userName=navigationView.getHeaderView(0).findViewById(R.id.main_user_name_id);
        userEmail=navigationView.getHeaderView(0).findViewById(R.id.main_user_email_id);
        profileImageAdd=navigationView.getHeaderView(0).findViewById(R.id.profileImageAddId);

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.

        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_my_mall)
                .setDrawerLayout(drawer)
                .build();

        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        navigationView.setNavigationItemSelectedListener(this);

        //check show cart activity
        if (show_cart == true) {
            setFragment(new MyCartFragment(), CART_FRAGMENT);
        } else {
            //to do latter when click card fragment
//                    setFragment(new HomeFragment(),HOME_FRAGMENT);
        }


        //signIn dilog module
        signindialog = new Dialog(MainActivity.this);
        signindialog.setContentView(R.layout.sign_in_dialog);
        signindialog.setCancelable(true);
        signindialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        Button dilogsignInBtn = signindialog.findViewById(R.id.dilogsignInBtnId);
        Button dilogsignUpBtn = signindialog.findViewById(R.id.dilogsignUpBtnId);

        final Intent regesterIntent = new Intent(MainActivity.this, RegesterActivity.class);

        dilogsignInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SigninFragment.disableCloseBtn = true;
                signindialog.dismiss();
                setSignUpFragment = false;
                startActivity(regesterIntent);
            }
        });
        dilogsignUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SignUpFragment.disablecloseBtn = true;
                signindialog.dismiss();
                setSignUpFragment = true;
                startActivity(regesterIntent);
            }
        });


    }

    //on start activity

    @Override
    protected void onStart() {
        super.onStart();
        currentUser = firebaseAuth.getInstance().getCurrentUser();
        //check user signin then drawer btn signOut btn enable or disable
        if (currentUser == null) {
            navigationView.getMenu().getItem(6).setEnabled(false);
        } else {
            FirebaseFirestore.getInstance().collection("USERS").document(FirebaseAuth.getInstance().getUid())
                    .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                            DBqueries.fullName=task.getResult().getString("fullName");
                            DBqueries.email=task.getResult().getString("email");
                            DBqueries.profile=task.getResult().getString("profile");

                            userName.setText(DBqueries.fullName);
                            userEmail.setText(DBqueries.email);
                            if (DBqueries.profile.equals(""))
                            {
                                profileImageAdd.setVisibility(View.VISIBLE);
                            }else {
                                profileImageAdd.setVisibility(View.INVISIBLE);
                                Glide.with(MainActivity.this).load(DBqueries.profile).apply(new RequestOptions().placeholder(R.mipmap.profile_placeholder)).into(profileImage);
                            }
                    } else {
                        Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_LONG).show();

                    }
                }
            });
            navigationView.getMenu().getItem(6).setEnabled(true);
        }
        invalidateOptionsMenu();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        if (currentFragment == HOME_FRAGMENT) {
            getMenuInflater().inflate(R.menu.main, menu);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            //for actionBar logo when back into the home
            myMallLogoActionBar = findViewById(R.id.myMallLogoID);
            myMallLogoActionBar.setVisibility(View.VISIBLE);


            //cart notification
            //cart icon ar upora badge number show
//            MenuItem cartItem=menu.findItem(R.id.maincartId);
//            if (DBqueries.cartList.size()>0)
//           {
//               cartItem.setActionView(R.layout.badge_layout);
//               ImageView badgeIcon=cartItem.getActionView().findViewById(R.id.badgeIcon);
//               badgeIcon.setImageResource(R.mipmap.cart_white);
//               TextView badgeCount=cartItem.getActionView().findViewById(R.id.badgeCountId);
//               badgeCount.setText(String.valueOf(DBqueries.cartList.size()));
//
//               cartItem.getActionView().setOnClickListener(new View.OnClickListener() {
//                   @Override
//                   public void onClick(View v) {
//                       if (currentUser == null) {
//                           signindialog.show();
//                       } else {
//                           myCart();
//                       }
//                   }
//               });
//           }else
//           {
//            cartItem.setActionView(null);
//           }
            return true;

        } else if (currentFragment == CART_FRAGMENT) {
            //for actionBar logo when back into the home
            myMallLogoActionBar = findViewById(R.id.myMallLogoID);
            myMallLogoActionBar.setVisibility(View.GONE);
            getSupportActionBar().setDisplayShowTitleEnabled(true);
            getSupportActionBar().setTitle("My Cart");


        } else if (currentFragment == MY_ORDER_FRAGMENT) {
            //for actionBar logo when back into the home
            myMallLogoActionBar = findViewById(R.id.myMallLogoID);
            myMallLogoActionBar.setVisibility(View.GONE);
            getSupportActionBar().setDisplayShowTitleEnabled(true);
            getSupportActionBar().setTitle("My Order");


        } else if (currentFragment == MY_WISH_LIST_FRAGMENT) {
            //for actionBar logo when back into the home
            myMallLogoActionBar = findViewById(R.id.myMallLogoID);
            myMallLogoActionBar.setVisibility(View.GONE);
            getSupportActionBar().setDisplayShowTitleEnabled(true);
            getSupportActionBar().setTitle("My Wish List");


        } else if (currentFragment == MY_REWARD_FRAGMENT) {
            //for actionBar logo when back into the home
            myMallLogoActionBar = findViewById(R.id.myMallLogoID);
            myMallLogoActionBar.setVisibility(View.GONE);
            getSupportActionBar().setDisplayShowTitleEnabled(true);
            getSupportActionBar().setTitle("My Rewards");

        } else if (currentFragment == MY_ACCOUNT_FRAGMENT) {
            //for actionBar logo when back into the home
            myMallLogoActionBar = findViewById(R.id.myMallLogoID);
            myMallLogoActionBar.setVisibility(View.GONE);
            getSupportActionBar().setDisplayShowTitleEnabled(true);
            getSupportActionBar().setTitle("My Account");

        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.mainsearch_icon_id) {
            Intent searchIntent=new Intent(getApplicationContext(),SearchActivity.class);
            startActivity(searchIntent);
            return true;
        } else if (id == R.id.mainNotificationId) {
            return true;
        } else if (id == R.id.maincartId) {

            if (currentUser == null) {
                signindialog.show();
            } else {
                myCart();
            }
            return true;
        } else if (id == android.R.id.home) {

        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id = menuItem.getItemId();
        if (id == R.id.nav_my_mall) {
            invalidateOptionsMenu();
            setFragment(new HomeFragment(), HOME_FRAGMENT);
        } else if (currentUser != null) {
            if (id == R.id.nav_my_cart) {
                setFragment(new MyCartFragment(), CART_FRAGMENT);
            } else if (id == R.id.nav_my_rewards) {
                setFragment(new MyRewardFragment(), MY_REWARD_FRAGMENT);
            } else if (id == R.id.nav_my_order) {
                setFragment(new MyOrderFragment(), MY_ORDER_FRAGMENT);

            } else if (id == R.id.nav_my_wishlist) {
                setFragment(new MyWishListFragment(), MY_WISH_LIST_FRAGMENT);
            } else if (id == R.id.nav_my_account) {
                setFragment(new MyAccountFragment(), MY_ACCOUNT_FRAGMENT);
            } else if (id == R.id.nav_my_account_logout) {
                firebaseAuth.getInstance().signOut();
                DBqueries.clearData();
                Intent regesterIntent = new Intent(MainActivity.this, RegesterActivity.class);
                startActivity(regesterIntent);

                finish();
            }

        } else {
            drawer.closeDrawer(GravityCompat.START);
            signindialog.show();
            return false;
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    //My cart function
    private void myCart() {

        //remove action bar icon ->invalidateOptionsMenu()//and call onCreateOptionsMenu methode
        invalidateOptionsMenu();
        setFragment(new MyCartFragment(), CART_FRAGMENT);
    }

    //for set fragment in frame layout
    private void setFragment(Fragment fragment, int fragmentNo) {
        if (fragmentNo != currentFragment) {
            if (fragmentNo == MY_REWARD_FRAGMENT) {
                //for change Reward Page Action Bar Color
                window.setStatusBarColor(Color.parseColor("#5B04B1"));
                toolbar.setBackgroundColor(Color.parseColor("#5B04B1"));
            } else {
                window.setStatusBarColor(getResources().getColor(R.color.colorPrimary));
                toolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            }
            currentFragment = fragmentNo;
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
            fragmentTransaction.replace(frameLayout.getId(), fragment);
            fragmentTransaction.commit();
        }

    }


    //mobile deful;t backPress  btn function
    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (currentFragment == HOME_FRAGMENT) {
                currentFragment = -1;
                super.onBackPressed();
            } else {

                setFragment(new HomeFragment(), HOME_FRAGMENT);
                navigationView.getMenu().getItem(0).setCheckable(true);


            }

        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }


}
