package com.nic.ODF_Thittam.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.telephony.TelephonyManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.util.Base64;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.VolleyError;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.nic.ODF_Thittam.R;
import com.nic.ODF_Thittam.adapter.CommonAdapter;
import com.nic.ODF_Thittam.adapter.WorkingAreaListAdapter;
import com.nic.ODF_Thittam.api.Api;
import com.nic.ODF_Thittam.api.ApiService;
import com.nic.ODF_Thittam.api.ServerResponse;
import com.nic.ODF_Thittam.constant.AppConstant;
import com.nic.ODF_Thittam.dataBase.DBHelper;
import com.nic.ODF_Thittam.model.ODF_Thittam;
import com.nic.ODF_Thittam.session.PrefManager;
import com.nic.ODF_Thittam.support.MyCustomTextView;
import com.nic.ODF_Thittam.support.MyEditTextView;
import com.nic.ODF_Thittam.support.ProgressHUD;
import com.nic.ODF_Thittam.utils.CameraUtils;
import com.nic.ODF_Thittam.utils.UrlGenerator;
import com.nic.ODF_Thittam.utils.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.nic.ODF_Thittam.dataBase.DBHelper.BANKLIST_BRANCH_TABLE_NAME;
import static com.nic.ODF_Thittam.dataBase.DBHelper.BANKLIST_TABLE_NAME;
import static com.nic.ODF_Thittam.dataBase.DBHelper.BLOCK_TABLE_NAME;
import static com.nic.ODF_Thittam.dataBase.DBHelper.DISTRICT_TABLE_NAME;
import static com.nic.ODF_Thittam.dataBase.DBHelper.MOTIVATOR_CATEGORY_LIST_TABLE_NAME;
import static com.nic.ODF_Thittam.dataBase.DBHelper.VILLAGE_TABLE_NAME;


public class RegisterScreen extends AppCompatActivity implements View.OnClickListener, Api.ServerResponseListener {

    private Button btn_register;
    private Handler handler = new Handler();
    private MyEditTextView motivator_name, motivator_address, motivator_mobileNO, motivator_email_id, motivator_state_level_tv, motivator_district_level_tv, motivator_block_level_tv, motivator_position_tv, motivator_account_tv, verify_motivator_account_tv, motivator_ifsc_tv;
    private MyCustomTextView motivator_bank_tv, motivator_branch_tv;
    private LinearLayout account_layout, verify_account_layout_show_hide, ifsc_layout, bank_layout, branch_layout;
    private static MyCustomTextView motivator_dob_tv;
    private RelativeLayout dob_layout, edit_image, verify_account_layout, phone_no_layout, email_id_layout;
    private LinearLayout position_layout;
    private Spinner sp_block, sp_district, sp_village, sp_category, gender_spinner, educational_qualification_spinner;
    private RadioButton motivator, other;
    private PrefManager prefManager;
    private List<ODF_Thittam> Block = new ArrayList<>();
    private List<ODF_Thittam> District = new ArrayList<>();
    private List<ODF_Thittam> Village = new ArrayList<>();
    private List<ODF_Thittam> Category = new ArrayList<>();
    ArrayList<ODF_Thittam> genderList = new ArrayList<>();
    ArrayList<ODF_Thittam> educationList = new ArrayList<>();
    private List<ODF_Thittam> BankDetails = new ArrayList<>();
    private List<ODF_Thittam> BranchDetails = new ArrayList<>();
    private ProgressHUD progressHUD;

    private ImageView arrowImage, arrowImageUp, back_img, tick;
    private NestedScrollView scrollView;
    private CircleImageView profile_image, profile_image_preview;
    String pref_Block, pref_district, pref_Village;
    public static DBHelper dbHelper;
    public static SQLiteDatabase db;
    List<String> array = new ArrayList<String>();
    List<String> brancharray = new ArrayList<String>();
    private Animation animation;
    private LinearLayout childlayout;
    public static final String GALLERY_DIRECTORY_NAME = "Hello Camera";
    public static final int MEDIA_TYPE_IMAGE = 1;
    public static final int MEDIA_TYPE_VIDEO = 2;

    private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 2500;
    private static final int CAMERA_CAPTURE_VIDEO_REQUEST_CODE = 200;
    private static final int PERMISSION_REQUEST_CODE = 200;
    private static String imageStoragePath;
    public static final int BITMAP_SAMPLE_SIZE = 8;
    private ExifInterface exifObject;
    private Integer isMotivatorOthers;
    String selectedGender, selectedGenderId, selectedEducation, selectedEducationId;
    String dcode, bcode, pv_code,ifsc_code;
    ArrayList<ODF_Thittam> VillageList;


    String IMEINumber;
    private static final int REQUEST_CODE = 101;

    RecyclerView working_area_recycler;
    FloatingActionButton add_working_area_btn;
    String load_alert_village="";
    private List<ODF_Thittam> AlertBlock = new ArrayList<>();
    private List<ODF_Thittam> AlertDistrict = new ArrayList<>();
    private List<ODF_Thittam> AlertVillage = new ArrayList<>();
    private List<ODF_Thittam> working_area_list = new ArrayList<>();
    String alert_dcode, alert_bcode,alert_pv_code;
    String alert_dname, alert_bname,alert_pv_name;
    Spinner alert_district,alert_block,alert_village;
    WorkingAreaListAdapter workingAreaListAdapter;
    int  serve_villages_limit_count=0;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        setContentView(R.layout.fragment_signup);
        try {
            dbHelper = new DBHelper(this);
            db = dbHelper.getWritableDatabase();
        } catch (Exception e) {
            e.printStackTrace();
        }

        intializeUI();

    }

    public void intializeUI() {
        prefManager = new PrefManager(this);
        btn_register = (Button) findViewById(R.id.btn_register);
        motivator_name = (MyEditTextView) findViewById(R.id.motivator_name);
        sp_block = (Spinner) findViewById(R.id.block);
        sp_district = (Spinner) findViewById(R.id.district);
        sp_village = (Spinner) findViewById(R.id.village);
        sp_category = (Spinner) findViewById(R.id.category);
        gender_spinner = (Spinner) findViewById(R.id.gender_spinner);
        educational_qualification_spinner = (Spinner) findViewById(R.id.educational_qualification_spinner);
        motivator_address = (MyEditTextView) findViewById(R.id.motivator_address);
        motivator_mobileNO = (MyEditTextView) findViewById(R.id.motivator_mobile_no);
        motivator_email_id = (MyEditTextView) findViewById(R.id.motivator_email_id);
        motivator_state_level_tv = (MyEditTextView) findViewById(R.id.motivator_state_level_tv);
        motivator_district_level_tv = (MyEditTextView) findViewById(R.id.motivator_district_level_tv);
        motivator_block_level_tv = (MyEditTextView) findViewById(R.id.motivator_block_level_tv);
        motivator_account_tv = (MyEditTextView) findViewById(R.id.motivator_account_tv);
        verify_motivator_account_tv = (MyEditTextView) findViewById(R.id.verify_motivator_account_tv);
        motivator_bank_tv = (MyCustomTextView) findViewById(R.id.motivator_bank_tv);
        motivator_branch_tv = (MyCustomTextView) findViewById(R.id.motivator_branch_tv);
        motivator_ifsc_tv = (MyEditTextView) findViewById(R.id.motivator_ifsc_tv);
        motivator_dob_tv = (MyCustomTextView) findViewById(R.id.motivator_dob_tv);
        motivator_position_tv = (MyEditTextView) findViewById(R.id.motivator_position_tv);
        scrollView = (NestedScrollView) findViewById(R.id.scroll_view);
        arrowImage = (ImageView) findViewById(R.id.arrow_image);
        arrowImageUp = (ImageView) findViewById(R.id.arrow_image_up);
        tick = (ImageView) findViewById(R.id.tick);
        childlayout = (LinearLayout) findViewById(R.id.child_view);
        account_layout = (LinearLayout) findViewById(R.id.account_layout);
        verify_account_layout_show_hide = (LinearLayout) findViewById(R.id.verify_account_layout_show_hide);
        ifsc_layout = (LinearLayout) findViewById(R.id.ifsc_layout);
        bank_layout = (LinearLayout) findViewById(R.id.bank_layout);
        branch_layout = (LinearLayout) findViewById(R.id.branch_layout);
        dob_layout = (RelativeLayout) findViewById(R.id.dob_layout);
        edit_image = (RelativeLayout) findViewById(R.id.edit_image);
        verify_account_layout = (RelativeLayout) findViewById(R.id.verify_account_layout);
        phone_no_layout = (RelativeLayout) findViewById(R.id.phone_no_layout);
        email_id_layout = (RelativeLayout) findViewById(R.id.email_id_layout);
        position_layout = (LinearLayout) findViewById(R.id.position_layout);
        profile_image = (CircleImageView) findViewById(R.id.profile_image);
        profile_image_preview = (CircleImageView) findViewById(R.id.profile_image_preview);
        back_img = (ImageView) findViewById(R.id.back_img);
        motivator = (RadioButton) findViewById(R.id.motivator);
        other = (RadioButton) findViewById(R.id.others);
        working_area_recycler = (RecyclerView) findViewById(R.id.working_area_recycler);
        add_working_area_btn = (FloatingActionButton) findViewById(R.id.add_btn);
        arrowImage.setOnClickListener(this);
        arrowImageUp.setOnClickListener(this);
        dob_layout.setOnClickListener(this);
        edit_image.setOnClickListener(this);
        back_img.setOnClickListener(this);
        tick.setOnClickListener(this);
        btn_register.setOnClickListener(this);
        motivator.setChecked(true);
        other.setChecked(false);

//        if (childlayout.getMeasuredHeight() > scrollView.getMeasuredHeight()) {
//            showArrowImage();
//        }
//        scrollView.fullScroll(ScrollView.FOCUS_UP);

//        scrollView.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
//            @Override
//            public void onScrollChanged() {
//                View view = (View) scrollView.getChildAt(scrollView.getChildCount() - 1);
//                int diffBottom = (view.getBottom() - (scrollView.getHeight() + scrollView.getScrollY()));
//
//                if (diffBottom == 0) {
//                    arrowImage.setVisibility(View.GONE);
//                    arrowImage.clearAnimation();
//                } else if (scrollView.getScrollY() == 0) {
//                    arrowImageUp.setVisibility(View.GONE);
//                    arrowImageUp.clearAnimation();
//                } else {
//                    showArrowImage();
//                    showUpArrowImage();
//                }
//            }
//        });
//        getBankNameList();
//        getBankBranchList();
        sp_district.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    sp_block.setClickable(false);
                    sp_block.setVisibility(View.GONE);
                } else {
                    sp_block.setClickable(true);
                    sp_block.setVisibility(View.VISIBLE);
                    dcode = District.get(position).getDistictCode();
                }
                pref_district = District.get(position).getDistrictName();
                prefManager.setDistrictName(pref_district);

                blockFilterSpinner(District.get(position).getDistictCode());
                prefManager.setDistrictCode(District.get(position).getDistictCode());


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        sp_block.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    sp_village.setClickable(false);
                    sp_village.setVisibility(View.GONE);
                } else {
                    sp_village.setClickable(true);
                    sp_village.setVisibility(View.VISIBLE);
                    bcode = Block.get(position).getBlockCode();
                    if (Utils.isOnline()) {
                        load_alert_village="";
                        getVillageList();
                    } else {
                        Utils.showAlert(RegisterScreen.this, "Turn on Mobile Data!");
                    }
                }
                pref_Block = Block.get(position).getBlockName();
                prefManager.setBlockName(pref_Block);
                prefManager.setKeySpinnerSelectedBlockcode(Block.get(position).getBlockCode());


                //villageFilterSpinner(Block.get(position).getBlockCode());


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        sp_village.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                pref_Village = VillageList.get(position).getPvName();
                pv_code = VillageList.get(position).getPvCode();
                prefManager.setVillageListPvName(pref_Village);
                prefManager.setKeySpinnerSelectedPvcode(VillageList.get(position).getPvCode());

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        sp_category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (Category.get(position).getMotivatorCategoryId() == 5) {
                    position_layout.setVisibility(View.VISIBLE);
                } else {
                    position_layout.setVisibility(View.GONE);

                }
                prefManager.setSpinnerSelectedCategoryName(Category.get(position).getMotivatorCategoryName());
                prefManager.setSpinnerSelectedCategoryId(Category.get(position).getMotivatorCategoryId());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        gender_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedGender = (genderList.get(position).getGenderEn());
                selectedGenderId = (genderList.get(position).getGenderCode());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        educational_qualification_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedEducation = (educationList.get(position).getEducationName());
                selectedEducationId = (educationList.get(position).getEducationCode());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        motivator.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {if (isChecked) {
                    isMotivatorOthers = 1;
                    other.setChecked(false);
                    account_layout.setVisibility(View.VISIBLE);
                    verify_account_layout_show_hide.setVisibility(View.VISIBLE);
                    ifsc_layout.setVisibility(View.VISIBLE);
                    bank_layout.setVisibility(View.VISIBLE);
                    branch_layout.setVisibility(View.VISIBLE);

                }    }
        });
        other.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    isMotivatorOthers = 2;
                    motivator.setChecked(false);
                    account_layout.setVisibility(View.GONE);
                    verify_account_layout_show_hide.setVisibility(View.GONE);
                    ifsc_layout.setVisibility(View.GONE);
                    bank_layout.setVisibility(View.GONE);
                    branch_layout.setVisibility(View.GONE);
                    motivator_account_tv.getText().clear();
                    verify_motivator_account_tv.getText().clear();
                    motivator_ifsc_tv.getText().clear();
                    motivator_bank_tv.setText("");
                    motivator_branch_tv.setText("");

                }

            }
        });

        add_working_area_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!"Select District".equalsIgnoreCase(District.get(sp_district.getSelectedItemPosition()).getDistrictName())) {
                    if (!"Select Block".equalsIgnoreCase(Block.get(sp_block.getSelectedItemPosition()).getBlockName())) {
                        if (!"Select Village".equalsIgnoreCase(VillageList.get(sp_village.getSelectedItemPosition()).getPvName())) {
                            if(serve_villages_limit_count>working_area_list.size()){
                                addWorkingArea();
                            }
                            else {
                                Utils.showAlert(RegisterScreen.this,"Limit Exceeded");
                            }
                        } else {
                            Utils.showAlert(RegisterScreen.this, "உங்கள் கிராமத்தைத் தேர்ந்தெடுக்கவும்!");
                        }
                    } else {
                        Utils.showAlert(RegisterScreen.this, "உங்கள் வட்டத்தைத் தேர்ந்தெடுக்கவும்!");
                    }
                } else {
                    Utils.showAlert(RegisterScreen.this, "உங்கள் மாவட்டத்தைத் தேர்ந்தெடுக்கவும்!");
                }


            }
        });

        loadOfflineDistrictListDBValues();
        loadCategoryListDBValues();
        loadGenderList();
        loadEducationList();
        textFieldValidation();
        getCountLimit();
    }

    public JSONObject villageListJsonParams() throws JSONException {
        JSONObject dataSet = new JSONObject();
        dataSet.put(AppConstant.KEY_SERVICE_ID, AppConstant.KEY_VILLAGE_LIST_DISTRICT_BLOCK_WISE);
        if(load_alert_village.equals("Yes")){
            dataSet.put(AppConstant.DISTRICT_CODE, alert_dcode);
            dataSet.put(AppConstant.BLOCK_CODE, alert_bcode);
        }
        else{
            dataSet.put(AppConstant.DISTRICT_CODE, dcode);
            dataSet.put(AppConstant.BLOCK_CODE, bcode);
        }

        Log.d("VillageList", "" + dataSet);
        return dataSet;
    } public JSONObject getCountOfWillingWork() throws JSONException {
        JSONObject dataSet = new JSONObject();
        dataSet.put(AppConstant.KEY_SERVICE_ID, "serve_villages_limit");
        Log.d("serve_villages_limit", "" + dataSet);
        return dataSet;
    }


    private void loadEducationList() {
        try {
            JSONArray jsonarray = new JSONArray(prefManager.getEducationalQualification());
            if (jsonarray != null && jsonarray.length() > 0) {
                ODF_Thittam ODF_Thittam = new ODF_Thittam();
                ODF_Thittam.setEducationName("Select Educational Qualification");
                educationList.add(ODF_Thittam);
                for (int i = 0; i < jsonarray.length(); i++) {
                    JSONObject jsonobject = jsonarray.getJSONObject(i);
                    String education_code = jsonobject.getString("qualification_id");
                    String education_name = (jsonobject.getString("qualification_name"));
                    ODF_Thittam roadListValue = new ODF_Thittam();
                    roadListValue.setEducationCode(education_code);
                    roadListValue.setEducationName(education_name);
                    educationList.add(roadListValue);
                }
                educational_qualification_spinner.setAdapter(new CommonAdapter(this, educationList, "EducationList"));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void loadGenderList() {
        try {
            JSONArray jsonarray = new JSONArray(prefManager.getGenderList());
            if (jsonarray != null && jsonarray.length() > 0) {
                ODF_Thittam ODF_Thittam = new ODF_Thittam();
                ODF_Thittam.setGenderEn("Select Gender");
                genderList.add(ODF_Thittam);
                for (int i = 0; i < jsonarray.length(); i++) {
                    JSONObject jsonobject = jsonarray.getJSONObject(i);
                    String gender_code = jsonobject.getString("gender_code");
                    String gender_name_en = (jsonobject.getString("gender_name_en"));
                    String gender_name_ta = (jsonobject.getString("gender_name_ta"));
                    ODF_Thittam roadListValue = new ODF_Thittam();
                    roadListValue.setGenderCode(gender_code);
                    roadListValue.setGenderEn(gender_name_en);
                    roadListValue.setGenderTa(gender_name_ta);
                    genderList.add(roadListValue);
                }
                gender_spinner.setAdapter(new CommonAdapter(this, genderList, "GenderList"));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void loadVillageSpinner(JSONArray jsonArray) {
        try {
            VillageList = new ArrayList<>();
            ODF_Thittam villageListValue1 = new ODF_Thittam();
            villageListValue1.setPvName("Select Village");
            VillageList.add(villageListValue1);
            for (int i = 0; i < jsonArray.length(); i++) {
                ODF_Thittam villageListValue = new ODF_Thittam();
                try {
                    villageListValue.setDistictCode(jsonArray.getJSONObject(i).getString(AppConstant.DISTRICT_CODE));
                    villageListValue.setBlockCode(jsonArray.getJSONObject(i).getString(AppConstant.BLOCK_CODE));
                    villageListValue.setPvCode(jsonArray.getJSONObject(i).getString(AppConstant.PV_CODE));
                    villageListValue.setPvName(jsonArray.getJSONObject(i).getString(AppConstant.PV_NAME));
                    VillageList.add(villageListValue);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
            sp_village.setAdapter(new CommonAdapter(this, VillageList, "VillageList"));


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void textFieldValidation() {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
//                motivator_account_tv.setTransformationMethod(new AsteriskPasswordTransformationMethod());

            }
        };
        handler.postDelayed(runnable, 5000);
        verify_motivator_account_tv.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if (!motivator_account_tv.getText().toString().equalsIgnoreCase(verify_motivator_account_tv.getText().toString())) {
                    verify_account_layout.setBackgroundResource(R.drawable.red_rectangle_box);
                } else {
                    verify_account_layout.setBackgroundResource(R.drawable.rectangle_box);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!motivator_account_tv.getText().toString().equalsIgnoreCase(verify_motivator_account_tv.getText().toString())) {
                    verify_account_layout.setBackgroundResource(R.drawable.red_rectangle_box);
                } else {
                    verify_account_layout.setBackgroundResource(R.drawable.rectangle_box);
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!motivator_account_tv.getText().toString().equalsIgnoreCase(verify_motivator_account_tv.getText().toString())) {
                    verify_account_layout.setBackgroundResource(R.drawable.red_rectangle_box);
                } else {
                    verify_account_layout.setBackgroundResource(R.drawable.rectangle_box);
                }
            }
        });


        motivator_mobileNO.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (Utils.isValidMobile(motivator_mobileNO.getText().toString())) {
                    phone_no_layout.setBackgroundResource(R.drawable.rectangle_box);
                } else {
                    phone_no_layout.setBackgroundResource(R.drawable.red_rectangle_box);
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
        });

        motivator_email_id.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (Utils.isEmailValid(motivator_email_id.getText().toString())) {
                    email_id_layout.setBackgroundResource(R.drawable.rectangle_box);
                } else {
                    email_id_layout.setBackgroundResource(R.drawable.red_rectangle_box);
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
        });

        (motivator_ifsc_tv).setOnEditorActionListener(
                new MyEditTextView.OnEditorActionListener() {
                    @Override
                    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                        if (actionId == EditorInfo.IME_ACTION_SEARCH ||
                                actionId == EditorInfo.IME_ACTION_DONE ||
                                event != null &&
                                        event.getAction() == KeyEvent.ACTION_DOWN &&
                                        event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                            if (event == null || !event.isShiftPressed()) {
// the user is done typing.
                                Log.d("ifsc_check", motivator_ifsc_tv.getText().toString());
                                //fetchBranchName(motivator_ifsc_tv.getText().toString().toUpperCase());
                                getBankandBranchName(motivator_ifsc_tv.getText().toString().toUpperCase());
                                hide_keyboard();
                                return true; // consume.
                            }
                        }
                        return false; // pass on to other listeners.
                    }
                }
        );
        motivator_ifsc_tv.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

//You can identify which key pressed buy checking keyCode value with KeyEvent.KEYCODE_
                if (keyCode == KeyEvent.KEYCODE_DEL) {
//this is for backspace
                    tick.setVisibility(View.GONE);
                    motivator_branch_tv.setText("");
                    motivator_bank_tv.setText("");
                }
                return false;
            }
        });
        motivator_ifsc_tv.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!motivator_ifsc_tv.getText().toString().isEmpty()) {
                    tick.setVisibility(View.VISIBLE);
                } else {
                    tick.setVisibility(View.GONE);
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
        });

    }

    private void hide_keyboard() {
        InputMethodManager imm = (InputMethodManager) getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(motivator_ifsc_tv.getWindowToken(), 0);
    }

    public void fetchBranchName(String ifsc_code) {
        String branchNameSql = "SELECT * FROM " + BANKLIST_BRANCH_TABLE_NAME + " WHERE ifsc = '" + ifsc_code + "'";
        Log.d("branchNameSql", "" + branchNameSql);
        Cursor branchNameList = getRawEvents(branchNameSql, null);
        if (branchNameList.getCount() > 0) {
            if (branchNameList.moveToFirst()) {
                do {
                    String branch = branchNameList.getString(branchNameList.getColumnIndexOrThrow(AppConstant.BRANCH_NAME));
                    String branch_id = branchNameList.getString(branchNameList.getColumnIndexOrThrow(AppConstant.BRANCH_ID));
                    Integer bank_id = branchNameList.getInt(branchNameList.getColumnIndexOrThrow(AppConstant.BANK_ID));
                    prefManager.setKeyAutocompleteSelectedBranchID(Integer.valueOf(branch_id));
                    prefManager.setKeyAutocompleteSelectedIfscCode(ifsc_code);
                    prefManager.setKeyAutocompleteSelectedBankID(bank_id);
                    motivator_branch_tv.setText(branch);
                    fetchBankName(bank_id);
                    Log.d("branch", "" + branch);
                } while (branchNameList.moveToNext());
            }
        } else {
            Utils.showAlert(RegisterScreen.this, "Enter the valid IFSC!");
            motivator_bank_tv.setText("");
            motivator_branch_tv.setText("");
        }
    }

    public void fetchBankName(Integer bank_id) {
        String bankNameSql = "SELECT * FROM " + BANKLIST_TABLE_NAME + " WHERE bank_id = " + bank_id;
        Log.d("bankNameSql", "" + bankNameSql);
        Cursor bankNameList = getRawEvents(bankNameSql, null);
        if (bankNameList.getCount() > 0) {
            if (bankNameList.moveToFirst()) {
                do {
                    String bank_name = bankNameList.getString(bankNameList.getColumnIndexOrThrow(AppConstant.BANK_NAME));
                    motivator_bank_tv.setText(bank_name);
                    Log.d("bank_name", "" + bank_name);
                } while (bankNameList.moveToNext());
            }
        }
    }

    public void loadOfflineDistrictListDBValues() {
        Cursor DistrictList = getRawEvents("Select * from " + DISTRICT_TABLE_NAME + " WHERE dcode != 29 order by dname asc", null);
        District.clear();
        ODF_Thittam ODF_Thittam = new ODF_Thittam();
        ODF_Thittam.setDistrictName("Select District");
        District.add(ODF_Thittam);
        if (DistrictList.getCount() > 0) {
            if (DistrictList.moveToFirst()) {
                do {
                    ODF_Thittam districtList = new ODF_Thittam();
                    String districtCode = DistrictList.getString(DistrictList.getColumnIndexOrThrow(AppConstant.DISTRICT_CODE));
                    String districtName = DistrictList.getString(DistrictList.getColumnIndexOrThrow(AppConstant.DISTRICT_NAME));
                    districtList.setDistictCode(districtCode);
                    districtList.setDistrictName(districtName);
                    District.add(districtList);
                } while (DistrictList.moveToNext());
            }
        }
        sp_district.setAdapter(new CommonAdapter(this, District, "DistrictList"));
    }

    public void blockFilterSpinner(String filterBlock) {

        String blocksql = "SELECT * FROM " + BLOCK_TABLE_NAME + " WHERE dcode = " + filterBlock + " order by bname asc";
        Log.d("blocksql", blocksql);
        Cursor BlockList = getRawEvents(blocksql, null);
        Block.clear();
        ODF_Thittam blockListValue = new ODF_Thittam();
        blockListValue.setBlockName("Select Block");
        Block.add(blockListValue);
        if (BlockList.getCount() > 0) {
            if (BlockList.moveToFirst()) {
                do {
                    ODF_Thittam blockList = new ODF_Thittam();
                    String districtCode = BlockList.getString(BlockList.getColumnIndexOrThrow(AppConstant.DISTRICT_CODE));
                    String blockCode = BlockList.getString(BlockList.getColumnIndexOrThrow(AppConstant.BLOCK_CODE));
                    String blockName = BlockList.getString(BlockList.getColumnIndexOrThrow(AppConstant.BLOCK_NAME));
                    blockList.setDistictCode(districtCode);
                    blockList.setBlockCode(blockCode);
                    blockList.setBlockName(blockName);
                    Block.add(blockList);
                } while (BlockList.moveToNext());
            }
        }
        sp_block.setAdapter(new CommonAdapter(this, Block, "BlockList"));
    }

    public void villageFilterSpinner(String filterVillage) {
        String villageSql = "SELECT * FROM " + VILLAGE_TABLE_NAME + " WHERE dcode = " + prefManager.getDistrictCode() + " and bcode = " + filterVillage;
        Log.d("villageSql", "" + villageSql);
        Cursor VillageList = getRawEvents(villageSql, null);
        Village.clear();
        ODF_Thittam villageListValue = new ODF_Thittam();
        villageListValue.setVillageListPvName("Select Village");
        Village.add(villageListValue);
        if (VillageList.getCount() > 0) {
            if (VillageList.moveToFirst()) {
                do {
                    ODF_Thittam villageList = new ODF_Thittam();
                    String districtCode = VillageList.getString(VillageList.getColumnIndexOrThrow(AppConstant.DISTRICT_CODE));
                    String blockCode = VillageList.getString(VillageList.getColumnIndexOrThrow(AppConstant.BLOCK_CODE));
                    String pvCode = VillageList.getString(VillageList.getColumnIndexOrThrow(AppConstant.PV_CODE));
                    String pvname = VillageList.getString(VillageList.getColumnIndexOrThrow(AppConstant.PV_NAME));

                    villageList.setVillageListDistrictCode(districtCode);
                    villageList.setVillageListBlockCode(blockCode);
                    villageList.setVillageListPvCode(pvCode);
                    villageList.setVillageListPvName(pvname);

                    Village.add(villageList);
                    Log.d("spinnersize", "" + Village.size());
                } while (VillageList.moveToNext());
            }
        }
        sp_village.setAdapter(new CommonAdapter(this, Village, "VillageList"));
    }

    public void loadCategoryListDBValues() {
        Cursor CategoryListCursor = getRawEvents("Select * from " + MOTIVATOR_CATEGORY_LIST_TABLE_NAME, null);
        Category.clear();
        ODF_Thittam ODF_Thittam = new ODF_Thittam();
        ODF_Thittam.setMotivatorCategoryName("Select Category");
        Category.add(ODF_Thittam);
        if (CategoryListCursor.getCount() > 0) {
            if (CategoryListCursor.moveToFirst()) {
                do {
                    ODF_Thittam categoryList = new ODF_Thittam();
                    int categoryId = CategoryListCursor.getInt(CategoryListCursor.getColumnIndexOrThrow(AppConstant.KEY_MOTIVATOR_CATEGORY_ID));
                    String categoryName = CategoryListCursor.getString(CategoryListCursor.getColumnIndexOrThrow(AppConstant.KEY_MOTIVATOR_CATEGORY_NAME));
                    categoryList.setMotivatorCategoryId(categoryId);
                    categoryList.setMotivatorCategoryName(categoryName);
                    Category.add(categoryList);
                } while (CategoryListCursor.moveToNext());
            }
        }
        sp_category.setAdapter(new CommonAdapter(this, Category, "CategoryList"));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_register:
                validateMotivatorDetails();
                break;
//            case R.id.arrow_image:
//                scrollView.fullScroll(ScrollView.FOCUS_DOWN);
//                break;
//            case R.id.arrow_image_up:
//                scrollView.fullScroll(ScrollView.FOCUS_UP);
//                break;
            case R.id.dob_layout:
                showStartDatePickerDialog();
                break;
            case R.id.edit_image:
                getCameraPermission();
                break;
            case R.id.back_img:
                onBackPress();
                break;
            case R.id.tick:
                //fetchBranchName(motivator_ifsc_tv.getText().toString().toUpperCase());
                getBankandBranchName(motivator_ifsc_tv.getText().toString().toUpperCase());
                hide_keyboard();
                break;
        }
    }

    //The method for opening the registration page and another processes or checks for registering
    private void validateMotivatorDetails() {
        if (profile_image.getDrawable() != null) {
            if (!motivator_name.getText().toString().isEmpty()) {
                if (!"Select District".equalsIgnoreCase(District.get(sp_district.getSelectedItemPosition()).getDistrictName())) {
                    if (!"Select Block".equalsIgnoreCase(Block.get(sp_block.getSelectedItemPosition()).getBlockName())) {
                        if (!"Select Village".equalsIgnoreCase(VillageList.get(sp_village.getSelectedItemPosition()).getPvName())) {
                            if (!motivator_address.getText().toString().isEmpty()) {
                                if (!motivator_mobileNO.getText().toString().isEmpty()) {
                                    if (Utils.isValidMobile(motivator_mobileNO.getText().toString())) {
//                                        if (!motivator_email_id.getText().toString().isEmpty()) {
//                                            if (Utils.isEmailValid(motivator_email_id.getText().toString())) {
//                                        if ((motivator.isChecked()) || (other.isChecked())) {
                                        if (motivator.isChecked()) {
                                            if (!motivator_account_tv.getText().toString().isEmpty()) {
                                                if (!verify_motivator_account_tv.getText().toString().isEmpty()) {
                                                    if (motivator_account_tv.getText().toString().equalsIgnoreCase(verify_motivator_account_tv.getText().toString())) {
                                                        if (!motivator_ifsc_tv.getText().toString().isEmpty()) {
                                                            if (!motivator_bank_tv.getText().toString().isEmpty()) {
                                                                if (!motivator_branch_tv.getText().toString().isEmpty()) {
                                                                    motivatorOthersValidation();
                                                                } else {
                                                                    Utils.showAlert(RegisterScreen.this, "வங்கி கிளை பெயரைத் தேர்ந்தெடுக்கவும்!");
                                                                }
                                                            } else {
                                                                Utils.showAlert(RegisterScreen.this, "வங்கியின் பெயரைத் தேர்ந்தெடுக்கவும்!");
                                                            }
                                                        } else {
                                                            Utils.showAlert(RegisterScreen.this, "IFSC குறியீட்டை உள்ளிடவும்!");
                                                        }
                                                    } else {
                                                        Utils.showAlert(RegisterScreen.this, "வங்கி கணக்கு எண் மற்றும் சரிபார்ப்பு கணக்கு எண் ஒரே மாதிரியாக இல்லை!");
                                                    }
                                                } else {
                                                    Utils.showAlert(RegisterScreen.this, "சரிபார்க்கும் வங்கி கணக்கு எண்ணை உள்ளிடவும்!");
                                                }
                                            } else {
                                                Utils.showAlert(RegisterScreen.this, "உங்கள் வங்கி கணக்கு எண்ணை உள்ளிடவும்!");
                                            }
//                                            } else {
//                                                Utils.showAlert(this, "சரியான மின்னஞ்சல் முகவரியை உள்ளிடவும்!");
//                                            }
//                                        } else {
//                                            Utils.showAlert(this, "உங்கள் மின்னஞ்சல் முகவரியை உள்ளிடவும்!");
//                                        }
                                        } else {
                                            prefManager.setKeyAutocompleteSelectedBankID(0);
                                            prefManager.setKeyAutocompleteSelectedBranchID(0);
                                            prefManager.setKeyAutocompleteSelectedIfscCode("");
                                            motivator_account_tv.getText().clear();
                                            verify_motivator_account_tv.getText().clear();
                                            motivator_ifsc_tv.getText().clear();
                                            motivator_bank_tv.setText("");
                                            motivator_branch_tv.setText("");
                                            motivatorOthersValidation();
                                        }
                                        /*} else {
                                            Utils.showAlert(this, "விண்ணப்பதாரர் ஊக்குவிப்பவரா/மற்றவரா என்பதை தேர்வு செய்யவும்!");
                                        }*/
                                    } else {
                                        Utils.showAlert(RegisterScreen.this, "சரியான கைபேசி எண்ணை உள்ளிடவும்!");
                                    }
                                } else {
                                    Utils.showAlert(RegisterScreen.this, "உங்கள் கைபேசி எண்ணை உள்ளிடவும்!");
                                }
                            } else {
                                Utils.showAlert(RegisterScreen.this, "உங்கள் முகவரியை உள்ளிடவும்!");
                            }
                        } else {
                            Utils.showAlert(RegisterScreen.this, "உங்கள் கிராமத்தைத் தேர்ந்தெடுக்கவும்!");
                        }
                    } else {
                        Utils.showAlert(RegisterScreen.this, "உங்கள் வட்டத்தைத் தேர்ந்தெடுக்கவும்!");
                    }
                } else {
                    Utils.showAlert(RegisterScreen.this, "உங்கள் மாவட்டத்தைத் தேர்ந்தெடுக்கவும்!");
                }
            } else {
                Utils.showAlert(RegisterScreen.this, "உங்கள் பெயரை உள்ளிடவும்!");
            }
        } else {
            Utils.showAlert(RegisterScreen.this, "முதலில் சுயவிவரப் படத்தைப் பிடிக்கவும்!!");
        }
    }

    public void motivatorOthersValidation() {
        if (!motivator_dob_tv.getText().toString().isEmpty()) {

            if (!motivator_state_level_tv.getText().toString().isEmpty()) {
           /* if (!motivator_district_level_tv.getText().toString().isEmpty()) {
            if (!motivator_block_level_tv.getText().toString().isEmpty()) {
*/
                if (!"Select Category".equalsIgnoreCase(Category.get(sp_category.getSelectedItemPosition()).getMotivatorCategoryName())) {
                    if (!"Select Gender".equalsIgnoreCase(genderList.get(gender_spinner.getSelectedItemPosition()).getGenderEn())) {
                        if (!"Select Educational Qualification".equalsIgnoreCase(educationList.get(educational_qualification_spinner.getSelectedItemPosition()).getEducationName())) {
                            if ((prefManager.getSpinnerSelectedCategoryName()).equalsIgnoreCase("others")) {
                                if (!motivator_position_tv.getText().toString().isEmpty()) {
                                    if(working_area_list.size()>0){
                                        signUP();
                                    }
                                    else {
                                        Utils.showAlert(RegisterScreen.this, getResources().getString(R.string.add_your_working_area));
                                    }

                                } else {
                                    Utils.showAlert(RegisterScreen.this, "நிலையை உள்ளிடவும்!");
                                }
                            } else {
                                if(working_area_list.size()>0){
                                    signUP();
                                }
                                else {
                                    Utils.showAlert(RegisterScreen.this, "Add Your Working Area List");
                                }
                            }
                        } else {
                            Utils.showAlert(RegisterScreen.this, "கல்வித் தகுதியைத் தேர்ந்தெடுக்கவும்!");
                        }
                    } else {
                        Utils.showAlert(RegisterScreen.this, "பாலினத்தைத் தேர்ந்தெடுக்கவும்!");
                    }
                } else {
                    Utils.showAlert(RegisterScreen.this, "வகையைத் தேர்ந்தெடுக்கவும்!");
                }
            /*} else {
                Utils.showAlert(this, "கலந்துகொண்ட தொகுதி-நிலை பயிற்சியின் எண்ணிக்கையை உள்ளிடவும்!");
            }
            } else {
                Utils.showAlert(this, "கலந்துகொண்ட மாவட்ட அளவிலான பயிற்சியின் எண்ணிக்கையை உள்ளிடவும்!");
            }*/
            } else {
                Utils.showAlert(RegisterScreen.this, "கலந்துகொண்ட மாநில அளவிலான பயிற்சியின் எண்ணிக்கையை உள்ளிடவும்!");
            }

        } else {
            Utils.showAlert(RegisterScreen.this, "பிறந்த தேதியைத் தேர்ந்தெடுக்கவும்!");
        }
    }

    public class AsteriskPasswordTransformationMethod extends PasswordTransformationMethod {
        @Override
        public CharSequence getTransformation(CharSequence source, View view) {
            return new PasswordCharSequence(source);
        }

        private class PasswordCharSequence implements CharSequence {
            private CharSequence mSource;

            public PasswordCharSequence(CharSequence source) {
                mSource = source; // Store char sequence
            }

            public char charAt(int index) {
                return '*'; // This is the important part
            }

            public int length() {
                return mSource.length(); // Return default
            }

            public CharSequence subSequence(int start, int end) {
                return mSource.subSequence(start, end); // Return default
            }
        }
    }


    public Cursor getRawEvents(String sql, String string) {
        Cursor cursor = db.rawQuery(sql, null);
        return cursor;
    }


    public void showArrowImage() {
        arrowImage.setVisibility(View.VISIBLE);
        animation = new AlphaAnimation((float) 0.5, 0); // Change alpha from fully visible to invisible
        animation.setDuration(500); // duration - half a second
        animation.setInterpolator(new LinearInterpolator()); // do not alter
        animation.setRepeatCount(Animation.INFINITE); // Repeat animation
        animation.setRepeatMode(Animation.REVERSE); // Reverse animation at the
        arrowImage.startAnimation(animation);
    }

    public void showUpArrowImage() {
        arrowImageUp.setVisibility(View.VISIBLE);
        animation = new AlphaAnimation((float) 0.5, 0); // Change alpha from fully visible to invisible
        animation.setDuration(500); // duration - half a second
        animation.setInterpolator(new LinearInterpolator()); // do not alter
        animation.setRepeatCount(Animation.INFINITE); // Repeat animation
        animation.setRepeatMode(Animation.REVERSE); // Reverse animation at the
        arrowImageUp.startAnimation(animation);
    }


    @Override
    public void OnError(VolleyError volleyError) {
        volleyError.printStackTrace();
       // Utils.showAlert(RegisterScreen.this, "Login Again");
    }

    public void showStartDatePickerDialog() {
        DialogFragment newFragment = new fromDatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");

    }

    public static class fromDatePickerFragment extends DialogFragment implements
            DatePickerDialog.OnDateSetListener {
        static Calendar cldr = Calendar.getInstance();

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker

            int day = cldr.get(Calendar.DAY_OF_MONTH);
            int month = cldr.get(Calendar.MONTH);
            int year = cldr.get(Calendar.YEAR);
            DatePickerDialog datePickerDialog;
            datePickerDialog = new DatePickerDialog(getActivity(), this, year,
                    month, day);
            cldr.set(year, month, day);
            datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
            return datePickerDialog;
        }

        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            // Do something with the date chosen by the user
            motivator_dob_tv.setText(dateFormet(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year));
            String start_date = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
            cldr.set(Calendar.YEAR, year);
            cldr.set(Calendar.MONTH, (monthOfYear));
            cldr.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            Log.d("startdate", "" + dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
        }

    }

    private void getCameraPermission() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (CameraUtils.checkPermissions(RegisterScreen.this)) {
                captureImage();
            } else {
                requestCameraPermission(MEDIA_TYPE_IMAGE);
            }
        } else {
            captureImage();
        }


    }

    private void captureImage() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(cameraIntent, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);
        }
        else {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

            File file = null;
            PackageManager it = getPackageManager();
            if (intent.resolveActivity(it) != null) {
                file = CameraUtils.getOutputMediaFile(MEDIA_TYPE_IMAGE);
            }

//        File file = CameraUtils.getOutputMediaFile(MEDIA_TYPE_IMAGE);
            if (file != null) {
                imageStoragePath = file.getAbsolutePath();
            }

            Uri fileUri = CameraUtils.getOutputMediaFileUri(this, file);

            intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);

            // start the image capture Intent
            startActivityForResult(intent, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);
        }
    }


    private void requestCameraPermission(final int type) {
        Dexter.withActivity(this)
                .withPermissions(Manifest.permission.CAMERA,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        if (report.areAllPermissionsGranted()) {

                            if (type == MEDIA_TYPE_IMAGE) {
                                // capture picture
                                captureImage();
                            } else {
//                                captureVideo();
                            }

                        } else if (report.isAnyPermissionPermanentlyDenied()) {
                            showPermissionsAlert();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).check();
    }

    private void showPermissionsAlert() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Permissions required!")
                .setMessage("Camera needs few permissions to work properly. Grant them in settings.")
                .setPositiveButton("GOTO SETTINGS", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        CameraUtils.openSettings(RegisterScreen.this);
                    }
                })
                .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).show();
    }

    public void previewCapturedImage() {
        try {
            // hide video preview
            Bitmap bitmap = CameraUtils.optimizeBitmap(BITMAP_SAMPLE_SIZE, imageStoragePath);
            profile_image_preview.setVisibility(View.GONE);
            ExifInterface ei = null;
            try {
                ei = new ExifInterface(imageStoragePath);
            } catch (IOException e) {
                e.printStackTrace();
            }
            int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_UNDEFINED);

            Bitmap rotatedBitmap = null;
            switch (orientation) {

                case ExifInterface.ORIENTATION_ROTATE_90:
                    rotatedBitmap = rotateImage(bitmap, 90);
                    break;

                case ExifInterface.ORIENTATION_ROTATE_180:
                    rotatedBitmap = rotateImage(bitmap, 180);
                    break;

                case ExifInterface.ORIENTATION_ROTATE_270:
                    rotatedBitmap = rotateImage(bitmap, 270);
                    break;

                case ExifInterface.ORIENTATION_NORMAL:
                default:
                    rotatedBitmap = bitmap;
            }
            profile_image.setImageBitmap(rotatedBitmap);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    public static Bitmap rotateImage(Bitmap source, float angle) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(),
                matrix, true);
    }

    @SuppressLint("MissingSuperCall")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // if the result is capturing Image
        if (requestCode == CAMERA_CAPTURE_IMAGE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    Bitmap photo = (Bitmap) data.getExtras().get("data");
                    profile_image_preview.setVisibility(View.GONE);
                    profile_image.setVisibility(View.VISIBLE);
                    profile_image.setImageBitmap(photo);
                }
                else {
                    // Refreshing the gallery
                    CameraUtils.refreshGallery(getApplicationContext(), imageStoragePath);

                    // successfully captured the image
                    // display it in image view
                    previewCapturedImage();
                }
            } else if (resultCode == RESULT_CANCELED) {
                // user cancelled Image capture
                Toast.makeText(getApplicationContext(),
                        "User cancelled image capture", Toast.LENGTH_SHORT)
                        .show();
            } else {
                // failed to capture image
                Toast.makeText(getApplicationContext(),
                        "Sorry! Failed to capture image", Toast.LENGTH_SHORT)
                        .show();
            }
        } else if (requestCode == CAMERA_CAPTURE_VIDEO_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                // Refreshing the gallery
                CameraUtils.refreshGallery(getApplicationContext(), imageStoragePath);

                // video successfully recorded
                // preview the recorded video
//                previewVideo();
            } else if (resultCode == RESULT_CANCELED) {
                // user cancelled recording
                Toast.makeText(getApplicationContext(),
                        "User cancelled video recording", Toast.LENGTH_SHORT)
                        .show();
            } else {
                // failed to record video
                Toast.makeText(getApplicationContext(),
                        "Sorry! Failed to record video", Toast.LENGTH_SHORT)
                        .show();
            }
        }
    }

    public void signUP() {
        try {
            new ApiService(this).makeJSONObjectRequest("Register", Api.Method.POST, UrlGenerator.getRegistrationUrl(), dataTobeSavedJsonParams(), "not cache", this);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public JSONObject dataTobeSavedJsonParams() throws JSONException {

        JSONArray willing_to_serve_villages_list = new JSONArray();
        byte[] imageInByte = new byte[0];
        String image_str = "";
        try {
            Bitmap bitmap = ((BitmapDrawable) profile_image.getDrawable()).getBitmap();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, baos);
            imageInByte = baos.toByteArray();
            image_str = Base64.encodeToString(imageInByte, Base64.DEFAULT);

        } catch (Exception e) {
            e.printStackTrace();
        }

        JSONObject dataSet = new JSONObject();
        dataSet.put(AppConstant.KEY_SERVICE_ID, AppConstant.KEY_REGISTER_SIGNUP);
        dataSet.put(AppConstant.KEY_MOTIVATOR_NAME, motivator_name.getText().toString());
        dataSet.put(AppConstant.KEY_REGISTER_DOB, motivator_dob_tv.getText().toString());
        dataSet.put(AppConstant.KEY_MOTIVATOR_OTHERS, 1/*isMotivatorOthers*/);
        dataSet.put(AppConstant.KEY_REGISTER_MOBILE, motivator_mobileNO.getText().toString());
        dataSet.put(AppConstant.KEY_REGISTER_EMAIL, motivator_email_id.getText().toString());
        dataSet.put(AppConstant.KEY_REGISTER_ADDRESS, motivator_address.getText().toString());
        dataSet.put(AppConstant.DISTRICT_CODE, prefManager.getDistrictCode());
        dataSet.put(AppConstant.BLOCK_CODE, prefManager.getKeySpinnerSelectedBlockcode());
        dataSet.put(AppConstant.PV_CODE, prefManager.getKeySpinnerSelectedPVcode());
        dataSet.put(AppConstant.KEY_MOTIVATOR_PHOTO, image_str.trim());
        dataSet.put(AppConstant.KEY_REGISTER_ACC_NO, verify_motivator_account_tv.getText().toString());
        dataSet.put(AppConstant.BANK_ID, prefManager.getKeyAutocompleteSelectedBankID());
        dataSet.put(AppConstant.BRANCH_ID, prefManager.getKeyAutocompleteSelectedBranchID());
        dataSet.put(AppConstant.KEY_REGISTER_IFSC_CODE, prefManager.getKeyAutocompleteSelectedIfscCode());
        dataSet.put(AppConstant.KEY_MOTIVATOR_NO_OF_STATE_LEVEL_TRAINEE, motivator_state_level_tv.getText().toString());
        dataSet.put(AppConstant.KEY_GENDER_CODE, selectedGenderId);
        dataSet.put(AppConstant.KEY_EDUCATION_CODE, selectedEducationId);
        if (!motivator_district_level_tv.getText().toString().equals("")) {
            dataSet.put(AppConstant.KEY_MOTIVATOR_NO_OF_DISTRICT_LEVEL_TRAINEE, motivator_district_level_tv.getText().toString());
        } else {
            dataSet.put(AppConstant.KEY_MOTIVATOR_NO_OF_DISTRICT_LEVEL_TRAINEE, "0");
        }
        if (!motivator_block_level_tv.getText().toString().equals("")) {
            dataSet.put(AppConstant.KEY_MOTIVATOR_NO_OF_BLOCK_LEVEL_TRAINEE, motivator_block_level_tv.getText().toString());
        } else {
            dataSet.put(AppConstant.KEY_MOTIVATOR_NO_OF_BLOCK_LEVEL_TRAINEE, "0");
        }

        if ((prefManager.getSpinnerSelectedCategoryName()).equalsIgnoreCase("others")) {
            dataSet.put(AppConstant.KEY_REGISTER_CATEGORY_OTHERS, prefManager.getSpinnerSelectedCategoryId());
            dataSet.put(AppConstant.KEY_REGISTER_MOTIVATOR_POSITION, motivator_position_tv.getText().toString());
        } else {
            dataSet.put(AppConstant.KEY_REGISTER_CATEGORY, prefManager.getSpinnerSelectedCategoryId());
        }
        for(int i =0 ;i<working_area_list.size();i++){
            JSONObject newJson = new JSONObject();
            newJson.put("dcode",working_area_list.get(i).getDistictCode());
            newJson.put("bcode",working_area_list.get(i).getBlockCode());
            newJson.put("pvcode",working_area_list.get(i).getPvCode());
            willing_to_serve_villages_list.put(newJson);
        }
        dataSet.put("willing_to_serve_villages_list", willing_to_serve_villages_list);
        Log.d("RegisterDataSet", "" + dataSet);
        String authKey = dataSet.toString();
        int maxLogSize = 2000;
        for (int i = 0; i <= authKey.length() / maxLogSize; i++) {
            int start = i * maxLogSize;
            int end = (i + 1) * maxLogSize;
            end = end > authKey.length() ? authKey.length() : end;
            Log.v("to_send+_plain", authKey.substring(start, end));
        }
        return dataSet;
    }

    @Override
    public void OnMyResponse(ServerResponse serverResponse) {
        try {
            JSONObject responseObj = serverResponse.getJsonResponse();
            String urlType = serverResponse.getApi();
            String status = responseObj.getString(AppConstant.KEY_STATUS);
            String response = responseObj.getString(AppConstant.KEY_RESPONSE);
            if ("Register".equals(urlType) && responseObj != null) {
                if (status.equalsIgnoreCase("OK") && response.equalsIgnoreCase("OK")) {
                    //JSONObject jsonObject = responseObj.getJSONObject(AppConstant.JSON_DATA);
                    String Motivatorid = responseObj.getString(AppConstant.KEY_REGISTER_MOTIVATOR_ID);
                    Log.d("motivatorid", "" + Motivatorid);
                    Utils.showAlert(RegisterScreen.this, "நீங்கள் வெற்றிகரமாக பதிவு செய்யப்பட்டுள்ளீர்கள்!");
                    Runnable runnable = new Runnable() {
                        @Override
                        public void run() {
                            finish();
                        }
                    };
                    handler.postDelayed(runnable, 1500);

                }
                else if (status.equalsIgnoreCase("OK") && response.equalsIgnoreCase("NO_RECORD")) {
                    Utils.showAlert(RegisterScreen.this, responseObj.getString("MESSAGE"));
                } else if (status.equalsIgnoreCase("OK") && response.equalsIgnoreCase("FAIL")) {
                    Utils.showAlert(RegisterScreen.this, responseObj.getString("MESSAGE"));
                }
                else {
                    Utils.showAlert(RegisterScreen.this, responseObj.getString("MESSAGE"));
                }
            }
            if ("VillageList".equals(urlType) && responseObj != null) {
                status = responseObj.getString(AppConstant.KEY_STATUS);
                response = responseObj.getString(AppConstant.KEY_RESPONSE);
                if (status.equalsIgnoreCase("OK") && response.equalsIgnoreCase("OK")) {
                    JSONArray jsonArray = responseObj.getJSONArray(AppConstant.JSON_DATA);
                    if(load_alert_village.equals("")) {
                        loadVillageSpinner(jsonArray);
                    }
                    else {
                        alertLoadVillageSpinner(jsonArray);
                    }
                } else if (status.equalsIgnoreCase("OK") && response.equalsIgnoreCase("NO_RECORD")) {
                    Log.d("Record", responseObj.getString(AppConstant.KEY_MESSAGE));
                }
                Log.d("VillageList", "" + responseObj.getJSONArray(AppConstant.JSON_DATA));
            }
            if ("BankandBranchName".equals(urlType) && responseObj != null) {
                status = responseObj.getString(AppConstant.KEY_STATUS);
                response = responseObj.getString(AppConstant.KEY_RESPONSE);
                if (status.equalsIgnoreCase("OK") && response.equalsIgnoreCase("OK")) {
                    JSONObject jsonObject = responseObj.getJSONObject(AppConstant.JSON_DATA);
                    String bank_id;
                    String branch_id;
                    String branch;
                    String bank_name;
                    String ifsc;
                    bank_id = jsonObject.getString("bank_id");
                    branch_id = jsonObject.getString("branch_id");
                    branch = jsonObject.getString("branch");
                    bank_name = jsonObject.getString("bank_name");
                    ifsc = jsonObject.getString("ifsc");
                    prefManager.setKeyAutocompleteSelectedBranchID(Integer.valueOf(branch_id));
                    prefManager.setKeyAutocompleteSelectedIfscCode(ifsc);
                    prefManager.setKeyAutocompleteSelectedBankID(Integer.parseInt(bank_id));
                    motivator_bank_tv.setText(bank_name);
                    motivator_branch_tv.setText(branch);


                } else if (status.equalsIgnoreCase("OK") && response.equalsIgnoreCase("NO_RECORD")) {
                    Log.d("Record", responseObj.getString(AppConstant.KEY_MESSAGE));
                    //Utils.showAlert(RegisterScreen.this,responseObj.getString(AppConstant.KEY_MESSAGE));
                    Utils.showAlert(RegisterScreen.this, "Enter the valid IFSC!");
                    motivator_bank_tv.setText("");
                    motivator_branch_tv.setText("");

                }
                Log.d("BankBranchList", "" + responseObj.getJSONObject(AppConstant.JSON_DATA));
            }
            if ("serve_villages_limit".equals(urlType) && responseObj != null) {
                status = responseObj.getString(AppConstant.KEY_STATUS);
                response = responseObj.getString(AppConstant.KEY_RESPONSE);
                if (status.equalsIgnoreCase("OK") && response.equalsIgnoreCase("OK")) {
                    serve_villages_limit_count = Integer.parseInt(responseObj.getString("COUNT"));

                }
                Log.d("serve_villages_limit", "" + responseObj);
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void onBackPress() {
        super.onBackPressed();
        setResult(Activity.RESULT_CANCELED);
        overridePendingTransition(R.anim.slide_enter, R.anim.slide_exit);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        setResult(Activity.RESULT_CANCELED);
        overridePendingTransition(R.anim.slide_enter, R.anim.slide_exit);
    }

    public void getVillageList() {
        try {
            new ApiService(this).makeJSONObjectRequest("VillageList", Api.Method.POST, UrlGenerator.getOpenUrl(), villageListJsonParams(), "not cache", this);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public void getCountLimit() {
        try {
            new ApiService(this).makeJSONObjectRequest("serve_villages_limit", Api.Method.POST, UrlGenerator.getRegistrationUrl(), getCountOfWillingWork(), "not cache", this);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void getBankandBranchName(String ifsc_code) {
        try {
            new ApiService(this).makeJSONObjectRequest("BankandBranchName", Api.Method.POST, UrlGenerator.getOpenUrl(), bankbranchNameListJsonParams(ifsc_code), "not cache", this);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public JSONObject bankbranchNameListJsonParams(String ifsc_code) throws JSONException {
        JSONObject dataSet = new JSONObject();
        dataSet.put(AppConstant.KEY_SERVICE_ID, AppConstant.KEY_BRANCH_DETAIL_BY_IFSC_CODE);
        dataSet.put(AppConstant.IFSC_CODE, ifsc_code);
        Log.d("object", "" + dataSet);
        return dataSet;
    }

    public void getPermission() {
        if (ActivityCompat.checkSelfPermission(RegisterScreen.this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(RegisterScreen.this, new String[]{Manifest.permission.READ_PHONE_STATE}, REQUEST_CODE);

        }
        else {
            getIMEInumber();
        }
    }

    public void getIMEInumber() {
        try {
            TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return ;
            }
            IMEINumber = telephonyManager.getDeviceId();
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //Toast.makeText(this, "Permission granted.", Toast.LENGTH_SHORT).show();
                } else {
                    //Toast.makeText(this, "Permission denied.", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
    private static String dateFormet(String dateStr) {
        String myFormat="";
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        try {
            Date date1 = format.parse(dateStr);
            System.out.println(date1);
            String dateTime = format.format(date1);
            System.out.println("Current Date Time : " + dateTime);
            myFormat = dateTime; //In which you need put here
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return myFormat;
    }

    public void addWorkingArea(){

        try {
            final Dialog dialog = new Dialog(this,R.style.AppTheme);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.add_working_arae_dialog_box);
            dialog.setCanceledOnTouchOutside(false);
            dialog.setCancelable(false);
            dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
            dialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
            WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
            lp.dimAmount = 0.7f;
            dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
            dialog.show();

            ImageView close_icon = dialog.findViewById(R.id.close_icon);
            Button btn_save = dialog.findViewById(R.id.btn_save);
            alert_district = dialog.findViewById(R.id.district);
            alert_block = dialog.findViewById(R.id.block);
            alert_village = dialog.findViewById(R.id.village);
            alertDialogDistrictList();
            alert_district.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    if (position == 0) {
                        alert_block.setClickable(false);
                        alert_block.setVisibility(View.GONE);
                        alert_dcode ="";
                        alert_dname ="";
                    } else {
                        alert_block.setClickable(true);
                        alert_block.setVisibility(View.VISIBLE);
                        alert_dcode = AlertDistrict.get(position).getDistictCode();
                        alert_dname = AlertDistrict.get(position).getDistrictName();
                        alertDialogBlockFilterSpinner(alert_dcode);
                    }


                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
            alert_block.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    if (position == 0) {
                        alert_village.setClickable(false);
                        alert_village.setVisibility(View.GONE);
                        alert_bcode="";
                        alert_bname="";
                    } else {
                        alert_village.setClickable(true);
                        alert_village.setVisibility(View.VISIBLE);
                        alert_bcode = AlertBlock.get(position).getBlockCode();
                        alert_bname = AlertBlock.get(position).getBlockName();
                        if (Utils.isOnline()) {
                            load_alert_village="Yes";
                            getVillageList();
                        } else {
                            Utils.showAlert(RegisterScreen.this, "Turn on Mobile Data!");
                        }
                    }


                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
            alert_village.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    if(position>0){
                        alert_pv_code = AlertVillage.get(position).getPvCode();
                        alert_pv_name = AlertVillage.get(position).getPvName();
                    }
                    else {
                        alert_pv_code = "";
                        alert_pv_name="";
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
            close_icon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
            btn_save.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!alert_dcode.equals("")){
                        if(!alert_bcode.equals("")){
                            if(!alert_pv_code.equals("")){
                                ODF_Thittam odf_thittam = new ODF_Thittam();
                                odf_thittam.setDistictCode(alert_dcode);
                                odf_thittam.setDistrictName(alert_dname);
                                odf_thittam.setBlockCode(alert_bcode);
                                odf_thittam.setBlockName(alert_bname);
                                odf_thittam.setPvCode(alert_pv_code);
                                odf_thittam.setPvName(alert_pv_name);
                                working_area_list.add(odf_thittam);
                                if(working_area_list.size()>0){
                                    working_area_recycler.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                                    workingAreaListAdapter = new WorkingAreaListAdapter(RegisterScreen.this,working_area_list);
                                    working_area_recycler.setAdapter(workingAreaListAdapter);
                                }
                                else {
                                    working_area_recycler.setAdapter(null);
                                }
                                dialog.dismiss();
                            }
                            else {
                                Utils.showAlert(RegisterScreen.this,getResources().getString(R.string.village));
                            }
                        }
                        else {
                            Utils.showAlert(RegisterScreen.this,getResources().getString(R.string.block));
                        }

                    }
                    else {
                        Utils.showAlert(RegisterScreen.this,getResources().getString(R.string.district));
                    }

                }
            });





        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    public void alertDialogDistrictList() {
        Cursor DistrictList = getRawEvents("Select * from " + DISTRICT_TABLE_NAME + " WHERE dcode != 29 order by dname asc", null);
        AlertDistrict = new ArrayList<>();
        AlertDistrict.clear();
        ODF_Thittam ODF_Thittam = new ODF_Thittam();
        ODF_Thittam.setDistrictName("Select District");
        AlertDistrict.add(ODF_Thittam);
        if (DistrictList.getCount() > 0) {
            if (DistrictList.moveToFirst()) {
                do {
                    ODF_Thittam districtList = new ODF_Thittam();
                    String districtCode = DistrictList.getString(DistrictList.getColumnIndexOrThrow(AppConstant.DISTRICT_CODE));
                    String districtName = DistrictList.getString(DistrictList.getColumnIndexOrThrow(AppConstant.DISTRICT_NAME));
                    districtList.setDistictCode(districtCode);
                    districtList.setDistrictName(districtName);
                    AlertDistrict.add(districtList);
                } while (DistrictList.moveToNext());
            }
        }
        alert_district.setAdapter(new CommonAdapter(this, AlertDistrict, "DistrictList"));

    }
    public void alertDialogBlockFilterSpinner(String filterBlock) {

        String blocksql = "SELECT * FROM " + BLOCK_TABLE_NAME + " WHERE dcode = " + filterBlock + " order by bname asc";
        Log.d("blocksql", blocksql);
        Cursor BlockList = getRawEvents(blocksql, null);
        AlertBlock = new ArrayList<>();
        AlertBlock.clear();
        ODF_Thittam blockListValue = new ODF_Thittam();
        blockListValue.setBlockName("Select Block");
        AlertBlock.add(blockListValue);
        if (BlockList.getCount() > 0) {
            if (BlockList.moveToFirst()) {
                do {
                    ODF_Thittam blockList = new ODF_Thittam();
                    String districtCode = BlockList.getString(BlockList.getColumnIndexOrThrow(AppConstant.DISTRICT_CODE));
                    String blockCode = BlockList.getString(BlockList.getColumnIndexOrThrow(AppConstant.BLOCK_CODE));
                    String blockName = BlockList.getString(BlockList.getColumnIndexOrThrow(AppConstant.BLOCK_NAME));
                    blockList.setDistictCode(districtCode);
                    blockList.setBlockCode(blockCode);
                    blockList.setBlockName(blockName);
                    AlertBlock.add(blockList);
                } while (BlockList.moveToNext());
            }

        }
        alert_block.setAdapter(new CommonAdapter(this, AlertBlock, "BlockList"));

    }
    private void alertLoadVillageSpinner(JSONArray jsonArray) {
        try {
            AlertVillage = new ArrayList<>();
            ODF_Thittam villageListValue1 = new ODF_Thittam();
            villageListValue1.setPvName("Select Village");
            AlertVillage.add(villageListValue1);
            for (int i = 0; i < jsonArray.length(); i++) {
                ODF_Thittam villageListValue = new ODF_Thittam();
                try {
                    String jsondcode2 = jsonArray.getJSONObject(i).getString(AppConstant.DISTRICT_CODE);
                    String jsonbcode2 = jsonArray.getJSONObject(i).getString(AppConstant.BLOCK_CODE);
                    String jsonpvcode2 = jsonArray.getJSONObject(i).getString(AppConstant.PV_CODE);
                    if(!(jsondcode2.equals(dcode)&&jsonbcode2.equals(bcode)&&jsonpvcode2.equals(pv_code))){
                        if(working_area_list.size()>0){
                            for(int j=0;j<working_area_list.size();j++){
                                String jsondcode = jsonArray.getJSONObject(i).getString(AppConstant.DISTRICT_CODE);
                                String jsonbcode = jsonArray.getJSONObject(i).getString(AppConstant.BLOCK_CODE);
                                String jsonpvcode = jsonArray.getJSONObject(i).getString(AppConstant.PV_CODE);
                                String jsonpvname = jsonArray.getJSONObject(i).getString(AppConstant.PV_NAME);

                                String wdcode = working_area_list.get(j).getDistictCode();
                                String wbcode = working_area_list.get(j).getBlockCode();
                                String wpvcode = working_area_list.get(j).getPvCode();
                                if(!(jsondcode.equals(wdcode)&&jsonbcode.equals(wbcode)&&jsonpvcode.equals(wpvcode))){

                                    villageListValue.setDistictCode(jsondcode);
                                    villageListValue.setBlockCode(jsonbcode);
                                    villageListValue.setPvCode(jsonpvcode);
                                    villageListValue.setPvName(jsonpvname);
                                    AlertVillage.add(villageListValue);

                                }


                            }
                        }
                        else {
                            String jsondcode = jsonArray.getJSONObject(i).getString(AppConstant.DISTRICT_CODE);
                            String jsonbcode = jsonArray.getJSONObject(i).getString(AppConstant.BLOCK_CODE);
                            String jsonpvcode = jsonArray.getJSONObject(i).getString(AppConstant.PV_CODE);
                            String jsonpvname = jsonArray.getJSONObject(i).getString(AppConstant.PV_NAME);

                            villageListValue.setDistictCode(jsondcode);
                            villageListValue.setBlockCode(jsonbcode);
                            villageListValue.setPvCode(jsonpvcode);
                            villageListValue.setPvName(jsonpvname);
                            AlertVillage.add(villageListValue);
                        }
                    }




                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            alert_village.setAdapter(new CommonAdapter(this, AlertVillage, "VillageList"));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}