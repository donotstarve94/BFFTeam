package com.example.gnud.dulich.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.gnud.dulich.item.ThingsPre;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by GNUD on 19/08/2016.
 */
public class DatabaseThings {
    private static final String DATABASE_NAME = "DB_THINGS";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_THINGS = "THINGS";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_THINGS = "things";

    private static Context context;
    static SQLiteDatabase db;
    private OpenHelper openHelper;

    public DatabaseThings(Context c){
        DatabaseThings.context = c;
    }

    public DatabaseThings open() throws SQLException{
        openHelper = new OpenHelper(context);
        db = openHelper.getWritableDatabase();
        return this;
    }
    public void close(){
        openHelper.close();
    }
    private static class OpenHelper extends SQLiteOpenHelper{

        public OpenHelper(Context context){
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }
        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("CREATE TABLE " + TABLE_THINGS + "("
                            + COLUMN_ID + " TEXT PRIMARY KEY, "
                            + COLUMN_THINGS + " TEXT NOT NULL); "
            );
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_THINGS);
            onCreate(db);
        }
    }
    public void addThings(String id, String things){
        String query = "INSERT INTO " + TABLE_THINGS + " (" + COLUMN_ID + "," + COLUMN_THINGS + ") VALUES ('" + id + "','" + things + "');";
        db.execSQL(query);
//        ContentValues cv = new ContentValues();
//        cv.put(COLUMN_ID, thingsPre.getId());
//        cv.put(COLUMN_THINGS, thingsPre.getThings());
//        db.insert(TABLE_THINGS, null, cv);
    }
    public void deleteAll(){
        db.delete(TABLE_THINGS, null, null);
    }

    public List<ThingsPre> getAllThings(){
        List<ThingsPre> listThings = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_THINGS;
        Cursor cursor = db.rawQuery(query, null);

        if(cursor.moveToFirst()){
            do{
                ThingsPre thingsPre = new ThingsPre();
                thingsPre.setId(cursor.getString(0));
                thingsPre.setThings(cursor.getString(1));

                listThings.add(thingsPre);
            } while (cursor.moveToNext());
        }
        return listThings;
    }
    public void updateActivities(ArrayList<String> inputArray){
        String string = "";
        for(int i = 0; i < inputArray.size(); i++){
            string = string + inputArray.get(i) + "-";
        }
    }



    public String getActivities(ArrayList<String> listID){
        String result = "";
        for(int i = 0; i < listID.size(); i++){
            for(int j = 0; j < getAllThings().size(); j++){
                if(listID.get(i).equals(getAllThings().get(j).getId())){
                    result += getAllThings().get(j).getThings();
                }
            }
        }
        return result;
    }
    public boolean ischeckData(){
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_THINGS, null);
        if(cursor.getCount() != 0){
            cursor.close();
            return true;
        } else {
            cursor.close();
            return false;
        }
    }
}
