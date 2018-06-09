package com.merseyside.admin.pincodeview.containers;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;

import com.merseyside.admin.pincodeview.R;
import com.merseyside.admin.pincodeview.base.BaseViewContainer;
import com.merseyside.admin.pincodeview.items.ImageItem;

public class ImageContainer extends BaseViewContainer<ImageItem> {

    private Drawable image;
    private Context context;

    public ImageContainer(Context context, Drawable image) {
        super(context);
        this.image = image;
        this.context = context;
    }

    public ImageContainer(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected int getItemView() {
        return R.layout.circle_view;
    }

    @Override
    protected ImageItem getNewItem() {
        Log.d("Item", "getNewImage");
        return new ImageItem(context, image);
    }
}
