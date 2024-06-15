package com.example.vacancies.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.vacancies.R;
import com.example.vacancies.Model.Users;
import com.example.vacancies.Prevalent.Prevalent;
import com.example.vacancies.ui.Admin.AdminCategoryActivity;
import com.example.vacancies.ui.Users.HomeActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import io.paperdb.Paper;

public class LoginActivity extends AppCompatActivity {

    private Button loginBtn;
    private EditText phoneInput, passwordInput;
    private ProgressDialog loadingBar;
    private TextView AdminLink, NotAdminLink;

    private String parentDbName = "Users";
    private CheckBox checkBoxRememberMe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        loginBtn = (Button)findViewById(R.id.login_button);
        phoneInput = (EditText) findViewById(R.id.login_phone_input);
        passwordInput = (EditText) findViewById(R.id.login_password_input);
        loadingBar = new ProgressDialog(this);
        checkBoxRememberMe = (CheckBox)findViewById(R.id.login_checkbox);
        Paper.init(this);

        AdminLink = (TextView)findViewById(R.id.admin_panel_link);
        NotAdminLink = (TextView)findViewById(R.id.not_admin_panel_link);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginUser();
            }
        });

        AdminLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AdminLink.setVisibility(View.INVISIBLE);
                NotAdminLink.setVisibility(View.VISIBLE);
                loginBtn.setText("Вход для админа");
                parentDbName = "Admins";
            }
        });
        NotAdminLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AdminLink.setVisibility(View.VISIBLE);
                NotAdminLink.setVisibility(View.INVISIBLE);
                loginBtn.setText("Войти");
                parentDbName = "Users";
            }
        });
    }

    private void loginUser() {
        String phone = phoneInput.getText().toString();
        String password = passwordInput.getText().toString();

        if(TextUtils.isEmpty(phone)) {
            Toast.makeText(this, "Введите номер", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Введите пароль", Toast.LENGTH_SHORT).show();
        }
        else {
            loadingBar.setTitle("Вход в приложение");
            loadingBar.setMessage("Пожалуйста, подождите...");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();

            ValidateUser(phone, password);
        }
    }

    private void ValidateUser(String phone, String password) {

        if(checkBoxRememberMe.isChecked()) {
            Paper.book().write(Prevalent.UserPhoneKey, phone);
            Paper.book().write(Prevalent.UserPasswordKey, password);
        }

        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();

        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.child(parentDbName).child(phone).exists()) {
                    Users usersDate = snapshot.child(parentDbName).child(phone).getValue(Users.class);

                    if(usersDate.getPhone().equals(phone)) {
                        if(usersDate.getPassword().equals(password)) {
                            if(parentDbName.equals("Users")){
                                loadingBar.dismiss();
                                Toast.makeText (LoginActivity.this, "Успешний вход", Toast.LENGTH_SHORT).show();

                                Intent homeIntent = new Intent(LoginActivity.this, HomeActivity.class);
                                startActivity(homeIntent);
                            }
                            else if (parentDbName.equals("Admins")){
                                loadingBar.dismiss();
                                Toast.makeText (LoginActivity.this, "Успешний вход", Toast.LENGTH_SHORT).show();

                                Intent adminIntent = new Intent(LoginActivity.this, AdminCategoryActivity.class);
                                startActivity(adminIntent);
                            }
                        }
                        else {
                            loadingBar.dismiss();
                            Toast.makeText (LoginActivity.this, "Неверный пароль", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                else {
                    loadingBar.dismiss();
                    Toast.makeText (LoginActivity.this, "Аккаунт с номером" + phone + "не существует", Toast.LENGTH_SHORT).show();

                    Intent registerIntent = new Intent(LoginActivity.this, RegisterActivity.class);
                    startActivity(registerIntent);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}