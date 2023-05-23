from flask import Flask, jsonify, request
import pickle
import numpy as np
from sklearn.preprocessing import PolynomialFeatures


model = pickle.load(open('revisedPoly.pkl','rb'))

app = Flask(__name__)

@app.route('/')
def index():
    return "Hello world"


@app.route('/predict',methods=['POST'])
def predict():

    Age = float(request.form.get('Age'))
    Gender = float(request.form.get('Gender'))
    Weight = float(request.form.get('Weight'))
    Height = float(request.form.get('Height'))
    Level_of_Activity = float(request.form.get('Level_of_Activity'))
    Meal_Type = float(request.form.get('Meal_Type'))
    Menu_No = float(request.form.get('Menu_No'))

    # Calculate the necessary features using the input values
    # calculating necc

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
    rec_tot_carb = rec_cal * 0.45 * 0.25

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

    # Create a dictionary for the new data point with the calculated features
    new_data = {'Menu No': Menu_No,
                'Recommended Carbohydrate (g)': rec_carb}

    # Reshape the new data point to match the expected shape (36 features)
    poly_features = PolynomialFeatures(degree=7)
    input_data = poly_features.fit_transform(np.array([list(new_data.values())]))

    # Predict the serving size using the model
    result = model.predict(input_data)[0]

    # Create a response dictionary with the result and calculated values
    response = {
        'placement': str(result),
        'Recommended Calorie Per Day': rec_cal,
        'Recommended Total Carbohydrate(g) Per Day': rec_tot_carb,
        'Recommended Carbohydrate(g) for the Selected Meal': rec_carb
    }

    return jsonify(response)


if __name__ == '__main__':
    app.run(debug=True)