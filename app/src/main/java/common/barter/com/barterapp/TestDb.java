package common.barter.com.barterapp;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class TestDb extends Activity {

    // Progress Dialog
    private ProgressDialog pDialog;

    JSONParser jsonParser = new JSONParser();
   TextView title;
    TextView description;
    TextView email;
    ImageView image;
    Button fetch;
    JSONArray products = null;

    String sTitle;
    String sDescription;
    String sEmail;
    String sImage;

    // url to create new product
    private static String url_all_products = "http://192.168.0.103:8686/get_post1.php";

    // JSON Node names
    private static final String TAG_SUCCESS = "success";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_db);

        // Edit Text
       title = (TextView)findViewById(R.id.tvTitle);
               description = (TextView)findViewById(R.id.tvDescription);
               email = (TextView)findViewById(R.id.tvEmailiD);
                       image = (ImageView)findViewById(R.id.ivRetrieved);


        // Create button


        // button click event

        fetch = (Button)findViewById(R.id.btnfetch);
        fetch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new CreateNewProduct().execute();

            }
        });

    }

    /**
     * Background Async Task to Create new product
     * */
    class CreateNewProduct extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(TestDb.this);
            pDialog.setMessage("Creating Product..");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        /**
         * Creating product
         * */
        protected String doInBackground(String... args) {


            // Building Parameters
            HashMap <String,String> params = new HashMap<String,String>();
            JSONObject json = jsonParser.makeHttpRequest(url_all_products, params);
            Log.d("All Products: ", json.toString());
            // getting JSON Object
            // Note that create product url accepts POST method

            // check log cat fro response
            Log.d("Row ", json.toString());

            // check for success tag
            try {
                int success = json.getInt(TAG_SUCCESS);

                if (success == 1) {
                    products = json.getJSONArray("products");
                    // successfully created product
//                    Toast.makeText(getApplicationContext(),"Created",Toast.LENGTH_LONG).show();
                    //finish();



                    for (int i = 0; i < products.length(); i++) {
                        JSONObject c = products.getJSONObject(i);

                        // Storing each json item in variable
                        sTitle = c.getString("title");
                        sDescription = c.getString("description");
                        sEmail = c.getString("email");
                        sImage = c.getString("image");
                       // sImage = c.ge
                    }
                } else {
                    // failed to create product
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        /**
         * After completing background task Dismiss the progress dialog
         * **/
        protected void onPostExecute(String file_url) {
            // dismiss the dialog after getting all products
            pDialog.dismiss();
            // updating UI from Background Thread
            runOnUiThread(new Runnable() {
                public void run() {
                    /**
                     * Updating parsed JSON data into ListView
                     * */
                    title.setText(sTitle);
                    description.setText(sDescription);
                    email.setText(sEmail);
                    byte[] decodedString = Base64.decode(sImage, Base64.DEFAULT);
                    Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                    image.setImageBitmap(decodedByte);
                }
            });

        }

    }
}