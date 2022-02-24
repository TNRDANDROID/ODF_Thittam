package com.nic.ODF_Thittam.dataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;

import com.nic.ODF_Thittam.constant.AppConstant;
import com.nic.ODF_Thittam.model.ODF_Thittam;

import java.util.ArrayList;


public class dbData {
    private SQLiteDatabase db;
    private SQLiteOpenHelper dbHelper;
    private Context context;

    public dbData(Context context){
        this.dbHelper = new DBHelper(context);
        this.context = context;
    }

    public void open() {
        db = dbHelper.getWritableDatabase();
    }

    public void close() {
        if(dbHelper != null) {
            dbHelper.close();
        }
    }

    /****** DISTRICT TABLE *****/
    public ODF_Thittam insertDistrict(ODF_Thittam ODF_Thittam) {

        ContentValues values = new ContentValues();
        values.put(AppConstant.DISTRICT_CODE, ODF_Thittam.getDistictCode());
        values.put(AppConstant.DISTRICT_NAME, ODF_Thittam.getDistrictName());

        long id = db.insert(DBHelper.DISTRICT_TABLE_NAME,null,values);
        Log.d("Inserted_id_district", String.valueOf(id));

        return ODF_Thittam;
    }

    public ArrayList<ODF_Thittam > getAll_District() {

        ArrayList<ODF_Thittam > cards = new ArrayList<>();
        Cursor cursor = null;

        try {
            cursor = db.rawQuery("select * from "+DBHelper.DISTRICT_TABLE_NAME,null);
            // cursor = db.query(CardsDBHelper.TABLE_CARDS,
            //       COLUMNS, null, null, null, null, null);
            if (cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    ODF_Thittam  card = new ODF_Thittam ();
                    card.setDistictCode(cursor.getString(cursor
                            .getColumnIndexOrThrow(AppConstant.DISTRICT_CODE)));
                    card.setDistrictName(cursor.getString(cursor
                            .getColumnIndexOrThrow(AppConstant.DISTRICT_NAME)));

                    cards.add(card);
                }
            }
        } catch (Exception e){
            //   Log.d(DEBUG_TAG, "Exception raised with a value of " + e);
        } finally{
            if (cursor != null) {
                cursor.close();
            }
        }
        return cards;
    }

    /****** BLOCKTABLE *****/
    public ODF_Thittam insertBlock(ODF_Thittam ODF_Thittam) {

        ContentValues values = new ContentValues();
        values.put(AppConstant.DISTRICT_CODE, ODF_Thittam.getDistictCode());
        values.put(AppConstant.BLOCK_CODE, ODF_Thittam.getBlockCode());
        values.put(AppConstant.BLOCK_NAME, ODF_Thittam.getBlockName());

        long id = db.insert(DBHelper.BLOCK_TABLE_NAME,null,values);
        Log.d("Inserted_id_block", String.valueOf(id));

        return ODF_Thittam;
    }
    public ODF_Thittam insertCategoryList(ODF_Thittam odfMonitoringListValue) {

        ContentValues values = new ContentValues();
        values.put(AppConstant.KEY_MOTIVATOR_CATEGORY_ID,odfMonitoringListValue.getMotivatorCategoryId());
        values.put(AppConstant.KEY_MOTIVATOR_CATEGORY_NAME,odfMonitoringListValue.getMotivatorCategoryName());

        long id = db.insert(DBHelper.MOTIVATOR_CATEGORY_LIST_TABLE_NAME,null,values);
        Log.d("Inserted_idCategoryList",String.valueOf(id));

        return odfMonitoringListValue;
    }

    public ArrayList<ODF_Thittam> getAllCategoryList() {

        ArrayList<ODF_Thittam> cards = new ArrayList<>();
        Cursor cursor = null;

        try {
            cursor = db.rawQuery("select * from "+DBHelper.MOTIVATOR_CATEGORY_LIST_TABLE_NAME,null);
            // cursor = db.query(CardsDBHelper.TABLE_CARDS,
            //       COLUMNS, null, null, null, null, null);
            if (cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    ODF_Thittam categoryList = new ODF_Thittam();

                    categoryList.setMotivatorCategoryId(cursor.getInt(cursor
                            .getColumnIndexOrThrow(AppConstant.KEY_MOTIVATOR_CATEGORY_ID)));
                    categoryList.setMotivatorCategoryName(cursor.getString(cursor
                            .getColumnIndexOrThrow(AppConstant.KEY_MOTIVATOR_CATEGORY_NAME)));


                    cards.add(categoryList);
                }
            }
        } catch (Exception e){
            //   Log.d(DEBUG_TAG, "Exception raised with a value of " + e);
        } finally{
            if (cursor != null) {
                cursor.close();
            }
        }
        return cards;
    }

    public ArrayList<ODF_Thittam > getAll_Block() {

        ArrayList<ODF_Thittam > cards = new ArrayList<>();
        Cursor cursor = null;

        try {
            cursor = db.rawQuery("select * from "+DBHelper.BLOCK_TABLE_NAME,null);
            // cursor = db.query(CardsDBHelper.TABLE_CARDS,
            //       COLUMNS, null, null, null, null, null);
            if (cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    ODF_Thittam  card = new ODF_Thittam ();
                    card.setDistictCode(cursor.getString(cursor
                            .getColumnIndexOrThrow(AppConstant.DISTRICT_CODE)));
                    card.setBlockCode(cursor.getString(cursor
                            .getColumnIndexOrThrow(AppConstant.BLOCK_CODE)));
                    card.setBlockName(cursor.getString(cursor
                            .getColumnIndexOrThrow(AppConstant.BLOCK_NAME)));

                    cards.add(card);
                }
            }
        } catch (Exception e){
            //   Log.d(DEBUG_TAG, "Exception raised with a value of " + e);
        } finally{
            if (cursor != null) {
                cursor.close();
            }
        }
        return cards;
    }
    public ODF_Thittam insertBankName(ODF_Thittam odfMonitoringListValue) {

        ContentValues values = new ContentValues();
        values.put(AppConstant.BANK_ID,odfMonitoringListValue.getBank_Id());
        values.put(AppConstant.OMC_NAME,odfMonitoringListValue.getOMC_Name());
        values.put(AppConstant.BANK_NAME,odfMonitoringListValue.getBank_Name());

        long id = db.insert(DBHelper.BANKLIST_TABLE_NAME,null,values);
        Log.d("Inserted_id_bankname",String.valueOf(id));

        return odfMonitoringListValue;
    }

    /****** VILLAGE TABLE *****/
    public ODF_Thittam insertVillage(ODF_Thittam ODF_Thittam) {

        ContentValues values = new ContentValues();
        values.put(AppConstant.DISTRICT_CODE, ODF_Thittam.getDistictCode());
        values.put(AppConstant.BLOCK_CODE, ODF_Thittam.getBlockCode());
        values.put(AppConstant.PV_CODE, ODF_Thittam.getPvCode());
        values.put(AppConstant.PV_NAME, ODF_Thittam.getPvName());

        long id = db.insert(DBHelper.VILLAGE_TABLE_NAME,null,values);
        Log.d("Inserted_id_village", String.valueOf(id));

        return ODF_Thittam;
    }

    public ArrayList<ODF_Thittam > getAll_Village() {

        ArrayList<ODF_Thittam > cards = new ArrayList<>();
        Cursor cursor = null;

        try {
            cursor = db.rawQuery("select * from "+DBHelper.VILLAGE_TABLE_NAME+" order by pvname asc",null);
            // cursor = db.query(CardsDBHelper.TABLE_CARDS,
            //       COLUMNS, null, null, null, null, null);
            if (cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    ODF_Thittam  card = new ODF_Thittam ();
                    card.setDistictCode(cursor.getString(cursor
                            .getColumnIndexOrThrow(AppConstant.DISTRICT_CODE)));
                    card.setBlockCode(cursor.getString(cursor
                            .getColumnIndexOrThrow(AppConstant.BLOCK_CODE)));
                    card.setPvCode(cursor.getString(cursor
                            .getColumnIndexOrThrow(AppConstant.PV_CODE)));
                    card.setPvName(cursor.getString(cursor
                            .getColumnIndexOrThrow(AppConstant.PV_NAME)));

                    cards.add(card);
                }
            }
        } catch (Exception e){
            //   Log.d(DEBUG_TAG, "Exception raised with a value of " + e);
        } finally{
            if (cursor != null) {
                cursor.close();
            }
        }
        return cards;
    }

    public ODF_Thittam insertScheme(ODF_Thittam ODF_Thittam) {

        ContentValues values = new ContentValues();
        values.put(AppConstant.KEY_SCHEME_SEQUENTIAL_ID, ODF_Thittam.getSchemeSequentialID());
        values.put(AppConstant.KEY_SCHEME_NAME, ODF_Thittam.getSchemeName());
        values.put(AppConstant.FINANCIAL_YEAR, ODF_Thittam.getFinancialYear());

        long id = db.insert(DBHelper.SCHEME_TABLE_NAME, null, values);
        Log.d("Inserted_id_Stage", String.valueOf(id));

        return ODF_Thittam;
    }

    public ArrayList<ODF_Thittam> getAll_Scheme() {

        ArrayList<ODF_Thittam> cards = new ArrayList<>();
        Cursor cursor = null;

        try {
            cursor = db.rawQuery("select * from " + DBHelper.SCHEME_TABLE_NAME, null);
            // cursor = db.query(CardsDBHelper.TABLE_CARDS,
            //       COLUMNS, null, null, null, null, null);
            if (cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    ODF_Thittam card = new ODF_Thittam();
                    card.setSchemeSequentialID(cursor.getInt(cursor
                            .getColumnIndexOrThrow(AppConstant.KEY_SCHEME_SEQUENTIAL_ID)));
                    card.setSchemeName(cursor.getString(cursor
                            .getColumnIndexOrThrow(AppConstant.KEY_SCHEME_NAME)));
                    card.setFinancialYear(cursor.getString(cursor
                            .getColumnIndexOrThrow(AppConstant.FINANCIAL_YEAR)));
                    cards.add(card);
                }
            }
        } catch (Exception e) {
            //   Log.d(DEBUG_TAG, "Exception raised with a value of " + e);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return cards;
    }

    public ODF_Thittam insertFinYear(ODF_Thittam ODF_Thittam) {

        ContentValues values = new ContentValues();
        values.put(AppConstant.FINANCIAL_YEAR, ODF_Thittam.getFinancialYear());

        long id = db.insert(DBHelper.FINANCIAL_YEAR_TABLE_NAME, null, values);
        Log.d("Inserted_id_FinYear", String.valueOf(id));

        return ODF_Thittam;
    }

    public ArrayList<ODF_Thittam> getAll_FinYear() {

        ArrayList<ODF_Thittam> cards = new ArrayList<>();
        Cursor cursor = null;

        try {
            cursor = db.rawQuery("select * from " + DBHelper.FINANCIAL_YEAR_TABLE_NAME, null);
            // cursor = db.query(CardsDBHelper.TABLE_CARDS,
            //       COLUMNS, null, null, null, null, null);
            if (cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    ODF_Thittam card = new ODF_Thittam();
                    card.setFinancialYear(cursor.getString(cursor
                            .getColumnIndexOrThrow(AppConstant.FINANCIAL_YEAR)));

                    cards.add(card);
                }
            }
        } catch (Exception e) {
            //   Log.d(DEBUG_TAG, "Exception raised with a value of " + e);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return cards;
    }

    public ODF_Thittam insertStage(ODF_Thittam ODF_Thittam) {

        ContentValues values = new ContentValues();
        values.put(AppConstant.WORK_GROUP_ID, ODF_Thittam.getWorkGroupID());
        values.put(AppConstant.WORK_TYPE_ID, ODF_Thittam.getWorkTypeID());
        values.put(AppConstant.WORK_STAGE_ORDER, ODF_Thittam.getWorkStageOrder());
        values.put(AppConstant.WORK_STAGE_CODE, ODF_Thittam.getWorkStageCode());
        values.put(AppConstant.WORK_SATGE_NAME, ODF_Thittam.getWorkStageName());

        long id = db.insert(DBHelper.WORK_STAGE_TABLE, null, values);
        Log.d("Inserted_id_Stage", String.valueOf(id));

        return ODF_Thittam;
    }

    public ArrayList<ODF_Thittam> getAll_Stage() {

        ArrayList<ODF_Thittam> cards = new ArrayList<>();
        Cursor cursor = null;

        try {
            cursor = db.rawQuery("select * from " + DBHelper.WORK_STAGE_TABLE, null);
            // cursor = db.query(CardsDBHelper.TABLE_CARDS,
            //       COLUMNS, null, null, null, null, null);
            if (cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    ODF_Thittam card = new ODF_Thittam();
                    card.setWorkGroupID(cursor.getString(cursor
                            .getColumnIndexOrThrow(AppConstant.WORK_GROUP_ID)));
                    card.setWorkTypeID(cursor.getString(cursor
                            .getColumnIndexOrThrow(AppConstant.WORK_TYPE_ID)));
                    card.setWorkStageOrder(cursor.getString(cursor
                            .getColumnIndexOrThrow(AppConstant.WORK_STAGE_ORDER)));
                    card.setWorkStageCode(cursor.getString(cursor
                            .getColumnIndexOrThrow(AppConstant.WORK_STAGE_CODE)));

                    card.setWorkStageName(cursor.getString(cursor
                            .getColumnIndexOrThrow(AppConstant.WORK_SATGE_NAME)));

                    cards.add(card);
                }
            }
        } catch (Exception e) {
            //   Log.d(DEBUG_TAG, "Exception raised with a value of " + e);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return cards;
    }

    public ODF_Thittam insertAdditionalStage(ODF_Thittam ODF_Thittam) {

        ContentValues values = new ContentValues();
        values.put(AppConstant.WORK_TYPE_CODE, ODF_Thittam.getWorkTypeCode());
        values.put(AppConstant.WORK_STAGE_ORDER, ODF_Thittam.getWorkStageOrder());
        values.put(AppConstant.WORK_STAGE_CODE, ODF_Thittam.getWorkStageCode());
        values.put(AppConstant.WORK_SATGE_NAME, ODF_Thittam.getWorkStageName());
        values.put(AppConstant.CD_TYPE_FLAG, ODF_Thittam.getWorkTypeFlagLe());

        long id = db.insert(DBHelper.ADDITIONAL_WORK_STAGE_TABLE, null, values);
        Log.d("Inserted_id_Add_Stage", String.valueOf(id));

        return ODF_Thittam;
    }

    public ArrayList<ODF_Thittam> getAdditionalStage() {

        ArrayList<ODF_Thittam> cards = new ArrayList<>();
        Cursor cursor = null;

        try {
            cursor = db.rawQuery("select * from " + DBHelper.ADDITIONAL_WORK_STAGE_TABLE, null);
            // cursor = db.query(CardsDBHelper.TABLE_CARDS,
            //       COLUMNS, null, null, null, null, null);
            if (cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    ODF_Thittam card = new ODF_Thittam();
                    card.setWorkTypeCode(cursor.getString(cursor
                            .getColumnIndexOrThrow(AppConstant.WORK_TYPE_CODE)));;
                    card.setWorkStageOrder(cursor.getString(cursor
                            .getColumnIndexOrThrow(AppConstant.WORK_STAGE_ORDER)));
                    card.setWorkStageCode(cursor.getString(cursor
                            .getColumnIndexOrThrow(AppConstant.WORK_STAGE_CODE)));

                    card.setWorkStageName(cursor.getString(cursor
                            .getColumnIndexOrThrow(AppConstant.WORK_SATGE_NAME)));
                    card.setWorkTypeFlagLe(cursor.getString(cursor
                            .getColumnIndexOrThrow(AppConstant.CD_TYPE_FLAG)));

                    cards.add(card);
                }
            }
        } catch (Exception e) {
            //   Log.d(DEBUG_TAG, "Exception raised with a value of " + e);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return cards;
    }

    public ODF_Thittam insertWorkList(ODF_Thittam ODF_Thittam) {

        ContentValues values = new ContentValues();
        values.put(AppConstant.DISTRICT_CODE, ODF_Thittam.getDistictCode());
        values.put(AppConstant.BLOCK_CODE, ODF_Thittam.getBlockCode());
        values.put(AppConstant.PV_CODE, ODF_Thittam.getPvCode());
        values.put(AppConstant.WORK_ID, ODF_Thittam.getWorkId());
        values.put(AppConstant.SCHEME_GROUP_ID, ODF_Thittam.getSchemeGroupID());
        values.put(AppConstant.SCHEME_ID, ODF_Thittam.getSchemeID());
        values.put(AppConstant.SCHEME_GROUP_NAME, ODF_Thittam.getSchemeGroupName());
        values.put(AppConstant.KEY_SCHEME_NAME, ODF_Thittam.getSchemeName());
        values.put(AppConstant.FINANCIAL_YEAR, ODF_Thittam.getFinancialYear());
        values.put(AppConstant.AGENCY_NAME, ODF_Thittam.getAgencyName());
        values.put(AppConstant.WORK_GROUP_NAME, ODF_Thittam.getWorkGroupNmae());
        values.put(AppConstant.WORK_NAME, ODF_Thittam.getWorkName());
        values.put(AppConstant.WORK_GROUP_ID, ODF_Thittam.getWorkGroupID());
        values.put(AppConstant.WORK_TYPE, ODF_Thittam.getWorkTypeID());
        values.put(AppConstant.CURRENT_STAGE_OF_WORK, ODF_Thittam.getCurrentStage());
        values.put(AppConstant.AS_VALUE, ODF_Thittam.getAsValue());
        values.put(AppConstant.AMOUNT_SPENT_SOFAR, ODF_Thittam.getAmountSpendSoFar());
        values.put(AppConstant.STAGE_NAME, ODF_Thittam.getStageName());
        values.put(AppConstant.BENEFICIARY_NAME, ODF_Thittam.getBeneficiaryName());
        values.put(AppConstant.BENEFICIARY_FATHER_NAME, ODF_Thittam.getBeneficiaryFatherName());
        values.put(AppConstant.WORK_TYPE_NAME, ODF_Thittam.getWorkTypeName());
        values.put(AppConstant.YN_COMPLETED, ODF_Thittam.getYnCompleted());
        values.put(AppConstant.CD_PROT_WORK_YN, ODF_Thittam.getCdProtWorkYn());
        values.put(AppConstant.STATE_CODE, ODF_Thittam.getStateCode());
        values.put(AppConstant.DISTRICT_NAME, ODF_Thittam.getDistrictName());
        values.put(AppConstant.BLOCK_NAME, ODF_Thittam.getBlockName());
        values.put(AppConstant.PV_NAME, ODF_Thittam.getPvName());
        values.put(AppConstant.COMMUNITY_NAME, ODF_Thittam.getCommunity());
        values.put(AppConstant.GENDER, ODF_Thittam.getGender());
        values.put(AppConstant.LAST_VISITED_DATE, ODF_Thittam.getLastVisitedDate());
        values.put(AppConstant.KEY_IMAGE_AVAILABLE, ODF_Thittam.getImageAvailable());

        long id = db.insert(DBHelper.WORK_LIST_TABLE_BASED_ON_FINYEAR_VIlLAGE, null, values);
        Log.d("Inserted_id_Worklist", String.valueOf(id));

        return ODF_Thittam;
    }

    public ArrayList<ODF_Thittam> getAllWorkLIst(String purpose, String fin_year, String dcode, String bcode, String pvcode,Integer schemeSeqId) {

        ArrayList<ODF_Thittam> cards = new ArrayList<>();
        Cursor cursor = null;
        String condition = "";

        if(purpose.equalsIgnoreCase("fetch")) {
            condition = " where fin_year = '" + fin_year + "' and dcode = "+dcode+" and bcode = "+bcode+ " and pvcode = "+pvcode+ " and scheme_id = "+schemeSeqId+ " and current_stage_of_work != 10";
        }else{
            condition = " where fin_year = '" + fin_year + "' and dcode = "+dcode+" and bcode = "+bcode+ " and pvcode = "+pvcode+ " and current_stage_of_work != 10";

        }

        try {
            cursor = db.rawQuery("select * from " + DBHelper.WORK_LIST_TABLE_BASED_ON_FINYEAR_VIlLAGE +  condition, null);

            if (cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    ODF_Thittam card = new ODF_Thittam();

                    card.setDistictCode(cursor.getString(cursor.getColumnIndexOrThrow(AppConstant.DISTRICT_CODE)));
                    card.setBlockCode(cursor.getString(cursor.getColumnIndexOrThrow(AppConstant.BLOCK_CODE)));
                    card.setPvCode(cursor.getString(cursor.getColumnIndexOrThrow(AppConstant.PV_CODE)));
                    card.setWorkId(cursor.getInt(cursor.getColumnIndexOrThrow(AppConstant.WORK_ID)));
                    card.setSchemeGroupID(cursor.getInt(cursor.getColumnIndexOrThrow(AppConstant.SCHEME_GROUP_ID)));
                    card.setSchemeID(cursor.getInt(cursor.getColumnIndexOrThrow(AppConstant.SCHEME_ID)));
                    card.setSchemeGroupName(cursor.getString(cursor.getColumnIndexOrThrow(AppConstant.SCHEME_GROUP_NAME)));
                    card.setSchemeName(cursor.getString(cursor.getColumnIndexOrThrow(AppConstant.KEY_SCHEME_NAME)));
                    card.setFinancialYear(cursor.getString(cursor.getColumnIndexOrThrow(AppConstant.FINANCIAL_YEAR)));
                    card.setAgencyName(cursor.getString(cursor.getColumnIndexOrThrow(AppConstant.AGENCY_NAME)));
                    card.setWorkGroupNmae(cursor.getString(cursor.getColumnIndexOrThrow(AppConstant.WORK_GROUP_NAME)));
                    card.setWorkName(cursor.getString(cursor.getColumnIndexOrThrow(AppConstant.WORK_NAME)));
                    card.setWorkGroupID(cursor.getString(cursor.getColumnIndexOrThrow(AppConstant.WORK_GROUP_ID)));
                    card.setWorkTypeID(cursor.getString(cursor.getColumnIndexOrThrow(AppConstant.WORK_TYPE)));
                    card.setCurrentStage(cursor.getInt(cursor.getColumnIndexOrThrow(AppConstant.CURRENT_STAGE_OF_WORK)));
                    card.setAsValue(cursor.getInt(cursor.getColumnIndexOrThrow(AppConstant.AS_VALUE)));
                    card.setAmountSpendSoFar(cursor.getString(cursor.getColumnIndexOrThrow(AppConstant.AMOUNT_SPENT_SOFAR)));
                    card.setStageName(cursor.getString(cursor.getColumnIndexOrThrow(AppConstant.STAGE_NAME)));
                    card.setBeneficiaryName(cursor.getString(cursor.getColumnIndexOrThrow(AppConstant.BENEFICIARY_NAME)));
                    card.setBeneficiaryFatherName(cursor.getString(cursor.getColumnIndexOrThrow(AppConstant.BENEFICIARY_FATHER_NAME)));
                    card.setWorkTypeName(cursor.getString(cursor.getColumnIndexOrThrow(AppConstant.WORK_TYPE_NAME)));
                    card.setYnCompleted(cursor.getString(cursor.getColumnIndexOrThrow(AppConstant.YN_COMPLETED)));
                    card.setCdProtWorkYn(cursor.getString(cursor.getColumnIndexOrThrow(AppConstant.CD_PROT_WORK_YN)));
                    card.setStateCode(cursor.getInt(cursor.getColumnIndexOrThrow(AppConstant.STATE_CODE)));
                    card.setDistrictName(cursor.getString(cursor.getColumnIndexOrThrow(AppConstant.DISTRICT_NAME)));
                    card.setBlockName(cursor.getString(cursor.getColumnIndexOrThrow(AppConstant.BLOCK_NAME)));
                    card.setPvName(cursor.getString(cursor.getColumnIndexOrThrow(AppConstant.PV_NAME)));
                    card.setCommunity(cursor.getString(cursor.getColumnIndexOrThrow(AppConstant.COMMUNITY_NAME)));
                    card.setGender(cursor.getString(cursor.getColumnIndexOrThrow(AppConstant.GENDER)));
                    card.setLastVisitedDate(cursor.getString(cursor.getColumnIndexOrThrow(AppConstant.LAST_VISITED_DATE)));
                    card.setImageAvailable(cursor.getString(cursor.getColumnIndexOrThrow(AppConstant.KEY_IMAGE_AVAILABLE)));

                    cards.add(card);
                }
            }
        } catch (Exception e) {
               Log.d("Excep" +
                       "", "Exception raised with a value of " + e);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return cards;
    }

    public ODF_Thittam insertAdditionalWorkList(ODF_Thittam ODF_Thittam) {

        ContentValues values = new ContentValues();
        values.put(AppConstant.DISTRICT_CODE, ODF_Thittam.getDistictCode());
        values.put(AppConstant.BLOCK_CODE, ODF_Thittam.getBlockCode());
        values.put(AppConstant.PV_CODE, ODF_Thittam.getPvCode());
        values.put(AppConstant.SCHEME_ID, ODF_Thittam.getSchemeID());
        values.put(AppConstant.FINANCIAL_YEAR, ODF_Thittam.getFinancialYear());
        values.put(AppConstant.WORK_ID, ODF_Thittam.getWorkId());
        values.put(AppConstant.WORK_GROUP_ID, ODF_Thittam.getWorkGroupID());
        values.put(AppConstant.ROAD_NAME, ODF_Thittam.getRoadName());
        values.put(AppConstant.CD_WORK_NO, ODF_Thittam.getCdWorkNo());
        values.put(AppConstant.CURRENT_STAGE_OF_WORK, ODF_Thittam.getCurrentStage());
        values.put(AppConstant.CD_TYPE_ID, ODF_Thittam.getCdTypeId());
        values.put(AppConstant.WORK_TYPE_FLAG_LE, ODF_Thittam.getWorkTypeFlagLe());
        values.put(AppConstant.CD_CODE, ODF_Thittam.getCdCode());
        values.put(AppConstant.CD_NAME, ODF_Thittam.getCdName());
        values.put(AppConstant.CHAINAGE_METER, ODF_Thittam.getChainageMeter());
        values.put(AppConstant.WORK_SATGE_NAME, ODF_Thittam.getWorkStageName());
        values.put(AppConstant.KEY_IMAGE_AVAILABLE, ODF_Thittam.getImageAvailable());


        long id = db.insert(DBHelper.ADDITIONAL_WORK_LIST, null, values);
        Log.d("Inserted_id_Additional", String.valueOf(id));

        return ODF_Thittam;
    }

    public ArrayList<ODF_Thittam> getAllAdditionalWork(String work_id,String fin_year, String dcode, String bcode, String pvcode,Integer schemeSeqId) {

        ArrayList<ODF_Thittam> cards = new ArrayList<>();
        Cursor cursor = null;

        String condition = "";

        if (work_id != "") {
            condition = " where work_id = " + work_id + " and fin_year = '" + fin_year + "' and dcode = " + dcode + " and bcode = " + bcode + " and pvcode = " + pvcode+ " and scheme_id = " + schemeSeqId+ " and current_stage_of_work != 10";
        }else {
            condition = " where fin_year = '" + fin_year + "' and dcode = " + dcode + " and bcode = " + bcode + " and pvcode = " + pvcode+ " and current_stage_of_work != 10";
        }


        try {
            cursor = db.rawQuery("select * from " + DBHelper.ADDITIONAL_WORK_LIST + condition, null);
            // cursor = db.query(CardsDBHelper.TABLE_CARDS,
            //       COLUMNS, null, null, null, null, null);
            if (cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    ODF_Thittam card = new ODF_Thittam();

                    card.setDistictCode(cursor.getString(cursor.getColumnIndexOrThrow(AppConstant.DISTRICT_CODE)));
                    card.setBlockCode(cursor.getString(cursor.getColumnIndexOrThrow(AppConstant.BLOCK_CODE)));
                    card.setPvCode(cursor.getString(cursor.getColumnIndexOrThrow(AppConstant.PV_CODE)));
                    card.setSchemeID(Integer.valueOf(cursor.getString(cursor.getColumnIndexOrThrow(AppConstant.SCHEME_ID))));
                    card.setFinancialYear(cursor.getString(cursor.getColumnIndexOrThrow(AppConstant.FINANCIAL_YEAR)));
                    card.setWorkId(cursor.getInt(cursor.getColumnIndexOrThrow(AppConstant.WORK_ID)));
                    card.setWorkGroupID(cursor.getString(cursor.getColumnIndexOrThrow(AppConstant.WORK_GROUP_ID)));
                    card.setRoadName(cursor.getString(cursor.getColumnIndexOrThrow(AppConstant.ROAD_NAME)));
                    card.setCdWorkNo(cursor.getInt(cursor.getColumnIndexOrThrow(AppConstant.CD_WORK_NO)));
                    card.setCurrentStage(cursor.getInt(cursor.getColumnIndexOrThrow(AppConstant.CURRENT_STAGE_OF_WORK)));
                    card.setCdTypeId(cursor.getInt(cursor.getColumnIndexOrThrow(AppConstant.CD_TYPE_ID)));
                    card.setWorkTypeFlagLe(cursor.getString(cursor.getColumnIndexOrThrow(AppConstant.WORK_TYPE_FLAG_LE)));
                    card.setCdCode(cursor.getInt(cursor.getColumnIndexOrThrow(AppConstant.CD_CODE)));
                    card.setCdName(cursor.getString(cursor.getColumnIndexOrThrow(AppConstant.CD_NAME)));
                    card.setChainageMeter(cursor.getString(cursor.getColumnIndexOrThrow(AppConstant.CHAINAGE_METER)));
                    card.setWorkStageName(cursor.getString(cursor.getColumnIndexOrThrow(AppConstant.WORK_SATGE_NAME)));
                    card.setImageAvailable(cursor.getString(cursor.getColumnIndexOrThrow(AppConstant.KEY_IMAGE_AVAILABLE)));

                    cards.add(card);
                }
            }
        } catch (Exception e) {
            //   Log.d(DEBUG_TAG, "Exception raised with a value of " + e);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return cards;
    }

    public ArrayList<ODF_Thittam> getSavedWorkImage(String purpose,String dcode,String bcode,String pvcode,String work_id) {

        ArrayList<ODF_Thittam> cards = new ArrayList<>();
        Cursor cursor = null;
        String selection = "server_flag = ? ";
        String[] selectionArgs = new String[]{"0"};

        if(purpose.equalsIgnoreCase("upload")) {
            selection = "server_flag = ? and dcode = ? and bcode = ? and pvcode = ? and work_id = ?";
            selectionArgs = new String[]{"0",dcode,bcode,pvcode,work_id};
        }




        try {
            cursor = db.query(DBHelper.SAVE_IMAGE,
                    new String[]{"*"}, selection, selectionArgs, null, null, null);
            if (cursor.getCount() > 0) {
                while (cursor.moveToNext()) {

                    byte[] photo = cursor.getBlob(cursor.getColumnIndexOrThrow(AppConstant.KEY_IMAGES));
                    byte[] decodedString = Base64.decode(photo, Base64.DEFAULT);
                    Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

                    ODF_Thittam card = new ODF_Thittam();
                    card.setWorkId(cursor.getInt(cursor
                            .getColumnIndexOrThrow(AppConstant.WORK_ID)));
                    card.setWorkGroupID(cursor.getString(cursor
                            .getColumnIndexOrThrow(AppConstant.WORK_GROUP_ID)));
                    card.setCdWorkNo(cursor.getInt(cursor
                            .getColumnIndexOrThrow(AppConstant.CD_WORK_NO)));
                    card.setWorkTypeFlagLe(cursor.getString(cursor
                            .getColumnIndexOrThrow(AppConstant.WORK_TYPE_FLAG_LE)));
                    card.setDistictCode(cursor.getString(cursor
                            .getColumnIndexOrThrow(AppConstant.DISTRICT_CODE)));
                    card.setBlockCode(cursor.getString(cursor
                            .getColumnIndex(AppConstant.BLOCK_CODE)));
                    card.setPvCode(cursor.getString(cursor
                            .getColumnIndexOrThrow(AppConstant.PV_CODE)));
                    card.setTypeOfWork(cursor.getString(cursor
                            .getColumnIndexOrThrow(AppConstant.TYPE_OF_WORK)));
                    card.setWorkStageCode(cursor.getString(cursor
                            .getColumnIndexOrThrow(AppConstant.WORK_STAGE_CODE)));
                    card.setWorkStageName(cursor.getString(cursor
                            .getColumnIndexOrThrow(AppConstant.WORK_SATGE_NAME)));
                    card.setLatitude(cursor.getString(cursor
                            .getColumnIndexOrThrow(AppConstant.KEY_LATITUDE)));
                    card.setLongitude(cursor.getString(cursor
                            .getColumnIndexOrThrow(AppConstant.KEY_LONGITUDE)));
                    card.setImage(decodedByte);
                    card.setImageRemark(cursor.getString(cursor
                            .getColumnIndexOrThrow(AppConstant.KEY_IMAGE_REMARK)));
                    card.setCreatedDate(cursor.getString(cursor
                            .getColumnIndexOrThrow(AppConstant.KEY_CREATED_DATE)));

                    cards.add(card);
                }
            }
        } catch (Exception e){
            //   Log.d(DEBUG_TAG, "Exception raised with a value of " + e);
        } finally{
            if (cursor != null) {
                cursor.close();
            }
        }
        return cards;
    }

    public ArrayList<ODF_Thittam> selectImage(String dcode,String bcode, String pvcode,String work_id,String type_of_work,String cd_work_no,String work_type_flag_le) {
        db.isOpen();
        ArrayList<ODF_Thittam> cards = new ArrayList<>();
        Cursor cursor = null;
        String selection = null;
        String[] selectionArgs = null;
        if (type_of_work.equalsIgnoreCase(AppConstant.ADDITIONAL_WORK)) {
            selection = "dcode = ? and bcode = ? and pvcode = ? and work_id = ? and type_of_work = ? and cd_work_no = ? and work_type_flag_le = ?";
            selectionArgs = new String[]{dcode,bcode,pvcode,work_id,type_of_work,cd_work_no,work_type_flag_le};
        }else {
            selection = "dcode = ? and bcode = ? and pvcode = ? and work_id = ? and type_of_work = ?";
            selectionArgs = new String[]{dcode,bcode,pvcode,work_id,type_of_work};
        }

        try {
            cursor = db.query(DBHelper.SAVE_IMAGE,
                    new String[]{"*"}, selection,selectionArgs, null, null, null);
            if (cursor.getCount() > 0) {
                while (cursor.moveToNext()) {

                    byte[] photo = cursor.getBlob(cursor.getColumnIndexOrThrow(AppConstant.KEY_IMAGES));
                    byte[] decodedString = Base64.decode(photo, Base64.DEFAULT);
                    Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

                    ODF_Thittam card = new ODF_Thittam();
                    card.setWorkId(cursor.getInt(cursor
                            .getColumnIndexOrThrow(AppConstant.WORK_ID)));
                    card.setWorkGroupID(cursor.getString(cursor
                            .getColumnIndexOrThrow(AppConstant.WORK_GROUP_ID)));
                    card.setCdWorkNo(cursor.getInt(cursor
                            .getColumnIndexOrThrow(AppConstant.CD_WORK_NO)));
                    card.setDistictCode(cursor.getString(cursor
                            .getColumnIndexOrThrow(AppConstant.DISTRICT_CODE)));
                    card.setBlockCode(cursor.getString(cursor
                            .getColumnIndex(AppConstant.BLOCK_CODE)));
                    card.setPvCode(cursor.getString(cursor
                            .getColumnIndexOrThrow(AppConstant.PV_CODE)));
                    card.setTypeOfWork(cursor.getString(cursor
                            .getColumnIndexOrThrow(AppConstant.TYPE_OF_WORK)));
                    card.setWorkStageCode(cursor.getString(cursor
                            .getColumnIndexOrThrow(AppConstant.WORK_STAGE_CODE)));
                    card.setLatitude(cursor.getString(cursor
                            .getColumnIndexOrThrow(AppConstant.KEY_LATITUDE)));
                    card.setLongitude(cursor.getString(cursor
                            .getColumnIndexOrThrow(AppConstant.KEY_LONGITUDE)));
                    card.setImage(decodedByte);
                    card.setImageRemark(cursor.getString(cursor
                            .getColumnIndexOrThrow(AppConstant.KEY_IMAGE_REMARK)));
                    card.setCreatedDate(cursor.getString(cursor
                            .getColumnIndexOrThrow(AppConstant.KEY_CREATED_DATE)));
                    card.setWorkStageName(cursor.getString(cursor
                            .getColumnIndexOrThrow(AppConstant.WORK_SATGE_NAME)));

                    cards.add(card);
                }
            }
        } catch (Exception e){
            //   Log.d(DEBUG_TAG, "Exception raised with a value of " + e);
        } finally{
            if (cursor != null) {
                cursor.close();
            }
        }
        return cards;
    }

    public void deleteDistrictTable() {
        db.execSQL("delete from " + DBHelper.DISTRICT_TABLE_NAME);
    }

    public void deleteBlockTable() {
        db.execSQL("delete from " + DBHelper.BLOCK_TABLE_NAME);
    }

    public void deleteVillageTable() {
        db.execSQL("delete from " + DBHelper.VILLAGE_TABLE_NAME);
    }

    public void deleteFinYearTable() { db.execSQL("delete from " + DBHelper.FINANCIAL_YEAR_TABLE_NAME); }

    public void deleteSchemeTable() {
        db.execSQL("delete from " + DBHelper.SCHEME_TABLE_NAME);
    }

    public void deleteWorkStageTable() {
        db.execSQL("delete from " + DBHelper.WORK_STAGE_TABLE);
    }

    public void deleteWorkListTable() {
        db.execSQL("delete from " + DBHelper.WORK_LIST_TABLE_BASED_ON_FINYEAR_VIlLAGE);
    }

    public void deleteAdditionalListTable() {
        db.execSQL("delete from " + DBHelper.ADDITIONAL_WORK_LIST);
    }


    public void deleteAll() {
        deleteDistrictTable();
        deleteBlockTable();
        deleteVillageTable();
        deleteFinYearTable();
        deleteSchemeTable();
        deleteWorkStageTable();
        deleteWorkListTable();
        deleteAdditionalListTable();
    }



}
