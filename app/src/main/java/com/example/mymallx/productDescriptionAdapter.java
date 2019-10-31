package com.example.mymallx;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.List;

public class productDescriptionAdapter extends FragmentPagerAdapter {

    private int totalTab;
    private String productDesription;
    private String productOtherDeatails;
    private List<ProductSpecificationModel>productSpecificationModelList;

    public productDescriptionAdapter(@NonNull FragmentManager fm, int totalTab, String productDesription, String productOtherDeatails, List<ProductSpecificationModel> productSpecificationModelList) {
        super(fm);
        this.productDesription = productDesription;
        this.productOtherDeatails = productOtherDeatails;
        this.productSpecificationModelList = productSpecificationModelList;
        this.totalTab=totalTab;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position)
        {
            case 0:
                ProductDescriptionFragment productDescriptionFragment1=new ProductDescriptionFragment();
                productDescriptionFragment1.bodyText=productDesription;
                return productDescriptionFragment1;

            case 1:
                ProductSpecificationFragment productSpecificationFragment=new ProductSpecificationFragment();
                productSpecificationFragment.productSpecificationModelsList=productSpecificationModelList;
                return productSpecificationFragment;
            case 2:
                ProductDescriptionFragment productDescriptionFragment3=new ProductDescriptionFragment();
                productDescriptionFragment3.bodyText=productOtherDeatails;
                return productDescriptionFragment3;

                default:
                    return null;
        }
    }

    @Override
    public int getCount() {
        return totalTab;
    }
}
