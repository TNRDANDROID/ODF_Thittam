package com.nic.ODF_Thittam.adapter;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.nic.ODF_Thittam.R;
import com.nic.ODF_Thittam.dataBase.DBHelper;
import com.nic.ODF_Thittam.dataBase.dbData;
import com.nic.ODF_Thittam.databinding.WorkingAreaViewItemBinding;
import com.nic.ODF_Thittam.model.ODF_Thittam;

import java.util.ArrayList;
import java.util.List;

public class WorkingAreaListAdapter extends RecyclerView.Adapter<WorkingAreaListAdapter.MyViewHolder> {
    private LayoutInflater layoutInflater;
    private List<ODF_Thittam> work_area_list;
    Context context;

    public WorkingAreaListAdapter(Context context,List<ODF_Thittam> work_area_list) {
        this.work_area_list = work_area_list;
        this.context = context;

    }

    @NonNull
    @Override
    public WorkingAreaListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(viewGroup.getContext());
        }
        WorkingAreaViewItemBinding workingAreaViewItemBinding =
                DataBindingUtil.inflate(layoutInflater, R.layout.working_area_view_item, viewGroup, false);
        return new MyViewHolder(workingAreaViewItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull final WorkingAreaListAdapter.MyViewHolder holder, final int position) {

        holder.workingAreaViewItemBinding.districtName.setText("District Name : "+work_area_list.get(position).getDistrictName());
        holder.workingAreaViewItemBinding.blockName.setText("Block  Name      : "+work_area_list.get(position).getBlockName());
        holder.workingAreaViewItemBinding.pvName.setText("Village  Name    : "+work_area_list.get(position).getPvName());
    }

    public ArrayList<ODF_Thittam> getWorkingAreaList(){
        return (ArrayList<ODF_Thittam>) work_area_list;
    }

    @Override
    public int getItemCount() {
        return work_area_list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        private WorkingAreaViewItemBinding workingAreaViewItemBinding;

        public MyViewHolder(WorkingAreaViewItemBinding Binding) {
            super(Binding.getRoot());
            workingAreaViewItemBinding = Binding;
        }
    }
}

