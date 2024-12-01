package com.example.foodapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class FoodDashboard extends AppCompatActivity {

    Button button_navigate;
    CardView spicyPizza,burger,fries,pork,chicken,beef,shanghai,pancit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.food_dashboard);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        button_navigate = findViewById(R.id.button_navigate);
        spicyPizza = findViewById(R.id.spicyPizza);
        burger = findViewById(R.id.burger);
        fries = findViewById(R.id.fries);
        pork = findViewById(R.id.pork);
        chicken = findViewById(R.id.chicken);
        beef = findViewById(R.id.beef);
        shanghai = findViewById(R.id.shanghai);
        pancit = findViewById(R.id.pancit);


        button_navigate.setOnClickListener(view ->{
            Intent intent = new Intent(FoodDashboard.this, MyOrderActivity.class);
            startActivity(intent);
        });

        spicyPizza.setOnClickListener(view ->{
            Intent intent = new Intent(FoodDashboard.this, SpicyPizzaActivity.class);
            startActivity(intent);
        });

        burger.setOnClickListener(view ->{
            Intent intent = new Intent(FoodDashboard.this, BurgerActivity.class);
            startActivity(intent);
        });

        fries.setOnClickListener(view ->{
            Intent intent = new Intent(FoodDashboard.this, FriesActivity.class);
            startActivity(intent);
        });

        pork.setOnClickListener(view ->{
            Intent intent = new Intent(FoodDashboard.this, SmokedPorkActivity.class);
            startActivity(intent);
        });

        chicken.setOnClickListener(view ->{
            Intent intent = new Intent(FoodDashboard.this, RoastedChickenActivity.class);
            startActivity(intent);
        });

        beef.setOnClickListener(view ->{
            Intent intent = new Intent(FoodDashboard.this, GordonBeefActivity.class);
            startActivity(intent);
        });

        shanghai.setOnClickListener(view ->{
            Intent intent = new Intent(FoodDashboard.this, com.example.foodapp.shanghai.class);
            startActivity(intent);
        });

        pancit.setOnClickListener(view ->{
            Intent intent = new Intent(FoodDashboard.this, PancitBihonActivity.class);
            startActivity(intent);
        });



    }
}