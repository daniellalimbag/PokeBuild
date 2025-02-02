package com.pokebuild.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Pokemon implements Serializable {
    private int dexNum;
    private String name;
    private String url;
    private String sprite;
    private String type;
    private String ability;
    private List<String> moves;

    // Constructor
    public Pokemon(int dexNum, String name, String url, String sprite, String type, String ability, List<String> moves) {
        this.dexNum = dexNum;
        this.name = name;
        this.url = url;
        this.sprite = sprite;
        this.type = type;
        this.ability = ability;
        setMoves(moves); // Using setter to enforce the limit
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
            this.moves = new ArrayList<>(moves.subList(0, 4)); // Trim the list to the first 4 moves
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
}
