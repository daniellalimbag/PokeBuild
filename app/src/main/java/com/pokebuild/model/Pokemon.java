package com.pokebuild.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

public class Pokemon implements Serializable {
    private int dexNum;
    private String name;
    private String url;
    private String sprite;
    private String type;
    private String ability;
    private List<String> moves;
    private String item;

    // Constructor
    public Pokemon(int dexNum, String name, String url, String sprite, String type, String ability, String moves, String item) {
        this.dexNum = dexNum;
        this.name = name;
        this.url = url;
        this.sprite = sprite;
        this.type = type;
        this.ability = ability;
        this.moves = parseMovesString(moves); // Parse the moves string into a list
        this.item = item;
    }

    private List<String> parseMovesString(String movesString) {
        if (movesString != null && !movesString.isEmpty()) {
            return Arrays.asList(movesString.split(",\\s*")); // Split by comma and optional spaces
        }
        return new ArrayList<>();
    }

    // Getters and setters for all fields
    public int getDexNum() {
        return dexNum;
    }

    public void setDexNum(int dexNum) {
        this.dexNum = dexNum;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getSprite() {
        return sprite;
    }

    public void setSprite(String sprite) {
        this.sprite = sprite;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAbility() {
        return ability;
    }

    public void setAbility(String ability) {
        this.ability = ability;
    }

    public List<String> getMoves() {
        return moves;
    }

    public void setMoves(List<String> moves) {
        if (moves != null && moves.size() > 4) {
            this.moves = new ArrayList<>(moves.subList(0, 4));
        } else {
            this.moves = moves;
        }
    }

    public void addMove(String move) {
        if (this.moves == null) {
            this.moves = new ArrayList<>();
        }
        if (this.moves.size() < 4) {
            this.moves.add(move);
        } else {
            System.out.println("A Pokémon can only have up to 4 moves.");
        }
    }

    public String getItemName() {
        return item;
    }

    public void setItemName(String itemName) {
        this.item = itemName;
    }
}
