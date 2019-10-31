package com.example.mymallx;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MyRewardAdapter extends RecyclerView.Adapter<MyRewardAdapter.ViewHolder> {

    List<RewardModel>rewardModelList;

    //use that layou in small size in coupen redem dialog box
    private Boolean useMiniLayout=false;

    public MyRewardAdapter(List<RewardModel> rewardModelList,Boolean useMiniLayout) {
        this.rewardModelList = rewardModelList;
        this.useMiniLayout = useMiniLayout;
    }

    @NonNull
    @Override
    public MyRewardAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if (useMiniLayout)
        {
            //use that layou in small size in coupen redem dialog box
            View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.mini_reward_item_lyaout,parent,false);
            return new ViewHolder(view);
        }
        else {
            View viewx= LayoutInflater.from(parent.getContext()).inflate(R.layout.reward_item_layout,parent,false);
            return new ViewHolder(viewx);
        }

    }

    @Override
    public void onBindViewHolder(@NonNull MyRewardAdapter.ViewHolder holder, int position) {

        String title=rewardModelList.get(position).getTitle();
        String expire=rewardModelList.get(position).getExpirDate();
        String details=rewardModelList.get(position).getCoupenBody();

        holder.setData(title,details,expire);


    }

    @Override
    public int getItemCount() {
        return rewardModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView coupentTitle,coupenDescription,cuopenExpireDate;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            coupentTitle=itemView.findViewById(R.id.reward_coupen_title_id);
            cuopenExpireDate=itemView.findViewById(R.id.rewars_expir_date_id);
            coupenDescription=itemView.findViewById(R.id.cuopen_details_id);
        }

        private void setData(final String title, final String description, final String expireDat)
        {
            coupentTitle.setText(title);
            cuopenExpireDate.setText(expireDat);
            coupenDescription.setText(description);

            if (useMiniLayout)
            {
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ProductDetailsActivity.coupenTitle.setText(title);
                        ProductDetailsActivity.coupenBody.setText(description);
                        ProductDetailsActivity.coupenExpireDate.setText(expireDat);
                        ProductDetailsActivity.morecoupenShow();

                    }
                });
            }
        }
    }

}
