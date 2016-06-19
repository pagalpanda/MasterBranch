package common.barter.com.barterapp.manageuser;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

/**
 * Created by vikram on 04/06/16.
 */
public class ManageUserListener {

    private ManageUserPresenter manageUserPresenter;

    public ManageUserListener(ManageUserPresenter manageUserPresenter) {
        this.manageUserPresenter=manageUserPresenter;
    }

    public TextWatcher getMobileNumTextWatcher(){
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                manageUserPresenter.onMobileNumTextChanged(s);
            }

            @Override
            public void afterTextChanged(Editable s) {}
        };
    }

    public View.OnClickListener getVerifyButtonOnClickListener(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                manageUserPresenter.onVerifyButtonClicked();
            }
        };
    }

    public View.OnClickListener getChangePwdOnClickListener(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                manageUserPresenter.OnChangePwdClicked();
            }
        };
    }

    public View.OnClickListener getSaveOnClickListener(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                manageUserPresenter.onSaveClicked();
            }
        };

    }





}
