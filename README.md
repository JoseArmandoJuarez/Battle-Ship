# Battle-Ship
* I created the battle ship game using Java

# How to play?
* This game is played 1 against 1.

* Each player will get a turn to enter their ships location in a 5x5 matrix.

* The ships are represented by a '@' and the matrix is read row by column. Make sure to enter first number + space + second number.

```
  Enter ship 1 location:
  2 4
  
    0 1 2 3 4
  0 - - - - -
  1 - - - - -
  2 - - - - @
  3 - - - - -
  4 - - - - -
```

* The '-' are white spaces where you will guess where the enemies ship is (total 5 ships).

* Once the game begins each player will have a turn to fire to the enemies board.

* The 'X' on the board means you Hit the enemies ship :D

* The 'O' on the board means you missed :(

```
    0 1 2 3 4
  0 - O - - -
  1 - - - - -
  2 - - - - X
  3 - - - - -
  4 - - O - -
```

* Which ever player hits all 5 enemies ship first, WINS!
