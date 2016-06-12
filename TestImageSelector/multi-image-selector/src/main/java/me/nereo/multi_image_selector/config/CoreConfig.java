package me.nereo.multi_image_selector.config;

import android.content.Context;
import android.os.Environment;

import java.io.File;

import me.nereo.multi_image_selector.GZImageLoader;

/**
 * 核心配置类(图片加载库 + 主题配置 + 操作配置)
 * Created by haoxunwang on 2016/1/7.
 */
public class CoreConfig {

    public static final String SAVE_DIR = "guazi";

    private Context context;
    private ThemeConfig themeConfig;
    private boolean debug;
    private GZImageLoader imageLoader;
    private File takePhotoFolder;//配置拍照缓存目录
    private FunctionConfig mFunctionConfig;

    public CoreConfig(Builder builder){
        this.context = builder.context;
        this.themeConfig = builder.themeConfig;
        this.debug = builder.debug;
        this.imageLoader = builder.imageLoader;
        this.takePhotoFolder = builder.takePhotoFolder;
        this.mFunctionConfig = builder.mFunctionConfig;

        if(takePhotoFolder == null){
            takePhotoFolder = new File(Environment.getExternalStorageDirectory(),SAVE_DIR);
        }
        if(!takePhotoFolder.exists()){
            takePhotoFolder.mkdirs();
        }
    }

    public static class Builder {
        private Context context;
        private ThemeConfig themeConfig;
        private boolean debug;
        private GZImageLoader imageLoader;
        private File takePhotoFolder;//配置拍照缓存目录
        private FunctionConfig mFunctionConfig;

        public Builder(Context context,GZImageLoader imageLoader,ThemeConfig themeConfig){
            this.context = context;
            this.imageLoader = imageLoader;
            this.themeConfig = themeConfig;
        }

        public Builder setContext(Context context) {
            this.context = context;
            return this;
        }

        public Builder setThemeConfig(ThemeConfig themeConfig) {
            this.themeConfig = themeConfig;
            return this;
        }

        public Builder setDebug(boolean debug) {
            this.debug = debug;
            return this;
        }

        public Builder setImageLoader(GZImageLoader imageLoader) {
            this.imageLoader = imageLoader;
            return this;
        }

        public Builder setTakePhotoFolder(File takePhotoFolder) {
            this.takePhotoFolder = takePhotoFolder;
            return this;
        }

        public Builder setFunctionConfig(FunctionConfig mFunctionConfig) {
            this.mFunctionConfig = mFunctionConfig;
            return this;
        }

        public CoreConfig build(){
            return new CoreConfig(this);
        }
    }

    public Context getContext() {
        return context;
    }

    public ThemeConfig getThemeConfig() {
        return themeConfig;
    }

    public boolean isDebug() {
        return debug;
    }

    public GZImageLoader getImageLoader() {
        return imageLoader;
    }

    public File getTakePhotoFolder() {
        return takePhotoFolder;
    }

    public FunctionConfig getFunctionConfig() {
        return mFunctionConfig;
    }
}
