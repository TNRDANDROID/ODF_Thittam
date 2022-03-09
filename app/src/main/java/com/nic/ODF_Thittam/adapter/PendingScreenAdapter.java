package com.nic.ODF_Thittam.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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
import com.nic.ODF_Thittam.activity.FullImageActivity;
import com.nic.ODF_Thittam.activity.PendingScreen;
import com.nic.ODF_Thittam.activity.WorkListScreen;
import com.nic.ODF_Thittam.constant.AppConstant;
import com.nic.ODF_Thittam.dataBase.dbData;
import com.nic.ODF_Thittam.databinding.AdapterVillageListBinding;
import com.nic.ODF_Thittam.databinding.PendingScreenAdapterBinding;
import com.nic.ODF_Thittam.model.ODF_Thittam;
import com.nic.ODF_Thittam.session.PrefManager;
import com.nic.ODF_Thittam.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class PendingScreenAdapter extends PagedListAdapter<ODF_Thittam,PendingScreenAdapter.MyViewHolder> implements Filterable {
    private List<ODF_Thittam> pendingListValues;
    private List<ODF_Thittam> pendingListFiltered;
    private String letter;
    private Context context;
    private ColorGenerator generator = ColorGenerator.MATERIAL;
    private dbData  dbData;
    private PrefManager prefManager;

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
    public PendingScreenAdapter(Context context, List<ODF_Thittam> pendingListValues) {
        super(DIFF_CALLBACK);
        this.context = context;
        prefManager = new PrefManager(context);
        dbData = new dbData(context);
        this.pendingListValues = pendingListValues;
        this.pendingListFiltered = pendingListValues;

    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private PendingScreenAdapterBinding pendingScreenAdapterBinding;

        public MyViewHolder(PendingScreenAdapterBinding Binding) {
            super(Binding.getRoot());
            pendingScreenAdapterBinding = Binding;
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(viewGroup.getContext());
        }
        PendingScreenAdapterBinding pendingScreenAdapterBinding =
                DataBindingUtil.inflate(layoutInflater, R.layout.pending_screen_adapter, viewGroup, false);
        return new MyViewHolder(pendingScreenAdapterBinding);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

      holder.pendingScreenAdapterBinding.workId.setText(String.valueOf(pendingListFiltered.get(position).getWorkId()));
        if(pendingListFiltered.get(position).getTypeOfWork().equalsIgnoreCase(AppConstant.ADDITIONAL_WORK)) {
            holder.pendingScreenAdapterBinding.cdView.setVisibility(View.VISIBLE);
            holder.pendingScreenAdapterBinding.cdWorkLayout.setVisibility(View.VISIBLE);
            holder.pendingScreenAdapterBinding.cdWorkNo.setText(String.valueOf(pendingListFiltered.get(position).getCdWorkNo()));
        }else{
            holder.pendingScreenAdapterBinding.cdView.setVisibility(View.GONE);
            holder.pendingScreenAdapterBinding.cdWorkLayout.setVisibility(View.GONE);
        }
        holder.pendingScreenAdapterBinding.stageName.setText(pendingListFiltered.get(position).getWorkStageName());

        final String dcode = pendingListFiltered.get(position).getDistictCode();
        final String bcode = pendingListFiltered.get(position).getBlockCode();
        final String pvcode = pendingListFiltered.get(position).getPvCode();
        final String work_id = String.valueOf(pendingListFiltered.get(position).getWorkId());
        final String type_of_work = pendingListFiltered.get(position).getTypeOfWork();
        final String cd_work_no = String.valueOf(pendingListFiltered.get(position).getCdWorkNo());
        final String work_type_flag_le = pendingListFiltered.get(position).getWorkTypeFlagLe();

        dbData.open();
        ArrayList<ODF_Thittam> imageCount = dbData.selectImage(dcode,bcode,pvcode,work_id,type_of_work,cd_work_no,work_type_flag_le);
        if(imageCount.size() > 0) {
            holder.pendingScreenAdapterBinding.viewOfflineImages.setVisibility(View.VISIBLE);
        }
        else {
            holder.pendingScreenAdapterBinding.viewOfflineImages.setVisibility(View.GONE);
        }

        holder.pendingScreenAdapterBinding.viewOfflineImages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                prefManager.setDistrictCode(dcode);
                prefManager.setBlockCode(bcode);
                prefManager.setPvCode(pvcode);
                viewOfflineImages(dcode,bcode,pvcode,work_id,cd_work_no,work_type_flag_le,type_of_work,"Offline");
            }
        });

        holder.pendingScreenAdapterBinding.upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Utils.isOnline()) {
                    ODF_Thittam realTimeValue = new ODF_Thittam();
                    realTimeValue.setDistictCode(dcode);
                    realTimeValue.setBlockCode(bcode);
                    realTimeValue.setPvCode(pvcode);
                    realTimeValue.setWorkId(Integer.valueOf(work_id));
                    prefManager.setDistrictCode(dcode);
                    prefManager.setBlockCode(bcode);
                    prefManager.setPvCode(pvcode);
                    prefManager.setDeleteWorkId(String.valueOf(work_id));
                    prefManager.setDeleteAdapterPosition(position);
                    ((PendingScreen) context).new toUploadTask().execute(realTimeValue);
                } else {
                    Activity activity = (Activity) context;
                    Utils.showAlert(activity, "Turn On Mobile Data To Synchronize!");
                }
            }
        });
    }

    public void removeSavedItem(int position) {
        pendingListFiltered.remove(position);
        notifyItemRemoved(position);
        notifyItemChanged(position, pendingListValues.size());
    }


    public void viewOfflineImages(String dcode,String bcode,String pvcode,String work_id,String cd_work_no,String work_type_flag_le,String type_of_work,String OnOffType) {
        Activity activity = (Activity) context;
        Intent intent = new Intent(context, FullImageActivity.class);
        intent.putExtra(AppConstant.WORK_ID,work_id);
        intent.putExtra(AppConstant.CD_WORK_NO,cd_work_no);
        intent.putExtra(AppConstant.WORK_TYPE_FLAG_LE,work_type_flag_le);
        intent.putExtra(AppConstant.DISTRICT_CODE,dcode);
        intent.putExtra(AppConstant.BLOCK_CODE,bcode);
        intent.putExtra(AppConstant.PV_CODE,pvcode);
        intent.putExtra("OnOffType",OnOffType);
        if(OnOffType.equalsIgnoreCase("Offline")){
            intent.putExtra(AppConstant.TYPE_OF_WORK,type_of_work);
            activity.startActivity(intent);
        }
        activity.overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
    }


    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    pendingListFiltered = pendingListValues;
                } else {
                    List<ODF_Thittam> filteredList = new ArrayList<>();
                    for (ODF_Thittam row : pendingListValues) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.getPvName().toLowerCase().contains(charString.toLowerCase()) || row.getPvName().toLowerCase().contains(charString.toUpperCase())) {
                            filteredList.add(row);
                        }
                    }

                    pendingListFiltered = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = pendingListFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                pendingListFiltered = (ArrayList<ODF_Thittam>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public void viewHousingWorks(int pos) {
        Activity activity = (Activity) context;
        Intent intent = new Intent(activity, WorkListScreen.class);
        intent.putExtra(AppConstant.PV_CODE,pendingListFiltered.get(pos).getPvCode());
        activity.startActivity(intent);
        activity.overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
    }

    @Override
    public int getItemCount() {
        return pendingListFiltered == null ? 0 : pendingListFiltered.size();
    }
}

