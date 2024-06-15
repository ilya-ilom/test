package com.example.vacancies.ui.Admin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.vacancies.R;

public class AdminCategoryActivity extends AppCompatActivity {

    private TextView cat1, cat2, cat3, cat4;
    private TextView cat5, cat6, cat7, cat8;
    private TextView cat9, cat10, cat11, cat12;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_admin_category);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        init();

        cat1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminCategoryActivity.this, AdminAddVacanciesActivity.class);
                intent.putExtra("category", "cat1");
                startActivity(intent);
            }
        });
        cat2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminCategoryActivity.this, AdminAddVacanciesActivity.class);
                intent.putExtra("category", "cat2");
                startActivity(intent);
            }
        });
        cat3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminCategoryActivity.this, AdminAddVacanciesActivity.class);
                intent.putExtra("category", "cat3");
                startActivity(intent);
            }
        });
        cat4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminCategoryActivity.this, AdminAddVacanciesActivity.class);
                intent.putExtra("category", "cat4");
                startActivity(intent);
            }
        });
        cat5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminCategoryActivity.this, AdminAddVacanciesActivity.class);
                intent.putExtra("category", "cat5");
                startActivity(intent);
            }
        });
        cat6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminCategoryActivity.this, AdminAddVacanciesActivity.class);
                intent.putExtra("category", "cat6");
                startActivity(intent);
            }
        });
        cat7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminCategoryActivity.this, AdminAddVacanciesActivity.class);
                intent.putExtra("category", "cat7");
                startActivity(intent);
            }
        });
        cat8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminCategoryActivity.this, AdminAddVacanciesActivity.class);
                intent.putExtra("category", "cat8");
                startActivity(intent);
            }
        });
        cat9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminCategoryActivity.this, AdminAddVacanciesActivity.class);
                intent.putExtra("category", "cat9");
                startActivity(intent);
            }
        });
        cat10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminCategoryActivity.this, AdminAddVacanciesActivity.class);
                intent.putExtra("category", "cat10");
                startActivity(intent);
            }
        });
        cat11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminCategoryActivity.this, AdminAddVacanciesActivity.class);
                intent.putExtra("category", "cat11");
                startActivity(intent);
            }
        });
        cat12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminCategoryActivity.this, AdminAddVacanciesActivity.class);
                intent.putExtra("category", "cat12");
                startActivity(intent);
            }
        });

    }

    private void init() {
        cat1 = findViewById(R.id.cat1);
        cat2 = findViewById(R.id.cat2);
        cat3 = findViewById(R.id.cat3);
        cat4 = findViewById(R.id.cat4);

        cat5 = findViewById(R.id.cat5);
        cat6 = findViewById(R.id.cat6);
        cat7 = findViewById(R.id.cat7);
        cat8 = findViewById(R.id.cat8);

        cat9 = findViewById(R.id.cat9);
        cat10 = findViewById(R.id.cat10);
        cat11 = findViewById(R.id.cat11);
        cat12 = findViewById(R.id.cat12);
    }
}