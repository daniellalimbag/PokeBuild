package com.pokebuild.database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.pokebuild.model.OwnedPokemon;
import com.pokebuild.model.Team;
import com.pokebuild.model.Teams;

import java.util.ArrayList;
import java.util.List;

public class DatabaseManager {
    private final MyDatabaseHelper dbHelper;

    public DatabaseManager(Context context) {
        dbHelper = new MyDatabaseHelper(context);
    }

    public Teams getAllTeams() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Teams teams = new Teams();

        String query = "SELECT * FROM " + MyDatabaseHelper.TEAM_TABLE;
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") String teamName = cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.TEAM_NAME));
                @SuppressLint("Range") String pokemonIdsStr = cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.TEAM_POKEMON_IDS));
                List<OwnedPokemon> pokemonList = getPokemonsByIds(pokemonIdsStr);
                Team team = new Team();
                team.setName(teamName);
                team.setTeam(pokemonList);
                teams.getTeams().add(team);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return teams;
    }

    public Team getTeamById(int teamId) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Team team = null;

        String query = "SELECT * FROM " + MyDatabaseHelper.TEAM_TABLE + " WHERE " + MyDatabaseHelper.TEAM_ID + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(teamId)});

        if (cursor.moveToFirst()) {
            @SuppressLint("Range") String teamName = cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.TEAM_NAME));
            @SuppressLint("Range") String pokemonIdsStr = cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.TEAM_POKEMON_IDS));
            List<OwnedPokemon> pokemonList = getPokemonsByIds(pokemonIdsStr);
            team = new Team();
            team.setName(teamName);
            team.setTeam(pokemonList);
        }
        cursor.close();
        db.close();
        return team;
    }

    public Team getTeamByName(String teamName) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Team team = null;

        String query = "SELECT * FROM " + MyDatabaseHelper.TEAM_TABLE + " WHERE " + MyDatabaseHelper.TEAM_NAME + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{teamName});

        if (cursor.moveToFirst()) {
            @SuppressLint("Range") String pokemonIdsStr = cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.TEAM_POKEMON_IDS));
            List<OwnedPokemon> pokemonList = getPokemonsByIds(pokemonIdsStr);
            team = new Team();
            team.setName(teamName);
            team.setTeam(pokemonList);
        }
        cursor.close();
        db.close();
        return team;
    }

    private List<OwnedPokemon> getPokemonsByIds(String pokemonIdsStr) {
        List<OwnedPokemon> pokemonList = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] pokemonIds = pokemonIdsStr.replace("[", "").replace("]", "").split(",");
        for (String id : pokemonIds) {
            String query = "SELECT * FROM " + MyDatabaseHelper.POKEMON_TABLE + " WHERE " + MyDatabaseHelper.POKEMON_ID + " = ?";
            Cursor cursor = db.rawQuery(query, new String[]{id.trim()});

            if (cursor.moveToFirst()) {
                @SuppressLint("Range") int dexNum = cursor.getInt(cursor.getColumnIndex(MyDatabaseHelper.POKEMON_DEX_NUM));
                @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.POKEMON_NAME));
                @SuppressLint("Range") String url = cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.POKEMON_URL));
                @SuppressLint("Range") String sprite = cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.POKEMON_SPRITE));
                @SuppressLint("Range") String type = cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.POKEMON_TYPE));
                @SuppressLint("Range") String ability = cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.POKEMON_ABILITY));
                @SuppressLint("Range") String moves = cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.POKEMON_MOVES));
                @SuppressLint("Range") String item = cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.POKEMON_ITEM));

                OwnedPokemon pokemon = new OwnedPokemon(dexNum, name, url, sprite, type, ability, moves, item);
                pokemonList.add(pokemon);
            }
            cursor.close();
        }
        db.close();
        return pokemonList;
    }


    public List<OwnedPokemon> getPokemonsByTeamId(int teamId) {
        Team team = getTeamById(teamId);
        return team != null ? team.getTeam() : new ArrayList<>();
    }

    // Method to insert a new team into the database
    public void insertTeam(Team team) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try {
            ContentValues contentValues = new ContentValues();
            List<Integer> pokemonIds = new ArrayList<>();
            for (OwnedPokemon pokemon : team.getTeam()) {
                long pokemonId = insertPokemon(pokemon);
                if (pokemonId != -1) {
                    pokemonIds.add((int) pokemonId);
                }
            }
            contentValues.put(MyDatabaseHelper.TEAM_NAME, team.getName());
            contentValues.put(MyDatabaseHelper.TEAM_POKEMON_IDS, pokemonIds.toString()); // Store Pokémon IDs as JSON or CSV string
            long teamId = db.insert(MyDatabaseHelper.TEAM_TABLE, null, contentValues);
            Log.d("DatabaseLog", "Team inserted with ID: " + teamId);
        } catch (Exception e) {
            Log.e("DatabaseLog", "Error inserting team", e);
        } finally {
            db.close();
        }
    }

    // Helper method to insert a Pokémon into the database
    private long insertPokemon(OwnedPokemon pokemon) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put(MyDatabaseHelper.POKEMON_DEX_NUM, pokemon.getDexNum());
            contentValues.put(MyDatabaseHelper.POKEMON_NAME, pokemon.getName());
            contentValues.put(MyDatabaseHelper.POKEMON_URL, pokemon.getUrl());
            contentValues.put(MyDatabaseHelper.POKEMON_SPRITE, pokemon.getSprite());
            contentValues.put(MyDatabaseHelper.POKEMON_TYPE, pokemon.getType());
            contentValues.put(MyDatabaseHelper.POKEMON_ABILITY, pokemon.getAbility());
            contentValues.put(MyDatabaseHelper.POKEMON_MOVES, pokemon.getMoves() != null ? pokemon.getMoves().toString() : null);
            contentValues.put(MyDatabaseHelper.POKEMON_ITEM, pokemon.getItemName()); // Add item
            return db.insert(MyDatabaseHelper.POKEMON_TABLE, null, contentValues);
        } catch (Exception e) {
            Log.e("DatabaseLog", "Error inserting Pokémon", e);
            return -1;
        }
    }
}
