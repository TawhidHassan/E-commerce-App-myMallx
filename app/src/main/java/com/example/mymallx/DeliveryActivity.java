package com.example.mymallx;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class DeliveryActivity extends AppCompatActivity {

    RecyclerView deliveryItemsRecyclerView;
    ImageView myMallLogoActionBar;
    FrameLayout frameLayout;
    Button changeAddressBtn, continue_btn;
    TextView total_Cart_Amount;
    private Dialog loadingDialog;
    private Dialog paymentMethodDilog;

    private TextView fullname;
    private TextView fullAddress;
    private TextView pincod;
    ImageButton caseOndeliverbtn;
    ImageButton paytmBtn;

    String name, mobileNo;

    private ConstraintLayout orderConfremationLayout;
    TextView confirmProductorderId;
    TextView continueShopeing;

    public static List<CartItemModel> cartItemModelList;
    /**
     * my address select ar jonno
     **/
    public static final int SELECT_ADDRESS = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle("Delivery");
        //back arrow button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        deliveryItemsRecyclerView = findViewById(R.id.deliveryRecycler_Id);
        changeAddressBtn = findViewById(R.id.shipping_addressChangeBtn_id);
        continue_btn = findViewById(R.id.continue_btn_id);
        total_Cart_Amount = findViewById(R.id.total_Cart_Amount_Id);

        fullname = findViewById(R.id.shiping_full_name_id);
        fullAddress = findViewById(R.id.shiping_fullAddress_id);
        pincod = findViewById(R.id.Shiping_pinCode_id);

        orderConfremationLayout = findViewById(R.id.confiremationLayoutId);
        confirmProductorderId = findViewById(R.id.confirmorderId);
        continueShopeing = findViewById(R.id.continueShopingId);

        //loading dialog
        loadingDialog = new Dialog(DeliveryActivity.this);
        loadingDialog.setContentView(R.layout.loading_progress_dilog);
        loadingDialog.setCancelable(false);
        loadingDialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.slider_background));
        loadingDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        //end dialog

        //paymentDilog dialog
        paymentMethodDilog = new Dialog(DeliveryActivity.this);
        paymentMethodDilog.setContentView(R.layout.payment_meethods);
        paymentMethodDilog.setCancelable(true);
        paymentMethodDilog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.slider_background));
        paymentMethodDilog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        //end paymentDilog

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        deliveryItemsRecyclerView.setLayoutManager(linearLayoutManager);


        /**cart items
         //        List<CartItemModel> deliveryItemModelList=new ArrayList<>();
         //        deliveryItemModelList.add(new CartItemModel(0,"Redmi x","RS.3849/-","Rs.9999/-",R.mipmap.mobile,2,1,0,0));
         //        deliveryItemModelList.add(new CartItemModel(0,"Redmi x","RS.3849/-","Rs.9999/-",R.mipmap.mobile,4,1,1,0));
         //        deliveryItemModelList.add(new CartItemModel(0,"Redmi x","RS.3849/-","Rs.9999/-",R.mipmap.mobile,0,1,1,0));

         //totao cart
         //        deliveryItemModelList.add(new CartItemModel(1,"Price (3 Items)","Free","9188","Rs.9373/-","Rs.64744/-"));
         **/

        CartAddapter cartAddapter = new CartAddapter(cartItemModelList, total_Cart_Amount);
        deliveryItemsRecyclerView.setAdapter(cartAddapter);
        cartAddapter.notifyDataSetChanged();

        changeAddressBtn.setVisibility(View.VISIBLE);

        continue_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                paymentMethodDilog.show();
                caseOndeliverbtn = paymentMethodDilog.findViewById(R.id.caseOndeliveryBtnId);
                paytmBtn = paymentMethodDilog.findViewById(R.id.paytmBtnId);

                caseOndeliverbtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        paymentMethodDilog.dismiss();
                        Intent OTPIntent = new Intent(getApplicationContext(), OTPverificationActivity.class);
                        OTPIntent.putExtra("mobileNo", mobileNo.substring(0, 10));
                        startActivity(OTPIntent);
                    }
                });
            }
        });
        if (OTPverificationActivity.thanquActivity) {
            confirmProductorderId.setText("Order Id: " + cartItemModelList.get(0).getProductTitle());
            orderConfremationLayout.setVisibility(View.VISIBLE);

            continueShopeing.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                    OTPverificationActivity.thanquActivity = false;
                    Intent homeIntent = new Intent(getApplicationContext(), MainActivity.class);

                    /**
                     *when complecet order come back main activety and clear all old use activity
                     */
                    homeIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(homeIntent);
                }
            });
        }

        changeAddressBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent MyaddressInten = new Intent(getApplicationContext(), MyAddressActivity.class);
                MyaddressInten.putExtra("MODE", SELECT_ADDRESS);
                startActivity(MyaddressInten);
            }
        });

    }

    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * when user select another /new address then call onStart methods
     */

    @Override
    protected void onStart() {
        super.onStart();
        name = DBqueries.myAddessModelList.get(DBqueries.selcetedAddress).getFullName();
        mobileNo = DBqueries.myAddessModelList.get(DBqueries.selcetedAddress).getMobileNo();

        fullname.setText(name + "-" + mobileNo);
        fullAddress.setText(DBqueries.myAddessModelList.get(DBqueries.selcetedAddress).getAddress());
        pincod.setText(DBqueries.myAddessModelList.get(DBqueries.selcetedAddress).getPincode());
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        Intent homeIntent = new Intent(getApplicationContext(), MainActivity.class);

        /**
         *when complecet order come back main activety and clear all old use activity
         */
        homeIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(homeIntent);
    }
}
