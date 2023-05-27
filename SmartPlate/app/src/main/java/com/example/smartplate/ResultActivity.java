package com.example.smartplate;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

public class ResultActivity extends AppCompatActivity {

    TextView servingSizeTextView;
    TextView recCalTextView;
    TextView recTotCarbTextView;
    TextView recCarbTextView;
    TextView selectedMenuTextView;
    TextView selectedFoodTextView;
    TextView selectedAgeTextView;
    TextView selectedGenderTextView;
    TextView selectedWeightTextView;
    TextView selectedHeightTextView;
    TextView selectedLATextView;
    TextView selectedMealTextView;
    Button confirmButton;
    Button cancelButton;

    String url = "https://smartplate-sri-lanka.onrender.com/predict";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        /*servingSizeTextView = findViewById(R.id.result);
        recCalTextView = findViewById(R.id.rec_cal);
        recTotCarbTextView = findViewById(R.id.rec_tot_carb);
        recCarbTextView = findViewById(R.id.rec_carb);*/

        selectedMenuTextView = findViewById(R.id.selected_menu);
        selectedFoodTextView = findViewById(R.id.selected_food);
        selectedAgeTextView = findViewById(R.id.selected_age);
        selectedGenderTextView = findViewById(R.id.selected_gender);
        selectedWeightTextView = findViewById(R.id.selected_weight);
        selectedHeightTextView = findViewById(R.id.selected_height);
        selectedLATextView = findViewById(R.id.selected_la);
        selectedMealTextView = findViewById(R.id.selected_meal);
        confirmButton = findViewById(R.id.confirm_button);
        cancelButton = findViewById(R.id.cancel_button);

        /*servingSizeTextView.setText("");
        recCalTextView.setText("");
        recTotCarbTextView.setText("");
        recCarbTextView.setText("");*/

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            int age = extras.getInt("age");
            int gender = extras.getInt("gender");
            int weight = extras.getInt("weight");
            int height = extras.getInt("height");
            int activityLevel = extras.getInt("activityLevel");
            int mealType = extras.getInt("mealType");
            int menuNo = extras.getInt("menuNo");
            String food = extras.getString("food");

            selectedMenuTextView.setText("You have selected: Menu " + menuNo);
            selectedFoodTextView.setText("Standard serving size is " + food);
            selectedAgeTextView.setText("Your age is " + age);
            selectedGenderTextView.setText("You have selected: Gender " + gender);
            selectedWeightTextView.setText("Your weight is " + weight);
            selectedHeightTextView.setText("Your height is " + height);
            selectedLATextView.setText("You have selected: Activity Level " + activityLevel);
            selectedMealTextView.setText("You have selected: Meal Type " + mealType);

            confirmButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    try {
                                        JSONObject jsonObject = new JSONObject(response);
                                        String servingSize = jsonObject.getString("Serving Size");
                                        String recCal = jsonObject.getString("Recommended Calorie Per Day");
                                        String recTotCarb = jsonObject.getString("Recommended Total Carbohydrate(g) Per Day");
                                        String recCarb = jsonObject.getString("Recommended Carbohydrate(g) for the Selected Meal");

                                        DecimalFormat decimalFormat = new DecimalFormat("#.#");
                                        servingSize = decimalFormat.format(Double.parseDouble(servingSize));
                                        recCal = decimalFormat.format(Double.parseDouble(recCal));
                                        recTotCarb = decimalFormat.format(Double.parseDouble(recTotCarb));
                                        recCarb = decimalFormat.format(Double.parseDouble(recCarb));

                                        /*servingSizeTextView.setText("Serving Size: " + servingSize);
                                        recCalTextView.setText("Recommended Calorie: " + recCal);
                                        recTotCarbTextView.setText("Recommended Total Carbohydrate: " + recTotCarb);
                                        recCarbTextView.setText("Recommended Carbohydrate for the Selected Meal: " + recCarb);*/

                                        Intent intent = new Intent(ResultActivity.this, ResponseActivity.class);
                                        intent.putExtra("servingSize", servingSize);
                                        intent.putExtra("recCal", recCal);
                                        intent.putExtra("recTotCarb", recTotCarb);
                                        intent.putExtra("recCarb", recCarb);
                                        startActivity(intent);
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Toast.makeText(ResultActivity.this, "Error: Something went wrong", Toast.LENGTH_SHORT).show();
                                }
                            }) {

                        @Override
                        protected Map<String, String> getParams() {
                            Map<String, String> params = new HashMap<String, String>();
                            params.put("Age", String.valueOf(age));
                            params.put("Gender", String.valueOf(gender));
                            params.put("Weight", String.valueOf(weight));
                            params.put("Height", String.valueOf(height));
                            params.put("Level_of_Activity", String.valueOf(activityLevel));
                            params.put("Meal_Type", String.valueOf(mealType));
                            params.put("Menu_No", String.valueOf(menuNo));
                            return params;
                        }
                    };

                    RequestQueue queue = Volley.newRequestQueue(ResultActivity.this);
                    queue.add(stringRequest);
                }
            });

            cancelButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        }
    }
}
