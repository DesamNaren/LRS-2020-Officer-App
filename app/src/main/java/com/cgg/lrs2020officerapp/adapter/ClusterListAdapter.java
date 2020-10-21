package com.cgg.lrs2020officerapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.cgg.lrs2020officerapp.R;
import com.cgg.lrs2020officerapp.application.LRSApplication;
import com.cgg.lrs2020officerapp.constants.AppConstants;
import com.cgg.lrs2020officerapp.databinding.ItemClusterListBinding;
import com.cgg.lrs2020officerapp.databinding.ItemViewApplicationListBinding;
import com.cgg.lrs2020officerapp.model.applicationList.ApplicationListData;
import com.cgg.lrs2020officerapp.model.applicationList.Cluster;
import com.cgg.lrs2020officerapp.ui.ApplicationDetailsActivity;
import com.cgg.lrs2020officerapp.ui.ApplicationListActivity;

import java.util.ArrayList;
import java.util.List;


public class ClusterListAdapter extends RecyclerView.Adapter<ClusterListAdapter.ItemHolder> {
    private Context context;
    private List<Cluster> list;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    public ClusterListAdapter(Context context, List<Cluster> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemClusterListBinding listItemBinding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.item_cluster_list, parent, false);
        return new ItemHolder(listItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull final ItemHolder holder, final int i) {
        final Cluster dataModel = list.get(i);
        holder.listItemBinding.setTaskData(dataModel);
        holder.bind(dataModel);


        holder.listItemBinding.llData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedPreferences = LRSApplication.get(context).getPreferences();
                editor = sharedPreferences.edit();
                editor.putString(AppConstants.SELECTED_CLUSTERID, dataModel.getCluster_id());
                editor.commit();

                Intent intent = new Intent(context, ApplicationListActivity.class);
                context.startActivity(intent);
            }
        });
    }

    class ItemHolder extends RecyclerView.ViewHolder {

        ItemClusterListBinding listItemBinding;

        ItemHolder(ItemClusterListBinding listItemBinding) {
            super(listItemBinding.getRoot());
            this.listItemBinding = listItemBinding;
        }

        void bind(Object obj) {
            listItemBinding.executePendingBindings();
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }


}
