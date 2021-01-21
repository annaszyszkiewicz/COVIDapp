package edu.ib.lista13zpo2020;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class StatsActivity extends AppCompatActivity {
    SQLiteDatabase database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);

        database = openOrCreateDatabase("COVID",MODE_PRIVATE,null);

        TextView txtSum = (TextView)findViewById(R.id.txtSum);
        TextView txtHighestCured = (TextView)findViewById(R.id.txtHighestCured);
        TextView txtTests= (TextView)findViewById(R.id.txtTests);

        try {
            String sum = "SELECT SUM(cases) FROM COVID";
            Cursor cursor = database.rawQuery(sum, null);
            cursor.moveToFirst();
            txtSum.setText("SUM OF CASES: " + cursor.getInt(0));
            cursor.close();

            String highestCursed = "SELECT Country_name FROM COVID ORDER BY (cases-active)/cases desc limit 1";
            cursor = database.rawQuery(highestCursed, null);
            cursor.moveToFirst();
            txtHighestCured.setText("HIGHEST CURED PERCENT IN: " + cursor.getInt(0));

            String tests = "SELECT Country_name FROM COVID ORDER BY testsPerOneMillion desc";
            cursor = database.rawQuery(tests, null);
            cursor.moveToFirst();
            StringBuilder countries = new StringBuilder();
            while (!cursor.isAfterLast()) {
                countries.append(cursor.getString(0)).append(", ");
                cursor.moveToNext();
            }
            txtTests.setText("TESTS PER ONE MILLION (DESCENDING): " + countries.toString().substring(0, countries.length() - 2));
        }
        catch (Exception e){
            Log.e(MainActivity.TAG, e.toString());
            txtSum.setText("SUM OF CASES: null");
            txtHighestCured.setText("HIGHEST CURED PERCENT IN: null");
            txtTests.setText("TESTS PER ONE MILLION (DESCENDING): null");
        }
    }
}