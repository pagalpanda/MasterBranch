package common.barter.com.barterapp;

/**
 * Created by amitpa on 8/18/2015.
 */
import android.app.Activity;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CountDownLatch;

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
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
                etOTP.setVisibility(View.VISIBLE);

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
        etemail.setText(LoginDetails.getInstance().getEmail());
        etname.setText(LoginDetails.getInstance().getPersonName());
        etphone.setText(LoginDetails.getInstance().getBirthday()); // Test
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

}