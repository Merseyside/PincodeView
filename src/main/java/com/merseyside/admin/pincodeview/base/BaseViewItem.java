package com.merseyside.admin.pincodeview.base;

import android.view.View;

public abstract class BaseViewItem {

    abstract protected void fill();

    abstract protected void clear();

    abstract protected View getView();

    abstract protected void setView(View view);
}
