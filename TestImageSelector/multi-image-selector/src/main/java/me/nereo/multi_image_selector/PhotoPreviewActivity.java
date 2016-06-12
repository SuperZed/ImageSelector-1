package me.nereo.multi_image_selector;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import me.nereo.multi_image_selector.adapter.PhotoPreviewAdapter;
import me.nereo.multi_image_selector.config.ThemeConfig;
import me.nereo.multi_image_selector.widget.GFViewPager;
import me.nereo.multi_image_selector.widget.zoomview.PhotoView;

/**
 * Created by Haoxunwang on 2016/1/17.
 */
public class PhotoPreviewActivity extends Activity implements ViewPager.OnPageChangeListener {

    static final String PHOTO_LIST = "photo_list";

    private RelativeLayout mTitleBar;
    private ImageView mIvBack;
    private TextView mTvTitle;
    private TextView mTvIndicator;

    private GFViewPager mVpPager;
    private List<String> mPhotoList;
    private PhotoPreviewAdapter mPhotoPreviewAdapter;

    private ThemeConfig mThemeConfig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mThemeConfig = SelectorFinal.getGalleryTheme();

        if (mThemeConfig == null) {
            // TODO: 2016/1/17
        } else {
            setContentView(R.layout.activity_photo_preview);
            findViews();
            setListener();
            setTheme();

            mPhotoList = (List<String>) getIntent().getSerializableExtra(PHOTO_LIST);
            mPhotoPreviewAdapter = new PhotoPreviewAdapter(this, mPhotoList);
            mVpPager.setAdapter(mPhotoPreviewAdapter);
        }
    }

    private void findViews() {
        mTitleBar = (RelativeLayout) findViewById(R.id.titlebar);
        mIvBack = (ImageView) findViewById(R.id.iv_back);
        mTvTitle = (TextView) findViewById(R.id.tv_title);
        mTvIndicator = (TextView) findViewById(R.id.tv_indicator);

        mVpPager = (GFViewPager) findViewById(R.id.vp_pager);
    }

    private void setListener() {
//        mVpPager.addOnPageChangeListener(this);
        mVpPager.setOnPageChangeListener(this);
        mIvBack.setOnClickListener(mBackListener);
    }

    private void setTheme() {
//        mIvBack.setImageResource(mThemeConfig.getIconBack());
//        if (mThemeConfig.getIconBack() == R.drawable.ic_gf_back) {
//            mIvBack.setColorFilter(mThemeConfig.getTitleBarIconColor());
//        }

        mTitleBar.setBackgroundColor(mThemeConfig.getTitleBarBgColor());
        mTvTitle.setTextColor(mThemeConfig.getTitleBarTextColor());
//        if (mThemeConfig.getPreviewBg() != null) {
//            mVpPager.setBackgroundDrawable(mThemeConfig.getPreviewBg());
//        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        mTvIndicator.setText((position + 1) + "/" + mPhotoList.size());
        PhotoView childView = (PhotoView) mVpPager.getChildAt(position);
    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    private View.OnClickListener mBackListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            finish();
        }
    };

}
