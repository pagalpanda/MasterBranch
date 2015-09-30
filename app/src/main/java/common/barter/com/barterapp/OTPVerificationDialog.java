
package common.barter.com.barterapp;

/**
 * Created by amitpa on 8/23/2015.
 */
        import android.app.Dialog;
        import android.content.Context;

        import android.os.Bundle;
        import android.view.Window;


class OTPVerificationDialog extends Dialog {




    private OTPVerificationDialog thisDialog;


    public OTPVerificationDialog(Context context) {
        super(context);
        // TODO Auto-generated constructor stub

        this.thisDialog = this;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        thisDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);// Hides the are for title bar
        setContentView(R.layout.dialog_otp_verification);

        initalize();
    }

    private void initalize() {
        // TODO Auto-generated method stub






    }



}

