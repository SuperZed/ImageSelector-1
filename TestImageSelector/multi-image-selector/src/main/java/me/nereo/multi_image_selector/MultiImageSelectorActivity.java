package me.nereo.multi_image_selector;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;

import me.nereo.multi_image_selector.config.FunctionConfig;

/**
 * 多图选择
 * Created by Nereo on 2015/4/7.
 */
public class MultiImageSelectorActivity extends FragmentActivity implements MultiImageSelectorFragment.Callback, View.OnClickListener {

    /**
     * 最大图片选择次数，int类型，默认9
     */
    public static final String EXTRA_SELECT_COUNT = "max_select_count";
    /**
     * 图片选择模式，默认多选
     */
    public static final String EXTRA_SELECT_MODE = "select_count_mode";
    /**
     * 是否显示相机，默认显示
     */
    public static final String EXTRA_SHOW_CAMERA = "show_camera";
    /**
     * 选择结果，返回为 ArrayList&lt;String&gt; 图片路径集合
     */
    public static final String EXTRA_RESULT = "select_result";
    /**
     * 默认选择集
     */
    public static final String EXTRA_DEFAULT_SELECTED_LIST = "default_list";

    /**
     * 单选
     */
    public static final int MODE_SINGLE = 0;
    /**
     * 多选
     */
    public static final int MODE_MULTI = 1;

    private static final String FRAGMENT_TAG = "MultiFragment";

    private ArrayList<String> resultList = new ArrayList<String>();
    private int mDefaultCount;
    private View mTitleBar;
    private TextView mTitleName, mTvChooseCount;
    private ImageView mIvBack, mIvClear, mIvPreview;
    private FunctionConfig mFunctionConfig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_default);

        mFunctionConfig = SelectorFinal.getFunctionConfig();

        setRequestedOrientation(mFunctionConfig.isEnablePortrait() ? ActivityInfo.SCREEN_ORIENTATION_PORTRAIT : ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        Intent intent = getIntent();
//        mDefaultCount = intent.getIntExtra(EXTRA_SELECT_COUNT, 9);
//        int mode = intent.getIntExtra(EXTRA_SELECT_MODE, MODE_MULTI);
//        boolean isShow = intent.getBooleanExtra(EXTRA_SHOW_CAMERA, true);

        mDefaultCount = mFunctionConfig.getMaxSize();
        int mode = mFunctionConfig.isEnableMutiSelect() ? MODE_MULTI : MODE_SINGLE;
        boolean isShow = mFunctionConfig.isEnableCamera();
        if (mode == MODE_MULTI && intent.hasExtra(EXTRA_DEFAULT_SELECTED_LIST)) {
            resultList = intent.getStringArrayListExtra(EXTRA_DEFAULT_SELECTED_LIST);
        }

        Bundle bundle = new Bundle();
        bundle.putInt(MultiImageSelectorFragment.EXTRA_SELECT_COUNT, mDefaultCount);
        bundle.putInt(MultiImageSelectorFragment.EXTRA_SELECT_MODE, mode);
        bundle.putBoolean(MultiImageSelectorFragment.EXTRA_SHOW_CAMERA, isShow);
        bundle.putStringArrayList(MultiImageSelectorFragment.EXTRA_DEFAULT_SELECTED_LIST, resultList);

        getSupportFragmentManager().beginTransaction()
                .add(R.id.image_grid, Fragment.instantiate(this, MultiImageSelectorFragment.class.getName(), bundle), FRAGMENT_TAG)
                .commit();

        findViews();
        setTheme();
        refreshSelectCount();
    }

    private void findViews() {
        mTitleBar = findViewById(R.id.rl_root_titlebar);
        mIvBack = (ImageView) findViewById(R.id.iv_back);
        mTitleName = (TextView) findViewById(R.id.tv_titlename);
        mTvChooseCount = (TextView) findViewById(R.id.tv_choose_count);
        mIvClear = (ImageView) findViewById(R.id.iv_clear);
        mIvPreview = (ImageView) findViewById(R.id.iv_preview);

        // 返回按钮
        mIvBack.setOnClickListener(this);
        mIvPreview.setOnClickListener(this);
        mIvClear.setOnClickListener(this);
        mTvChooseCount.setOnClickListener(this);
    }

    private void setTheme() {
        mTitleBar.setBackgroundColor(SelectorFinal.getGalleryTheme().getTitleBarBgColor());
//        mTitleName.setText("选择图片");
        mTitleName.setTextColor(SelectorFinal.getGalleryTheme().getTitleBarTextColor());
        if (!mFunctionConfig.isEnableMutiSelect()) {
            mTvChooseCount.setVisibility(View.INVISIBLE);
            return;
        }
        mTvChooseCount.setText(getString(R.string.selected, resultList.size(), mDefaultCount));
        mTvChooseCount.setTextColor(SelectorFinal.getGalleryTheme().getTitleBarTextColor());
        // 完成按钮
        if (resultList == null || resultList.size() <= 0) {
//            mTvChooseCount.setText("完成");
            mTvChooseCount.setEnabled(false);
        } else {
//            mTvChooseCount.setText("完成(" + resultList.size() + "/" + mDefaultCount + ")");
            mTvChooseCount.setEnabled(true);
        }
    }

    private void refreshSelectCount() {
        mTvChooseCount.setText(getString(R.string.selected, resultList.size(), mDefaultCount));
        if (resultList.size() > 0 && mFunctionConfig.isEnableMutiSelect()) {
            if (mFunctionConfig.isEnableClear()) {
                mIvClear.setVisibility(View.VISIBLE);
            }
            if (mFunctionConfig.isEnablePreview()) {
                mIvPreview.setVisibility(View.VISIBLE);
            }
        } else {
            mIvClear.setVisibility(View.GONE);
            mIvPreview.setVisibility(View.GONE);
        }
    }

    @Override
    public void onSingleImageSelected(String path) {
//        Intent data = new Intent();
//        resultList.add(path);
//        data.putStringArrayListExtra(EXTRA_RESULT, resultList);
//        setResult(RESULT_OK, data);
//        finish();

        resultList.add(path);
        SelectorFinal.getCallback().onHandlerSuccess(resultList);
        finish();
    }

    @Override
    public void onImageSelected(String path) {
        if (!resultList.contains(path)) {
            resultList.add(path);
        }
        // 有图片之后，改变按钮状态
        if (resultList.size() > 0) {
            mTvChooseCount.setText(getString(R.string.selected, resultList.size(), mDefaultCount));
            if (!mTvChooseCount.isEnabled()) {
                mTvChooseCount.setEnabled(true);
            }
        }
        refreshSelectCount();
    }

    @Override
    public void onImageUnselected(String path) {
        if (resultList.contains(path)) {
            resultList.remove(path);
            mTvChooseCount.setText(getString(R.string.selected, resultList.size(), mDefaultCount));
        } else {
            mTvChooseCount.setText(getString(R.string.selected, resultList.size(), mDefaultCount));
        }
        // 当为选择图片时候的状态
        if (resultList.size() == 0) {
            mTvChooseCount.setText(getString(R.string.selected, resultList.size(), mDefaultCount));
            mTvChooseCount.setEnabled(false);
        }
        refreshSelectCount();
    }

    @Override
    public void onCameraShot(File imageFile) {
//        if (imageFile != null) {
//            Intent data = new Intent();
//            resultList.add(imageFile.getAbsolutePath());
//            data.putStringArrayListExtra(EXTRA_RESULT, resultList);
//            setResult(RESULT_OK, data);
//            finish();
//        }

        if (imageFile != null) {
            resultList.add(imageFile.getAbsolutePath());
            SelectorFinal.getCallback().onHandlerSuccess(resultList);
            finish();
        }
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.iv_back) {
            setResult(RESULT_CANCELED);
            finish();
        } else if (id == R.id.iv_clear) {
            MultiImageSelectorFragment mMultiFragment = (MultiImageSelectorFragment) getSupportFragmentManager().findFragmentByTag(FRAGMENT_TAG);
            mMultiFragment.clearSelectImage();
            resultList.clear();
            refreshSelectCount();
        } else if (id == R.id.iv_preview) {
            if (resultList != null && resultList.size() > 0) {
                Intent intent = new Intent();
                intent.setClass(MultiImageSelectorActivity.this, PhotoPreviewActivity.class);
                intent.putStringArrayListExtra(PhotoPreviewActivity.PHOTO_LIST, resultList);
                startActivity(intent);
            }
        } else if (id == R.id.tv_choose_count) {
//            if (resultList != null && resultList.size() > 0) {
//                // 返回已选择的图片数据
//                Intent data = new Intent();
//                data.putStringArrayListExtra(EXTRA_RESULT, resultList);
//                setResult(RESULT_OK, data);
//                finish();

            if (resultList != null && resultList.size() > 0) {
                SelectorFinal.getCallback().onHandlerSuccess(resultList);
                finish();
            }
        }
    }

}

