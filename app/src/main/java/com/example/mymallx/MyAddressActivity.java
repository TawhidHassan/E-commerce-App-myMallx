package com.example.mymallx;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.mymallx.DeliveryActivity.SELECT_ADDRESS;

public class MyAddressActivity extends AppCompatActivity {

    RecyclerView addressRecyclerView;
    LinearLayout my_address_Btn;
    Button deliverBtn;
    TextView noOfAddressList;
    Dialog loadingDialog;

    private int preSelectedAddress;
    public static MyAddressesAdapter myAddressesAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_address);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle("My Address");
        //back arrow button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        my_address_Btn=findViewById(R.id.myAddress_addNew_btn_id);
        addressRecyclerView=findViewById(R.id.myAddress_recyclerView_id);
        deliverBtn=findViewById(R.id.myAddress_deliverHere_id);
        noOfAddressList=findViewById(R.id.noOfAddresListId);

        preSelectedAddress=DBqueries.selcetedAddress;

        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        addressRecyclerView.setLayoutManager(linearLayoutManager);

        //loading dialog
        loadingDialog = new Dialog(this);
        loadingDialog.setContentView(R.layout.loading_progress_dilog);
        loadingDialog.setCancelable(false);
        loadingDialog.getWindow().setBackgroundDrawable(this.getDrawable(R.drawable.slider_background));
        loadingDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        //end dialog

//        List<MyAddessModel>myAddessModelList=new ArrayList<>();
//        myAddessModelList.add(new MyAddessModel("sifat","dhaka","178393",true));
//        myAddessModelList.add(new MyAddessModel("sifat","dhaka","178393",false));
//        myAddessModelList.add(new MyAddessModel("sifat","dhaka","178393",false));
//        myAddessModelList.add(new MyAddessModel("sifat","dhaka","178393",false));

        //Mode define which activtiy or fragment user come
        int Mode=getIntent().getIntExtra("MODE",-1);

        //for hide or show delivery here btn
        if (Mode==SELECT_ADDRESS)
        {
            deliverBtn.setVisibility(View.VISIBLE);
        }else
        {
            deliverBtn.setVisibility(View.GONE);
        }
         myAddressesAdapter=new MyAddressesAdapter(DBqueries.myAddessModelList,Mode);
        addressRecyclerView.setAdapter(myAddressesAdapter);
        //remove default animation
        myAddressesAdapter.notifyDataSetChanged();


        my_address_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addAddressIntent=new Intent(MyAddressActivity.this,AddAddressActivity.class);
                addAddressIntent.putExtra("INTENT","null");
                startActivity(addAddressIntent);
            }
        });


        deliverBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (DBqueries.selcetedAddress!=preSelectedAddress)
                {
                    final int previousAddressIndex=preSelectedAddress;
                    //dialog show
                    loadingDialog.show();
                    Map<String ,Object>updateSelection=new HashMap<>();
                    updateSelection.put("selected_"+String.valueOf(preSelectedAddress+1),false);
                    updateSelection.put("selected_"+String.valueOf(DBqueries.selcetedAddress+1),true);

                    preSelectedAddress=DBqueries.selcetedAddress;

                    FirebaseFirestore.getInstance().collection("USERS").document(FirebaseAuth.getInstance().getUid())
                            .collection("USER_DATA").document("MY_ADDRESS")
                            .update(updateSelection).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful())
                            {
                                finish();
                            }else
                            {
                                preSelectedAddress=previousAddressIndex;
                                String error = task.getException().getMessage();
                                Toast.makeText(getApplicationContext(), error, Toast.LENGTH_SHORT).show();
                            }
                            loadingDialog.dismiss();
                        }
                    });
                }else
                {
                    finish();
                }
            }
        });
    }

    //for check user come which fragment or activity delivery/my profile
    public static void refreceItem(int deselect,int select)
    {
        myAddressesAdapter.notifyItemChanged(select);
        myAddressesAdapter.notifyItemChanged(deselect);
    }

    public boolean onOptionsItemSelected(MenuItem item) {

        int id=item.getItemId();
        if (id==android.R.id.home)
        {
            if (DBqueries.selcetedAddress!=preSelectedAddress)
            {
                DBqueries.myAddessModelList.get(DBqueries.selcetedAddress).setSelected(false);
                DBqueries.myAddessModelList.get(preSelectedAddress).setSelected(true);
                DBqueries.selcetedAddress=preSelectedAddress;

            }
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //backpress btn

    @Override
    public void onBackPressed() {
        if (DBqueries.selcetedAddress!=preSelectedAddress)
        {
            DBqueries.myAddessModelList.get(DBqueries.selcetedAddress).setSelected(false);
            DBqueries.myAddessModelList.get(preSelectedAddress).setSelected(true);
            DBqueries.selcetedAddress=preSelectedAddress;

        }
        super.onBackPressed();
    }

    @Override
    protected void onStart() {
        super.onStart();
        noOfAddressList.setText(String.valueOf(DBqueries.myAddessModelList.size())+" Saved addresses");
    }
}
