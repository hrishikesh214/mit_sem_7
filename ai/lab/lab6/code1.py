import os
import numpy as np
import tensorflow as tf
import cv2
from tensorflow import keras

mnist = keras.datasets.mnist
(X_train, y_train), (X_test, y_test) = mnist.load_data()

X_train = X_train / 255.0
X_test = X_test / 255.0

# training model
# model = keras.Sequential()
# model.add(keras.layers.Flatten(input_shape=(28,28)))
# model.add(keras.layers.Dense(128, activation=tf.nn.relu))
# model.add(keras.layers.Dense(10, activation=tf.nn.softmax))
# model.compile(optimizer='adam', loss='sparse_categorical_crossentropy', metrics=['accuracy'])
# history = model.fit(X_train, y_train, epochs=10)
# model.save('model.h5')
# print(history)

# loading model
model = keras.models.load_model('model.h5')
img = cv2.imread('./test_digit.png', 0)

# cv2.imshow('img', img)
# cv2.waitKey(0)
# img = cv2.resize(img, (28, 28))
img.resize(28, 28)
img = img / 255.0
x = (model.predict([img]))
print(np.argmax(x[0]))
