package com.example.mymallx;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.google.firebase.firestore.FirebaseFirestore;




/**
 * A simple {@link Fragment} subclass.
 */
public class ProductDescriptionFragment extends Fragment {

    TextView productDescriptiTexton;
   public String bodyText;

    public ProductDescriptionFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_product_description, container, false);
        productDescriptiTexton=view.findViewById(R.id.productDescriptiTextonId);

        //tabPotion comes from ProductDetailsActivity
        productDescriptiTexton.setText(bodyText);//data comes from ProductDetailsActivity



        return view;
    }

}
