package ru.kaooak.android.goblinscursorloaderexample.activity;

import android.app.LoaderManager;
import android.content.ContentUris;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.CursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import ru.kaooak.android.goblinscursorloaderexample.R;
import ru.kaooak.android.goblinscursorloaderexample.database.DbAsyncHandler;
import ru.kaooak.android.goblinscursorloaderexample.database.DbContract;

public class GoblinsListActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>{

    private static final String TAG = GoblinsListActivity.class.getSimpleName();

    SimpleCursorAdapter mSimpleCursorAdapter;
    GoblinsAdapter mAdapter;

    Button mBtnDeleteAll;
    FloatingActionButton mBtnCreate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goblins_list);

//        mSimpleCursorAdapter = new SimpleCursorAdapter(this, R.layout.item, null, new String[]{DbContract.UnitsTable.Columns.NAME}, new int[]{R.id.tv}, SimpleCursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
        getLoaderManager().initLoader(0, null, this);

        ListView listView = findViewById(R.id.list_view_caverns);
        mAdapter = new GoblinsAdapter(this, null, 0);
        listView.setAdapter(mAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Uri uri = ContentUris.withAppendedId(DbContract.UnitsTable.CONTENT_URI, l);

                Intent intent = new Intent(GoblinsListActivity.this, GoblinActivity.class);
                intent.setData(uri);
                startActivity(intent);
            }
        });

        mBtnDeleteAll = findViewById(R.id.btn_delete_all);
        mBtnCreate = findViewById(R.id.floatingActionButton);
    }

    public void onClick(View view) {
        DbAsyncHandler handler = new DbAsyncHandler(getContentResolver());

        switch (view.getId()) {
            case R.id.btn_delete_all:
                Log.d(TAG, "deleting");

                handler.startDelete(0, null, DbContract.UnitsTable.CONTENT_URI, null ,null);

                break;
            case R.id.floatingActionButton:
                Intent intent = new Intent(GoblinsListActivity.this, GoblinActivity.class);
                intent.setData(null);
                startActivity(intent);

                break;
        }
    }


    //
    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        Log.d(TAG, "onCreateLoader: ");

        String[] projection = {
                DbContract.UnitsTable.Columns._ID,
                DbContract.UnitsTable.Columns.NAME,
                DbContract.UnitsTable.Columns.MAX_HP,
                DbContract.UnitsTable.Columns.CURRENT_HP
        };

        return new CursorLoader(this, DbContract.UnitsTable.CONTENT_URI, null, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        Log.d(TAG, "onLoadFinished: " + cursor.getCount());

        mAdapter.swapCursor(cursor);

        mBtnDeleteAll.setEnabled(true);
        mBtnCreate.setEnabled(true);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        Log.d(TAG, "onLoaderReset");

        mBtnDeleteAll.setEnabled(false);
        mBtnCreate.setEnabled(false);

        mAdapter.swapCursor(null);
    }



    //
    private class GoblinsAdapter extends CursorAdapter {

        public GoblinsAdapter(Context context, Cursor c, int flags) {
            super(context, c, flags);
        }

        @Override
        public View newView(Context context, Cursor cursor, ViewGroup parent) {
            return LayoutInflater.from(context).inflate(R.layout.item, parent, false);
        }

        @Override
        public void bindView(View view, Context context, Cursor cursor) {
            String name = cursor.getString(cursor.getColumnIndex(DbContract.UnitsTable.Columns.NAME));
            int maxHp = cursor.getInt(cursor.getColumnIndex(DbContract.UnitsTable.Columns.MAX_HP));
            int currentHp = cursor.getInt(cursor.getColumnIndex(DbContract.UnitsTable.Columns.CURRENT_HP));

            TextView tv = view.findViewById(R.id.tv);
            tv.setText(name + " (" + currentHp + "/" + maxHp + ")");
        }

    }
}
