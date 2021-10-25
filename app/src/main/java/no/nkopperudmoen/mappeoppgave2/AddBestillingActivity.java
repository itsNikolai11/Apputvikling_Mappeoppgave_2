package no.nkopperudmoen.mappeoppgave2;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;

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
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = new DBHandler(getApplicationContext());
        setContentView(R.layout.activity_addbestilling);
        setupTimeDialogs();
        initializeSpinners();


    }

    public void setupTimeDialogs() {
        final Calendar calendar = Calendar.getInstance();
        dateSelect = (EditText) findViewById(R.id.ordreDato);
        dateSelect.setOnClickListener(view -> {
            int calDay = calendar.get(Calendar.DAY_OF_MONTH);
            int calMonth = calendar.get(Calendar.MONTH);
            int calYear = calendar.get(Calendar.YEAR);
            datePicker = new DatePickerDialog(AddBestillingActivity.this, new DatePickerDialog.OnDateSetListener() {
                @SuppressLint("SetTextI18n")
                @Override
                public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                    //Måneder begynner på 0, for å unngå offByOne feil
                    month = month+1;
                    dateSelect.setText(day + "/" + month + "-" + year);
                    time += day + "/" + month + "/" + year + " ";

                }
            }, calYear, calMonth, calDay);
            datePicker.show();
        });
        timeSelect = (EditText) findViewById(R.id.ordreTid);
        timeSelect.setOnClickListener(view -> {
            int calHour = calendar.get(Calendar.HOUR_OF_DAY);
            int calMinute = calendar.get(Calendar.MINUTE);
            timePicker = new TimePickerDialog(AddBestillingActivity.this, new TimePickerDialog.OnTimeSetListener() {
                @SuppressLint("SetTextI18n")
                @Override
                public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                    timeSelect.setText(hour + ":" + minute);
                    time += hour + ":" + minute;

                }
            }, calHour, calMinute, true);
            timePicker.show();
        });

    }

    public void initializeSpinners() {
        Spinner resSpinner = findViewById(R.id.restauranterSpinner);
        Spinner kontaktSpinner = findViewById(R.id.vennerSpinner);
        ArrayList<Restaurant> restauranter = db.hentRestauranter();
        ArrayList<Kontakt> kontakter = db.hentKontakter();
        ArrayAdapter<Restaurant> resAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, restauranter);
        resAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        resSpinner.setAdapter(resAdapter);
        ArrayAdapter<Kontakt> kontaktAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, kontakter);
        kontaktAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        kontaktSpinner.setAdapter(kontaktAdapter);


    }

    public Date hentDato() {
        Date d = new Date();
        return d;
    }

    public void lagreBestilling(View v) {
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
        db.lagreBestilling(b);
        finish();


    }

    public void cancel(View v) {
        finish();
    }
    //TODO spinner for venner med onSelect som legger til venner i en tableLayout.
    //TODO fjern valgt venn fra spinner
}
