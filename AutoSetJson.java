package com.sumeru.commons.helper;

import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.JsonArray;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

public class AutoSetJson {


    /**
     * Before using this method please be sure that you make the exact view hierarchy of
     * you view as of json like if json is having object and inside object it is having values
     * then first create a ViewGroup i.e. LinearLayout, RelativeLayout, etc with the tag name
     * same as json object name and then set the internal component tag names as json object 
     * elements.
     * 
     * For example 
     * if the json is this
     * 
     *   {
     *       "student":{
     *           "name":"Rishabh",
     *           "qualification":"BCA"
     *       }
     *   }
     *   
     *   then your view should be like this
     *   
     *   <Layout
     *   android:tag="student"
     *   >
     *       <TextView
     *       tag="name"
     *       />
     *       <TextView
     *       tag="qualification"
     *       />
     *   </Layout>
     * @param view
     * @param json
     * @throws JSONException
     */
    public static void setJsonOnView(View view, String json) throws JSONException {
        JSONObject jsonObject = new JSONObject(json);
        iterateJsonAndSetData(jsonObject, view, null);
    }


    /**
     * @param jsonObject pass the json object that you need to set on the view
     * @param view       contains the current view of the activity where you need to set json
     * @param index      index contains the position of the internal child in view. Please pass
     *                   this as always null while calling this function
     * @throws JSONException is thrown by this library if json is not valid.
     */
    private static void iterateJsonAndSetData(JSONObject jsonObject, View view, @Nullable Integer index) throws JSONException {
        int count = -1;
        Iterator<String> keys = jsonObject.keys();
        while (keys.hasNext()) {
            String key = keys.next();
            if (jsonObject.get(key) instanceof JSONObject) {
                /**
                 * making a recusive call to the function until we get the content of final json object
                 */
                iterateJsonAndSetData(jsonObject.getJSONObject(key), view.findViewWithTag(key), null);
            } else if (!(jsonObject.get(key) instanceof JsonArray)) {
                if (view instanceof ViewGroup) {
                    JSONObject json = new JSONObject();
                    json.put(key, jsonObject.getString(key));
                    if (((ViewGroup) view).getChildCount() > count) {
                        count++;
                        /**
                         * making a recursive call to a function to get it's internal child's
                         * and get there details depending upon the tags
                         */
                        iterateJsonAndSetData(jsonObject, ((ViewGroup) view).getChildAt(count), count);
                    }
                } else if (view instanceof TextView) {
                    TextView textView = view.findViewWithTag(key);
                    if (textView != null) {
                        textView.setText(jsonObject.getString(key));
                    }
                } else if (view instanceof EditText) {
                    EditText editText = view.findViewWithTag(key);
                    if (editText != null) {
                        editText.setText(jsonObject.getString(key));
                    }
                }
            }
        }
    }
}
