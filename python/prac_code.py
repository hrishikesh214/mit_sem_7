# set

# set creation
num_list = [1, 1, 4, 3, 2, 6, 7, 4, 9, 2]
num_set = set(num_list)
print('Num Set:', num_set)

# set add
num_set.add(10)
print('Adding 10:', num_set)

# set remove
num_set.remove(10)
print('Removing 10:', num_set)

# set operations

# union
num_set1 = set([1, 2, 3, 4, 5])
num_set2 = set([3, 4, 5, 6, 7])
print('Set 1:', num_set1)
print('Set 2:', num_set2)
print('Union:', num_set1 | num_set2)

# intersection
print('Intersection:', num_set1 & num_set2)

# difference
print('Difference:', num_set1 - num_set2)

# symmetric difference
print('Symmetric Difference:', num_set1 ^ num_set2)

# Frozen Set

# frozen set creation
num_set = frozenset([1, 2, 3, 4, 5])
print('Frozen Set:', num_set)
