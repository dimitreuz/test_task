package com.sokolov.dimitreuz.mostdeliciousomelet.model.DTO;

import com.google.gson.annotations.SerializedName;

public class OmeletAPI extends AbstractOmelet {

    @SerializedName("title")
    private String mTitle;
    @SerializedName("href")
    private String mHref;
    @SerializedName("ingredients")
    private String mIngredients;
    @SerializedName("thumbnail")
    private String mThumbnail;

    @Override
    public String getTitle() {
        return mTitle;
    }

    @Override
    public String getHref() {
        return mHref;
    }

    @Override
    public String getIngredients() {
        return mIngredients;
    }

    @Override
    public String getThumbnail() {
        return mThumbnail;
    }

    @Override
    public void setTitle(String title) {
        this.mTitle = title;
    }

    @Override
    public void setHref(String href) {
        this.mHref = href;
    }

    @Override
    public void setIngredients(String ingredients) {
        this.mIngredients = ingredients;
    }

    @Override
    public void setThumbnail(String thumbnail) {
        this.mThumbnail = thumbnail;
    }
}
