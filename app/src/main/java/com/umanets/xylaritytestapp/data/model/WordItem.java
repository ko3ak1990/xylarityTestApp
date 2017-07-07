package com.umanets.xylaritytestapp.data.model;


import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;

/**
 * Created by ko3ak_zhn on 7/7/17.
 */

@AutoValue
public abstract class WordItem {
    public abstract String name();
    public abstract String id();
    public abstract String url();
    public abstract String language();
    public abstract String country();


//    public static Builder builder() {
//        return new AutoValue_WordItem.Builder();
//    }

    public static TypeAdapter<WordItem> typeAdapter(Gson gson) {
        return new AutoValue_WordItem.GsonTypeAdapter(gson);
    }
//
//    @AutoValue.Builder
//    public abstract static class Builder {
//        public abstract Builder setName(String name);
//        public abstract Builder setId(String id);
//        public abstract Builder setLanguage(String language);
//        public abstract Builder setCountry(String country);
//        public abstract WordItem build();
//    }
}