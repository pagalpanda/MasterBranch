
package common.barter.com.barterapp.otp;

/**
 * Created by amitpa on 8/23/2015.
 */
        import android.app.Dialog;
        import android.content.Context;

        import android.os.Bundle;
        import android.view.Window;

        import common.barter.com.barterapp.R;


public class OTPVerificationDialog extends Dialog {

    private OTPVerificationDialog thisDialog;

    public OTPVerificationDialog(Context context) {
        super(context);
        this.thisDialog = this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        thisDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);// Hides the are for title bar
        setContentView(R.layout.dialog_otp_verification);
        initalize();
    }

    private void initalize() {
    }

}

