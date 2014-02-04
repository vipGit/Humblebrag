package com.twitter.humblebrag.volley.request;

import android.content.Context;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.json.JSONArray;

import com.android.volley.AuthFailureError;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.JsonArrayRequest;
import com.twitter.humblebrag.util.ApplicationConstants;
import com.twitter.humblebrag.volley.logic.VolleyLogic;

/**
 * Created by vipulsomani on 1/27/14.
 */
public class TweetRequest extends JsonArrayRequest{
    private static ApplicationConstants constants;

    public TweetRequest(String url, Listener<JSONArray> listener, ErrorListener errorListener,
                         List<NameValuePair> params, Context context) {
        super(url + "?" + URLEncodedUtils.format(params, "UTF-8"), listener, errorListener);
        constants = ApplicationConstants.getInstance(context.getResources());
    }


    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        Map<String, String> headers = new HashMap<String, String>();
        String auth = constants.AUTH_TYPE_BEARER + VolleyLogic.access_token;
        headers.put(constants.HEADER_AUTH, auth);
        return headers;
    }
}
