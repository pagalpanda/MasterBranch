package common.barter.com.barterapp.postad;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import common.barter.com.barterapp.AbstractFragment;
import common.barter.com.barterapp.CommonResources;
import common.barter.com.barterapp.CommonUtil;
import common.barter.com.barterapp.Home.HomeFragment;
import common.barter.com.barterapp.JSONParser;
import common.barter.com.barterapp.LocationsDialog;
import common.barter.com.barterapp.MessagesString;
import common.barter.com.barterapp.PhotosGridViewAdapter;
import common.barter.com.barterapp.Post;
import common.barter.com.barterapp.R;
import common.barter.com.barterapp.SubCategory.SubCategoryFragment;
import common.barter.com.barterapp.globalhome.GlobalHome;


public class PostAdA extends AbstractFragment {

    private static final int CAMERA_REQUEST = 0;
    private static final int GALLERY_REQUEST = 1;
    private static final int MAX_NUM_OF_IMAGES = 6;
    private ImageView imageView;
    GridView photosGrid;
    ArrayList<Bitmap> photos;
    static int counterNumberOfPhotos;
    GlobalHome activity;
    public PhotosGridViewAdapter getAdapter() {
        return adapter;
    }

    PhotosGridViewAdapter adapter;
    Activity context;
    TextView tvRemovePhoto;
    Button btnSelectLocality;
    Button btnSelectCity;
    Button btnSelectCategory;
    Button btnSelectSubCategory;
    ArrayList<String> listOfSubCategories;
    LocationsDialog ld;
    static boolean isEdited;
    Button btnPostAd;
    ProgressBar spinner;
    private PhotosGalleryDialog addPhotosDialog;
    private ProgressDialog pDialog;

    JSONParser jsonParser = new JSONParser();

    EditText etTitlePost;
    EditText etDescriptionPost;
    //Bitmap primaryImage;


    private String title;
    private String description;
    private String subCategory;
    private String city;
    private String locality;
    private ImageButton photoButton;

    private PostAdAPresenter presenter;




    public void setCategory(String category) {
        this.category = category;
    }

    private String category;
    //private Context context;

    Post post;
    String postId;
    String mode;
    public static String sMode;// created to handle the back action in the activity


    public static SubCategoryFragment newInstance(String param1, String param2) {
        SubCategoryFragment fragment = new SubCategoryFragment();
        Bundle args = new Bundle();
        args.putString("param1", param1);
        args.putString("param2", param2);
        fragment.setArguments(args);
        return fragment;
    }


    public PostAdA() {
        // Required empty public constructor
    }
    public PostAdA(String mode, Post post, String post_Id, ArrayList<Bitmap> photos) {
        this();
        this.mode=mode;
        this.post=post;
        this.postId=post_Id;
        this.photos=photos;
        sMode = mode;
        // Required empty public constructor
    }

    public PostAdA(String mode) {
        // Required empty public constructor
        this.mode = mode;
        sMode = mode;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
    }


    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        if (menu.findItem(R.id.action_search) != null)
            menu.findItem(R.id.action_search).setVisible(false);
        if (menu.findItem(R.id.action_edit_post) != null)
            menu.findItem(R.id.action_edit_post).setVisible(false);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return false;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.activity_post_ad, container, false);
        activity = ((GlobalHome)getActivity());
        setHasOptionsMenu(true);
        initializeWidgets(rootView);
        if(presenter == null) {
            presenter = new PostAdAPresenter();
        }
        presenter.onLoadView(this);
        presenter.fillDetailsBasedOnMode(mode);
        presenter.getLocalitiesBasedOnLocation(btnSelectCity.getText().toString());

        setActionListeners();
        return rootView;
    }

    private void setActionListeners() {
        photoButton.setOnClickListener(photoButtonOnClickListener);
        photosGrid.setOnItemClickListener(removePhotoListener);
        btnSelectCity.setOnClickListener(selectCityOnClickListener);
        btnSelectLocality.setOnClickListener(onLocalitySelectListener);
        btnSelectCategory.setOnClickListener(onCategorySelectListener);
        btnSelectSubCategory.setOnClickListener(onSubCategorySelectListener);
        btnPostAd.setOnClickListener(onPostAdClickListener);
    }




    void setDetailsForPostMode() {
        setActionBarTitle("Post Ad");
    }

    void setDetailsForEditMode() {
        etTitlePost.setText(post.getTitle());
        etDescriptionPost.setText(post.getDescription());
        btnSelectCity.setText(post.getCity());
        btnSelectLocality.setText(post.getLocality());
        btnSelectCategory.setText(post.getCategory());
        btnSelectSubCategory.setVisibility(View.VISIBLE);
        btnSelectSubCategory.setText(post.getSubCategory());
        btnPostAd.setText("Save");
        if(photos !=null && photos.size()>0){
            tvRemovePhoto.setVisibility(View.VISIBLE);
        }

        ((GlobalHome) getActivity()).setActionBarTitle("Edit Post");
    }

    private boolean isInEditMode() {
        return "edit".equalsIgnoreCase(mode);
    }


    private void initializeWidgets(View rootView) {
        tvRemovePhoto = (TextView)rootView.findViewById(R.id.tvRemovePhoto);
        tvRemovePhoto.setVisibility(View.INVISIBLE);
        photoButton = (ImageButton) rootView.findViewById(R.id.buttonClickImage);


        if(!isInEditMode()) {
            photos = new ArrayList<Bitmap>();
        }
        photosGrid = (GridView)rootView.findViewById(R.id.gvPhotos);
        adapter = new PhotosGridViewAdapter((GlobalHome)getActivity(), photos);
        photosGrid.setAdapter(adapter);

        etTitlePost = (EditText)rootView.findViewById(R.id.etItemNamePost);
        etDescriptionPost = (EditText)rootView.findViewById(R.id.etItemDescriptionPost);
        btnSelectCity = (Button)rootView.findViewById(R.id.btnCityDropDown);
        btnSelectCity.setText(new CommonResources(getContext()).readLocation());
        btnSelectLocality = (Button)rootView.findViewById(R.id.btnlocalityDropDown);
        btnSelectLocality.setText(MessagesString.HINT_LOCALITY);
        btnSelectCategory = (Button)rootView.findViewById(R.id.btnCategotyDropDown);
        btnSelectCategory.setText(MessagesString.HINT_CATEGORY);
        btnSelectSubCategory = (Button)rootView.findViewById(R.id.btnSubCategoryDropDown);
        btnSelectSubCategory.setText(MessagesString.HINT_SUBCATEGORY);
        btnSelectSubCategory.setVisibility(View.INVISIBLE);
        btnPostAd = (Button)rootView.findViewById(R.id.btnPostAd);

        spinner = (ProgressBar)rootView.findViewById(R.id.progressBarPostAd);
        spinner.setVisibility(View.GONE);

    }

    void setTextToLocationButton(String text){
        btnSelectCity.setText(text);
    }

    View.OnClickListener onPostAdClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            hideKeybaord();
            title = etTitlePost.getText().toString();
            description = etDescriptionPost.getText().toString();
            subCategory = btnSelectSubCategory.getText().toString();
            city = btnSelectCity.getText().toString();
            locality = btnSelectLocality.getText().toString();
            category = btnSelectCategory.getText().toString();
            presenter.onPostAdClick(title, description,category, subCategory, city,locality, photos, postId, mode);

        }
    };



    View.OnClickListener onSubCategorySelectListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            listOfSubCategories = new ArrayList<String>();
            String selectedCategory = (btnSelectCategory.getText()).toString();
            listOfSubCategories = presenter.getSubCategories(selectedCategory);
            ld = new LocationsDialog(getActivity(),getContext(), null, listOfSubCategories, false);
            ld.show();
            AutoCompleteTextView actv = ld.getAutoCompleteTextView();
            TextView tvLocationDialogText = ld.getTvLocationDialogText();
            Button btnSetCurrentLocation = ld.getBtnSetCurrentLocation();
            actv.setOnItemClickListener(subCategorySelectionListener);
            actv.setHint(MessagesString.HINT_SUBCATEGORY);
            btnSetCurrentLocation.setVisibility(View.INVISIBLE);
            tvLocationDialogText.setText(MessagesString.DIALOG_TITLE_TEXT_SUBCATEGORY);

        }
    };

    AdapterView.OnItemClickListener subCategorySelectionListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            btnSelectSubCategory.setText((String) parent.getItemAtPosition(position));
            ld.cancel();
        }

    };
    View.OnClickListener onCategorySelectListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            final ArrayList listOfCategories = presenter.getListOfCategories();
            ld = new LocationsDialog(getActivity(), getContext(), null, listOfCategories, true);
            ld.show();
            AutoCompleteTextView actv = ld.getAutoCompleteTextView();
            TextView tvLocationDialogText = ld.getTvLocationDialogText();
            Button btnSetCurrentLocation = ld.getBtnSetCurrentLocation();

            actv.setOnItemClickListener(categorySelectionListener);
            actv.setHint(MessagesString.HINT_CATEGORY);
            btnSetCurrentLocation.setVisibility(View.INVISIBLE);
            tvLocationDialogText.setText(MessagesString.DIALOG_TITLE_TEXT_CATEGORY);

        }
    };
    AdapterView.OnItemClickListener categorySelectionListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            btnSelectCategory.setText((String) parent.getItemAtPosition(position));
            btnSelectSubCategory.setText(MessagesString.HINT_SUBCATEGORY);
            ld.cancel();
            btnSelectSubCategory.setVisibility(View.VISIBLE);
        }

    };

    View.OnClickListener photoButtonOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            presenter.onCameraIconClick();

        }
    };

    void showPhotoSelectionOptions() {
        addPhotosDialog = new PhotosGalleryDialog((GlobalHome) getActivity());
        addPhotosDialog.show();
        addPhotosDialog.getClickPhoto().setOnClickListener(onClickPhotoListener);
        addPhotosDialog.getPickFromGallery().setOnClickListener(onClickPickFromGalleryListener);
    }

    View.OnClickListener onClickPhotoListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            selectOrClickPhoto(CAMERA_REQUEST);

        }
    };

    View.OnClickListener onClickPickFromGalleryListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            selectOrClickPhoto(GALLERY_REQUEST);

        }
    };
    View.OnClickListener selectCityOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            ld = new LocationsDialog(getActivity(), getContext(), null, CommonResources.getListOfCities(), false);
            ld.show();
            AutoCompleteTextView actv = ld.getAutoCompleteTextView();
            TextView tvLocationDialogText = ld.getTvLocationDialogText();
            Button btnSetCurrentLocation = ld.getBtnSetCurrentLocation();
            actv.setOnItemClickListener(onLocationSelectListener);
            actv.setHint(MessagesString.HINT_CITY);
            //// TODO: 21-05-2016 make the current location button visible and implement functionality
            btnSetCurrentLocation.setVisibility(View.INVISIBLE);
            btnSetCurrentLocation.setText(MessagesString.LOCATION_DIALOG_BUTTON_TEXT);
            tvLocationDialogText.setText(MessagesString.DIALOG_TITLE_TEXT_CITY);
        }
    };

    AdapterView.OnItemClickListener onLocationSelectListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            String cityName = (String) parent.getItemAtPosition(position);
            presenter.onLocationSelect(cityName);
            ld.cancel();
            ((GlobalHome) getActivity()).getmAdapter().notifyDataSetChanged();
        }
    };

    AdapterView.OnItemClickListener removePhotoListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            presenter.onRemovePhotoClick(position, photos.size());

        }
    };

    View.OnClickListener onLocalitySelectListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            ArrayList<String> listOfLocalities = CommonResources.getListOfLocalities();
            ld = new LocationsDialog(getActivity(), getContext(), null, listOfLocalities, true);
            ld.show();
            AutoCompleteTextView actv = ld.getAutoCompleteTextView();
            TextView tvLocationDialogText = ld.getTvLocationDialogText();
            Button btnSetCurrentLocation = ld.getBtnSetCurrentLocation();
            actv.setOnItemClickListener(onLocalitySelection);
            actv.setHint(MessagesString.HINT_LOCALITY);
            btnSetCurrentLocation.setVisibility(View.INVISIBLE);
            tvLocationDialogText.setText(MessagesString.DIALOG_TITLE_TEXT_LOCALITY);


        }
    };
    AdapterView.OnItemClickListener onLocalitySelection = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            btnSelectLocality.setText((String) parent.getItemAtPosition(position));
            ld.cancel();
        }

    };

    public boolean validateInput(){

        title = etTitlePost.getText().toString();
        description = etDescriptionPost.getText().toString();
        subCategory = btnSelectSubCategory.getText().toString();
        city = btnSelectCity.getText().toString();
        locality = btnSelectLocality.getText().toString();
        category = btnSelectCategory.getText().toString();
         if( "".equals(title) || "".equals(description) || MessagesString.HINT_SUBCATEGORY.equalsIgnoreCase(subCategory) || MessagesString.HINT_SUBCATEGORY.equalsIgnoreCase(subCategory) || MessagesString.HINT_CITY.equalsIgnoreCase(city) || MessagesString.HINT_LOCALITY.equalsIgnoreCase(locality)){
            Toast.makeText(getContext(),"All fields are mandatory",Toast.LENGTH_SHORT).show();
            return false;
        }




        return true;
    }

    void removePhoto(int position) {

        photos.remove(position);
        adapter.notifyDataSetChanged();

    }

    void setVisibilityOfRemovePhotosText(boolean visible) {
        tvRemovePhoto.setVisibility(visible ? View.VISIBLE : View.INVISIBLE);
    }

    private void selectOrClickPhoto(int selection){
        if(photos.size() < MAX_NUM_OF_IMAGES) {
            Intent intent;
            if(selection == CAMERA_REQUEST){
                intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
            }else{
                intent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            }
            startActivityForResult(intent,selection);
        }else {
            CommonUtil.flash(getContext(), MessagesString.MAXIMUM_LIMIT_OF_IMAGE_UPLOAD);
        }


        addPhotosDialog.cancel();

    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Bitmap photo = null;
        if (requestCode == CAMERA_REQUEST && resultCode == getActivity().RESULT_OK) {
            photo = (Bitmap) data.getExtras().get("data");
        }else if(requestCode == GALLERY_REQUEST && resultCode == getActivity().RESULT_OK) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};
            Cursor cursor = getActivity().getContentResolver().query(
                    selectedImage, filePathColumn, null, null, null);
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String filePath = cursor.getString(columnIndex);
            cursor.close();
            photo = BitmapFactory.decodeFile(filePath);
        }
        if(photo != null) {
            Bitmap compressedPhoto = getResizedBitmap(photo);
            photos.add(compressedPhoto);
            counterNumberOfPhotos++;
            adapter.notifyDataSetChanged();

            if(photos.size() > 0){
                tvRemovePhoto.setVisibility(View.VISIBLE);
            }
        }
    }

    public Bitmap getResizedBitmap(Bitmap image) {
        int width = image.getWidth();
        int height = image.getHeight();

        if(width>300){
            width = 300;//image.getWidth();
        }
        if(height > 300){
            height =300;//image.getHeight();
        }
        return Bitmap.createScaledBitmap(image, width, height, true);
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

    @Override
    public void navigate() {
        Fragment fragment = null;
        fragment=new HomeFragment();
        if(fragment!=null)
        {
            getFragmentManager().beginTransaction()
                    .replace(R.id.frame_container, fragment).commit();
        }
        else
        {
            // error in creating fragment
            Log.e("MainActivity", "Error in creating fragment");
        }
    }

    public void hideLocalityButton() {
        btnSelectLocality.setVisibility(View.INVISIBLE);
    }

    public void showLocalityButton() {
        btnSelectLocality.setVisibility(View.VISIBLE);
    }





}
