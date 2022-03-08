package com.nic.ODF_Thittam.activity;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.widget.SearchView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.VolleyError;
import com.cooltechworks.views.shimmer.ShimmerRecyclerView;
import com.nic.ODF_Thittam.R;
import com.nic.ODF_Thittam.adapter.PendingScreenAdapter;
import com.nic.ODF_Thittam.api.Api;
import com.nic.ODF_Thittam.api.ApiService;
import com.nic.ODF_Thittam.api.ServerResponse;
import com.nic.ODF_Thittam.constant.AppConstant;
import com.nic.ODF_Thittam.dataBase.DBHelper;
import com.nic.ODF_Thittam.dataBase.dbData;
import com.nic.ODF_Thittam.databinding.PendingScreenBinding;
import com.nic.ODF_Thittam.model.ODF_Thittam;
import com.nic.ODF_Thittam.session.PrefManager;
import com.nic.ODF_Thittam.support.ProgressHUD;
import com.nic.ODF_Thittam.utils.UrlGenerator;
import com.nic.ODF_Thittam.utils.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class PendingScreen extends AppCompatActivity implements Api.ServerResponseListener {
    private PendingScreenBinding pendingScreenBinding;
    private ShimmerRecyclerView recyclerView;
    private PrefManager prefManager;
    private SQLiteDatabase db;
    public static DBHelper dbHelper;
    public dbData dbData = new dbData(this);
    ArrayList<ODF_Thittam> pendingList = new ArrayList<>();
    private PendingScreenAdapter pendingScreenAdapter;
    private SearchView searchView;
    JSONObject dataset = new JSONObject();
    private ProgressHUD progressHUD;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pendingScreenBinding = DataBindingUtil.setContentView(this, R.layout.pending_screen);
        pendingScreenBinding.setActivity(this);
        setSupportActionBar(pendingScreenBinding.toolbar);
        try {
            dbHelper = new DBHelper(this);
            db = dbHelper.getWritableDatabase();
        } catch (Exception e) {
            e.printStackTrace();
        }
        prefManager = new PrefManager(this);

        pendingList = new ArrayList<>();
        pendingScreenAdapter = new PendingScreenAdapter(PendingScreen.this,pendingList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView = pendingScreenBinding.pendingListRecycler;
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());


        new fetchpendingtask().execute();

    }



    public class fetchpendingtask extends AsyncTask<JSONObject, Void,
            ArrayList<ODF_Thittam>> {
        @Override
        protected ArrayList<ODF_Thittam> doInBackground(JSONObject... params) {
            dbData.open();
            pendingList = new ArrayList<>();
            pendingList = dbData.getSavedWorkImage("","","","","");
            Log.d("PENDING_COUNT", String.valueOf(pendingList.size()));
            return pendingList;
        }

        @Override
        protected void onPostExecute(ArrayList<ODF_Thittam> pendingList) {
            super.onPostExecute(pendingList);
            pendingScreenAdapter = new PendingScreenAdapter(PendingScreen.this,
                    pendingList);
            recyclerView.setAdapter(pendingScreenAdapter);
            recyclerView.showShimmerAdapter();
            recyclerView.postDelayed(new Runnable() {
                @Override
                public void run() {
                    loadCards();
                }
            }, 1000);
        }
    }

    private void loadCards() {

        recyclerView.hideShimmerAdapter();

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

// Associate searchable configuration with the SearchView
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.action_search)
                .getActionView();
        searchView.setSearchableInfo(searchManager
                .getSearchableInfo(getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);

// listening to search query text change
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
// filter recycler view when query submitted
                pendingScreenAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
// filter recycler view when text is changed
                pendingScreenAdapter.getFilter().filter(query);
                return false;
            }
        });
        return true;
    }

    public class toUploadTask extends AsyncTask<ODF_Thittam, Void,
            JSONObject> {
        @Override
        protected JSONObject doInBackground(ODF_Thittam... realTimeValue) {
            dbData.open();
            JSONArray track_data = new JSONArray();
            JSONArray image_data = new JSONArray();
            ArrayList<ODF_Thittam> assets = dbData.getSavedWorkImage("upload",realTimeValue[0].getDistictCode(),realTimeValue[0].getBlockCode(),realTimeValue[0].getPvCode(),String.valueOf(realTimeValue[0].getWorkId()));

            if (assets.size() > 0) {
                for (int i = 0; i < 1; i++) {
                    JSONObject jsonObject = new JSONObject();
                    try {
                        String work_id = String.valueOf(assets.get(i).getWorkId());

                        jsonObject.put(AppConstant.WORK_ID,work_id);
                        jsonObject.put(AppConstant.WORK_GROUP_ID,assets.get(i).getWorkGroupID());
                        jsonObject.put(AppConstant.WORK_TYPE_ID,assets.get(i).getWorkTypeID());
                        jsonObject.put(AppConstant.DISTRICT_CODE,assets.get(i).getDistictCode());
                        jsonObject.put(AppConstant.BLOCK_CODE,assets.get(i).getBlockCode());
                        jsonObject.put(AppConstant.PV_CODE,assets.get(i).getPvCode());
                        jsonObject.put(AppConstant.TYPE_OF_WORK,assets.get(i).getTypeOfWork());
                        if(assets.get(i).getTypeOfWork().equalsIgnoreCase(AppConstant.ADDITIONAL_WORK)){
                            String cd_work_no = String.valueOf(assets.get(i).getCdWorkNo());
                            jsonObject.put(AppConstant.CD_WORK_NO,cd_work_no);
                            jsonObject.put(AppConstant.WORK_TYPE_FLAG_LE,assets.get(i).getWorkTypeFlagLe());
                            prefManager.setTypeOfWork(AppConstant.ADDITIONAL_WORK);
                            prefManager.setDeleteCdWorkNo(cd_work_no);
                            prefManager.setDeleteCdWorkTypeFlag(assets.get(i).getWorkTypeFlagLe());
                        }else {
                            prefManager.setTypeOfWork(AppConstant.MAIN_WORK);
                        }
                        jsonObject.put(AppConstant.WORK_STAGE_CODE,assets.get(i).getWorkStageCode());
                        for (int j = 0; j < assets.size(); j++) {
                            JSONObject jsonObject1 = new JSONObject();
                            jsonObject1.put("photo_captured_latitude",assets.get(j).getLatitude());
                            jsonObject1.put("photo_captured_longitude",assets.get(j).getLongitude());
                            jsonObject1.put(AppConstant.KEY_CREATED_DATE,assets.get(j).getCreatedDate());

                            Bitmap bitmap = assets.get(i).getImage();
                            ByteArrayOutputStream baos = new ByteArrayOutputStream();
                            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, baos);
                            byte[] imageInByte = baos.toByteArray();
                            String image_str = Base64.encodeToString(imageInByte, Base64.DEFAULT);

                            jsonObject1.put(AppConstant.KEY_IMAGES,image_str);
                            jsonObject1.put(AppConstant.KEY_IMAGE_REMARK,assets.get(j).getImageRemark());
                            image_data.put(jsonObject1);
                        }

                        jsonObject.put("images",image_data);


                        track_data.put(jsonObject);
                        for(int k=0;k<track_data.length();k++){
                            for(int l=k+1;l<track_data.length();l++){
                                if (track_data.get(k) == track_data.get(l)) {
                                    track_data.remove(l);
                                    l--;
                                }
                            }
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                dataset = new JSONObject();

                try {
                    dataset.put(AppConstant.KEY_SERVICE_ID,"work_phy_stage_save");
                    dataset.put(AppConstant.KEY_TRACK_DATA,track_data);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            return dataset;
        }

        @Override
        protected void onPostExecute(JSONObject dataset) {
            super.onPostExecute(dataset);
            syncData();
        }
    }

    public void syncData() {
        try {
            new ApiService(this).makeJSONObjectRequest("saveImage", Api.Method.POST, UrlGenerator.getWorkListUrl(), saveListJsonParams(), "not cache", this);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public JSONObject saveListJsonParams() throws JSONException {
        String authKey = Utils.encrypt(prefManager.getUserPassKey(), getResources().getString(R.string.init_vector), dataset.toString());
        JSONObject dataSet = new JSONObject();
        dataSet.put(AppConstant.KEY_USER_NAME, prefManager.getUserName());
        dataSet.put(AppConstant.DATA_CONTENT, authKey);
        Log.d("saveImage", "" + authKey);
        return dataSet;
    }

    @Override
    public void OnMyResponse(ServerResponse serverResponse) {
        try {
            String urlType = serverResponse.getApi();
            JSONObject responseObj = serverResponse.getJsonResponse();

            if ("saveImage".equals(urlType) && responseObj != null) {
                String key = responseObj.getString(AppConstant.ENCODE_DATA);
                String responseDecryptedBlockKey = Utils.decrypt(prefManager.getUserPassKey(), key);
                JSONObject jsonObject = new JSONObject(responseDecryptedBlockKey);
                if (jsonObject.getString("STATUS").equalsIgnoreCase("OK") && jsonObject.getString("RESPONSE").equalsIgnoreCase("OK")) {
                    Utils.showAlert(this, "Your Data is Synchronized to the server!");
                    String type_of_work = prefManager.getTypeOfWork();
                    dbData.open();
                    if(type_of_work.equalsIgnoreCase(AppConstant.MAIN_WORK))
                    {
                        dbData.deleteWorkListTable();
                        db.delete(DBHelper.SAVE_IMAGE, "dcode = ? and bcode = ? and pvcode = ? and work_id = ? and  type_of_work = ?", new String[]{prefManager.getDistrictCode(), prefManager.getBlockCode(), prefManager.getPvCode(), prefManager.getDeleteWorkId(),type_of_work});
                        pendingScreenAdapter.removeSavedItem(prefManager.getDeleteAdapterPosition());
                        pendingScreenAdapter.notifyDataSetChanged();
                    }else if(type_of_work.equalsIgnoreCase(AppConstant.ADDITIONAL_WORK))
                    {
                        dbData.deleteAdditionalListTable();
                        db.delete(DBHelper.SAVE_IMAGE, "dcode = ? and bcode = ? and pvcode = ? and work_id = ? and  type_of_work = ? and cd_work_no = ? and work_type_flag_le = ?", new String[]{prefManager.getDistrictCode(), prefManager.getBlockCode(), prefManager.getPvCode(), prefManager.getDeleteWorkId(),type_of_work,prefManager.getDeleteCdWorkNo(),prefManager.getDeleteCdWorkTypeFlag()});
                        pendingScreenAdapter.removeSavedItem(prefManager.getDeleteAdapterPosition());
                        pendingScreenAdapter.notifyDataSetChanged();
                    }
                }
                else if (jsonObject.getString("STATUS").equalsIgnoreCase("OK") && jsonObject.getString("RESPONSE").equalsIgnoreCase("FAIL")) {
                    Utils.showAlert(this, jsonObject.getString("MESSAGE"));

                }
                Log.d("savedImage", "" + responseDecryptedBlockKey);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void onBackPressed() {
        if (!searchView.isIconified()) {
            searchView.setIconified(true);
            return;
        }
        super.onBackPressed();
        setResult(Activity.RESULT_CANCELED);
        overridePendingTransition(R.anim.slide_enter, R.anim.slide_exit);
    }
    @Override
    public void OnError(VolleyError volleyError) {

    }
}
