# Toy Robot Simulator #

Author: **Camilo Pestana** <br>
Year: **2017** <br>
Language: **Clojure**

## Try this code here: ##
[Try this code online here](https://repl.it/NWr4/0)

## Description: ##

- The application is a simulation of a toy robot moving on a
square tabletop, of dimensions 5 units x 5 units.
- There are no other obstructions on the table surface.
- The robot is free to roam around the surface of the table, but
must be prevented from falling to destruction. Any movement
that would result in the robot falling from the table must be
prevented, however further valid movement commands must still
be allowed.
- Create an application that can read in commands of the
following form -
	* PLACE X,Y,F
	* MOVE
	* LEFT
	* RIGHT
	* REPORT
- PLACE will put the toy robot on the table in position X,Y and
facing NORTH, SOUTH, EAST or WEST.
- The origin (0,0) can be considered to be the SOUTH WEST most
corner.
- The first valid command to the robot is a PLACE command, after
that, any sequence of commands may be issued, in any order,
including another PLACE command. The application should
discard all commands in the sequence until a valid PLACE
command has been executed.
- MOVE will move the toy robot one unit forward in the direction it
is currently facing.
- LEFT and RIGHT will rotate the robot 90 degrees in the specified
direction without changing the position of the robot.
- REPORT will announce the X,Y and F of the robot.
- A robot that is not on the table can choose the ignore the MOVE, LEFT, RIGHT and REPORT commands.


## Constrains: ##

- The toy robot must not fall of the table during movement. This
also includes the initial placement of the toy robot.
- Any move that would cause the robot to fall must be ignored.

## TEST DATA ##

1. FIRST TEST
	* PLACE 0,0,NORTH
	* MOVE
	* REPORT
	* Output: 0,1,NORTH

2. SECOND TEST
	* PLACE 0,0,NORTH
	* LEFT
	* REPORT
	* Output: 0,0,WEST

3. THIRD TEST
 	* PLACE 1,2,EAST
	* MOVE
	* MOVE
	* LEFT
	* MOVE
	* REPORT
	* Output: 3,3,NORTH

4. FOURTH TEST
 	* PLACE 5,2,EAST
	* MOVE
	* MOVE
	* LEFT
	* REPORT
	* PLACE 2,2,NORT
	* MOVE
	* REPORT
	* PLACE 2,2,WEST
	* MOVE
	* LEFT
	* REPORT
	* Output: 1,2,NORTH
