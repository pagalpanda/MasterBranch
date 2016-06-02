package common.barter.com.barterapp.Login;

import android.app.ProgressDialog;
import android.content.Context;

/**
 * Created by vikram on 30/05/16.
 */
public class LoginProcessDialog {
    private ProgressDialog pDialog;
    private Context context;

    public LoginProcessDialog(Context context) {
        this.context = context;
    }

    public void createProcessDialog(String msg) {
        if(pDialog==null){
            pDialog = ProgressDialog.show(context, "", msg, true, true);
        } else {
            pDialog.setMessage(msg);
        }

        if ( !(pDialog.isShowing()) )
            pDialog.show();
    }

    void dismissProcessDialog() {
        if ((pDialog!=null)&& (pDialog.isShowing()))
            pDialog.dismiss();
    }
}
