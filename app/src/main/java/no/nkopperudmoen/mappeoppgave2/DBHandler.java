package no.nkopperudmoen.mappeoppgave2;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import no.nkopperudmoen.mappeoppgave2.Models.Bestilling;
import no.nkopperudmoen.mappeoppgave2.Models.Kontakt;
import no.nkopperudmoen.mappeoppgave2.Models.Restaurant;

public class DBHandler extends SQLiteOpenHelper {
    @SuppressLint("SimpleDateFormat")
    public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd-MM-yyyy HH:mm");
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
            + "(" + KEY_ID + " INTEGER PRIMARY KEY," + KEY_RESTAURANT_ID + " INTEGER," + KEY_TID + " TEXT" + ")";
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
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAVN, r.getNavn());
        values.put(KEY_TELEFON, r.getTelefon());
        values.put(KEY_ADRESSE, r.getAdresse());
        values.put(KEY_POSTNR, r.getPostNr());
        values.put(KEY_TYPE, r.getType());
        db.insert(TABLE_RESTAURANTER, null, values);
    }

    public void lagreBestilling(Bestilling b) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues ordreValues = new ContentValues();
        ordreValues.put(KEY_RESTAURANT_ID, b.getRestaurantID());
        ordreValues.put(KEY_TID, DATE_FORMAT.format(b.getTid()));
        long id = db.insert(TABLE_ORDRE, null, ordreValues);
        for (Kontakt k : b.getVenner()) {
            ContentValues vennerValues = new ContentValues();
            vennerValues.put(KEY_ORDRE_ID, id);
            vennerValues.put(KEY_KONTAKT_ID, k.get_ID());
            db.insert(TABLE_ORDREVENNER, null, vennerValues);
        }
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
        SQLiteDatabase db = this.getWritableDatabase();
        String select = "SELECT * FROM " + TABLE_ORDRE;
        ArrayList<Bestilling> bestillinger = new ArrayList<>();
        Cursor c = db.rawQuery(select, null);
        if (c.moveToFirst()) {
            do {
                Bestilling b = new Bestilling();
                b.set_ID(c.getLong((c.getColumnIndex(KEY_ID))));
                b.setRestaurantID(c.getLong((c.getColumnIndex(KEY_RESTAURANT_ID))));
                try {
                    b.setTid(DATE_FORMAT.parse(c.getString((c.getColumnIndex(KEY_TID)))));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                bestillinger.add(b);

            } while (c.moveToNext());
        }
        return bestillinger;
    }

    public ArrayList<Restaurant> hentRestauranter() {
        SQLiteDatabase db = this.getWritableDatabase();
        String select = "SELECT * FROM " + TABLE_RESTAURANTER;
        ArrayList<Restaurant> restauranter = new ArrayList<>();
        Cursor c = db.rawQuery(select, null);
        if (c.moveToFirst()) {
            do {
                Restaurant r = new Restaurant();
                r.set_ID(c.getLong((c.getColumnIndex(KEY_ID))));
                r.setTelefon(c.getString((c.getColumnIndex(KEY_TELEFON))));
                r.setNavn(c.getString((c.getColumnIndex(KEY_NAVN))));
                r.setAdresse(c.getString((c.getColumnIndex(KEY_ADRESSE))));
                r.setPostNr(c.getInt((c.getColumnIndex(KEY_POSTNR))));
                r.setType(c.getString((c.getColumnIndex(KEY_TYPE))));
                restauranter.add(r);
            } while (c.moveToNext());
        }
        return restauranter;
    }

    public Kontakt hentKontakt(int id) {
        SQLiteDatabase db = this.getWritableDatabase();

        return null;
    }

    public Restaurant hentRestaurant(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return null;
    }

    public Bestilling hentBestilling(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return null;
    }

    /*
    Endre-metoder
     */
    public int endreKontakt(Kontakt k) {
        SQLiteDatabase db = this.getWritableDatabase();
        return 0;
    }

    public int endreRestaurant(Restaurant r) {
        SQLiteDatabase db = this.getWritableDatabase();
        return 0;
    }

    public int endreBestilling(Bestilling b) {
        SQLiteDatabase db = this.getWritableDatabase();
        return 0;
    }

    /*
    Slett-metoder
     */
    public void slettKontakt(Long id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_KONTAKTER, KEY_ID + " = ?", new String[]{String.valueOf(id)});
    }

    public void slettRestaurant(Long id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_RESTAURANTER, KEY_ID + " = ?", new String[]{String.valueOf(id)});
    }
}
