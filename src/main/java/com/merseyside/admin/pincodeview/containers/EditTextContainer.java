package com.merseyside.admin.pincodeview.containers;

import android.content.Context;
import android.util.AttributeSet;

import com.merseyside.admin.pincodeview.R;
import com.merseyside.admin.pincodeview.base.BaseViewContainer;
import com.merseyside.admin.pincodeview.items.EditTextItem;

public class EditTextContainer extends BaseViewContainer<EditTextItem> {

    private final String TAG = "ItemEditView";

    public EditTextContainer(Context context) {
        super(context);
    }

    public EditTextContainer(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected int getItemView() {
        return R.layout.edit_view;
    }

    @Override
    protected EditTextItem getNewItem() {
        return new EditTextItem();
    }
}
