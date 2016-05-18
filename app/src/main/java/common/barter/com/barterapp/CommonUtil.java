package common.barter.com.barterapp;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by vikram on 18/05/16.
 */
public class CommonUtil {

    public static void flash(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
}
