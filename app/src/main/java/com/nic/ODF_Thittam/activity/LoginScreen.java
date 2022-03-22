package com.nic.ODF_Thittam.activity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.InputType;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.android.volley.VolleyError;
import com.nic.ODF_Thittam.R;
import com.nic.ODF_Thittam.api.Api;
import com.nic.ODF_Thittam.api.ApiService;
import com.nic.ODF_Thittam.api.ServerResponse;
import com.nic.ODF_Thittam.constant.AppConstant;
import com.nic.ODF_Thittam.dataBase.DBHelper;
import com.nic.ODF_Thittam.dataBase.dbData;
import com.nic.ODF_Thittam.databinding.LoginScreenBinding;
import com.nic.ODF_Thittam.model.ODF_Thittam;
import com.nic.ODF_Thittam.session.PrefManager;
import com.nic.ODF_Thittam.support.ProgressHUD;
import com.nic.ODF_Thittam.utils.FontCache;
import com.nic.ODF_Thittam.utils.UrlGenerator;
import com.nic.ODF_Thittam.utils.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by AchanthiSundar on 28-12-2018.
 */

public class LoginScreen extends AppCompatActivity implements View.OnClickListener, Api.ServerResponseListener {

    private String randString;

    public static DBHelper dbHelper;
    public static SQLiteDatabase db;
    JSONObject jsonObject;

    private PrefManager prefManager;
    private ProgressHUD progressHUD;
    private int setPType;
    public com.nic.ODF_Thittam.dataBase.dbData dbData = new dbData(this);
    public LoginScreenBinding loginScreenBinding;
    boolean flag=false;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        loginScreenBinding = DataBindingUtil.setContentView(this, R.layout.login_screen);
        loginScreenBinding.setActivity(this);
        intializeUI();
        getGenderList();
        getEducationalQualificationList();
        getMotivatorCategoryList();
        getDistrictList();
        getBlockList();
    }

    public void intializeUI() {
        prefManager = new PrefManager(this);
        loginScreenBinding.btnSignIn.setOnClickListener(this);

        loginScreenBinding.password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        //loginScreenBinding.inputLayoutEmail.setTypeface(FontCache.getInstance(this).getFont(FontCache.Font.REGULAR));
        //loginScreenBinding.inputLayoutPassword.setTypeface(FontCache.getInstance(this).getFont(FontCache.Font.REGULAR));
        loginScreenBinding.btnSignIn.setTypeface(FontCache.getInstance(this).getFont(FontCache.Font.MEDIUM));
        //loginScreenBinding.inputLayoutEmail.setHintTextAppearance(R.style.InActive);
        //loginScreenBinding.inputLayoutPassword.setHintTextAppearance(R.style.InActive);

        loginScreenBinding.password.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {
                    checkLoginScreen();
                }
                return false;
            }
        });
        loginScreenBinding.password.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/Avenir-Roman.ttf"));
        randString = Utils.randomChar();


        try {
            String versionName = getPackageManager()
                    .getPackageInfo(getPackageName(), 0).versionName;
            loginScreenBinding.tvVersionNumber.setText(getResources().getString(R.string.version) + " " + versionName);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        setPType = 1;
        loginScreenBinding.redEye.setOnClickListener(this);


    }

    public void showPassword() {
        if (setPType == 1) {
            setPType = 0;
            loginScreenBinding.password.setTransformationMethod(null);
            if (loginScreenBinding.password.getText().length() > 0) {
                loginScreenBinding.password.setSelection(loginScreenBinding.password.getText().length());
                loginScreenBinding.redEye.setBackgroundResource(R.drawable.ic_baseline_visibility_off_24px);
            }
        } else {
            setPType = 1;
            loginScreenBinding.password.setTransformationMethod(new PasswordTransformationMethod());
            if (loginScreenBinding.password.getText().length() > 0) {
                loginScreenBinding.password.setSelection(loginScreenBinding.password.getText().length());
                loginScreenBinding.redEye.setBackgroundResource(R.drawable.ic_baseline_visibility_24px);
            }
        }

    }



    @Override
    public void onClick(View v) {
        /*switch (v.getId()) {


        }*/
    }

    public boolean validate() {
        boolean valid = true;
        String username = loginScreenBinding.userName.getText().toString().trim();
        prefManager.setUserName(username);
        String password = loginScreenBinding.password.getText().toString().trim();


        if (username.isEmpty()) {
            valid = false;
            Utils.showAlert(this, "Please enter the username");
        } else if (password.isEmpty()) {
            valid = false;
            Utils.showAlert(this, "Please enter the password");
        }
        return valid;
    }

    public void checkLoginScreen() {
        loginScreenBinding.userName.setText("9655215865");
        loginScreenBinding.password.setText("test123#$");//loc
        /*loginScreenBinding.userName.setText("9843368346");
        loginScreenBinding.password.setText("odf61#$");//live*/

        final String username = loginScreenBinding.userName.getText().toString().trim();
        final String password = loginScreenBinding.password.getText().toString().trim();
        prefManager.setUserPassword(password);

        if (Utils.isOnline()) {
            if (!validate())
                return;
            else if (prefManager.getUserName().length() > 0 && password.length() > 0) {
                new ApiService(this).makeRequest("LoginScreen", Api.Method.POST, UrlGenerator.getLoginUrl(), loginParams(), "not cache", this);
            } else {
                Utils.showAlert(this, "Please enter your username and password!");
            }
        } else {
            //Utils.showAlert(this, getResources().getString(R.string.no_internet));
            AlertDialog.Builder ab = new AlertDialog.Builder(
                    LoginScreen.this);
            ab.setMessage("Internet Connection is not avaliable..Please Turn ON Network Connection OR Continue With Off-line Mode..");
            ab.setPositiveButton("Settings",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog,
                                            int whichButton) {
                            Intent I = new Intent(
                                    android.provider.Settings.ACTION_WIRELESS_SETTINGS);
                            startActivity(I);
                        }
                    });
            ab.setNegativeButton("Continue With Off-Line",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog,
                                            int whichButton) {
                            offline_mode(username, password);
                        }
                    });
            ab.show();
        }
    }


    public Map<String, String> loginParams() {
        Map<String, String> params = new HashMap<>();
        params.put(AppConstant.KEY_SERVICE_ID, "login");


        String random = Utils.randomChar();

        params.put(AppConstant.USER_LOGIN_KEY, random);
        Log.d("randchar", "" + random);

        params.put(AppConstant.KEY_USER_NAME, prefManager.getUserName());
        Log.d("user", "" + loginScreenBinding.userName.getText().toString().trim());

        String encryptUserPass = Utils.md5(loginScreenBinding.password.getText().toString().trim());
        prefManager.setEncryptPass(encryptUserPass);
        Log.d("md5", "" + encryptUserPass);

        String userPass = encryptUserPass.concat(random);
        Log.d("userpass", "" + userPass);
        String sha256 = Utils.getSHA(userPass);
        Log.d("sha", "" + sha256);

        params.put(AppConstant.KEY_USER_PASSWORD, sha256);


        Log.d("user", "" + loginScreenBinding.userName.getText().toString().trim());

Log.d("params",""+params);
        return params;
    }

    //The method for opening the registration page and another processes or checks for registering

    public void clickSignUp() {
        if(flag){
        Intent intent  = new Intent(LoginScreen.this,RegisterScreen.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
    }
    }

    public void getGenderList() {
        try {
            new ApiService(this).makeJSONObjectRequest("Gender", Api.Method.POST, UrlGenerator.getRegistrationUrl(), genderParams(), "not cache", this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public JSONObject genderParams() throws JSONException {
        JSONObject dataSet = new JSONObject();
        dataSet.put(AppConstant.KEY_SERVICE_ID, "gender");
        return dataSet;
    }

    public void getEducationalQualificationList() {
        try {
            new ApiService(this).makeJSONObjectRequest("EducationalQualification", Api.Method.POST, UrlGenerator.getRegistrationUrl(), EducationalQualificationParams(), "not cache", this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public JSONObject EducationalQualificationParams() throws JSONException {
        JSONObject dataSet = new JSONObject();
        dataSet.put(AppConstant.KEY_SERVICE_ID, "educational_qualification");
        return dataSet;
    }
    public void getMotivatorCategoryList() {
        try {
            new ApiService(this).makeJSONObjectRequest("MotivatorCategoryList", Api.Method.POST, UrlGenerator.getRegistrationUrl(), motivatorCategoryListJsonParams(), "not cache", this);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public JSONObject motivatorCategoryListJsonParams() throws JSONException {
        JSONObject dataSet = new JSONObject();
        dataSet.put(AppConstant.KEY_SERVICE_ID,AppConstant.KEY_MOTIVATOR_CATEGORY);
        Log.d("object", "" + dataSet);
        return dataSet;
    }
    public void getDistrictList() {
        try {
            new ApiService(this).makeJSONObjectRequest("DistrictList", Api.Method.POST, UrlGenerator.getOpenUrl(), districtListJsonParams(), "not cache", this);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void getBlockList() {
        try {
            new ApiService(this).makeJSONObjectRequest("BlockList", Api.Method.POST, UrlGenerator.getOpenUrl(), blockListJsonParams(), "not cache", this);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public JSONObject districtListJsonParams() throws JSONException {
        JSONObject dataSet = new JSONObject();
        dataSet.put(AppConstant.KEY_SERVICE_ID,AppConstant.KEY_DISTRICT_LIST_ALL);
        Log.d("object", "" + dataSet);
        return dataSet;
    }

    public JSONObject blockListJsonParams() throws JSONException {
        JSONObject dataSet = new JSONObject();
        dataSet.put(AppConstant.KEY_SERVICE_ID,AppConstant.KEY_BLOCK_LIST_ALL);
        Log.d("object", "" + dataSet);
        return dataSet;
    }
    @Override
    public void OnMyResponse(ServerResponse serverResponse) {
        try {
            JSONObject responseObj = serverResponse.getJsonResponse();
            String urlType = serverResponse.getApi();
            String status = responseObj.getString(AppConstant.KEY_STATUS);
            String response = responseObj.getString(AppConstant.KEY_RESPONSE);


            if ("LoginScreen".equals(urlType)) {
                if (status.equalsIgnoreCase("OK")) {
                    if (response.equals("LOGIN_SUCCESS")) {
                        String key = responseObj.getString(AppConstant.KEY_USER);
                        String user_data = responseObj.getString(AppConstant.USER_DATA);
                        String decryptedKey = Utils.decrypt(prefManager.getEncryptPass(), key);
                        String userDataDecrypt = Utils.decrypt(prefManager.getEncryptPass(), user_data);
                        Log.d("userdatadecry", "" + userDataDecrypt);
                        jsonObject = new JSONObject(userDataDecrypt);
                        JSONArray districtCodeJsonArray = new JSONArray();
                        districtCodeJsonArray.put(jsonObject.get(AppConstant.DISTRICT_CODE));
                        prefManager.setDistrictCodeJson(districtCodeJsonArray);
                        prefManager.setDistrictCode(jsonObject.get(AppConstant.DISTRICT_CODE));
                        prefManager.setParicularDCode(jsonObject.get(AppConstant.DISTRICT_CODE));
                        prefManager.setBlockCode(jsonObject.get(AppConstant.BLOCK_CODE));
                        prefManager.setPvCode(jsonObject.get(AppConstant.PV_CODE));
                        prefManager.setDistrictName(jsonObject.get(AppConstant.DISTRICT_NAME));
                        prefManager.setBlockName(jsonObject.get(AppConstant.BLOCK_NAME));
                        prefManager.setDesignation(jsonObject.get(AppConstant.DESIG_NAME));
                        prefManager.setName(String.valueOf(jsonObject.get(AppConstant.DESIG_NAME)));
                        Log.d("userdata", "" + prefManager.getDistrictCode() + prefManager.getBlockCode() + prefManager.getPvCode() + prefManager.getDistrictName() + prefManager.getBlockName()+prefManager.getName());
                        prefManager.setUserPassKey(decryptedKey);
                        showHomeScreen();

                        String result=jsonObject.getString("odf_test_qualified");
                        if(result.equals("N")){
                            Utils.showAlertResult(LoginScreen.this,"Attend The ODF Plus Qualifying Test");
                        }else if(result.equals("Y")){
                            showHomeScreen();
                        }

                    } else {
                        if (response.equals("LOGIN_FAILED")) {
                            String message = responseObj.getString(AppConstant.KEY_MESSAGE);
                            Utils.showAlert(this, message);
                        }
                    }
                }

            }
            if ("Gender".equals(urlType) && responseObj != null) {
                status  = responseObj.getString(AppConstant.KEY_STATUS);
                response = responseObj.getString(AppConstant.KEY_RESPONSE);
                if (status.equalsIgnoreCase("OK")&& response.equalsIgnoreCase("OK")){
                    JSONArray jsonarray = responseObj.getJSONArray(AppConstant.JSON_DATA);
                    prefManager.setGenderList(jsonarray.toString());
                    Log.d("Gender", "" + responseObj.getJSONArray(AppConstant.JSON_DATA));
                }

            }
            if ("EducationalQualification".equals(urlType) && responseObj != null) {
                status  = responseObj.getString(AppConstant.KEY_STATUS);
                response = responseObj.getString(AppConstant.KEY_RESPONSE);
                if (status.equalsIgnoreCase("OK")&& response.equalsIgnoreCase("OK")) {
                    JSONArray jsonarray = responseObj.getJSONArray(AppConstant.JSON_DATA);
                    prefManager.setEducationalQualification(jsonarray.toString());
                    Log.d("EducationalQua", "" + responseObj.getJSONArray(AppConstant.JSON_DATA));
                }
            }
            if ("MotivatorCategoryList".equals(urlType) && responseObj != null) {
                status  = responseObj.getString(AppConstant.KEY_STATUS);
                response = responseObj.getString(AppConstant.KEY_RESPONSE);
                if (status.equalsIgnoreCase("OK") && response.equalsIgnoreCase("OK")) {
                    new MotivatorCategoryList().execute(responseObj.getJSONArray(AppConstant.JSON_DATA));
                } else if (status.equalsIgnoreCase("OK") && response.equalsIgnoreCase("NO_RECORD")) {
                    Log.d("Record", responseObj.getString(AppConstant.KEY_MESSAGE));
                }
                Log.d("MotivatorCategoryList", "" + responseObj.getJSONArray(AppConstant.JSON_DATA));
            }
            if ("DistrictList".equals(urlType) && responseObj != null) {
                status  = responseObj.getString(AppConstant.KEY_STATUS);
                response = responseObj.getString(AppConstant.KEY_RESPONSE);
                if (status.equalsIgnoreCase("OK") && response.equalsIgnoreCase("OK")) {
                    new  InsertDistrictTask().execute(responseObj.getJSONArray(AppConstant.JSON_DATA));
                } else if (status.equalsIgnoreCase("OK") && response.equalsIgnoreCase("NO_RECORD")) {
                    Log.d("Record", responseObj.getString(AppConstant.KEY_MESSAGE));
                }
                Log.d("DistrictList", "" + responseObj.getJSONArray(AppConstant.JSON_DATA));
            }

            if ("BlockList".equals(urlType) && responseObj != null) {
                status  = responseObj.getString(AppConstant.KEY_STATUS);
                response = responseObj.getString(AppConstant.KEY_RESPONSE);
                if (status.equalsIgnoreCase("OK") && response.equalsIgnoreCase("OK")) {
                    new  InsertBlockTask().execute(responseObj.getJSONArray(AppConstant.JSON_DATA));
                } else if (status.equalsIgnoreCase("OK") && response.equalsIgnoreCase("NO_RECORD")) {
                    Log.d("Record", responseObj.getString(AppConstant.KEY_MESSAGE));
                }
                Log.d("BlockList", "" + responseObj.getJSONArray(AppConstant.JSON_DATA));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @SuppressLint("StaticFieldLeak")
    public class InsertDistrictTask extends AsyncTask<JSONArray ,Void ,Void> {

        @Override
        protected Void doInBackground(JSONArray... params) {
            dbData.open();
            ArrayList<ODF_Thittam> districtlist_count = dbData.getAll_District();
            if (districtlist_count.size() <= 0) {
                if (params.length > 0) {
                    JSONArray jsonArray = new JSONArray();
                    jsonArray = params[0];
                    for (int i = 0; i < jsonArray.length(); i++) {
                        ODF_Thittam districtListValue = new ODF_Thittam();
                        try {
                            districtListValue.setDistictCode(jsonArray.getJSONObject(i).getString(AppConstant.DISTRICT_CODE));
                            districtListValue.setDistrictName(jsonArray.getJSONObject(i).getString(AppConstant.DISTRICT_NAME));

                            dbData.insertDistrict(districtListValue);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }

            }
            return null;
        }

    }

    @SuppressLint("StaticFieldLeak")
    public class InsertBlockTask extends AsyncTask<JSONArray ,Void ,Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressHUD = ProgressHUD.show(LoginScreen.this, "Downloading", true, false, null);
        }

        @Override
        protected Void doInBackground(JSONArray... params) {
            dbData.open();
            ArrayList<ODF_Thittam> blocklist_count = dbData.getAll_Block();
            if (blocklist_count.size() <= 0) {
                if (params.length > 0) {
                    JSONArray jsonArray = new JSONArray();
                    jsonArray = params[0];
                    for (int i = 0; i < jsonArray.length(); i++) {
                        ODF_Thittam blocktListValue = new ODF_Thittam();
                        try {
                            blocktListValue.setDistictCode(jsonArray.getJSONObject(i).getString(AppConstant.DISTRICT_CODE));
                            blocktListValue.setBlockCode(jsonArray.getJSONObject(i).getString(AppConstant.BLOCK_CODE));
                            blocktListValue.setBlockName(jsonArray.getJSONObject(i).getString(AppConstant.BLOCK_NAME));

                            dbData.insertBlock(blocktListValue);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }

            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if(progressHUD!=null){
                progressHUD.cancel();
            }

            flag=true;
        }
    }

    @SuppressLint("StaticFieldLeak")
    public class MotivatorCategoryList extends AsyncTask<JSONArray, Void, Void> {

        @Override
        protected Void doInBackground(JSONArray... params) {
            dbData.open();
            ArrayList<ODF_Thittam> categoryListCount = dbData.getAllCategoryList();
            if (categoryListCount.size() <= 0) {
                if (params.length > 0) {
                    JSONArray jsonArray = new JSONArray();
                    jsonArray = params[0];
                    for (int i = 0; i < jsonArray.length(); i++) {
                        ODF_Thittam motivatorCategoryListValue = new ODF_Thittam();
                        try {
                            motivatorCategoryListValue.setMotivatorCategoryId(jsonArray.getJSONObject(i).getInt(AppConstant.KEY_MOTIVATOR_CATEGORY_ID));
                            motivatorCategoryListValue.setMotivatorCategoryName(jsonArray.getJSONObject(i).getString(AppConstant.KEY_MOTIVATOR_CATEGORY_NAME));
                            motivatorCategoryListValue.setMotivatorCategoryName(jsonArray.getJSONObject(i).getString(AppConstant.KEY_MOTIVATOR_CATEGORY_NAME_FULL_FORM)+"("+jsonArray.getJSONObject(i).getString(AppConstant.KEY_MOTIVATOR_CATEGORY_NAME)+")");


                            dbData.insertCategoryList(motivatorCategoryListValue);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }

            }
            return null;
        }
    }


    @Override
    public void OnError(VolleyError volleyError) {
        Utils.showAlert(this, "Login Again");
    }

//    @Override
//    protected void onResume() {
//        super.onResume();
//        showHomeScreen();
//    }

    private void showHomeScreen() {
        Intent intent = new Intent(LoginScreen.this, HomePage.class);
        intent.putExtra("Home", "Login");
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
    }

    public void offline_mode(String name, String pass) {
        String userName = prefManager.getUserName();
        String password = prefManager.getUserPassword();
        if (name.equals(userName) && pass.equals(password)) {
            showHomeScreen();
        } else {
            Utils.showAlert(this, "No data available for offline. Please Turn On Your Network");
        }
    }

}
