package fr.jeremy.doctoapp;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class DatabaseManager extends SQLiteOpenHelper {

    public static  final String DATABASE_NAME = "Base.db";
    private static final int DATABASE_VERSION = 1;

    public DatabaseManager(Context context){ //le context sera l'activité
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) { //la premiere fois pour creer la base de données
        //table à créer
        String strSql = "create table T_Infos ("
                      +"    name text not null," //not null ça veut dire obligatoire la le nom de l'utilisateur
                      +"    phone integer not null,"
                      +"    email text not null,"
                      +"    password text not null"
                      +")";
        db.execSQL( strSql );
        Log.i("DATABASE", "onCreate invoked");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) { //quand il y a une maj de la base de donnée sera invoqué qaund detecte un ecart de version
        //lancer des ordres sql
        //String strSql = "alter table T_Infos add column ..."; //ordre pour altérer la table
        String strSql = "drop table T_Infos"; //détruit tout
        db.execSQL(strSql); //la il faudrait vraiment mettre à jour
        this.onCreate(db);
        Log.i("DATABASE", "onUpgrade invoked");
    }

    public void insertInfos(String name, int phone, String email, String password){
        name = name.replace("'", "''");
        email = email.replace("'", "''");
        password = password.replace("'", "''");

        String strSql = "insert into T_Infos (name, phone, email, password) values ('"
                      + name + "', " + phone + ", " + email + ", " + password + ")";

        this.getWritableDatabase().execSQL( strSql );
        Log.i("DATABASE", "insertInfos invoked");
    }

    public List<PersonData> readTop10(){
        List<PersonData> infos = new ArrayList<>();

        //1ere technique : SQl
        String strSql = "select * from T_Infos order by phone desc limit 10";
        Cursor cursor = this.getReadableDatabase().rawQuery(strSql,null);
        cursor.moveToFirst();
        while(! cursor.isAfterLast()){
            PersonData info = new PersonData(cursor.getString(0), cursor.getInt(1),cursor.getString(2), cursor.getString(3));
            infos.add(info);
            cursor.moveToNext();
        }

        cursor.close();

        return infos;
    }
}
