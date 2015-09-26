package common.barter.com.barterapp;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.inputmethod.InputMethodManager;
import android.widget.AutoCompleteTextView;

/**
 * Created by amitpa on 9/27/2015.
 */
class CustomAutoCompleteTextView extends AutoCompleteTextView {
    private boolean mIsKeyboardVisible;
    public CustomAutoCompleteTextView(Context context) {
        super(context);
    }
    public CustomAutoCompleteTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
    }

    public CustomAutoCompleteTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        // TODO Auto-generated constructor stub
    }
    @Override
    protected void onFocusChanged(boolean focused, int direction, Rect previouslyFocusedRect) {
        super.onFocusChanged(focused, direction, previouslyFocusedRect);

        mIsKeyboardVisible = focused;
    }

    @Override
    protected void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter);
        mIsKeyboardVisible = true;
    }

    @Override
    public boolean onKeyPreIme(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && isPopupShowing()) {
            InputMethodManager inputManager = (InputMethodManager) getContext().getSystemService(
                    Context.INPUT_METHOD_SERVICE);
            // inputManager.isAcceptingText() will not work because view is still focused.
            if (mIsKeyboardVisible) { // Is keyboard visible?
                // Hide keyboard.
                inputManager.hideSoftInputFromWindow(getWindowToken(), 0);
                mIsKeyboardVisible = false;

                // Consume event.
                return true;
            } else {
                // Do nothing.
            }
        }

        return super.onKeyPreIme(keyCode, event);
    }
}