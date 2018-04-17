package com.sokolov.dimitreuz.mostdeliciousomelet.model.api;

import com.google.gson.annotations.SerializedName;
import com.sokolov.dimitreuz.mostdeliciousomelet.model.DTO.OmeletAPI;
import java.util.List;

public class OmeletsAPIHolder {

    @SerializedName("results")
    private List<OmeletAPI> mRequiredOmelets;

    public List<OmeletAPI> getRequiredOmelets() {
        return mRequiredOmelets;
    }

    public void setRequiredOmelets(List<OmeletAPI> mRequiredOmelets) {
        this.mRequiredOmelets = mRequiredOmelets;
    }
}
