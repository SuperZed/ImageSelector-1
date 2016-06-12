package me.nereo.multi_image_selector.config;

import android.graphics.Color;

/**
 * 主题配置类
 * Created by haoxunwang on 2016/1/16.
 */
public class ThemeConfig {

    //默认主题
    public static ThemeConfig DEFAULT = new Builder().build();
    //黑色主题
    public static ThemeConfig DARK = new Builder()
            .setTitleBarTextColor(Color.WHITE)
            .setTitleBarBgColor(Color.rgb(0x38, 0x42, 0x48))
            .setCheckSelectedColor(Color.rgb(0x38, 0x42, 0x48))
            .build();

    //蓝绿主题
    public static ThemeConfig CYAN = new Builder()
            .setTitleBarTextColor(Color.WHITE)
            .setTitleBarBgColor(Color.rgb(0x01, 0x83, 0x93))
            .setCheckSelectedColor(Color.rgb(0x00, 0xac, 0xc1))
            .build();
    //橙色主题
    public static ThemeConfig ORANGE = new Builder()
            .setTitleBarTextColor(Color.WHITE)
            .setTitleBarBgColor(Color.rgb(0xFF, 0x57, 0x22))
            .setCheckSelectedColor(Color.rgb(0xFF, 0x57, 0x22))
            .build();
    //绿色主题
    public static ThemeConfig GREEN = new Builder()
            .setTitleBarTextColor(Color.WHITE)
            .setTitleBarBgColor(Color.rgb(0x4C, 0xAF, 0x50))
            .setCheckSelectedColor(Color.rgb(0x4C, 0xAF, 0x50))
            .build();
    //青绿色主题
    public static ThemeConfig TEAL = new Builder()
            .setTitleBarTextColor(Color.WHITE)
            .setTitleBarBgColor(Color.rgb(0x00, 0x96, 0x88))
            .setCheckSelectedColor(Color.rgb(0x00, 0x96, 0x88))
            .build();


    private int titleBarTextColor;
    private int titleBarBgColor;
    private int checkNornalColor;
    private int checkSelectedColor;

    private ThemeConfig(Builder builder){
        this.titleBarTextColor = builder.titleBarTextColor;
        this.titleBarBgColor = builder.titleBarBgColor;
        this.checkNornalColor = builder.checkNornalColor;
        this.checkSelectedColor = builder.checkSelectedColor;
    }

    public static class Builder {
        private int titleBarTextColor = Color.WHITE;// 标题栏文本字体颜色
        private int titleBarBgColor = Color.rgb(0x3F, 0x51, 0xB5);//标题栏背景颜色
        private int checkNornalColor = Color.rgb(0xd2, 0xd2, 0xd7);//选择框未选颜色
        private int checkSelectedColor = Color.rgb(0x3F, 0x51, 0xB5);// 选择框选中颜色

        public Builder setTitleBarTextColor(int titleBarTextColor) {
            this.titleBarTextColor = titleBarTextColor;
            return this;
        }

        public Builder setTitleBarBgColor(int titleBarBgColor) {
            this.titleBarBgColor = titleBarBgColor;
            return this;
        }

        // TODO: 2016/1/16 选择框的颜色 ？？？
        public Builder setCheckNornalColor(int checkNornalColor) {
            this.checkNornalColor = checkNornalColor;
            return this;
        }

        public Builder setCheckSelectedColor(int checkSelectedColor) {
            this.checkSelectedColor = checkSelectedColor;
            return this;
        }

        public ThemeConfig build() {
            return new ThemeConfig(this);
        }
    }

    public int getTitleBarTextColor() {
        return titleBarTextColor;
    }

    public int getTitleBarBgColor() {
        return titleBarBgColor;
    }

    public int getCheckNornalColor() {
        return checkNornalColor;
    }

    public int getCheckSelectedColor() {
        return checkSelectedColor;
    }
}
