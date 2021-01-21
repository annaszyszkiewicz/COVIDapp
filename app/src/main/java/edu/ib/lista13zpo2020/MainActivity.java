package edu.ib.lista13zpo2020;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    SQLiteDatabase database;
    public static final String TAG = "EDUIB";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        database = openOrCreateDatabase("COVID",MODE_PRIVATE,null);
        String drop = "DROP TABLE IF EXISTS COVID";
        database.execSQL(drop);
        String sqlDB = "CREATE TABLE IF NOT EXISTS COVID (Country_name VARCHAR primary key, Cases INTEGER, Active INTEGER, casesPerOneMillion INTEGER, testsPerOneMillion INTEGER)";
        database.execSQL(sqlDB);

    }

    public void add(View view) {
        Intent intent = new Intent(this,AddActivity.class);
        startActivity(intent);
    }

    public void showData(View view) {
        Intent intent = new Intent(this,ShowActivity.class);
        startActivity(intent);
    }

    public void getStats(View view) {
        Intent intent = new Intent(this,StatsActivity.class);
        startActivity(intent);
    }
}