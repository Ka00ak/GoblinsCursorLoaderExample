package ru.kaooak.android.goblinscursorloaderexample.database;

import android.content.AsyncQueryHandler;
import android.content.ContentResolver;

public class DbAsyncHandler extends AsyncQueryHandler {

    public DbAsyncHandler(ContentResolver cr) {
        super(cr);
    }
}
