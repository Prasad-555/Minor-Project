package com.developer.opdmanager;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class BookingActivity extends AppCompatActivity {

    private TextView selectedDateText;
    private TabItem selectDateIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.schedule);

        TabLayout tabLayout = findViewById(R.id.tabLayoutDates);

        String today = getFormattedDate(0);
        String tomorrow = getFormattedDate(1);
        String dayAfterTomorrow = getFormattedDate(2);
        if (tabLayout.getTabAt(0) != null) {
            tabLayout.getTabAt(0).setText("Today\n" + today);
        }
        if (tabLayout.getTabAt(1) != null) {
            tabLayout.getTabAt(1).setText("Tomorrow\n" + tomorrow);
        }
        if (tabLayout.getTabAt(2) != null) {
            tabLayout.getTabAt(2).setText("Day After\n" + dayAfterTomorrow);
        }

         // Reference TabLayout, not TabItem
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition(); // Get the selected tab position
                Log.d("BookingActivity", "Tab selected: " + position);

                // Perform actions based on the selected tab
                switch (position) {
                    case 0:
                        // Handle first tab click
                        break;
                    case 1:
                        // Handle second tab click
                        break;
                    case 3:
                        showDatePicker();
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}

            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });

    }

    private void showDatePicker() {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                (DatePicker view, int selectedYear, int selectedMonth, int selectedDay) -> {
                    // Update the TextView with the selected date
                    calendar.set(selectedYear, selectedMonth, selectedDay);
                    SimpleDateFormat sdf = new SimpleDateFormat("EEEE, dd MMM", Locale.getDefault());
                    String formattedDate = sdf.format(calendar.getTime());
                    selectedDateText.setText(formattedDate);
                }, year, month, day);

        datePickerDialog.show();
    }
    private String getFormattedDate(int daysToAdd) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, daysToAdd); // Add days to current date
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM", Locale.getDefault());
        return dateFormat.format(calendar.getTime());
    }
}
