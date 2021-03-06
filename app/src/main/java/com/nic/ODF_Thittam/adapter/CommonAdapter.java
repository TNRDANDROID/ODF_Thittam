package com.nic.ODF_Thittam.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.nic.ODF_Thittam.R;
import com.nic.ODF_Thittam.model.ODF_Thittam;

import java.util.List;

/**
 * Created by shanmugapriyan on 25/05/16.
 */
public class CommonAdapter extends BaseAdapter {
    private List<ODF_Thittam> ODF_Thittams;
    private Context mContext;
    private String type;


    public CommonAdapter(Context mContext, List<ODF_Thittam> ODF_Thittams, String type) {
        this.ODF_Thittams = ODF_Thittams;
        this.mContext = mContext;
        this.type = type;
    }


    public int getCount() {
        return ODF_Thittams.size();
    }


    public Object getItem(int position) {
        return position;
    }


    public long getItemId(int position) {
        return position;
    }


    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        View view = inflater.inflate(R.layout.spinner_drop_down_item, parent, false);
//        TextView tv_type = (TextView) view.findViewById(R.id.tv_spinner_item);
        View view = inflater.inflate(R.layout.spinner_value, parent, false);
        TextView tv_type = (TextView) view.findViewById(R.id.spinner_list_value);
        ODF_Thittam ODF_Thittam = ODF_Thittams.get(position);

        if (type.equalsIgnoreCase("SchemeList")) {
            tv_type.setText(ODF_Thittam.getSchemeName());
        } else if (type.equalsIgnoreCase("FinYearList")) {
            tv_type.setText(ODF_Thittam.getFinancialYear());
        } else if (type.equalsIgnoreCase("StageList")) {
            tv_type.setText(ODF_Thittam.getWorkStageName());
        }else if (type.equalsIgnoreCase("GenderList")) {
            tv_type.setText(ODF_Thittam.getGenderEn());
        }else if (type.equalsIgnoreCase("EducationList")) {
            tv_type.setText(ODF_Thittam.getEducationName());
        }else if (type.equalsIgnoreCase("CategoryList")) {
            tv_type.setText(ODF_Thittam.getMotivatorCategoryName());
        } if (type.equalsIgnoreCase("DistrictList")) {
            tv_type.setText(ODF_Thittam.getDistrictName());
        } else if (type.equalsIgnoreCase("BlockList")) {
            tv_type.setText(ODF_Thittam.getBlockName());
        }else if (type.equalsIgnoreCase("VillageList")) {
            tv_type.setText(ODF_Thittam.getPvName());
        }
        return view;
    }
}
