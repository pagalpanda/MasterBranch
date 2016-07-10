package common.barter.com.barterapp;

import java.util.HashMap;

/**
 * Created by vikram on 24/06/16.
 */
public class HttpConnectionParameters {
    private String url;
    private String method;
    private HashMap<String,String> params;

    public HttpConnectionParameters(String url, String method, HashMap<String, String> params) {
        this.url = url;
        this.method = method;
        this.params = params;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public HashMap<String, String> getParams() {
        return params;
    }

    public void setParams(HashMap<String, String> params) {
        this.params = params;
    }
}
