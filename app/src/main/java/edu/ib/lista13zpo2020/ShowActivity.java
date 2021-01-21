package edu.ib.lista13zpo2020;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class ShowActivity extends AppCompatActivity {
    SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);

        database = openOrCreateDatabase("COVID", MODE_PRIVATE, null);

        ArrayList<String> wyniki = new ArrayList<>();
        Cursor c = database.rawQuery("SELECT * FROM COVID", null);
        c.moveToFirst();

        try {
            while (!c.isAfterLast()) {

                String country = c.getString(c.getColumnIndex("Country_name"));
                int cases = c.getInt(c.getColumnIndex("Cases"));
                int active = c.getInt(c.getColumnIndex("Active"));
                int casesPerMillion = c.getInt(c.getColumnIndex("casesPerOneMillion"));
                int testsPerMillion = c.getInt(c.getColumnIndex("testsPerOneMillion"));

                wyniki.add("Country: " + country + ", C/A: " + cases + "/" + active + " cPerOneM: " + casesPerMillion + " tPerOneM: " + testsPerMillion);
                c.moveToNext();
            }
            c.close();

            ListView lvData = (ListView) findViewById(R.id.lvData);
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, wyniki);
            lvData.setAdapter(adapter);
        }
        catch (Exception e){
            Log.e(MainActivity.TAG, e.toString());
        }

    }
}