package com.app.eho.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.location.LocationManager;
import android.os.Build;
import android.provider.Settings.Secure;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import androidx.fragment.app.Fragment;

import com.app.eho.R;
import com.app.eho.ui.custom.AlertDialogUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Locale;
import java.util.Set;

public class Utils {

    public static String TAG = "Utils";
    private final static boolean isLogEnabled = true;

    /*
     * Validate Functions
     */

    //Verify - EditText value empty or not
    public static boolean isNonEmptyEditText(EditText etText) {
        return ((etText.getText().toString().trim().length() != 0));
    }

    //Verify - Email String value
    public static boolean isInValidEmailAddress(String email) {
        return !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }


    //Check - Is null?
    public static String isNull(String str) {
        if (str == null) {
            str = "";
        } else if (str.equalsIgnoreCase("null")) {
            str = "";
        }
        return str;
    }

    //Verify - Phone Number Length
    public static boolean isInValidPasswordLength(String password) {
        return password.length() < 6;
    }

    public static boolean isPasswordMatch(String password, String reenterPassword) {
        return password.equals(reenterPassword);
    }

    public static boolean isEmailMatch(String email, String reenterEmail) {
        return email.equals(reenterEmail);
    }

    //Check - Check 2 string are equal or not?
    public static boolean isCompare(String str, String strCompare) {
        str = isNull(str);
        strCompare = isNull(strCompare);

        str = str.trim().toLowerCase(Locale.getDefault());
        strCompare = strCompare.trim().toLowerCase(Locale.getDefault());

        return str.equalsIgnoreCase(strCompare);
    }

    //Verify - Color Code Length
    public static boolean isValidColorCodeLength(String color) {
        return color.length() <= 6;
    }

    public static boolean isInteger(String s) {
        boolean isValidInteger = false;
        try {
            Integer.parseInt(s);
            isValidInteger = true;
        } catch (NumberFormatException ex) {
            ex.printStackTrace();
        }

        return isValidInteger;
    }

    /*
     * Show/Hide keyboard
     */

    //Show - Keyboard
    public static void showKeyboard(Context context) {
        ((InputMethodManager) (context).getSystemService(Context.INPUT_METHOD_SERVICE)).toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
    }

    //Hide - Keyboard
    public static void hideKeyboard(Context context) {

        try {

            ((Activity) context).getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
            if ((((Activity) context).getCurrentFocus() != null) && (((Activity) context).getCurrentFocus().getWindowToken() != null))
                ((InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(((Activity) context).getCurrentFocus().getWindowToken(), 0);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //Hide - Keyboard
    public static void hideKeyboard(Context mContext, View view) {

        try {

            InputMethodManager inputMethodManager = (InputMethodManager) mContext.getSystemService(Activity.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //Hide - keyboard
    public static void hideKeyboard(Activity activity) {

        try {

            InputMethodManager inputManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            View view = activity.getCurrentFocus();
            if (view != null)
                inputManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static boolean parseBoolean(String s) {

        s = s.toLowerCase();
        Set<String> trueSet = new HashSet<>(Arrays.asList("1", "true", "yes"));
        Set<String> falseSet = new HashSet<>(Arrays.asList("0", "false", "no"));

        if (trueSet.contains(s))
            return true;
        else if (falseSet.contains(s))
            return false;
        else
            return false;

    }

    public HashMap<String, String> jsonToMap(JSONObject object) throws JSONException {
        HashMap<String, String> map = new HashMap<>();

        Iterator<String> keysItr = object.keys();
        while(keysItr.hasNext()) {
            String key = keysItr.next();
            Object value = object.get(key);
            map.put(key, String.valueOf(value));
        }
        return map;
    }

    public static boolean checkInputContainsListWords(String inputString, ArrayList<String> items){
        inputString = inputString.toLowerCase();
        return  containsWords(inputString, items);
    }

    public static boolean containsWords(String inputString, ArrayList<String> items) {
        boolean found = false;
        for (String item : items) {
            String containWord = item.toLowerCase();
            if(inputString.contains(containWord)){    //containWord.length() > 2 && (inputString.contains(containWord.substring(0,2))) ||
                found = true;
                break;
            }
        }
        return found;
    }

    public static boolean checkInputContainsAllListWords(String inputString, ArrayList<String> items){
        boolean found = false;
        inputString = inputString.toLowerCase();
        for (String item : items) {
            //LogUtils.displayLog("Tag","checkInputContainsAllListWords item: "+item+" inputString: "+inputString);
            if(inputString.contains(item.toLowerCase())){
                found = true;
                break;
            }
        }
        return found;
    }

    public static boolean isSafeFragment( Fragment frag )
    {
        return !(frag.isRemoving() || frag.getActivity() == null || frag.isDetached() || !frag.isAdded() || frag.getView() == null );
    }

    public static boolean getBooleanValueFromInt(int iValue){
        return iValue == 1;
    }

    public static int getIntValueFromBoolean(boolean isFlag){
        return isFlag ? 1 : 0;
    }

    public static void handleNetworkResponse(Context mContext, String error){
        handleNetworkResponse(mContext, error, false);
    }

    public static void handleNetworkResponse(Context mContext, String error, boolean isShowError) {
        if (!error.equalsIgnoreCase(mContext.getResources().getString(R.string.check_internet_connection))) {
            if (isShowError)
                AlertDialogUtil.showAlert(mContext, mContext.getResources().getString(R.string.app_name), error);
            else
                AlertDialogUtil.showAlert(mContext, mContext.getResources().getString(R.string.app_name), mContext.getResources().getString(R.string.something_went_wrong));
        }
    }

    //Check - GPS is available?
    public static boolean checkGPSAvailable(final Context mContext) {
        LocationManager locationManager = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
                LocationManager.NETWORK_PROVIDER);
        //return manager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }
}
