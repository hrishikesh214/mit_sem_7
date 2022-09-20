import os
import numpy as np
import tensorflow as tf
from tensorflow import keras

training_input = [
    [i] for i in range(1000)
]

training_output =[
    [i*i] for i in range(1000)
]

X_train, X_test = training_input[:800], training_input[800:]
y_train, y_test = training_output[:800], training_output[800:]

if False:
    # training model
    model = keras.Sequential()
    model.add(keras.layers.Dense(1, input_shape=(1,1)))
    model.compile(optimizer='sgd', loss='mse', metrics=['mse'])
    history = model.fit(X_train, y_train, epochs=10)
    model.save('squaring_model.h5')
    print(history)
else:
    # loading model
    model = keras.models.load_model('squaring_model.h5')
    print(model.predict([10]))