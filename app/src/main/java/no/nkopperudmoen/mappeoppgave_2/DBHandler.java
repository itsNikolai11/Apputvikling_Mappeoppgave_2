package no.nkopperudmoen.mappeoppgave_2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import no.nkopperudmoen.mappeoppgave_2.Models.Bestilling;
import no.nkopperudmoen.mappeoppgave_2.Models.Kontakt;
import no.nkopperudmoen.mappeoppgave_2.Models.Restaurant;

public class DBHandler extends SQLiteOpenHelper {
    //Databasenavn og versjon
    public static final String DATABASE_NAVN = "ordreManager";
    public static final int DATABASE_VERSJON = 1;
    //Tabellnavn
    public static final String TABLE_KONTAKTER = "Kontakter";
    public static final String TABLE_RESTAURANTER = "Restauranter";
    public static final String TABLE_ORDRE = "Ordre";
    public static final String TABLE_ORDREVENNER = "Ordrevenner";
    public static final String TABLE_POSTSTED = "Poststeder";
    //Felles kolonnenavn
    public static final String KEY_ID = "_ID";
    public static final String KEY_TELEFON = "Telefon";
    //Kontakter Kolonnenavn
    public static final String KEY_FORNAVN = "Fornavn";
    public static final String KEY_ETTERNAVN = "Etternavn";
    //Poststed kolonnenavn
    public static final String KEY_POSTSTED = "Poststed";
    //Restauranter kolonnenavn
    public static final String KEY_NAVN = "Navn";
    public static final String KEY_ADRESSE = "Adresse";
    public static final String KEY_POSTNR = "PostNr";
    public static final String KEY_TYPE = "Type";
    //Ordre kolonnenavn
    public static final String KEY_RESTAURANT_ID = "RestaurantId";
    public static final String KEY_TID = "Tid";
    //Ordrevenner
    public static final String KEY_ORDRE_ID = "OrdreNr";
    public static final String KEY_KONTAKT_ID = "KontaktId";
    //Opprette tabeller
    //Restaurant
    public static final String CREATE_TABLE_RESTAURANTER = "CREATE TABLE " + TABLE_RESTAURANTER
            + "(" + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAVN + " TEXT," + KEY_ADRESSE + " TEXT," + KEY_POSTNR + " INTEGER,"
            + KEY_TELEFON + " TEXT," + KEY_TYPE + " TEXT" + ")";
    //Poststed
    public static final String CREATE_TABLE_POSTSTED = "CREATE TABLE " + TABLE_POSTSTED
            + "(" + KEY_POSTNR + " INTEGER PRIMARY KEY," + KEY_POSTSTED + " TEXT" + ")";
    //Kontakt
    public static final String CREATE_TABLE_KONTAKTER = "CREATE TABLE " + TABLE_KONTAKTER
            + "(" + KEY_ID + " INTEGER PRIMARY KEY," + KEY_FORNAVN + " TEXT," + KEY_ETTERNAVN + " TEXT," + KEY_TELEFON + " TEXT" + ")";
    //Ordre
    public static final String CREATE_TABLE_ORDRE = "CREATE TABLE " + TABLE_ORDRE
            + "(" + KEY_ID + " INTEGER PRIMARY KEY," + KEY_RESTAURANT_ID + " INTEGER," + KEY_TID + " DATETIME" + ")";
    //OrdreKontakter
    public static final String CREATE_TABLE_ORDREKONTAKTER = "CREATE TABLE " + TABLE_ORDREVENNER
            + "(" + KEY_ORDRE_ID + " INTEGER PRIMARY KEY," + KEY_KONTAKT_ID + " INTEGER" + ")";

    public DBHandler(Context context) {
        super(context, DATABASE_NAVN, null, DATABASE_VERSJON);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_KONTAKTER);
        db.execSQL(CREATE_TABLE_ORDRE);
        db.execSQL(CREATE_TABLE_ORDREKONTAKTER);
        db.execSQL(CREATE_TABLE_POSTSTED);
        db.execSQL(CREATE_TABLE_RESTAURANTER);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_POSTSTED);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ORDRE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ORDREVENNER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_KONTAKTER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RESTAURANTER);
    }

    /*
    Legg til-metoder
     */
    public void leggTilKontakt(Kontakt kontakt) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_FORNAVN, kontakt.getFornavn());
        values.put(KEY_ETTERNAVN, kontakt.getEtternavn());
        values.put(KEY_TELEFON, kontakt.getTelefon());
        db.insert(TABLE_KONTAKTER, null, values);
    }

    public void leggTilRestaurant(Restaurant r) {

    }

    public void lagreBestilling(Bestilling b) {

    }

    /*
    Hent-metoder
     */
    public ArrayList<Kontakt> hentKontakter() {
        SQLiteDatabase db = this.getWritableDatabase();
        String select = "SELECT * FROM " + TABLE_KONTAKTER;
        Cursor c = db.rawQuery(select, null);
        ArrayList<Kontakt> kontakter = new ArrayList<>();
        if (c.moveToFirst()) {
            do {
                Kontakt k = new Kontakt();
                k.set_ID(c.getLong((c.getColumnIndex(KEY_ID))));
                k.setFornavn(c.getString((c.getColumnIndex(KEY_FORNAVN))));
                k.setEtternavn(c.getString((c.getColumnIndex(KEY_ETTERNAVN))));
                k.setTelefon(c.getString((c.getColumnIndex(KEY_TELEFON))));
                kontakter.add(k);
            } while (c.moveToNext());
        }
        return kontakter;
    }

    public ArrayList<Bestilling> hentBestillinger() {

        return null;
    }

    public ArrayList<Restaurant> hentRestauranter() {
        return null;
    }

    public Kontakt hentKontakt(int id) {
        return null;
    }

    public Restaurant hentRestaurant(int id) {
        return null;
    }

    public Bestilling hentBestilling(int id) {
        return null;
    }

    /*
    Endre-metoder
     */
    public int endreKontakt(Kontakt k) {
        return 0;
    }

    public int endreRestaurant(Restaurant r) {
        return 0;
    }

    public int endreBestilling(Bestilling b) {
        return 0;
    }

    /*
    Slett-metoder
     */
    public void slettKontakt(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "DELETE * FROM " + TABLE_KONTAKTER + " WHERE " + KEY_ID + " LIKE " + id;
        db.execSQL(sql);
    }
}
