package com.sathish.learning.util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "learning.db";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table contacts" + "(id integer primary key, name text,phone text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS contacts");
        onCreate(db);
    }

    public boolean saveContacts(String name, String phone) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("phone", phone);
        sqLiteDatabase.insert("contacts", null, contentValues);
        return true;
    }

    public List<ContactsDetails> getAllCotacts() {
        List<ContactsDetails> array_list = new ArrayList<ContactsDetails>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from contacts", null);
        res.moveToFirst();

        while (res.isAfterLast() == false) {
            ContactsDetails contact = new ContactsDetails();
            contact.setName(res.getString(res.getColumnIndex("name")));
            contact.setPhoneNumber(res.getString(res.getColumnIndex("phone")));
            array_list.add(contact);
            res.moveToNext();
        }
        return array_list;
    }
}
