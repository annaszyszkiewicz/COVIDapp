package edu.ib.lista13zpo2020;

import androidx.appcompat.app.AppCompatActivity;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class AddActivity extends AppCompatActivity {
    SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        database = openOrCreateDatabase("COVID", MODE_PRIVATE, null);
    }

    public void addData(View view) {
        EditText country = (EditText) findViewById(R.id.txtCountry);

        StringBuilder response = new StringBuilder();
        String url = "https://coronavirus-19-api.herokuapp.com/countries/" + country.getText().toString();

        Thread thread = new Thread(() -> {

            try {
                URL obj = new URL(url);
                HttpURLConnection connection = (HttpURLConnection) obj.openConnection();
                connection.setRequestMethod("GET");
                int responseCode = connection.getResponseCode();
                System.out.println("Response " + responseCode);

                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputLine;

                while ((inputLine = in.readLine()) != null)
                    response.append(inputLine);

                in.close();

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try {
            Gson gson = new Gson();
            Log.d(MainActivity.TAG, response.toString());
            COVIDData covidData = gson.fromJson(response.toString(), COVIDData.class);

            String sqlCOVID = "INSERT INTO COVID VALUES (?,?,?,?,?)";
            SQLiteStatement statement = database.compileStatement(sqlCOVID);

            statement.bindString(1, covidData.getCountry());
            statement.bindLong(2, covidData.getCases());
            statement.bindLong(3, covidData.getActive());
            statement.bindLong(4, covidData.getCasesPerOneMillion());
            statement.bindLong(5, covidData.getTestsPerOneMillion());
            statement.executeInsert();
        } catch (Exception e) {
            Log.e(MainActivity.TAG, e.toString());
        }
    }
}