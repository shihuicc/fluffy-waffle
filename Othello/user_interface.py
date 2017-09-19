
from game import *
from inputs import *

def user_interface():
    ''' The function that runs and prints the game.'''
    rows = ask_for_row_or_col_number(num_rows)
    cols = ask_for_row_or_col_number(num_cols)

    turn = ask_for_inputs(moves_first)
    left_color = ask_for_inputs(top_left)
    mode = ask_for_game_mode()
    
    game_board = new_game_board(cols, rows, left_color)
    state = game(game_board, turn, mode)
    
    while True:
        _print_display_info(game_board, cols, rows, state, turn)
        if state.color_no_legal_move(turn):
            print('\nNo valid move remains for this color, opposite turn\n')
                
            opposite = opposite_color(turn)
            turn = opposite
            state = game(game_board, turn, mode)
            #_print_display_info(game_board, cols, rows, state, turn)
            
        else:
            col_to_drop = ask_for_cell_to_drop(ask_col_num)
            row_to_drop = ask_for_cell_to_drop(ask_row_num)
            
            try:
                flip_list = CheckMove(game_board, col_to_drop,
                                    row_to_drop, turn).check_a_move()
    
            except InvalidMoveError: 
                print('Invalid move, please try again\n')
            except OutOfBoardError:
                print('Specified cell out of board\n')
            except CellFilledError:
                print('Specified cell is not empty\n')
            except:
                print('Error\n')

            else:
                game_board = state.make_move(flip_list, col_to_drop,
                                                row_to_drop)
                
                if not game_not_over(game_board, turn, mode):
                    turn = None
                    _print_display_info(game_board, cols, rows, state, turn)
                    break

                else:
                    opposite = opposite_color(turn)
                    turn = opposite
                    
                    state = game(game_board, turn, mode)
                    game_not_over(game_board, turn, mode)




def game_not_over(board, color, mode) -> bool:
    ''' Checks whether the current game state is over. If the game is over,
    prints the winning or tie message and the winning player if exists.'''
    try:
        state = game(board, color, mode)
        state._game_over()
    except GameOverError:
        if state._more_discs() == 'White' or 'Black':
            print('\nGame Over, the winner is: {}\n'.format(state._more_discs()))
            return False
        else:
            print('\nThe game is a tie\n')
            return False
    else:
        return True

    
                  
def _print_display_info(board, cols, rows, state, turn):
    ''' Prints out the board, number of discs on the board, and who's turn it is to the console.'''
    draw_board(board, cols, rows)
    _print_num_of_discs(board)
    _print_turn(turn)
                  
def _print_num_of_discs(board):
    ''' Prints out the number of discs on the board.'''
    string = "White: {} , Black: {}"
    discs = string.format(count_discs(board)[0], count_discs(board)[-1])
    print(discs)

def _print_turn(color):
    ''' Prints out who's turn it is currently. If the game is over, then don't print anything.'''
    if color == white:
        print('White Turn')
    elif color == black:
        print('Black Turn')
    else:
        None



def ask_for_game_mode():
    ''' Function that asks for whether the player with most or fewest discs
    wins, and does input error checking.'''
    while True:
        resp = input(game_mode)
        if resp == 'a':
            return 'most'
            break
        elif resp == 'b':
            return 'fewest'
            break
        else:
            print(invalid)
            
def ask_for_inputs(theInput):
    ''' Takes an input string which asks user for inputs and does error checking to the input.'''
    while True:
        resp = input(theInput)
        if resp.upper() == 'W':
            return white
            break
        elif resp.upper() == 'B':
            return black
            break
        else:
            print(invalid)
            
def ask_for_row_or_col_number(theInput)->int:
    ''' The function that asks for row or column number and does corresponding error checking to the input.'''
    while True:
        try:
            resp = int(input(theInput))
        except ValueError:
            print('Row or column number must be integer')
        else:
            if resp %2 != 0:
                print('Row or column number must be even')
            elif resp < 4 or resp > 16:
                print('Must be between 4 and 16')
            else:
                return resp
                break
        
def ask_for_cell_to_drop(theInput):
    ''' Asks the user the cell that they want to drop the disc and does error checking to the input.'''
    while True:
        try:
            resp = int(input(theInput))
        except ValueError:
            print('Row or column number must be integer')
        else:
            return resp
            break


        
################## Board ######################
def new_game_board(cols, rows, color) ->list:
    # "color" is the color to be on the top-left coner when a new game starts
    '''Creates a new game board with the size cols x rows,and is comprised
    of dots and the initial four discs in the center.'''
    board = []
        
    for row in range(rows):
        board.append([])
        for col in range(cols):
            board[-1].append('.')

    row_mid = int(rows / 2 -1)
    col_mid = int(cols / 2 -1)

    board[row_mid][col_mid] = color # Top-left
    board[row_mid+1][col_mid+1] = color
    board[row_mid][col_mid+1] = opposite_color(color) # Opposite color
    board[row_mid+1][col_mid] = opposite_color(color)
    
    return board


def draw_board(board, cols, rows):
    ''' Prints out the board in a sepcific format.'''
    col_nums = '   '
    for i in range(cols):
        col_nums += str(i) + '  '
    print(col_nums + '\n')

    for row in range(rows):
        string = ''
        string += str(row) + '  '
        for col in range(cols):
            string += board[row][col] + '  '
                
        print(string + '\n')



if __name__ == '__main__':
    user_interface()
