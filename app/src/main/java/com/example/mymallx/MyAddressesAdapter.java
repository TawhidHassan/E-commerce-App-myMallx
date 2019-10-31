package com.example.mymallx;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import static com.example.mymallx.DeliveryActivity.SELECT_ADDRESS;
import static com.example.mymallx.MyAccountFragment.MANAGE_ADDRESS;
import static com.example.mymallx.MyAddressActivity.refreceItem;

public class MyAddressesAdapter extends RecyclerView.Adapter<MyAddressesAdapter.ViewHolder> {
    List<MyAddessModel>myAddessModelList;
    private int MODE;
    private int pre_selected_position;

    public MyAddressesAdapter(List<MyAddessModel> myAddessModelList,int mode) {
        this.myAddessModelList = myAddessModelList;
        this.MODE = mode;
        pre_selected_position=DBqueries.selcetedAddress;
    }

    @NonNull
    @Override
    public MyAddressesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.my_address_item_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAddressesAdapter.ViewHolder holder, int position) {

        String name=myAddessModelList.get(position).getFullName();
        String add=myAddessModelList.get(position).getAddress();
        String pine=myAddessModelList.get(position).getPincode();
        String mobileNo=myAddessModelList.get(position).getMobileNo();
        Boolean selected=myAddessModelList.get(position).getSelected();
        holder.setData(name,add,pine,selected,position,mobileNo);
    }

    @Override
    public int getItemCount() {
        return myAddessModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

       private TextView address;
        private TextView fullname;
        private TextView pincode;
        private ImageView my_address_checkIcon;
        private LinearLayout my_address_Btn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            fullname=itemView.findViewById(R.id.my_address_fullNam_id);
            address=itemView.findViewById(R.id.my_address_fullAddress_id);
            pincode=itemView.findViewById(R.id.my_address_pincode_id);
            my_address_checkIcon=itemView.findViewById(R.id.my_address_checkIcon_id);
            my_address_Btn=itemView.findViewById(R.id.my_address_Btn_id);


            my_address_Btn.setVisibility(View.GONE);
        }

        private void setData(String nam, String useaddress, String usepincode, final Boolean selected, final int position,String mobileNo)
        {
            fullname.setText(nam+"-"+mobileNo);
            address.setText(useaddress);
            pincode.setText(usepincode);

            //for check user come which fragment or activity delivery/my profile
            if (MODE==SELECT_ADDRESS)
            {

                my_address_checkIcon.setImageResource(R.mipmap.tik);

                if (selected)
                {
                    my_address_checkIcon.setVisibility(View.VISIBLE);
                    pre_selected_position=position;

                }else
                {
                    my_address_checkIcon.setVisibility(View.GONE);
                }

                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        //if user sledted on time run this code
                        if (pre_selected_position!=position)
                        {
                            myAddessModelList.get(position).setSelected(true);
                            //if uder item selected
                            myAddessModelList.get(pre_selected_position).setSelected(false);

                            //for refrec item data
                            refreceItem(pre_selected_position,position);

                            //selected item set icon and pre selected item remove icon
                            pre_selected_position=position;
                            DBqueries.selcetedAddress=position;

                        }

                    }
                });


            }else if (MODE==MANAGE_ADDRESS)
            {
                my_address_Btn.setVisibility(View.GONE);
                my_address_checkIcon.setImageResource(R.mipmap.plus);
                my_address_checkIcon.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        my_address_Btn.setVisibility(View.VISIBLE);
                        //when sleect on item edit or remove the hole liniyar layout gone
                        refreceItem(pre_selected_position,pre_selected_position);
                        pre_selected_position=position;
                    }
                });

                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        refreceItem(pre_selected_position,pre_selected_position);
                        pre_selected_position=-1;

                    }
                });
            }
            //for check user come which fragment or activity delivery/my profile


        }
    }
}
