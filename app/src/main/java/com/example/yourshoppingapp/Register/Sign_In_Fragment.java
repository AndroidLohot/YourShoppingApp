package com.example.yourshoppingapp.Register;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.text.TextUtils;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.yourshoppingapp.HomeActivity;
import com.example.yourshoppingapp.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Sign_In_Fragment extends Fragment {

    private EditText edEmail, edPassword;
    private TextView tvForgetPassword, tvCreateNewAccount;
    private ProgressBar progressBar;
    private CheckBox checkShowPassword;
    private Button btnSign;
    private ImageButton btnClose;


    public Sign_In_Fragment() {
        // Required empty public constructor
    }


    private void initializationViews(View view) {

        edEmail=view.findViewById(R.id.editText_Sign_Email);
        edPassword=view.findViewById(R.id.editText2_Sign_Password);

        tvForgetPassword=view.findViewById(R.id.textView2_Sign_ForgetPassword);
        tvCreateNewAccount=view.findViewById(R.id.textView3_Sign_CreateNewAccount);

        progressBar=view.findViewById(R.id.progressBar_Sign);
        checkShowPassword=view.findViewById(R.id.checkBox_Sign_ShowPassword);

        btnSign=view.findViewById(R.id.button_SignIn);
        btnClose=view.findViewById(R.id.imageButton_Sign_Close);

        progressBar.setVisibility(View.INVISIBLE);

    }

    private void changeFragment(Fragment fragment){

        FragmentTransaction ft=getActivity().getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.mainFrame,fragment).addToBackStack(null);
        ft.commit();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        
        View view = inflater.inflate(R.layout.fragment_sign, container, false);
        
        initializationViews(view);
        
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Forget password to find and create new password
        tvForgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                changeFragment(new Forget_Password_Fragment());

            }
        });

        // Creating new account
        tvCreateNewAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeFragment(new Sign_Up_Fragment());
            }
        });

        // Closing sign fragment without login to go home activity
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().startActivity(new Intent(getContext(), HomeActivity.class));
            }
        });

        checkShowPassword.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked){
                    edPassword.setTransformationMethod(null);
                }else {

                    edPassword.setTransformationMethod(new PasswordTransformationMethod());

                }
            }
        });

        btnSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = edEmail.getText().toString().trim();
                String password = edEmail.getText().toString().trim();

                if (checkingViewsIsEmpty()&&checkingEmailPassword(email,password)){

                    getActivity().startActivity(new Intent(getContext(), HomeActivity.class));
                }

            }
        });


    }


    private boolean checkingEmailPassword(String email, String password) {


        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);

        if (matcher.matches()){

            if (edPassword.length()>=5){
                return true;
            }else {

                edPassword.setError("Incorrect password");
                return false;

            }

        }else {

            edEmail.setError("Involved email");
            return false;

        }
    }

    private boolean checkingViewsIsEmpty() {

        String email = edEmail.getText().toString().trim();
        String password = edEmail.getText().toString().trim();


        if (TextUtils.isEmpty(email)){

            edEmail.setError("Enter the email id");
            return false;

        }else if (TextUtils.isEmpty(password)){

            edPassword.setError("Enter the password");
            return false;
        }else {
            return true;
        }
    }
}
