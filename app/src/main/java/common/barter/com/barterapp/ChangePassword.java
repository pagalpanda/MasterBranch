package common.barter.com.barterapp;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;


/**
 * Created by vikram on 02/10/15.
 */
public class ChangePassword extends Fragment {

    Activity context;
    private Button btSavePwd;
    private EditText etCurrentPwd;
    private EditText etNewPwd;
    private EditText etConfirmPwd;


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        if (menu.findItem(R.id.action_logout) != null) {
            menu.findItem(R.id.action_logout).setVisible(false);
            menu.removeItem(R.id.action_logout);

        }
        menu.clear();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View ChangePasswordView = inflater.inflate(R.layout.change_password, container, false);
        setHasOptionsMenu(true);
        etCurrentPwd = (EditText) ChangePasswordView.findViewById(R.id.etCurrentPwd);
        etNewPwd = (EditText) ChangePasswordView.findViewById(R.id.etNewPwd);
        etConfirmPwd = (EditText) ChangePasswordView.findViewById(R.id.etConfirmPwd);

        btSavePwd = (Button) ChangePasswordView.findViewById(R.id.btSavePwd);

        btSavePwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isValidInput())
                {
                    if(isNewPassDifferent())
                    savePwd();
                    else
                        flash("New Password can't be same as the old one!");

                }

            }
        });

//        etCurrentPwd.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View view, boolean b) {
//                if (!b) {
//                    if (!(isValidCurrentPassword())) {
//                        etCurrentPwd.setError("Please provide correct password");
//                    }
//
//                }
//            }
//        });

//        etConfirmPwd.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View view, boolean b) {
//                if (!b)
//                {
//                    if ( !(isValidConfirmPassword()) )
//                    {
//                        etCurrentPwd.setError("Please provide correct password");
//                    }
//
//                }
//            }
//        });

        return ChangePasswordView;

    }

//    private boolean isValidCurrentPassword() {
//        if (((LoginDetails.getInstance().getPassword()).equals(etCurrentPwd.getText().toString()))) {
//            return true;
//        }
//        return false;
//    }

    private boolean isValidConfirmPassword() {

        String new_pwd = etNewPwd.getText().toString();
        String confirm_pwd = etConfirmPwd.getText().toString();

        if ( confirm_pwd.equals(new_pwd))
        {
            return true;
        }

        return false;
    }

    private boolean isValidPassword(EditText et) {
        String pass = et.getText().toString();
        if (pass != null && !pass.equals("") && pass.length() > 7) {
            return true;
        }

        if (pass.length() < 8)
        {
            et.setError("Password should be 8 characters long");
        }
        else if (pass == null || pass.equals(""))
        {
            et.setError("Password should not be blank");
        }
        else
        {
            et.setError("Invalid Password");
        }
        return false;
    }

    public  void flash(String message){
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public Boolean isValidInput() {

        if ( !(isValidPassword(etCurrentPwd)) && !(isValidPassword(etNewPwd)) && !(isValidPassword(etConfirmPwd)) && isValidConfirmPassword() )
        {
            return false;
        }
        return true;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        context=activity;

    }
    @Override
    public void onDetach() {
        super.onDetach();

    }

    public void savePwd()
    {

        HashMap<String, String> params = new HashMap<String, String>();
        params.put("userid",LoginDetails.getInstance().getUserid());
        params.put("newpassword", etConfirmPwd.getText().toString());
        params.put("oldpassword", etCurrentPwd.getText().toString());
        params.put("instruction", "3");

        AsyncConnection as = new AsyncConnection(context,CommonResources.getURL("UserHandler"),"POST",params,false,null){
            public void receiveData(JSONObject json){
                try {
                    String TAG_SUCCESS = "success";
                    int success = json.getInt(TAG_SUCCESS);
                    if (success == 0) {
                        flash("Password updated");
                        getActivity().getSupportFragmentManager().popBackStack();
                        // TODO: move to Manage user
                    }
                    else if (success == 1) {
                        if ("1".equals(json.getInt("userpresent")))
                        {
                            flash("We are facing some issues. Please try again later.");
                        }
                        else
                        {
                            flash("Please enter correct password");
                            etCurrentPwd.setError("Please enter correct password");
                        }

                    }
                    else {
                        flash("We are facing some issues. Please try again later.");
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        as.execute();

    }

    public boolean isNewPassDifferent() {
        return !(etCurrentPwd.getText().toString()).equals(etConfirmPwd.getText().toString());
    }
}



