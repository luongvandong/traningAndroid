package com.android.project1.view.ui.adapter;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.android.project1.model.pojo.Notify;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @Date : 09/05/2017
 * @Author : ka
 */
public class DbAdapter extends SQLiteOpenHelper {

    @SuppressLint("SdCardPath")
    private static final String DB_PATH = "/data/data/com.android.project1/databases/";

    private static final String DB_NAME = "noti.sqlite";
    private static final String LOG = "DatabaseHelper";
    private static final int DB_VERSION = 1;
    private static final String ROW_ID = "id";
    private static final String NOTIFICATION = "notification";
    private static final String NAME = "name";
    private static final String CONTENT = "content";

    private Context context;
    private SQLiteDatabase myDb;

    public DbAdapter(Context context) {
        super(context, DB_NAME, null, DB_VERSION);

        this.context = context;
        boolean dbExist = checkDb();
        if (dbExist) {
            Log.i(LOG, "Db exist");
        } else {
            Log.i(LOG, "Db not exist");
            createDb();
        }
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    private boolean checkDb() {
        boolean checkDb = false;

        try {
            String myPath = DB_PATH + DB_NAME;
            File dbFile = new File(myPath);
            checkDb = dbFile.exists();
        } catch (SQLException e) {
            System.out.println("Db doesn't exist");
        }
        return checkDb;
    }

    private void copyDb() throws IOException {
        AssetManager dirPath = context.getAssets();

        InputStream inputStream = dirPath.open(DB_NAME);
        String outFileName = DB_PATH + DB_NAME;

        OutputStream outputStream = new FileOutputStream(outFileName);

        byte[] buffer = new byte[1024];
        int length;

        while ((length = inputStream.read(buffer)) > 0) {
            outputStream.write(buffer, 0, length);
        }
        outputStream.flush();
        outputStream.close();
        inputStream.close();
    }

    private void createDb() {
        this.getReadableDatabase();
        try {
            copyDb();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private SQLiteDatabase getMyDb() {
        return myDb;
    }

    public synchronized void close() {
        myDb.close();
        super.close();
    }

    private void open() {
        String myPath = DB_PATH + DB_NAME;
        myDb = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);
    }

    public List<Notify> getAllNotification() {
        List<Notify> list = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + NOTIFICATION;

        Log.i(LOG, selectQuery);

        open();
        SQLiteDatabase db = getMyDb();
        Cursor c = db.rawQuery(selectQuery, null);

        if (c.moveToFirst()) {
            do {
                Notify n = new Notify();
                n.setId(c.getLong(c.getColumnIndex(ROW_ID)));
                n.setName(c.getString(c.getColumnIndex(NAME)));
                n.setContent(c.getString(c.getColumnIndex(CONTENT)));
                list.add(n);
            } while (c.moveToNext());
        }
        c.close();
        close();
        return list;
    }

    public boolean deleteNotification(long rowId) {
        open();
        SQLiteDatabase db = getMyDb();
        boolean b = db.delete(NOTIFICATION, ROW_ID + "=" + rowId, null) > 0;
        close();
        return b;
    }

    public long insertNotification(Notify n) {
        open();
        ContentValues values = new ContentValues();
        values.put(NAME, n.getName());
        values.put(CONTENT, n.getContent());
        SQLiteDatabase db = getMyDb();
        long l = db.insert(NOTIFICATION, null, values);
        close();
        return l;
    }
}
