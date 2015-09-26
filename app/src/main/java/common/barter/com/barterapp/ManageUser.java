package common.barter.com.barterapp;

/**
 * Created by amitpa on 8/18/2015.
 */
import android.app.Activity;


import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

public class ManageUser extends Fragment {

    Activity context;
    private EditText etemail;
    private Button btchangePwd;
    private EditText etphone;
    private ImageButton btverify;
    private EditText etname;
    private RadioButton rbmale;
    private RadioButton rbfemale;
    private EditText etOTP;
    private Button btLogOut;
    private ImageView ivVerified;

    private TextView tvcountdown;
    private EditText etotp;
    private PopupWindow popupWindow;
    private CountDownTimer countDownTimer;
    private boolean timerHasStarted = false;
    private final long startTime = 30 * 1000;
    private final long interval = 1 * 1000;

    private DialogFragment dialogFragment;

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        if (menu.findItem(R.id.action_search) != null)
            menu.findItem(R.id.action_search).setVisible(false);
//        if (menu.findItem(R.id.action_sign_up) != null)
//            menu.findItem(R.id.action_sign_up).setVisible(true);
    }

    public ManageUser( ){


    }
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.manage_user, container, false);
//        MenuItem item = menu.findItem(R.id.my_item);
//        item.setVisible(false);

        setHasOptionsMenu(true);

        etemail = (EditText)rootView.findViewById(R.id.etEmailIdlogin);
        etphone = (EditText)rootView.findViewById(R.id.etPhone);
        etname = (EditText)rootView.findViewById(R.id.etName);
        btchangePwd = (Button)rootView.findViewById(R.id.btChangePwd);
        btverify = (ImageButton)rootView.findViewById(R.id.btVerify);
        rbmale = (RadioButton)rootView.findViewById(R.id.rbMale);
        rbfemale = (RadioButton)rootView.findViewById(R.id.rbFemale);
        etOTP = (EditText)rootView.findViewById(R.id.etOTP);
        btLogOut = (Button)rootView.findViewById(R.id.btSaveManageUser);
        ivVerified= (ImageView)rootView.findViewById(R.id.ivVerified);
        getDetails();

        btverify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //etOTP.setVisibility(View.VISIBLE);
                flash("Button clicked");
                LoginDetails.getInstance().setIsverifying(true);
                //setPopUp(inflater);
                setDialogFragment();

            }
        });

        btchangePwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });

        return rootView;
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        context=activity;

    }

    public boolean validateInput() {

        return true;
    }
    public void getDetails() {


//        etemail.setText("asdasd@ghgg.com");
//        etname.setText("vvbvvbvb");
//        etphone.setText("7032910032"); // Test
//        btverify.setVisibility(View.VISIBLE);
//        rbmale.setChecked(true);
//
//        Commented to test

        etemail.setText(LoginDetails.getInstance().getEmail());
        etname.setText(LoginDetails.getInstance().getPersonName());
        etphone.setText(LoginDetails.getInstance().getMobilenum()); // Test
        if((LoginDetails.getInstance().getGender())!=null)
        {
            if (LoginDetails.getInstance().getGender().equalsIgnoreCase("M"))
            {
                rbmale.setChecked(true);
            }

            if (LoginDetails.getInstance().getGender().equalsIgnoreCase("F"))
            {
                rbfemale.setChecked(true);
            }
        }
        if (LoginDetails.getInstance().getMobilenum() !=null)
        {
            if (LoginDetails.getInstance().getMob_verified().equals("0"))
            {
                btverify.setVisibility(View.VISIBLE);
            }
            else
            {
                ivVerified.setVisibility(View.VISIBLE);
            }

        }

    }


    public void setDialogFragment()
    {
        Fragment fragment = new OTPFragment();

        if (fragment != null) {

//            fragmentManager.beginTransaction()
//                    .replace(R.id.frame_container, fragment).addToBackStack("Manage User").commit();
            getFragmentManager().beginTransaction()
                    .replace(R.id.frame_container, fragment).commit();

        } else {
            // error in creating fragment
            Log.e("MainActivity", "Error in creating Manage user fragment");
        }


    }

    public class MyCountDownTimer extends CountDownTimer {

        public  MyCountDownTimer(long startTime,long interval)
        {
            super(startTime, interval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            tvcountdown.setText("" + millisUntilFinished / 1000);
        }

        @Override
        public void onFinish() {
            tvcountdown.setText("Time's up!");
            popupWindow.dismiss();
        }
    }
    public  void flash(String message){
        Toast.makeText(context,message,Toast.LENGTH_SHORT).show();
    }

}