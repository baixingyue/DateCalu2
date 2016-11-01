package com.example.dell.datecalu2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        assignControlNames();
        initUI();
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
        if (id == R.id.action_calendar) {
            startActivity(new Intent(MainActivity.this, CalendarActivity.class));
        }
        if (id == R.id.action_about) {
            startActivity(new Intent(MainActivity.this, aboutActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private EditText dateDisplay1, dateDisplay2;
    private Button buttonPick1, buttonPick2, buttonOK, buttonGo, buttonToday1, buttonToday2;
    private DatePicker myDatePicker;
    private TextView outputBox;
    private int currentId = 0;

    private Calendar date1, date2;
    private boolean picking = false;

    private RelativeLayout relativeLayout;

    private void assignControlNames() {
        relativeLayout = (RelativeLayout) findViewById(R.id.mainLayout);
        myDatePicker = (DatePicker) findViewById(R.id.datePicker);
        dateDisplay1 = (EditText) findViewById(R.id.dateDisplay1);
        dateDisplay2 = (EditText) findViewById(R.id.dateDisplay2);
        buttonPick1 = (Button) findViewById(R.id.button1);
        buttonPick2 = (Button) findViewById(R.id.button2);
        buttonToday1 = (Button) findViewById(R.id.buttonToday1);
        buttonToday2 = (Button) findViewById(R.id.buttonToday2);
        buttonOK = (Button) findViewById(R.id.pickOK);
        buttonGo = (Button) findViewById(R.id.buttonGo);
        outputBox = (TextView) findViewById(R.id.textView);
    }

    private void initUI() {
        Calendar c = Calendar.getInstance();
        String formattedDate = dateToStr(c.getTime());

        dateDisplay1.setText(formattedDate);
        dateDisplay2.setText(formattedDate);
        date1 = c;
        date2 = c;
    }

    private String dateToStr(Date myDate) {
        SimpleDateFormat df = new SimpleDateFormat("MM-dd-yyyy");
        return df.format(myDate);
    }

    private void beginPicking() {
        dateDisplay1.setVisibility(View.INVISIBLE);
        dateDisplay2.setVisibility(View.INVISIBLE);
        buttonToday1.setVisibility(View.INVISIBLE);
        buttonToday2.setVisibility(View.INVISIBLE);
        buttonPick1.setVisibility(View.INVISIBLE);
        buttonPick2.setVisibility(View.INVISIBLE);
        buttonGo.setVisibility(View.INVISIBLE);

        outputBox.setVisibility(View.INVISIBLE);
        relativeLayout.setBackground(getResources().getDrawable(R.drawable.light));
        picking = true;
    }

    private void donePicking() {
        dateDisplay1.setVisibility(View.VISIBLE);
        dateDisplay2.setVisibility(View.VISIBLE);
        buttonToday1.setVisibility(View.VISIBLE);
        buttonToday2.setVisibility(View.VISIBLE);
        buttonPick1.setVisibility(View.VISIBLE);
        buttonPick2.setVisibility(View.VISIBLE);
        buttonGo.setVisibility(View.VISIBLE);

        relativeLayout.setBackground(getResources().getDrawable(R.drawable.fiji));
        picking = false;
    }

    private void setDatePickerVisibility(int id, int visibility) {
        currentId = id;
        myDatePicker.setVisibility(visibility);
        buttonOK.setVisibility(visibility);
        if (id > 0)
            beginPicking();
        else
            donePicking();
    }

    public void Button1Clicked(View v) {
        setDatePickerVisibility(1, View.VISIBLE);
    }

    public void Button2Clicked(View v) {
        setDatePickerVisibility(2, View.VISIBLE);
    }

    private Calendar getDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(myDatePicker.getYear(), myDatePicker.getMonth(), myDatePicker.getDayOfMonth(), 0, 0, 0);
        return calendar;
    }

    private Calendar getToday() {
        Calendar calendar = Calendar.getInstance();
        Date today = new Date();
        today.setHours(0);
        today.setMinutes(0);
        today.setSeconds(0);
        calendar.setTime(today);
        return calendar;
    }

    public void ButtonGoClick(View v) {
        try {
            if (!picking) {
                date1.set(Calendar.MILLISECOND, 0);
                date2.set(Calendar.MILLISECOND, 0);
                long diff = date2.getTimeInMillis() - date1.getTimeInMillis(); //result in millis
                long days = diff / (24 * 60 * 60 * 1000);
                String answer = Long.toString(days) + " days";
                Toast.makeText(getBaseContext(), answer , Toast.LENGTH_SHORT).show();
                outputBox.setText(answer);
                outputBox.setVisibility(View.VISIBLE);
            }
        } catch (Exception e) {
            Toast.makeText(getBaseContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public void ButtonOkHit(View v) {
        if (currentId == 1) {
            date1 = getDate();
            dateDisplay1.setText(dateToStr(date1.getTime()));
        }
        if (currentId == 2) {
            date2 = getDate();
            dateDisplay2.setText(dateToStr(date2.getTime()));
        }
        setDatePickerVisibility(0, View.INVISIBLE);
    }

    private void setAsToday() {
        if (currentId == 1) {
            date1 = getToday();
            dateDisplay1.setText(dateToStr(date1.getTime()));
        }
        if (currentId == 2) {
            date2 = getToday();
            dateDisplay2.setText(dateToStr(date2.getTime()));
        }
    }

    public void ButtonToday1Clicked(View v) {
        currentId = 1;
        setAsToday();
    }

    public void ButtonToday2Clicked(View v) {
        currentId = 2;
        setAsToday();
    }

}
