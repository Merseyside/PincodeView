package com.merseyside.admin.pincodeview.items;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.merseyside.admin.pincodeview.base.BaseViewItem;

public class ImageItem extends BaseViewItem {

    private ImageView imageView;
    private Drawable drawable;

    public ImageItem(Context context, Drawable drawable) {
        this.imageView = new ImageView(context);

        /*this.imageView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        this.imageView.setImageDrawable(drawable);*/
        this.drawable = drawable;
    }

    @Override
    protected void fill() {
        Log.d("Item", "setSelected " + imageView);
        imageView.setSelected(true);
        Log.d("Item", "setView id = " + imageView.getId());
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
