package common.barter.com.barterapp;

import android.app.Activity;
import android.app.Dialog;
import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Rect;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Collections;


class LocationsDialog extends Dialog{

    SharedPreferences prefs;

    private Context context;
    private Button search, cancel;
    private EditText text;
    private LocationsDialog thisDialog;
    private ArrayAdapter<String> adapter;
    ArrayList list_of_items;
    RecyclerView mRecyclerView;
    CustomAutoCompleteTextView autoCompleteTextView;

    public TextView getTvLocationDialogText() {
        return tvLocationDialogText;
    }

    public Button getBtnSetCurrentLocation() {
        return btnSetCurrentLocation;
    }

    TextView tvLocationDialogText;
    Button btnSetCurrentLocation;
    Boolean isClickedFromLocality;
    private String localityName;
    Activity activity;


    public LocationsDialog(Activity activity,Context context, RecyclerView mRecyclerView, ArrayList<String> list_of_Cities, Boolean isClickedFromLocality) {
        super(context);
        // TODO Auto-generated constructor stub
        this.context = context;
        this.thisDialog = this;
        this.mRecyclerView = mRecyclerView;
        this.list_of_items = list_of_Cities;
        this.isClickedFromLocality = isClickedFromLocality;
        this.activity = activity;
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);// This brings the list to appear on top of the keyboard

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);

        setContentView(R.layout.dialog_locations);
        prefs  = PreferenceManager.getDefaultSharedPreferences(context);

        getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.MATCH_PARENT);



        initalize();
    }

    private void initalize() {
        // TODO Auto-generated method stub


        //setListOfCities();
        autoCompleteTextView = (CustomAutoCompleteTextView)findViewById(R.id.actvLocatioNames);
        tvLocationDialogText = (TextView)findViewById(R.id.tvTitleOfLocationsDialog);
        btnSetCurrentLocation = (Button)findViewById(R.id.btn_set_current_location);
        if(list_of_items != null) {
            adapter = new ArrayAdapter<String>(getContext(), R.layout.item_list_locations, list_of_items);
//            adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_dropdown_item_1line, list_of_Cities) {
//                @Override
//                public View getView(int position, View convertView, ViewGroup parent) {
//
//                    View v = super.getView(position, convertView, parent);
//
//                    v.setOnTouchListener(new View.OnTouchListener() {
//
//                        @Override
//                        public boolean onTouch(View v, MotionEvent event) {
//                            View view = activity.getCurrentFocus();
//                            if (event.getAction() == MotionEvent.ACTION_DOWN) {
//                                InputMethodManager imm = (InputMethodManager) activity
//                                        .getSystemService(
//                                                Context.INPUT_METHOD_SERVICE);
//                                imm.hideSoftInputFromWindow(
//                                        view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
//                            }
//
//                            return true;
//                        }
//                    });
//                    return v;
//
//                }
//            };
        }
            autoCompleteTextView.setThreshold(0);

        //Set adapter to AutoCompleteTextView
        autoCompleteTextView.setAdapter(adapter);
        autoCompleteTextView.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus)
                    autoCompleteTextView.showDropDown();


            }


        });






        autoCompleteTextView.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                autoCompleteTextView.showDropDown();
                return false;
            }
        });



//        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                if (!isClickedFromLocality) {
//                    String cityName = (String) list_of_items.get(position);
//                    saveToSharedPrefs("location", cityName);
//                    GlobalHome.location = cityName;
//                    if (mRecyclerView != null)
//                        mRecyclerView.getAdapter().notifyDataSetChanged();
//
//                }
//                thisDialog.cancel();
//            }
//        });


    }

    public AutoCompleteTextView getAutoCompleteTextView(){
        //autoCompleteTextView.setDropDownHeight(LinearLayout.LayoutParams.MATCH_PARENT);
        //autoCompleteTextView.setPivotY(0);

        autoCompleteTextView.setMinLines(1);
        return autoCompleteTextView;
    }

//    private void setListOfCities() {
//        list_of_Cities = new ArrayList<String>();
//        list_of_Cities.add("Hyderabad");
//        list_of_Cities.add("Secunderabad");
//        list_of_Cities.add("Mohali");
//        list_of_Cities.add("Bangalore");
//        list_of_Cities.add("Mumbai");
//        list_of_Cities.add("Delhi");
//        list_of_Cities.add("Bhubaneswar");
//
//        Collections.sort(list_of_Cities);
//
//    }

    Object obj;
    public  Object loadFromSharedPrefs(String key){
        Gson gson = new Gson();
        obj = null;
        if(prefs != null) {
            String json = prefs.getString(key, "");

            obj = gson.fromJson(json, String.class);
            //obj = gson.fromJson(json, Object.class);

        }
        return obj;
    }

    public void saveToSharedPrefs(String key, Object objToSave){
        SharedPreferences.Editor editor = prefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(objToSave,String.class);
        editor.putString(key, json);
        editor.commit();
    }


    public String getLocalityName() {
        return localityName;
    }
    public void setLocalityName(String localityName) {
        this.localityName = localityName;
    }



}
