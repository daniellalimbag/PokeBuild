package com.pokebuild.model;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class ItemResponseList {
    @SerializedName("results")
    private List<Item> results;

    public List<Item> getResults() {
        return results;
    }

    public static class Item {
        @SerializedName("name")
        private String name;

        @SerializedName("url")
        private String url;

        public String getName() {
            return name;
        }

        public String getUrl() {
            return url;
        }
    }
}
