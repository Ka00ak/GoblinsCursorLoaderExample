package ru.kaooak.android.goblinscursorloaderexample.database;

import android.net.Uri;
import android.provider.BaseColumns;

public class DbContract {

    public static class UnitsTable {
        public static final String NAME = "units";

        public static class Columns implements BaseColumns{
            public static final String NAME = "name";
            public static final String MAX_HP = "maxHP";
            public static final String CURRENT_HP = "currentHP";
        }

        private static final Uri BASE_CONTENT_URI = Uri.parse("content://ru.kaooak.android.goblinscursorloaderexample.provider");
        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, NAME);
    }

}
