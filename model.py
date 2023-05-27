# -*- coding: utf-8 -*-
"""Untitled29.ipynb

Automatically generated by Colaboratory.

Original file is located at
    https://colab.research.google.com/drive/1WHBDdVYPLFIAA3xbIM1H40ZaacJDeMX9
"""



# -*- coding: utf-8 -*-
"""Untitled22.ipynb

Automatically generated by Colaboratory.

Original file is located at
    https://colab.research.google.com/drive/1AAr6-cqZfa-u5WOHW7h8JUm9OOmDztFB
"""

# make sure to import all of our modules
# sklearn package
from sklearn.preprocessing import PolynomialFeatures
from sklearn.linear_model import LinearRegression
from sklearn.metrics import mean_squared_error
# dataframes
import pandas as pd
# computation
import numpy as np
# visualization
import matplotlib.pyplot as plt

# Load the diabetes dataset
diabetes_df = pd.read_csv("SERV_DATA.csv")

# Select the predictor variables and the response variable
X = diabetes_df[[ 'Menu No',  'Recommended Carbohydrate (g)', ]]
y = diabetes_df['Serving Size']



print("****Checking the independent variables****")
# Have a glance at the independent variables
print(X)
print("****Checking the dependent variable****")
# Have a glance at the dependent variable
print(y)

# Split the data into training and testing sets
from sklearn.model_selection import train_test_split
X_train, X_test, y_train, y_test = train_test_split(X, y, test_size=0.2, random_state=0)

# Scale the input variables
from sklearn.preprocessing import StandardScaler
scaler = StandardScaler()
X_train = scaler.fit_transform(X_train)
X_test = scaler.transform(X_test)

print("****Checking the dependent variable****")

# Have a glance at the shape of the train and test sets
print(X_train.shape)
print(X_test.shape)
print(y_train.shape)
print(y_test.shape)

'''# check our accuracy for each degree, to find the  the loweest the error for a better model
number_degrees = [1,2,3,4,5,6,7]
plt_mean_squared_error = []
for degree in number_degrees:

   poly_model = PolynomialFeatures(degree=degree)
  
   poly_X = poly_model.fit_transform(X)
   poly_model.fit(poly_X, y)
  
   regression_model = LinearRegression()
   regression_model.fit(poly_X, y)
   y_pred = regression_model.predict(poly_X)
  
   plt_mean_squared_error.append(mean_squared_error(y, y_pred, squared=False))
  
plt.scatter(number_degrees,plt_mean_squared_error, color="green")
plt.plot(number_degrees,plt_mean_squared_error, color="red")'''

#define our polynomial model, with whatever degree we want
degree=7

# PolynomialFeatures  creates a new matrix consisting of all polynomial combinations 
# of the features with a degree less than or equal to the degree 
poly_model = PolynomialFeatures(degree=degree)

# transform out polynomial features
poly_X = poly_model.fit_transform(X)

# should be in the form [1, a, b, a^2, ab, b^2]
print(f'initial values {X}\nMapped to {poly_X}')

# [1, a=5, b=2940, a^2=25, 5*2940=14700, b^2=8643600]

#fit the model
poly_model.fit(poly_X, y)


#use linear regression as a base
#regression_model = LinearRegression()
# we use Ridge regression with a regularization parameter of alpha=0.1
from sklearn.linear_model import Ridge
regression_model = Ridge(alpha=0.1)

regression_model.fit(poly_X, y)

y_pred = regression_model.predict(poly_X)

#coefficients for each input feature
print("Coefficients", regression_model.coef_)

print(mean_squared_error(y, y_pred, squared=False))

from sklearn.metrics import r2_score, mean_squared_error, mean_absolute_error

print("R-squared:", r2_score(y, y_pred))
print("Mean Squared Error (MSE):", mean_squared_error(y, y_pred))
print("Root Mean Squared Error (RMSE):", mean_squared_error(y, y_pred, squared=False))
print("Mean Absolute Error (MAE):", mean_absolute_error(y, y_pred))

# Create a DataFrame with actual and predicted values
results_df = pd.DataFrame({'Actual': y, 'Predicted': y_pred})
print(results_df)

# Ask the user to input values for a new data point
#should extract the user account details for meal type and menu
Age = float(input("Enter your age: "))
Gender = float(input("Enter your gender(1 = Female / 2 = Male): "))
Weight = float(input("Enter weight in kgs: "))
Height  = float(input("Enter height in cm: "))
Level_of_Activity = float(input("How active are you (1 = light / 2 = Moderate / 3 = Extream): "))
Meal_Type = float(input("Enter your meal type(1= Breakfast / 2 = MorningSnack 3= Lunch / 4 = MorningSnack 5 = Dinner): "))
Menu_No  = float(input("Choose the menu no: "))

#calculating necc

# Calculate the "Recommended Calorie" using a Mifflin St Jeor equations
if Gender == '2':
        bmr = 10 * Weight + 6.25 * Height - 5 * Age + 5
else:
        bmr = 10 * Weight + 6.25 * Height - 5 * Age - 161

if Level_of_Activity == '1':
        rec_cal = bmr * 1.375
elif Level_of_Activity == '2':
        rec_cal = bmr * 1.55
else:
        rec_cal = bmr * 1.725

     

# Calculate "Recommended Total Carbohydrate(g)" using the American Diabetes Association (ADA) Recommendations
rec_tot_carb = rec_cal * 0.45  * 0.25

# Calculate "Recommended Carbohydrate (g)" using the American Diabetes Association (ADA) Recommendations
if Age >= 3:
    if Meal_Type in [1, 3, 5]:
      carb_percent = 0.30
    else: 
      carb_percent = 0.05
elif Age >= 4 and Age <= 8:
    if Meal_Type in [1, 3, 5]:
          carb_percent = 0.29
    else: 
            carb_percent = 0.07
elif Age >= 9 and Age <= 13:
        if Meal_Type in [1, 3, 5]:
          carb_percent = 0.28
        else: 
            carb_percent = 0.08
else:
    if Meal_Type in [1, 3, 5]:
          carb_percent = 0.26
    else:
            carb_percent = 0.11
    
rec_carb = rec_tot_carb * carb_percent

#####Not Needed for the selected  one
# Create a dictionary for the new data point with the calculated features

# Create a dictionary for the new data point with the calculated features
new_data = {'Menu No': Menu_No,
            'Recommended Carbohydrate (g)': rec_carb}         



# Reshape the new data point
new_data = np.array([list(new_data.values())]).reshape(1, -1)

# Define the polynomial features for the new data point
new_poly_features = poly_model.fit_transform(new_data)

# Predict the serving size using the new data point and the trained model
new_prediction = regression_model.predict(new_poly_features)

print(f"The predicted serving size is {new_prediction[0]}")

# Plot the actual vs predicted values
plt.scatter(y, y_pred)
plt.plot(y, y, color='red')
plt.xlabel('Actual Serving Size')
plt.ylabel('Predicted Serving Size')
plt.title('Actual vs Predicted Serving Size')
plt.show()

# Ask the user if they want to print additional features
print_additional_data = input("Do you want to know your Recommended Calorie and Recommended Carbohydrate amount? (yes or no): ")
if print_additional_data.lower() == 'yes':
 print("Recommended Calorie Per Day: ",rec_cal)
 print("Recommended Total Carbohydrate(g) Per Day: ",rec_tot_carb)
 print("Recommended Carbohydrate(g) for the Selected Meal: ",rec_carb)

import pickle
with open('revisedPoly.pkl', 'wb') as f:
    pickle.dump(regression_model, f)
with open('revisedPoly.pkl', 'rb') as f:
    model = pickle.load(f)