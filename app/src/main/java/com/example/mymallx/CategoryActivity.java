package com.example.mymallx;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import static com.example.mymallx.DBqueries.lists;
import static com.example.mymallx.DBqueries.loadFragmentData;
import static com.example.mymallx.DBqueries.loadedCategoryNames;

public class CategoryActivity extends AppCompatActivity {
    //category part start
    private RecyclerView categoryRecyclerView;
    private CategoryAdapter categoryAdapter;

    //when load the data the fack list placeholder show
    private List<CategoryModel>categoryModelListFack=new ArrayList<>();
    private List<HomePageModel>homePageModelListFack=new ArrayList<>();

    HomePageAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        String catTitle=getIntent().getStringExtra("category_title");
        getSupportActionBar().setTitle(catTitle);

        //back arrow button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        categoryRecyclerView=findViewById(R.id.categoryRecyclerViewId);



        //fack home page data show when data are not come from firebase
        List<SliderModel>sliderModelListFack=new ArrayList<>();
        sliderModelListFack.add(new SliderModel("null","#ffffff"));
        sliderModelListFack.add(new SliderModel("null","#ffffff"));
        sliderModelListFack.add(new SliderModel("null","#ffffff"));
        sliderModelListFack.add(new SliderModel("null","#ffffff"));
        sliderModelListFack.add(new SliderModel("null","#ffffff"));


        List<HorizontalScroolProductsModel> horizontalScroolProductsModelListfack = new ArrayList<>();
        horizontalScroolProductsModelListfack.add(new HorizontalScroolProductsModel("","","","",""));
        horizontalScroolProductsModelListfack.add(new HorizontalScroolProductsModel("","","","",""));
        horizontalScroolProductsModelListfack.add(new HorizontalScroolProductsModel("","","","",""));
        horizontalScroolProductsModelListfack.add(new HorizontalScroolProductsModel("","","","",""));
        horizontalScroolProductsModelListfack.add(new HorizontalScroolProductsModel("","","","",""));
        horizontalScroolProductsModelListfack.add(new HorizontalScroolProductsModel("","","","",""));
        horizontalScroolProductsModelListfack.add(new HorizontalScroolProductsModel("","","","",""));


        homePageModelListFack.add(new HomePageModel(0,sliderModelListFack));
        homePageModelListFack.add(new HomePageModel(1,"","#ffffff"));
        homePageModelListFack.add(new HomePageModel(2,"","#ffffff",horizontalScroolProductsModelListfack,new ArrayList<WishListModel>()));
        homePageModelListFack.add(new HomePageModel(3,"","#ffffff",horizontalScroolProductsModelListfack));

        //fack home page data show when data are not come from firebase

//*********************************************************************************************************
//--------------------------------------------------------{
        //baner slider start

//        List<SliderModel> sliderModelList=new ArrayList<SliderModel>();
//
//        sliderModelList.add(new SliderModel(R.mipmap.ic_launcher_round,"#0075EE"));
//        sliderModelList.add(new SliderModel(R.mipmap.forgot_password_image,"#166F15"));
//        //currentpageBanner=2 (first banner)
//        sliderModelList.add(new SliderModel(R.mipmap.banner,"#ffffff"));
//        //currentpageBanner=2
//
//        sliderModelList.add(new SliderModel(R.mipmap.logo,"#166F15"));
//        sliderModelList.add(new SliderModel(R.mipmap.profile_placeholder,"#0075EE"));
//        sliderModelList.add(new SliderModel(R.mipmap.banner,"#166F15"));
//        sliderModelList.add(new SliderModel(R.mipmap.ic_launcher_round,"#0075EE"));
//
//        sliderModelList.add(new SliderModel(R.mipmap.forgot_password_image,"#0075EE"));
//        sliderModelList.add(new SliderModel(R.mipmap.banner,"#0075EE"));
//        sliderModelList.add(new SliderModel(R.mipmap.logo,"#0075EE"));

        //baner slider end
//--------------------------------------------------------}
//*********************************************************************************************************

//*********************************************************************************************************
// -------------------------------------------------------{
//        List<HorizontalScroolProductsModel> horizontalScroolProductsModelList=new ArrayList<>();
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

        ////////////Home Page Recycler
        LinearLayoutManager testinglinearLayoutManager=new LinearLayoutManager(this);
        testinglinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        categoryRecyclerView.setLayoutManager(testinglinearLayoutManager);
        adapter=new HomePageAdapter(homePageModelListFack);
        categoryRecyclerView.setAdapter(adapter);

//        List<HomePageModel> homePageModelList=new ArrayList<>();
//        //scrool banner add
//        homePageModelList.add(new HomePageModel(0,sliderModelList));
//        //strip  banner add
//        homePageModelList.add(new HomePageModel(1,R.mipmap.banner,"#000000"));
//        //horizontal products
//        homePageModelList.add(new HomePageModel(2, "Deals Of the day", horizontalScroolProductsModelList));
//        //GridView products
//        homePageModelList.add(new HomePageModel(3, "Deals Of the day", horizontalScroolProductsModelList));

        //firebase initialize

        int listPostion=0;
        for (int x=0;x<loadedCategoryNames.size();x++)
        {
            if (loadedCategoryNames.get(x).equals(catTitle.toUpperCase()))
            {
                listPostion=x;
            }
        }

        if (listPostion==0)
        {
            loadedCategoryNames.add(catTitle);
            lists.add(new ArrayList<HomePageModel>());
            loadFragmentData(categoryRecyclerView,this,loadedCategoryNames.size()-1,catTitle);
        }else
        {
            adapter = new HomePageAdapter(lists.get(listPostion));

        }
        
        adapter.notifyDataSetChanged();


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.search_icon, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id=item.getItemId();
        if (id==R.id.mainsearch_icon_id)
        {
            Intent searchIntent=new Intent(getApplicationContext(),SearchActivity.class);
            startActivity(searchIntent);
            return true;
        }else if (id==android.R.id.home)
        {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
