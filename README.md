# COMP1110 Assignment 2

## Academic Honesty and Integrity

Honesty and integrity are of utmost importance. These goals are *not* at odds with being resourceful and working collaboratively. You *should* be resourceful, you should collaborate within your team, and you should discuss the assignment and other aspects of the course with others taking the class. However, *you must never misrepresent the work of others as your own*. If you have taken ideas from elsewhere or used code sourced from elsewhere, you must say so with *utmost clarity*. At each stage of the assignment you will be asked to submit a statement of originality, either as a group or as individuals. This statement is the place for you to declare which ideas or code contained in your submission were sourced from elsewhere.

Please read the ANU's [official position](http://academichonesty.anu.edu.au/) on academic honesty. If you have any questions, please ask me.

Carefully review the [statement of originality](originality.md) which you must complete.  Edit that statement and update it as you complete each state of the assignment, ensuring that when you complete each stage, a truthful statement is committed and pushed to your repo.

## Purpose

In this assignment you will *work as a group* to master a number of major themes of this course, 
including software design and implementation, group work, using development tools such as Git and IntelliJ,
 and using JavaFX to build a user interface.  **Above all, this assignment will emphasize group work**;
  while you will receive an individual mark for your work based on your contributions to the assignment, 
  **you can only succeed if all members contribute to your group's success**.


## Assignment Deliverables

The assignment is worth 30% of your total assessment, and it will be marked out of 30. So each mark in the assignment corresponds to a mark in your final assessment for the course. Note that for some stages of the assignment you will get a _group_ mark, and for others you will be _individually_ marked. The mark breakdown and the due dates are described on the [deliverables](https://cs.anu.edu.au/courses/comp1110/assessments/deliverables/) page.

Your work will be marked via your tutor accessing git, so it is essential that you carefully follow instructions for setting up and maintaining your group repository. At each deadline you will be marked according to whatever is committed to your repository at the time of the deadline. You will be assessed on how effectively you use git as a development tool.

## Problem Description

The assignment involves implementing in Java, a board game called [IQ-Fit](https://www.smartgames.eu/uk/one-player-games/iq-fit)
made by the games developer [SmartGames](https://www.smartgames.eu/uk).

<img src="assets/iqfit.png" width="400">

#### Objective 

The game is a puzzle.   The object of the game is to place ten plastic pieces on a board so that the pieces
fill the board perfectly, with no overlaps and no gaps. The player starts by selecting a
[challenge](#challenges) which will have some number of pieces already placed.  Their task is to place all 
remaining pieces.  Broadly, the more pieces that are placed, the smaller the set of remaining options for
the player, and therefore the easier it is to complete the puzzle.   The challenge is made interesting by
the complex three-dimensional shapes of the plastic pieces.

A completed game:

<img src="assets/game7.png" width="400">

To help you visualize the game, we have provided a
[paper](assets/papergame.pdf) version, which you can cut out.

#### Challenges

A game starts by choosing a challenge which specifies in what positions certain
puzzle pieces must be placed. The puzzle pieces that are pre-placed by the challenge
at the start of the game cannot be moved by the player until the game is ended.

Here is the starting challenge for the game above:

<img src="assets/game1.png" width="400">

In the challenge above, the _lime_ (L), _sky blue_ (S), _indigo_ (I), and _green_ (G) pieces 
are placed as part of the challenge.   They can't be moved.   You can see that much of the 
board is unfilled (grey holes).  The player's task is to place the
remaining six pieces in such a way that all of the board is covered perfectly, which means no 
gaps and no overlaps.

Some challenges are much easier to solve than others.  When you think about the game,
you may wish to reflect on what makes some challenges so much
easier than others.  One of the assignment tasks is to create interesting challenges.
 Note that as a general rule for puzzles, the more
constrained the player is, the fewer options they have, and
consequently the solution to the challenge is simpler.

The game comes with five difficulty levels: _starter_, _junior_, _expert_, _master_, and _wizard_,
and offers 24 challenges at each level, for a total of 120 prescribed challenges.
These challenges are provided for you in the
[Solutions class](https://gitlab.cecs.anu.edu.au/comp1110/comp1110-ass2/blob/master/src/comp1110/ass2/Solution.java#L28),
and the different difficulty levels are tested in the
[SolutionsTest](https://gitlab.cecs.anu.edu.au/comp1110/comp1110-ass2/blob/master/tests/comp1110/ass2/SolutionsTest.java#L38) test.

#### Solutions

Each challenge has just one solution.

The following sequence shows one possible progression of a solution to the game
above (note that the order in which the pieces are played is not important; this
is just one possible sequence of moves).

<img src="assets/game1.png" width="200">
<img src="assets/game2.png" width="200">
<img src="assets/game3.png" width="200">
<img src="assets/game4.png" width="200">
<img src="assets/game5.png" width="200">
<img src="assets/game6.png" width="200">
<img src="assets/game7.png" width="200">

#### Board

The game is played on a board comprised of 50 **locations** arranged
in a 10x5 grid.  

<img src="assets/board-labelled.png" width="300">

In the real-world game, each location consists of a
circular indent into which a piece may fit.  We refer to the placement of
pieces in terms of their upper left corner when a bounding box is placed around them.
In our game, locations are
encoded as two digits, the first one identifying the column from `0`
to `9`, followed by another identifying the row from `0` to `4`.
  
In the example illustrated below we show how the location of pieces on the board
is encoded.   Notice that we identify each piece by the location of the upper-left
corner of its _bounding box_ (a rectangular box drawn around the piece).

<img src="assets/game1boxes.png" width="300">

For example, in the game above, the _lime_ piece (lower-left, lime-green in color) is
in position `02` (column `0`, row `2`), the _sky-blue_ piece (bottom center-left) is
in position `23` (column `2`, row `3`), the _indigo_ piece (purple, bottom center-right)
is in position `63` (column `6`, row `3`), and the _green_ piece (green, right) is in
position `82` (column `8`, row `2`).  Notice how the bounding box is necessary
to identify the location of the _sky-blue_ piece since the piece itself does not cover
location `23`, but that is the top left corner of its bounding box.   Likewise for the
_green_ piece, which does  not cover `82`, but that is the top left corner of its bounding box.
For the _lime-green_ and _indigo_ pieces the top-left corner of the piece is the
same as the top-left corner of its bounding box.

#### Pieces

The game comprises **10 playing shapes**, each of a distinct color (_blue_, _green_,
_indigo_, _lime-green_, _navy-blue_, _orange_, _pink_, _red_, _sky-blue_, and _yellow_).

Each piece is **3-dimensional**.  Note that in your assignment, your game will be on a screen (just like this
description), so will only view each piece from a two-dimensional perspective.

You should study the picture below carefully.  The _lime-green_, _navy-blue_, and _red_
pieces have not been placed, revealing their three dimensional shape.

<img src="assets/iqfit.png" width="400">

Each piece can be thought of in terms of a **spine** comprising either _three_ (_green_,
_indigo_, _lime-green_, _navy_) or _four_ (_blue_, _orange_, _pink_, _red_, _sky-blue_,
and _yellow_) spheres in a straight line.   

Each piece has three additional spheres that **protrude** from the spine, two in one plane, and one in the other.
Thus, when laid flat on a table and viewed from above, they will either have a **single protrusion**
 sticking out from the spine (_blue_ and _red_ in the photo), or a **double protrusion** (_lime-green_).  You
  can see these clearly in the diagram below. 

When a piece is placed on the board, it must be placed with either the single or double
protrusion pointing _downwards_ into
the holes in the board.   Thus in the photo above, each of the 7 pieces placed on the board
has no protrusions pointing upwards.   When a player places a piece, they can have either the
_double protrusion_ pointing down, into the board, or the _single protrusion_ pointing down into 
the board.  In the photo above, the _pink_, _orange_, and _sky-blue_ pieces each have their
_single protrusion_ visible, which means they were all placed with the _double protrusion_ pointing 
 downwards, into the
board.   On the other hand, the _blue_, _green_, _indigo_ and _yellow_ pieces all
have their _double protrusion_ visible, which means they have been placed with their
_single protrusion_ pointing downwards into the board.

Since each piece may be placed in one of four 90-degree _rotations_ (_north_, _east_, _south_, _west_),
and in two _flips_ (_single protrusion_ visible or _double protrusion_ visible), each piece has eight possible ways it can be placed on the board.

The diagram below illustrates all ten pieces and their eight possible orientations.  The first four
columns show each piece its _single_ protrusion visible with four 90-degree rotations, while the last four columns 
show each piece with its _double_ protrusion visible with four 90-degree rotations.  When we encode pieces in 
this assignment, we use lower case letters (e.g. `b`) to encode a piece in its 
_single protrusion_ orientation (left half of the diagram), and we use upper case letters
(e.g. `B` to encode a piece its _double protrusion_ orientation (right half of the diagram).

<img src="assets/all_pieces.png" width="600">


In the game below, you can see that _lime_ was placed in the `L` flip (double protrusion) and the `W` 
orientation (west), _sky-blue_ was placed in the `s` flip (single protrusion) and `S` orientation 
(south), etc.

<img src="assets/game7.png" width="200">


#### Legal Piece Placements

For a piece placement to be valid, the following must be true:

* All of the visible spheres in the piece must be placed above board locations
 (**no part of a piece may be off the board**).
* All of the visible spheres in the piece must be placed on vacant board
  locations (**pieces may not overlap**).

#### Encoding Game State and Challenges

Game states and challenges are encoded as strings.  Your game will
need to be able to initialize itself using these strings and some of
your tasks relate directly to these strings.

##### Challenge Strings

A challenge string consists of a _placement string_ comprising one or more 
piece placements.  

For the sample challenge below, the challenge string is `"g82EI63SL02Ws23S"`,
which consists of piece placements for _green_ (`g81E`), _indigo_ (`I63S`),
_lime-green_ (`L02W`), and _sky-blue_ (`s23S`).  These encodings are explained
further below.

<img src="assets/game1.png" width="200">

##### Placement Strings

A placement string consists of between one to ten (inclusive) **piece
placements** (pieces _blue_ to _yellow_, listed in order).  The placement string may not
include any piece twice.  A completed game must include ten piece
placements.  Pieces are always listed in order (by the first letter of the
color name; the ordering of the rows in the figure above (_blue_ ... _yellow_)).
Each piece placement is described using four characters.
For example, the completed game below is encoded
by the string `"b52Ng82EI63SL02Wn12So40NP60Sr00Ns23SY11N"`.  Note that
the placement string is **ordered** (the _blue_ piece (`b` or `B`) first, and
_yellow_ piece (`y` or `Y`) last, following the ordering in the diagram above).
Correct ordering is a requirement for valid placement strings.  The encoding
of the string is described further below.

<img src="assets/game7.png" width="200">

##### Piece Placement Strings

A piece placement string consists of four characters describing the
location and orientation of one particular piece on the board:

* The first character identifies **which of the ten shapes** is being
  placed, and which flip (lower case: single protrusion, upper case: double
  protrusion).  The table
  above shows the letters used.
* The second character identifies **which column** the left of the
  piece is in (columns are labelled `0` to `9`).
* The third character identifies **which row** the top of the piece is
  in (rows are labelled `0` to `4`).
* The fourth character identifies **which orientation** the piece is in (north `N`,
  east `E`, south `S`, and west `W`).

The image above shows the first and fourth characters for each of the
pieces in each of their orientations (80 in total). 

In the placement string above, `r00N` describes the placement of the _red_ piece,
which is placed with one protrusion (lower case `r`) in orientation `N` at location (`0`, `0`) (the top left corner
of the board).  `L02W` describes the placement of the _lime-green_ piece, which is placed
with one protrusion (upper case `L`) in orientation `W` at location (`0`, `2`).  `b52N` describes the
placement of the _blue_ piece, which is placed with one protrusion (lower case `b`) in orientation `N`
at location (`5`, `2`).

## Legal and Ethical Issues

First, as with any work you do, you must abide by the principles of
[honesty and integrity](http://academichonesty.anu.edu.au). You are
expected to demonstrate honesty and integrity in everything you do.

In addition to those ground rules, you are to follow the rules one
would normally be subject to in a commercial setting. In particular,
you may make use of the works of others under two fundamental
conditions: a) your use of their work must be clearly acknowledged,
and b) your use of their work must be legal (for example, consistent
with any copyright and licensing that applies to the given
material). *Please understand that violation of these rules is a very
serious offence.* However, as long as you abide by these rules, you
are explicitly invited to conduct research and make use of a variety
of sources. You are also given an explicit means with which to declare
your use of other sources (via originality statements you must
complete). It is important to realize that you will be assessed on the
basis of your original contributions to the project. While you won't
be penalized for correctly attributed use of others' ideas, the work
of others will not be considered as part of your
contribution. Therefore, these rules allow you to copy another
student's work entirely if: a) they gave you permission to do so, and
b) you acknowledged that you had done so. Notice, however, that if you
were to do this you would have no original contribution and so would
receive no marks for the assignment (but you would not have broken any
rules either).

## Evaluation Criteria

It is essential that you refer to the
[deliverables page](https://cs.anu.edu.au/courses/comp1110/assessments/deliverables/)
to check that you understand each of the deadlines and what is
required.  Your assignment will be marked via tests run through git's
continuous integration (CI) framework, so all submittable materials
will need to be in git and in the *correct* locations, as prescribed
by the
[deliverables page](https://cs.anu.edu.au/courses/comp1110/assessments/deliverables/).


**The mark breakdown is described on the
[deliverables](https://cs.anu.edu.au/courses/comp1110/assessments/deliverables/)
page.**

### Part One

In the first part of the assignment you will:
* Implement parts of the text interface to the game (Tasks #2, and #3).
* Implement a simple viewer that allows you to visualize game states (Task #4).

The criteria for the [completion of part one](https://gitlab.cecs.anu.edu.au/comp1110/comp1110/wikis/deliverables#d2c) is as follows:

<a name="p"></a>
**Pass**
* Tasks #2 and #3

<a name="cr"></a>
**Credit**
* Task #4 *(in addition to all tasks required for Pass)*

<a name="d"></a>
**Distinction**
* Task #5 *(in addition to all tasks required for Credit)*

### Part Two

Create a fully working game, using JavaFX to implement a playable
graphical version of the game in a 933x700 window.

Notice that aside from the window size, the details of exactly how the
game looks etc, are **intentionally** left up to you.  The diagrams
above are for illustration purposes only, although you are welcome to
use all of the resources provided in this repo, including the bitmap
images for each of the eight shapes.

The only **firm** requirements are that:

* you use Java and JavaFX,
* the game respects the specification of the game given here,
* the game be easy to play,
* it runs in a 933x700 window, and
* that it is executable on a standard lab machine from a jar file called `game.jar`,

Your game must successfully run from `game.jar` from within another
user's (i.e.  your tutor's) account on a standard lab machine (in
other words, your game must not depend on features not self-contained
within that jar file and the Java 13 runtime).

<a name="2p"></a>
**Pass**
* Correctly implements all of the <b>Part One</b> criteria.
* Appropriate use of git (as demonstrated by the history of your repo).
* Completion of Task #6
* Executable on a standard lab computer from a runnable jar file,
  game.jar, which resides in the root level of your group repo.

<a name="2c"></a>
**Credit**
* _All of the Pass-level criteria, plus the following..._
* Task #7

<a name="2d"></a>
**Distinction**
* _All of the Credit-level criteria, plus the following..._
* Tasks #8 and #9

<a name="2hd"></a>
**High Distinction**
* _All of the Distinction-level criteria, plus the following..._
* Tasks #10 and #11
