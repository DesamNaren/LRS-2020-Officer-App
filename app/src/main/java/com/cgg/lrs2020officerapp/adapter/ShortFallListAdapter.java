package com.cgg.lrs2020officerapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.cgg.lrs2020officerapp.R;
import com.cgg.lrs2020officerapp.application.LRSApplication;
import com.cgg.lrs2020officerapp.constants.AppConstants;
import com.cgg.lrs2020officerapp.databinding.ItemClusterListBinding;
import com.cgg.lrs2020officerapp.databinding.ItemShortfallListBinding;
import com.cgg.lrs2020officerapp.model.applicationList.Cluster;
import com.cgg.lrs2020officerapp.model.shortfall.ShortfallList;
import com.cgg.lrs2020officerapp.ui.ApplicationListActivity;

import java.util.List;


public class ShortFallListAdapter extends RecyclerView.Adapter<ShortFallListAdapter.ItemHolder> {
    private Context context;
    private List<ShortfallList> list;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    public ShortFallListAdapter(Context context, List<ShortfallList> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemShortfallListBinding listItemBinding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.item_shortfall_list, parent, false);
        return new ItemHolder(listItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull final ItemHolder holder, final int i) {
        final ShortfallList dataModel = list.get(i);
        holder.listItemBinding.setShortfall(dataModel);
        holder.bind(dataModel);
    }

    class ItemHolder extends RecyclerView.ViewHolder {

        ItemShortfallListBinding listItemBinding;

        ItemHolder(ItemShortfallListBinding listItemBinding) {
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
