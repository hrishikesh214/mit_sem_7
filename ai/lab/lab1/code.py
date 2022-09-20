import copy
from heapq import heappush, heappop  # priority queue

n = 3  # (3 => 8 - puzzle) (4 => 15 - puzzle)

# bottom, left, top, right => x and y for four direction movement
row = [1, 0, -1, 0]
col = [0, -1, 0, 1]

# A class for Priority Queue


class priorityQueue:
    def __init__(self):
        self.heap = []

    def push(self, k):
        '''
        insert into queue
        '''
        heappush(self.heap, k)

    def pop(self):
        """
        removes minimum element -> high priority
        """
        return heappop(self.heap)

    def empty(self):
        """
        returns true if queue is empty 
        """
        return True if not self.heap else False

# Node structure


class node:

    def __init__(self, parent, mat, empty_tile_pos,
                 cost, level):
        self.parent = parent  # stores parent of current node for backtracing
        self.mat = mat  # puzzle at current poiunt
        self.empty_tile_pos = empty_tile_pos  # x,y of empty tile
        self.cost = cost  # cost function value
        self.level = level  # current level in the tree

    def __lt__(self, b):
        """
        overloading less than operator for comparing with cost
        """
        return self.cost < b.cost


def calculateCost(mat, final) -> int:
    """
    calculates cost -> no. of misplaced tiles
    """
    count = 0
    for i in range(n):
        for j in range(n):
            if ((mat[i][j]) and
                    (mat[i][j] != final[i][j])):
                count += 1

    return count


def newNode(mat, empty_tile_pos, new_empty_tile_pos,
            level, parent, final) -> node:
    """
    generates new child node and returns it
    """
    # 1. Copy data from parent matrix to current matrix
    new_mat = copy.deepcopy(mat)

    # 2. Move tile by 1 position
    x1 = empty_tile_pos[0]
    y1 = empty_tile_pos[1]
    x2 = new_empty_tile_pos[0]
    y2 = new_empty_tile_pos[1]
    new_mat[x1][y1], new_mat[x2][y2] = new_mat[x2][y2], new_mat[x1][y1]

    # 3. Set number of misplaced tiles
    cost = calculateCost(new_mat, final)

    new_node = node(parent, new_mat, new_empty_tile_pos,
                    cost, level)
    return new_node


def printMatrix(mat):
    """
    this will hepl printing the puzzle
    """
    for i in range(n):
        for j in range(n):
            print("%d " % (mat[i][j]), end=" ")
        print()


def isSafe(x, y):
    """
    just checks if x, y doesnt overflow the puzzle board
    """
    return x >= 0 and x < n and y >= 0 and y < n


def printPath(root):
    """
    prints path from current node to root node using recursion
    """
    if root == None:
        return

    printPath(root.parent)
    printMatrix(root.mat)
    print()


def solve(initial, empty_tile_pos, final):
    """
    this function will solve the puzzle
    """
    pq = priorityQueue()

    # Create the root node
    cost = calculateCost(initial, final)
    root = node(None, initial,
                empty_tile_pos, cost, 0)

    # Add root to list of live nodes
    pq.push(root)

    while not pq.empty():
        minimum = pq.pop()  # get the node with least estimated cost and process it first

        if minimum.cost == 0:
            # we have reached goal state
            # Print the path
            print('\nPath: \n')
            printPath(minimum)
            return

        # Generate all possible children
        for i in range(4):
            new_tile_pos = [
                minimum.empty_tile_pos[0] + row[i],
                minimum.empty_tile_pos[1] + col[i], ]

            if isSafe(new_tile_pos[0], new_tile_pos[1]):
                # Create a new child node
                child = newNode(minimum.mat,
                                minimum.empty_tile_pos,
                                new_tile_pos,
                                minimum.level + 1,
                                minimum, final,)

                # push it to queue
                pq.push(child)


def get_input():
    initial = [[None for i in range(n)] for j in range(n)]
    final = [[None for i in range(n)] for j in range(n)]
    empty_tile_pos = []

    print('\nEnter Initial state: ')
    for i in range(n):
        for j in range(n):
            initial[i][j] = int(input())
            if initial[i][j] == 0:
                empty_tile_pos = [i, j]

    print('\nEnter Goal state: ')
    for i in range(n):
        for j in range(n):
            final[i][j] = int(input())

    return initial, final, empty_tile_pos


initial, final, empty_tile_pos = get_input()

print('\nProcessing...\n')

solve(initial, empty_tile_pos, final)
