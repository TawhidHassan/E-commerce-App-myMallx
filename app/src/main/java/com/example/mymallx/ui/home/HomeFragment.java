package com.example.mymallx.ui.home;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bumptech.glide.Glide;
import com.example.mymallx.CategoryAdapter;
import com.example.mymallx.CategoryModel;
import com.example.mymallx.HomePageAdapter;
import com.example.mymallx.HomePageModel;
import com.example.mymallx.HorizontalScroolProductsModel;
import com.example.mymallx.MainActivity;
import com.example.mymallx.R;
import com.example.mymallx.SliderModel;
import com.example.mymallx.WishListModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import static com.example.mymallx.DBqueries.categoryModelList;
import static com.example.mymallx.DBqueries.lists;
import static com.example.mymallx.DBqueries.loadCategories;
import static com.example.mymallx.DBqueries.loadFragmentData;
import static com.example.mymallx.DBqueries.loadedCategoryNames;
import static com.example.mymallx.MainActivity.HOME_FRAGMENT;
import static com.example.mymallx.MainActivity.currentFragment;
import static com.example.mymallx.MainActivity.drawer;

public class HomeFragment extends Fragment {


    private HomeViewModel homeViewModel;
    //--------------------------------------------------{
    //category part start
    private RecyclerView categoryRecyclerView;
    private CategoryAdapter categoryAdapter;

    //category part end
    RecyclerView homePageRecyclerView;

    ImageView imageViewnoInternetConection;

    HomePageAdapter adapter;

    //for refres the page by swipe
   public static SwipeRefreshLayout swipeRefreshLayout;

    //for internet connection check
    ConnectivityManager connMgr;
    NetworkInfo networkInfo;

    //when load the data the fack list placeholder show
    private List<CategoryModel>categoryModelListFack=new ArrayList<>();
    private List<HomePageModel>homePageModelListFack=new ArrayList<>();

    //refresh btn
    Button refreshBtn;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, final Bundle savedInstanceState) {
        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        currentFragment = HOME_FRAGMENT;

        //fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);//*********************************************************************************************************

        swipeRefreshLayout=view.findViewById(R.id.refreshLatoutId);

        imageViewnoInternetConection=view.findViewById(R.id.noInternetConectionId);
        imageViewnoInternetConection.setVisibility(View.GONE);

        refreshBtn=view.findViewById(R.id.refreshButnId);

        //category part start
        categoryRecyclerView = view.findViewById(R.id.category_recyclerViewId);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        categoryRecyclerView.setLayoutManager(layoutManager);

        //fack data show when data are not come from firebase
        categoryModelListFack.add(new CategoryModel("null",""));
        categoryModelListFack.add(new CategoryModel("",""));
        categoryModelListFack.add(new CategoryModel("",""));
        categoryModelListFack.add(new CategoryModel("",""));
        categoryModelListFack.add(new CategoryModel("",""));
        categoryModelListFack.add(new CategoryModel("",""));
        categoryModelListFack.add(new CategoryModel("",""));
        categoryModelListFack.add(new CategoryModel("",""));
        categoryModelListFack.add(new CategoryModel("",""));
        categoryModelListFack.add(new CategoryModel("",""));




        //fack home page data show when data are not come from firebase
        List<SliderModel>sliderModelListFack=new ArrayList<>();
        sliderModelListFack.add(new SliderModel("null","#dfdfdf"));
        sliderModelListFack.add(new SliderModel("null","#dfdfdf"));
        sliderModelListFack.add(new SliderModel("null","#dfdfdf"));
        sliderModelListFack.add(new SliderModel("null","#dfdfdf"));
        sliderModelListFack.add(new SliderModel("null","#dfdfdf"));
        sliderModelListFack.add(new SliderModel("null","#dfdfdf"));



        List<HorizontalScroolProductsModel> horizontalScroolProductsModelListfack = new ArrayList<>();
        horizontalScroolProductsModelListfack.add(new HorizontalScroolProductsModel("","","","",""));
        horizontalScroolProductsModelListfack.add(new HorizontalScroolProductsModel("","","","",""));
        horizontalScroolProductsModelListfack.add(new HorizontalScroolProductsModel("","","","",""));
        horizontalScroolProductsModelListfack.add(new HorizontalScroolProductsModel("","","","",""));
        horizontalScroolProductsModelListfack.add(new HorizontalScroolProductsModel("","","","",""));
        horizontalScroolProductsModelListfack.add(new HorizontalScroolProductsModel("","","","",""));
        horizontalScroolProductsModelListfack.add(new HorizontalScroolProductsModel("","","","",""));


        homePageModelListFack.add(new HomePageModel(0,sliderModelListFack));
        homePageModelListFack.add(new HomePageModel(1,"","#dfdfdf"));
        homePageModelListFack.add(new HomePageModel(2,"","#dfdfdf",horizontalScroolProductsModelListfack,new ArrayList<WishListModel>()));
        homePageModelListFack.add(new HomePageModel(3,"","#dfdfdf",horizontalScroolProductsModelListfack));
        ////////////Home Page Recycler
        homePageRecyclerView = view.findViewById(R.id.homePageRecyclerViewId);
        LinearLayoutManager testinglinearLayoutManager = new LinearLayoutManager(view.getContext());
        testinglinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        homePageRecyclerView.setLayoutManager(testinglinearLayoutManager);
        //fack home page data show when data are not come from firebase

        //data pull into the fiarbase
        categoryAdapter = new CategoryAdapter(categoryModelListFack);

        adapter = new HomePageAdapter(homePageModelListFack);



        connMgr = (ConnectivityManager) getActivity()
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
            //--------------------------------------------------------{
            imageViewnoInternetConection.setVisibility(View.GONE);
            refreshBtn.setVisibility(View.GONE);

            if(categoryModelList.size()==0)
            {
                loadCategories(categoryRecyclerView,getContext());
            }else
            {
                categoryAdapter=new CategoryAdapter(categoryModelList);
                categoryAdapter.notifyDataSetChanged();
            }
            categoryRecyclerView.setAdapter(categoryAdapter);



            //------------------------------------------------
            //category part end
//--------------------------------------------------------}
//*********************************************************************************************************
//--------------------------------------------------------{
            //baner slider start

            //baner slider end
//--------------------------------------------------------}
//*********************************************************************************************************

//*********************************************************************************************************
// -------------------------------------------------------{
//        final List<HorizontalScroolProductsModel> horizontalScroolProductsModelList = new ArrayList<>();
//        horizontalScroolProductsModelList.add(new HorizontalScroolProductsModel(R.mipmap.mobile,"redmi 5","SD processor","Rs.5999"));
//        horizontalScroolProductsModelList.add(new HorizontalScroolProductsModel(R.mipmap.mobile,"redmi 7","KK processor","Rs.9999"));
//        horizontalScroolProductsModelList.add(new HorizontalScroolProductsModel(R.mipmap.mobile,"redmi 7pro","Sk processor","Rs.74894"));
//        horizontalScroolProductsModelList.add(new HorizontalScroolProductsModel(R.mipmap.mobile,"redmi x","PP processor","Rs.83839"));
//        horizontalScroolProductsModelList.add(new HorizontalScroolProductsModel(R.mipmap.mobile,"redmi 5","PPk processor","Rs.8389939"));
//        horizontalScroolProductsModelList.add(new HorizontalScroolProductsModel(R.mipmap.mobile,"redmi Alite","PXP processor","Rs.8399"));
//        horizontalScroolProductsModelList.add(new HorizontalScroolProductsModel(R.mipmap.mobile,"redmi 7pro","Sk processor","Rs.74894"));
//        horizontalScroolProductsModelList.add(new HorizontalScroolProductsModel(R.mipmap.mobile,"redmi x","PP processor","Rs.83839"));
//        horizontalScroolProductsModelList.add(new HorizontalScroolProductsModel(R.mipmap.mobile,"redmi x","PP processor","Rs.83839"));

            //horizontal scrool products layout end
//-------------------------------------------------------}
// *********************************************************************************************************

//        final List<HomePageModel> homePageModelList = new ArrayList<>();

            //scrool banner add
//        homePageModelList.add(new HomePageModel(0,sliderModelList));
            //strip  banner add
//        homePageModelList.add(new HomePageModel(1,R.mipmap.banner,"#000000"));
            //horizontal products
//        homePageModelList.add(new HomePageModel(2, "Deals Of the day", horizontalScroolProductsModelList));
            //GridView products
//        homePageModelList.add(new HomePageModel(3, "Deals Of the day", horizontalScroolProductsModelList));



            //data pull into the fiarbase
            if(lists.size()==0)
            {
                loadedCategoryNames.add("HOME");
                lists.add(new ArrayList<HomePageModel>());
                loadFragmentData(homePageRecyclerView,getContext(),0,"HOME");
            }else
            {
                adapter = new HomePageAdapter(lists.get(0));
                adapter.notifyDataSetChanged();
            }
            homePageRecyclerView.setAdapter(adapter);

        } else {

            drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
            imageViewnoInternetConection.setVisibility(View.VISIBLE);
            refreshBtn.setVisibility(View.VISIBLE);
            Glide.with(this).load(R.drawable.nointernet).into(imageViewnoInternetConection);

        }


        //swipe for refresh the page
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(true);
                refreshpage();

            }
        });

        refreshBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refreshpage();
            }
        });
        return view;
    }

    //refreshing the home page
    private void refreshpage()
    {
        swipeRefreshLayout.setColorSchemeColors(getContext().getResources().getColor(R.color.colorPrimary),getContext().getResources().getColor(R.color.colorPrimary),getContext().getResources().getColor(R.color.colorPrimary));
        networkInfo = connMgr.getActiveNetworkInfo();
        categoryModelList.clear();
        lists.clear();
        loadedCategoryNames.clear();

        connMgr = (ConnectivityManager) getActivity()
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        networkInfo = connMgr.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {
            drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
            categoryRecyclerView.setVisibility(View.VISIBLE);
            homePageRecyclerView.setVisibility(View.VISIBLE);
            imageViewnoInternetConection.setVisibility(View.GONE);
            refreshBtn.setVisibility(View.GONE);
            categoryRecyclerView.setAdapter(categoryAdapter);
            homePageRecyclerView.setAdapter(adapter);
            loadCategories(categoryRecyclerView,getContext());
            loadedCategoryNames.add("HOME");

            lists.add(new ArrayList<HomePageModel>());
            loadFragmentData(homePageRecyclerView,getContext(),0,"HOME");
            swipeRefreshLayout.setRefreshing(false);

        }else {
            drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
            categoryRecyclerView.setVisibility(View.GONE);
            homePageRecyclerView.setVisibility(View.GONE);
            imageViewnoInternetConection.setVisibility(View.VISIBLE);
            refreshBtn.setVisibility(View.VISIBLE);
            Glide.with(getContext()).load(R.drawable.nointernet).into(imageViewnoInternetConection);
            swipeRefreshLayout.setRefreshing(false);
        }
    }



}