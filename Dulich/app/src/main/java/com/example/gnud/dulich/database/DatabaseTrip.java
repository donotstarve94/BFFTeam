package com.example.gnud.dulich.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.gnud.dulich.item.Trip;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by GNUD on 15/08/2016.
 */

public class DatabaseTrip {
    private static final String DATABASE_NAME = "DB_TRIP";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_TRIP = "TRIP";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_LOCATION = "location";
    public static final String COLUMN_DATEMONTH = "datemonth";
    public static final String COLUMN_THINGS = "things";

    private int k;
    private static Context context;
    static SQLiteDatabase db;
    private OpenHelper openHelper;

    public DatabaseTrip(Context c){
        DatabaseTrip.context = c;
    }

    public DatabaseTrip open() throws SQLException{
        openHelper = new OpenHelper(context);
        db = openHelper.getWritableDatabase();
        return this;
    }

    public void close(){
        openHelper.close();
    }
    private static class OpenHelper extends SQLiteOpenHelper{

        public OpenHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }
//http://stackoverflow.com/questions/16694786/how-to-customize-a-spinner-in-android
        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("CREATE TABLE " + TABLE_TRIP + "("
                  + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                  + COLUMN_LOCATION + " TEXT NOT NULL, "
                  + COLUMN_DATEMONTH + " TEXT NOT NULL, "
                  + COLUMN_THINGS + " TEXT NOT NULL); "
            );
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_TRIP);
            onCreate(db);
        }
    }
    public Trip GetTripbyID(int idTrip){
        String query = "SELECT * FROM " + TABLE_TRIP + " WHERE " + COLUMN_ID + " = " + idTrip;
        Cursor cursor = db.rawQuery(query, null);
        if(cursor.getCount() != 0 && cursor.moveToFirst()){
            Trip trip = new Trip();
            trip.setId(Integer.parseInt(cursor.getString(0)));
            trip.setLocation(cursor.getString(1));
            trip.setDatemonth(cursor.getString(2));
            trip.setThings(cursor.getString(3));

            return trip;
        }
        return null;

    }
    public int getIdMax(){
        Cursor cursor = db.rawQuery("SELECT " + COLUMN_ID + " FROM " + TABLE_TRIP, null);
        if(cursor.moveToLast()) return Integer.parseInt(cursor.getString(0));
        return 0;
    }

    public void updatebyId(int id, String things){
        String query = "UPDATE " + TABLE_TRIP + " SET " + COLUMN_THINGS + " = '" + things + "' WHERE " + COLUMN_ID + " = " + id;
        db.execSQL(query);
    }

    public void addTrip(Trip trip){
        String location = "";
        if(trip.getLocation().indexOf(",") == -1){
            location = trip.getLocation();
        } else {
            location = trip.getLocation().substring(0, trip.getLocation().indexOf(","));
        }
        String query = "INSERT INTO " + TABLE_TRIP + " (" + COLUMN_LOCATION + "," + COLUMN_DATEMONTH + "," + COLUMN_THINGS + ") VALUES ('" + location + "','" + trip.getDatemonth() + "','" + trip.getThings() + "');";
        db.execSQL(query);
    }

    public void deleteAll(){
        db.delete(TABLE_TRIP, null, null);
    }
    public void deletebyId(int id){
        db.delete(TABLE_TRIP, COLUMN_ID + " = ?", new String[]{String.valueOf(id)});

    }

    public ArrayList<Trip> getAllTrip(){
        ArrayList<Trip> tripList = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_TRIP;
        Cursor cursor = db.rawQuery(query, null);

        if(cursor.moveToFirst()){
            do{
                Trip trip = new Trip();
                trip.setId(Integer.parseInt(cursor.getString(0)));
                trip.setLocation(cursor.getString(1));
                trip.setDatemonth(cursor.getString(2));
                trip.setThings(cursor.getString(3));

                tripList.add(trip);
            } while (cursor.moveToNext());
        }
        return tripList;
    }
}

