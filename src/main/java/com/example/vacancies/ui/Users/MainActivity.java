package com.example.vacancies.ui.Users;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.vacancies.ui.LoginActivity;
import com.example.vacancies.Model.Users;
import com.example.vacancies.Prevalent.Prevalent;
import com.example.vacancies.R;
import com.example.vacancies.ui.RegisterActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import io.paperdb.Paper;

public class MainActivity extends AppCompatActivity {

    private Button joinButton,loginButton;
    private ProgressDialog loadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        joinButton = (Button) findViewById(R.id.main_join_button);
        loginButton = (Button) findViewById(R.id.main_login_button);
        loadingBar = new ProgressDialog(this);

        Paper.init(this);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent LoginIntent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(LoginIntent);
            }
        });

        joinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent registerIntent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(registerIntent);
            }
        });

        String UserPhoneKey = Paper.book().read(Prevalent.UserPhoneKey);
        String UserPasswordKey = Paper.book().read(Prevalent.UserPasswordKey);

        if(UserPhoneKey != "" && UserPasswordKey != "") {
            if(!TextUtils.isEmpty(UserPhoneKey) && !TextUtils.isEmpty(UserPasswordKey)) {
                ValidateUser(UserPhoneKey,UserPasswordKey);

                loadingBar.setTitle("Вход в приложение");
                loadingBar.setMessage("Пожалуйста, подождите...");
                loadingBar.setCanceledOnTouchOutside(false);
                loadingBar.show();
            }
        }
    }

    private void ValidateUser(final String phone, final String password) {
        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();

        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.child("Users").child(phone).exists()) {
                    Users usersDate = snapshot.child("Users").child(phone).getValue(Users.class);

                    if(usersDate.getPhone().equals(phone)) {
                        if(usersDate.getPassword().equals(password)) {
                            loadingBar.dismiss();
                            Toast.makeText (MainActivity.this, "Успешний вход", Toast.LENGTH_SHORT).show();

                            Intent homeIntent = new Intent(MainActivity.this, HomeActivity.class);
                            startActivity(homeIntent);
                        }
                        else {
                            loadingBar.dismiss();
                        }
                    }
                }
                else {
                    loadingBar.dismiss();
                    Toast.makeText (MainActivity.this, "Аккаунт с номером" + phone + "не существует", Toast.LENGTH_SHORT).show();

                    Intent registerIntent = new Intent(MainActivity.this, RegisterActivity.class);
                    startActivity(registerIntent);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}