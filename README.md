# Trains Exercise

Problem: The local commuter railroad services a number of towns in Kiwiland.  Because of monetary concerns, all of the tracks are 'one-way.' That is, a route from Kaitaia to Invercargill does not imply the existence of a route from Invercargill to Kaitaia. In fact, even if both of these routes do
happen to exist, they are distinct and are not necessarily the same distance!

The purpose of this problem is to help the railroad provide its customers with information about the routes. In particular, you will compute the distance along a certain route, the number of different routes between two towns, and the shortest route between two towns.

Input: A directed graph where a node represents a town and an edge represents a route between two towns. The weighting of the edge represents the distance between the two towns. A given route will never appear more than once, and for a given route, the starting and ending
town will not be the same town.

Output: For test input 1 through 5, if no such route exists, output 'NO SUCH ROUTE'. Please use the most direct route and do not make any
extra stops! For example, the first problem means to start at city A, then travel directly to city B (a distance of 5), then directly to city C (a distance
of 4).

1. The distance of the route A-B-C.
2. The distance of the route A-D.
3. The distance of the route A-D-C.
4. The distance of the route A-E-B-C-D.
5. The distance of the route A-E-D.

Test Input:

For the test input, the towns are named using the first few letters of the alphabet from A to D. A route between two towns (A to B) with a
distance of 5 is represented as AB5.

Graph: AB5, BC4, CD8, DC8, DE6, AD5, CE2, EB3, AE7
Expected Output:
Output #1: 9
Output #2: 5
Output #3: 13
Output #4: 22
Output #5: NO SUCH ROUTE

## Instructions of use

### Technology used: 
    * Windows 10 64 bits
    * JavaSE 1.8
    * Eclipse 2019-09 R (4.13.0)

### Assumptions
    * A track is a direct connection from one town to another
    * A path is way to get from one town to another, trough one or more tracks
    * Town names are just 1 letter from a-z or A-Z
    * For a given track, the starting and ending town will not be the same
    * The weight of the track must be > 0
    * A given track will never appear more than once
    * When searching for path, only simple path will be considered
    * When testing, this will run on the last graph given

### First use:
    * To execute the program you need to run the Main as a Java Application,
      it's at the route /Trains Exercise/src/trains/exercise/Main.java.
    * The first screen will show  a menu with this options
        0. Print the menu
        1. If you need help
        2. Introduce input graph data manually
        3. Introduce input graph data with a file
        4. Calculate distance along route
        5. Number of different routes between two towns
        6. Shortest route between two towns
        7. Exit

#### Option 0. Print the menu
    Prints the menu

#### Option 1. Help
    Shows program helpful information

#### Option 2. Introduce input graph data manually 
    Option to introduce the graph information. The format is TownTownWeight, 
    example: AB5. Means that there is a direct route from A to B with weight 5,
    Each direct route is separated by , Example: AB5, BC4, CD8, DC8, DE6, AD5, CE2, EB3, AE7
    All the routes should be written in the same line, after all routes are written you can press Enter.

#### Option 3. Introduce input graph data with a file
    Option to introduce the graph information. The format is TownTownWeight,
    example: AB5. Each route is separated by , Example: AB5, BC4, CD8, DC8, DE6, AD5, CE2, EB3, AE7
    All the routes should be written in the same line of a .txt file. After
    selecting the option 3 you'll be ask for the absolute route
    of the file, example: C:\user\pepito\input.txt

#### Option 4. Calculate distance along route
    Computes distance along a route. The program will compute the distance
    between each track and give the total sum of the path. the format of the input is
    Town-Town-Town-Town, as many towns as you want, example: A-B-C-D-E

#### Option 5. Number of different routes between two towns
    Computes the number of different routes between two towns,
    the format of the input is TownTown, example: AB    

#### Option 6. Shortest route between two towns
    Computes the shortest route between two towns, the format of
    the input is TownTown, example: AB    

#### Option 7. Test distance along route
    For the test, the input file should be with the format of first line input, second line expected output:
    A-B-C
    9
    B-C
    6
    You can do more than one test with the same input file.
    You'll be asked to introduce the full path of the test file.
    The result of the test will be True in case the result == expected output, false otherwise. The test will run as many lines/2 the file has.

#### Option 8. Test shortest path between two towns
    For the test, the input file should be with the format of first line input, second line expected output:
    A-B-C
    9
    B-C
    6
    You can do more than one test with the same input file.
    You'll be asked to introduce the full path of the test file.
    The result of the test will be True in case the result == expected output, false otherwise. The test will run as many lines/2 the file has.

#### Option 9. Exit of the program
    This option finishes the execution of the program.