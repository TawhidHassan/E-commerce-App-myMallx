package com.example.mymallx;

import android.app.Dialog;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import static com.example.mymallx.MainActivity.show_cart;
import static com.example.mymallx.RegesterActivity.setSignUpFragment;

public class ProductDetailsActivity extends AppCompatActivity {

    public static boolean running_cart_query = false;
    //product name,imag,price
    ViewPager productImagesViewPager;
    TabLayout viewPagerIndecator;
    public static FloatingActionButton addToWishListBtn;
    TextView producttitle;
    TextView productDetailsproductRatingMiniView;
    TextView totalRatingMiniView;
    TextView productPrice;
    TextView productCutedPrice;
    TextView codIndecatortextView;
    ImageView codIndeCatorImg;
    Button addtoCartBtn;

    //reward coupen
    TextView rewardTitle;
    TextView rewardBody;

    Button cupenReemptionBtn;

    Dialog signindialog;
    Dialog loadingDialog;


    public static int initialrating;

    //deatils part
    ViewPager productDescriptionViewPager;
    TabLayout productDescriptionTabLayou;
    //set value for other details and description
    private String productDescriptionData;
    private String productOtherDescriptionData;

    private List<ProductSpecificationModel> productSpecificationModelList = new ArrayList<>();


    //full description part
    ConstraintLayout product_detail_tabs_container;
    ConstraintLayout productOnlyDescriptionLayoutContainer;
    TextView productOnlyDetailsBody;


    ///rating layout by star
    public static LinearLayout rateNowLayout;
    TextView totalrating;
    LinearLayout ratingsNumberContainer;
    TextView textViewYourRating;
    LinearLayout ratingProgressBarStats;
    TextView totalContinerRating;
    TextView average_rating;

    public static boolean running_rating_query = false;

    Button buyNowBtn;

    public static String pId;

    //firebase
    private FirebaseFirestore firebaseFirestore;

    //for dialog
    private static RecyclerView coupenDialogContainer;
    private static LinearLayout coupenDialogSelected;
    TextView oldPrice, descountPrice;

    //for coupen dialog
    public static TextView coupenTitle;
    public static TextView coupenBody;
    public static TextView coupenExpireDate;
    //for coupen dialog

    public static Boolean AlreadyaddTpWishList = false;
    public static Boolean AlreadyaddTpCartList = false;

    public static boolean fromesearch=false;


    FirebaseAuth firebaseAuth;
    FirebaseUser currentUser;
    DocumentSnapshot documentSnapshot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        //back arrow button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //fireBase
        firebaseFirestore = FirebaseFirestore.getInstance();


        //product image,name,price start
        productImagesViewPager = findViewById(R.id.productImagesViewPagerId);
        viewPagerIndecator = findViewById(R.id.viewPagerIndecatorId);
        addToWishListBtn = findViewById(R.id.AdToWishLisrtfloatingActionButtonId);
        producttitle = findViewById(R.id.producttitleId);
        productDetailsproductRatingMiniView = findViewById(R.id.productDetailsproductRatingMiniViewId);
        totalRatingMiniView = findViewById(R.id.totalRatingMiniViewId);
        productPrice = findViewById(R.id.productPriceId);
        productCutedPrice = findViewById(R.id.productCutedPriceId);
        codIndecatortextView = findViewById(R.id.codIndecatortextViewId);
        codIndeCatorImg = findViewById(R.id.codIndeCatorImgId);
        addtoCartBtn = findViewById(R.id.addtoCartBtnId);

        //description part
        product_detail_tabs_container = findViewById(R.id.product_detail_tabs_container_id);
        productOnlyDescriptionLayoutContainer = findViewById(R.id.productOnlyDescriptionLayoutId);

        //only details
        productOnlyDetailsBody = findViewById(R.id.productOnlyDetailsBodyId);

        //reward views
        rewardTitle = findViewById(R.id.rewardTitleId);
        rewardBody = findViewById(R.id.rewardBodyId);

        //rating layout
        totalrating = findViewById(R.id.totalratingId);
        ratingsNumberContainer = findViewById(R.id.ratingsNumberContainerId);
        textViewYourRating = findViewById(R.id.textViewYourRatingId);
        ratingProgressBarStats = findViewById(R.id.ratingProgressBarStatsId);
        totalContinerRating = findViewById(R.id.totalContinerRatingId);
        average_rating = findViewById(R.id.average_ratingId);
//========================================================================================
        cupenReemptionBtn = findViewById(R.id.cupenReemptionBtnId);
        //coupen dialog
        final Dialog coupenRedeemDialog = new Dialog(ProductDetailsActivity.this);
        coupenRedeemDialog.setContentView(R.layout.coupen_redeem_dialog);
        coupenRedeemDialog.setCancelable(true);
        coupenRedeemDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);


        coupenDialogContainer = coupenRedeemDialog.findViewById(R.id.coupenDialogContainerId);
        coupenDialogSelected = coupenRedeemDialog.findViewById(R.id.coupenDialogSelectedId);

        coupenTitle = coupenRedeemDialog.findViewById(R.id.reward_coupen_title_id);
        coupenBody = coupenRedeemDialog.findViewById(R.id.cuopen_details_id);
        coupenExpireDate = coupenRedeemDialog.findViewById(R.id.rewars_expir_date_id);


        oldPrice = coupenRedeemDialog.findViewById(R.id.coupenDialogOldPriceId);
        descountPrice = coupenRedeemDialog.findViewById(R.id.coupenDialogDiscountPriceId);
        ImageView moreCoupen = coupenRedeemDialog.findViewById(R.id.cupoenDialogBtnIdn);


        initialrating = -1;
        //loading dialog

        loadingDialog = new Dialog(ProductDetailsActivity.this);
        loadingDialog.setContentView(R.layout.loading_progress_dilog);
        loadingDialog.setCancelable(false);
        loadingDialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.slider_background));
        loadingDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        loadingDialog.show();


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        coupenDialogContainer.setLayoutManager(linearLayoutManager);

        List<RewardModel> rewardModelList = new ArrayList<>();
        rewardModelList.add(new RewardModel("Eid Offer", "till 3rd, August 2018", "GET 20% OFF on any product above Rs.500/- and below Rs.2500/-"));
        rewardModelList.add(new RewardModel("New Year Offer", "till 3rd, August 2019", "GET 20% OFF on any 3 product above Rs.400/- and below Rs.2200/-"));
        rewardModelList.add(new RewardModel("New Year Offer", "till 3rd, August 2019", "GET 20% OFF on any 3 product above Rs.400/- and below Rs.2200/-"));
        rewardModelList.add(new RewardModel("New Year Offer", "till 3rd, August 2019", "GET 20% OFF on any 3 product above Rs.400/- and below Rs.2200/-"));

        MyRewardAdapter myRewardAdapter = new MyRewardAdapter(rewardModelList, true);
        coupenDialogContainer.setAdapter(myRewardAdapter);
        myRewardAdapter.notifyDataSetChanged();

        moreCoupen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                morecoupenShow();
            }
        });

        cupenReemptionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentUser == null) {
                    signindialog.show();
                } else {
                    coupenRedeemDialog.show();
                }


            }

        });
//coupen dialog
//=================================================================================== ==========
        //product image,name,price end

        //product description with tab and viewPager start
        productDescriptionViewPager = findViewById(R.id.productDetailsViewPagerId);
        productDescriptionTabLayou = findViewById(R.id.productDescriptionTabLayoutId);


        buyNowBtn = findViewById(R.id.by_now_btn_id);
//product description with tab and viewPager end


        //product image,name,price start
        final List<String> productImagesList = new ArrayList<>();
        pId = getIntent().getStringExtra("ptoductId");
        firebaseFirestore.collection("PRODUCTS").document(pId).get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            documentSnapshot = task.getResult();

                            //for productImg
                            for (long x = 1; x < (long) documentSnapshot.get("no_of_product_img") + 1; x++) {
                                productImagesList.add((String) documentSnapshot.get("product_img_" + x));
                            }
                            //for productImg

                            //product name
                            producttitle.setText(documentSnapshot.get("product_title").toString());
                            //product averg rating
                            productDetailsproductRatingMiniView.setText(documentSnapshot.get("average_rating").toString());
                            //total rating
                            totalRatingMiniView.setText("(" + (String) documentSnapshot.get("total_rating") + ")Ratings");
                            //price
                            productPrice.setText(documentSnapshot.get("product_price").toString());
                            //cutted price
                            productCutedPrice.setText(documentSnapshot.get("cutted_price").toString());
                            //COD
                            if ((boolean) documentSnapshot.get("COD")) {
                                codIndecatortextView.setVisibility(View.VISIBLE);
                                codIndeCatorImg.setVisibility(View.VISIBLE);
                            } else {
                                codIndecatortextView.setVisibility(View.INVISIBLE);
                                codIndeCatorImg.setVisibility(View.INVISIBLE);
                            }
                            //reward
                            rewardTitle.setText((long) documentSnapshot.get("free_coupen") + (String) documentSnapshot.get("free_coupen_title"));
                            rewardBody.setText(documentSnapshot.get("free_coupen_body").toString());
                            //use tab layout for product details
                            if ((boolean) documentSnapshot.get("use_tab_layout")) {
                                product_detail_tabs_container.setVisibility(View.VISIBLE);
                                productOnlyDescriptionLayoutContainer.setVisibility(View.GONE);

                                productDescriptionData = documentSnapshot.get("product_description").toString();
                                //product other details
                                productOtherDescriptionData = documentSnapshot.get("product_other_details").toString();

                                //product description sepcefications
//                                ProductSpecificationFragment.productSpecificationModelList=new ArrayList<>();
                                for (long x = 1; x < (long) documentSnapshot.get("total_spec_title") + 1; x++) {
                                    productSpecificationModelList.add(new ProductSpecificationModel(0, (String) documentSnapshot.get("spec_title_" + x)));
                                    for (long y = 1; y < (long) documentSnapshot.get("spec_title_" + x + "_total_fields") + 1; y++) {
                                        productSpecificationModelList.add(new ProductSpecificationModel(1, (String) documentSnapshot.get("spec_title_" + x + "_field_" + y + "_name"), (String) documentSnapshot.get("spec_title_" + x + "_field_" + y + "_value")));

                                    }
                                }
                                //product description sepcefications
                            } else {
                                product_detail_tabs_container.setVisibility(View.GONE);
                                productOnlyDescriptionLayoutContainer.setVisibility(View.VISIBLE);

                                productOnlyDetailsBody.setText(documentSnapshot.get("product_description").toString());
                            }

//                            rating data
                            totalrating.setText((String) documentSnapshot.get("total_rating") + "Ratings");
                            //set rating indevisuly
                            for (int x = 0; x < 5; x++) {
                                TextView rating = (TextView) ratingsNumberContainer.getChildAt(x);
                                rating.setText(String.valueOf((long) documentSnapshot.get((5 - x) + "_star")));
                            }

                            //rating progress bar
                            for (int x = 0; x < 5; x++) {
                                ProgressBar progressBar = (ProgressBar) ratingProgressBarStats.getChildAt(x);
                                int maxProgress = Integer.parseInt(String.valueOf((String) documentSnapshot.get("total_rating")));
                                progressBar.setMax(maxProgress);
                                progressBar.setProgress(Integer.parseInt(String.valueOf((long) documentSnapshot.get((5 - x) + "_star"))));

                            }
                            //total rating
                            totalContinerRating.setText(String.valueOf((String) documentSnapshot.get("total_rating")));
                            average_rating.setText(documentSnapshot.get("average_rating").toString());
                            productDescriptionViewPager.setAdapter(new productDescriptionAdapter(getSupportFragmentManager(), productDescriptionTabLayou.getTabCount(), productDescriptionData, productOtherDescriptionData, productSpecificationModelList));

                            ProductsImagesAdapter productsImagesAdapter = new ProductsImagesAdapter(productImagesList);
                            productImagesViewPager.setAdapter(productsImagesAdapter);

                            //check user add this product wishList
                            if (currentUser != null) {
                                //rating load
                                if (DBqueries.myRating.size() == 0) {
                                    DBqueries.loadratingList(ProductDetailsActivity.this);
                                }

                                if (DBqueries.wishList.size() == 0) {
                                    DBqueries.loadWishList(ProductDetailsActivity.this, loadingDialog, false);
                                } else {
                                    loadingDialog.dismiss();
                                }

                                if (DBqueries.cartList.size() == 0) {
                                    DBqueries.loadCartList(ProductDetailsActivity.this, loadingDialog, false);
                                } else {
                                    loadingDialog.dismiss();
                                }

                            } else {
                                loadingDialog.dismiss();
                            }
                            //start selected
                            if (DBqueries.myRatedIds.contains(pId)) {
                                int indexx = DBqueries.myRatedIds.indexOf(pId);
                                initialrating = Integer.parseInt(String.valueOf(DBqueries.myRating.get(indexx))) - 1;
                                setRating(initialrating);
                            }
                            if (DBqueries.wishList.contains(pId)) {
                                AlreadyaddTpWishList = true;
                                addToWishListBtn.setSupportImageTintList(getResources().getColorStateList(R.color.colorPrimary));

                            } else {
                                AlreadyaddTpWishList = false;
                            }

                            if (DBqueries.cartList.contains(pId)) {
                                AlreadyaddTpCartList = true;
                            } else {
                                AlreadyaddTpCartList = false;
                            }

                            if((boolean)documentSnapshot.get("in_stock"))
                            {
                                buyNowBtn.setVisibility(View.VISIBLE);
                                addtoCartBtn.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        if (currentUser == null) {
                                            signindialog.show();
                                        } else {
                                            if (!running_cart_query) {
                                                running_cart_query = true;
                                                if (AlreadyaddTpCartList) {
                                                    running_cart_query = false;
                                                    Toast.makeText(ProductDetailsActivity.this, "Already add to cart", Toast.LENGTH_LONG).show();
                                                } else {
                                                    Map<String, Object> addProduct = new HashMap<>();
                                                    addProduct.put("product_id" + String.valueOf(DBqueries.cartList.size()), pId);
                                                    addProduct.put("list_size", (long) (DBqueries.cartList.size() + 1));

                                                    firebaseFirestore.collection("USERS").document(currentUser.getUid()).collection("USER_DATA").document("MY_CART")
                                                            .update(addProduct).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {

                                                            if (task.isSuccessful()) {
                                                                if (DBqueries.cartItemModelList.size() != 0) {
                                                                    DBqueries.cartItemModelList.add(new CartItemModel(CartItemModel.CART_ITEM, pId,
                                                                            (String) documentSnapshot.get("product_title"),
                                                                            (String) documentSnapshot.get("product_price"),
                                                                            (String) documentSnapshot.get("cutted_price"),
                                                                            (String) documentSnapshot.get("product_img_" + 1),
                                                                            (long) documentSnapshot.get("free_coupen"),
                                                                            (long) 1,
                                                                            (long) 0,
                                                                            (long) 0,
                                                                            (boolean)documentSnapshot.get("in_stock")));
                                                                }
                                                                AlreadyaddTpCartList = true;
                                                                DBqueries.cartList.add(pId);
                                                                Toast.makeText(ProductDetailsActivity.this, "Added to cart done", Toast.LENGTH_LONG).show();
                                                                invalidateOptionsMenu();//for when quera run seccesfully the option menu is refrsed
                                                                running_cart_query = false;
                                                            } else {
                                                                running_cart_query = false;
                                                                String error = task.getException().getMessage();
                                                                Toast.makeText(getApplicationContext(), error, Toast.LENGTH_SHORT).show();
                                                            }
                                                        }
                                                    });

                                                }
                                            }
                                        }


                                    }
                                });
                            }else
                            {
                                buyNowBtn.setVisibility(View.GONE);
                               addtoCartBtn.setText("OUT OF STOCK");
                               addtoCartBtn.setTextColor(getResources().getColor(R.color.colorPrimary));
                            }

                        } else {
                            loadingDialog.dismiss();
                            String error = task.getException().getMessage();
                            Toast.makeText(getApplicationContext(), error, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        viewPagerIndecator.setupWithViewPager(productImagesViewPager, true);
//product image,name,price end

        //floating wish list btn
        addToWishListBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (currentUser == null) {
                    signindialog.show();
                } else {
                    addToWishListBtn.setEnabled(false);//for when user click one time wishlist is updated and btn are disable and when it was added the it was again enabke
                    if (AlreadyaddTpWishList) {
                        int index = DBqueries.wishList.indexOf(pId);
                        DBqueries.removeFromeWisgList(index, ProductDetailsActivity.this);
                        addToWishListBtn.setSupportImageTintList(ColorStateList.valueOf(Color.parseColor("#9e9e9e")));

                    } else {

                        Map<String, Object> add_product_Id = new HashMap<>();
                        add_product_Id.put("product_id_" + String.valueOf(DBqueries.wishList.size()), pId);
                        add_product_Id.put("list_size", (long) DBqueries.wishList.size() + 1);

                        firebaseFirestore.collection("USERS").document(currentUser.getUid()).collection("USER_DATA").document("MY_WISHLIST")
                                .update(add_product_Id).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    if (DBqueries.wishListModelList.size() != 0) {
                                        DBqueries.wishListModelList.add(new WishListModel(pId, (String) documentSnapshot.get("product_img_1"),
                                                (String) documentSnapshot.get("product_title"),
                                                (long) documentSnapshot.get("free_coupens"),
                                                (String) documentSnapshot.get("average_rating"),
                                                (String) documentSnapshot.get("total_rating"),
                                                (String) documentSnapshot.get("product_price"),
                                                (String) documentSnapshot.get("cutted_price"),
                                                (Boolean) documentSnapshot.get("COD")));

                                    }

                                    AlreadyaddTpWishList = true;
                                    addToWishListBtn.setSupportImageTintList(getResources().getColorStateList(R.color.colorPrimary));
                                    DBqueries.wishList.add(pId);
                                    Toast.makeText(getApplicationContext(), "Product is added wish SucsessFully", Toast.LENGTH_LONG).show();

                                    addToWishListBtn.setEnabled(true);//for when user click one time wishlist is updated and btn are disable and when it was added the it was again enabke
                                } else {
                                    addToWishListBtn.setSupportImageTintList(ColorStateList.valueOf(Color.parseColor("#9e9e9e")));
                                    addToWishListBtn.setEnabled(true);//for when user click one time wishlist is updated and btn are disable and when it was added the it was again enabke
                                    String error = task.getException().getMessage();
                                    Toast.makeText(getApplicationContext(), error, Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

                    }
                }

            }
        });

        //product description with tab and viewPager start
//        productDescriptionViewPager.setAdapter(new productDescriptionAdapter(getSupportFragmentManager(),productDescriptionTabLayou.getTabCount()));
        productDescriptionViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(productDescriptionTabLayou));
        productDescriptionTabLayou.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                productDescriptionViewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        //product description with tab and viewPager end


        //rating now Layout
        rateNowLayout = findViewById(R.id.rateNowStarContainerId);
        for (int x = 0; x < rateNowLayout.getChildCount(); x++) {
            final int starPosition = x;
            rateNowLayout.getChildAt(x).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (currentUser == null) {
                        signindialog.show();
                    } else {
                        if (!running_rating_query) {
                            running_rating_query = true;
                            setRating(starPosition);
                            if (DBqueries.myRatedIds.contains(pId)) {

                            } else {
                                Map<String, Object> productRating = new HashMap<>();
                                productRating.put(starPosition + 1 + "_star", (long) documentSnapshot.get(starPosition + 1 + "_star") + 1);
                                productRating.put("average_rating", calculateAvaregeRating(starPosition + 1));
                                productRating.put("total_rating", (long) documentSnapshot.get("total_rating") + 1);

                                firebaseFirestore.collection("PRODUCTS").document(pId)
                                        .update(productRating).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            Map<String, Object> rating = new HashMap<>();
                                            rating.put("list_size", (long) DBqueries.myRatedIds.size() + 1);
                                            rating.put("product_id_" + DBqueries.myRatedIds.size(), pId);
                                            rating.put("rating_" + DBqueries.myRatedIds.size(), (long) starPosition + 1);

                                            firebaseFirestore.collection("USERS").document(currentUser.getUid()).collection("USER_DATA").document("MY_RATING")
                                                    .update(rating).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isSuccessful()) {
                                                        DBqueries.myRatedIds.add(pId);
                                                        DBqueries.myRating.add((long) starPosition + 1);

                                                        TextView rating = (TextView) ratingsNumberContainer.getChildAt(5 - starPosition - 1);
                                                        rating.setText(String.valueOf(Integer.parseInt(rating.getText().toString()) + 1));

                                                        totalRatingMiniView.setText("(" + ((long) documentSnapshot.get("total_rating") + 1) + ")Ratings");
                                                        totalrating.setText(((long) documentSnapshot.get("total_rating") + 1) + "Ratings");
                                                        totalContinerRating.setText(String.valueOf((long) documentSnapshot.get("total_rating") + 1));

                                                        average_rating.setText(String.valueOf(calculateAvaregeRating(starPosition + 1)));
                                                        productDetailsproductRatingMiniView.setText(String.valueOf(calculateAvaregeRating(starPosition + 1)));

                                                        for (int x = 0; x < 5; x++) {
                                                            TextView ratingfiger = (TextView) ratingsNumberContainer.getChildAt(x);

                                                            ProgressBar progressBar = (ProgressBar) ratingProgressBarStats.getChildAt(x);
                                                            int maxProgress = Integer.parseInt(String.valueOf((long) documentSnapshot.get("total_rating") + 1));
                                                            progressBar.setMax(maxProgress);
                                                            progressBar.setProgress(Integer.parseInt(ratingfiger.getText().toString()));

                                                        }

                                                        Toast.makeText(getApplicationContext(), "Thank you for rating", Toast.LENGTH_LONG).show();
                                                    } else {
                                                        setRating(initialrating);
                                                        String error = task.getException().getMessage();
                                                        Toast.makeText(getApplicationContext(), error, Toast.LENGTH_SHORT).show();
                                                    }
                                                    running_rating_query = false;
                                                }
                                            });
                                        } else {
                                            running_rating_query = false;
                                            setRating(initialrating);
                                            String error = task.getException().getMessage();
                                            Toast.makeText(getApplicationContext(), error, Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });


                            }
                        }

                    }

                }
            });
        }
        //rating now Layout


        //buy now btn
        buyNowBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingDialog.show();
                if (currentUser == null) {
                    signindialog.show();
                } else {
                    DeliveryActivity.cartItemModelList=new ArrayList<>();
                    DeliveryActivity.cartItemModelList.add(new CartItemModel(CartItemModel.CART_ITEM, pId,
                            (String) documentSnapshot.get("product_title"),
                            (String) documentSnapshot.get("product_price"),
                            (String) documentSnapshot.get("cutted_price"),
                            (String) documentSnapshot.get("product_img_" + 1),
                            (long) documentSnapshot.get("free_coupen"),
                            (long) 1,
                            (long) 0,
                            (long) 0,
                            (boolean)documentSnapshot.get("in_stock")));
                    DeliveryActivity.cartItemModelList.add(new CartItemModel(CartItemModel.CART_TOTAL_AMOUNT));

                    if (DBqueries.myAddessModelList.size()==0)
                    {
                        DBqueries.loadAddress(ProductDetailsActivity.this, loadingDialog);
                    }else
                    {
                        loadingDialog.dismiss();
                        Intent deliveryIntent=new Intent(ProductDetailsActivity.this,DeliveryActivity.class);
                        startActivity(deliveryIntent);
                    }
                }


            }
        });
        //buy now btn



        //--------------------------------
        //signIn dilog module
        signindialog = new Dialog(ProductDetailsActivity.this);
        signindialog.setContentView(R.layout.sign_in_dialog);
        signindialog.setCancelable(true);
        signindialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        Button dilogsignInBtn = signindialog.findViewById(R.id.dilogsignInBtnId);
        Button dilogsignUpBtn = signindialog.findViewById(R.id.dilogsignUpBtnId);

        final Intent regesterIntent = new Intent(ProductDetailsActivity.this, RegesterActivity.class);

        dilogsignInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SigninFragment.disableCloseBtn = true;
                SignUpFragment.disablecloseBtn = true;
                signindialog.dismiss();
                setSignUpFragment = false;
                startActivity(regesterIntent);
            }
        });
        dilogsignUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SignUpFragment.disablecloseBtn = true;
                SigninFragment.disableCloseBtn = true;
                signindialog.dismiss();
                setSignUpFragment = true;
                startActivity(regesterIntent);
            }
        });
    }

    //on start activity


    @Override
    protected void onStart() {
        currentUser = firebaseAuth.getInstance().getCurrentUser();
        final String pId = getIntent().getStringExtra("ptoductId");

        //check user add this product wishList
        if (currentUser != null) {
            //rating load
            if (DBqueries.myRating.size() == 0) {
                DBqueries.loadratingList(ProductDetailsActivity.this);
            }
            if (DBqueries.wishList.size() == 0) {
                DBqueries.loadWishList(ProductDetailsActivity.this, loadingDialog, false);

            } else {
                loadingDialog.dismiss();
            }
            if (DBqueries.cartList.size() == 0) {
                DBqueries.loadCartList(ProductDetailsActivity.this, loadingDialog, false);
            } else {
                loadingDialog.dismiss();
            }


        } else {
            loadingDialog.dismiss();
        }

        //start selected
        if (DBqueries.myRatedIds.contains(pId)) {
            int indexx = DBqueries.myRatedIds.indexOf(pId);
            initialrating = Integer.parseInt(String.valueOf(DBqueries.myRating.get(indexx))) - 1;
            setRating(initialrating);
        }

        //btn red condition
        if (DBqueries.wishList.contains(pId)) {
            AlreadyaddTpWishList = true;
            addToWishListBtn.setSupportImageTintList(getResources().getColorStateList(R.color.colorPrimary));

        } else {
            AlreadyaddTpWishList = false;
        }
        //wishlist end

        if (DBqueries.cartList.contains(pId)) {
            AlreadyaddTpCartList = true;
        } else {
            AlreadyaddTpCartList = false;
        }
        super.onStart();
    }

    //        diloag recycler view show with more btn
    public static void morecoupenShow() {
        if (coupenDialogContainer.getVisibility() == View.GONE) {
            coupenDialogContainer.setVisibility(View.VISIBLE);
            coupenDialogSelected.setVisibility(View.GONE);

        } else {
            coupenDialogContainer.setVisibility(View.GONE);
            coupenDialogSelected.setVisibility(View.VISIBLE);
        }


    }

    //rating now Layout
    public static void setRating(int starPosition) {

        for (int x = 0; x < rateNowLayout.getChildCount(); x++) {
            ImageView startBtn = (ImageView) rateNowLayout.getChildAt(x);
            startBtn.setImageTintList(ColorStateList.valueOf(Color.parseColor("#BFBDBE")));
            if (x <= starPosition) {
                startBtn.setImageTintList(ColorStateList.valueOf(Color.parseColor("#ffbb00")));
            }
        }


    }

    //rating now Layout
    //varage rating
    private long calculateAvaregeRating(long curentUserRating) {
        long totalStars = 0;
        for (int x = 1; x < 6; x++) {
            totalStars = totalStars + ((long) documentSnapshot.get(x + "_star")) * x;
        }
        totalStars = totalStars + curentUserRating;
        return totalStars / ((long) documentSnapshot.get("total_rating") + 1);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.search_and_cart_icon, menu);
//cart notifictaion
//        //cart icon ar upora badge number show
//        MenuItem cartItem=menu.findItem(R.id.maincartId);
//        if (DBqueries.cartList.size()>0)
//        {
//            cartItem.setActionView((int) R.layout.badge_layout);
//            ImageView badgeIcon=cartItem.getActionView().findViewById(R.id.badgeIcon);
//            badgeIcon.setImageResource(R.mipmap.cart_white);
//            TextView badgeCount=cartItem.getActionView().findViewById(R.id.badgeCountId);
//            badgeCount.setText(String.valueOf(DBqueries.cartList.size()));
//
//            cartItem.getActionView().setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if (currentUser == null) {
//                        signindialog.show();
//                    } else {
//                        Intent cartIntent = new Intent(getApplicationContext(), MainActivity.class);
//                        show_cart = true;
//                        startActivity(cartIntent);
//                    }
//                }
//            });
//        }else
//        {
//            cartItem.setActionView(null);
//        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.mainsearch_icon_id) {
            if (fromesearch)
            {
                finish();
            }else
            {
                Intent searchIntent=new Intent(getApplicationContext(),SearchActivity.class);
                startActivity(searchIntent);
            }
            return true;
        } else if (id == R.id.main_cart_icon_id) {
            if (currentUser == null) {
                signindialog.show();
            } else {
                Intent cartIntent = new Intent(getApplicationContext(), MainActivity.class);
                show_cart = true;
                startActivity(cartIntent);
            }

        } else if (id == android.R.id.home) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        fromesearch=false;
    }
}
