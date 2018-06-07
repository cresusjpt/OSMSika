package com.saltechdigital.osmsika.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


/**
 * Created by Jeanpaul Tossou on 29/12/2016.
 */

public abstract class DAOBase extends SQLiteOpenHelper {

    // Nous sommes à la première version de la base
    // Si je décide de la mettre à jour après que l'app soit en utilisation, il faudra changer cet attribut
    private static int VERSION = 1;
    // Le nom du fichier qui représente ma base
    public final static String NOM = "osmsika.db";

    public static int getVERSION() {
        return VERSION;
    }

    public static void setVERSION(int VERSION) {
        DAOBase.VERSION = VERSION;
    }

    private static final String TABLE_CREATE_CULTURE = "CREATE TABLE IF NOT EXISTS CULTURE(\n" +
            "\tIDCULTURE\tINTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,\n" +
            "\tNOMCULTURE\tTEXT NOT NULL \n" +
            ")";

    private static final String TABLE_CREATE_INFOCPARSOL = "CREATE TABLE IF NOT EXISTS INFOCPARSOL(\n" +
            "\tIDCULTURE\tINTEGER\tNOT NULL,\n" +
            "\tIDSOL\tINTEGER NOT NULL,\n" +
            "\tPRIMARY KEY (IDCULTURE,IDSOL),\n" +
            "\tFOREIGN KEY (IDCULTURE) REFERENCES CULTURE(IDCULTURE),\n" +
            "\tFOREIGN KEY (IDSOL) REFERENCES SOL(IDSOL)\n" +
            ")";

    private static final String TABLE_CREATE_PERIODECULTURE = "CREATE TABLE IF NOT EXISTS PERIODECULTURE(\n" +
            "\tIDPERIODE INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
            "\tDATEDEBUT TEXT NOT NULL,\n" +
            "\tDATEFIN TEXT NOT NULL\n" +
            ")";

    private static final String TABLE_CREATE_RENDEMENT = "CREATE TABLE IF NOT EXISTS RENDEMENT(\n" +
            "\tIDCULTURE INTEGER NOT NULL,\n" +
            "\tIDPERIODE INTEGER NOT NULL,\n" +
            "\tQTEMETRECARRE REAL DEFAULT 0,\n" +
            "\tPRIMARY KEY (IDCULTURE,IDPERIODE),\n" +
            "\tFOREIGN KEY (IDCULTURE) REFERENCES CULTURE (IDCULTURE),\n" +
            "\tFOREIGN KEY (IDPERIODE) REFERENCES PERIODECULTURE (IDPERIODE)\n" +
            ")";

    private static final String TABLE_CREATE_SOL = "CREATE TABLE IF NOT EXISTS SOL(\n" +
            "\tIDSOL INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,\n" +
            "\tTYPESOL TEXT NOT NULL\n" +
            ")";

    private static final String TABLE_CREATE_REGION = "CREATE TABLE  IF NOT EXISTS Region (\n" +
            "\tIDREGION INTEGER NOT NULL,\n" +
            "\tNOMREGION TEXT NOT NULL,\n" +
            "\tSURFACECULTIVABLE REAL DEFAULT 0,\n" +
            "\tPRIMARY KEY (IDREGION)\n" +
            ")";

    private static final String TABLE_CREATE_REGSOL = "CREATE TABLE IF NOT EXISTS REGSOL (\n" +
            "\tIDREGION INTEGER NOT NULL,\n" +
            "\tIDSOL INTEGER NOT NULL,\n" +
            "\tPRIMARY KEY (`idRegion`,`idSol`),\n" +
            "\tFOREIGN KEY (`idRegion`) REFERENCES REGION (IDREGION),\n" +
            "\tFOREIGN KEY (`idSol`) REFERENCES SOL (IDSOL)\n" +
            ")";

    private static final String TABLE_DROP_CULTURE = "DROP TABLE IF EXISTS CULTURE";
    private static final String TABLE_DROP_INFOCPARSOL = "DROP TABLE IF EXISTS INFOCPARSOL";
    private static final String TABLE_DROP_PERIODECULTURE = "DROP TABLE IF EXISTS PERIODECULTURE";
    private static final String TABLE_DROP_RENDEMENT = "DROP TABLE IF EXISTS RENDEMENT";
    private static final String TABLE_DROP_SOL = "DROP TABLE IF EXISTS SOL";
    private static final String TABLE_DROP_REGION = "DROP TABLE IF EXISTS REGION";
    private static final String TABLE_DROP_REGSOL = "DROP TABLE IF EXISTS REGSOL";

    private SQLiteDatabase mDb = null;

    public DAOBase(Context pContext) {
        super(pContext, NOM, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(TABLE_CREATE_SOL);
        sqLiteDatabase.execSQL(TABLE_CREATE_PERIODECULTURE);
        sqLiteDatabase.execSQL(TABLE_CREATE_CULTURE);
        sqLiteDatabase.execSQL(TABLE_CREATE_INFOCPARSOL);
        sqLiteDatabase.execSQL(TABLE_CREATE_RENDEMENT);
        sqLiteDatabase.execSQL(TABLE_CREATE_REGION);
        sqLiteDatabase.execSQL(TABLE_CREATE_REGSOL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newversion) {
        sqLiteDatabase.execSQL(TABLE_DROP_RENDEMENT);
        sqLiteDatabase.execSQL(TABLE_DROP_INFOCPARSOL);
        sqLiteDatabase.execSQL(TABLE_DROP_CULTURE);
        sqLiteDatabase.execSQL(TABLE_DROP_PERIODECULTURE);
        sqLiteDatabase.execSQL(TABLE_DROP_SOL);
        sqLiteDatabase.execSQL(TABLE_DROP_REGION);
        sqLiteDatabase.execSQL(TABLE_DROP_REGSOL);
        onCreate(sqLiteDatabase);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
        super.onDowngrade(db, oldVersion, newVersion);
    }

    protected SQLiteDatabase open() {
        // Pas besoin de fermer la dernière base puisque getWritableDatabase s'en charge
        mDb = getWritableDatabase();
        return mDb;
    }

    private SQLiteDatabase read() {
        mDb = getReadableDatabase();
        return mDb;
    }

    public void close() {
        //quelques soit l'etat de la base ouverte ou pas on s'assure de pouvoir le fermer
        read();
        mDb.close();
    }

    protected SQLiteDatabase getDb() {
        return mDb;
    }
}
