package no.nkopperudmoen.mappeoppgave2;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import no.nkopperudmoen.mappeoppgave2.Models.Bestilling;
import no.nkopperudmoen.mappeoppgave2.Models.Kontakt;
import no.nkopperudmoen.mappeoppgave2.Models.Restaurant;

public class AddBestillingActivity extends AppCompatActivity {
    DBHandler db;
    DatePickerDialog datePicker;
    TimePickerDialog timePicker;
    EditText dateSelect;
    EditText timeSelect;
    String time = "";
    ArrayList<Kontakt> kontakter = new ArrayList<>();
    @SuppressLint("SimpleDateFormat")
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = new DBHandler(getApplicationContext());
        setContentView(R.layout.activity_addbestilling);
        setupTimeDialogs();
        initializeSpinners();
        visValgteKontakter();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Gson gson = new Gson();
        SharedPreferences prefs = this.getSharedPreferences(getString(R.string.sharedPrefs), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        String arrayJson = prefs.getString(getString(R.string.selectVenner), "");
        Type t = new TypeToken<ArrayList<Kontakt>>(){}.getType();
        ArrayList<Kontakt> tempArray = gson.fromJson(arrayJson, t);
        if(tempArray!=null){
            for(Kontakt k : tempArray){
                if(!kontakter.contains(k)){
                    kontakter.add(k);
                }
            }
        }
        visValgteKontakter();
    }

    public void setupTimeDialogs() {
        final Calendar calendar = Calendar.getInstance();
        dateSelect = (EditText) findViewById(R.id.ordreDato);
        dateSelect.setOnClickListener(view -> {
            int calDay = calendar.get(Calendar.DAY_OF_MONTH);
            int calMonth = calendar.get(Calendar.MONTH);
            int calYear = calendar.get(Calendar.YEAR);
            datePicker = new DatePickerDialog(AddBestillingActivity.this, (datePicker, year, month, day) -> {
                //Måneder begynner på 0, for å unngå offByOne feil
                month = month + 1;
                dateSelect.setText(day + "/" + month + "-" + year);
                time += day + "/" + month + "/" + year + " ";
            }, calYear, calMonth, calDay);
            datePicker.show();
        });
        timeSelect = (EditText) findViewById(R.id.ordreTid);
        timeSelect.setOnClickListener(view -> {
            int calHour = calendar.get(Calendar.HOUR_OF_DAY);
            int calMinute = calendar.get(Calendar.MINUTE);
            timePicker = new TimePickerDialog(AddBestillingActivity.this, (timePicker, hour, minute) -> {
                timeSelect.setText(hour + ":" + minute);
                time += hour + ":" + minute;

            }, calHour, calMinute, true);
            timePicker.show();
        });

    }

    public void initializeSpinners() {
        Spinner resSpinner = findViewById(R.id.restauranterSpinner);
        ArrayList<Restaurant> restauranter = db.hentRestauranter();
        ArrayList<Kontakt> kontakter = db.hentKontakter();
        ArrayAdapter<Restaurant> resAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, restauranter);
        resAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        resSpinner.setAdapter(resAdapter);
        ArrayAdapter<Kontakt> kontaktAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, kontakter);
        kontaktAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


    }

    @SuppressLint({"SetTextI18n", "InflateParams"})
    public void visValgteKontakter() {
        TableLayout tl = (TableLayout) findViewById(R.id.selectedKontakter);
        TableRow tr;
        tl.removeAllViews();
        TextView tv;
        Button btn;
        if(kontakter.isEmpty()){
            return;
        }
        for (Kontakt k : kontakter) {
            tr = (TableRow) getLayoutInflater().inflate(R.layout.tablerow_ordre_kontakt, null);
            tv = tr.findViewById(R.id.ordreKNavn);
            tv.setText(k.getFornavn() + " " + k.getEtternavn());
            btn = tr.findViewById(R.id.ordreFjernKontakt);
            btn.setOnClickListener(view -> {
                kontakter.remove(k);
                visValgteKontakter();
            });
            tl.addView(tr);
        }
    }

    public boolean validerBestilling() {
        return true;
    }

    public void lagreBestilling(View v) {
        if (!validerBestilling()) {
            return;
        }
        Spinner resSpinner = findViewById(R.id.restauranterSpinner);
        Bestilling b = new Bestilling();
        Restaurant r = (Restaurant) resSpinner.getSelectedItem();
        b.setRestaurantID(r.get_ID());
        try {
            Date d = dateFormat.parse(time);
            b.setTid(d);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        b.setVenner(kontakter);
        db.lagreBestilling(b);

        finish();
    }

    public void cancel(View v) {
        finish();
    }

    public void leggTilVenner(View view) {
        Intent i = new Intent(this, ActivitySelectKontakter.class);
        startActivity(i);
    }

    @Override
    protected void onStop() {
        super.onStop();
        SharedPreferences prefs = this.getSharedPreferences(getString(R.string.sharedPrefs), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(getString(R.string.selectVenner), "");
        editor.apply();
    }
}
