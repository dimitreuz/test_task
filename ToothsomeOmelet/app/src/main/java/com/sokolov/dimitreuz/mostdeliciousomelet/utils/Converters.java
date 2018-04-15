package com.sokolov.dimitreuz.mostdeliciousomelet.utils;

import android.support.annotation.NonNull;

import com.sokolov.dimitreuz.mostdeliciousomelet.model.dto.Omelet;
import com.sokolov.dimitreuz.mostdeliciousomelet.model.dto.OmeletDB;

import java.util.ArrayList;
import java.util.List;

public class Converters {

    public static OmeletDB[] convertToLocalOmelets(@NonNull List<? extends Omelet> omelets) {
        OmeletDB[] localOmelets = new OmeletDB[omelets.size()];
        for (int i = 0; i < omelets.size(); i++) {
            localOmelets[i] = new OmeletDB(omelets.get(i));
        }
        return localOmelets;
    }

    public static List<Omelet.OmeletDTO> convertToCachedOmelets(@NonNull List<? extends Omelet> omelets) {
        List<Omelet.OmeletDTO> cachedOmelets = new ArrayList<>();
        for (Omelet omelet : omelets) {
            cachedOmelets.add(new Omelet.OmeletDTO(omelet));
        }
        return cachedOmelets;
    }

}
