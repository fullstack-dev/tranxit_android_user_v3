package com.tranxitpro.user.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tranxitpro.user.R;
import com.tranxitpro.user.base.BaseActivity;
import com.tranxitpro.user.ui.activity.login.EmailActivity;
import com.tranxitpro.user.ui.activity.register.RegisterActivity;
import com.tranxitpro.user.data.network.model.WalkThrough;
import com.tranxitpro.user.ui.activity.social.SocialLoginActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class OnBoardActivity extends BaseActivity implements ViewPager.OnPageChangeListener {

    @BindView(R.id.view_pager)
    ViewPager viewPager;
    @BindView(R.id.layoutDots)
    LinearLayout layoutDots;
    private MyViewPagerAdapter adapter;
    private int dotsCount;
    private ImageView[] dots;

    @Override
    public int getLayoutId() {
        return R.layout.activity_on_board;
    }

    @Override
    public void initView() {
        ButterKnife.bind(this);

        List<WalkThrough> list = new ArrayList<>();
        list.add(new WalkThrough(R.drawable.bg_walk_one,
                getString(R.string.walk_1), getString(R.string.walk_1_description)));
        list.add(new WalkThrough(R.drawable.bg_walk_two,
                getString(R.string.walk_2), getString(R.string.walk_2_description)));
        list.add(new WalkThrough(R.drawable.bg_walk_three,
                getString(R.string.walk_3), getString(R.string.walk_3_description)));

        adapter = new MyViewPagerAdapter(this, list);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(0);
        viewPager.addOnPageChangeListener(this);
        addBottomDots();

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        for (int i = 0; i < dotsCount; i++)
            dots[i].setImageDrawable(getResources().getDrawable(R.drawable.ic_dot_unselected));
        dots[position].setImageDrawable(getResources().getDrawable(R.drawable.ic_dot_selected));
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }


    private void addBottomDots() {
        dotsCount = adapter.getCount();
        dots = new ImageView[dotsCount];
        if (dotsCount == 0)
            return;

        layoutDots.removeAllViews();

        for (int i = 0; i < dotsCount; i++) {
            dots[i] = new ImageView(this);
            dots[i].setImageDrawable(getResources().getDrawable(R.drawable.ic_dot_unselected));

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );

            params.setMargins(4, 4, 4, 4);

            layoutDots.addView(dots[i], params);
        }

        dots[0].setImageDrawable(getResources().getDrawable(R.drawable.ic_dot_selected));
    }

    @OnClick({R.id.sign_in, R.id.sign_up, R.id.social_login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.sign_in:
                startActivity(new Intent(this, EmailActivity.class));
                break;
            case R.id.sign_up:
                startActivity(new Intent(this, RegisterActivity.class));
                break;
            case R.id.social_login:
                startActivity(new Intent(this, SocialLoginActivity.class));
                break;
        }
    }


    public class MyViewPagerAdapter extends PagerAdapter {
        List<WalkThrough> list;
        Context context;

        MyViewPagerAdapter(Context context, List<WalkThrough> list) {
            this.list = list;
            this.context = context;

        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            View itemView = LayoutInflater.from(container.getContext()).inflate(R.layout.pager_item, container, false);
            WalkThrough walk = list.get(position);

            TextView title = itemView.findViewById(R.id.title);
            TextView description = itemView.findViewById(R.id.description);
            ImageView imageView = itemView.findViewById(R.id.img_pager_item);

            title.setText(walk.title);
            description.setText(walk.description);
            Glide.with(context).load(walk.drawable).into(imageView);
            container.addView(itemView);

            return itemView;
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object obj) {
            return view == obj;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            View view = (View) object;
            container.removeView(view);
        }
    }
}
