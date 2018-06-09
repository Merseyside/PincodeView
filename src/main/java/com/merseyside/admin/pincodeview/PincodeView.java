package com.merseyside.admin.pincodeview;


import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import android.support.annotation.NonNull;

import com.merseyside.admin.pincodeview.base.BaseViewContainer;
import com.merseyside.admin.pincodeview.containers.EditTextContainer;
import com.merseyside.admin.pincodeview.containers.ImageContainer;

public class PincodeView extends RelativeLayout implements View.OnClickListener {

    public enum PincodeType {
        TEXT(0), IMAGE(1);

        private int id;

        PincodeType(int id) {
            this.id = id;
        }

        static PincodeType fromId(int id) {
            for (PincodeType type : values()) {
                if (type.id == id) {
                    return type;
                }
            }
            throw new IllegalArgumentException("No color with passed id");
        }

        public int getId() {
            return id;
        }
    }

    private final String TAG = "PincodeView";

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.button_0) {
            onButtonPressed("0");
        } else if (id == R.id.button_1) {
            onButtonPressed("1");
        } else if (id == R.id.button_2) {
            onButtonPressed("2");
        } else if (id == R.id.button_3) {
            onButtonPressed("3");
        } else if (id == R.id.button_4) {
            onButtonPressed("4");
        } else if (id == R.id.button_5) {
            onButtonPressed("5");
        } else if (id == R.id.button_6) {
            onButtonPressed("6");
        } else if (id == R.id.button_7) {
            onButtonPressed("7");
        } else if (id == R.id.button_8) {
            onButtonPressed("8");
        } else if (id == R.id.button_9) {
            onButtonPressed("9");
        } else if (id == R.id.button_delete) {
            onButtonPressed(DELETE_BUTTON_SYMBOL);
        } else if (id == R.id.button_clear) {
            onButtonPressed(CLEAR_BUTTON_SYMBOL);
        }
    }

    public interface PincodeAccessListener {
        void accessDenied();
        void accessGranted();
        void timeout();
    }

    public interface PincodeSetupModeListener {
        void passPinOneMoreTime();
        void newPincodeSetUp(String pin);
        void differentPins();
    }

    private Handler handler;

    private final String DELETE_BUTTON_SYMBOL = "-1";
    private final String CLEAR_BUTTON_SYMBOL = "-2";

    private Handler timeoutHandler;
    private PincodeAccessListener listener;
    private PincodeSetupModeListener setupModeListener;
    private int timeoutMillis;
    private boolean isTimeout = false;

    private Context context;
    private BaseViewContainer container;
    private String pincode = "";
    private String correct_pin = Constants.DEFAULT_PIN;

    private boolean setupMode;
    private String setupPincode;

    /* Attrs */

    private int color;
    private PincodeType type = PincodeType.TEXT;
    private Drawable buttonBackgroundImage;
    private Drawable imageDrawable;

    public PincodeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        Log.d(TAG, "constructor");

        this.context = context;
        handler = new Handler();
        loadAttrs(attrs);
        initializeView();
    }

    private void loadAttrs(AttributeSet attrs) {
        TypedArray array = context.getTheme().obtainStyledAttributes(attrs, R.styleable.PincodeView, 0, 0);

        if (array.getString(R.styleable.PincodeView_color) == null)
            color = ContextCompat.getColor(context, Constants.DEFAULT_COLOR);
        else {
            color = Color.parseColor(array.getString(R.styleable.PincodeView_color));
            Log.d(TAG, color + "");
        }
        buttonBackgroundImage = array.getDrawable(R.styleable.PincodeView_button_bg);
        type = PincodeType.fromId(array.getInt(R.styleable.PincodeView_type, 0));
        if (type == PincodeType.IMAGE) {
            if ((imageDrawable = array.getDrawable(R.styleable.PincodeView_image)) == null) {
                type = PincodeType.TEXT;
                Log.d(TAG, "Please, pass your drawable in image tag!");
            }
        }
    }

    private void initializeView() {
        LayoutInflater.from(context).inflate(R.layout.pincode_view, this);
        switch(type) {
            case TEXT:
                container = new EditTextContainer(context);
                break;
            case IMAGE:
                container = new ImageContainer(context, imageDrawable);
                break;
        }
        FrameLayout linearContainer = findViewById(R.id.container_view);
        linearContainer.addView(container);
        initButtonsView(findViewById(R.id.buttons_layout));

    }

    private void initButtonsView(View view) {
        if (view instanceof LinearLayout) {
            for (int i = 0; i < ((LinearLayout) view).getChildCount(); i++) {
                initButtonsView(((LinearLayout) view).getChildAt(i));
            }
        } else {
            view.setOnClickListener(this);
            if (view instanceof ImageButton) {
                if (buttonBackgroundImage != null) view.setBackground(buttonBackgroundImage);
                ((ImageButton)view).setColorFilter(color, android.graphics.PorterDuff.Mode.SRC_IN);
            } else if (view instanceof Button) {
                if (buttonBackgroundImage != null) view.setBackground(buttonBackgroundImage);
                ((Button) view).setTextColor(color);
            }
        }
    }

    public void setPincodeAccessListener(PincodeAccessListener pincodeAccessListener) {
        this.listener = pincodeAccessListener;
    }

    public void setSetupModeListener(PincodeSetupModeListener pincodeSetupModeListener) {
        this.setupModeListener = pincodeSetupModeListener;
    }

    @Override
    public boolean onKeyDown(int keyCode, @NonNull KeyEvent event) {
        switch(keyCode) {
            case KeyEvent.KEYCODE_0:
                onButtonPressed("0");
                break;
            case KeyEvent.KEYCODE_1:
                onButtonPressed("1");
                break;
            case KeyEvent.KEYCODE_2:
                onButtonPressed("2");
                break;
            case KeyEvent.KEYCODE_3:
                onButtonPressed("3");
                break;
            case KeyEvent.KEYCODE_4:
                onButtonPressed("4");
                break;
            case KeyEvent.KEYCODE_5:
                onButtonPressed("5");
                break;
            case KeyEvent.KEYCODE_6:
                onButtonPressed("6");
                break;
            case KeyEvent.KEYCODE_7:
                onButtonPressed("7");
                break;
            case KeyEvent.KEYCODE_8:
                onButtonPressed("8");
                break;
            case KeyEvent.KEYCODE_9:
                onButtonPressed("9");
                break;
            case KeyEvent.KEYCODE_DEL:
                onButtonPressed(DELETE_BUTTON_SYMBOL);
                break;
        }
        Log.d(TAG, keyCode + "");
        if (isTimeout) restartTimeout();
        return false;
    }

    public void setSetupMode(boolean setupMode, int pinLength) {
        this.setupMode = setupMode;
        pincode = "";
        container.clearPinView();
        if (setupMode && pinLength != 0) {
            setPinLength(pinLength);
        }
    }

    private void onButtonPressed(String value) {
        if (value.equals(DELETE_BUTTON_SYMBOL)) {
            if (pincode.length() != 0) {
                pincode = deleteLastSymbol(pincode);
                container.clearLast();
                return;
            } else return;
        } else if (value.equals(CLEAR_BUTTON_SYMBOL)) {
            pincode = "";
            container.clearPinView();
            return;
        } else if (pincode.length() < container.getPinLength()) {
            Log.d(TAG, value);
            pincode = pincode + value;
        } else {
            return;
        }

        container.setPin(pincode);

        if (pincode.length() == container.getPinLength()) {
            if (setupMode) {
                if (TextUtils.isEmpty(setupPincode)) {
                    setupPincode = pincode;
                    if (setupModeListener != null) setupModeListener.passPinOneMoreTime();

                } else {
                    if (setupPincode.equals(pincode)) {
                        if (setupModeListener != null) setupModeListener.newPincodeSetUp(pincode);
                    } else {
                        if (setupModeListener != null) setupModeListener.differentPins();
                    }
                    setupPincode = "";
                }
                handler.postDelayed(clearPinRunnable, Constants.DELAY_MILLIS);
            } else {
                if (listener != null) {
                    if (isPinCorrect(pincode)) {
                        listener.accessGranted();
                    } else {
                        listener.accessDenied();
                        handler.postDelayed(clearPinRunnable, Constants.DELAY_MILLIS);
                    }
                }
            }
        }
    }


    private Runnable clearPinRunnable = new Runnable() {
        @Override
        public void run() {
            pincode = "";
            container.clearPinView();
        }
    };

    private void setPinLength(int length) {
        container.setPinLength(length);
    }

    public void setCorrectPin(String pin) {
        this.correct_pin = pin;
        setPinLength(correct_pin.length());
    }

    private String deleteLastSymbol(String str) {
        return str.substring(0, str.length()-1);
    }

    private boolean isPinCorrect(String pincode) {
        return pincode.equals(correct_pin);
    }



    /* Timeout */

    public void setTimeout(int sec) {
        if (sec > 0) {
            isTimeout = true;
            timeoutMillis = sec * 1000;
            timeoutHandler = new Handler();
            startDismissTimer();
        } else {
            isTimeout = false;
            timeoutHandler = null;
        }
    }

    public void setTimeout() {
        setTimeout(Constants.DEFAULT_TIMEOUT);
    }

    private void restartTimeout() {
        stopDismissTimer();
        startDismissTimer();
    }

    private void startDismissTimer() {
        timeoutHandler.postDelayed(hideDialog, timeoutMillis);
    }

    private void stopDismissTimer() {
        if (timeoutHandler != null) timeoutHandler.removeCallbacks(hideDialog);
    }

    final Runnable hideDialog = new Runnable() {
        @Override
        public void run() {
            listener.timeout();
        }
    };

}
