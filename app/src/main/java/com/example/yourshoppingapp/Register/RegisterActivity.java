package com.example.yourshoppingapp.Register;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;

import com.example.yourshoppingapp.R;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        checkingFragment(new Sign_In_Fragment());


    }

    private void checkingFragment(Fragment fragment) {

        int ft=getSupportFragmentManager().beginTransaction()
                .replace(R.id.mainFrame,fragment).commit();

    }
}
