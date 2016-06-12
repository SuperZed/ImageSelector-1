package me.nereo.multi_image_selector;

import android.content.Intent;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import me.nereo.multi_image_selector.config.CoreConfig;
import me.nereo.multi_image_selector.config.FunctionConfig;
import me.nereo.multi_image_selector.config.ThemeConfig;

/**
 * 图片选择管理器
 * Created by Haoxunwang on 2016/1/8.
 */
public class SelectorFinal {

    private static CoreConfig mCoreConfig;
    private static FunctionConfig mFunctionConfig;
    private static ThemeConfig mThemeConfig;
    private static OnHandlerResultCallback mCallback;

    public static void init(CoreConfig coreConfig) {
        mThemeConfig = coreConfig.getThemeConfig();
        mCoreConfig = coreConfig;
        mFunctionConfig = coreConfig.getFunctionConfig();
    }

    public static CoreConfig getCoreConfig() {
        return mCoreConfig;
    }

    public static FunctionConfig getFunctionConfig() {
        return mFunctionConfig;
    }

    public static ThemeConfig getGalleryTheme() {
        if (mThemeConfig == null) {
            mThemeConfig = ThemeConfig.DEFAULT;
        }
        return mThemeConfig;
    }

    public static void openGallery(/*int requestCode, */ ArrayList<String> preListData, OnHandlerResultCallback callback) {
        if (mCoreConfig == null || mCoreConfig.getImageLoader() == null) {
            Toast.makeText(mCoreConfig.getContext(), mCoreConfig.getContext().getString(R.string.open_gallery_fail), Toast.LENGTH_LONG).show();
            return;
        }

        mCallback = callback;

        Intent intent = new Intent(mCoreConfig.getContext(), MultiImageSelectorActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putStringArrayListExtra(MultiImageSelectorActivity.EXTRA_DEFAULT_SELECTED_LIST, preListData);
        mCoreConfig.getContext().startActivity(intent);
    }

    /**
     * clean cache data
     */
    public static void cleanCacheFile() {
        // TODO: 2016/1/9

    }

    public interface OnHandlerResultCallback {
        /**
         * handler success
         *
         * @param resultList
         */
//        public void onHandlerSuccess(int requestCode, List<String> resultList);
        public void onHandlerSuccess(List<String> resultList);

        /**
         * handler failure
         *
         * @param errorMsg
         */
//        public void onHandlerFailure(int requestCode, String errorMsg);
        public void onHandlerFailure(String errorMsg);
    }

    public static OnHandlerResultCallback getCallback() {
        return mCallback;
    }

}
