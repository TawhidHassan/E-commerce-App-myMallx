package com.example.mymallx;

import android.app.Dialog;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

public class CartAddapter extends RecyclerView.Adapter {

    List<CartItemModel> cartItemModelList;
    private int lastposition=-1;
    private TextView total_Cart_Amount;
    public CartAddapter(List<CartItemModel> cartItemModelList,TextView total_Cart_Amount) {
        this.cartItemModelList = cartItemModelList;
        this.total_Cart_Amount=total_Cart_Amount;

    }

    @Override
    public int getItemViewType(int position) {
        switch (cartItemModelList.get(position).getType()) {
            case 0:
                return CartItemModel.CART_ITEM;
            case 1:
                return CartItemModel.CART_TOTAL_AMOUNT;
            default:
                return -1;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case CartItemModel.CART_ITEM:
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_item_layout, parent, false);
                return new cartItemViewHolder(view);
            case CartItemModel.CART_TOTAL_AMOUNT:
                View viewx = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_total_amount_layout, parent, false);
                return new cartTotalAmount(viewx);
            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        switch (cartItemModelList.get(position).getType()) {
            case CartItemModel.CART_ITEM:
                String productId=cartItemModelList.get(position).getProductId();
                String resource=cartItemModelList.get(position).getProductImage();
                String title=cartItemModelList.get(position).getProductTitle();
                Long freeCoupen=cartItemModelList.get(position).getFreeCoupen();
                String price=cartItemModelList.get(position).getProductPrice();
                String cutedPrice=cartItemModelList.get(position).getProductCutedPrice();
                Long offerapplied=cartItemModelList.get(position).getProductOfferApplyed();
                Long coupenApplied=cartItemModelList.get(position).getProductCoupenApplyed();
                boolean inStock=cartItemModelList.get(position).isInStock();
                ((cartItemViewHolder)holder).setCartItemDetails(productId,resource,title,freeCoupen,price,cutedPrice,offerapplied,position,inStock);
                break;
            case CartItemModel.CART_TOTAL_AMOUNT:
                    int totalItems=0;
                    int totalItemsPrice=0;
                String deliveryPrice;
                int totalAmount;
                int savedAmount=0;
                for(int x=0;x<cartItemModelList.size();x++)
                {
                    if (cartItemModelList.get(x).getType()==CartItemModel.CART_ITEM&& cartItemModelList.get(x).isInStock())
                    {
                        totalItems++;
                        totalItemsPrice=totalItemsPrice+Integer.parseInt(cartItemModelList.get(x).getProductPrice());

                    }
                }
                if (totalItemsPrice>500)
                {
                    deliveryPrice="Free";
                    totalAmount= totalItemsPrice;
                }else
                {
                    deliveryPrice="60";
                    totalAmount= totalItemsPrice+60;

                }

//                String totalItems=cartItemModelList.get(position).getTotalitems();
//                String  totalItemsPrice=cartItemModelList.get(position).getTotalItemPrice();
//                String totalAmount=cartItemModelList.get(position).getTotalAmount();
//                String deliveryPrice=cartItemModelList.get(position).getDeliveryPrice();
//                String savedAmount=cartItemModelList.get(position).getSavedAmmount();

                ((cartTotalAmount)holder).setCartTotalAmount(totalItems,totalItemsPrice,deliveryPrice,totalAmount,savedAmount);
                break;
            default:
                return;
        }
        if (lastposition<position)
        {
            Animation animation= AnimationUtils.loadAnimation(holder.itemView.getContext(),R.anim.fade_in);
            holder.itemView.setAnimation(animation);
            lastposition=position;
        }
    }

    @Override
    public int getItemCount() {
        return cartItemModelList.size();
    }

    class cartItemViewHolder extends RecyclerView.ViewHolder {
        ImageView productImag;
        ImageView coupenIcon;
        TextView productTitle;
        TextView productPrice;
        TextView productCutedPrice;
        TextView productQuntity;
        TextView freeCoupen;
        TextView coupenAbilable;
        TextView offerAvailable;
        LinearLayout removeItemBtn;
        LinearLayout coupenRedemLayout;



        public cartItemViewHolder(@NonNull View itemView) {
            super(itemView);
            productImag = itemView.findViewById(R.id.productImagId);
            coupenIcon = itemView.findViewById(R.id.freeCuponIconId);
            productTitle = itemView.findViewById(R.id.productTitleId);
            productPrice = itemView.findViewById(R.id.prioductPriceId);
            productCutedPrice = itemView.findViewById(R.id.produCtcutedPriceId);
            productQuntity = itemView.findViewById(R.id.productQuntityTextId);
            freeCoupen = itemView.findViewById(R.id.textViewFreeCoupenId);
            coupenAbilable = itemView.findViewById(R.id.coupenApplidId21);
            offerAvailable = itemView.findViewById(R.id.offerAppliedId);
            removeItemBtn = itemView.findViewById(R.id.removeItemBtnId);
            coupenRedemLayout = itemView.findViewById(R.id.coupenRedemLayoutId);
        }

        private void setCartItemDetails(String productId, String  resource, String title, Long freeCoupentNumber, String price, String cutPrice, Long offersAppliedNumber, final int position,boolean inStock) {
            Glide.with(itemView.getContext()).load(resource).apply(new RequestOptions().placeholder(R.drawable.postplaceholder)).into(productImag);
            productTitle.setText(title);



            if (inStock)
            {
                productPrice.setText("TK."+price+"/-");
                productPrice.setTextColor(Color.parseColor("#000000"));
                productCutedPrice.setText("Tk."+cutPrice+"/-");
                coupenRedemLayout.setVisibility(View.VISIBLE);

                /**if product in stocck then run
                 *
                 */
                productQuntity.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final Dialog quntityDialog=new Dialog(itemView.getContext());
                        quntityDialog.setContentView(R.layout.quantity_dialog);
                        quntityDialog.setCancelable(false);
                        quntityDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);

                        final EditText quantityNumber=quntityDialog.findViewById(R.id.quantityId);
                        Button okBtn=quntityDialog.findViewById(R.id.dilogOkpBtnId);
                        Button canceBtn=quntityDialog.findViewById(R.id.dilogCancelBtnId);

                        okBtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                productQuntity.setText("Qty: "+quantityNumber.getText());
                                quntityDialog.dismiss();
                            }
                        });
                        canceBtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                quntityDialog.dismiss();
                            }
                        });
                        quntityDialog.show();
                    }
                });

                /**
                 * offer apply or number of offer
                 */
                if (offersAppliedNumber > 0) {
                    offerAvailable.setVisibility(View.VISIBLE);
                    offerAvailable.setText(offersAppliedNumber + " Offer applied");
                } else {
                    offerAvailable.setVisibility(View.INVISIBLE);
                }

                /**
                 * free coupen
                 */
                if (freeCoupentNumber > 0) {
                    coupenIcon.setVisibility(View.VISIBLE);
                    freeCoupen.setVisibility(View.VISIBLE);
                    if (freeCoupentNumber == 1) {
                        freeCoupen.setText("Free " + freeCoupentNumber + " coupen");
                    } else {
                        freeCoupen.setText("Free " + freeCoupentNumber + " coupens");
                    }

                } else {
                    coupenIcon.setVisibility(View.INVISIBLE);
                    freeCoupen.setVisibility(View.INVISIBLE);
                }

            }else
            {
                productPrice.setText("OUT OF STOCk");
                productPrice.setTextColor(itemView.getContext().getResources().getColor(R.color.colorPrimary));
                productCutedPrice.setText("");
                coupenRedemLayout.setVisibility(View.GONE);
                productQuntity.setText("Qty: "+0);
                productQuntity.setTextColor(Color.parseColor("#70000000"));
                productQuntity.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#70000000")));
                freeCoupen.setVisibility(View.INVISIBLE);
                coupenIcon.setVisibility(View.INVISIBLE);
                offerAvailable.setVisibility(View.INVISIBLE);

            }


            removeItemBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (!ProductDetailsActivity.running_cart_query)
                    {
                        ProductDetailsActivity.running_cart_query=true;
                        DBqueries.removeFromeCart(position,itemView.getContext());
                    }
                }
            });
        }
    }


    class cartTotalAmount extends RecyclerView.ViewHolder {

        TextView totalItems;
        TextView totalItemPrice;
        TextView totalAmount;
        TextView deliveryPrice;
        TextView savedAmount;

        public cartTotalAmount(@NonNull View itemView) {
            super(itemView);
            totalItems = itemView.findViewById(R.id.total_items_Id);
            totalItemPrice = itemView.findViewById(R.id.total_price_id);
            totalAmount = itemView.findViewById(R.id.total_price_id);
            deliveryPrice = itemView.findViewById(R.id.delivery_charge_price_id);
            savedAmount = itemView.findViewById(R.id.saved_amount_id);
        }

        private void setCartTotalAmount(int totalItemText, int totalItemPriceText, String  deliveryPriceText, int TotalAmountPriceText, int savedAmountText) {
            totalItems.setText("price("+totalItemText+" items)");
            totalItemPrice.setText("Tk."+totalItemPriceText+"/-");
            totalAmount.setText("Tk."+TotalAmountPriceText+"/-");
            if (deliveryPriceText.equals("Free"))
            {
                deliveryPrice.setText(deliveryPriceText);
            }else
            {
                deliveryPrice.setText("Tk."+deliveryPriceText+"/-");
            }
            savedAmount.setText("you saved Tk."+savedAmountText+"/- on this order");

            total_Cart_Amount.setText("Tk."+TotalAmountPriceText+"/-");
            if (totalItemPriceText==0)
            {
                DBqueries.cartItemModelList.remove(DBqueries.cartItemModelList.size()-1);

            }


        }
    }

}


