# # Pacman . IO

## Program Description
This Java game is inspired by Agar.io and aims to integrate the OOP concepts in the game. Pacman can be moved around the screen/map with the goal of increasing its size. He can achieve this by eating food and smaller enemy ghosts.

## Mechanics
At the start of the game, fifty (50) food and ten (10) enemy ghosts are spawned in random areas on the map. Although the map dimension is 2400x2400 pixels, the display or window is smaller at 800x800 pixels and only partially shows certain areas of the map. As Pacman moves, the game area pans through the other parts of the map until it reaches the map boundaries. The Pacman cannot go beyond the edges of the map.

#### Food
-   Round objects on the map
-   Size: 20px in diameter    
-   Eaten by Pacman and the ghosts. Once eaten, food respawns on the map (always keeping 50 food items on the map).

#### Pacman and Ghosts
-   Can eat any other smaller blob
-   Size
	-   Pacman initially at 40;
	-   This determines the visual size of the blob on the screen. If the blob is a round character and its diameter in px is its size. e.g. size of 40 means the blob is 40px in diameter
	-   Increases by 10 each time food is eaten
	-   Eating another blob increases size by the size of the blob eaten
-   Speed formula: 120/size
-   Different Sprite images should be used for the player and enemies

#### Power-ups
Throughout the game, power-ups appear at random locations (within the window) every 10-second intervals. These can be collected by Pacman. Power-ups disappear after 5 seconds if uncollected. The game should implement these two power-ups:
1.  Speed boost - doubles the speed of the Pacman for 5 secs
2.  Immunity - provides immunity to Pacman for 5 secs 

#### How To Play The Game
-   Player controls
	-  WASD keys to move the blob in any direction.
-   Enemy behaviors
	 -   Random direction for a random # of seconds (e.g. to the left for 3 seconds then downwards for 2 seconds, and so on..) 
   -   The game ends when the player blob is absorbed by an enemy blob

## Sample UI
**Entrance Page**
![Entrance](https://github.com/SADAsuncion/Blob.io_PacmanEdition/blob/main/Welcome_Page.png)
**Main Game**
![Game](https://github.com/SADAsuncion/Blob.io_PacmanEdition/blob/main/MainGame_Page.png)
**Game Result**
![GameOver](https://github.com/SADAsuncion/Blob.io_PacmanEdition/blob/main/GameResults_Page.png)
**Information Page**
![Information](https://github.com/SADAsuncion/Blob.io_PacmanEdition/blob/main/Information_Page.png)
**Instructions Page**
![Instructions](https://github.com/SADAsuncion/Blob.io_PacmanEdition/blob/main/Instruction_Page.png)

## Notes
-  This  is done in partial fulfillment of CMSC 22: Object Oriented Programming in UPLB. 
