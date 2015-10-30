# CMPSC470 - Assignment 1
Doug Fultz

## How To

### Requirements

1. Sun Lab machine

### Compile & Run buildParser.java

`# javac buildParser.java && java buildParser`

### Compile & Run buildParser.java with a *.cfg file

`# javac buildParser.java && java buildParser ./FILE.cfg`

### Compile & Run buildParser.cpp

`# g++ -o buildParser buildParser.cpp && ./buildParser`

### Compile & Run buildParser.cpp with a *.cfg file

`# g++ -o buildParser buildParser.cpp && ./buildParser ./FILE.cfg`

## Assignment Description

Write a C++/Java program that reads a CFG and an LL(1) parse table and automatically generates the corresponding recursive-descent parser in C++ code.

### Input:

A text file that contains the LL(1) parse table and the CFG.

### Output:

A *recDescent.cpp* file that contains the working code for the parser. The parser should be a class that implements the recursive descent parser with a single public method called parse that accepts a string of characters to parse and returns *true* if the string parses correctly otherwise it returns *false*.

### Structure of the CFG:

* A terminal will always be a single lowercase character.
* A non-terminal will always be a single uppercase character.
* Symbols on the RHS of a production rule are always separated by a space.
* A production that produces lambda has an empty RHS.

### Structure of the parse table:

* The first row has two spaces followed by the terminal symbols (where each terminal is separated from another terminal using a space)
* The rest of the rows has the following structure:
  * The non-terminal symbol followed by a space followed by an entry for each of the terminal symbols. Each table entry is either a rule number or a zero (denoting an error).
* The rule number refers to the order of the CFG rules given to you in the file. The first production rule in the CFG has a rule number of 1.

%% is used in the file as terminator for the CFG and the table. An example of an input file is **input1.cfg**.

