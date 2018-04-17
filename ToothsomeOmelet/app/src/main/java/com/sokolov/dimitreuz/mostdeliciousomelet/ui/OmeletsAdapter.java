package com.sokolov.dimitreuz.mostdeliciousomelet.ui;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.databinding.Observable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.sokolov.dimitreuz.mostdeliciousomelet.R;
import com.sokolov.dimitreuz.mostdeliciousomelet.databinding.ItemOmeletBinding;
import com.sokolov.dimitreuz.mostdeliciousomelet.model.DTO.Omelet;
import com.sokolov.dimitreuz.mostdeliciousomelet.omelet.OmeletItemViewModel;
import com.sokolov.dimitreuz.mostdeliciousomelet.omelet.OmeletNavigator;
import com.sokolov.dimitreuz.mostdeliciousomelet.ui.list.AbstractRecyclerViewAdapter;

public class OmeletsAdapter extends AbstractRecyclerViewAdapter<Omelet.OmeletDTO, ItemOmeletBinding> {

    @NonNull
    private OmeletNavigator mNavigator;

    public OmeletsAdapter(@NonNull OmeletNavigator navigator) {
        super();
        this.mNavigator = navigator;
    }

    public void clearNavigators() {
        mNavigator = null;
    }

    private static final int GLIDE_CONNECTION_TIMEOUT = 3000;

    @Override
    public void bind(ItemOmeletBinding binding, Omelet.OmeletDTO omelet) {
        Context context = binding.getRoot().getContext();
        if (context instanceof ContextWrapper) {
            if (context instanceof Activity) {
                BaseActivity<?> activity = (BaseActivity<?>) context;

                OmeletItemViewModel viewModel = activity.obtainViewModel(OmeletItemViewModel.class);
                viewModel.setOmelet(omelet);

                String thumbnail = omelet.getThumbnail();
                if (thumbnail != null) {
                    Glide.with(context)
                            .load(thumbnail)
                            .apply(RequestOptions.circleCropTransform()
                                    .timeout(GLIDE_CONNECTION_TIMEOUT)
                                    .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                                    .fallback(R.drawable.ic_photo_camera_black_48dp)
                                    .placeholder(R.drawable.ic_photo_camera_black_48dp)
                            ).into(binding.thumbnailImageView);
                }
                viewModel.setNavigator(mNavigator);
                binding.setOmeletViewModel(viewModel);
            }
        }
    }

    @Override
    public void unbind(ItemOmeletBinding binding) {
        Glide.get(binding.getRoot().getContext()).clearMemory();
    }

    @Override
    public int getLayoutResId() {
        return R.layout.item_omelet;
    }
}
