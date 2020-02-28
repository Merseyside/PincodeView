package com.merseyside.admin.pincodeview.base;


import android.content.Context;
import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.merseyside.admin.pincodeview.R;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseViewContainer<T extends BaseViewItem> extends LinearLayout {

    private final String TAG = "BaseViewContainer";

    protected int pinLength;
    protected Context context;
    protected ViewGroup viewContainer;

    private List<T> views;

    public BaseViewContainer(Context context) {
        super(context);
        this.context = context;
        setBackgroundColor(ContextCompat.getColor(context, R.color.transparent));
        initializeView();
    }

    public BaseViewContainer(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        this.context = context;
        setBackgroundColor(ContextCompat.getColor(context, R.color.transparent));
        initializeView();
    }

    protected void initializeView() {
        LayoutInflater.from(context).inflate(R.layout.container, this);
        viewContainer = findViewById(R.id.container_view);

        views = new ArrayList<>();
    }

    public void setPinLength(int pinLength) {
        Log.d("Item", "pinLength = " + pinLength);
        this.pinLength = pinLength;

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        viewContainer.removeAllViews();

        List<T> temp = new ArrayList<>(this.pinLength);
        T view;
        for (int i = 0; i < this.pinLength; i++) {
            if (i < views.size()) {
                view = views.get(i);
            } else {
                view = getNewItem();
                view.setView(inflater.inflate(getItemView(), viewContainer, false));
            }
            viewContainer.addView(view.getView());
            temp.add(view);
        }

        /*
        FUCK! THIS! SHIT!
        Can some1 fix this???
        */
        view = getNewItem();
        view.setView(inflater.inflate(getItemView(), viewContainer, false));
        view.getView().setVisibility(View.GONE);
        viewContainer.addView(view.getView());

        /**/

        views.clear();
        views.addAll(temp);
    }

    public int getPinLength() {
        return pinLength;
    }

    public List<T> getViews() {
        return views;
    }

    public void setPin(int count) {
        if (count <= views.size())
            views.get(count-1).fill();
    }

    public void refresh(int count) {
        Log.d(TAG, "refresh = " + count);
        for (int i = 0; i < count; i++) {
            views.get(i).clear();
            views.get(i).fill();
        }
    }

    public void clearPinView() {
        for (T view : getViews())
            view.clear();
    }

    public void clearLast() {
        for (int i = getViews().size()-1; i>=0; i--) {
            if (getViews().get(i).getView().isSelected()) {
                getViews().get(i).clear();
                return;
            }
        }
    }

    @LayoutRes
    protected abstract int getItemView();

    protected abstract T getNewItem();
}
