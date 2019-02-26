package eazy.iforgot.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


/**
 * Created by Administrator on 23-02-2017.
 */

public class MyDBhelper extends SQLiteOpenHelper {
    public MyDBhelper(Context context) {
        super(context, "iforgotdb", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("create table passcodetable(_id integer primary key autoincrement, silenton text,normalmode text,contacts text,diverton text,divertoff text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    public void insertData(String silent, String normal,String contacts,String divert, String divertoff){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues cv=new ContentValues();
        cv.put("silenton",silent);
        cv.put("normalmode",normal);

        cv.put("diverton",divert);

        cv.put("contacts",contacts);
        cv.put("divertoff",divertoff);


        db.insert("passcodetable",null,cv);


    }


    public void updatedata(String silent, String normal,String contacts,String divert, String divertoff){
        SQLiteDatabase db=this.getWritableDatabase();
        String id="1";
        ContentValues cv=new ContentValues();
        cv.put("silenton",silent);
        cv.put("normalmode",normal);

        cv.put("diverton",divert);

        cv.put("contacts",contacts);
        cv.put("divertoff",divertoff);


        db.update("passcodetable",cv,"_id="+id,null);
    }
    public Cursor getdata(){
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor c=db.rawQuery("select * from passcodetable",null);
        return c;
    }
    public void truncatedata(){
        SQLiteDatabase db=this.getWritableDatabase();
        db.execSQL("delete from passcodetable");

    }



}
