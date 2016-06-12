package me.nereo.multi_image_selector.adapter;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import java.util.List;

import me.nereo.multi_image_selector.R;
import me.nereo.multi_image_selector.SelectorFinal;
import me.nereo.multi_image_selector.utils.DeviceUtils;
import me.nereo.multi_image_selector.widget.zoomview.PhotoView;

public class PhotoPreviewAdapter extends ViewHolderRecyclingPagerAdapter<PhotoPreviewAdapter.PreviewViewHolder, String> {

    private Activity mActivity;
    private DisplayMetrics mDisplayMetrics;

    public PhotoPreviewAdapter(Activity activity, List<String> list) {
        super(activity, list);
        this.mActivity = activity;
        this.mDisplayMetrics = DeviceUtils.getScreenPix(mActivity);
    }

    @Override
    public PreviewViewHolder onCreateViewHolder(ViewGroup parent, int position) {
        View view = getLayoutInflater().inflate(R.layout.item_adapter_preview_viewpgaer, null);
        return new PreviewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PreviewViewHolder holder, int position) {
        String path = getDatas().get(position);
        holder.mImageView.setImageResource(R.drawable.ic_gf_default_photo);
        Drawable defaultDrawable = mActivity.getResources().getDrawable(R.drawable.ic_gf_default_photo);
        SelectorFinal.getCoreConfig().getImageLoader().displayImage(mActivity, path, holder.mImageView, defaultDrawable, mDisplayMetrics.widthPixels/2, mDisplayMetrics.heightPixels/2);
    }

    static class PreviewViewHolder extends ViewHolderRecyclingPagerAdapter.ViewHolder{
        PhotoView mImageView;
        public PreviewViewHolder(View view) {
            super(view);
            mImageView = (PhotoView) view;
        }
    }
}
