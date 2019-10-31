package com.example.mymallx;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.gridlayout.widget.GridLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class HomePageAdapter extends RecyclerView.Adapter {
    private List<HomePageModel> homePageModelList;
    private RecyclerView.RecycledViewPool recycledViewPool;
    private int lastPosition=-1;

    public HomePageAdapter(List<HomePageModel> homePageModelList) {
        this.homePageModelList = homePageModelList;
        recycledViewPool=new RecyclerView.RecycledViewPool();
    }

    @Override
    public int getItemViewType(int position) {
        switch (homePageModelList.get(position).getType()) {
            case 0:
                return HomePageModel.BannerSlider;
            case 1:
                return HomePageModel.StripAddBaneer;
            case 2:
                return HomePageModel.HorizontalProductView;
            case 3:
                return HomePageModel.GridProductsView;
            default:
                return -1;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            //Scrool Baner Add
            case HomePageModel.BannerSlider:
                View banerSliderView = LayoutInflater.from(parent.getContext()).inflate(R.layout.sliding_add_banner_layout, parent, false);
                return new bannerSliderViewHolder(banerSliderView);
            //Strip Baneer Add
            case HomePageModel.StripAddBaneer:
                View StripAddView = LayoutInflater.from(parent.getContext()).inflate(R.layout.strip_add_layout, parent, false);
                return new stripBannerViewHolder(StripAddView);
            //for horizontal productView
            case HomePageModel.HorizontalProductView:
                View horizontalPorductView = LayoutInflater.from(parent.getContext()).inflate(R.layout.horizontal_scrool_products_layout, parent, false);
                return new horizontelProductViewHolder(horizontalPorductView);
            //for GridProducts View
            case HomePageModel.GridProductsView:
                View gridePorductView = LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_product_layout, parent, false);
                return new GridProductViewHolder(gridePorductView);
            default:
                return null;
        }


    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        switch (homePageModelList.get(position).getType()) {
            //scrool banner add
            case HomePageModel.BannerSlider:
                List<SliderModel> sliderModelList = homePageModelList.get(position).getSliderModelList();
                ((bannerSliderViewHolder) holder).setBanersliderViewPager(sliderModelList);
                break;

            //strip banner add
            case HomePageModel.StripAddBaneer:
                String  resource = homePageModelList.get(position).getImagResource();
                String color = homePageModelList.get(position).getBackgroundColor();
                ((stripBannerViewHolder) holder).setStripAdd(resource, color);
                break;

            //horizontal Product View
            case HomePageModel.HorizontalProductView:
                String colorx=homePageModelList.get(position).getBackgroundColor();
                String title = homePageModelList.get(position).getTitle();
                List<HorizontalScroolProductsModel> horizontalScroolProductsModelList = homePageModelList.get(position).getHorizontalScroolProductsModelList();
                List<WishListModel> viewAllProductsList = homePageModelList.get(position).getViewAllProductList();
                ((horizontelProductViewHolder) holder).setHorizontalLProductLayout(horizontalScroolProductsModelList, title,colorx,viewAllProductsList);
                break;
            //GrideProduct View
            case HomePageModel.GridProductsView:
                String colory=homePageModelList.get(position).getBackgroundColor();
                String titlex = homePageModelList.get(position).getTitle();
                List<HorizontalScroolProductsModel> horizontalScroolProductsModel = homePageModelList.get(position).getHorizontalScroolProductsModelList();
                ((GridProductViewHolder) holder).setGridViewLayout(horizontalScroolProductsModel, titlex,colory);
                break;

            default:
                return;
        }
        if (lastPosition<position)
        {
            Animation animation= AnimationUtils.loadAnimation(holder.itemView.getContext(),R.anim.fade_in);
            holder.itemView.setAnimation(animation);
            lastPosition=position;
        }

    }

    @Override
    public int getItemCount() {
        return homePageModelList.size();
    }

    //------------------------------------------------------------
    //for Banner Add
    public class bannerSliderViewHolder extends RecyclerView.ViewHolder {

        //view pagger
        private ViewPager banersliderViewPager;
        //banner looper for banner infanite time scroll
        int currentpageBanner;
        //auto change banner
        Timer timer;
        final private long delayTime = 3000;
        final private long periodTime = 3000;

        //for infinate scroll
        private List<SliderModel>arrangeList;

        //auto change banner
        //baner slider end
        public bannerSliderViewHolder(@NonNull View itemView) {
            super(itemView);
            banersliderViewPager = itemView.findViewById(R.id.banner_sliderViewPagerId);

        }

        //*********************************************************************************************************
        //function
        private void setBanersliderViewPager(final List<SliderModel> sliderModelList) {

            currentpageBanner=2;

//            when user scroll many time the home page item the time also increase.for the the slider slide spped also in crease.for stop that we implemnet if stetment
            if (timer!=null)
            {
                timer.cancel();
            }

            //for infinate scroll
            //and arrengeList copy all list from sliderModelList
            arrangeList=new ArrayList<>();
            for (int x=0; x<sliderModelList.size();x++)
            {
                arrangeList.add(x,sliderModelList.get(x));
            }

//            sliderModelList.add(new SliderModel(R.mipmap.ic_launcher_round,"#0075EE"));
//            sliderModelList.add(new SliderModel(R.mipmap.forgot_password_image,"#166F15"));
//            currentpageBanner=2 (first banner)
//            sliderModelList.add(new SliderModel(R.mipmap.banner,"#ffffff"));
//            currentpageBanner=2
//
//            sliderModelList.add(new SliderModel(R.mipmap.logo,"#166F15"));
//            sliderModelList.add(new SliderModel(R.mipmap.profile_placeholder,"#0075EE"));
//            sliderModelList.add(new SliderModel(R.mipmap.banner,"#166F15"));
//            sliderModelList.add(new SliderModel(R.mipmap.ic_launcher_round,"#0075EE"));
//
//            sliderModelList.add(new SliderModel(R.mipmap.forgot_password_image,"#0075EE"));
//            sliderModelList.add(new SliderModel(R.mipmap.banner,"#0075EE"));
//            sliderModelList.add(new SliderModel(R.mipmap.logo,"#0075EE"));
//
//            convet that list on arrangeList.add

            arrangeList.add(0,sliderModelList.get(sliderModelList.size()-2));
            arrangeList.add(1,sliderModelList.get(sliderModelList.size()-1));
            arrangeList.add(sliderModelList.get(0));
            arrangeList.add(sliderModelList.get(1));
//            convet that list on arrangeList.add

            SliderBanerAdapter sliderBanerAdapter = new SliderBanerAdapter(arrangeList);
            banersliderViewPager.setAdapter(sliderBanerAdapter);
            banersliderViewPager.setClipToPadding(false);
            banersliderViewPager.setPageMargin(20);
            banersliderViewPager.setCurrentItem(currentpageBanner);

            //implement infaniti scrool bannner custome function
            ViewPager.OnPageChangeListener onPageChangeListener = new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                }

                @Override
                public void onPageSelected(int position) {
                    currentpageBanner = position;
                }

                @Override
                public void onPageScrollStateChanged(int state) {
                    if (state == ViewPager.SCROLL_STATE_IDLE) {
                        pageLooper(arrangeList);
                    }
                }
            };
            banersliderViewPager.addOnPageChangeListener(onPageChangeListener);
            //implement infaniti scrool bannner custome function

            //banner slider auto change with animation
            stratBannerSlideShow(arrangeList);

            //when user touch the banner the auto animation function(stratBannerSlideShow) is stop and and the banner hold it
            banersliderViewPager.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    pageLooper(arrangeList);
                    stopBannerSlideShow();

//                if user hand remove on the banner the auto animation function(stratBannerSlideShow) start
                    if (event.getAction() == MotionEvent.ACTION_UP) {
                        stratBannerSlideShow(arrangeList);
                    }

                    return false;
                }
            });
            //baner slider end
        }

        //banner looper for banner infanite time scroll
        private void pageLooper(List<SliderModel> sliderModelList) {

//         that was chcek the user current banner and get back first banner
            if (currentpageBanner == sliderModelList.size() - 2) {
                currentpageBanner = 2;
                banersliderViewPager.setCurrentItem(currentpageBanner, false);
            }
//if user scrool back site
            if (currentpageBanner == 1) {
                currentpageBanner = sliderModelList.size() - 3;
                banersliderViewPager.setCurrentItem(currentpageBanner, false);
            }
        }

        //for banner Slider Auto change show animation
        private void stratBannerSlideShow(final List<SliderModel> sliderModelList) {
            final Handler handler = new Handler();
            final Runnable update = new Runnable() {
                @Override
                public void run() {
                    if (currentpageBanner >= sliderModelList.size()) {
                        currentpageBanner = 1;
                    }
                    banersliderViewPager.setCurrentItem(currentpageBanner++, true);
                }
            };
            timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    handler.post(update);
                }
            }, delayTime, periodTime);
        }

        //stop auto animation
        private void stopBannerSlideShow() {
            timer.cancel();
        }
        //for banner Slider Auto change show animation
        //banner looper for banner infanite time scroll
        //*********************************************************************************************************
    }

    //----------------------------------------------------------
    //for Strip Banner Add
    public class stripBannerViewHolder extends RecyclerView.ViewHolder {
        //Strip baner  add slider strat
        private ImageView stripBannerAddImage;
        private ConstraintLayout stripBannerAddContainer;

        public stripBannerViewHolder(@NonNull View itemView) {
            super(itemView);
            stripBannerAddImage = itemView.findViewById(R.id.strip_add_banner_img_Id);
            stripBannerAddContainer = itemView.findViewById(R.id.strip_banne_add_container_id);
        }

        //*********************************************************************************************************
        //function
        private void setStripAdd(String  resource, String color) {
            Glide.with(itemView.getContext()).load(resource).apply(new RequestOptions().placeholder(R.drawable.placeholder)).into(stripBannerAddImage);
            try {
                stripBannerAddContainer.setBackgroundColor(Color.parseColor(color));
            } catch (Exception e) {
                e.printStackTrace();
            }


        }
    }

    //-----------------------------------------------
    //horizontal scrool products layout start
    public class horizontelProductViewHolder extends RecyclerView.ViewHolder {

        private TextView horizontalLayoutTitle;
        private Button horizontalviewAllBtn;
        private RecyclerView horizontalRecyclerView;
        private ConstraintLayout horizontalLayout_container;

        public horizontelProductViewHolder(@NonNull View itemView) {
            super(itemView);
            horizontalLayoutTitle = itemView.findViewById(R.id.horizontalscroollayoutTitleId);
            horizontalviewAllBtn = itemView.findViewById(R.id.horizontalscroollayoutButtonId);
            horizontalRecyclerView = itemView.findViewById(R.id.horizontalscroollayoutRecyclerId);
            horizontalLayout_container = itemView.findViewById(R.id.horizontalLayout_container_id);

            horizontalRecyclerView.setRecycledViewPool(recycledViewPool);
            horizontalRecyclerView.setRecycledViewPool(recycledViewPool);
        }

        //*********************************************************************************************************
        //function
        public void setHorizontalLProductLayout(List<HorizontalScroolProductsModel> horizontalScroolProductsModelList, final String title, String color, final List<WishListModel>viewAllProductsList) {
            horizontalLayout_container.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(color)));
            horizontalLayoutTitle.setText(title);
            if (horizontalScroolProductsModelList.size() > 8) {
                horizontalviewAllBtn.setVisibility(View.VISIBLE);

                horizontalviewAllBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ViewAllActivity.wishListModelList=viewAllProductsList;
                        Intent horizontalViewAllIntent=new Intent(itemView.getContext(),ViewAllActivity.class);
                        horizontalViewAllIntent.putExtra("layout_code",0);
                        horizontalViewAllIntent.putExtra("layou_title",title);
                        itemView.getContext().startActivities(new Intent[]{horizontalViewAllIntent});
                    }
                });
            }
            HorizontalScroolProductsAdapter horizontalScroolProductsAdapter = new HorizontalScroolProductsAdapter(horizontalScroolProductsModelList);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(itemView.getContext());
            linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
            horizontalRecyclerView.setLayoutManager(linearLayoutManager);

            horizontalRecyclerView.setAdapter(horizontalScroolProductsAdapter);
            horizontalScroolProductsAdapter.notifyDataSetChanged();
        }

        //horizontal scrool products layout end
    }


    //-----------------------------------------------
    //gridProducts View  start
    public class GridProductViewHolder extends RecyclerView.ViewHolder {
        private TextView GridViewLayoutTitle;
        private Button GridViewAllViewBtn;
        private GridView gridViewLayout;
        private ConstraintLayout grideLayoutContainer;
        public GridProductViewHolder(@NonNull View itemView) {
            super(itemView);
            //Grid View products layout start
            GridViewLayoutTitle = itemView.findViewById(R.id.gridProductLayoutTitleid);
            GridViewAllViewBtn = itemView.findViewById(R.id.gridProductLayoutButtonid);
            gridViewLayout = itemView.findViewById(R.id.gridProductLayoutGirdViewid);
            grideLayoutContainer = itemView.findViewById(R.id.grideLayoutContainerId);

        }

        //*********************************************************************************************************
        //function
        public void setGridViewLayout(final List<HorizontalScroolProductsModel> horizontalScroolProductsModelListx, final String title, String colorx) {
            grideLayoutContainer.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(colorx)));
            GridViewLayoutTitle.setText(title);
            gridViewLayout.setAdapter(new GridProductLayoutAdapter(horizontalScroolProductsModelListx));

            GridViewAllViewBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ViewAllActivity.horizontalScroolProductsModelList=horizontalScroolProductsModelListx;
                    Intent horizontalViewAllIntent=new Intent(itemView.getContext(),ViewAllActivity.class);
                    horizontalViewAllIntent.putExtra("layout_code",1);
                    horizontalViewAllIntent.putExtra("layou_title",title);
                    itemView.getContext().startActivities(new Intent[]{horizontalViewAllIntent});
                }
            });
        }

    }

    //gridProducts View  end

}


