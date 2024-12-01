package com.example.foodapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.foodapp.cartmanager.CartManager;
import com.example.foodapp.fooditem.FoodItem;

import java.util.ArrayList;

public class MyOrderActivity extends AppCompatActivity {

    ImageView back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_order);

        // Reference to the views
        back = findViewById(R.id.back);
        LinearLayout orderItemsContainer = findViewById(R.id.orderItemsContainer);
        LayoutInflater inflater = LayoutInflater.from(this);
        Button btnProcessOrder = findViewById(R.id.btnProcessOrder);
        Button btnCheckoutOrders = findViewById(R.id.btnCheckoutOrders); // Reference to checkout button
        TextView txtTotalPrice = findViewById(R.id.txtTotalPrice);
        TextView totalLabel = findViewById(R.id.totalLabel);  // Declare and find the totalLabel

        back.setOnClickListener(view ->{
            Intent intent = new Intent(this, FoodDashboard.class);
            startActivity(intent);
        });
        // Get the cart items from CartManager
        ArrayList<FoodItem> cartItems = CartManager.getInstance().getCartItems();

        double[] totalPrice = {0}; // Keep track of the total price

        // Add each food item in cartItems to the layout
        if (cartItems != null && !cartItems.isEmpty()) {
            for (int i = 0; i < cartItems.size(); i++) {
                FoodItem foodItem = cartItems.get(i);

                // Inflate food_item_card for each food item
                View foodItemView = inflater.inflate(R.layout.food_item_card, orderItemsContainer, false);

                // Find and set data to each view within food_item_card
                ImageView foodImage = foodItemView.findViewById(R.id.foodImage);
                TextView foodName = foodItemView.findViewById(R.id.foodName);
                TextView foodPrice = foodItemView.findViewById(R.id.foodPrice);
                Button btnDecrease = foodItemView.findViewById(R.id.btnDecreaseQuantity);
                Button btnIncrease = foodItemView.findViewById(R.id.btnIncreaseQuantity);
                EditText quantityInput = foodItemView.findViewById(R.id.quantityInput);
                ImageView btnCancelOrder = foodItemView.findViewById(R.id.btnCancelOrder);

                // Set food item details
                foodImage.setImageResource(foodItem.getImageResource());
                foodName.setText(foodItem.getName());
                foodPrice.setText(foodItem.getPrice());

                double price = 0;
                try {
                    // Clean and parse price as a double (remove '$' and any extra spaces)
                    String cleanedPrice = foodItem.getPrice().replace("$", "").trim();
                    price = Double.parseDouble(cleanedPrice);
                } catch (NumberFormatException e) {
                    Toast.makeText(MyOrderActivity.this, "Invalid price format", Toast.LENGTH_SHORT).show();
                }

                int[] quantity = {1}; // Default quantity is 1
                quantityInput.setText(String.valueOf(quantity[0]));

                // Handle quantity buttons
                btnDecrease.setOnClickListener(view -> {
                    if (quantity[0] > 1) {
                        quantity[0]--;
                        quantityInput.setText(String.valueOf(quantity[0]));
                    }
                });
                btnIncrease.setOnClickListener(view -> {
                    quantity[0]++;
                    quantityInput.setText(String.valueOf(quantity[0]));
                });

                // Cancel (remove) order item
                btnCancelOrder.setOnClickListener(view -> {
                    CartManager.getInstance().removeFromCart(foodItem);
                    orderItemsContainer.removeView(foodItemView);
                    Toast.makeText(MyOrderActivity.this, foodItem.getName() + " removed from your orders.", Toast.LENGTH_SHORT).show();
                });

                // Add the configured item view to the container
                orderItemsContainer.addView(foodItemView);
            }
        }

        // Process Order button functionality (calculating total)
        btnProcessOrder.setOnClickListener(view -> {
            totalPrice[0] = 0; // Reset total price
            for (int i = 0; i < orderItemsContainer.getChildCount(); i++) {
                View foodItemView = orderItemsContainer.getChildAt(i);
                EditText quantityInput = foodItemView.findViewById(R.id.quantityInput);
                TextView foodPrice = foodItemView.findViewById(R.id.foodPrice);

                int quantity = Integer.parseInt(quantityInput.getText().toString());

                // Clean and parse the food price (remove '$' and any extra spaces)
                String priceString = foodPrice.getText().toString().replace("$", "").trim();
                try {
                    double price = Double.parseDouble(priceString); // Parse the cleaned price string
                    totalPrice[0] += quantity * price;
                } catch (NumberFormatException e) {
                    // Handle the exception if parsing fails
                    Toast.makeText(MyOrderActivity.this, "Invalid price format", Toast.LENGTH_SHORT).show();
                }
            }

            // Add delivery service charge of 8$
            double deliveryCharge = 8.0;
            double overallTotal = totalPrice[0] + deliveryCharge;

            // Display the food total and overall total with delivery
            txtTotalPrice.setText(String.format("Item total: $%.2f", totalPrice[0]));
            totalLabel.setText(String.format("Total: $%.2f", overallTotal));
        });

        // Checkout button functionality (AlertDialog to confirm)
        btnCheckoutOrders.setOnClickListener(view -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(MyOrderActivity.this);
            builder.setTitle("Checkout Orders");
            builder.setMessage("Are you sure you want to check out your orders?");

            // Positive button: Yes
            builder.setPositiveButton("Yes", (dialog, which) -> {
                // Clear the cart after successful checkout
                CartManager.getInstance().clearCart(); // Clear the cart

                // Remove all food item views from the container
                orderItemsContainer.removeAllViews();

                // Reset the total price labels
                txtTotalPrice.setText("Item total: $0.00");
                totalLabel.setText("Total: $0.00");

                // Show a success message
                Toast.makeText(MyOrderActivity.this, "Order successfully placed and cart is reset!", Toast.LENGTH_SHORT).show();

                // Optional: You can navigate to a new activity (e.g., a confirmation screen)
                // Intent intent = new Intent(MyOrderActivity.this, ConfirmationActivity.class);
                // startActivity(intent);

                Intent intent = new Intent(this, FoodDashboard.class);
                startActivity(intent);
            });

            // Negative button: No
            builder.setNegativeButton("No", (dialog, which) -> {
                // Dismiss the dialog and do nothing
                dialog.dismiss();
            });

            // Show the dialog
            builder.create().show();
        });
    }


}

