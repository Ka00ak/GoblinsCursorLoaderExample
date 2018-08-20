package ru.kaooak.android.goblinscursorloaderexample.activity;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import ru.kaooak.android.goblinscursorloaderexample.R;
import ru.kaooak.android.goblinscursorloaderexample.database.DbContract;
import ru.kaooak.android.goblinscursorloaderexample.database.DbAsyncHandler;

public class GoblinActivity extends AppCompatActivity implements android.app.LoaderManager.LoaderCallbacks<Cursor>{

    private EditText mEtName;
    private EditText mEtCurrentHp;
    private EditText mEtMaxHp;
    private Button mBtnSave;
    private Button mBtnDelete;

    private Uri mUri;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goblin);

        mEtName = findViewById(R.id.et_name);
        mEtCurrentHp = findViewById(R.id.et_current_hp);
        mEtMaxHp = findViewById(R.id.et_max_hp);

        mBtnSave = findViewById(R.id.btn_save);
        mBtnDelete = findViewById(R.id.btn_delete);

        mUri = getIntent().getData();
        if (mUri == null) {
            mBtnSave.setText("Create");
            mBtnSave.setEnabled(true);
            mBtnDelete.setVisibility(View.INVISIBLE);
        }
        else {
            mBtnSave.setText("Save");
            getLoaderManager().initLoader(1, null, this);
        }
    }

    public void onClick(View view) {
        DbAsyncHandler handler = new DbAsyncHandler(getContentResolver());

        switch (view.getId()) {
            case R.id.btn_save:
                ContentValues contentValues = new ContentValues();
                contentValues.put(DbContract.UnitsTable.Columns.NAME, mEtName.getText().toString());
                contentValues.put(DbContract.UnitsTable.Columns.CURRENT_HP, Integer.valueOf(mEtCurrentHp.getText().toString()));
                contentValues.put(DbContract.UnitsTable.Columns.MAX_HP, Integer.valueOf(mEtMaxHp.getText().toString()));

                if (mUri == null) {
                    mBtnSave.setEnabled(false);
                    handler.startInsert(0, null, DbContract.UnitsTable.CONTENT_URI, contentValues);
                } else {
                    mBtnSave.setEnabled(false);
                    mBtnDelete.setEnabled(false);
                    handler.startUpdate(0, null, mUri, contentValues, null, null);
                }

                break;
            case R.id.btn_delete:
                mBtnSave.setEnabled(false);
                mBtnDelete.setEnabled(false);
                handler.startDelete(0, null, mUri, null, null);
                break;
        }

        finish();
    }

    //
    @NonNull
    @Override
    public android.content.CursorLoader onCreateLoader(int id, @Nullable Bundle args) {

        String[] projection = {
                DbContract.UnitsTable.Columns._ID,
                DbContract.UnitsTable.Columns.NAME,
                DbContract.UnitsTable.Columns.MAX_HP,
                DbContract.UnitsTable.Columns.CURRENT_HP
        };

        return new android.content.CursorLoader(this, mUri, null, null, null, null);
    }

    @Override
    public void onLoadFinished(android.content.Loader<Cursor> loader, Cursor cursor) {
        cursor.moveToFirst();

//        String uuid = cursor.getString(cursor.getColumnIndex(DbContract.UnitsTable.Columns._ID));
        String name = cursor.getString(cursor.getColumnIndex(DbContract.UnitsTable.Columns.NAME));
        int maxHp = cursor.getInt(cursor.getColumnIndex(DbContract.UnitsTable.Columns.MAX_HP));
        int currentHp = cursor.getInt(cursor.getColumnIndex(DbContract.UnitsTable.Columns.CURRENT_HP));

        mEtName.setText(name);
        mEtCurrentHp.setText(String.valueOf(currentHp));
        mEtMaxHp.setText(String.valueOf(maxHp));

        mBtnSave.setEnabled(true);
        mBtnDelete.setEnabled(true);
    }

    @Override
    public void onLoaderReset(android.content.Loader<Cursor> loader) {
        mBtnSave.setEnabled(false);
        mBtnDelete.setEnabled(false);
    }
}
