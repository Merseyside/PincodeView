package com.merseyside.admin.pincodeview.items;

import android.view.View;
import android.widget.EditText;

import com.merseyside.admin.pincodeview.base.BaseViewItem;

public class EditTextItem extends BaseViewItem {

    private EditText editText;

    @Override
    protected void fill() {
        editText.setText("*");
    }

    @Override
    protected void clear() {
        editText.setText("");
    }

    @Override
    public View getView() {
        return editText;
    }

    @Override
    protected void setView(View view) {
        editText = (EditText) view;
    }
}
