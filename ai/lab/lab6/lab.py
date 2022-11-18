import os
import numpy as np
import tensorflow as tf
import matplotlib.pyplot as plt

EPOCHS = 25

(x_train, y_train), (x_test, y_test) = tf.keras.datasets.mnist.load_data()

x_train = tf.keras.utils.normalize(x_train, axis=1)
x_test = tf.keras.utils.normalize(x_test, axis=1)

model = tf.keras.Sequential()
model.add(tf.keras.layers.Flatten(input_shape=(28, 28)))
model.add(tf.keras.layers.Dense(128, activation='sigmoid'))
# coz we will identify numbers from 0 ... 9
model.add(tf.keras.layers.Dense(10, activation='softmax'))

model.compile(optimizer='adam', loss='sparse_categorical_crossentropy',
              metrics=['accuracy'])

model.fit(x_train, y_train, epochs=EPOCHS)

model.save('h__m.model')

model = tf.keras.models.load_model('h__m.model')

loss, accuracy = model.evaluate(x_test, y_test)
print(loss, accuracy)

predict_x = model.predict(x_test)
predicted_classes = np.argmax(predict_x, axis=1)
# predicted_classes = model.predict_classes(x_test)
# see which we predicted correctly and which not
correct_indices = np.nonzero(predicted_classes == y_test)[0]
incorrect_indices = np.nonzero(predicted_classes != y_test)[0]

print(len(correct_indices), " classified correctly")
print(len(incorrect_indices), " classified incorrectly")
