package com.example.mymallx;


import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.circularreveal.cardview.CircularRevealCardView;

import java.util.ArrayList;
import java.util.List;

import static com.example.mymallx.MainActivity.CART_FRAGMENT;
import static com.example.mymallx.MainActivity.HOME_FRAGMENT;
import static com.example.mymallx.MainActivity.currentFragment;


/**
 * A simple {@link Fragment} subclass.
 */
public class MyCartFragment extends Fragment {


    RecyclerView cartItemsRecyclerView;
    ImageView myMallLogoActionBar;
    FrameLayout frameLayout;
    Button continueBtn;
    TextView total_Cart_Amount;

    Dialog loadingDialog;
    public static CartAddapter cartAddapter;

    public MyCartFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        currentFragment=CART_FRAGMENT;


        View view= inflater.inflate(R.layout.fragment_my_cart, container, false);
        cartItemsRecyclerView=view.findViewById(R.id.cart_item_recyclerView_id);
        continueBtn=view.findViewById(R.id.continue_btn_id);
        total_Cart_Amount=view.findViewById(R.id.total_Cart_Amount_Id);

        //loading dialog
        loadingDialog = new Dialog(getContext());
        loadingDialog.setContentView(R.layout.loading_progress_dilog);
        loadingDialog.setCancelable(false);
        loadingDialog.getWindow().setBackgroundDrawable(getContext().getDrawable(R.drawable.slider_background));
        loadingDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        loadingDialog.show();
        //end dialog

        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        cartItemsRecyclerView.setLayoutManager(linearLayoutManager);

        //cart items
//        List<CartItemModel>cartItemModelList=new ArrayList<>();
//        cartItemModelList.add(new CartItemModel(0,"Redmi x","RS.3849/-","Rs.9999/-",R.mipmap.mobile,2,1,0,0));
//        cartItemModelList.add(new CartItemModel(0,"Redmi x","RS.3849/-","Rs.9999/-",R.mipmap.mobile,4,1,1,0));
//        cartItemModelList.add(new CartItemModel(0,"Redmi x","RS.3849/-","Rs.9999/-",R.mipmap.mobile,0,1,1,0));
//        cartItemModelList.add(new CartItemModel(1,"Price (3 Items)","Free","9188","Rs.9373/-","Rs.64744/-"));

        if (DBqueries.cartItemModelList.size()==0)
        {
            DBqueries.cartList.clear();
            DBqueries.loadCartList(getContext(),loadingDialog,true);
        }else
        {
            loadingDialog.dismiss();
        }
        //totao cart

        cartAddapter=new CartAddapter(DBqueries.cartItemModelList,total_Cart_Amount);
        cartItemsRecyclerView.setAdapter(cartAddapter);
        cartAddapter.notifyDataSetChanged();

        continueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DeliveryActivity.cartItemModelList=new ArrayList<>();
                for (int x=0;x<DBqueries.cartItemModelList.size();x++)
                {
                    CartItemModel cartItemModel=DBqueries.cartItemModelList.get(x);
                    if (cartItemModel.isInStock())
                    {
                        DeliveryActivity.cartItemModelList.add(cartItemModel);
                    }
                }
                DeliveryActivity.cartItemModelList.add(new CartItemModel(CartItemModel.CART_TOTAL_AMOUNT));
                loadingDialog.show();

                if (DBqueries.myAddessModelList.size()==0)
                {
                    DBqueries.loadAddress(getContext(), loadingDialog);
                }else
                {
                    loadingDialog.dismiss();
                    Intent deliveryIntent=new Intent(getContext(),DeliveryActivity.class);
                    startActivity(deliveryIntent);
                }


            }
        });
        return view;

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);


    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        if (currentFragment==HOME_FRAGMENT) {
            super.onCreateOptionsMenu(menu, inflater);
        }

    }




}
