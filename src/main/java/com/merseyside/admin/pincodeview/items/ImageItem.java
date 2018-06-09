package com.merseyside.admin.pincodeview.items;

import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.merseyside.admin.pincodeview.base.BaseViewItem;

public class ImageItem extends BaseViewItem {

    private ImageView imageView;
    private Drawable drawable;

    public ImageItem(Drawable drawable) {
        this.drawable = drawable;
    }

    @Override
    protected void fill() {
        imageView.setSelected(true);
    }

    @Override
    protected void clear() {
        imageView.setSelected(false);
    }

    @Override
    protected View getView() {
        return imageView;
    }

    @Override
    protected void setView(View view) {
        imageView = (ImageView) view;
        imageView.setImageDrawable(drawable);
    }
}
