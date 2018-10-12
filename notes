This task was kinda fun, took me a good few hours
DISCLAIMER: My solution is inefficient and is not the best way *at all* to do it so yeah

Should be able to find the "code.txt" file in src/main and that's where it will execute. All variables need to be +ve ints and follow syntax or errors come say hi.

I used a hell of a lot of stacks to hold all the data during loops. Fun.

_The Variable class_
So I thought that I could hold the names and values of variables declared using objects, and this worked rather well. There are a few "getter" methods and a few "setter"s too but in essence is just a placeholder for a name and value. I could make it generic but I'm assuming this language only works with 32-bit "Integer"s.

_The Main class_
There are a couple of methods about reading from a file and finding stuff but they're simple and kinda self explanatory.
So main():
Up until line 34, it's all just intialisation. Getting the file data, making stacks, etc. The file data is split into a static array by the ";", meaning each line is a different element in this array, "code".
The various stacks are for holding various things, and are made to cope with the loops: 
# "stack" holds the code being executed at this moment at the top of the stack, then removes it from the top when it has finished. It holds sections of code, each loops from the parts underneath it.
# "lineTrace" holds the corresponding line to continue the code from (in another block) after a loop has finished
# "loopWatch" holds the variable names and stop condition values for loops, with corresponding variables in the same positions as the loop they control (i.e a sub-loop at index 3 on "stack" is controlled by loopWatch[3])
# "vars" holds the variables (not a stack)
The array "block" holds the specific block of code being executed

--Now. FOR loop no.1:
The String "line" literally holds the line being executed, and there are a whole bunch of IFs that determine what to do. They're all pretty easy to make/understand except maybe variables and how I implemented the WHILE kw.
Variables are simpler than loops so let's start with that. It searches the list and checks each name, if it finds it, returns the index (which can then be used to change the var.), or -1 if not (then a new one with value 0 is created and added to the list).
Basically, it searches through and counts how many sub-loops there are inside, until it finds the "end" kw that belongs to it. It does this by counting up when a WHILE is found and down when an END is found. When it hits 0, it has found it's end so stops looking further. All the code that is inbetween the WHILE and respective END is put into a block, which is then added to the top of "stack". The line at which the loops stops is recorded in "lineTrace" and the variable conditions in "loopWatch".
Once all the code in the block has been checked, it decides whether or not to end the loop based on the conditions in "loopWatch" and the data in "vars". If it should end, it removes the top data (which would control the block that is to end) off the stacks.
On the next iteration the block at the top of the stack is executed. Either:
 # The whole code has ended, then the loop doesn't run because the stack would be empty
 # A loop ended on the last line, so would continue off the end of the code (handled by the if statement)
 # The last loop did not end, in which case the stack has not changed and the same block of code is executed (the looped code repeats)
 
 Then, at the end there's a var dump so we can see the results. Various info dumps are put in to help debug, but it is quite useful to see each loop.
 
 So yeah. I would've preferred to do this with a nested loop and to just reset the pointer in a FOR loop to control the loops but HECK I have to go complicated.
 
 If you have any queries (ik it's complicated) pm me or something idk maybe leave a comment if you can
