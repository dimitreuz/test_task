package com.sokolov.dimitreuz.mostdeliciousomelet.omelet;

import android.content.Context;
import android.databinding.Observable;
import android.databinding.ObservableField;

import com.sokolov.dimitreuz.mostdeliciousomelet.model.DTO.Omelet;
import com.sokolov.dimitreuz.mostdeliciousomelet.model.repository.OmeletRepository;

import java.lang.ref.WeakReference;

public class OmeletItemViewModel {

    public final ObservableField<String> title = new ObservableField<>();

    public final ObservableField<String> href = new ObservableField<>();

    public final ObservableField<String> ingredients = new ObservableField<>();

    public final ObservableField<String> thumbnail = new ObservableField<>();

    private final ObservableField<Omelet.OmeletDTO> mOmelet = new ObservableField<>();

    private Context mContext;

    private WeakReference<OmeletNavigator> mNavigator;

    public OmeletItemViewModel(Context context) {
        this.mContext = context;
        this.mOmelet.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                Omelet omelet = mOmelet.get();
                if (omelet != null) {
                    title.set(omelet.getTitle());
                    href.set(omelet.getHref());
                    ingredients.set(omelet.getIngredients());
                    thumbnail.set(omelet.getThumbnail());
                }
            }
        });
    }

    public void setOmelet(Omelet.OmeletDTO omelet) {
        this.mOmelet.set(omelet);
    }

    public void setNavigator(OmeletNavigator navigator) {
        this.mNavigator = new WeakReference<>(navigator);
    }

    public void onOmeletClicked() {
        String currentLink = href.get();

        if (currentLink != null && mNavigator != null) {
            OmeletNavigator navigator = mNavigator.get();
            if (navigator != null) {
                navigator.openOmeletRecipe(currentLink);
            }
        }
    }
}
