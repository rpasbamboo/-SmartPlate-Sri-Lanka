package com.example.smartplate;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;



public class InputActivity extends AppCompatActivity {

    EditText ageEditText;
    RadioGroup genderRadioGroup;
    EditText weightEditText;
    EditText heightEditText;
    RadioGroup activityLevelRadioGroup;
    RadioGroup mealTypeRadioGroup;
    Spinner menuNoSpinner;
    Button predictButton;

    String[] menuOptions = {
            "Kiribath -1 slice",
            "Pol Roti -1 piece",
            "Pittu -1 piece",
            "Mung (Green gram) -1 cup",
            "Kadala (Chickpea) -1 cup",
            "Roasted bread with fish -1 piece",
            "Uppuma -1 cup",
            "Low fat milk -1 cup",
            "Kola Kanda -1 glass",
            "Yogurt -1 cup",
            "Avocado -1/2 medium",
            "Fruit juice -1 glass",
            "Dark chocolate -1 piece",
            "Banana -1 medium",
            "Red rice, chicken curry, beetroot curry, and gotukola sambol -1 plate",
            "Red rice, fish curry, green bean curry, and tomato and onion sambol -1 plate",
            "Red rice, jackfruit curry, eggplant moju, and cabbage mallung -1 plate",
            "Red rice, mung bean curry, pumpkin curry, and cucumber salad -1 plate",
            "Chicken curry, dhal curry, and mixed vegetable curry with rice -1 plate",
            "Fish curry, beetroot curry, and okra curry with rice -1 plate",
            "Potato curry, jackfruit curry, and green bean curry with rice -1 plate",
            "Orange -1 medium",
            "Guava -1 medium",
            "Apple -1 medium",
            "Peanuts -25 g",
            "Veralu -4-5 fruits",
            "Watermelon -1 cup",
            "Ambarella -1 medium",
            "String hoppers with fish -2 hoppers",
            "Hoppers with lunu miris -1 hopper",
            "Dosa with sambar -1 dosa",
            "Bread with dhal curry -1 slice",
            "Kurakkan thalapa with chicken curry -1 piece",
            "Egg noodles -1 cup",
            "Chapathi with gravy -1 piece"
    };

    int[] menuOptionValues = {
            30,
            32,
            26,
            38,
            41,
            25,
            30,
            12,
            23,
            15,
            9,
            33,
            12,
            27,
            27,
            33,
            33,
            37,
            47,
            47,
            47,
            15,
            12,
            25,
            5,
            22,
            11,
            14,
            40,
            10,
            20,
            15,
            40,
            15
    };


    // Retrieve the menu options array from the arrays.xml resource
    //String[] menuOptions = getResources().getStringArray(R.array.menu_options_array);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);

        ageEditText = findViewById(R.id.Age);
        genderRadioGroup = findViewById(R.id.gender_radio_group);
        weightEditText = findViewById(R.id.Weight);
        heightEditText = findViewById(R.id.Height);
        activityLevelRadioGroup = findViewById(R.id.activity_level_radio_group);
        mealTypeRadioGroup = findViewById(R.id.meal_type_radio_group);
        predictButton = findViewById(R.id.predict);

        /* Find the spinner view in the activity
        menuNoSpinner = findViewById(R.id.menu_no_spinner);

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.menu_options, android.R.layout.simple_spinner_item);

        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        menuNoSpinner.setAdapter(adapter);*/

        predictButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get input values
                int age = Integer.parseInt(ageEditText.getText().toString());

                int genderId = genderRadioGroup.getCheckedRadioButtonId();
                int gender = 0;

                switch (genderId) {
                    case R.id.female_radio_button:
                        gender = 1;
                        break;
                    case R.id.male_radio_button:
                        gender = 2;
                        break;
                }



                int weight = Integer.parseInt(weightEditText.getText().toString());
                int height = Integer.parseInt(heightEditText.getText().toString());

                int activityLevelId = activityLevelRadioGroup.getCheckedRadioButtonId();
                int activityLevel = 0;

                switch (activityLevelId) {
                    case R.id.light_radio_button:
                        activityLevel = 1;
                        break;
                    case R.id.moderate_radio_button:
                        activityLevel = 2;
                        break;
                    case R.id.extreme_radio_button:
                        activityLevel = 3;
                        break;
                }

                int mealTypeId = mealTypeRadioGroup.getCheckedRadioButtonId();
                int mealType = 0;

                switch (mealTypeId) {
                    case R.id.breakfast_radio_button:
                        mealType = 1;
                        break;
                    case R.id.morning_snack_radio_button:
                        mealType = 2;
                        break;
                    case R.id.lunch_radio_button:
                        mealType = 3;
                        break;
                    case R.id.afternoon_snack_radio_button:
                        mealType = 4;
                        break;
                    case R.id.dinner_radio_button:
                        mealType = 5;
                        break;
                }

                // Create a dialog with the menu options
                AlertDialog.Builder builder = new AlertDialog.Builder(InputActivity.this);
                int finalGender = gender;
                int finalActivityLevel = activityLevel;
                int finalMealType = mealType;
                builder.setTitle("Select Menu").setSingleChoiceItems(menuOptions, -1, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int selectedIndex) {
                                // Get the selected menu option
                                String selectedFood = menuOptions[selectedIndex];

                                // Adjust the index by adding 1
                                //int adjustedIndex = selectedIndex + 1;
                                // Retrieve the associated value
                                int selectedValue = menuOptionValues[selectedIndex];



                                // Pass the values to the ResultActivity
                                Intent intent = new Intent(InputActivity.this, ResultActivity.class);
                                intent.putExtra("age", age);
                                intent.putExtra("gender", finalGender);
                                intent.putExtra("weight", weight);
                                intent.putExtra("height", height);
                                intent.putExtra("activityLevel", finalActivityLevel);
                                intent.putExtra("mealType", finalMealType);
                                intent.putExtra("menuNo", selectedValue);
                                intent.putExtra("food", selectedFood);
                                startActivity(intent);

                                dialogInterface.dismiss(); // Dismiss the dialog
                            }
                        })
                        .setCancelable(true)
                        .show();
            }
        });
    }
}