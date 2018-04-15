package com.sokolov.dimitreuz.mostdeliciousomelet.model.DTO;

public abstract class AbstractOmelet implements Omelet {

    public AbstractOmelet(Omelet omelet) {
        setTitle(omelet.getTitle());
        setHref(omelet.getHref());
        setIngredients(omelet.getIngredients());
        setThumbnail(omelet.getThumbnail());
    }

    public AbstractOmelet() {
        this(new OmeletDTO());
    }
}
