package com.saltechdigital.osmsika.database.culture;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.saltechdigital.osmsika.database.DAOBase;

import java.util.ArrayList;
import java.util.List;

public class TCultureDao extends DAOBase {

    private static final String TABLE_NAME = "CULTURE";
    private static final String KEY = "IDCULTURE";
    private static final String NOMCULTURE = "NOMCULTURE";

    private Context context;
    private SQLiteDatabase database = getDb();

    public TCultureDao(Context pContext) {
        super(pContext);
        this.context = pContext;
    }

    public void ajouter(TCulture mCulture) {
        if (mCulture.getNomCulture() != null) {
            database = open();
            ContentValues values = new ContentValues();
            values.put(NOMCULTURE, mCulture.getNomCulture());
            database.insert(TABLE_NAME, null, values);
            database.close();
        }
    }

    public int getKey(String nomCulture) {
        int ret = 0;
        database = open();
        Cursor cursor = database.rawQuery("SELECT " + KEY + " FROM " + TABLE_NAME + " WHERE " + NOMCULTURE + " = ?", new String[]{nomCulture});
        if (cursor.moveToFirst()) {
            ret = cursor.getInt(0);
        }
        cursor.close();
        database.close();
        return ret;
    }

    public void ajouterListe(List<TCulture> cultureList) {
        for (TCulture culture : cultureList) {
            ajouter(culture);
        }
    }

    public void supprimer(long id) {
        database = open();
        database.delete(TABLE_NAME, KEY + " = ?", new String[]{String.valueOf(id)});
        database.close();
    }

    public String getNomclasse(int idCulture) {
        database = open();
        String retourne = "";
        Cursor cursor = database.rawQuery("SELECT " + NOMCULTURE + " FROM " + TABLE_NAME + " WHERE " + KEY + " = ?", new String[]{String.valueOf(idCulture)});
        if (cursor.moveToFirst()) {
            retourne = cursor.getString(0);
        }
        cursor.close();
        database.close();
        return retourne;
    }

    public TCulture selectionner(long id) {
        database = open();
        TCulture culture = new TCulture();

        Cursor c = database.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + KEY + " = ?", new String[]{String.valueOf(id)});
        if (c.moveToFirst()) {
            culture.setIdCulture(c.getInt(0));
            culture.setNomCulture(c.getString(1));
            return culture;
        }
        c.close();
        database.close();
        return culture;
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

    public List<TCulture> selectionnerListByRegion(int idRegion){
        database = open();

        List<TCulture> liste = new ArrayList<>();

        String requete = "SELECT c.IDCULTURE, c.NOMCULTURE FROM SOL s, CULTURE c, INFOCPARSOL i, REGSOL rs, REGION r WHERE rs.idRegion = r.idregion AND rs.idSol = s.idSol AND s.idSol = i.idSol AND c.idCulture = i.idCulture AND r.idRegion = ?";

        Cursor cursor = database.rawQuery(requete, new String[]{String.valueOf(idRegion)},null);

        if (cursor.moveToFirst()) {
            do {
                TCulture culture = new TCulture();
                culture.setIdCulture(cursor.getInt(0));
                culture.setNomCulture(cursor.getString(1));
                liste.add(culture);
            } while (cursor.moveToNext());
        }
        cursor.close();
        database.close();
        return liste;
    }

    public List<TCulture> selectionnerList() {

        database = open();

        List<TCulture> liste = new ArrayList<>();

        String requete = "SELECT * FROM " + TABLE_NAME/* + " WHERE " + KEY + " <> ?"*/;

        Cursor cursor = database.rawQuery(requete, /*new String[]{String.valueOf(1)}*/null);

        if (cursor.moveToFirst()) {
            do {
                TCulture culture = new TCulture();
                culture.setIdCulture(cursor.getInt(0));
                culture.setNomCulture(cursor.getString(1));
                liste.add(culture);
            } while (cursor.moveToNext());
        }
        cursor.close();
        database.close();
        return liste;
    }
}
