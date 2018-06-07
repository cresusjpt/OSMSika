package com.saltechdigital.osmsika.database.rendement;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.saltechdigital.osmsika.database.DAOBase;
import com.saltechdigital.osmsika.database.culture.TCulture;

import java.util.ArrayList;
import java.util.List;

public class TRendementDao extends DAOBase {

    private static final String TABLE_NAME = "RENDEMENT";
    private static final String IDCULTURE = "IDCULTURE";
    private static final String IDPERIODE = "IDPERIODE";
    private static final String QTEMETRECARRE = "QTEMETRECARRE";

    private Context context;
    private SQLiteDatabase database = getDb();

    public TRendementDao(Context pContext) {
        super(pContext);
    }


    public void ajouter(TRendement mRendement) {
        if (mRendement.getIdCulture() != 0) {
            database = open();
            ContentValues values = new ContentValues();
            values.put(IDCULTURE, mRendement.getIdCulture());
            database.insert(TABLE_NAME, null, values);
            database.close();
        }
        if (mRendement.getIdPeriode() != 0) {
            database = open();
            ContentValues values = new ContentValues();
            values.put(IDPERIODE, mRendement.getIdPeriode());
            database.insert(TABLE_NAME, null, values);
            database.close();
        }

        if (mRendement.getQtemetrecarre() != 0) {
            database = open();
            ContentValues values = new ContentValues();
            values.put(QTEMETRECARRE, mRendement.getQtemetrecarre());
            database.insert(TABLE_NAME, null, values);
            database.close();
        }
    }

    public void ajouterListe(List<TRendement> rendementList) {
        for (TRendement rendement : rendementList) {
            ajouter(rendement);
        }
    }

    public void supprimer(TRendement rendement) {
        database = open();
        database.delete(TABLE_NAME, IDCULTURE + " = ? and "+IDPERIODE+" = ?", new String[]{String.valueOf(rendement.getIdCulture()),String.valueOf(rendement.getIdPeriode())});
        database.close();
    }

    public TRendement selectionner(long id) {
        database = open();
        TRendement rendement = new TRendement();

        Cursor c = database.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + IDCULTURE + " = ?", new String[]{String.valueOf(id)});
        if (c.moveToFirst()) {
            rendement.setIdCulture(c.getInt(0));
            rendement.setIdPeriode(c.getInt(1));
            rendement.setQtemetrecarre(c.getDouble(2));
            return rendement;
        }
        c.close();
        database.close();
        return rendement;
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

    public List<Other> selectionnerListByCultureAndPeriode(TCulture mCulture) {

        database = open();

        List<Other> liste = new ArrayList<>();

        String requete = "SELECT DATEFIN, DATEDEBUT, QTEMETRECARRE FROM RENDEMENT r, CULTURE c, PERIODECULTURE p WHERE c.idCulture = r.idCulture AND p.idPeriode = r.idPeriode AND c.idCulture = ?";

        Cursor cursor = database.rawQuery(requete, new String[]{String.valueOf(mCulture.getIdCulture())});

        if (cursor.moveToFirst()) {
            do {
                Other autre = new Other();
                autre.setDateFin(cursor.getString(0));
                autre.setDateDebut(cursor.getString(1));
                autre.setQteMetreCarre(cursor.getDouble(2));
                liste.add(autre);
            } while (cursor.moveToNext());
        }

        cursor.close();
        database.close();
        return liste;
    }

    public List<TRendement> selectionnerList() {

        database = open();

        List<TRendement> liste = new ArrayList<>();

        String requete = "SELECT * FROM " + TABLE_NAME/* + " WHERE " + KEY + " <> ?"*/;

        Cursor cursor = database.rawQuery(requete, /*new String[]{String.valueOf(1)}*/null);

        if (cursor.moveToFirst()) {
            do {
                TRendement rendement = new TRendement();
                rendement.setIdCulture(cursor.getInt(0));
                rendement.setIdPeriode(cursor.getInt(1));
                rendement.setQtemetrecarre(cursor.getDouble(2));
                liste.add(rendement);
            } while (cursor.moveToNext());
        }
        cursor.close();
        database.close();
        return liste;
    }
}
