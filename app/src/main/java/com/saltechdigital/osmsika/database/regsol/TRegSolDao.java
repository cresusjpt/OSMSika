package com.saltechdigital.osmsika.database.regsol;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.saltechdigital.osmsika.database.DAOBase;

import java.util.ArrayList;
import java.util.List;

public class TRegSolDao extends DAOBase {
    private static final String TABLE_NAME = "REGSOL";
    private static final String KEY = "IDREGION";
    private static final String IDSOL = "IDSOL";

    private Context context;
    private SQLiteDatabase database = getDb();

    public TRegSolDao(Context pContext) {
        super(pContext);
    }

    public void ajouter(TRegSol mRegSol) {
        if (mRegSol.getIdSol() != 0) {
            database = open();
            ContentValues values = new ContentValues();
            values.put(IDSOL, mRegSol.getIdSol());
            database.insert(TABLE_NAME, null, values);
            database.close();
        }
    }

    public int getKey(int idSol) {
        int ret = 0;
        database = open();
        Cursor cursor = database.rawQuery("SELECT " + KEY + " FROM " + TABLE_NAME + " WHERE " + IDSOL + " = ?", new String[]{String.valueOf(idSol)});
        if (cursor.moveToFirst()) {
            ret = cursor.getInt(0);
        }
        cursor.close();
        database.close();
        return ret;
    }

    public void ajouterListe(List<TRegSol> regSolList) {
        for (TRegSol regSol  : regSolList) {
            ajouter(regSol);
        }
    }

    public void supprimer(long id) {
        database = open();
        database.delete(TABLE_NAME, KEY + " = ?", new String[]{String.valueOf(id)});
        database.close();
    }

    public String getIdSol(int idSol) {
        database = open();
        String retourne = "";
        Cursor cursor = database.rawQuery("SELECT " + IDSOL + " FROM " + TABLE_NAME + " WHERE " + KEY + " = ?", new String[]{String.valueOf(idSol)});
        if (cursor.moveToFirst()) {
            retourne = cursor.getString(0);
        }
        cursor.close();
        database.close();
        return retourne;
    }

    public TRegSol selectionner(long id) {
        database = open();
        TRegSol regSol = new TRegSol();

        Cursor c = database.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + KEY + " = ?", new String[]{String.valueOf(id)});
        if (c.moveToFirst()) {
            regSol.setIdRegion(c.getInt(0));
            regSol.setIdSol(c.getInt(1));
            return regSol;
        }
        c.close();
        database.close();
        return regSol;
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

    public List<TRegSol> selectionnerList() {

        database = open();

        List<TRegSol> liste = new ArrayList<>();

        String requete = "SELECT * FROM " + TABLE_NAME/* + " WHERE " + KEY + " <> ?"*/;

        Cursor cursor = database.rawQuery(requete, /*new String[]{String.valueOf(1)}*/null);

        if (cursor.moveToFirst()) {
            do {
                TRegSol regSol = new TRegSol();
                regSol.setIdRegion(cursor.getInt(0));
                regSol.setIdSol(cursor.getInt(1));
                liste.add(regSol);
            } while (cursor.moveToNext());
        }
        cursor.close();
        database.close();
        return liste;
    }
}
