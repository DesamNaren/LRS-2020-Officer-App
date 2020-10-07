package com.cgg.lrs2020officerapp.viewmodel;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.cgg.lrs2020officerapp.databinding.ActivityListBinding;


public class AddScrutinyCustomViewModel implements ViewModelProvider.Factory {
    private ActivityListBinding binding;
    private Context context;

    public AddScrutinyCustomViewModel(ActivityListBinding binding, Context context) {
        this.binding = binding;
        this.context = context;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new ApplicationListViewModel( context);
    }
}
