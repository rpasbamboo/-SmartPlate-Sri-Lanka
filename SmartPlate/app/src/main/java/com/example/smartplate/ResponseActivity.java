package com.example.smartplate;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ResponseActivity extends AppCompatActivity {

    TextView servingSizeTextView;
    TextView recCalTextView;
    TextView recTotCarbTextView;
    TextView recCarbTextView;
    Button returnButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_response);

        servingSizeTextView = findViewById(R.id.result);
        recCalTextView = findViewById(R.id.rec_cal);
        recTotCarbTextView = findViewById(R.id.rec_tot_carb);
        recCarbTextView = findViewById(R.id.rec_carb);
        returnButton = findViewById(R.id.return_button);

        // Get the response values from the intent
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String servingSize = extras.getString("servingSize");
            String recCal = extras.getString("recCal");
            String recTotCarb = extras.getString("recTotCarb");
            String recCarb = extras.getString("recCarb");

            // Update the TextViews with the response values
            servingSizeTextView.setText( servingSize);
            recCalTextView.setText( recCal);
            recTotCarbTextView.setText(recTotCarb);
            recCarbTextView.setText(recCarb);
        }

        // Set click listener for the return button
        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an intent to navigate back to InputActivity
                Intent intent = new Intent(ResponseActivity.this, InputActivity.class);
                startActivity(intent);
                finish(); // Optional: Finish the current activity to remove it from the back stack
            }
        });
    }
}
