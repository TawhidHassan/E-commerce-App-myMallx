package com.example.mymallx;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mymallx.ui.home.HomeFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class DBqueries {


    //initiallize firebase
    public static FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    public static FirebaseUser currentUser = firebaseAuth.getCurrentUser();
    public static FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();

    public static List<CategoryModel> categoryModelList = new ArrayList<>();


    //for category perticuler product
    public static List<List<HomePageModel>> lists = new ArrayList<>();
    public static List<String> loadedCategoryNames = new ArrayList<>();

    public static List<String> wishList = new ArrayList<>();
    public static List<WishListModel> wishListModelList = new ArrayList<>();

    public static List<String> myRatedIds = new ArrayList<>();
    public static List<Long> myRating = new ArrayList<>();

    public static List<String> cartList = new ArrayList<>();
    public static List<CartItemModel> cartItemModelList = new ArrayList<>();

    public static List<MyAddessModel> myAddessModelList = new ArrayList<>();
    public static int selcetedAddress = -1;

    public static String email,fullName,profile;

    public static void loadCategories(final RecyclerView categoryRecyclerView, Context context) {
        categoryModelList.clear();
        firebaseFirestore.collection("CATEGORIES").orderBy("index").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot queryDocumentSnapshot : task.getResult()) {
                                categoryModelList.add(new CategoryModel(queryDocumentSnapshot.get("icon").toString(), queryDocumentSnapshot.get("categorieName").toString()));
                            }
                            CategoryAdapter categoryAdapter = new CategoryAdapter(categoryModelList);
                            categoryRecyclerView.setAdapter(categoryAdapter);
                            categoryAdapter.notifyDataSetChanged();
                        } else {

                        }
                    }
                });
    }

    public static void loadFragmentData(final RecyclerView homePageRecyclerView, final Context context, final int index, String categoryNme) {
        //initiallize firebase
        firebaseFirestore.collection("CATEGORIES").document(categoryNme.toUpperCase()).collection("TOP_DEALS").orderBy("index").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot queryDocumentSnapshot : task.getResult()) {
                                if (queryDocumentSnapshot.get("view_type") != null) {
                                    if ((long) queryDocumentSnapshot.get("view_type") == 0)//for slidder banner frome firebase
                                    {
                                        List<SliderModel> sliderModelList = new ArrayList<>();
                                        long no_of_banner = (long) queryDocumentSnapshot.get("no_of_banner");
                                        for (long x = 1; x < no_of_banner + 1; x++) {
                                            sliderModelList.add(new SliderModel((String) queryDocumentSnapshot.get("banner_" + x), (String) queryDocumentSnapshot.get("banner_" + x + "_background")));
                                        }
                                        lists.get(index).add(new HomePageModel(0, sliderModelList));

                                    } else if ((long) queryDocumentSnapshot.get("view_type") == 1)//for strip banner
                                    {
                                        lists.get(index).add(new HomePageModel(1, (String) queryDocumentSnapshot.get("strip_ad_banner"), (String) queryDocumentSnapshot.get("background")));

                                    } else if ((long) queryDocumentSnapshot.get("view_type") == 2) {//for horizontalScroolProduct

                                        List<HorizontalScroolProductsModel> horizontalScroolProductsModelList1 = new ArrayList<>();
                                        List<WishListModel> viewAllHorizontalScroolProductsModelList1 = new ArrayList<>();

                                        long no_of_products = (long) queryDocumentSnapshot.get("no_of_product");
                                        for (long x = 1; x < no_of_products + 1; x++) {
                                            horizontalScroolProductsModelList1.add(new HorizontalScroolProductsModel((String) queryDocumentSnapshot.get("product_id_" + x),
                                                    (String) queryDocumentSnapshot.get("product_img_" + x),
                                                    (String) queryDocumentSnapshot.get("product_title_" + x),
                                                    (String) queryDocumentSnapshot.get("product_subtitle_" + x),
                                                    (String) queryDocumentSnapshot.get("product_price_" + x)));

                                            viewAllHorizontalScroolProductsModelList1.add(new WishListModel((String) queryDocumentSnapshot.get("product_id_" + x), (String) queryDocumentSnapshot.get("product_img_" + x),
                                                    (String) queryDocumentSnapshot.get("product_full_title_" + x),
                                                    (long) queryDocumentSnapshot.get("free_coupens_" + x),
                                                    (String) queryDocumentSnapshot.get("average_rating_" + x),
                                                    (String) queryDocumentSnapshot.get("total_rating_" + x),
                                                    (String) queryDocumentSnapshot.get("product_price_" + x),
                                                    (String) queryDocumentSnapshot.get("cutted_price_" + x),
                                                    (Boolean) queryDocumentSnapshot.get("COD_" + x)));
                                        }
                                        lists.get(index).add(new HomePageModel(2, (String) queryDocumentSnapshot.get("layout_title"), queryDocumentSnapshot.get("layout_background").toString(), horizontalScroolProductsModelList1, viewAllHorizontalScroolProductsModelList1));


                                    } else if ((long) queryDocumentSnapshot.get("view_type") == 3) {

                                        List<HorizontalScroolProductsModel> grideProductsModelList1 = new ArrayList<>();
                                        long no_of_products = (long) queryDocumentSnapshot.get("no_of_product");
                                        for (long x = 1; x < no_of_products + 1; x++) {
                                            grideProductsModelList1.add(new HorizontalScroolProductsModel(queryDocumentSnapshot.get("product_id_" + x).toString(), queryDocumentSnapshot.get("product_img_" + x).toString(), queryDocumentSnapshot.get("product_title_" + x).toString(), queryDocumentSnapshot.get("product_subtitle_" + x).toString(), (String) queryDocumentSnapshot.get("product_price_" + x)));
                                        }
                                        lists.get(index).add(new HomePageModel(3, queryDocumentSnapshot.get("layout_title").toString(), queryDocumentSnapshot.get("layout_background").toString(), grideProductsModelList1));

                                    }
                                } else {

                                }
                            }
                            HomePageAdapter homePageAdapter = new HomePageAdapter(lists.get(index));
                            homePageRecyclerView.setAdapter(homePageAdapter);
                            homePageAdapter.notifyDataSetChanged();
                            HomeFragment.swipeRefreshLayout.setRefreshing(false);
                        } else {
                            String error = task.getException().getMessage();
                            Toast.makeText(context, error, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    //for wish list
    public static void loadWishList(final Context context, final Dialog dialog, final boolean loadProductData) {
        wishList.clear();
        firebaseFirestore.collection("USERS").document(FirebaseAuth.getInstance().getUid()).collection("USER_DATA").document("MY_WISHLIST")
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                if (task.isSuccessful()) {
                    for (long x = 0; x < (long) task.getResult().get("list_size"); x++) {
                        wishList.add((String) task.getResult().get("product_id_" + x));

                        if (loadProductData) {
                            wishListModelList.clear();
                            final String pid = (String) task.getResult().get("product_id_" + x);
                            firebaseFirestore.collection("PRODUCTS").document(pid)
                                    .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                    if (task.isSuccessful()) {
                                        wishListModelList.add(new WishListModel(pid, (String) task.getResult().get("product_img_" + 1),
                                                (String) task.getResult().get("product_title"),
                                                (Long) task.getResult().get("free_coupen"),
                                                (String) task.getResult().get("average_rating"),
                                                (String) task.getResult().get("total_rating"),
                                                (String) task.getResult().get("product_price"),
                                                (String) task.getResult().get("cutted_price"),
                                                (Boolean) task.getResult().get("COD")));

                                        MyWishListFragment.wishListAdapter.notifyDataSetChanged();

                                    } else {
                                        String error = task.getException().getMessage();
                                        Toast.makeText(context, error, Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }
                    }
                } else {
                    String error = task.getException().getMessage();
                    Toast.makeText(context, error, Toast.LENGTH_SHORT).show();
                }
                dialog.dismiss();
            }
        });
    }

    //remove frome wish lsit
    public static void removeFromeWisgList(final int index, final Context context) {
        final String removeProductId = wishList.get(index);
        wishList.remove(index);
        Map<String, Object> updateWishList = new HashMap<>();
        for (int x = 0; x < wishList.size(); x++) {
            updateWishList.put("product_id_" + x, wishList.get(x));
        }
        updateWishList.put("list_size", (long) wishList.size());

        firebaseFirestore.collection("USERS").document(FirebaseAuth.getInstance().getUid()).collection("USER_DATA").document("MY_WISHLIST")
                .set(updateWishList).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    if (wishListModelList.size() != 0) {
                        wishListModelList.remove(index);
                        MyWishListFragment.wishListAdapter.notifyDataSetChanged();
                    }
                    ProductDetailsActivity.AlreadyaddTpWishList = false;
                    Toast.makeText(context, "remove sucessfully", Toast.LENGTH_LONG).show();

                } else {
                    wishList.add(index, removeProductId);
                    ProductDetailsActivity.addToWishListBtn.setSupportImageTintList(context.getResources().getColorStateList(R.color.colorPrimary));
                    String error = task.getException().getMessage();
                    Toast.makeText(context, error, Toast.LENGTH_SHORT).show();
                }
                ProductDetailsActivity.addToWishListBtn.setEnabled(true);//for when user click one time wishlist is updated and btn are disable and when it was added the it was again enabke

            }
        });
    }

    //for rating
    public static void loadratingList(final Context context) {
        myRatedIds.clear();
        myRating.clear();
        firebaseFirestore.collection("USERS").document(FirebaseAuth.getInstance().getUid()).collection("USER_DATA").document("MY_RATING")
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    for (long x = 0; x < (long) task.getResult().get("list_size"); x++) {
                        myRatedIds.add((String) task.getResult().get("product_id_" + x));
                        myRating.add((long) task.getResult().get("rating_" + x));

                        if (task.getResult().get("product_id_" + x).equals(ProductDetailsActivity.pId) && ProductDetailsActivity.rateNowLayout != null) {
                            ProductDetailsActivity.initialrating = Integer.parseInt(String.valueOf((long) task.getResult().get("rating_" + x))) - 1;
                            ProductDetailsActivity.setRating(ProductDetailsActivity.initialrating);
                        }
                    }
                } else {
                    String error = task.getException().getMessage();
                    Toast.makeText(context, error, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    //cart item
    public static void loadCartList(final Context context, final Dialog dialog, final Boolean loadProductData) {
        cartList.clear();
        firebaseFirestore.collection("USERS").document(FirebaseAuth.getInstance().getUid()).collection("USER_DATA").document("MY_CART")
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                if (task.isSuccessful()) {
                    for (long x = 0; x < (long) task.getResult().get("list_size"); x++) {
                        cartList.add((String) task.getResult().get("product_id_" + x));

                        if (DBqueries.cartList.contains(ProductDetailsActivity.pId)) {
                            ProductDetailsActivity.AlreadyaddTpCartList = true;
                        } else {
                            ProductDetailsActivity.AlreadyaddTpCartList = false;
                        }

                        if (loadProductData) {
                            cartItemModelList.clear();
                            final String pid = (String) task.getResult().get("product_id" + x);
                            firebaseFirestore.collection("PRODUCTS").document(pid)
                                    .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                    if (task.isSuccessful()) {
                                        int index = 0;
                                        if (cartList.size() >= 2) {
                                            index = cartList.size() - 2;
                                        }
                                        cartItemModelList.add(index, new CartItemModel(CartItemModel.CART_ITEM,
                                                pid,
                                                (String) task.getResult().get("product_title"),
                                                (String) task.getResult().get("product_price"),
                                                (String) task.getResult().get("cutted_price"),
                                                (String) task.getResult().get("product_img_" + 1),
                                                (long) task.getResult().get("free_coupen"),
                                                (long) 1,
                                                (long) 0,
                                                (long) 0,
                                                (boolean) task.getResult().get("in_stock")));

                                        //cart total amount logic
                                        if (cartList.size() == 1) {
                                            cartItemModelList.add(new CartItemModel(CartItemModel.CART_TOTAL_AMOUNT));
                                        }
                                        if (cartList.size() == 0) {
                                            cartItemModelList.clear();
                                        }
                                        //cart total amount logic
                                        MyCartFragment.cartAddapter.notifyDataSetChanged();

                                    } else {
                                        String error = task.getException().getMessage();
                                        Toast.makeText(context, error, Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }
                    }
                } else {
                    String error = task.getException().getMessage();
                    Toast.makeText(context, error, Toast.LENGTH_SHORT).show();
                }
                dialog.dismiss();
            }
        });
    }


    //remove cart item
    public static void removeFromeCart(final int index, final Context context) {
        final String removeProductId = wishList.get(index);
        cartList.remove(index);
        Map<String, Object> updateCartList = new HashMap<>();
        for (int x = 0; x < cartList.size(); x++) {
            updateCartList.put("product_id_" + x, cartList.get(x));
        }
        updateCartList.put("list_size", (long) cartList.size());

        firebaseFirestore.collection("USERS").document(FirebaseAuth.getInstance().getUid()).collection("USER_DATA").document("MY_CART")
                .set(updateCartList).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    if (cartItemModelList.size() != 0) {
                        cartItemModelList.remove(index);
                        MyCartFragment.cartAddapter.notifyDataSetChanged();
                    }
                    if (cartList.size() == 0) {
                        cartItemModelList.clear();
                    }
                    Toast.makeText(context, "remove sucessfully", Toast.LENGTH_LONG).show();

                } else {
                    cartList.add(index, removeProductId);
                    String error = task.getException().getMessage();
                    Toast.makeText(context, error, Toast.LENGTH_SHORT).show();
                }

                ProductDetailsActivity.running_cart_query = false;
            }
        });
    }


    //    user address
    public static void loadAddress(final Context context, final Dialog loadingDialog) {
        myAddessModelList.clear();
        firebaseFirestore.collection("USERS").document(currentUser.getUid()).collection("USER_DATA").document("MY_ADDRESS")
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {

                    Intent addaddressIntent;

                    if ((Long) task.getResult().get("list_size") == 0) {
                        addaddressIntent = new Intent(context, AddAddressActivity.class);
                        addaddressIntent.putExtra("INTENT", "deliveryInten");

                    } else {
                        for (long x = 1; x < (long) task.getResult().get("list_size") + 1; x++) {
                            myAddessModelList.add(new MyAddessModel((String) task.getResult().get("fullname_" + x),
                                    (String) task.getResult().get("address_" + x),
                                    (String) task.getResult().get("pincode_" + x),
                                    (boolean) task.getResult().get("selected_" + x),
                                    (String)task.getResult().get("mobile_no_"+x)));

                            if ((boolean) task.getResult().get("selected_" + x)) {
                                selcetedAddress = Integer.parseInt(String.valueOf(x - 1));
                            }
                        }
                        addaddressIntent = new Intent(context, DeliveryActivity.class);
                    }
                    context.startActivity(addaddressIntent);
                } else {
                    String error = task.getException().getMessage();
                    Toast.makeText(context, error, Toast.LENGTH_SHORT).show();
                }

                loadingDialog.dismiss();
            }
        });
    }

    //clear data
    public static void clearData() {
        categoryModelList.clear();
        lists.clear();
        loadedCategoryNames.clear();
        wishList.clear();
        wishListModelList.clear();
    }


}
