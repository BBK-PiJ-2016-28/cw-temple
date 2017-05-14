# cw-temple
Coursework 4 - Simon Augustus-Payne
PROJECT DESCRIPTION
Read and understand all the code contained and then use key classes and methods in order to achieve the desired outcome.
Two methods inside Explorer.java have been coded by myself inside the Student package. These include:

Method 1: explore
This method uses a DFS algorithm to traverse the entire graph (maze), searching for the orb. I have made use of Java 8 streams 
and lambda expressions in order to achieve this as elegantly as possible.

Method 2: escape
The objective here it to escape the cavern after having found the orb in the first method detailed above. I have used a DFS
solution in order to traverse the cave with a node that mimicks the nodes contained within the graph. This then finds the shortest
path of taversal, holding that path in an ArrayList<Node> IDs (defying convention, I chose to capitalise the first letter
of the ArrayList structure for readability). This list is then used by the avatar to escape while picking up gold. 

CREATED FILES/CLASSES/METHODS OUTSIDE PROVIDED CODE
None.
However, I have left in printouts and tests to show my logic as I coded both methods without a GUI.

LIMITATIONS
1. I was unable to use the GUI and have relied entirely on the TXTmain.java class to code this coursework. I believe this 
has added a layer of difficulty as I was unable to picture the maze and thus spent a great deal of time creating outputs,
reading these and, when necessary, drawing the graph and its nodes. I tired to fix this problem with Keith, but I was unable to solve the issue and relied solely on printing out to the console. However, to that end it works.

2. I have declared some unused data structures in the escape method. 
