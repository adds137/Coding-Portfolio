# APT-A2 - MAA
### By Adrian Vong, Amna Alonto and Mark Baptista 
Assignment 2 - APT - Azul Game

#### How to play the game
To play a game, you can either run the program by writing './azul' with or without a seed. To add a seed, you simply write:
./azul -s \<seed>

A player turn is written as:
turn \<factory> \<colour> \<row>
(as written in the assignment specification)

Throughout the game, a user can save their game by typing 'save' or can exit anytime he/she wants to by pressing Ctrl + D.

#### Loading from a file
Load file can uses both absolute path and relative path  
Absolute path is done by copying the whole path name  

C:\Users\u\CLionProjects\APT-A2\Test\test3.txt

Relative pathing is done with a ../etc at the start before continuing  
If i wanted to access test3.txt in the Test folder of the repo use:  

../Test/test3.txt

#### Saving from a file
when asked for input on name of file for saving, you dont need to add the ".txt" extension to the end