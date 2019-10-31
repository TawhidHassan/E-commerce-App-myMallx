package com.example.mymallx;

import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

public class GridProductLayoutAdapter extends BaseAdapter {

    //horizontal products layout same tar jonno same layout use kora
    List<HorizontalScroolProductsModel> horizontalScroolProductsModelList;

    public GridProductLayoutAdapter(List<HorizontalScroolProductsModel> horizontalScroolProductsModelList) {
        this.horizontalScroolProductsModelList = horizontalScroolProductsModelList;
    }

    @Override
    public int getCount() {
        return horizontalScroolProductsModelList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        View view;
        if(convertView==null)
        {

            view= LayoutInflater.from(parent.getContext()).inflate(R.layout.horizontal_scrool_item_layout,null);
            //horizontal products layout same tar jonno same layout use kora
            view.setElevation(0);
            view.setBackgroundColor(Color.parseColor("#ffffff"));
            ImageView productImag=view.findViewById(R.id.horizontalProductImagId);
            TextView productName=view.findViewById(R.id.horizontalProductNameId);
            TextView productDescriptiom=view.findViewById(R.id.horizontalProductDescriptionId);
            TextView productPrice=view.findViewById(R.id.horizontalProducPriceId);

            Glide.with(parent.getContext()).load(horizontalScroolProductsModelList.get(position).getProductImag()).apply(new RequestOptions().placeholder(R.drawable.placeholder)).into(productImag);
            productName.setText(horizontalScroolProductsModelList.get(position).getProductName());
            productDescriptiom.setText(horizontalScroolProductsModelList.get(position).getProductDescription());
            productPrice.setText("Rs."+horizontalScroolProductsModelList.get(position).getProductPrice()+"/-");

            if (horizontalScroolProductsModelList.size()!=0)
            {
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent productDetailsIntent=new Intent(parent.getContext(),ProductDetailsActivity.class);
                        productDetailsIntent.putExtra("ptoductId",horizontalScroolProductsModelList.get(position).getProductId());
                        parent.getContext().startActivities(new Intent[]{productDetailsIntent});
                    }
                });

            }


        }else {

            view=convertView;
        }
        return view;
    }
}
