package com.pokebuild.model;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class PokemonDetailResponse {
    @SerializedName("abilities")
    private List<AbilityWrapper> abilities;

    @SerializedName("types")
    private List<TypeWrapper> types;

    public List<AbilityWrapper> getAbilities() {
        return abilities;
    }

    public List<TypeWrapper> getTypes() {
        return types;
    }

    public static class AbilityWrapper {
        @SerializedName("ability")
        private AbilityInfo ability;

        @SerializedName("is_hidden")
        private boolean isHidden;

        public AbilityInfo getAbility() {
            return ability;
        }

        public boolean isHidden() {
            return isHidden;
        }
    }

    public static class AbilityInfo {
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

    public static class TypeWrapper {
        @SerializedName("type")
        private TypeInfo type;

        public TypeInfo getType() {
            return type;
        }
    }

    public static class TypeInfo {
        @SerializedName("name")
        private String name;

        public String getName() {
            return name;
        }
    }
}
