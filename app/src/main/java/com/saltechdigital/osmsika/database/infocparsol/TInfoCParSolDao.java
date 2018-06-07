package com.saltechdigital.osmsika.database.infocparsol;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.saltechdigital.osmsika.database.DAOBase;

import java.util.ArrayList;
import java.util.List;

public class TInfoCParSolDao extends DAOBase {
    private static final String TABLE_NAME = "INFOCPARSOL";
    private static final String IDCULTURE = "IDCULTURE";
    private static final String IDSOL = "IDSOL";

    private Context context;
    private SQLiteDatabase database = getDb();

    public TInfoCParSolDao(Context pContext) {
        super(pContext);
    }

    public void ajouter(TInfoCParSol mInfoCParSol) {
        if (mInfoCParSol.getIdCulture() != 0) {
            database = open();
            ContentValues values = new ContentValues();
            values.put(IDCULTURE, mInfoCParSol.getIdCulture());
            database.insert(TABLE_NAME, null, values);
            database.close();
        }
        if (mInfoCParSol.getIdSol() != 0) {
            database = open();
            ContentValues values = new ContentValues();
            values.put(IDSOL, mInfoCParSol.getIdSol());
            database.insert(TABLE_NAME, null, values);
            database.close();
        }
    }

    public void ajouterListe(List<TInfoCParSol> infoCParSolList) {
        for (TInfoCParSol infoCParSol : infoCParSolList) {
            ajouter(infoCParSol);
        }
    }

    public void supprimer(TInfoCParSol infoCParSol) {
        database = open();
        database.delete(TABLE_NAME, IDCULTURE + " = ? and "+IDSOL+" = ?", new String[]{String.valueOf(infoCParSol.getIdCulture()),String.valueOf(infoCParSol.getIdSol())});
        database.close();
    }

    public TInfoCParSol selectionner(long id) {
        database = open();
        TInfoCParSol infoCParSol = new TInfoCParSol();

        Cursor c = database.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + IDCULTURE + " = ?", new String[]{String.valueOf(id)});
        if (c.moveToFirst()) {
            infoCParSol.setIdCulture(c.getInt(0));
            infoCParSol.setIdSol(c.getInt(1));
            return infoCParSol;
        }
        c.close();
        database.close();
        return infoCParSol;
    }

    public int taille() {
        database = open();
        int retourne = 0;
        Cursor cursor = database.rawQuery("SELECT COUNT(" + IDCULTURE+") FROM " + TABLE_NAME, null);
        if (cursor.moveToFirst()) {
            retourne = cursor.getInt(0);
        }
        cursor.close();
        database.close();
        return retourne;
    }

    public List<TInfoCParSol> selectionnerList() {

        database = open();

        List<TInfoCParSol> liste = new ArrayList<>();

        String requete = "SELECT * FROM " + TABLE_NAME/* + " WHERE " + KEY + " <> ?"*/;

        Cursor cursor = database.rawQuery(requete, /*new String[]{String.valueOf(1)}*/null);

        if (cursor.moveToFirst()) {
            do {
                TInfoCParSol infoCParSol = new TInfoCParSol();
                infoCParSol.setIdCulture(cursor.getInt(0));
                infoCParSol.setIdSol(cursor.getInt(1));
                liste.add(infoCParSol);
            } while (cursor.moveToNext());
        }
        cursor.close();
        database.close();
        return liste;
    }
}
