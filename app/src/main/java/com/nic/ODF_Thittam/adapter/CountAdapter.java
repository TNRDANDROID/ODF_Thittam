package com.nic.ODF_Thittam.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.nic.ODF_Thittam.R;
import com.nic.ODF_Thittam.activity.CameraScreen;


public class CountAdapter extends RecyclerView.Adapter<CountAdapter.MyViewHolder>{

    Context context;
    int count_size,mini_size;
    int count=0;

    public CountAdapter(Context context, int count_size, int mini_size) {
        this.context=context;
        this.count_size=count_size;
        this.mini_size=mini_size;
        this.count=mini_size;
    }

    @Override
    public CountAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.counter_adapter_item, parent, false);
        return new MyViewHolder(itemView);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView spinner_list_value;



        public MyViewHolder(View itemView) {
            super(itemView);
            spinner_list_value = itemView.findViewById(R.id.spinner_list_value);
        }


        @Override
        public void onClick(View v) {
            switch (v.getId()) {

            }
        }
    }

    @Override
    public void onBindViewHolder(final CountAdapter.MyViewHolder holder, final int position) {
        holder.spinner_list_value.setText(""+count);
        count=count+1;

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((CameraScreen)context).onClickedItems(holder.spinner_list_value.getText().toString());
            }
        });

    }


    @Override
    public int getItemCount() {
        return count_size;
    }

}

