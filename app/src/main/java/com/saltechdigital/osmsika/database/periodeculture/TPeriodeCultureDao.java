package com.saltechdigital.osmsika.database.periodeculture;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.saltechdigital.osmsika.database.DAOBase;

import java.util.ArrayList;
import java.util.List;

public class TPeriodeCultureDao extends DAOBase {
    private static final String TABLE_NAME = "PERIODECULTURE";
    private static final String KEY = "IDPERIODE";
    private static final String DATEDEBUT = "DATEDEBUT";
    private static final String DATEFIN = "DATEFIN";

    private Context context;
    private SQLiteDatabase database = getDb();

    public TPeriodeCultureDao(Context pContext) {
        super(pContext);
    }

    public void ajouter(TPeriodeCulture mPeriodeCulture) {
        if (mPeriodeCulture.getDateDebut() != null) {
            database = open();
            ContentValues values = new ContentValues();
            values.put(DATEDEBUT, mPeriodeCulture.getDateDebut());
            database.insert(TABLE_NAME, null, values);
            database.close();
        }
        if (mPeriodeCulture.getDateFin() != null) {
            database = open();
            ContentValues values = new ContentValues();
            values.put(DATEFIN, mPeriodeCulture.getDateFin());
            database.insert(TABLE_NAME, null, values);
            database.close();
        }
    }

    public int getKey(String dateDebut,String dateFin) {
        int ret = 0;
        database = open();
        Cursor cursor = database.rawQuery("SELECT " + KEY + " FROM " + TABLE_NAME + " WHERE " + DATEDEBUT + " = ?"+" AND "+DATEFIN+" = ?", new String[]{dateDebut,dateFin});
        if (cursor.moveToFirst()) {
            ret = cursor.getInt(0);
        }
        cursor.close();
        database.close();
        return ret;
    }

    public void ajouterListe(List<TPeriodeCulture> periodeCultureList) {
        for (TPeriodeCulture periodeCulture : periodeCultureList) {
            ajouter(periodeCulture);
        }
    }

    public void supprimer(long id) {
        database = open();
        database.delete(TABLE_NAME, KEY + " = ?", new String[]{String.valueOf(id)});
        database.close();
    }

    public String getDateDebut(int id) {
        database = open();
        String retourne = "";
        Cursor cursor = database.rawQuery("SELECT " + DATEDEBUT + " FROM " + TABLE_NAME + " WHERE " + KEY + " = ?", new String[]{String.valueOf(id)});
        if (cursor.moveToFirst()) {
            retourne = cursor.getString(0);
        }
        cursor.close();
        database.close();
        return retourne;
    }

    public String getDateFin(int id) {
        database = open();
        String retourne = "";
        Cursor cursor = database.rawQuery("SELECT " + DATEFIN + " FROM " + TABLE_NAME + " WHERE " + KEY + " = ?", new String[]{String.valueOf(id)});
        if (cursor.moveToFirst()) {
            retourne = cursor.getString(0);
        }
        cursor.close();
        database.close();
        return retourne;
    }

    public TPeriodeCulture selectionner(long id) {
        database = open();
        TPeriodeCulture periodeCulture = new TPeriodeCulture();

        Cursor c = database.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + KEY + " = ?", new String[]{String.valueOf(id)});
        if (c.moveToFirst()) {
            periodeCulture.setIdPeriode(c.getInt(0));
            periodeCulture.setDateDebut(c.getString(1));
            periodeCulture.setDateFin(c.getString(2));
            return periodeCulture;
        }
        c.close();
        database.close();
        return periodeCulture;
    }

    public int taille() {
        database = open();
        int retourne = 0;
        Cursor cursor = database.rawQuery("SELECT COUNT(" + KEY + ") FROM " + TABLE_NAME, null);
        if (cursor.moveToFirst()) {
            retourne = cursor.getInt(0);
        }
        cursor.close();
        database.close();
        return retourne;
    }

    public List<TPeriodeCulture> selectionnerList() {

        database = open();

        List<TPeriodeCulture> liste = new ArrayList<>();

        String requete = "SELECT * FROM " + TABLE_NAME/* + " WHERE " + KEY + " <> ?"*/;

        Cursor cursor = database.rawQuery(requete, /*new String[]{String.valueOf(1)}*/null);

        if (cursor.moveToFirst()) {
            do {
                TPeriodeCulture periodeCulture = new TPeriodeCulture();
                periodeCulture.setIdPeriode(cursor.getInt(0));
                periodeCulture.setDateDebut(cursor.getString(1));
                periodeCulture.setDateFin(cursor.getString(2));
                liste.add(periodeCulture);
            } while (cursor.moveToNext());
        }
        cursor.close();
        database.close();
        return liste;
    }
}
