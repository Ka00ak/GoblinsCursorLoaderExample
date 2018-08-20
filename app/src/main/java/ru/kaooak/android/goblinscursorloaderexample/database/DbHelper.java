package ru.kaooak.android.goblinscursorloaderexample.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "goblins.db";
    private static final int VERSION = 1;

    public DbHelper(Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE " + DbContract.UnitsTable.NAME + "(" +
                DbContract.UnitsTable.Columns._ID + " integer primary key autoincrement, " +
                DbContract.UnitsTable.Columns.NAME + " text, " +
                DbContract.UnitsTable.Columns.MAX_HP + " integer, " +
                DbContract.UnitsTable.Columns.CURRENT_HP + " integer" + ");"
        );

        insertData(sqLiteDatabase);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    private void insertData(SQLiteDatabase sqLiteDatabase) {
        for(int i=0; i < 10; i++) {
            ContentValues contentValues = toContentValues("name " + i, i, i * 2);

            sqLiteDatabase.insert(DbContract.UnitsTable.NAME, null, contentValues);
        }
    }

    private ContentValues toContentValues(String name, int currentHp, int maxHp) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DbContract.UnitsTable.Columns.NAME, name);
        contentValues.put(DbContract.UnitsTable.Columns.CURRENT_HP, currentHp);
        contentValues.put(DbContract.UnitsTable.Columns.MAX_HP, maxHp);
        return contentValues;
    }
}
