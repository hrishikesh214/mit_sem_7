import numpy as np

EPOCH = 10000


def sigmoid(x):
    return 1/(1 + np.exp(-x))


def sigmoid_derivative(x):
    return x*(1-x)


training_input = np.array([
    [0, 0],
    [0, 1],
    [1, 0],
    [1, 1]
])

training_output = np.array([0, 0, 0, 1])  # and
# training_output = np.array([0,1,1,1]) # or

weights = [1]*len(training_input[0])

# print(training_input, training_output, weights)

outputs = None

for i in range(EPOCH):
    input_layer = training_input
    outputs = sigmoid(np.dot(input_layer, weights))
    error = training_output - outputs
    adjustments = error * sigmoid_derivative(outputs)
    weights += np.dot(input_layer.T, adjustments)


weights = [round(i, 2) for i in weights]

print('output:')
print(outputs)
print('weights:')
print(weights)
