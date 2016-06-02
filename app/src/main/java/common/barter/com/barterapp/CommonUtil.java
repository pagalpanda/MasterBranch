package common.barter.com.barterapp;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
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
}
