package com.sokolov.dimitreuz.mostdeliciousomelet.model.DTO;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "omelet")
public class OmeletDB extends AbstractOmelet {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "recipeId")
    private int mId;
    @ColumnInfo(name = "title")
    private String mTitle;
    @ColumnInfo(name = "href")
    private String mHref;
    @ColumnInfo(name = "ingredients")
    private String mIngredients;
    @ColumnInfo(name = "thumbnail")
    private String mThumbnail;

    public OmeletDB() {
        super();
    }

    public OmeletDB(Omelet omelet) {
        super(omelet);
    }

    public int getId() {
        return mId;
    }

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

    public void setId(int id) {
        this.mId = id;
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
