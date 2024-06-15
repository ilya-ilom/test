package com.example.vacancies.ui.Admin;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.vacancies.R;
import com.example.vacancies.ui.LoginActivity;
import com.example.vacancies.ui.RegisterActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class AdminAddVacanciesActivity extends AppCompatActivity {

    private String categoryName, vName, Address,City,Description,Exp,Price,Skill,Time,Connect, saveCurrentDate, saveCurrentTime, vacancyRandomKey;
    private EditText vacancyName, vacancyAddress,vacancyCity,vacancyDescription,vacancyExp,vacancyPrice,vacancySkill,vacancyTime,vacancyConnect;
    private Button addNewVacancyButton;
    private DatabaseReference VacanciesRef;
    private ProgressDialog loadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_admin_add_vacancies);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        init();

        addNewVacancyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ValidateVacancyData();
            }
        });

    }

    private void ValidateVacancyData() {
        vName = vacancyName.getText().toString();
        Address = vacancyAddress.getText().toString();
        City = vacancyCity.getText().toString();
        Description = vacancyDescription.getText().toString();
        Exp = vacancyExp.getText().toString();
        Price = vacancyPrice.getText().toString();
        Skill = vacancySkill.getText().toString();
        Time = vacancyTime.getText().toString();
        Connect = vacancyConnect.getText().toString();

        if (TextUtils.isEmpty(vName)) {
            Toast.makeText (AdminAddVacanciesActivity.this, "Добавьте название вакансии", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(Address)) {
            Toast.makeText (AdminAddVacanciesActivity.this, "Добавьте адресс предприятия", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(City)) {
            Toast.makeText (AdminAddVacanciesActivity.this, "Добавьте город", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(Description)) {
            Toast.makeText (AdminAddVacanciesActivity.this, "Добавьте описание", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(Exp)) {
            Toast.makeText (AdminAddVacanciesActivity.this, "Добавьте опыт работы", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(Price)) {
            Toast.makeText (AdminAddVacanciesActivity.this, "Добавьте зароботную плату", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(Skill)) {
            Toast.makeText (AdminAddVacanciesActivity.this, "Добавьте ключевые навыки", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(Time)) {
            Toast.makeText (AdminAddVacanciesActivity.this, "Добавьте время работы", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(Connect)) {
            Toast.makeText (AdminAddVacanciesActivity.this, "Добавьте контактные данные", Toast.LENGTH_SHORT).show();
        } else {
            StoreVacancyInformation();
        }
    }

    private void StoreVacancyInformation() {

        loadingBar.setTitle("Загрузка данных");
        loadingBar.setMessage("Пожалуйста, подождите...");
        loadingBar.setCanceledOnTouchOutside(false);
        loadingBar.show();

        Calendar calendar = Calendar.getInstance();

        SimpleDateFormat currentDate = new SimpleDateFormat("aaMMyyyy");
        saveCurrentDate = currentDate.format(calendar.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HHMMSS");
        saveCurrentTime = currentTime.format(calendar.getTime());

        vacancyRandomKey = saveCurrentDate+saveCurrentTime;

        HashMap<String, Object> vacancyMap = new HashMap<>();

        vacancyMap.put("vid", vacancyRandomKey);
        vacancyMap.put("date", saveCurrentDate);
        vacancyMap.put("time", saveCurrentTime);
        vacancyMap.put("category", categoryName);
        vacancyMap.put("vname", vName);
        vacancyMap.put("address", Address);
        vacancyMap.put("city", City);
        vacancyMap.put("description", Description);
        vacancyMap.put("exp", Exp);
        vacancyMap.put("price", Price);
        vacancyMap.put("skill", Skill);
        vacancyMap.put("vtime", Time);
        vacancyMap.put("connect", Connect);

        VacanciesRef.child(vacancyRandomKey).updateChildren(vacancyMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()) {

                    loadingBar.dismiss();
                    Toast.makeText(AdminAddVacanciesActivity.this, "Вакансия добавлена", Toast.LENGTH_SHORT).show();

                    Intent CategoryIntent = new Intent(AdminAddVacanciesActivity.this, AdminCategoryActivity.class);
                    startActivity(CategoryIntent);
                }
                else {
                    String message = task.getException().toString();
                    Toast.makeText(AdminAddVacanciesActivity.this, "Ошибка: " + message, Toast.LENGTH_SHORT).show();
                    loadingBar.dismiss();
                }
            }
        });
    }

    private void init() {
        categoryName = getIntent().getExtras().get("category").toString();
        vacancyAddress = findViewById(R.id.vacancy_address);
        vacancyCity = findViewById(R.id.vacancy_city);
        vacancyName = findViewById(R.id.vacancy_name);
        vacancyDescription = findViewById(R.id.vacancy_description);
        vacancyExp = findViewById(R.id.vacancy_exp);
        vacancyPrice = findViewById(R.id.vacancy_price);
        vacancySkill = findViewById(R.id.vacancy_skill);
        vacancyTime = findViewById(R.id.vacancy_time);
        vacancyConnect = findViewById(R.id.vacancy_conect);
        addNewVacancyButton = findViewById(R.id.add_new_vacancy);
        VacanciesRef = FirebaseDatabase.getInstance().getReference().child("Vacancies");
        loadingBar = new ProgressDialog(this);
    }
}