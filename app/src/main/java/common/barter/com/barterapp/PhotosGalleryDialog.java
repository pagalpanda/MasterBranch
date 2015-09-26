package common.barter.com.barterapp;

/**
 * Created by amitpa on 8/23/2015.
 */
import android.app.Dialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Collections;


class PhotosGalleryDialog extends Dialog{



    private Button clickPhoto, pickFromGallery, cancel;
    private EditText text;
    private PhotosGalleryDialog thisDialog;
    private ArrayAdapter<String> adapter;
    ArrayList list_of_Cities;
    RecyclerView mRecyclerView;

    public PhotosGalleryDialog(GlobalHome context) {
        super(context);
        // TODO Auto-generated constructor stub

        this.thisDialog = this;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_photos_gallery);

        getWindow().setLayout(android.view.ViewGroup.LayoutParams.FILL_PARENT,
                android.view.ViewGroup.LayoutParams.WRAP_CONTENT);
        initalize();
    }

    private void initalize() {
        // TODO Auto-generated method stub



        clickPhoto = (Button)findViewById(R.id.btnClickPhoto);
        pickFromGallery = (Button)findViewById(R.id.btnChooseFromGallery);
        cancel = (Button)findViewById(R.id.btnCancelGallery);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                thisDialog.cancel();
            }
        });

    }
    public Button getClickPhoto() {
        return clickPhoto;
    }

    public Button getPickFromGallery() {
        return pickFromGallery;
    }


    }

