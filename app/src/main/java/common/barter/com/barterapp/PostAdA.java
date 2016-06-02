package common.barter.com.barterapp;



        import android.app.ProgressDialog;
        import android.database.Cursor;
        import android.graphics.BitmapFactory;
        import android.net.Uri;
        import android.os.AsyncTask;
        import android.os.Build;
        import android.provider.MediaStore;
        import android.support.v4.app.Fragment;
        import android.os.Bundle;
        import android.util.Base64;
        import android.util.Log;
        import android.view.LayoutInflater;
        import android.view.Menu;
        import android.view.MenuInflater;
        import android.view.MenuItem;

        import android.app.Activity;
        import android.content.Intent;
        import android.graphics.Bitmap;
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

        import com.squareup.picasso.Picasso;

        import org.json.JSONArray;
        import org.json.JSONException;
        import org.json.JSONObject;

        import java.io.ByteArrayOutputStream;
        import java.io.IOException;
        import java.util.ArrayList;
        import java.util.HashMap;

        import common.barter.com.barterapp.Home.HomeFragment;
        import common.barter.com.barterapp.SubCategory.SubCategoryFragment;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SubCategoryFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SubCategoryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PostAdA extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private static final int CAMERA_REQUEST = 0;
    private static final int GALLERY_REQUEST = 1;
    private ImageView imageView;
    GridView photosGrid;
    ArrayList<Bitmap> photos;
    static int counterNumberOfPhotos;

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


    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    private String category;
    //private Context context;

    Post post;
    String postId;
    String mode;
    static String sMode;// created to handle the back action in the activity

    // JSON Node names
    private static final String TAG_SUCCESS = "success";


    private String uniqueUserId;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSubCategory() {
        return subCategory;
    }

    public void setSubCategory(String subCategory) {
        this.subCategory = subCategory;
    }


    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getLocality() {
        return locality;
    }

    public void setLocality(String locality) {
        this.locality = locality;
    }

    public String getUniqueUserId() {
        return (String)(new CommonResources(context).loadFromSharedPrefs("uniqueid"));
    }




    public Button getBtnSelectCity() {
        return btnSelectCity;
    }



    private OnFragmentInteractionListener mListener;
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SubCategoryFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SubCategoryFragment newInstance(String param1, String param2) {
        SubCategoryFragment fragment = new SubCategoryFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
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
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
//        if("edit".equalsIgnoreCase(mode) && isRemoving()){
//            ((GlobalHome) getActivity()).setActionBarTitle("My Posts");
//        }
    }

    public Button getBtnSelectLocality() {
        return btnSelectLocality;
    }



    public void setBtnSelectLocality(Button btnSelectLocality) {
        this.btnSelectLocality = btnSelectLocality;
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
//        if("subcategories".equalsIgnoreCase(mode)){
//            activity.getmDrawerToggle().setDrawerIndicatorEnabled(false);
//            activity.setActionBarTitle(SubCategoryFragment.selectedCategory);
//        }else if("home".equalsIgnoreCase(mode)){
//            activity.getmDrawerToggle().setDrawerIndicatorEnabled(true);
//            activity.setActionBarTitle("Home");
//        }else if("edit".equalsIgnoreCase(mode)){
//            activity.getmDrawerToggle().setDrawerIndicatorEnabled(false);
//        }

        return false;
    }

    GlobalHome activity;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.activity_post_ad, container, false);
        activity = ((GlobalHome)getActivity());
        setHasOptionsMenu(true);
        //Set the title bar text
        activity.getmDrawerToggle().setDrawerIndicatorEnabled(false);
        activity.setActionBarTitle("Post Ad");
        tvRemovePhoto = (TextView)rootView.findViewById(R.id.tvRemovePhoto);
        tvRemovePhoto.setVisibility(View.INVISIBLE);
       // this.imageView = (ImageView)rootView.findViewById(R.id.ivPhoto1);
        ImageButton photoButton = (ImageButton) rootView.findViewById(R.id.buttonClickImage);
        //activity = (GlobalHome)getActivity();
        if(!"edit".equalsIgnoreCase(mode))
            photos = new ArrayList<Bitmap>();
        photosGrid = (GridView)rootView.findViewById(R.id.gvPhotos);
        adapter = new PhotosGridViewAdapter((GlobalHome)getActivity(), photos);
        photosGrid.setAdapter(adapter);

        etTitlePost = (EditText)rootView.findViewById(R.id.etItemNamePost);
        etDescriptionPost = (EditText)rootView.findViewById(R.id.etItemDescriptionPost);

        photoButton.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                final PhotosGalleryDialog dialog = new PhotosGalleryDialog((GlobalHome) getActivity());
                dialog.show();

                dialog.getClickPhoto().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        selectOrClickPhoto(0);
                        dialog.cancel();
                    }
                });

                dialog.getPickFromGallery().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        selectOrClickPhoto(1);
                        dialog.cancel();
                    }
                });
            }
        });


        photosGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                removePhoto(position);
            }
        });


        btnSelectCity = (Button)rootView.findViewById(R.id.btnCityDropDown);
        btnSelectCity.setText(new CommonResources(getContext()).readLocation());
        btnSelectCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ld = new LocationsDialog(getActivity(), getContext(), null, CommonResources.getListOfCities(), false);
                ld.show();
                AutoCompleteTextView actv = ld.getAutoCompleteTextView();
                TextView tvLocationDialogText = ld.getTvLocationDialogText();
                Button btnSetCurrentLocation = ld.getBtnSetCurrentLocation();
                actv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        CommonResources cr = new CommonResources(context);
                        String cityName = (String) parent.getItemAtPosition(position);//String cityName = CommonResources.getListOfCities().get(position);
                        cr.saveToSharedPrefs("location", cityName);
                        GlobalHome.location = cityName;
                        btnSelectCity.setText(cityName);
                        btnSelectLocality.setVisibility(View.VISIBLE);
                        HashMap param = new HashMap();
                        param.put("", "");
                        new AsyncConnection(getContext(), CommonResources.getURL("city_jsons/" + cityName.replace(" ", "%20") + ".json").replace(".php", ""), "POST", param, true, "Fetching Localities") {
                            @Override
                            public void receiveData(JSONObject json) {
                                ArrayList<String> list = new ArrayList<String>();
                                try {
                                    JSONArray array = json.getJSONArray("locality");

                                    for (int i = 0; i < array.length(); i++) {
                                        list.add(array.getString(i));
                                    }
                                } catch (Exception e) {
                                    Log.d("Exception in locality", e.getMessage());
                                }
                                CommonResources.setListOfLocalities(list);
                            }
                        }.execute();
                        ld.cancel();
                        ((GlobalHome) getActivity()).getmAdapter().notifyDataSetChanged();

                    }
                });

                actv.setHint(MessagesString.HINT_CITY);
                btnSetCurrentLocation.setVisibility(View.VISIBLE);
                btnSetCurrentLocation.setText(MessagesString.LOCATION_DIALOG_BUTTON_TEXT);
                tvLocationDialogText.setText(MessagesString.DIALOG_TITLE_TEXT_CITY);
            }
        });



        btnSelectLocality = (Button)rootView.findViewById(R.id.btnlocalityDropDown);
        btnSelectLocality.setText(MessagesString.HINT_LOCALITY);
        if(MessagesString.LOCATION_SET_MANUALLY.equalsIgnoreCase(btnSelectCity.getText().toString())){
            btnSelectLocality.setVisibility(View.INVISIBLE);
        }else {
            HashMap param = new HashMap();
            param.put("","");
            new AsyncConnection(getContext(), CommonResources.getURL("city_jsons/" + btnSelectCity.getText().toString().replace(" ", "%20") + ".json").replace(".php",""), "POST", param, true, "Fetching Localities") {
                @Override
                public void receiveData(JSONObject json) {
                    ArrayList<String> list = new ArrayList<String>();
                    try {
                        JSONArray array = json.getJSONArray("locality");

                        for(int i = 0; i<array.length();i++){
                            list.add(array.getString(i));
                        }
                    }catch (Exception e){
                        Log.d("Exception in locality",e.getMessage());
                    }
                    CommonResources.setListOfLocalities(list);
                }
            }.execute();
            btnSelectLocality.setVisibility(View.VISIBLE);
        }



        btnSelectLocality.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<String> listOfLocalities = new ArrayList<String>();
                listOfLocalities = CommonResources.getListOfLocalities(new CommonResources(context).readLocation());
                ld = new LocationsDialog(getActivity(), getContext(), null, listOfLocalities, true);
                ld.show();
                AutoCompleteTextView actv = ld.getAutoCompleteTextView();
                TextView tvLocationDialogText = ld.getTvLocationDialogText();
                Button btnSetCurrentLocation = ld.getBtnSetCurrentLocation();
                actv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        btnSelectLocality.setText((String) parent.getItemAtPosition(position));
                        ld.cancel();
                    }

                });

                actv.setHint(MessagesString.HINT_LOCALITY);
                btnSetCurrentLocation.setVisibility(View.INVISIBLE);
                //btnSetCurrentLocation.setText(MessagesString.LOCATION_DIALOG_BUTTON_TEXT);
                tvLocationDialogText.setText(MessagesString.DIALOG_TITLE_TEXT_LOCALITY);


            }
        });

        btnSelectCategory = (Button)rootView.findViewById(R.id.btnCategotyDropDown);
        btnSelectCategory.setText(MessagesString.HINT_CATEGORY);

        btnSelectCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ArrayList<String> listOfcategories = new ArrayList<String>();
                for (Categories c : CommonResources.categories) {
                    listOfcategories.add(c.getName());
                }
                ld = new LocationsDialog(getActivity(), getContext(), null, listOfcategories, true);
                ld.show();
                AutoCompleteTextView actv = ld.getAutoCompleteTextView();
                TextView tvLocationDialogText = ld.getTvLocationDialogText();
                Button btnSetCurrentLocation = ld.getBtnSetCurrentLocation();
                actv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        btnSelectCategory.setText((String) parent.getItemAtPosition(position));
                        btnSelectSubCategory.setText(MessagesString.HINT_SUBCATEGORY);
                        ld.cancel();
                        btnSelectSubCategory.setVisibility(View.VISIBLE);
                    }

                });
                //actv.setHint("Category");
                actv.setHint(MessagesString.HINT_CATEGORY);
                btnSetCurrentLocation.setVisibility(View.INVISIBLE);
                //btnSetCurrentLocation.setText(MessagesString.LOCATION_DIALOG_BUTTON_TEXT);
                tvLocationDialogText.setText(MessagesString.DIALOG_TITLE_TEXT_CATEGORY);

            }
        });



        btnSelectSubCategory = (Button)rootView.findViewById(R.id.btnSubCategoryDropDown);
        btnSelectSubCategory.setText(MessagesString.HINT_SUBCATEGORY);
        btnSelectSubCategory.setVisibility(View.INVISIBLE);

        btnSelectSubCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listOfSubCategories = new ArrayList<String>();
                listOfSubCategories = CommonResources.getSubCategories((btnSelectCategory.getText()).toString());
                ld = new LocationsDialog(getActivity(),getContext(), null, listOfSubCategories, false);
                ld.show();
                AutoCompleteTextView actv = ld.getAutoCompleteTextView();
                TextView tvLocationDialogText = ld.getTvLocationDialogText();
                Button btnSetCurrentLocation = ld.getBtnSetCurrentLocation();
                actv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        btnSelectSubCategory.setText((String)parent.getItemAtPosition(position));
                        ld.cancel();
                    }

                });
                //actv.setHint("SubCategory");
                actv.setHint(MessagesString.HINT_SUBCATEGORY);
                btnSetCurrentLocation.setVisibility(View.INVISIBLE);
                //btnSetCurrentLocation.setText(MessagesString.LOCATION_DIALOG_BUTTON_TEXT);
                tvLocationDialogText.setText(MessagesString.DIALOG_TITLE_TEXT_SUBCATEGORY);

            }
        });

        btnPostAd = (Button)rootView.findViewById(R.id.btnPostAd);

        spinner = (ProgressBar)rootView.findViewById(R.id.progressBarPostAd);
        spinner.setVisibility(View.GONE);
        btnPostAd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommonResources resources = new CommonResources(getContext());
                CommonResources.hideKeyboard(getActivity());
                if( "true".equals ((String)resources.loadFromSharedPrefs("isLoggedIn") ) ) {
                    if (validateInput()) {
                        new CreateNewPost().execute();


                    } else {

                    }

                }else {
                    Toast.makeText(getContext(),"Please Log in to Continue",Toast.LENGTH_LONG).show();
                }



//                getFragmentManager().popBackStackImmediate();
            }
        });
        if("edit".equalsIgnoreCase(mode)){
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


        }else {
            ((GlobalHome) getActivity()).setActionBarTitle("Post Ad");
        }
        return rootView;
    }

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

        setTitle(title);
        setDescription(description);
        setSubCategory(subCategory);
        setCity(city);
        setLocality(locality);
        setCategory(category);


        return true;
    }

    private void removePhoto(int position) {

        photos.remove(position);
        adapter.notifyDataSetChanged();
        if(photos.size() == 0){
            tvRemovePhoto.setVisibility(View.INVISIBLE);
        }
    }

    private void selectOrClickPhoto(int selection){

        if(photos.size() < 6) {
            if(selection == 0){
                //code for camera action
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            }else{
                //code for selection from gallery
                Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                startActivityForResult(pickPhoto,GALLERY_REQUEST);
            }
        }else {
            Toast.makeText(getContext(),"You Can Only upload 6 images.",Toast.LENGTH_SHORT).show();
        }




    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Bitmap photo = null;
        if (requestCode == CAMERA_REQUEST && resultCode == getActivity().RESULT_OK) {
            photo = (Bitmap) data.getExtras().get("data");
            // imageView.setImageBitmap(photo);



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


            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                Toast.makeText(getContext(), "Before" + photo.getAllocationByteCount() + " After" + compressedPhoto.getAllocationByteCount(), Toast.LENGTH_LONG).show();
                //return bitmap.getAllocationByteCount();
            }
            if(photos.size() > 0){
                tvRemovePhoto.setVisibility(View.VISIBLE);
            }
            //primaryImage = photos.get(0);
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
        Toast.makeText(getContext(),"Width of Image" + image.getWidth()+" Height Of Image"+ image.getHeight(),Toast.LENGTH_LONG).show();
        return Bitmap.createScaledBitmap(image, width, height, true);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        context=activity;

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }



    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }


    class CreateNewPost extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(getContext());
            pDialog.setMessage("Creating Product..");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        /**
         * Creating product
         */

        protected String doInBackground(String... args) {


            // Building Parameters
            HashMap <String,String> params = new HashMap <String,String>();

            params.put("title", getTitle());
            params.put("description", getDescription());
            params.put("subcategory", getSubCategory());
            params.put("city", getCity());
            params.put("locality", getLocality());
            params.put("uniqueid", getUniqueUserId());


            params.put("numofimages", photos.size() + "");
            params.put("category", getCategory());

            ByteArrayOutputStream byteArrayBitmapStream;
            int i = 0;
            for (Bitmap image : photos) {
                byteArrayBitmapStream = new ByteArrayOutputStream();
                image.compress(Bitmap.CompressFormat.PNG, 100, byteArrayBitmapStream);
                byte[] b = byteArrayBitmapStream.toByteArray();
                String encodedImage = Base64.encodeToString(b, Base64.DEFAULT);
                params.put("image_" + i, encodedImage);
                i++;
                try {
                    byteArrayBitmapStream.close();
                    byteArrayBitmapStream = null;
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();


                }
            }


            // getting JSON Object
            // Note that create product url accepts POST method
            JSONObject json;
            if ("edit".equalsIgnoreCase(mode)) {

                params.put("postid",postId);
                json = jsonParser.makeHttpRequest(CommonResources.getURL("edit_my_post"),
                        params);
            } else{
                json = jsonParser.makeHttpRequest(CommonResources.getURL("create_post"),
                        params);
            }
            // check log cat fro response
            if (json == null)
                return null;
            Log.d("Create Response", json.toString());
            //Toast.makeText(getContext(),"JSON: "+json.toString(), Toast.LENGTH_LONG).show();
            // check for success tag
            int success = -1;
            try {
                success = json.getInt(TAG_SUCCESS);

                if (success == 0) {
                    // successfully created product
//                    Toast.makeText(getApplicationContext(),"Created",Toast.LENGTH_LONG).show();
                    //finish();
                } else if (success == 1) {
                    // failed to create product
                } else if (success == 2) {
                    //Invalid input
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return success + "";
        }

        /**
         * After completing background task Dismiss the progress dialog
         **/
        protected void onPostExecute(String success) {
            // dismiss the dialog once done
            pDialog.dismiss();
            if ("0".equalsIgnoreCase(success)) {
                isEdited = true;

                for(int i=0; i<photos.size();i++){
                    String url = CommonResources.getStaticURL()+"uploadedimages/"+postId+"_"+(i+1);
                    Picasso.with(getContext()).invalidate(url);
                }

                Toast.makeText(getContext(), "Ad was successfully posted.", Toast.LENGTH_LONG).show();
                //getFragmentManager().popBackStackImmediate();
                navigateToHome();
            } else if ("1".equalsIgnoreCase(success)) {
                Toast.makeText(getContext(), "Please try again", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getContext(), "Please try again later", Toast.LENGTH_LONG).show();
            }
        }

        public void navigateToHome() {
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

    }

}
