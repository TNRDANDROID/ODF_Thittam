package com.nic.ODF_Thittam.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.databinding.DataBindingUtil;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.nic.ODF_Thittam.R;
import com.nic.ODF_Thittam.activity.AdditionalWorkScreen;
import com.nic.ODF_Thittam.activity.CameraScreen;
import com.nic.ODF_Thittam.activity.FullImageActivity;
import com.nic.ODF_Thittam.constant.AppConstant;
import com.nic.ODF_Thittam.dataBase.DBHelper;
import com.nic.ODF_Thittam.dataBase.dbData;
import com.nic.ODF_Thittam.databinding.AdapterWorkListBinding;
import com.nic.ODF_Thittam.model.ODF_Thittam;
import com.nic.ODF_Thittam.session.PrefManager;
import com.nic.ODF_Thittam.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class WorkListAdapter extends PagedListAdapter<ODF_Thittam,WorkListAdapter.MyViewHolder> implements Filterable {
    private List<ODF_Thittam> WorkListValues;
    private List<ODF_Thittam> WorkListValuesFiltered;
    private String letter;
    private Context context;
    private ColorGenerator generator = ColorGenerator.MATERIAL;
    public final String dcode,bcode,pvcode;
    PrefManager prefManager;
    private final dbData dbData;
    public static DBHelper dbHelper;
    public static SQLiteDatabase db;

    private LayoutInflater layoutInflater;
    private static DiffUtil.ItemCallback<ODF_Thittam> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<ODF_Thittam>() {
                @Override
                public boolean areItemsTheSame(ODF_Thittam oldItem, ODF_Thittam newItem) {
                    return oldItem.getId() == newItem.getId();
                }

                @SuppressLint("DiffUtilEquals")
                @Override
                public boolean areContentsTheSame(ODF_Thittam oldItem, ODF_Thittam newItem) {
                    return oldItem.equals(newItem);
                }
            };
    public WorkListAdapter(Context context, List<ODF_Thittam> WorkListValues,dbData dbData) {
        super(DIFF_CALLBACK);
        this.context = context;
        this.WorkListValues = WorkListValues;
        this.WorkListValuesFiltered = WorkListValues;
        this.dbData = dbData;
        prefManager = new PrefManager(context);
        dcode = prefManager.getDistrictCode();
        bcode = prefManager.getBlockCode();
        pvcode = prefManager.getPvCode();
        try {
            dbHelper = new DBHelper(context);
            db = dbHelper.getWritableDatabase();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private AdapterWorkListBinding adapterWorkListBinding;

        public MyViewHolder(AdapterWorkListBinding Binding) {
            super(Binding.getRoot());
            adapterWorkListBinding = Binding;
        }
    }

    @Override
    public WorkListAdapter.MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(viewGroup.getContext());
        }
        AdapterWorkListBinding adapterWorkListBinding =
                DataBindingUtil.inflate(layoutInflater, R.layout.adapter_work_list, viewGroup, false);
        return new WorkListAdapter.MyViewHolder(adapterWorkListBinding);
    }

    @Override
    public void onBindViewHolder(WorkListAdapter.MyViewHolder holder, final int position) {

        String beneficary_name = WorkListValuesFiltered.get(position).getBeneficiaryName();
        Log.d("beneficary_name",beneficary_name);

        if(!beneficary_name.isEmpty()) {
            beneficary_name = " (" + WorkListValuesFiltered.get(position).getBeneficiaryName() + ")";
        }
        else {
            beneficary_name = "";
        }

        holder.adapterWorkListBinding.workid.setText(WorkListValuesFiltered.get(position).getWorkId() + beneficary_name);
        if(WorkListValuesFiltered.get(position).getSchemeGroupName().isEmpty()){
            holder.adapterWorkListBinding.tvSchemeGroupNameLayout.setVisibility(View.GONE);
        }else {
            holder.adapterWorkListBinding.tvSchemeGroupNameLayout.setVisibility(View.VISIBLE);
            holder.adapterWorkListBinding.tvSchemeGroupName.setText(WorkListValuesFiltered.get(position).getSchemeGroupName());
        }

        if(WorkListValuesFiltered.get(position).getSchemeName().isEmpty()){
            holder.adapterWorkListBinding.tvSchemeLayout.setVisibility(View.GONE);
        }else {
            holder.adapterWorkListBinding.tvSchemeLayout.setVisibility(View.VISIBLE);
            holder.adapterWorkListBinding.tvScheme.setText(WorkListValuesFiltered.get(position).getSchemeName());
        }

        if(WorkListValuesFiltered.get(position).getFinancialYear().isEmpty()){
            holder.adapterWorkListBinding.tvFinancialYearLayout.setVisibility(View.GONE);
        }else {
            holder.adapterWorkListBinding.tvFinancialYearLayout.setVisibility(View.VISIBLE);
            holder.adapterWorkListBinding.tvFinancialYear.setText(WorkListValuesFiltered.get(position).getFinancialYear());
        }

        if(WorkListValuesFiltered.get(position).getAgencyName().isEmpty()){
            holder.adapterWorkListBinding.tvAgencyNameLayout.setVisibility(View.GONE);
        }else {
            holder.adapterWorkListBinding.tvAgencyNameLayout.setVisibility(View.VISIBLE);
            holder.adapterWorkListBinding.tvAgencyName.setText(WorkListValuesFiltered.get(position).getAgencyName());
        }

        if(WorkListValuesFiltered.get(position).getWorkGroupNmae().isEmpty()){
            holder.adapterWorkListBinding.tvWorkGroupNameLayout.setVisibility(View.GONE);
        }else {
            holder.adapterWorkListBinding.tvWorkGroupNameLayout.setVisibility(View.VISIBLE);
            holder.adapterWorkListBinding.tvWorkGroupName.setText(WorkListValuesFiltered.get(position).getWorkGroupNmae());
        }

        if(WorkListValuesFiltered.get(position).getWorkName().isEmpty()){
            holder.adapterWorkListBinding.tvWorkNameLayout.setVisibility(View.GONE);
        }else {
            holder.adapterWorkListBinding.tvWorkNameLayout.setVisibility(View.VISIBLE);
            holder.adapterWorkListBinding.tvWorkName.setText(WorkListValuesFiltered.get(position).getWorkName());
        }

        if(WorkListValuesFiltered.get(position).getBlockName().isEmpty()){
            holder.adapterWorkListBinding.tvBlockLayout.setVisibility(View.GONE);
        }else {
            holder.adapterWorkListBinding.tvBlockLayout.setVisibility(View.VISIBLE);
            holder.adapterWorkListBinding.tvBlock.setText(WorkListValuesFiltered.get(position).getBlockName());
        }

        if(WorkListValuesFiltered.get(position).getPvName().isEmpty()){
            holder.adapterWorkListBinding.tvVillageLayout.setVisibility(View.GONE);
        }else {
            holder.adapterWorkListBinding.tvVillageLayout.setVisibility(View.VISIBLE);
            holder.adapterWorkListBinding.tvVillage.setText(WorkListValuesFiltered.get(position).getPvName());
        }

        if(WorkListValuesFiltered.get(position).getStageName().isEmpty()){
            holder.adapterWorkListBinding.tvCurrentStageLayout.setVisibility(View.GONE);
        }else {
            holder.adapterWorkListBinding.tvCurrentStageLayout.setVisibility(View.VISIBLE);
            holder.adapterWorkListBinding.tvCurrentStage.setText(WorkListValuesFiltered.get(position).getStageName());
        }

        if(WorkListValuesFiltered.get(position).getBeneficiaryFatherName().isEmpty()){
            holder.adapterWorkListBinding.tvBeneficiaryFatherNameLayout.setVisibility(View.GONE);
        }else {
            holder.adapterWorkListBinding.tvBeneficiaryFatherNameLayout.setVisibility(View.VISIBLE);
            holder.adapterWorkListBinding.tvBeneficiaryFatherName.setText(WorkListValuesFiltered.get(position).getBeneficiaryFatherName());
        }

        if(WorkListValuesFiltered.get(position).getGender().isEmpty()){
            holder.adapterWorkListBinding.tvGenderLayout.setVisibility(View.GONE);
        }else {
            holder.adapterWorkListBinding.tvGenderLayout.setVisibility(View.VISIBLE);
            holder.adapterWorkListBinding.tvGender.setText(WorkListValuesFiltered.get(position).getGender());
        }

        if(WorkListValuesFiltered.get(position).getCommunity().isEmpty()){
            holder.adapterWorkListBinding.tvCommunityLayout.setVisibility(View.GONE);
        }else {
            holder.adapterWorkListBinding.tvCommunityLayout.setVisibility(View.VISIBLE);
            holder.adapterWorkListBinding.tvCommunity.setText(WorkListValuesFiltered.get(position).getCommunity());
        }

        if(WorkListValuesFiltered.get(position).getIntialAmount() == null){
            holder.adapterWorkListBinding.tvInitialAmountLayout.setVisibility(View.GONE);
        }else {
            holder.adapterWorkListBinding.tvInitialAmountLayout.setVisibility(View.VISIBLE);
            holder.adapterWorkListBinding.tvInitialAmount.setText(WorkListValuesFiltered.get(position).getIntialAmount());
        }

        if(WorkListValuesFiltered.get(position).getAmountSpendSoFar().isEmpty()){
            holder.adapterWorkListBinding.tvAmountSpentSoFarLayout.setVisibility(View.GONE);
        }else {
            holder.adapterWorkListBinding.tvAmountSpentSoFarLayout.setVisibility(View.VISIBLE);
            holder.adapterWorkListBinding.tvAmountSpentSoFar.setText(WorkListValuesFiltered.get(position).getAmountSpendSoFar());
        }

        if(WorkListValuesFiltered.get(position).getLastVisitedDate().isEmpty()){
            holder.adapterWorkListBinding.tvLastVisitedDateLayout.setVisibility(View.GONE);
        }else {
            holder.adapterWorkListBinding.tvLastVisitedDateLayout.setVisibility(View.VISIBLE);
            holder.adapterWorkListBinding.tvLastVisitedDate.setText(Utils.formatDateReverse(WorkListValuesFiltered.get(position).getLastVisitedDate()));

        }


        if(WorkListValuesFiltered.get(position).getCdProtWorkYn().equalsIgnoreCase("Y")){
            holder.adapterWorkListBinding.viewAdditionalWorks.setVisibility(View.VISIBLE);
        }else {
            holder.adapterWorkListBinding.viewAdditionalWorks.setVisibility(View.GONE);
        }


        holder.adapterWorkListBinding.takePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openCameraScreen(position);
            }
        });

        holder.adapterWorkListBinding.viewAdditionalWorks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openAdditionalWorkList(position);
            }
        });

        final String work_id = String.valueOf(WorkListValuesFiltered.get(position).getWorkId());

        ArrayList<ODF_Thittam> imageOffline = dbData.selectImage(dcode,bcode,pvcode,work_id,AppConstant.MAIN_WORK,"","");

        if(imageOffline.size() > 0) {
            holder.adapterWorkListBinding.viewOfflineImage.setVisibility(View.VISIBLE);
        }
        else {
            holder.adapterWorkListBinding.viewOfflineImage.setVisibility(View.GONE);
        }

        holder.adapterWorkListBinding.viewOfflineImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewOfflineImages(work_id,AppConstant.MAIN_WORK,"Offline","","","");
            }
        });
        if (WorkListValuesFiltered.get(position).getImageAvailable().equalsIgnoreCase("Y")) {
            holder.adapterWorkListBinding.viewOnlineImage.setVisibility(View.VISIBLE);
        }
        else if(WorkListValuesFiltered.get(position).getImageAvailable().equalsIgnoreCase("N")){
            holder.adapterWorkListBinding.viewOnlineImage.setVisibility(View.GONE);
        }
        holder.adapterWorkListBinding.viewOnlineImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewOfflineImages(work_id,AppConstant.MAIN_WORK,"Online",dcode,bcode,pvcode);
            }
        });



        String workGroupId = String.valueOf(WorkListValuesFiltered.get(position).getWorkGroupID());
        String workTypeid = String.valueOf(WorkListValuesFiltered.get(position).getWorkTypeID());
        String currentStageCode = String.valueOf(WorkListValuesFiltered.get(position).getCurrentStage());

        String sql = "select * from "+ DBHelper.WORK_STAGE_TABLE+" where work_stage_order >(select work_stage_order from "+DBHelper.WORK_STAGE_TABLE+" where work_stage_code='"+currentStageCode+"' and work_group_id=" + workGroupId + "  and work_type_id=" + workTypeid + ")  and work_group_id=" + workGroupId + "  and work_type_id=" + workTypeid + " and work_stage_code != 11 order by work_stage_order";
        Log.d("que",sql);
        Cursor Stage = db.rawQuery(sql, null);

        if(Stage.getCount() > 0 ){
            holder.adapterWorkListBinding.takePhoto.setVisibility(View.VISIBLE);
        }
        else {
            holder.adapterWorkListBinding.takePhoto.setVisibility(View.GONE);
        }
    }


    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    WorkListValuesFiltered = WorkListValues;
                } else {
                    List<ODF_Thittam> filteredList = new ArrayList<>();
                    for (ODF_Thittam row : WorkListValues) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.getWorkId().toString().toLowerCase().contains(charString.toLowerCase()) || row.getBeneficiaryName().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(row);
                        }
                    }

                    WorkListValuesFiltered = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = WorkListValuesFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                WorkListValuesFiltered = (ArrayList<ODF_Thittam>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public void openCameraScreen(int pos) {
        Activity activity = (Activity) context;
        Intent intent = new Intent(activity, CameraScreen.class);
        intent.putExtra(AppConstant.TYPE_OF_WORK,AppConstant.MAIN_WORK);
        intent.putExtra(AppConstant.WORK_ID,String.valueOf(WorkListValuesFiltered.get(pos).getWorkId()));
        intent.putExtra(AppConstant.WORK_GROUP_ID,WorkListValuesFiltered.get(pos).getWorkGroupID());
        intent.putExtra(AppConstant.WORK_TYPE_ID,WorkListValuesFiltered.get(pos).getWorkTypeID());
        intent.putExtra(AppConstant.CURRENT_STAGE_OF_WORK,String.valueOf(WorkListValuesFiltered.get(pos).getCurrentStage()));
        activity.startActivity(intent);
        activity.overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
    }

    public void openAdditionalWorkList(int pos) {
        Activity activity = (Activity) context;
        Intent intent = new Intent(activity, AdditionalWorkScreen.class);
        intent.putExtra(AppConstant.WORK_ID,String.valueOf(WorkListValuesFiltered.get(pos).getWorkId()));
        activity.startActivity(intent);
        activity.overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
    }

    @Override
    public int getItemCount() {
        return WorkListValuesFiltered == null ? 0 : WorkListValuesFiltered.size();
    }

    public void viewOfflineImages(String work_id,String type_of_work,String OnOffType,String dcode,String bcode,String pvcode) {
        Activity activity = (Activity) context;
        Intent intent = new Intent(context, FullImageActivity.class);
        intent.putExtra(AppConstant.WORK_ID,work_id);
        intent.putExtra("OnOffType",OnOffType);

        if(OnOffType.equalsIgnoreCase("Offline")){
            intent.putExtra(AppConstant.TYPE_OF_WORK,type_of_work);
            activity.startActivity(intent);
        }
        else if(OnOffType.equalsIgnoreCase("Online")) {
            if(Utils.isOnline()){
                intent.putExtra(AppConstant.DISTRICT_CODE,dcode);
                intent.putExtra(AppConstant.BLOCK_CODE,bcode);
                intent.putExtra(AppConstant.PV_CODE,pvcode);
                intent.putExtra(AppConstant.TYPE_OF_WORK,type_of_work);
                activity.startActivity(intent);
            }else {
                Utils.showAlert(activity,"Your Internet seems to be Offline.Images can be viewed only in Online mode.");
            }
        }


        activity.overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
    }
}

