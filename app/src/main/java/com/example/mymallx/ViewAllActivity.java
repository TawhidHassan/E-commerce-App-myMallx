package com.example.mymallx;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.List;

public class ViewAllActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    GridView gridView;
    public static List<HorizontalScroolProductsModel> horizontalScroolProductsModelList;
    public static List<WishListModel> wishListModelList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle(getIntent().getStringExtra("layou_title"));
        //back arrow button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        recyclerView=findViewById(R.id.dealOfTheDay_recyclerView_id);
        gridView=findViewById(R.id.dealOfTheDay_GrideView_id);

        int layout_code=getIntent().getIntExtra("layout_code",-1);

        if (layout_code==0)
        {
            recyclerView.setVisibility(View.VISIBLE);
            LinearLayoutManager layoutManager=new LinearLayoutManager(this);
            layoutManager.setOrientation(RecyclerView.VERTICAL);
            recyclerView.setLayoutManager(layoutManager);

            WishListAdapter wishListAdapter=new WishListAdapter(wishListModelList,false);
            recyclerView.setAdapter(wishListAdapter);
            wishListAdapter.notifyDataSetChanged();

        }
        else if (layout_code==1){
//gride view start
            gridView.setVisibility(View.VISIBLE);

            GridProductLayoutAdapter gridProductLayoutAdapter=new GridProductLayoutAdapter(horizontalScroolProductsModelList);
            gridView.setAdapter(gridProductLayoutAdapter);

            //gride view end
        }





    }

    public boolean onOptionsItemSelected(MenuItem item) {

        int id=item.getItemId();
        if (id==android.R.id.home)
        {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
