package com.zybooks.mmimages;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;
import android.graphics.Bitmap;
import java.util.ArrayList;

public class ModelsDB extends SQLiteOpenHelper{

    static Context ctx;
    static int VERSION = 1;
    SQLiteDatabase db;
    BitmapHelper BitmapHelper;

    private static ModelsDB mModelsDb;

    private ModelsDB(Context context, String name, SQLiteDatabase.CursorFactory factory, int version){
        super(context, name, factory, version);
        ctx = context;
    }

    //Column values for the model table
    public static final class ModelTable{
        public static final String TABLE = "MODEL_TABLE";
        public static final String COL_ID = "id";
        public static final String COL_TITLE = "title";
        public static final String COL_IMAGE = "image";
    }

    //Get instance method
    public static ModelsDB getInstance(Context context){
        if(mModelsDb == null){
            mModelsDb = new ModelsDB(context, "ModelDB",null, VERSION);
        }
        return mModelsDb;
    }

    //onCreate() method
    @Override
    public void onCreate(SQLiteDatabase db){
        BitmapHelper = new BitmapHelper();

        try {
            db.execSQL("CREATE TABLE " + ModelTable.TABLE + " (" + ModelTable.COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                    ModelTable.COL_TITLE + " STRING, " + ModelTable.COL_IMAGE + " BLOB)");
        } catch(SQLiteException e){
        }
    }

    //onUpgrade() method
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        if(oldVersion == VERSION){
            db = getReadableDatabase();

            try{
                db.execSQL("DROP TABLE IF EXISTS "+ ModelTable.TABLE);
                onCreate(db);
                VERSION = newVersion;
            } catch(SQLiteException e){
            }
            db.close();
        }
    }

    //insert() method
    void insert(String name, byte [] image){
        ContentValues cv = new ContentValues();
        cv.put(ModelsDB.ModelTable.COL_IMAGE, image);
        cv.put(ModelsDB.ModelTable.COL_TITLE, name);
        db = getWritableDatabase();
        try {
            db.insert(ModelsDB.ModelTable.TABLE, null, cv);
        }catch(SQLiteException e){
            e.printStackTrace();
        }
        db.close();
    }

    public void erase(){
        db = getWritableDatabase();
        try {
            onUpgrade(db, VERSION, VERSION + 1);
        }catch(SQLiteException e){
            e.printStackTrace();
        }
        db.close();
    }

    //getAll() method
    public void getAll(ArrayList<DataObject> modelValues){
        db = getReadableDatabase();

        try {
            //Select all rows and columns
            Cursor c = db.rawQuery("SELECT * FROM " + ModelTable.TABLE + ";", null);
            if (c != null) {
                c.moveToFirst();

                //Iterate through table and append values to array list
                for (int x = 0; x < c.getCount(); x++) {
                    Long tempId = c.getLong(0);
                    Bitmap tempBitMap = BitmapHelper.getImage(c.getBlob(2));
                    String tempTitle = c.getString(1);
                    DataObject tempData = new DataObject(tempId, tempBitMap, tempTitle);
                    modelValues.add(tempData);
                    c.moveToNext();
                }
            }
        }catch (SQLiteException e){
        }
        db.close();
    }

    //delete() method
    public void delete(String id, String title){
        db = getWritableDatabase();
        try {
            db.delete(ModelTable.TABLE, ModelTable.COL_ID + " = ? AND " + ModelTable.COL_TITLE + " = ?", new String[]{id, title});
        }catch(SQLiteException e){
        }
        db.close();
    }
}