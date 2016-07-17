package common.barter.com.barterapp.postad;


import android.app.Activity;
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
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import common.barter.com.barterapp.AbstractFragment;
import common.barter.com.barterapp.CommonResources;
import common.barter.com.barterapp.CommonUtil;
import common.barter.com.barterapp.Home.HomeFragment;
import common.barter.com.barterapp.LocationsDialog;
import common.barter.com.barterapp.MessagesString;
import common.barter.com.barterapp.R;
import common.barter.com.barterapp.data.domain.NewPost;
import common.barter.com.barterapp.globalhome.GlobalHome;
import common.barter.com.barterapp.posts.NavigationMode;


public class PostAdA extends AbstractFragment {

    private static final int MAX_NUM_OF_IMAGES = 6;
    private GridView photosGrid;
    private ArrayList<Bitmap> photos;
    private TextView tvRemovePhoto;
    private Button btnSelectLocality;
    private Button btnSelectCity;
    private Button btnSelectCategory;
    private Button btnSelectSubCategory;
    private LocationsDialog ld;
    private Button btnPostAd;
    private ProgressBar spinner;
    private PhotosGalleryDialog addPhotosDialog;
    private EditText etTitlePost;
    private EditText etDescriptionPost;
    private ImageButton photoButton;

    private PostAdAPresenter presenter;
    private NewPost post;
    private long postId;
    private String mode;
    public static String sMode;

    public void setInputArguments() {
        Bundle bundle = getArguments();
        this.sMode = bundle.getString(MessagesString.CAME_FROM);
        this.mode = bundle.getString(MessagesString.NAV_MODE);
        this.post = (NewPost) bundle.getSerializable(MessagesString.POST);
        this.photos = (ArrayList<Bitmap>) bundle.getSerializable(MessagesString.IMAGES);
        this.postId = this.post.getId();
    }

    public long getPostId() {
        return postId;
    }

    public ArrayList<Bitmap> getPhotos() {
        return photos;
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
        if (menu.findItem(R.id.action_search) != null){
            menu.findItem(R.id.action_search).setVisible(false);
        }
        if (menu.findItem(R.id.action_edit_post) != null){
            menu.findItem(R.id.action_edit_post).setVisible(false);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return false;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_post_ad, container, false);
        setHasOptionsMenu(true);
        initializeWidgets(rootView);
        setInputArguments();
        setPresenter();
        setAdapters();
        presenter.setDetailsBasedOnMode(mode);
        presenter.getLocalitiesBasedOnLocation(btnSelectCity.getText().toString());
        setActionListeners();
        return rootView;
    }

    private void setAdapters() {
        photosGrid.setAdapter(presenter.getPhotosGridViewAdapter());
    }

    public void setPresenter() {
        if(presenter == null) {
            presenter = new PostAdAPresenter();
        }
        presenter.setFragment(this);
    }

    private void setActionListeners() {
        photoButton.setOnClickListener(presenter.getListener().getPhotoButtonOnClickListener());
        photosGrid.setOnItemClickListener(presenter.getListener().getRemovePhotoListener());
        btnSelectCity.setOnClickListener(presenter.getListener().getSelectCityOnClickListener());
        btnSelectLocality.setOnClickListener(presenter.getListener().getOnLocalitySelectListener());
        btnSelectCategory.setOnClickListener(presenter.getListener().getOnCategorySelectListener());
        btnSelectSubCategory.setOnClickListener(presenter.getListener().getOnSubCategorySelectListener());
        btnPostAd.setOnClickListener(presenter.getListener().getOnPostAdClickListener());
    }

    void setDetailsForPostMode() {
        setActionBarTitle(MessagesString.POST_AD);
    }

    void setDetailsForEditMode() {
        etTitlePost.setText(post.getTitle());
        etDescriptionPost.setText(post.getDescription());
        btnSelectCity.setText(post.getCity());
        btnSelectLocality.setText(post.getLocality());
        btnSelectCategory.setText(post.getCategory());
        btnSelectSubCategory.setVisibility(View.VISIBLE);
        btnSelectSubCategory.setText(post.getSubCategory());
        btnPostAd.setText(MessagesString.SAVE);
        if(photos !=null && photos.size()>0){
            tvRemovePhoto.setVisibility(View.VISIBLE);
        }
        setActionBarTitle(MessagesString.EDIT_POST);
    }

    private boolean isInEditMode() {
        return NavigationMode.EDIT.name().equalsIgnoreCase(mode);
    }


    private void initializeWidgets(View rootView) {
        tvRemovePhoto = (TextView)rootView.findViewById(R.id.tvRemovePhoto);
        tvRemovePhoto.setVisibility(View.INVISIBLE);
        photoButton = (ImageButton) rootView.findViewById(R.id.buttonClickImage);
        if(!isInEditMode()) {
            photos = new ArrayList<>();
        }
        photosGrid = (GridView)rootView.findViewById(R.id.gvPhotos);
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

    void showPhotoSelectionOptions() {
        addPhotosDialog = new PhotosGalleryDialog((GlobalHome) getActivity());
        addPhotosDialog.show();
        addPhotosDialog.getClickPhoto().setOnClickListener(presenter.getListener().getOnClickPhotoListener());
        addPhotosDialog.getPickFromGallery().setOnClickListener(presenter.getListener().getOnClickPickFromGalleryListener());
    }

    public boolean validateInput(){
        String title = etTitlePost.getText().toString();
        String description = etDescriptionPost.getText().toString();
        String subCategory = btnSelectSubCategory.getText().toString();
        String city = btnSelectCity.getText().toString();
        String locality = btnSelectLocality.getText().toString();
        if( "".equals(title) || "".equals(description) || MessagesString.HINT_SUBCATEGORY.equalsIgnoreCase(subCategory) || MessagesString.HINT_SUBCATEGORY.equalsIgnoreCase(subCategory) || MessagesString.HINT_CITY.equalsIgnoreCase(city) || MessagesString.HINT_LOCALITY.equalsIgnoreCase(locality)){
            Toast.makeText(getContext(),MessagesString.ALL_FIELDS_ARE_MANDATORY,Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    void removePhoto(int position) {
        photos.remove(position);
        presenter.notifyDataSetChanged();
        if(photos.isEmpty()){
            this.setVisibilityOfRemovePhotosText(false);
        }
    }

    void setVisibilityOfRemovePhotosText(boolean visible) {
        tvRemovePhoto.setVisibility(visible ? View.VISIBLE : View.INVISIBLE);
    }

    public void selectOrClickPhoto(int selection){
        if(photos.size() < MAX_NUM_OF_IMAGES) {
            Intent intent;
            if(selection == MessagesString.CAMERA_REQUEST){
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
        if (requestCode == MessagesString.CAMERA_REQUEST && resultCode == getActivity().RESULT_OK) {
            photo = (Bitmap) data.getExtras().get("data");
        }else if(requestCode == MessagesString.GALLERY_REQUEST && resultCode == getActivity().RESULT_OK) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};
            Cursor cursor = getActivity().getContentResolver().query(selectedImage, filePathColumn, null, null, null);
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String filePath = cursor.getString(columnIndex);
            cursor.close();
            photo = BitmapFactory.decodeFile(filePath);
        }
        if(photo != null) {
            Bitmap compressedPhoto = getResizedBitmap(photo);
            photos.add(compressedPhoto);
            presenter.notifyDataSetChanged();
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
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void navigate() {
        Fragment fragment=new HomeFragment();
        if(fragment!=null){
            getFragmentManager().beginTransaction()
                    .replace(R.id.frame_container, fragment).commit();
        }
        else{
            Log.e("MainActivity", "Error in creating fragment");
        }
    }

    public void hideLocalityButton() {
        btnSelectLocality.setVisibility(View.INVISIBLE);
    }

    public void showLocalityButton() {
        btnSelectLocality.setVisibility(View.VISIBLE);
    }

    public void createAndShowLocationDialogForCity() {
        this.createAndShowLocationDialog(MessagesString.CITY);
    }
    public void createAndShowLocationDialog(String mode) {
        AutoCompleteTextView actv;
        TextView tvLocationDialogText;
        Button btnSetCurrentLocation;
        switch (mode){
            case "city":
                ArrayList<String> listOfCities = CommonResources.getListOfCities();
                ld = new LocationsDialog(getActivity(), getContext(), null, listOfCities, false);
                ld.show();
                actv = ld.getAutoCompleteTextView();
                tvLocationDialogText = ld.getTvLocationDialogText();
                btnSetCurrentLocation = ld.getBtnSetCurrentLocation();
                actv.setOnItemClickListener(presenter.getListener().getOnLocationSelectListener());
                actv.setHint(MessagesString.HINT_CITY);
                //// TODO: 21-05-2016 make the current location button visible and implement functionality
                btnSetCurrentLocation.setVisibility(View.INVISIBLE);
                btnSetCurrentLocation.setText(MessagesString.LOCATION_DIALOG_BUTTON_TEXT);
                tvLocationDialogText.setText(MessagesString.DIALOG_TITLE_TEXT_CITY);
                break;

            case "locality":
                ArrayList<String> listOfLocalities = CommonResources.getListOfLocalities();
                ld = new LocationsDialog(getActivity(), getContext(), null, listOfLocalities, true);
                ld.show();
                actv = ld.getAutoCompleteTextView();
                tvLocationDialogText = ld.getTvLocationDialogText();
                btnSetCurrentLocation = ld.getBtnSetCurrentLocation();
                actv.setOnItemClickListener(presenter.getListener().getOnLocalitySelection());
                actv.setHint(MessagesString.HINT_LOCALITY);
                btnSetCurrentLocation.setVisibility(View.INVISIBLE);
                tvLocationDialogText.setText(MessagesString.DIALOG_TITLE_TEXT_LOCALITY);
                break;

            case "subCategory":
                String selectedCategory = (btnSelectCategory.getText()).toString();
                ArrayList<String> listOfSubCategories = presenter.getSubCategories(selectedCategory);
                ld = new LocationsDialog(getActivity(),getContext(), null, listOfSubCategories, false);
                ld.show();
                actv = ld.getAutoCompleteTextView();
                tvLocationDialogText = ld.getTvLocationDialogText();
                btnSetCurrentLocation = ld.getBtnSetCurrentLocation();
                actv.setOnItemClickListener(presenter.getListener().getSubCategorySelectionListener());
                actv.setHint(MessagesString.HINT_SUBCATEGORY);
                btnSetCurrentLocation.setVisibility(View.INVISIBLE);
                tvLocationDialogText.setText(MessagesString.DIALOG_TITLE_TEXT_SUBCATEGORY);
                break;

            case "Category":
                final ArrayList listOfCategories = presenter.getListOfCategories();
                ld = new LocationsDialog(getActivity(), getContext(), null, listOfCategories, true);
                ld.show();
                actv = ld.getAutoCompleteTextView();
                tvLocationDialogText = ld.getTvLocationDialogText();
                btnSetCurrentLocation = ld.getBtnSetCurrentLocation();
                actv.setOnItemClickListener(presenter.getListener().getCategorySelectionListener());
                actv.setHint(MessagesString.HINT_CATEGORY);
                btnSetCurrentLocation.setVisibility(View.INVISIBLE);
                tvLocationDialogText.setText(MessagesString.DIALOG_TITLE_TEXT_CATEGORY);
                break;

            default:
                break;
        }

    }

    public void createAndShowLocationDialogForLocality() {
        this.createAndShowLocationDialog(MessagesString.LOCALITY);
    }
    public void createAndShowLocationDialogForSubCategory() {
        this.createAndShowLocationDialog(MessagesString.SUBCATEGORY);
    }

    public void createAndShowLocationDialogForCategory() {
        this.createAndShowLocationDialog(MessagesString.CATEGORY);
    }

    public void onLocationSelect(String cityName) {
        this.setTextToLocationButton(cityName);
        this.showLocalityButton();
        ld.cancel();
        ((GlobalHome) getActivity()).getmAdapter().notifyDataSetChanged();
    }

    public void onLocalitySelect(String localityName) {
        btnSelectLocality.setText(localityName);
        ld.cancel();
    }

    public void onPostAdClicked() {
        hideKeybaord();
        String title = etTitlePost.getText().toString();
        String description = etDescriptionPost.getText().toString();
        String subCategory = btnSelectSubCategory.getText().toString();
        String city = btnSelectCity.getText().toString();
        String locality = btnSelectLocality.getText().toString();
        String category = btnSelectCategory.getText().toString();
        presenter.onPostAdClick(title, description,category, subCategory, city,locality, photos, String.valueOf(postId), mode);
    }

    public void onSubCategorySelect(String subCategory) {
        btnSelectSubCategory.setText(subCategory);
        ld.cancel();
    }

    public void onCategorySelect(String category) {
        btnSelectCategory.setText(category);
        btnSelectSubCategory.setText(MessagesString.HINT_SUBCATEGORY);
        ld.cancel();
        btnSelectSubCategory.setVisibility(View.VISIBLE);
    }
}
