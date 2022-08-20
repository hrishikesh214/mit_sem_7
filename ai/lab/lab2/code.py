"""
PB76 Hrishikesh Vaze
AI Lab 2 - MinMax(TictacToe)
"""

import os
from prettytable import PrettyTable
from icecream import ic

HUMAN = 'O'
AI = 'X'
AVAILABLE = '-'
AI_WIN_MSG = 'Ok looser! I won'
HUMAN_WIN_MSG = 'huh! You won'

# initial board
board = [
    [AVAILABLE, AVAILABLE, AVAILABLE],
    [AVAILABLE, AVAILABLE, AVAILABLE],
    [AVAILABLE, AVAILABLE, AVAILABLE]
]

def getBoardToPrint(board):
    xb = [
        [' ', ' ',1, 2, 3],
        [' ',' ',' ',' ', ' '],
        [1, ' ', board[0][0], board[0][1], board[0][2]],
        [2, ' ', board[1][0], board[1][1], board[1][2]],
        [3, ' ', board[2][0], board[2][1], board[2][2]]
    ]

    return xb

def clear_screen():
    """
    This function clears the screen
    """
    os.system('cls' if os.name == 'nt' else 'clear')

def getPrettyTable(board):
    """
    This function returns a pretty table of the board
    """
    table = PrettyTable(['a', 'b', 'c', 'd', 'e'], header=False, border=True)
    for r in getBoardToPrint(board):
        table.add_row(r)
    return table


scores = {
    'Tie': 0,
    'X': 1,
    'O': -1

}


class TicTacToe:
    def __init__(self):
        """
        Initialize the game
        we will need a board and need to keep track of the turn as well as the winner
        """
        self.board = board
        self.winner = None
        self.game_over = False
        self.ai_message = ''
        self.current_player = HUMAN # initially human will play first
    
    def draw_board(self):
        """
        This function draws the board with the help of PrettyTable Lib
        """
        clear_screen()
        print("\n")
        xb = getPrettyTable(self.board)

        print("TicTacToe Game | " ,end="")
        if not self.game_over:
            print(f"Current Player: {'Human, O' if self.current_player == HUMAN else 'AI, X'}")
            print(f"AI {self.ai_message if self.ai_message != '' else 'waiting...' if self.current_player == HUMAN else 'thinking...'}")
        else:
            print("Game Over | ", end='')
            if self.winner == HUMAN:
                print(HUMAN_WIN_MSG)
            elif self.winner == AI:
                print(AI_WIN_MSG)
            else:
                print("Draw")
            if self.ai_message:
                print(self.ai_message)

        print('\n')
        print(xb)
        print("\n\n")
    
    def make_turn(self):
        """
        This function takes the turn of the current player
        """
        if self.current_player == HUMAN:
            hi = input('Enter your turn (eg: 1,1  [-1 to exit]): ')
            hi = hi.split(',')
            hi = [int(i) for i in hi]
            # print(hi)
            if(hi[0] == -1):
                self.game_over = True
                return
            if((not(1 <= hi[0] <= 3) or not(1 <= hi[1] <= 3)) or self.board[hi[0]-1][hi[1]-1] != AVAILABLE):
                print("\nInvalid input")
                self.make_turn()
                return
            self.board[hi[0]-1][hi[1]-1] = HUMAN
        else:
            self.ai_turn()
            pass

    def check_winner(self):
        """
        This function checks if there is a winner
        """
        if self.game_over:
            return

        # check rows
        for row in self.board:
            if row[0] == row[1] == row[2] != AVAILABLE:
                return row[0],True
        # check columns
        for i in range(3):
            if self.board[0][i] == self.board[1][i] == self.board[2][i] != AVAILABLE:
                return self.board[0][i], True
        # check diagonals
        if self.board[0][0] == self.board[1][1] == self.board[2][2] != AVAILABLE:
            return self.board[0][0], True
        if self.board[0][2] == self.board[1][1] == self.board[2][0] != AVAILABLE:
            return self.board[0][2], True
        # check if game is over
        if AVAILABLE not in self.board[0] and AVAILABLE not in self.board[1] and AVAILABLE not in self.board[2]:
            return None, True
        return None, False
    
    def switch_player(self):
        """
        This function switches the player
        """
        if self.game_over: 
            return
        if self.current_player == HUMAN:
            self.current_player = AI
        else:
            self.current_player = HUMAN

    def ai_turn(self):
        """
        makes ai turn
        """
        if self.game_over:
            return
        best_score = -1000
        best_move = [-1,-1]
        for i in range(3):
            for j in range(3):
                if self.board[i][j] == AVAILABLE:
                    self.board[i][j] = AI
                    score = self.MinMax( 0, False) # as this is max player we will chack for min player
                    # ic(i,j,score)
                    self.board[i][j] = AVAILABLE
                    if score > best_score:
                        best_score = score
                        best_move = [i, j]
        if self.board[best_move[0]][best_move[1]] == AVAILABLE:
            self.board[best_move[0]][best_move[1]] = AI
        else:
            self.game_over = True
            self.winner = HUMAN
            self.ai_message = "couldn't make turn"

    def MinMax(self, depth, is_max):
        """
        This function returns the score for current board according to is_max
        """
        winner, is_completed = self.check_winner()
        if is_completed:
            if winner is not None: 
                return scores[winner]
            else:
                return scores['Tie']
        if is_max:
            best_score = -1000
            for i in range(3):
                for j in range(3):
                    if self.board[i][j] == AVAILABLE:
                        self.board[i][j] = AI
                        score = self.MinMax( depth + 1, False)
                        self.board[i][j] = AVAILABLE
                        best_score = max(score, best_score)
            return best_score
        else:
            best_score = 1000
            for i in range(3):
                for j in range(3):
                    if self.board[i][j] == AVAILABLE:
                        self.board[i][j] = HUMAN
                        score = self.MinMax(depth + 1, True)
                        self.board[i][j] = AVAILABLE
                        best_score = min(score, best_score)
            return best_score

    def run(self):
        """
        This function runs the game
        """
        while not self.game_over:
            self.draw_board()
            self.make_turn()
            if self.game_over:
                break
            winner, is_completed = self.check_winner()
            if is_completed:
                self.game_over = True
                self.winner = winner
            self.switch_player()
        
        self.draw_board()


def main():
    game = TicTacToe()
    game.run()

main()
