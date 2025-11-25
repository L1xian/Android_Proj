package com.example.tictac;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "TicTacGames.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "games";
    private static final String COL_ID = "id";
    private static final String COL_PLAYER1 = "player1";
    private static final String COL_PLAYER2 = "player2";
    private static final String COL_WINNER = "winner";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + " (" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_PLAYER1 + " TEXT, " +
                COL_PLAYER2 + " TEXT, " +
                COL_WINNER + " TEXT)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean addGame(String player1, String player2, String winner) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_PLAYER1, player1);
        contentValues.put(COL_PLAYER2, player2);
        contentValues.put(COL_WINNER, winner);
        long result = db.insert(TABLE_NAME, null, contentValues);
        return result != -1;
    }

    public Cursor getAllGames() {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
    }
}