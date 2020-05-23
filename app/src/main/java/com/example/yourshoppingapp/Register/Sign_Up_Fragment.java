package com.example.yourshoppingapp.Register;


import android.content.DialogInterface;
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
import android.widget.Toast;

import com.example.yourshoppingapp.HomeActivity;
import com.example.yourshoppingapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * A simple {@link Fragment} subclass.
 */
public class Sign_Up_Fragment extends Fragment implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    private EditText edFullName, edEmail, edPassword, edConfirmPassword;
    private CheckBox checkBoxShowPassword;
    private ImageButton btnClose;
    private Button btnRegister;
    private ProgressBar progressBar;
    private FirebaseAuth userAuth;
    private Object FirebaseAuthUserCollisionException;


    public Sign_Up_Fragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sign__up_, container, false);

        initializationViews(view);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btnRegister.setOnClickListener(this);
        btnClose.setOnClickListener(this);
        checkBoxShowPassword.setOnCheckedChangeListener(this);

    }

    @Override
    public void onClick(View v) {

        String email = edEmail.getText().toString().trim();
        String password = edPassword.getText().toString().trim();

        switch (v.getId()){

            case R.id.button_SignUp_Register:

                if (checkingViewsIsEmpty() && checkingEmailPassword(email,password)){



                    userAuth.createUserWithEmailAndPassword(email,password)
                            .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                                @Override
                                public void onSuccess(AuthResult authResult) {

                                    changeFragment(new Sign_In_Fragment());

                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {

                                    if (e==FirebaseAuthUserCollisionException){
                                        Toast.makeText(getContext()," Already have account",Toast.LENGTH_LONG).show();
                                    }

                                }
                            });


                }

                break;
            case R.id.imageButton_SignUp_Close:

                getActivity().startActivity(new Intent(getContext(), HomeActivity.class));

                break;


        }

    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

        if (isChecked){
            edPassword.setTransformationMethod(null);
            edConfirmPassword.setTransformationMethod(null);
        }else {

            edPassword.setTransformationMethod(new PasswordTransformationMethod());
            edConfirmPassword.setTransformationMethod(new PasswordTransformationMethod());

        }
    }


    private void initializationViews(View view){

        edFullName = view.findViewById(R.id.editText_SignUp_FullName);
        edEmail = view.findViewById(R.id.editText2_SignUp_Emall);
        edPassword  = view.findViewById(R.id.editText3_SignUp_Password);
        edConfirmPassword = view.findViewById(R.id.editText4_SignUp_ConfirmPassword);
        checkBoxShowPassword = view.findViewById(R.id.checkBox_SignUp_ShowPassword);
        btnRegister = view.findViewById(R.id.button_SignUp_Register);
        btnClose = view.findViewById(R.id.imageButton_SignUp_Close);
        progressBar = view.findViewById(R.id.progressBar_SignUp);

        progressBar.setVisibility(View.INVISIBLE);

        userAuth=FirebaseAuth.getInstance();

    }

    private boolean checkingViewsIsEmpty(){

        String fullName = edFullName.getText().toString().trim();
        String email = edEmail.getText().toString().trim();
        String password = edPassword.getText().toString().trim();
        String confirmPassword = edConfirmPassword.getText().toString().trim();

        if (TextUtils.isEmpty(fullName)){

            edFullName.setError("Enter the full name");
            return false;

        }else if (TextUtils.isEmpty(email)){

            edEmail.setError("Enter the email");
            return false;

        }else if (TextUtils.isEmpty(password)){

            edPassword.setError("Enter the password");
            return false;

        }else if (TextUtils.isEmpty(confirmPassword)){

            edConfirmPassword.setError("Enter the confirm password ");
            return false;
        }

        return true;
    }

    private void changeFragment(Fragment fragment){

        FragmentTransaction ft=getActivity().getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.mainFrame,fragment).addToBackStack(null);
        ft.commit();
    }

    private boolean checkingEmailPassword(String email, String password) {


        String confirmPassword = edConfirmPassword.getText().toString().trim();

        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);

        if (matcher.matches()){

            if (edPassword.length()>=5){

                if (password.equals(confirmPassword)){

                    return true;
                }else {

                    edConfirmPassword.setError("Wrong password entered");
                    return false;

                }
            }else {

                edConfirmPassword.setError("Password etlist length or 5 character");
                return false;

            }

        }else {

            edEmail.setError("Involved email");
            return false;

        }
    }


}
