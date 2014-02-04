package com.twitter.humblebrag.volley.request;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import com.android.volley.AuthFailureError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.twitter.humblebrag.util.ApplicationConstants;

/**
 * Created by vipulsomani on 1/27/14.
 */
public class TokenRequest extends StringRequest {
    private static ApplicationConstants constants;

    public TokenRequest(int method, String url, Listener<String> listener, ErrorListener errorListener, Context context) {
        super(method, url, listener, errorListener);
        constants = ApplicationConstants.getInstance(context.getResources());
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        Map<String, String> params = new HashMap<String, String>();
        params.put(constants.PARAM_GRANT, constants.GRANT_VALUE);
        return params;
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        Map<String, String> headers = new HashMap<String, String>();
        String auth = constants.AUTH_TYPE_BASIC
                + encodeKeys(constants.CONSUMER_KEY, constants.CONSUMER_SECRET);
        headers.put(constants.HEADER_AUTH, auth);
        return headers;
    }

    // Encodes the consumer key and secret to create the basic authorization key
    private static String encodeKeys(String consumerKey, String consumerSecret) {

        try {
            String encodedConsumerKey = URLEncoder.encode(consumerKey, constants.ENCODING);
            String encodedConsumerSecret = URLEncoder.encode(consumerSecret, constants.ENCODING);
            String fullKey = encodedConsumerKey + ":" + encodedConsumerSecret;
            // Log.i("HB", "encode " + fullKey);
            return new String(org.apache.commons.codec.binary.Base64.encodeBase64(fullKey.getBytes()));
        } catch (UnsupportedEncodingException e) {
            return new String(e.getMessage());
        }
    }
}
