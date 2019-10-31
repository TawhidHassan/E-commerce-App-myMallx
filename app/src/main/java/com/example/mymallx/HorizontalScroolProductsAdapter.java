package com.example.mymallx;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

public class HorizontalScroolProductsAdapter extends RecyclerView.Adapter<HorizontalScroolProductsAdapter.ViewHolder> {
    private List<HorizontalScroolProductsModel> horizontalScroolProductsModelList;

    public HorizontalScroolProductsAdapter(List<HorizontalScroolProductsModel> horizontalScroolProductsModelList) {
        this.horizontalScroolProductsModelList = horizontalScroolProductsModelList;
    }

    @NonNull
    @Override
    public HorizontalScroolProductsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.horizontal_scrool_item_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HorizontalScroolProductsAdapter.ViewHolder holder, int position) {
        String resource=horizontalScroolProductsModelList.get(position).getProductImag();
        String name=horizontalScroolProductsModelList.get(position).getProductName();
        String description=horizontalScroolProductsModelList.get(position).getProductDescription();
        String price=horizontalScroolProductsModelList.get(position).getProductPrice();
        String productId=horizontalScroolProductsModelList.get(position).getProductId();

        holder.setProdutImage(resource);
        holder.setProdutName(name,productId);
        holder.setProdutDescription(description);
        holder.setProdutPrice(price);


    }

    @Override
    public int getItemCount() {
        if (horizontalScroolProductsModelList.size()>8)
        {
            return 8;

        }else {
            return horizontalScroolProductsModelList.size();
        }

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView produtImage;
        private TextView produtName;
        private TextView produtDescription;
        private TextView produtPrice;

        public ViewHolder(@NonNull final View itemView) {
            super(itemView);
            produtImage=itemView.findViewById(R.id.horizontalProductImagId);
            produtName=itemView.findViewById(R.id.horizontalProductNameId);
            produtDescription=itemView.findViewById(R.id.horizontalProductDescriptionId);
            produtPrice=itemView.findViewById(R.id.horizontalProducPriceId);


        }

        private void setProdutImage(String  resource)
        {
            Glide.with(itemView.getContext()).load(resource).apply(new RequestOptions().placeholder(R.drawable.placeholder)).into(produtImage);
        }
        private void setProdutName(String x, final String productId)
        {
            produtName.setText(x);

            if (!x.equals(""))
            {
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent productDetailsIntent=new Intent(itemView.getContext(),ProductDetailsActivity.class);
                        productDetailsIntent.putExtra("ptoductId",productId);
                        itemView.getContext().startActivity(productDetailsIntent);
                    }
                });
            }

        }
        private void setProdutDescription(String description)
        {
            produtDescription.setText(description);
        }
        private void setProdutPrice(String price)
        {
            produtPrice.setText("Tk."+price+"/-");
        }


    }
}
