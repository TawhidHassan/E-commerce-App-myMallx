package com.example.mymallx;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MyOrderAdapter extends RecyclerView.Adapter<MyOrderAdapter.ViewHolder> {

    List<MyOrderItemModel> myOrderItemModelList;

    public MyOrderAdapter(List<MyOrderItemModel> myOrderItemModelList) {
        this.myOrderItemModelList = myOrderItemModelList;
    }

    @NonNull
    @Override
    public MyOrderAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.my_order_item_layout,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyOrderAdapter.ViewHolder holder, int position) {

        int resource=myOrderItemModelList.get(position).getProductImage();
        int rating=myOrderItemModelList.get(position).getRating();
        String  title=myOrderItemModelList.get(position).getProductTitle();
        String  deliveryStats=myOrderItemModelList.get(position).getDeliveryStatas();

        holder.setData(resource,title,deliveryStats,rating);

    }

    @Override
    public int getItemCount() {
        return myOrderItemModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView productImg,deliveryIndicator;
        TextView productTitle,productDeliveryDate;
        ///rating layout by star
        LinearLayout rateNowLayout;

        public ViewHolder(@NonNull final View itemView) {
            super(itemView);

            productImg=itemView.findViewById(R.id.productImgId);
            deliveryIndicator=itemView.findViewById(R.id.order_Indicator_id);
            productTitle=itemView.findViewById(R.id.productTitleId);
            productDeliveryDate=itemView.findViewById(R.id.order_delivery_time_id);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent orderDetailsIntent=new Intent(itemView.getContext(),OrderDetailsActivity.class);
                    itemView.getContext().startActivities(new Intent[]{orderDetailsIntent});

                }
            });
        }

        private void setData(int resource,String title,String deliveryStats,int rating)
        {
            productImg.setImageResource(resource);
            productTitle.setText(title);

            if (deliveryStats.equals("cancelled"))
            {
                deliveryIndicator.setImageTintList(ColorStateList.valueOf(itemView.getContext().getResources().getColor(R.color.colorPrimary)));
            }else {
                deliveryIndicator.setImageTintList(ColorStateList.valueOf(itemView.getContext().getResources().getColor(R.color.green)));
            }
            productDeliveryDate.setText(deliveryStats);
            //rating now Layout
            rateNowLayout=itemView.findViewById(R.id.rateNowStarContainerId);
//            if user pre set rating
            setRating(rating);
//            if user pre set rating

            for (int x=0;x<rateNowLayout.getChildCount();x++)
            {
                final int starPosition=x;
                rateNowLayout.getChildAt(x).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        setRating(starPosition);
                    }
                });
            }
            //rating now Layout

        }
        //rating now Layout function
        private void setRating(int starPosition) {
            for (int x=0;x<rateNowLayout.getChildCount();x++)
            {
                ImageView startBtn=(ImageView) rateNowLayout.getChildAt(x);
                startBtn.setImageTintList(ColorStateList.valueOf(Color.parseColor("#BFBDBE")));
                if (x<=starPosition)
                {
                    startBtn.setImageTintList(ColorStateList.valueOf(Color.parseColor("#ffbb00")));
                }
            }
        }
        //rating now Layout
    }
}
