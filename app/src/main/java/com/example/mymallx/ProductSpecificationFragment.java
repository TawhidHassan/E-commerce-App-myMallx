package com.example.mymallx;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProductSpecificationFragment extends Fragment {

        RecyclerView productSpecifoicationRecyclerView;
        public List<ProductSpecificationModel>productSpecificationModelsList;
    public ProductSpecificationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_product_specification, container, false);
        productSpecifoicationRecyclerView=view.findViewById(R.id.productSpecifoicationRecyclerViewId);

        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(view.getContext());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);

        productSpecifoicationRecyclerView.setLayoutManager(linearLayoutManager);
//        List<ProductSpecificationModel> productSpecificationModelList=new ArrayList<>();
//        productSpecificationModelList.add(new ProductSpecificationModel(0,"Genaral"));
//        productSpecificationModelList.add(new ProductSpecificationModel(1,"Ram","4 GB"));
//        productSpecificationModelList.add(new ProductSpecificationModel(1,"Camera","48 MPXL"));
//        productSpecificationModelList.add(new ProductSpecificationModel(1,"Battery","4300 MH"));



        ProductSpecificationAdapter productSpecificationAdapter=new ProductSpecificationAdapter(productSpecificationModelsList);
        productSpecificationAdapter.notifyDataSetChanged();

        productSpecifoicationRecyclerView.setAdapter(productSpecificationAdapter);

        return view;

    }

}
