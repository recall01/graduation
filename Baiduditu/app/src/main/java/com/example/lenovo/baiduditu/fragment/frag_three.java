package com.example.lenovo.baiduditu.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lenovo.baiduditu.R;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by lenovo on 2018/1/17.
 */

public class frag_three extends Fragment {
    private FrameLayout f1;
    private ViewPager mViewPager;
    private int[] imageId = new int[]{R.drawable.banner_0,R.drawable.banner_1,R.drawable.banner_2,R.drawable.banner_3,R.drawable.banner_4};
    private String[] titles = new String[]{"这是图片1","这是图片2","这是图片3","这是图片4","这是图片5"};
    private List<ImageView> images;
    private ViewPagerAdaptor adaptor;
    private TextView title;
    private List<View>dots;
    private int currentItem,oldPosition =0;
    private ScheduledExecutorService scheduledExecutorService;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_three,null);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        f1 = view.findViewById(R.id.f1);
     //   DisplayMetrics metrics = new DisplayMetrics();
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        int width = metrics.widthPixels;
        f1.getLayoutParams().width = width;
        f1.getLayoutParams().height = width/2;
        mViewPager = view.findViewById(R.id.vp);
        images = new ArrayList<ImageView>();
        for(int i = 0;i<imageId.length;i++){
            ImageView imageView = new ImageView(getActivity());
            imageView.setBackgroundResource(imageId[i]);
            images.add(imageView);
        }
        title = view.findViewById(R.id.banner_title);
        title.setText(titles[0]);
        dots = new ArrayList<View>();
        dots.add(view.findViewById(R.id.dot_0));
        dots.add(view.findViewById(R.id.dot_1));
        dots.add(view.findViewById(R.id.dot_2));
        dots.add(view.findViewById(R.id.dot_3));
        dots.add(view.findViewById(R.id.dot_4));

        adaptor = new ViewPagerAdaptor();
        mViewPager.setAdapter(adaptor);
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                title.setText(titles[position]);
                dots.get(position).setBackgroundResource(R.drawable.dot_focused);
                dots.get(oldPosition).setBackgroundResource(R.drawable.dot_normal);
                oldPosition =position;
                currentItem =position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
    public class ViewPagerAdaptor extends PagerAdapter {
        @Override
        public int getCount() {
            return images.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view ==object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(images.get(position));
            return images.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(images.get(position));
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        scheduledExecutorService.scheduleWithFixedDelay(new ViewPageTask(),5,5, TimeUnit.SECONDS);
    }
    private class ViewPageTask implements Runnable{

        @Override
        public void run() {
            currentItem = (currentItem + 1)%imageId.length;
            mHandler.sendEmptyMessage(0);
        }
    }
    private Handler mHandler = new Handler(){
        public void handleMessage(Message msg){
            mViewPager.setCurrentItem(currentItem);
        }
    };
}
