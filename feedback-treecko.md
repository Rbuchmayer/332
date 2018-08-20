# Project 3 Feedback #
## CSE 332 Summer 2018 ##

**Team:** Treecko <br />
**Graded By:** Caitlin Schaefer (ceschae@uw.edu), Alon Milchgrub (alonmil@cs.washington.edu) <br>

## Unit Tests ##

**Minimax**  `(4/4)`
> ✓ Passed *depth2* <br>
> ✓ Passed *depth3* <br>

**ParallelMinimax**  `(15/15)`
> ✓ Passed *depth2* <br>
> ✓ Passed *depth3* <br>
> ✓ Passed *depth4* <br>

**AlphaBeta**  `(9/9)`
> ✓ Passed *depth2* <br>
> ✓ Passed *depth3* <br>
> ✓ Passed *depth4* <br>

## Miscellaneous ##

`(-3/0)` In the divide-and-conquer cutoff, your ParallelSearcher should save one move to compute in the current thread (instead of forking it) 

`(-4/0)` You really needed to provide comments in your code to describe complex algorithms.

## Write-Up ##

**Project Enjoyment**
`(3/3)`<br>
It was great having you two in class this quarter! I'm really glad you overall enjoyed the project and found a good partnership. Thanks for all the fun, and good luck in your future coding endeavors :) 

### Experiments ###

**Chess Game**
`(6/6)`<br>
Great conclusions and graphs! These are very easy to read. 

**Sequential Cut-Offs**
`(7/7)`<br>
Really nice insights into why there's a "sweet spot" with the cutoffs!

**Comparing the Algorithms**
`(6/7)`<br>
Great work! All of your graphs are missing units though.

### Traffic ###

**Beating Traffic**
`(4/4)`<br>
Nice conclusions here. Consider too what happens as minimax executes (this is actually what Google does too): Once you get to your next intersection, the "game board" updates with new traffic information, so minimax can update in real time while Dijkstra's can't.