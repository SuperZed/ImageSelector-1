package me.nereo.multi_image_selector.config;

import java.util.ArrayList;

/**
 * 功能配置类(比如：是否开启相机，预览等功能)
 * Created by haoxunwang on 2016/1/7.
 */
public class FunctionConfig {

    public boolean mutiSelect;
    protected int maxSize;
    private boolean camera;
    private boolean preview;//预览
    private boolean clear;
    private ArrayList<String> selectedList;
    private boolean portrait;

    public FunctionConfig(Builder builder) {
        this.mutiSelect = builder.mutiSelect;
        this.maxSize = builder.maxSize;
        this.camera = builder.camera;
        this.selectedList = builder.selectedList;
        this.preview = builder.preview;
        this.clear = builder.clear;
        this.portrait = builder.portrait;
    }

    public static class Builder {
        private boolean mutiSelect;//多选or单选
        private int maxSize;//可选图片最大数量
        private boolean camera;//是否显示相机
        private boolean preview;//预览
        private boolean clear;//删除
        private ArrayList<String> selectedList;//选择的图片集合
        private boolean portrait;//Activity的方向(是否竖屏)

        public Builder setEnableMutiSelect(boolean mutiSelect) {
            this.mutiSelect = mutiSelect;
            return this;
        }

        public Builder setMaxSize(int maxSize) {
            this.maxSize = maxSize;
            return this;
        }

        public Builder setEnableCamera(boolean enable) {
            this.camera = enable;
            return this;
        }

        public Builder setEnablePreview(boolean enable) {
            this.preview = enable;
            return this;
        }

        public Builder setEnableClear(boolean enable) {
            this.clear = enable;
            return this;
        }

        public Builder setSelected(ArrayList<String> selectedList) {
            if (selectedList != null) {
                this.selectedList = (ArrayList<String>) selectedList;
            }
            return this;
        }

        public Builder setEnablePortrait(boolean isPortrait) {
            this.portrait = isPortrait;
            return this;
        }

        public FunctionConfig build() {
            return new FunctionConfig(this);
        }
    }

    public boolean isEnableMutiSelect() {
        return mutiSelect;
    }

    public int getMaxSize() {
        return maxSize;
    }

    public boolean isEnableCamera() {
        return camera;
    }

    public boolean isEnablePreview() {
        return preview;
    }

    public boolean isEnableClear() {
        return clear;
    }

    public boolean isEnablePortrait() {
        return portrait;
    }

    public void setMutiSelect(boolean mutiSelect) {
        this.mutiSelect = mutiSelect;
    }

    public void setMaxSize(int maxSize) {
        this.maxSize = maxSize;
    }

    public void setPreview(boolean preview) {
        this.preview = preview;
    }

    public void setCamera(boolean camera) {
        this.camera = camera;
    }

    public void setClear(boolean clear) {
        this.clear = clear;
    }

    public ArrayList<String> getSelectedList() {
        return selectedList;
    }

    public void setPortrait(boolean portrait) {
        this.portrait = portrait;
    }
}
