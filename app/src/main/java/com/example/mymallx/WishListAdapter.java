package com.example.mymallx;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

public class WishListAdapter extends RecyclerView.Adapter<WishListAdapter.ViewHolder> {

    List<WishListModel>wishListModelList;

    //that use because we use that on another activity
    Boolean wishList;

    private int lastposition=-1;
    private boolean fromeSearch;

    public boolean isFromeSearch() {
        return fromeSearch;
    }

    public void setFromeSearch(boolean fromeSearch) {
        this.fromeSearch = fromeSearch;
    }

    public WishListAdapter(List<WishListModel> wishListModelList, Boolean wish) {
        this.wishListModelList = wishListModelList;
        this.wishList=wish;
    }

    public List<WishListModel> getWishListModelList() {
        return wishListModelList;
    }

    public void setWishListModelList(List<WishListModel> wishListModelList) {
        this.wishListModelList = wishListModelList;
    }

    @NonNull
    @Override
    public WishListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.wish_list_item_layout,parent,false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull WishListAdapter.ViewHolder holder, int position) {
         String  productId=wishListModelList.get(position).getProductId();
         String  productImg=wishListModelList.get(position).getProductImg();
         String productTitle=wishListModelList.get(position).getProductTitle();
         long freeCoupens=wishListModelList.get(position).getFreeCoupens();
         String rating=wishListModelList.get(position).getRating();
         String totalRating=wishListModelList.get(position).getTotalRating();
         String productPrice=wishListModelList.get(position).getProductPrice();
         String cuttedproductPrice=wishListModelList.get(position).getCuttedproductPrice();
         Boolean paymentMethods=wishListModelList.get(position).getCOD();

         holder.setData(productImg,productTitle,freeCoupens,rating,totalRating,productPrice,cuttedproductPrice,paymentMethods,productId);


        if (lastposition<position)
        {
            Animation animation= AnimationUtils.loadAnimation(holder.itemView.getContext(),R.anim.fade_in);
            holder.itemView.setAnimation(animation);
            lastposition=position;
        }
    }

    @Override
    public int getItemCount() {
        return wishListModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView productImg,coupenIcon;
        TextView productTitle,freeCoupen,rating,totalRating,productPrice,productCuttedPrice,paymentMethods;
        View priceCuted;
        ImageButton wishListDeleteBtn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            productImg=itemView.findViewById(R.id.wish_list_product_Img_id);
            productTitle=itemView.findViewById(R.id.wishLIst_ProductTItle_id);
            coupenIcon=itemView.findViewById(R.id.wishList_coupen_icon_id);
            freeCoupen=itemView.findViewById(R.id.WishListFreeCoupenTitle_Id);
            rating=itemView.findViewById(R.id.WishListproductRatingMiniViewId);
            totalRating=itemView.findViewById(R.id.wishList_product_total_Rating_id);
            productPrice=itemView.findViewById(R.id.wishList_product_price_id);
            productCuttedPrice=itemView.findViewById(R.id.wishList_product_cuted_price_id);
            paymentMethods=itemView.findViewById(R.id.wishList_delivery_method_text_id);
            priceCuted=itemView.findViewById(R.id.wishListpriceCutId);
            wishListDeleteBtn=itemView.findViewById(R.id.wishList_delete_icon_id);

        }

        private void setData(String  resource, String text, long freeCoupenNo, String rat, String  totalRat, String price, String cutPrice, Boolean paymethode, final String productId)
        {
            Glide.with(itemView.getContext()).load(resource).apply(new RequestOptions().placeholder(R.mipmap.home)).into(productImg);

            productTitle.setText(text);

            if (freeCoupenNo!=0)
            {
                coupenIcon.setVisibility(View.VISIBLE);
                if (freeCoupenNo==1)
                {
                    freeCoupen.setText("Free "+freeCoupenNo+" coupen");
                }else
                {
                    freeCoupen.setText("Free "+freeCoupenNo+" coupens");

                }
            }else {
                coupenIcon.setVisibility(View.INVISIBLE);
                freeCoupen.setVisibility(View.INVISIBLE);
            }

            rating.setText(rat);
            totalRating.setText(totalRat+" (rating)");
            productPrice.setText(price);
            productCuttedPrice.setText(cutPrice);
            if (paymethode)
            {
                paymentMethods.setVisibility(View.VISIBLE);

            }else {
                paymentMethods.setVisibility(View.GONE);
            }

            if (wishList)
            {
                wishListDeleteBtn.setVisibility(View.VISIBLE);
            }else
            {
                wishListDeleteBtn.setVisibility(View.GONE);
            }

            wishListDeleteBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(itemView.getContext(),"working btn",Toast.LENGTH_LONG).show();
                }
            });

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (fromeSearch)
                    {
                        ProductDetailsActivity.fromesearch=true;
                    }
                    Intent productDetailIntent=new Intent(itemView.getContext(),ProductDetailsActivity.class);
                    productDetailIntent.putExtra("ptoductId",productId);
                    itemView.getContext().startActivities(new Intent[]{productDetailIntent});
                }
            });
        }
    }
}
