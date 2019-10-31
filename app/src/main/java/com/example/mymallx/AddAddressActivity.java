package com.example.mymallx;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class AddAddressActivity extends AppCompatActivity {

    Button saveBtn;
    EditText city;
    EditText locality;
    EditText homeName;
    EditText pincode;
    EditText landMark;
    EditText name;
    EditText mobile;
    EditText altemateMobile;

    Dialog loadingDialog;


    //spinner
    Spinner stateSpinner;
    private String selectedState;
    private String[] stateList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_address);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle("Address");
        //back arrow button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        city = findViewById(R.id.address_city_id);
        locality = findViewById(R.id.address_locality_id);
        homeName = findViewById(R.id.address_flateNo_id);
        pincode = findViewById(R.id.address_pinecode_id);
        landMark = findViewById(R.id.address_landMark_id);
        name = findViewById(R.id.address_nsme_id);
        mobile = findViewById(R.id.address_mobileNo_id);
        altemateMobile = findViewById(R.id.address_altranativeMobile_id);

        //string array state
        stateList = getResources().getStringArray(R.array.india_states);


        loadingDialog = new Dialog(AddAddressActivity.this);
        loadingDialog.setContentView(R.layout.loading_progress_dilog);
        loadingDialog.setCancelable(false);
        loadingDialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.slider_background));
        loadingDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        //spinner state
        stateSpinner = findViewById(R.id.stateSpinnerId);
        ArrayAdapter spinnerAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, stateList);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        stateSpinner.setAdapter(spinnerAdapter);

        stateSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedState = stateList[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //spinner state end


        saveBtn = findViewById(R.id.address_Save_btn_id);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!TextUtils.isEmpty(city.getText())) {
                    if (!TextUtils.isEmpty(locality.getText())) {
                        if (!TextUtils.isEmpty(homeName.getText())) {
                            if (!TextUtils.isEmpty(pincode.getText()) && pincode.getText().length() == 6) {
                                if (!TextUtils.isEmpty(name.getText())) {
                                    if (!TextUtils.isEmpty(mobile.getText()) && mobile.getText().length() == 11) {
                                        loadingDialog.show();

                                        final String fullAddress = city.getText().toString() + ", " + locality.getText().toString() + ", " + homeName.getText().toString() + ", " + landMark.getText().toString() + ", " + selectedState;

                                        Map<String, Object> addaddress = new HashMap();
                                        addaddress.put("list_size", (long) DBqueries.myAddessModelList.size() + 1);

                                        if (TextUtils.isEmpty(altemateMobile.getText())) {
                                            addaddress.put("mobile_no_" + String.valueOf((long) DBqueries.myAddessModelList.size() + 1),  mobile.getText().toString() );
                                        } else {
                                            addaddress.put("mobile_no_" + String.valueOf((long) DBqueries.myAddessModelList.size() + 1), mobile.getText().toString());
                                        }
                                        addaddress.put("fullname_" + String.valueOf((long) DBqueries.myAddessModelList.size() + 1), name.getText().toString());

                                        addaddress.put("address_" + String.valueOf((long) DBqueries.myAddessModelList.size() + 1), fullAddress);
                                        addaddress.put("pincode_" + String.valueOf((long) DBqueries.myAddessModelList.size() + 1), pincode.getText().toString());
                                        addaddress.put("selected_" + String.valueOf((long) DBqueries.myAddessModelList.size() + 1), true);

                                        if (DBqueries.myAddessModelList.size() > 0) {
                                            addaddress.put("selected_" + (DBqueries.selcetedAddress + 1), false);
                                        }

                                        FirebaseFirestore.getInstance().collection("USERS").document(FirebaseAuth.getInstance().getUid())
                                                .collection("USER_DATA").document("MY_ADDRESS")
                                                .update(addaddress).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    if (DBqueries.myAddessModelList.size() > 0) {
                                                        DBqueries.myAddessModelList.get(DBqueries.selcetedAddress).setSelected(false);
                                                    }

                                                    if (TextUtils.isEmpty(altemateMobile.getText())) {
                                                        DBqueries.myAddessModelList.add(new MyAddessModel(name.getText().toString() , fullAddress, pincode.getText().toString(), true,mobile.getText().toString()));

                                                    } else {
                                                        DBqueries.myAddessModelList.add(new MyAddessModel(name.getText().toString(), ", " + fullAddress, pincode.getText().toString(), true, mobile.getText().toString() + " Altranative: " + altemateMobile.getText().toString()));

                                                    }

                                                    if (getIntent().getStringExtra("INTENT").equals("deliveryInten")) {
                                                        Intent deliveryInten = new Intent(getApplicationContext(), DeliveryActivity.class);
                                                        startActivity(deliveryInten);
                                                    }else
                                                    {
                                                        MyAddressActivity.refreceItem(DBqueries.selcetedAddress,DBqueries.myAddessModelList.size()-1);
                                                    }
                                                    DBqueries.selcetedAddress = DBqueries.myAddessModelList.size() - 1;

                                                    finish();


                                                } else {
                                                    String error = task.getException().getMessage();
                                                    Toast.makeText(getApplicationContext(), error, Toast.LENGTH_SHORT).show();
                                                }
                                                loadingDialog.dismiss();
                                            }
                                        });
                                    } else {
                                        mobile.requestFocus();
                                        Toast.makeText(getApplicationContext(), "Please provide valide mobile number", Toast.LENGTH_SHORT).show();

                                    }
                                } else {
                                    name.requestFocus();
                                }
                            } else {
                                pincode.requestFocus();
                                Toast.makeText(getApplicationContext(), "Please provide valide PineCode", Toast.LENGTH_SHORT).show();

                            }
                        } else {
                            homeName.requestFocus();
                        }
                    } else {
                        locality.requestFocus();
                    }
                } else {
                    city.requestFocus();
                }

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
}
