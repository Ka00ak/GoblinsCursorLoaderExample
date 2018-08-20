package ru.kaooak.android.goblinscursorloaderexample.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import ru.kaooak.android.goblinscursorloaderexample.database.DbContract;
import ru.kaooak.android.goblinscursorloaderexample.database.DbHelper;

public class GoblinContentProvider extends ContentProvider {

    private static final String TAG = GoblinContentProvider.class.getSimpleName();

    private static final int GOBLINS = 100;
    private static final int GOBLIN_ID = 101;

    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    static {
        sUriMatcher.addURI("ru.kaooak.android.goblinscursorloaderexample.provider", DbContract.UnitsTable.NAME, GOBLINS);
        sUriMatcher.addURI("ru.kaooak.android.goblinscursorloaderexample.provider", DbContract.UnitsTable.NAME + "/#", GOBLIN_ID);
    }

    private DbHelper mDbHelper;

    @Override
    public boolean onCreate() {
        mDbHelper = new DbHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] strings, @Nullable String s, @Nullable String[] strings1, @Nullable String s1) {
        Log.d(TAG, "query");

        try {
            Thread.sleep(1000);
        } catch (Exception e) { }

        SQLiteDatabase database = mDbHelper.getReadableDatabase();

        int match = sUriMatcher.match(uri);
        switch (match) {
            case GOBLINS:
                Cursor cursorGoblins = database.query(DbContract.UnitsTable.NAME, strings, s, strings1, null, null, s1);
                cursorGoblins.setNotificationUri(getContext().getContentResolver(), uri);
                return cursorGoblins;
            case GOBLIN_ID:
                s = DbContract.UnitsTable.Columns._ID + " = ?";
                strings1 = new String[]{ String.valueOf(ContentUris.parseId(uri)) };
                Cursor cursor = database.query(DbContract.UnitsTable.NAME, strings, s, strings1, null, null, s1);
                cursor.setNotificationUri(getContext().getContentResolver(), uri);
                return cursor;
            default:
                throw new IllegalArgumentException("Cannot query unknown URI ");
        }

    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {

        final int match = sUriMatcher.match(uri);
        switch (match) {
            case GOBLINS:
                return "Goblins";
            case GOBLIN_ID:
                return "GoblinId";
            default:
                throw new IllegalArgumentException("Unknown URI " + uri + " with match " + match);
        }
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        Log.d(TAG, "insert");

        try {
            Thread.sleep(1000);
        } catch (Exception e) { }

        //check contentValues

        SQLiteDatabase database = mDbHelper.getWritableDatabase();

        int match = sUriMatcher.match(uri);
        switch (match) {
            case GOBLINS:
                long id = database.insert(DbContract.UnitsTable.NAME, null, contentValues);
                Uri newUri = ContentUris.withAppendedId(uri, id);
                getContext().getContentResolver().notifyChange(uri, null);

                return newUri;
            default:
                throw new IllegalArgumentException("Cannot insert unknown URI ");
        }
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        Log.d(TAG, "delete");

        try {
            Thread.sleep(1000);
        } catch (Exception e) { }

        SQLiteDatabase database = mDbHelper.getWritableDatabase();

        int match = sUriMatcher.match(uri);
        switch (match) {
            case GOBLINS:
                int rowsCount = database.delete(DbContract.UnitsTable.NAME, s, strings);
                getContext().getContentResolver().notifyChange(uri, null);

                return rowsCount;
            case GOBLIN_ID:
                s = DbContract.UnitsTable.Columns._ID + " = ?";
                strings = new String[]{ String.valueOf(ContentUris.parseId(uri)) };
                int rowsCount2 = database.delete(DbContract.UnitsTable.NAME, s, strings);
                getContext().getContentResolver().notifyChange(uri, null);

                return rowsCount2;
            default:
                throw new IllegalArgumentException("Cannot delete unknown URI ");
        }
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        Log.d(TAG, "update");

        try {
            Thread.sleep(1000);
        } catch (Exception e) { }

        SQLiteDatabase database = mDbHelper.getWritableDatabase();

        int match = sUriMatcher.match(uri);
        switch (match) {
            case GOBLINS:
                int rowsCount = database.update(DbContract.UnitsTable.NAME, contentValues, s, strings);
                getContext().getContentResolver().notifyChange(uri, null);

                return rowsCount;
            case GOBLIN_ID:
                s = DbContract.UnitsTable.Columns._ID + " = ?";
                strings = new String[]{ String.valueOf(ContentUris.parseId(uri)) };
                int rowsCount2 = database.update(DbContract.UnitsTable.NAME, contentValues, s, strings);
                getContext().getContentResolver().notifyChange(uri, null);

                return rowsCount2;
            default:
                throw new IllegalArgumentException("Cannot delete unknown URI ");
        }    }
}
