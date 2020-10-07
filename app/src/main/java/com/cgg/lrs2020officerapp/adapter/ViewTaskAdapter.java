package com.cgg.lrs2020officerapp.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.cgg.lrs2020officerapp.R;
import com.cgg.lrs2020officerapp.application.LRSApplication;
import com.cgg.lrs2020officerapp.databinding.ItemViewBinding;
import com.cgg.lrs2020officerapp.model.applicationList.ApplicationListData;
import com.google.gson.Gson;

import java.util.List;


public class ViewTaskAdapter extends RecyclerView.Adapter<ViewTaskAdapter.ItemHolder>  {
    private Context context;
    private List<ApplicationListData> list;


    public ViewTaskAdapter(Context context, List<ApplicationListData> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemViewBinding listItemBinding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.item_view, parent, false);
        return new ItemHolder(listItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull final ItemHolder holder, final int i) {
        final ApplicationListData dataModel = list.get(i);
        holder.listItemBinding.setTaskData(dataModel);
        holder.bind(dataModel);
    }

    @Override
    public int getItemCount() {
        return list != null && list.size() > 0 ? list.size() : 0;
    }

    class ItemHolder extends RecyclerView.ViewHolder {

        ItemViewBinding listItemBinding;

        ItemHolder(ItemViewBinding listItemBinding) {
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
    public int getItemViewType(int position) {
        return position;
    }

}
