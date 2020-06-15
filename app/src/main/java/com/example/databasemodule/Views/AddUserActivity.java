package com.example.databasemodule.Views;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.databasemodule.AppDatabase;
import com.example.databasemodule.Controllers.UserDao;
import com.example.databasemodule.Executor;
import com.example.databasemodule.Models.User;
import com.example.databasemodule.R;
import com.example.databasemodule.TestApplication;

import java.util.ArrayList;

import javax.inject.Inject;

public class AddUserActivity extends AppCompatActivity {

    //Wstrzykiwanie bazy danych oraz odpowiedniego Dao
    @Inject
    AppDatabase mAppDatabase;
    UserDao userDao;

    EditText loginEditText;
    EditText passwordEditText;
    CheckBox adminCheckBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);

        //Wstrzykiwanie bazy danych oraz odpowiedniego Dao
        ((TestApplication) getApplication()).getComponent().inject(this);
        userDao = mAppDatabase.userDao();

        loginEditText = (EditText) findViewById(R.id.loginEditText);
        validate(loginEditText);
        passwordEditText = (EditText) findViewById(R.id.passwordEditText);
        validate(passwordEditText);
        adminCheckBox = (CheckBox) findViewById(R.id.adminCheckBox);
    }

    private void validate(EditText editText) {
        if (editText.length() == 0) {
            editText.setError("Field is required!");
        }
    }

    public void addUserToDatabase(View view) {
        boolean success = false;
        try {
            sendUserToDatabase();
            success = true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (success) {
            Toast.makeText(this, "Użytkownik został dodany do bazy", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "Uzupełnij pola.", Toast.LENGTH_SHORT).show();
        }
    }

    public void sendUserToDatabase() throws Exception {
        User user = new User();

        if (loginEditText.getText().toString().equals("") || passwordEditText.getText().toString().equals("")) {
            throw new Exception("Pole login lub haslo jest puste.");
        } else {
            user.login = loginEditText.getText().toString();
            user.password = passwordEditText.getText().toString();
            user.isAdmin = adminCheckBox.isChecked();
        }

        //Użycie Dao za pomocą wątku Executor
        //Przykład użycia w klasie Executor
        Executor.IOThread(() -> userDao.insertUsers(user));
    }
}