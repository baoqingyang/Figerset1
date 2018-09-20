package com.example.intent.figerset.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.intent.figerset.R;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ViewPager pager = null;
    PagerTabStrip tabStrip = null;

    ArrayList<View> viewContainter = new ArrayList<>();
    ArrayList<String> titleContainer = new ArrayList<>();
    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adpter);

        pager = findViewById(R.id.viewpager);

        tabStrip = findViewById(R.id.tabstrip);
        //取消tab下面的长横线
        tabStrip.setDrawFullUnderline(false);
        //设置tab的背景
        //设置当前tab页签的下划线颜色
        //tabStrip.setTabIndicatorColorResource(R.color.red);
        tabStrip.setTextSpacing(400);

        View view1 = LayoutInflater.from(this).inflate(R.layout.tab1, null);
        View view2 = LayoutInflater.from(this).inflate(R.layout.tab2, null);
        View view3 = LayoutInflater.from(this).inflate(R.layout.tab3, null);
        View view4 = LayoutInflater.from(this).inflate(R.layout.tab4, null);
        //viewpager开始添加view
        viewContainter.add(view1);
        viewContainter.add(view2);
        viewContainter.add(view3);
        viewContainter.add(view4);
        //设置Adapter


        //页签项
        titleContainer.add("今日头条");
        titleContainer.add("今天热点");
        titleContainer.add("今日财经");
        titleContainer.add("今日军事");

        pager.setAdapter(new MyPagerAdapters());

    }

    /**
     *   ViewPager的数据适配器
     */
    class MyPagerAdapters extends PagerAdapter {
        //返回可以滑动的VIew的个数
        @Override
        public int getCount() {
            return viewContainter.size();
        }
        //滑动切换的时候销毁当前的组件
        @Override
        public void destroyItem(ViewGroup container, int position,
                                Object object) {
             container.removeView(viewContainter.get(position));
        }
        //将当前视图添加到container中并返回当前View视图
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(viewContainter.get(position));
            return viewContainter.get(position);
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titleContainer.get(position);
        }
    }
}
