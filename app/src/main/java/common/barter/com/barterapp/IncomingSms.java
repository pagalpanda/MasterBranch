package common.barter.com.barterapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by vikram on 07/09/15.
 */
public class IncomingSms extends BroadcastReceiver {
    // Get the object of SmsManager
    final SmsManager sms = SmsManager.getDefault();


    public void onReceive(Context context, Intent intent) {

        // Retrieves a map of extended data from the intent.
        final Bundle bundle = intent.getExtras();

        try {

            if (bundle != null) {

                final Object[] pdusObj = (Object[]) bundle.get("pdus");

                for (int i = 0; i < pdusObj.length; i++) {

                    SmsMessage currentMessage = SmsMessage.createFromPdu((byte[]) pdusObj[i]);
                    String phoneNumber = currentMessage.getDisplayOriginatingAddress();

                    String senderNum = phoneNumber;
                    String message = currentMessage.getDisplayMessageBody();

                   Log.i("SmsReceiver", "senderNum: " + senderNum + "; message: " + message);


                    if ((LoginDetails.getInstance().getIsverifying()) && (message.contains(context.getString(R.string.otp_string))))
                    {
                        // Show Alert
                        LoginDetails.getInstance().setOtp_received_from_device(message.substring(message.lastIndexOf(":")+1));
                        int duration = Toast.LENGTH_LONG;
                        Toast toast = Toast.makeText(context,
                                "senderNum: "+ senderNum + ", otp: " + LoginDetails.getInstance().getOtp_received_from_device(), duration);
                        toast.show();

                    }


                } // end for loop
            } // bundle is null

        } catch (Exception e) {
            //Log.e("SmsReceiver", "Exception smsReceiver" +e);

        }
    }
}
