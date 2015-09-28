package common.barter.com.barterapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by vikram on 23/09/15.
 */


public abstract class AsyncConnection extends AsyncTask<String, String, String> {

    private Context context;
    private String phpName;
    private String method;
    private HashMap<String,String> params;
    private Boolean showDialog;
    private String  showMessage;

    private JSONObject json;
    private ProgressDialog pDialog;
    private JSONParser jsonParser;

    // To Be defined by Calling class
    public abstract void receiveData(JSONObject json);

    public AsyncConnection(Context context, String phpName,String method ,HashMap<String, String> params, Boolean showDialog, String showMessage)
    {
        this.context=context;
        this.phpName=phpName;
        this.method = method;
        jsonParser = new JSONParser();
        this.params=params;
        this.showDialog=showDialog;
        this.showMessage=showMessage;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if (showDialog) {
            pDialog = new ProgressDialog(context);
            pDialog.setMessage(showMessage);
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }
    }

    @Override
    protected String doInBackground(String... params1) {
        // getting JSON Object
        // Note that create product url accepts POST method
        json = jsonParser.makeHttpRequest(phpName,
                method, params);

        if(null != json)
            Log.d("Create Response", json.toString());

        return null;

    }

    @Override
    protected void onPostExecute(String file_url) {
        // dismiss the dialog once done
        if (showDialog){
            pDialog.dismiss();
        }

        if(json!=null)
        {
            receiveData(json);
        }
    }



}
