package com.cgg.lrs2020officerapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.cgg.lrs2020officerapp.R;
import com.cgg.lrs2020officerapp.application.LRSApplication;
import com.cgg.lrs2020officerapp.constants.AppConstants;
import com.cgg.lrs2020officerapp.databinding.ItemViewBinding;
import com.cgg.lrs2020officerapp.model.applicationList.ApplicationListData;
import com.cgg.lrs2020officerapp.ui.LayoutActivity;

import java.util.ArrayList;
import java.util.List;


public class ViewTaskAdapter extends RecyclerView.Adapter<ViewTaskAdapter.ItemHolder>
        implements Filterable {
    private Context context;
    private List<ApplicationListData> list;
    private List<ApplicationListData> mFilteredList;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    public ViewTaskAdapter(Context context, List<ApplicationListData> list) {
        this.context = context;
        this.list = list;
        mFilteredList = list;
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
        final ApplicationListData dataModel = mFilteredList.get(i);
        holder.listItemBinding.setTaskData(dataModel);
        holder.bind(dataModel);

        holder.listItemBinding.llData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedPreferences = LRSApplication.get(context).getPreferences();
                editor = sharedPreferences.edit();
                editor.putString(AppConstants.APPLICATION_ID, dataModel.getAPPLICATIONID());
                editor.putString(AppConstants.APPLICANT_NAME, dataModel.getNAME());
                editor.commit();

                Intent intent = new Intent(context, LayoutActivity.class);
//                intent.putExtra(AppConstants.APPLICATION_ID, dataModel.getAPPLICATIONID());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mFilteredList != null && mFilteredList.size() > 0 ? mFilteredList.size() : 0;
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

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    mFilteredList = list;
                } else {
                    try {
                        ArrayList<ApplicationListData> filteredList = new ArrayList<>();
                        for (ApplicationListData detailsData : list) {
                            if (!TextUtils.isEmpty(detailsData.getAPPLICATIONID())
                                    && detailsData.getAPPLICATIONID().toLowerCase().contains(charString.toLowerCase())
                                    ||
                                    !TextUtils.isEmpty(detailsData.getNAME()) &&
                                            detailsData.getNAME().toLowerCase().contains(charString.toLowerCase())) {
                                filteredList.add(detailsData);
                            }
                        }
                        mFilteredList = filteredList;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = mFilteredList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                mFilteredList = (ArrayList<ApplicationListData>) filterResults.values;
                notifyDataSetChanged();

                getFilteredData();
            }
        };
    }

    public List<ApplicationListData> getFilteredData() {
        return mFilteredList;
    }


}
