# JavaGame
Junfei Zhang
2022

This repository is a game designed using java. I used the IntelliJ IDE for development.

Configuration:
The 'src' folder contains all the classes and interfaces of this program. 
The 'res' folder contains all the resources, including images and initial location of objects. 
The 'bagel' file is provided by The University of Melbourne, as well as the images.

Game Description:
The game features two levels : Level 0 is on the ship and Level 1 is on the island. The sailor moves continuously, and can perform attacks when the player presses a certain key. The blocks stop the sailor from overlapping with the blocks’ location.

Level 0 also contains some deadly pirates! Pirates are live entities that move in certain directions and have health points associated with them. They can shoot projectiles at the sailor when the sailor comes close. If a projectile hits the sailor, he will lose health points, but the sailor can still fight back. To win the level, the sailor must reach the exit, located by the ladder (in the bottom right). If the sailor’s health reduces to 0 or less, the game ends.

In Level 1, the sailor has escaped the ship and arrived on Blackbeard’s island. To win the level and the game, the sailor must get to the treasure. However, the sailor has to deal with bombs on the island which explode (causing damage) if the sailor collides with them. In addition to the bombs, the sailor will encounter items on the island that can help him. These items (potion, elixir and sword) will have different effects on the sailor when collided with and the item will disappear as they collide (i.e. the item gets picked up). And of course, Level 1 will feature even more pirates, including Blackbeard himself! The game will end if the sailor’s health reduces to 0 or less.
