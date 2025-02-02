package com.pokebuild.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MyDatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "pokebuild.db";
    private static final int DATABASE_VERSION = 1;

    // Table: Pokemon
    public static final String POKEMON_TABLE = "Pokemon";
    public static final String POKEMON_ID = "pokemon_id";
    public static final String POKEMON_DEX_NUM = "dex_num";
    public static final String POKEMON_NAME = "name";
    public static final String POKEMON_URL = "url";
    public static final String POKEMON_SPRITE = "sprite";
    public static final String POKEMON_TYPE = "type";
    public static final String POKEMON_ABILITY = "ability";
    public static final String POKEMON_MOVES = "moves";

    // Table: Team
    public static final String TEAM_TABLE = "Team";
    public static final String TEAM_ID = "team_id";
    public static final String TEAM_NAME = "team_name";
    public static final String TEAM_POKEMON_IDS = "pokemon_ids"; // JSON or CSV string of Pokémon IDs

    public MyDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d("DatabaseCheck", "onCreate called");

        String createPokemonTableQuery = "CREATE TABLE " + POKEMON_TABLE + " ("
                + POKEMON_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + POKEMON_DEX_NUM + " INTEGER NOT NULL, "
                + POKEMON_NAME + " TEXT NOT NULL, "
                + POKEMON_URL + " TEXT NOT NULL, "
                + POKEMON_SPRITE + " TEXT NOT NULL, "
                + POKEMON_TYPE + " TEXT NOT NULL, "
                + POKEMON_ABILITY + " TEXT NOT NULL, "
                + POKEMON_MOVES + " TEXT NOT NULL)";

        String createTeamTableQuery = "CREATE TABLE " + TEAM_TABLE + " ("
                + TEAM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + TEAM_NAME + " TEXT NOT NULL, "
                + TEAM_POKEMON_IDS + " TEXT NOT NULL)"; // Pokémon IDs stored as JSON or CSV string

        db.execSQL(createPokemonTableQuery);
        db.execSQL(createTeamTableQuery);

        // Insert dummy values
        insertPokemonDummyValues(db);
        insertTeamDummyValues(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + POKEMON_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + TEAM_TABLE);
        onCreate(db);
    }

    private void insertPokemonDummyValues(SQLiteDatabase db) {
        ContentValues contentValues = new ContentValues();

        contentValues.put(POKEMON_DEX_NUM, 1);
        contentValues.put(POKEMON_NAME, "Bulbasaur");
        contentValues.put(POKEMON_URL, "https://pokeapi.co/api/v2/pokemon/1/");
        contentValues.put(POKEMON_SPRITE, "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/1.png");
        contentValues.put(POKEMON_TYPE, "Grass/Poison");
        contentValues.put(POKEMON_ABILITY, "Overgrow");
        contentValues.put(POKEMON_MOVES, "Tackle, Growl, Leech Seed");
        db.insert(POKEMON_TABLE, null, contentValues);

        contentValues.clear();
        contentValues.put(POKEMON_DEX_NUM, 4);
        contentValues.put(POKEMON_NAME, "Charmander");
        contentValues.put(POKEMON_URL, "https://pokeapi.co/api/v2/pokemon/4/");
        contentValues.put(POKEMON_SPRITE, "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/4.png");
        contentValues.put(POKEMON_TYPE, "Fire");
        contentValues.put(POKEMON_ABILITY, "Blaze");
        contentValues.put(POKEMON_MOVES, "Scratch, Growl, Ember");
        db.insert(POKEMON_TABLE, null, contentValues);

        contentValues.clear();
        contentValues.put(POKEMON_DEX_NUM, 7);
        contentValues.put(POKEMON_NAME, "Squirtle");
        contentValues.put(POKEMON_URL, "https://pokeapi.co/api/v2/pokemon/7/");
        contentValues.put(POKEMON_SPRITE, "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/7.png");
        contentValues.put(POKEMON_TYPE, "Water");
        contentValues.put(POKEMON_ABILITY, "Torrent");
        contentValues.put(POKEMON_MOVES, "Tackle, Tail Whip, Water Gun");
        db.insert(POKEMON_TABLE, null, contentValues);
    }

    private void insertTeamDummyValues(SQLiteDatabase db) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(TEAM_NAME, "Starter Team");
        contentValues.put(TEAM_POKEMON_IDS, "[1, 2, 3]"); // Pokémon IDs stored as JSON or CSV string
        db.insert(TEAM_TABLE, null, contentValues);

        contentValues.clear();
        contentValues.put(TEAM_NAME, "Team 2");
        db.insert(TEAM_TABLE, null, contentValues);
    }
}
