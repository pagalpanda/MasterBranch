package common.barter.com.barterapp;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.Toast;

/**
 * Created by vikram on 18/05/16.
 */
public class CommonUtil {


    public CommonUtil() {
    }

    public static void flash(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
    public void createAndAddFragment(String tag, Class<? extends Fragment> cls,FragmentManager fragmentManager, boolean addToBackStack)
    {
        Fragment frag = fragmentManager.findFragmentByTag(tag);
        if(frag == null) {
            try {
                frag = cls.newInstance();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            // Add to the target layout id
        }
    }
    public static void getDrawableImage(Context context,View view,int drawable){
        final int sdk = android.os.Build.VERSION.SDK_INT;
        if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
            view.setBackgroundDrawable( context.getResources().getDrawable(drawable) );
        } else {
            view.setBackground( context.getResources().getDrawable(drawable));
        }
    }
    public static  int getColor(Context context,int color) {
        return context.getResources().getColor(color);
    }
    public static  int convertDpToPixel(Context context,int dps) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dps * scale + 0.5f);
    }

    public static boolean isUserLoggedIn() {
        String userid = LoginDetails.getInstance().getUserid();
        if (null == userid || "null".equalsIgnoreCase(userid) || "".equalsIgnoreCase(userid)) {
            return false;
        }
        return true;
    }
}
