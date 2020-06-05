package com.example.databasemodule.Views;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.databasemodule.AppDatabase;
import com.example.databasemodule.Controllers.ConfigDao;
import com.example.databasemodule.Controllers.UserDao;
import com.example.databasemodule.Executor;
import com.example.databasemodule.Models.Config;
import com.example.databasemodule.R;
import com.example.databasemodule.TestApplication;
import com.example.databasemodule.ValueFilter;

import javax.inject.Inject;

public class AddConfigActivity extends AppCompatActivity implements TextWatcher {

    //Wstrzykiwanie bazy danych oraz odpowiedniego Dao
    @Inject
    AppDatabase mAppDatabase;
    ConfigDao configDao;

    EditText tempFEditText;
    EditText humFEditText;
    EditText pressFEditText;
    EditText energyFEditText;
    EditText timestampEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_config);

        //Wstrzykiwanie bazy danych oraz odpowiedniego Dao
        ((TestApplication)getApplication()).getComponent().inject(this);
        configDao = mAppDatabase.configDao();

        tempFEditText = findViewById(R.id.tempFEditText);
        validate(tempFEditText);
        tempFEditText.setFilters( new InputFilter[]{ new ValueFilter( "1" , "60" )}) ;
        humFEditText = findViewById(R.id.humFEditText);
        validate(humFEditText);
        humFEditText.setFilters( new InputFilter[]{ new ValueFilter( "1" , "60" )}) ;
        pressFEditText = findViewById(R.id.pressFEditText);
        validate(pressFEditText);
        pressFEditText.setFilters( new InputFilter[]{ new ValueFilter( "1" , "60" )}) ;
        energyFEditText = findViewById(R.id.energyFEditText);
        validate(energyFEditText);
        energyFEditText.setFilters( new InputFilter[]{ new ValueFilter( "1" , "60" )}) ;
        timestampEditText = findViewById(R.id.timestampEditText);
        validate(timestampEditText);
        timestampEditText.setFilters( new InputFilter[]{ new ValueFilter( "1" , "60" )}) ;
    }

    private void validate(EditText editText) {
        if( editText.length() == 0 ){
            editText.setError( "Field is required!" );
        }
    }

    public void addConfigToDatabase(View view) {
        sendConfigToDatabase();
        Toast.makeText(this, "Dane konfiguracyjne zostały dodane do bazy", Toast.LENGTH_SHORT).show();
    }

    public void sendConfigToDatabase() {
        Config config = new Config();

        config.TEMP_F = Double.parseDouble(tempFEditText.getText().toString());
        config.HUM_F = Double.parseDouble(humFEditText.getText().toString());
        config.PRESS_F = Double.parseDouble(pressFEditText.getText().toString());
        config.ENERGY_F = Double.parseDouble(energyFEditText.getText().toString());
        config.TIMESTAMP = timestampEditText.getText().toString();

        //Użycie Dao za pomocą wątku Executor
        //Przykład użycia w klasie Executor
        Executor.IOThread(() -> configDao.insertAllConfigs(config));
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        try {

        }catch (NumberFormatException e){}
    }
}