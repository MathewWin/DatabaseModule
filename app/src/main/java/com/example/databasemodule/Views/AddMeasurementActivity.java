package com.example.databasemodule.Views;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.InputFilter;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.databasemodule.AppDatabase;
import com.example.databasemodule.ValueFilter;
import com.example.databasemodule.Controllers.MeasurementDao;
import com.example.databasemodule.Executor;
import com.example.databasemodule.Models.Measurement;
import com.example.databasemodule.Models.User;
import com.example.databasemodule.R;
import com.example.databasemodule.TestApplication;

import java.util.ArrayList;

import javax.inject.Inject;

public class AddMeasurementActivity extends AppCompatActivity {

    //Wstrzykiwanie bazy danych oraz odpowiedniego Dao
    @Inject
    AppDatabase mAppDatabase;
    MeasurementDao measurementDao;

    EditText tempEditText;
    EditText humEditText;
    EditText pressEditText;
    EditText batVEditText;
    EditText batIEditText;
    EditText solarVEditText;
    EditText solarIEditText;
    EditText nodeUEditText;
    EditText nodeIEditText;

    public ArrayList<User> myDataset;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_measurement);

        //Wstrzykiwanie bazy danych oraz odpowiedniego Dao
        ((TestApplication)getApplication()).getComponent().inject(this);
        measurementDao = mAppDatabase.measurementDao();

        tempEditText = findViewById(R.id.tempEditText);
        validate(tempEditText);
        humEditText = findViewById(R.id.humEditText);
        validate(humEditText);
        pressEditText = findViewById(R.id.pressEditText);
        validate(pressEditText);
        batVEditText = findViewById(R.id.batVEditText);
        validate(batVEditText);
        batVEditText.setFilters( new InputFilter[]{ new ValueFilter( "1" , "9" )}) ;
        batIEditText = findViewById(R.id.batIEditText);
        validate(batIEditText);
        batVEditText.setFilters( new InputFilter[]{ new ValueFilter( "1" , "9" )}) ;
        solarVEditText = findViewById(R.id.solarVEditText);
        validate(solarVEditText);
        solarVEditText.setFilters( new InputFilter[]{ new ValueFilter( "1" , "45" )}) ;
        solarIEditText = findViewById(R.id.solarIEditText);
        validate(solarIEditText);
        solarIEditText.setFilters( new InputFilter[]{ new ValueFilter( "1" , "9" )}) ;
        nodeUEditText = findViewById(R.id.nodeUEditText);
        validate(nodeUEditText);
        nodeUEditText.setFilters( new InputFilter[]{ new ValueFilter( "1" , "100" )}) ;
        nodeIEditText = findViewById(R.id.nodeIEditText);
        validate(nodeIEditText);
        nodeIEditText.setFilters( new InputFilter[]{ new ValueFilter( "1" , "9" )}) ;

        myDataset = new ArrayList();
    }

    private void validate(EditText editText) {
        if( editText.length() == 0 ){
            editText.setError( "Field is required!" );
        }
    }

    public void addMesurementToDatabase(View view) {
        sendMeasurementToDatabase();
        Toast.makeText(this, "Dodano zmienne pomiarowe do bazy danych", Toast.LENGTH_SHORT).show();
    }

    public void sendMeasurementToDatabase() {
        Measurement measurement = new Measurement();

        measurement.TEMP = Double.parseDouble(tempEditText.getText().toString());
        measurement.HUM = Double.parseDouble(humEditText.getText().toString());
        measurement.PRESS = Double.parseDouble(pressEditText.getText().toString());
        measurement.BAT_V = Double.parseDouble(batVEditText.getText().toString());
        measurement.BAT_I = Double.parseDouble(batIEditText.getText().toString());
        measurement.SOLAR_V = Double.parseDouble(solarVEditText.getText().toString());
        measurement.SOLAR_I = Double.parseDouble(solarIEditText.getText().toString());
        measurement.NODE_U = Double.parseDouble(nodeUEditText.getText().toString());
        measurement.NODE_I = Double.parseDouble(nodeIEditText.getText().toString());

        //Użycie Dao za pomocą wątku Executor
        //Przykład użycia w klasie Executor
        Executor.IOThread(() -> measurementDao.insertAllMeasurements(measurement));
    }
}