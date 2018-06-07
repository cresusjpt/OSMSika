package com.saltechdigital.osmsika.database.sol;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.saltechdigital.osmsika.database.DAOBase;

import java.util.ArrayList;
import java.util.List;

public class TSolDao extends DAOBase {

    private static final String TABLE_NAME = "SOL";
    private static final String KEY = "IDSOL";
    private static final String TYPESOL = "TYPESOL";

    private Context context;
    private SQLiteDatabase database = getDb();

    public void ajouter(TSol mSol) {
        if (mSol.getTypeSol() != null) {
            database = open();
            ContentValues values = new ContentValues();
            values.put(TYPESOL, mSol.getTypeSol());
            database.insert(TABLE_NAME, null, values);
            database.close();
        }
    }

    public int getKey(String typeSol) {
        int ret = 0;
        database = open();
        Cursor cursor = database.rawQuery("SELECT " + KEY + " FROM " + TABLE_NAME + " WHERE " + TYPESOL + " = ?", new String[]{typeSol});
        if (cursor.moveToFirst()) {
            ret = cursor.getInt(0);
        }
        cursor.close();
        database.close();
        return ret;
    }

    public void ajouterListe(List<TSol> cultureList) {
        for (TSol culture : cultureList) {
            ajouter(culture);
        }
    }

    public void supprimer(long id) {
        database = open();
        database.delete(TABLE_NAME, KEY + " = ?", new String[]{String.valueOf(id)});
        database.close();
    }

    public String getNomclasse(int idSol) {
        database = open();
        String retourne = "";
        Cursor cursor = database.rawQuery("SELECT " + TYPESOL + " FROM " + TABLE_NAME + " WHERE " + KEY + " = ?", new String[]{String.valueOf(idSol)});
        if (cursor.moveToFirst()) {
            retourne = cursor.getString(0);
        }
        cursor.close();
        database.close();
        return retourne;
    }

    public TSol selectionner(long id) {
        database = open();
        TSol sol = new TSol();

        Cursor c = database.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + KEY + " = ?", new String[]{String.valueOf(id)});
        if (c.moveToFirst()) {
            sol.setIdSol(c.getInt(0));
            sol.setTypeSol(c.getString(1));
            return sol;
        }
        c.close();
        database.close();
        return sol;
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

    public List<TSol> selectionnerListByRegion(int idRegion){
        database = open();

        List<TSol> liste = new ArrayList<>();

        String requete = "SELECT s.IDSOL, s.TYPESOL FROM SOL s, REGION r, REGSOL rs WHERE s.idSol = rs.idsol AND r.idRegion = rs.idRegion AND r.idRegion = ?";

        Cursor cursor = database.rawQuery(requete, new String[]{String.valueOf(idRegion)},null);

        if (cursor.moveToFirst()) {
            do {
                TSol sol = new TSol();
                sol.setIdSol(cursor.getInt(0));
                sol.setTypeSol(cursor.getString(1));
                liste.add(sol);
            } while (cursor.moveToNext());
        }
        cursor.close();
        database.close();
        return liste;
    }

    public List<TSol> selectionnerList() {

        database = open();

        List<TSol> liste = new ArrayList<>();

        String requete = "SELECT * FROM " + TABLE_NAME/* + " WHERE " + KEY + " <> ?"*/;

        Cursor cursor = database.rawQuery(requete, /*new String[]{String.valueOf(1)}*/null);

        if (cursor.moveToFirst()) {
            do {
                TSol sol = new TSol();
                sol.setIdSol(cursor.getInt(0));
                sol.setTypeSol(cursor.getString(1));
                liste.add(sol);
            } while (cursor.moveToNext());
        }
        cursor.close();
        database.close();
        return liste;
    }


    public TSolDao(Context pContext) {
        super(pContext);
    }
}
