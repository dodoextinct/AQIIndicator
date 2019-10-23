package com.example.aqiindicator;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Arrays;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    Spinner spinner1;
    Spinner source;
    Spinner dest;
    private DatePicker datePicker;
    private Calendar calendar;
    private TextView dateView;
    private int year, month, day;
    private TimePicker timePicker1;
    private TextView time;
    private String format = "";
    private int hour, min;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().hide();
       FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, Indiactor.class);
                startActivity(i);
            }
        });
        dateView = (TextView) findViewById(R.id.date_view);
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        time = (TextView) findViewById(R.id.time_view);
        hour = calendar.get(Calendar.HOUR_OF_DAY);
        min = calendar.get(Calendar.MINUTE);
        showTime(hour, min);

        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        showDate(year, month+1, day);
        spinner1 = findViewById(R.id.spinner1);
        String[] cities = new String[]{"Select the City", "Patna", "Raipur", "Hyderabad"};
        ArrayAdapter<String> city = new ArrayAdapter<>(this, R.layout.spinner, cities);
        source = findViewById(R.id.source);
        dateView.setVisibility(View.INVISIBLE);
        time.setVisibility(View.INVISIBLE);
        dest = findViewById(R.id.dest);
        spinner1.setAdapter(city);

        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                String item = adapterView.getItemAtPosition(i).toString();
                if( item == "Patna"){
                    dateView.setVisibility(View.VISIBLE);
                    time.setVisibility(View.VISIBLE);
                    String[] places_pat = new String[]{"Anisabad", "Boring Road", "Kurji", "Phulwari", "Kadam Kuan", "Kankar Bagh", "Rajendra Nagar", "Nala Road"};
                    Arrays.sort(places_pat);
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this, R.layout.spinner, places_pat);
                    source.setAdapter(adapter);
                    dest.setAdapter(adapter);
                }
                else if(item == "Raipur"){
                    dateView.setVisibility(View.VISIBLE);
                    time.setVisibility(View.VISIBLE);
                    String[] places = new String[]{"Amapara", "Amanaka", "Gahri Chowk",
                            "Jaistambh chowk", "Marine Drive", "NIT", "Atal Nagar", "Tatibandh", "Panchpedi Naka"};
                    Arrays.sort(places);
                    ArrayAdapter<String> source_ad;
                    source_ad = new ArrayAdapter<String>(MainActivity.this, R.layout.spinner,places);

                    source.setAdapter(source_ad);

                    ArrayAdapter<String> dest_ad = new ArrayAdapter<String>(MainActivity.this, R.layout.spinner, places);
                    dest.setAdapter(dest_ad);
                }
                else if( item == "Hyderabad"){
                    dateView.setVisibility(View.VISIBLE);
                    time.setVisibility(View.VISIBLE);
                    String[] places_hy = new String[]{"Nizam Pet", "Madhura Nagar", "Banjara Hills", "Jubly Hills", "Kukatpally", "Kalyan Nagar"};
                    Arrays.sort(places_hy);
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this, R.layout.spinner, places_hy);
                    source.setAdapter(adapter);
                    dest.setAdapter(adapter);
                }
                else{
                    dateView.setVisibility(View.INVISIBLE);
                    time.setVisibility(View.INVISIBLE);
                    String[] empty = new String[]{};
                    ArrayAdapter<String> empty_ad = new ArrayAdapter<String>(MainActivity.this, R.layout.spinner, empty);
                    source.setAdapter(empty_ad);
                    dest.setAdapter(empty_ad);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }


    public void showTime(int hour, int min) {
        if (hour == 0) {
            hour += 12;
            format = "AM";
        } else if (hour == 12) {
            format = "PM";
        } else if (hour > 12) {
            hour -= 12;
            format = "PM";
        } else {
            format = "AM";
        }

        time.setText(new StringBuilder().append(hour).append(" : ").append(min)
                .append(" ").append(format));
    }
    @SuppressWarnings("deprecation")
    public void setDate(View view) {
        showDialog(999);
    }

    @Nullable
    @SuppressWarnings("deprecation")
    public void setTime(View view) {
        showDialog(1000);
    }

    @Nullable
    @Override
    protected Dialog onCreateDialog(int id, Bundle args) {
        if (id == 999) {
            return new DatePickerDialog(this,
                    myDateListener, year, month, day);
        }
        else if(id == 1000){
            return new TimePickerDialog(MainActivity.this, myTime, hour, min, false);
        }
        return null;
    }
    private DatePickerDialog.OnDateSetListener myDateListener = new
            DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker arg0,
                                      int arg1, int arg2, int arg3) {
                    // TODO Auto-generated method stub
                    // arg1 = year
                    // arg2 = month
                    // arg3 = day
                    showDate(arg1, arg2+1, arg3);
                }
            };

    private TimePickerDialog.OnTimeSetListener myTime = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker timePicker, int i, int i1) {
            showTime(i, i1);
        }
    };
    private void showDate(int year, int month, int day) {
        dateView.setText(new StringBuilder().append(day).append("/")
                .append(month).append("/").append(year));
    }
    @Override
    protected Dialog onCreateDialog(int id) {
        // TODO Auto-generated method stub
        if (id == 999) {
            return new DatePickerDialog(this,
                    myDateListener, year, month, day);
        }
        return null;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
