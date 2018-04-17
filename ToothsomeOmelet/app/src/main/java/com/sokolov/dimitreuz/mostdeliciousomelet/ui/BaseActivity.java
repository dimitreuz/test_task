package com.sokolov.dimitreuz.mostdeliciousomelet.ui;

import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.databinding.BaseObservable;
import android.databinding.Observable;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.sokolov.dimitreuz.mostdeliciousomelet.R;
import com.sokolov.dimitreuz.mostdeliciousomelet.model.AppExecutors;
import com.sokolov.dimitreuz.mostdeliciousomelet.model.repository.OmeletRepository;
import com.sokolov.dimitreuz.mostdeliciousomelet.omelet.OmeletItemViewModel;
import com.sokolov.dimitreuz.mostdeliciousomelet.omelet.OmeletsListViewModel;

public abstract class BaseActivity<VM extends BaseObservable> extends AppCompatActivity {

    private VM mViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResId());
    }

    protected void addFragment(@NonNull Fragment fragment, @Nullable String name, boolean addToBackStack) {
        FragmentManager manager = getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction transaction  = manager.beginTransaction();
        transaction.add(getFragmentsViewHolderId(), fragment);
        if (addToBackStack) {
            transaction.addToBackStack(name);
        } else {
            transaction.disallowAddToBackStack();
        }
        transaction.commit();
    }

    @Override
    public Object onRetainCustomNonConfigurationInstance() {
        return mViewModel;
    }

    @NonNull
    public VM getViewModel() {
        VM model = (VM) getLastCustomNonConfigurationInstance();
        if (model == null) {
            model = obtainViewModel(getViewModelClass());
        }
        return model;
    }

    protected abstract Class<? extends Observable> getViewModelClass();

    protected void addFragment(Fragment fragment) {
        addFragment(fragment, null, false);
    }

    @IdRes
    protected int getFragmentsViewHolderId() {
        return R.id.fragments_holder;
    }

    @LayoutRes
    protected int getLayoutResId() {
        return R.layout.activity_base;
    }

    public <VB extends Observable> VB obtainViewModel(Class<? extends Observable> clazz) {
        AppExecutors appExecutors = new AppExecutors();
        OmeletRepository repository = OmeletRepository.getInstance(appExecutors, getApplicationContext());
        if (clazz.isAssignableFrom(OmeletItemViewModel.class)) {
            return (VB) new OmeletItemViewModel(repository);
        } else if(clazz.isAssignableFrom(OmeletsListViewModel.class)) {
            return (VB) new OmeletsListViewModel(repository);
        } else {
            throw new IllegalArgumentException("NOT SUPPORTED VIEW MODEL");
        }
    }
}
