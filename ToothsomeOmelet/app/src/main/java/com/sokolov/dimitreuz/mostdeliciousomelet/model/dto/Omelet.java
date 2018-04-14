package com.sokolov.dimitreuz.mostdeliciousomelet.model.dto;

public interface Omelet {

    String getTitle();

    String getHref();

    String getIngredients();

    String getThumbnail();

    void setTitle(String title);

    void setHref(String href);

    void setIngredients(String ingredients);

    void setThumbnail(String thumbnail);

    class OmeletDTO implements Omelet {

        private String mTitle;
        private String mHref;
        private String mIngredients;
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

}
