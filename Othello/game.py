
from inputs import *

black = 'B'
white = 'W'
tie = 'Tie'


class GameOverError(Exception):
    ''' Raises whenever a move attempted after the game is already over.'''
    pass

class CellFilledError(Exception):
    ''' Raises when a move is attempted on a cell that is already filled.'''
    pass

class OutOfBoardError(Exception):
    ''' Raises when the cell of an attempted move is off the board. '''
    pass

class InvalidMoveError(Exception):
    ''' Raises when a move is invalid.'''
    pass



class game:
    ''' A class that contains the game logic methods.'''
    def __init__(self, board, color, mode):
        ''' Gets the game board, whose turn it is, and the game mode.'''
        self._board = board
        self._color = color
        self._mode = mode

        self._cols = self.get_cols()
        self._rows = self.get_rows()
            
    def get_cols(self) -> int:
        ''' Gets and returns the number of columns in the current game
         state.'''
        self._num_of_cols = len(self._board[0])
        return self._num_of_cols

    def get_rows(self) -> int:
        ''' Gets and returns the number of rows in the current game
         state.'''
        self._num_of_rows = len(self._board)
        return self._num_of_rows

    def _game_over(self):
        ''' Checks whether the game is over, if game is over, raise
        GameOverError.'''
        self._board_full()
        
        if self.color_no_legal_move(black) and self.color_no_legal_move(white):
            raise GameOverError()

    def _board_full(self):
        ''' Checks whether the board is full with discs, if so, raise GameOverError.''' 
        i = 0
        for row in range(self._rows):
            for col in range(self._cols):
                if self._board[row][col] != '.':
                    i += 1

        if i == self._cols * self._rows:
            raise GameOverError()

    def color_no_legal_move(self, colorStr):
        ''' Checks whether there are still legal moves can be made by either player, if no, return False.'''
        empty_cells = []

        for row in range(self._rows):
            for col in range(self._cols):
                if self._board[row][col] == '.':
                    empty_cells.append([row, col]) # append the x and y
                                                    # coordinates as a list
        
        valid_moves = 0
        for x, y in empty_cells:
            for a_dir in _possible_directions:
                if check_a_direction(self._board, y, x, colorStr, a_dir)==False:
                    valid_moves += 0
                else:
                    valid_moves += 1
       
        if valid_moves == 0:
            return True
        else:
            return False
    
    def make_move(self, discs_to_flip:list, xorig, yorig):
        ''' Make a move on the original board and returns a new board with
        the move marked, and also flips the discs that need to be flipped.'''
        new_board = self._board
        new_board[yorig][xorig] = self._color

        for y, x in discs_to_flip:
            new_board[y][x] = self._color

        return new_board

    def _more_discs(self):
        ''' Determines which player has more discs on the board and returns
        that player. If there's a tie, return tie.'''
        w = count_discs(self._board)[0]
        b = count_discs(self._board)[-1]

        if w > b:
            return 'White'
        elif w < b:
            return 'Black'
        else:
            return tie
        



class CheckMove:
    def __init__(self, board, xorig, yorig, color):
        ## x : col number
        ## y : row number
        self._board = board
        self._color = color
        self._x = xorig
        self._y = yorig

    def check_a_move(self):
        ''' Checks if an attempted move is valid(on the board, the cell
        was not filled already, there are discs to flip). If valid,
        returns a list of discs that need to be flipped, otherwise,
        return False.'''
        discs_to_flip_lst = []
        
        self._cell_not_empty()
        
        for a_dir in _possible_directions:
            result = check_a_direction(self._board, self._x, self._y,
                                        self._color,a_dir)
            if result != False:
                discs_to_flip_lst.extend(result)

        if len(discs_to_flip_lst) == 0:
            raise InvalidMoveError()
        else:
            return discs_to_flip_lst

    def _cell_not_empty(self):
        ''' Checks to see if a cell is filled with disc already, if so, raises CellFilledError. '''
        if not on_board(self._x, self._y, self._board):
            raise OutOfBoardError()
        else:
            if self._board[self._y][self._x] != '.':
                raise CellFilledError()
        



def on_board(x, y, board) -> bool:
    ''' Checks to see if an attempted cell is on the board, if not,
    return False.'''
    cols = len(board[0])-1
    rows = len(board)-1
    
    if 0 <= y <= rows and 0 <= x <= cols:
        return True
    else:
        return False
        
    
def check_a_direction(board, xorig, yorig, color, direction:list):
    ''' Takes a list, [x, y] direction, which represents one of the
    directions needs to be checked to see if a move is valid. If valid,
    returns a list of lists(coordinates of discs need to be fliped in
    the current direction). If invalid, return False.'''
    other_color = opposite_color(color)
    discs_to_flip = []
    position_of_color = []
        
    x_dir = direction[0]
    y_dir = direction[-1]
    x = xorig + x_dir
    y = yorig + y_dir
    
    while on_board(x, y, board):
        if board[y][x] == other_color:
            discs_to_flip.append([y, x])
            x += x_dir
            y += y_dir
            
        elif board[y][x] == color:
            position_of_color.append(y)
            position_of_color.append(x)
            break
        else: # if no disc 
            break
        
    if len(position_of_color) == 0 or len(discs_to_flip) == 0:
        return False
    else:
        return discs_to_flip

def count_discs(board) -> tuple:
    ''' Counts the number of both black and white discs on the board.
    Returns a tuple of 2 integers represents the number of discs.'''
    num_white_discs = 0
    num_black_discs = 0
        
    for a_list in board:
        num_white_discs += a_list.count(white)
        num_black_discs += a_list.count(black)
    return num_white_discs, num_black_discs

    
def opposite_color(color) -> str:
    ''' Returns the opposite color of "color".'''
    if color == white:
        return black
    else:
        return white


# 8 possible directions need to be checked
_possible_directions = [[0, 1], [1, 1], [1, 0], [1, -1],
                        [0, -1], [-1, -1], [-1, 0], [-1, 1]]
