package com.guazi.wuxian;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.guazi.wuxian.loader.FrescoImageLoader;
import com.guazi.wuxian.loader.PicassoImageLoader;
import com.guazi.wuxian.loader.UILImageLoader;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import me.nereo.multi_image_selector.GZImageLoader;
import me.nereo.multi_image_selector.SelectorFinal;
import me.nereo.multi_image_selector.config.CoreConfig;
import me.nereo.multi_image_selector.config.FunctionConfig;
import me.nereo.multi_image_selector.config.ThemeConfig;
import me.nereo.multi_image_selector.widget.HorizontalListView;

/**
 * 图片选择
 * Created by Haoxunwang on 2016/1/9.
 */
public class SelectorActivity extends Activity implements View.OnClickListener {

    private static final String TAG = SelectorActivity.class.getSimpleName();

    private static final int UPLOAD_STATUS_UPLOAD_FAILED = 1;
    private static final int UPLOAD_PROGRESS = 2;

    private static final int REQUEST_CODE_GALLERY = 1000;

    private ArrayList<String> resultData = new ArrayList<>();

    private ExecutorService mThreadPool;

    private RadioGroup rg_theme, rg_loader, rg_select_mode, rg_act_orientation;
    private LinearLayout ll_max, ll_status;
    private EditText ed_max;
    private CheckBox cb_show_camera, cb_preview, cb_clear;
    private HorizontalListView hlv_result;
    private Button btn_open, btn_upload;
    private TextView tv_upload_status, tv_upload_progress;

    private ChoosePhotoListAdapter mChoosePhotoListAdapter;

    // 配置类
    GZImageLoader imageLoader = null;
    ThemeConfig themeConfig = null;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case UPLOAD_STATUS_UPLOAD_FAILED:
                    tv_upload_status.setTextColor(Color.RED);
                    tv_upload_status.setText((CharSequence) msg.obj);
                    tv_upload_progress.setVisibility(View.GONE);
                    break;
                case UPLOAD_PROGRESS:
                    tv_upload_status.setText(">>> upload progress :");
                    tv_upload_progress.setText(msg.obj + "");
                    break;
            }
        }
    };

//    UploadImageController.UploadImageCallback uploadImageCallback = new UploadImageController.UploadImageCallback() {
//        @Override
//        public void onProgress(int sum, int done) {
//            String status = (done + 1) + "/" + sum;
//            Log.d(TAG, "### downloaded : " + status);
//            mHandler.sendMessage(mHandler.obtainMessage(UPLOAD_PROGRESS, status));
//        }
//    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_selector);

        findViews();
        setListener();
        mThreadPool = Executors.newFixedThreadPool(2);

        initImageLoader(this);
        initFresco();
    }

    private void findViews() {
        rg_theme = (RadioGroup) findViewById(R.id.rg_theme);
        rg_loader = (RadioGroup) findViewById(R.id.rg_loader);
        rg_select_mode = (RadioGroup) findViewById(R.id.rg_select_mode);
        rg_act_orientation = (RadioGroup) findViewById(R.id.rg_act_orientation);

        ll_max = (LinearLayout) findViewById(R.id.ll_max_size);
        ed_max = (EditText) findViewById(R.id.et_max_size);
        cb_show_camera = (CheckBox) findViewById(R.id.cb_show_camera);
        cb_preview = (CheckBox) findViewById(R.id.cb_preview);
        cb_clear = (CheckBox) findViewById(R.id.cb_clear);

        btn_open = (Button) findViewById(R.id.btn_open_gallery);

        hlv_result = (HorizontalListView) findViewById(R.id.lv_photo);

        ll_status = (LinearLayout) findViewById(R.id.ll_status);
        btn_upload = (Button) findViewById(R.id.btn_upload);
        tv_upload_status = (TextView) findViewById(R.id.tv_upload_status);
        tv_upload_progress = (TextView) findViewById(R.id.tv_upload_progress);
    }


    private void setListener() {
        mChoosePhotoListAdapter = new ChoosePhotoListAdapter(SelectorActivity.this, resultData);
        hlv_result.setAdapter(mChoosePhotoListAdapter);

        rg_select_mode.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkId) {
                if (checkId == R.id.rb_single_select) {
                    ll_max.setVisibility(View.GONE);
                } else {
                    ll_max.setVisibility(View.VISIBLE);
                }
            }
        });

        btn_open.setOnClickListener(this);
        btn_upload.setOnClickListener(this);
    }

    private void initImageLoader(Context context) {
        // This configuration tuning is custom.You can tune every option,you may tune some of them,
        // or you can create default configuration by
        // ImageloaderConfiguration.createDefault(this); method
        ImageLoaderConfiguration.Builder config = new ImageLoaderConfiguration.Builder(context);
        config.threadPriority(Thread.NORM_PRIORITY - 2);
        config.denyCacheImageMultipleSizesInMemory();
        config.diskCacheFileNameGenerator(new Md5FileNameGenerator());
        config.diskCacheSize(50 * 1024 * 1024);
        config.tasksProcessingOrder(QueueProcessingType.LIFO);
        config.writeDebugLogs();

        // Initialize Imageloader with configuration
        ImageLoader.getInstance().init(config.build());
    }

    private void initFresco() {
        ImagePipelineConfig config = ImagePipelineConfig.newBuilder(this)
                .setBitmapsConfig(Bitmap.Config.ARGB_8888)
                .build();
        Fresco.initialize(this, config);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_open_gallery:
                collectData();
//                SelectorFinal.getFunctionConfig().setMutiSelect(false);

                SelectorFinal.openGallery(resultData, mResultCallback);
                break;
//            case R.id.btn_upload:
//                tv_upload_status.setVisibility(View.VISIBLE);
//                tv_upload_status.setText("正在上传，请稍后...");
//
//                mThreadPool.execute(new Runnable() {
//                    @Override
//                    public void run() {
//                        UploadImageController.UploadImageResult result = UploadImageController.getsInstance()
//                                .uploadImages(resultData, UploadImageController.UPLOAD_FILE_TYPE_IMG, uploadImageCallback);
//                        switch (result.resultCode) {
//                            case UploadImageController.SUCCESS:
//                                mHandler.sendMessage(mHandler.obtainMessage(UPLOAD_STATUS_UPLOAD_FAILED, "上传图片服务器成功！！！"));
//                                break;
//                            case UploadImageController.ERROR_CODE_UPLOAD_TO_KS_SERVER_FAILED:
//                                mHandler.sendMessage(mHandler.obtainMessage(UPLOAD_STATUS_UPLOAD_FAILED, "上传图片服务器失败"));
//                                break;
//
//                            case UploadImageController.ERROR_CODE_GET_TOKEN_FAILED:
//                                mHandler.sendMessage(mHandler.obtainMessage(UPLOAD_STATUS_UPLOAD_FAILED, "获取图片服务器签名失败"));
//                                break;
//                        }
//                    }
//                });
//                break;
        }
    }

    /**
     * 当前选择的配置信息（建议：把这些配置设置到Application中！！！）
     */
    private void collectData() {
        // 配置主题
        int themeId = rg_theme.getCheckedRadioButtonId();
        switch (themeId) {
            case R.id.rb_theme_default:
                themeConfig = ThemeConfig.DEFAULT;
                break;
            case R.id.rb_theme_custom:
                ThemeConfig theme = new ThemeConfig.Builder()
                        // 设置title背景颜色
                        .setTitleBarBgColor(Color.rgb(0xFF, 0x57, 0x22))
                        // 设置title字体颜色
                        .setTitleBarTextColor(Color.BLACK)
                        //选择框未选颜色
                        .setCheckNornalColor(Color.WHITE)
                        //选择框选中颜色
                        .setCheckSelectedColor(Color.BLACK)
                        .build();
                themeConfig = theme;
                break;
            case R.id.rb_theme_cyan:
                themeConfig = ThemeConfig.CYAN;
                break;
            case R.id.rb_theme_dark:
                themeConfig = ThemeConfig.DARK;
                break;
            case R.id.rb_theme_green:
                themeConfig = ThemeConfig.GREEN;
                break;
            case R.id.rb_theme_orange:
                themeConfig = ThemeConfig.ORANGE;
                break;
            case R.id.rb_theme_teal:
                themeConfig = ThemeConfig.TEAL;
                break;
        }

        //配置功能
        final FunctionConfig.Builder functionBuilder = new FunctionConfig.Builder();

        int loaderId = rg_loader.getCheckedRadioButtonId();
        switch (loaderId) {
            case R.id.rb_picasso:
                imageLoader = new PicassoImageLoader();
                break;
            case R.id.rb_uil:
                imageLoader = new UILImageLoader();
                break;
            case R.id.rb_fresco:
                imageLoader = new FrescoImageLoader(SelectorActivity.this);
                break;
        }
        // 选择模式
        int modeId = rg_select_mode.getCheckedRadioButtonId();
        switch (modeId) {
            case R.id.rb_single_select:
                ll_max.setVisibility(View.GONE);
                functionBuilder.setEnableMutiSelect(false);
                break;
            case R.id.rb_muti_select:
                ll_max.setVisibility(View.VISIBLE);
                functionBuilder.setEnableMutiSelect(true);
                break;
        }

        //Act横竖屏fx
        int orientationId = rg_act_orientation.getCheckedRadioButtonId();
        switch (orientationId) {
            case R.id.rb_portrait:
                functionBuilder.setEnablePortrait(true);
                break;
            case R.id.rb_landscape:
                functionBuilder.setEnablePortrait(false);
                break;
        }

        if (TextUtils.isEmpty(ed_max.getText().toString())) {
            Toast.makeText(getApplicationContext(), "请输入MaxSize", Toast.LENGTH_SHORT).show();
            return;
        }
        int maxSize = Integer.parseInt(ed_max.getText().toString());
        functionBuilder.setMaxSize(maxSize);

        if (cb_show_camera.isChecked()) {
            functionBuilder.setEnableCamera(true);
        }

        if (cb_preview.isChecked()) {
            functionBuilder.setEnablePreview(true);
        }

        if (cb_clear.isChecked()) {
            functionBuilder.setEnableClear(true);
        }

        FunctionConfig functionConfig = functionBuilder.build();

        //设置核心配置
        CoreConfig coreConfig = new CoreConfig.Builder(SelectorActivity.this, imageLoader, themeConfig)
                .setDebug(me.nereo.multi_image_selector.BuildConfig.DEBUG)
                .setFunctionConfig(functionConfig)
                .build();
        //初始化
        SelectorFinal.init(coreConfig);
    }

    SelectorFinal.OnHandlerResultCallback mResultCallback = new SelectorFinal.OnHandlerResultCallback() {

        @Override
        public void onHandlerSuccess(List<String> resultList) {
            ArrayList<String> finalList = (ArrayList<String>) resultList;
            if (null != finalList) {
                resultData.clear();
                resultData.addAll(finalList);
                refreshUI();
            }
        }

        @Override
        public void onHandlerFailure(String errorMsg) {

        }
    };

    private void refreshUI() {
        mChoosePhotoListAdapter.notifyDataSetChanged();
        ll_status.setVisibility(View.VISIBLE);
    }


    //返回选择图片的路径
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (data == null) return;
//        switch (requestCode) {
//            case REQUEST_CODE_GALLERY:
//                ArrayList<String> finalList = data.getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT);
//                if (null != finalList) {
//                    resultData.clear();
//                    resultData.addAll(finalList);
//                    refreshUI();
//                }
//                break;
//        }
//    }

}
