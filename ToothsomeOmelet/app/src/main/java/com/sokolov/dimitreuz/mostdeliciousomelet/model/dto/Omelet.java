package com.sokolov.dimitreuz.mostdeliciousomelet.model.DTO;

/**
 * Current interface is used to describe
 * omelet instance in several context, such as:
 * <ul>
 *     <li>Database</li>
 *     <li>Server API (Remote repository)</li>
 *     <li>Transfer object described below.</li>
 * </ul>
 *
 * Such approach was introduced to divide object`s
 * representation to several contexts described above.
 */
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

        public OmeletDTO(Omelet omelet) {
            setTitle(omelet.getTitle());
            setHref(omelet.getHref());
            setIngredients(omelet.getIngredients());
            setThumbnail(omelet.getThumbnail());
        }

        public OmeletDTO() {

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
