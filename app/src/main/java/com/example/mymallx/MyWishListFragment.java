package com.example.mymallx;


import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import static com.example.mymallx.MainActivity.MY_WISH_LIST_FRAGMENT;
import static com.example.mymallx.MainActivity.currentFragment;


/**
 * A simple {@link Fragment} subclass.
 */
public class MyWishListFragment extends Fragment {

    RecyclerView recyclerView;
    Dialog loadingDialog;
    public static WishListAdapter wishListAdapter;
    public MyWishListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        currentFragment=MY_WISH_LIST_FRAGMENT;
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_my_wish_list, container, false);

        recyclerView=view.findViewById(R.id.wishListRecyclerViewId);
        LinearLayoutManager layoutManager=new LinearLayoutManager(getContext());
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        //loading dialog
        loadingDialog = new Dialog(getContext());
        loadingDialog.setContentView(R.layout.loading_progress_dilog);
        loadingDialog.setCancelable(false);
        loadingDialog.getWindow().setBackgroundDrawable(getContext().getDrawable(R.drawable.slider_background));
        loadingDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        loadingDialog.show();
        //end dialog

//        List<WishListModel>wishListModelList=new ArrayList<>();
//        wishListModelList.add(new WishListModel(R.mipmap.mobile,"Google Pixel XL 2 (Mirror Black,128 GB)",0,"2",23,"Rs.674/-","Rs.99999/-","Cash On Delivery Available"));
//        wishListModelList.add(new WishListModel(R.mipmap.mobile,"Google Pixel XL 3 (Mirror Black,128 GB)",2,"2",23,"Rs.674/-","Rs.99999/-","Cash On Delivery Available"));
//        wishListModelList.add(new WishListModel(R.mipmap.mobile,"Google Pixel XL 4 (Mirror Black,128 GB)",0,"2",23,"Rs.674/-","Rs.99999/-","Cash On Delivery Available"));
//        wishListModelList.add(new WishListModel(R.mipmap.mobile,"Google Pixel XL 5 (Mirror Black,128 GB)",2,"2",23,"Rs.674/-","Rs.99999/-","Cash On Delivery Available"));

        if (DBqueries.wishListModelList.size()==0)
        {
            DBqueries.wishList.clear();
            DBqueries.loadWishList(getContext(),loadingDialog,true);
        }else
        {
            loadingDialog.dismiss();
        }

        wishListAdapter=new WishListAdapter(DBqueries.wishListModelList,true);
        recyclerView.setAdapter(wishListAdapter);
        wishListAdapter.notifyDataSetChanged();

        return view;
    }

}
