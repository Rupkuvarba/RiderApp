package com.app.eho.ui.custom;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.TypedValue;
import android.view.Window;
import android.widget.TextView;

import com.app.eho.R;
import com.app.eho.ui.SplashActivity;
import com.app.eho.utils.font.Fonts;

public class AlertDialogUtil {

    private AlertDialog alertDialog;
    private Window window;

    //Show - Message - Alert
    public static void showAlert(final Context context, String title, String message) {
        showAlert(context, title, message, context.getResources().getString(R.string.alert_btn_ok));
    }

    //Show - Message - Alert
    public static void showAlert(final Context context, String title, String message, String btnText) {
        try {
            if (title.isEmpty())
                title = context.getResources().getString(R.string.app_name);

            if (context != null && !((Activity) context).isFinishing()) {

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                alertDialogBuilder
                        .setTitle(Fonts.INSTANCE.getTypefaceBold(context, title))
                        .setMessage(Fonts.INSTANCE.getTypefaceMedium(context, message))
                        .setIcon(R.mipmap.ic_launcher)
                        .setPositiveButton(btnText, new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                if (context instanceof Activity) {
                                    if (!((Activity) context).isFinishing())
                                        dialog.dismiss();
                                }
                            }

                        });

                AlertDialog alertDialog = alertDialogBuilder.show();
                setFontToButton(context, alertDialog);
                setAlertTextView(context, message, alertDialog);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Show - Message - Alert
    public static void showAlert(final Context context, final String message) {

        try {

            if (context != null && !((Activity) context).isFinishing()) {

                ((Activity) context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                        alertDialogBuilder.setTitle(Fonts.INSTANCE.getTypefaceBold(context, context.getResources().getString(R.string.app_name)))
                                .setMessage(Fonts.INSTANCE.getTypefaceMedium(context, message))
                                .setIcon(R.mipmap.ic_launcher)
                                .setPositiveButton(context.getResources().getString(R.string.alert_btn_ok), new DialogInterface.OnClickListener() {

                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        if (context instanceof Activity) {
                                            if (!((Activity) context).isFinishing())
                                                dialog.dismiss();
                                        }
                                    }

                                });

                        AlertDialog alertDialog = alertDialogBuilder.show();
                        setFontToButton(context, alertDialog);
                    }

                });

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //Show - Message - Alert
    public static void showAlertFinish(final Context context, String message) {

        try {

            if (context != null && !((Activity) context).isFinishing()) {

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                alertDialogBuilder.setTitle(Fonts.INSTANCE.getTypefaceBold(context,context.getResources().getString(R.string.app_name)))
                        .setMessage(Fonts.INSTANCE.getTypefaceMedium(context, message))
                        .setIcon(R.mipmap.ic_launcher)
                        .setCancelable(false)
                        .setPositiveButton(context.getResources().getString(R.string.alert_btn_ok), new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //Dismiss Dialog
                                dialog.dismiss();
                                if(context instanceof SplashActivity){
                                    //((SplashActivity) context).openAppLaunch();
                                }else {
                                    ((Activity) context).finish();
                                }
                            }

                        });

                AlertDialog alertDialog = alertDialogBuilder.show();
                setFontToButton(context, alertDialog);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
/*
    //Show - Message - Alert
    public static void showAlertDismissActivityWithRoot(final Context context, String title, String message) {
        try {
            if (context != null && !((Activity) context).isFinishing()) {

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                alertDialogBuilder
                        .setTitle(Fonts.INSTANCE.getTypefaceBold(context,title))
                        .setMessage(Fonts.INSTANCE.getTypefaceMedium(context, message))
                        .setIcon(R.mipmap.ic_launcher)
                        .setPositiveButton(context.getResources().getString(R.string.alert_btn_ok), new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //Dismiss Dialog
                                dialog.dismiss();

                                //Start - Login Activity
                                Intent intent = new Intent(context, MainActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                context.startActivity(intent);

                                ((Activity) context).finish();

                            }

                        });

                AlertDialog alertDialog = alertDialogBuilder.show();
                setFontToButton(context, alertDialog);

                //Set text
                setAlertTextView(context, message, alertDialog);

            }
        } catch (Exception e) {
            Log.d("Alert", " alert exception: " + e);
        }
    }

    //Display - GPS Alert
    public static void GpsAlertMessage(final Context context) {
        try {

            if (context != null && !((Activity) context).isFinishing()) {

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                alertDialogBuilder.setTitle(Fonts.INSTANCE.getTypefaceBold(context,context.getResources().getString(R.string.app_name)))
                        .setIcon(R.mipmap.ic_launcher)
                        .setMessage(Fonts.INSTANCE.getTypefaceMedium(context, context.getResources().getString(R.string.gps_message)))
                        .setCancelable(false)
                        .setPositiveButton(context.getResources().getString(R.string.alert_btn_ok),
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,
                                                        int id) {
                                        //Enable GPS
                                        //Open - Setting - Location Service
                                        Intent intentOpenLocationServiceSettings = new Intent();
                                        intentOpenLocationServiceSettings.setAction(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                                        context.startActivity(intentOpenLocationServiceSettings);

                                    }
                                })
                        .setNegativeButton(context.getResources().getString(R.string.alert_btn_cancel),
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();

                //Set font for dialog buttons
                setFontToButton(context, alertDialog);

                //Set text
                setAlertTextView(context, context.getResources().getString(R.string.gps_message), alertDialog);

            }
        } catch (Exception e) {
            messageBox(context, context.getResources().getString(R.string.app_name), Log.getStackTraceString(e));
        }
    }

    //GPS Error alert
    public static void messageBox(Context context, String method, String message) {

        if (context != null && !((Activity) context).isFinishing()) {

            if (message != null) {

                AlertDialog.Builder messageBox = new AlertDialog.Builder(context);
                messageBox.setTitle(Fonts.INSTANCE.getTypefaceBold(context,method));
                messageBox.setMessage(Fonts.INSTANCE.getTypefaceMedium(context, message));
                messageBox.setCancelable(false);
                messageBox.setNeutralButton(context.getResources().getString(R.string.alert_btn_ok), null);
                AlertDialog alertDialog = messageBox.show();

                //Set font for dialog buttons
                setFontToButton(context, alertDialog);
            }
        }

    }

    //Logout from app
    public static void logoutDialog(final Context context) {

        if (context != null && !((Activity) context).isFinishing()) {

            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
            alertDialogBuilder
                    .setTitle(Fonts.INSTANCE.getTypefaceBold(context, context.getResources().getString(R.string.app_name)))
                    .setMessage(Fonts.INSTANCE.getTypefaceMedium(context, context.getResources().getString(R.string.are_you_sure_logout)))
                    .setIcon(R.mipmap.ic_launcher)
                    .setPositiveButton(context.getResources().getString(R.string.alert_btn_yes).toUpperCase(), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                            dialog.dismiss();
                            //LogoutUtils.logout(context);

                        }
                    })
                    .setNegativeButton(context.getResources().getString(R.string.alert_btn_no).toUpperCase(), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // do nothing
                        }
                    });

            AlertDialog alertDialog = alertDialogBuilder.show();
            setFontToButton(context, alertDialog);

            //Set text
            setAlertTextView(context, context.getResources().getString(R.string.are_you_sure_logout), alertDialog);

        }
    }

    public static void showAlertVerifyEmail(final Context context, String title, String message, final String email) {

        try {

            if (title.isEmpty())
                title = context.getResources().getString(R.string.app_name);

            if (context != null && !((Activity) context).isFinishing()) {

                AlertDialog alertDialog = new AlertDialog.Builder(context)
                        .setTitle(Fonts.getTypefaceBold(context, title))
                        .setMessage(Fonts.getTypefaceMedium(context, message))
                        .setIcon(R.mipmap.ic_launcher)
                        .setPositiveButton(context.getResources().getString(R.string.resend_mail), new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                JSONObject jsonRequest = new JSONObject();

                                try {

                                    JSONObject jsonEmail = new JSONObject();
                                    jsonEmail.put(Constants.EMAIL, email);
                                    jsonRequest.put(Constants.USER, jsonEmail);

                                    //Call Resend API
                                    APICallConfirmation resendEmail = new APICallConfirmation(context);
                                    resendEmail.emailConfirmation(jsonRequest);

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                                //Dismiss Dialog
                                dialog.dismiss();

                            }

                        })

                        .setNegativeButton(context.getResources().getString(R.string.dismiss), new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                //Dismiss Dialog
                                dialog.dismiss();
                            }

                        }).show();

                //Set font for dialog buttons
                setFontToButton(context, alertDialog);

                //Set message text
                AlertDialogUtil.setAlertTextView(context, context.getResources().getString(R.string.terms_message), alertDialog);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //Display dialog - without title
    public static void displayOkDialogWithTitleAndAction(Context context, boolean isCancel, String message, final String action,
                                                         String pBtnText, String nBtnText, boolean isTwoButtons,
                                                         final AlertDialogListener alertDialogListener) {
        displayOkDialogWithAction(context, isCancel, message, action, pBtnText, nBtnText, isTwoButtons,
                alertDialogListener);

    }

    //Display dialog
    private static void displayOkDialogWithAction(Context context, boolean isCancel, String message, final String action,
                                                  String pBtnText, String nBtnText, boolean isTwoButtons,
                                                  final AlertDialogListener alertDialogListener) {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setTitle(Fonts.getTypefaceBold(context,context.getResources().getString(R.string.app_name)));
        alertDialogBuilder.setIcon(R.mipmap.ic_launcher);
        alertDialogBuilder.setMessage(Fonts.getTypefaceMedium(context, message));
        alertDialogBuilder.setCancelable(isCancel);
        if(pBtnText.isEmpty()){
            pBtnText = context.getResources().getString(R.string.alert_btn_ok);
        }
        alertDialogBuilder.setPositiveButton(pBtnText, (dialog, which) -> {
            dialog.dismiss();

            alertDialogListener.setAlertButtonAction(action);

        });

        if (isTwoButtons) {
            alertDialogBuilder.setNegativeButton(nBtnText, (dialog, which) -> dialog.dismiss());
        }

        AlertDialog alertDialog = alertDialogBuilder.show();
        setFontToButton(context, alertDialog);
        setAlertTextView(context, message, alertDialog);

    }

    //Show - Message - Alert
    public static void showUnAuthorizeAlert(final Context context, boolean isUnAuthorized) {

        try {
            LogUtils.displayLog("UnAuthorise", " API pingResponseHandler onSuccess email "+ SharedPreferenceHelper.getMyStringPref(context, SharedPreferenceConstants.EMAIL));
            if (context != null && !((Activity) context).isFinishing()) {

                ((Activity) context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        String title = context.getResources().getString(R.string.app_name);
                        String message = context.getResources().getString(R.string.revoked_msg);
                        String pBtn = context.getResources().getString(R.string.revoked_btn);

                        if(isUnAuthorized){
                            title = context.getResources().getString(R.string.unauthorized_title);
                            message = context.getResources().getString(R.string.unauthorized_msg);
                            pBtn = context.getResources().getString(R.string.unauthorized_btn);
                        }

                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context)
                                .setTitle(Fonts.getTypefaceBold(context, title))
                                .setMessage(Fonts.getTypefaceMedium(context,message))
                                .setIcon(R.mipmap.ic_launcher)
                                .setCancelable(false)
                                .setPositiveButton(pBtn, (dialog, which) -> {

                                    if (!((Activity) context).isFinishing()) {

                                        dialog.dismiss();
                                        LogoutUtils.logout(context);

                                    }

                                });

                        if (!((Activity) context).isFinishing()) {
                            AlertDialog alertDialog = alertDialogBuilder.show();

                            //Set font for dialog buttons
                            setFontToButton(context, alertDialog);
                        }
                    }

                });

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void showEditTextAlertDialog(final Context mContext, final String action, final String previousNote, final String ID, boolean isShowTitle, final APICallListener apiCallListener) {
        final EditText input = new EditText(mContext);

        String alertMessage = mContext.getResources().getString(R.string.time_correction_note_alert_title);
        String pBtnText = mContext.getResources().getString(R.string.shift_note_save);
        String nBtnText = mContext.getResources().getString(R.string.shift_note_cancel);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mContext);
        if (isShowTitle) {
            alertDialogBuilder.setTitle(Fonts.getTypefaceBold(mContext, mContext.getResources().getString(R.string.message)));
            alertDialogBuilder.setIcon(R.mipmap.ic_launcher);
        }

        //Display Alert View
        LinearLayout container = new LinearLayout(mContext);
        container.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.setMargins(Common.dpToPx(mContext, 20), 0, Common.dpToPx(mContext, 20), Common.dpToPx(mContext, 3));

        input.setLayoutParams(lp);
        Fonts.setRegularFont(mContext, input);
        input.setGravity(Gravity.TOP | Gravity.START);
        input.setMaxLines(10);
        input.setText(previousNote.trim());
        input.setTextSize(15);
        input.setSelection(input.getText().length());
        input.setLineSpacing(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 2.0f, mContext.getResources().getDisplayMetrics()), 1.0f);
        if(input.getParent()!=null)
            ((ViewGroup)input.getParent()).removeView(input);

        if(container.getParent()!=null)
            ((ViewGroup)container.getParent()).removeView(container);

        container.addView(input, lp);
        alertDialogBuilder.setView(container);

        alertDialogBuilder
                .setMessage(Fonts.getTypefaceMedium(mContext, alertMessage))
                .setCancelable(false)
                .setPositiveButton(pBtnText, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        Utils.hideKeyboard(mContext);

                        String note = input.getText().toString();

                        if (previousNote.equalsIgnoreCase(note)) {
                            apiCallListener.getResponse("");

                        } else {

                            if (action.equalsIgnoreCase(Constants.ALERT_TIME_CORRECTION)) {
                                //Update time correction note
                                APICallUpdateTimeCorrection apiCallUpdateTimeCorrection = new APICallUpdateTimeCorrection(mContext);
                                apiCallUpdateTimeCorrection.updateShiftNote(ID, apiCallListener, note);
                            }
                        }

                    }

                })
                .setNegativeButton(nBtnText, (dialog, which) -> {
                    //Dismiss Alert
                    dialog.dismiss();
                    Utils.hideKeyboard(mContext);
                });

        alertDialog = alertDialogBuilder.create();
        alertDialog.show();
        alertDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        input.requestFocus();
        window = alertDialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setGravity(Gravity.CENTER);

        //Set font for dialog buttons
        setFontToButton(mContext, alertDialog);

        //Set message text
        setAlertTextView(mContext, alertMessage, alertDialog);
    }


    public void isOpenDialog(Context mContext, boolean isOpen) {
        try {
            if (alertDialog != null) {

                if (alertDialog.isShowing()) {
                    float scale = mContext.getResources().getDisplayMetrics().density;
                    WindowManager.LayoutParams attributes = window.getAttributes(); // get dialog window's attributes for top and left

                    //keyboard open
                    if (isOpen) {
                        window.setGravity(Gravity.TOP);                             // set dialog windows gravity top
                        attributes.y = (int) (60 * scale);                          // set margin for top 60, x for left and, y for top
                    } else {
                        window.setGravity(Gravity.CENTER);                          // set dialog windows gravity top
                        attributes.y = 0;                                           // set margin for top 60, x for left and, y for top
                    }

                    window.setAttributes(attributes);                               // apply margin on dialog window
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void displaySettingsChangedDialog(Context context, final AlertSettingsChangedListener mAlertDialogListener) {

        if (context != null && !((Activity) context).isFinishing()) {

            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
            alertDialogBuilder.setTitle(Fonts.getTypefaceBold(context,context.getResources().getString(R.string.app_name)));
            alertDialogBuilder.setMessage(Fonts.getTypefaceMedium(context,context.getResources().getString(R.string.settings_change_message)));
            alertDialogBuilder.setIcon(R.mipmap.ic_launcher);
            alertDialogBuilder.setCancelable(true);
            alertDialogBuilder.setPositiveButton(context.getResources().getString(R.string.settings_change_positive_button),
                    new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            mAlertDialogListener.onPositiveButtonClicked();
                        }

                    });
            alertDialogBuilder.setNegativeButton(context.getResources().getString(R.string.settings_change_negative_button),
                    new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            mAlertDialogListener.onNegativeButtonClicked();
                        }

                    });

            AlertDialog dialog = alertDialogBuilder.show();

            //Set font for dialog buttons
            setFontToButton(context, dialog);

            //Set message text
            setAlertTextView(context, context.getResources().getString(R.string.settings_change_message), dialog);

        }
    }

    public static void showPermissionAlert(final Context context, String message, final String permission) {

        if (context != null) {

            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
            alertDialogBuilder.setCancelable(true);
            alertDialogBuilder.setTitle(Fonts.getTypefaceBold(context,context.getResources().getString(R.string.app_name)));
            alertDialogBuilder.setIcon(R.mipmap.ic_launcher);
            alertDialogBuilder.setMessage(Fonts.getTypefaceMedium(context, message));
            alertDialogBuilder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    try {
                        //Get activity
                        Activity activity = (Activity) context;

                        //Performed actions according to activity and permission
                        if (permission.equalsIgnoreCase(PermissionUtils.CALENDER)) {

                            if (activity instanceof DashboardActivity) {
                                ((DashboardActivity) activity).devicePermissions();
                            }

                        } else if (permission.equalsIgnoreCase(PermissionUtils.LOCATION)) {

                            //Request for permission
                            new FusedLocationProvider().fusedLocationProviderClient(context);

                        } else if (permission.equalsIgnoreCase(PermissionUtils.CAMERA_STORAGE)) {

                            if (activity instanceof DashboardActivity) {
                                ((DashboardActivity) activity).showMenuOptionsOfPhoto();

                            } else if (activity instanceof CreateRoomActivity) {
                                ((CreateRoomActivity) activity).showCamera();
                            }

                        } else if (permission.equalsIgnoreCase(PermissionUtils.STORAGE)) {

                            if (activity instanceof DashboardActivity) {
                                ((DashboardActivity) activity).askForWriteExternalStoragePermission();
                            }

                        } else if (permission.equalsIgnoreCase(PermissionUtils.PHONE)) {
                            ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.CALL_PHONE}, PermissionUtils.PERMISSION_CALL);
                        }

                    } catch (Exception e) {
                        LogUtils.displayELog("AlertDialog", "Exception: " + e);
                    }
                }
            });

            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();

            //Set font for dialog buttons
            setFontToButton(context, alertDialog);

            //Set message text
            setAlertTextView(context, message, alertDialog);

        }

    }

    public void displayTwoButtonActionDialog(Context context, String message, String pBtnText, String nBtnText,
                                             String pAction, String nAction, AlertDialogButtonsListener mAlertDialogListener) {

        if (context != null && !((Activity) context).isFinishing()) {

            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
            alertDialogBuilder.setTitle(Fonts.getTypefaceBold(context,context.getResources().getString(R.string.app_name)));
            alertDialogBuilder.setMessage(Fonts.getTypefaceMedium(context, message));
            alertDialogBuilder.setIcon(R.mipmap.ic_launcher);
            alertDialogBuilder.setCancelable(true);
            alertDialogBuilder.setPositiveButton(pBtnText,
                    (dialog, which) -> {
                        dialog.dismiss();
                        mAlertDialogListener.onPositiveButtonClicked(pAction);
                    });
            alertDialogBuilder.setNegativeButton(nBtnText,
                    (dialog, which) -> {
                        dialog.dismiss();
                        mAlertDialogListener.onNegativeButtonClicked(nAction);
                    });

            AlertDialog alertDialog = alertDialogBuilder.show();

            //Set font for dialog buttons
            setFontToButton(context, alertDialog);

            //Set message text
            setAlertTextView(context, message, alertDialog);

        }
    }
*/

    //Show text in app theme text
    public static void setAlertTextView(Context context, String message, Dialog dialog) {
        try {

            TextView textView = dialog.findViewById(android.R.id.message);
            textView.setTypeface(Fonts.INSTANCE.getFontTypeface(context));
            textView.setLineSpacing(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 2.0f,
                    context.getResources().getDisplayMetrics()), 1.0f);
            textView.setText(message);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Set font for dialog buttons
    public static void setFontToButton(Context context, AlertDialog dialog) {
        Fonts.INSTANCE.setRegularBoldFont(context, dialog.getButton(AlertDialog.BUTTON_NEGATIVE));
        Fonts.INSTANCE.setRegularBoldFont(context, dialog.getButton(AlertDialog.BUTTON_POSITIVE));
        Fonts.INSTANCE.setRegularBoldFont(context, dialog.getButton(AlertDialog.BUTTON_NEUTRAL));
    }

}
