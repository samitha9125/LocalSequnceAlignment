# LocalSequnceAlignment
This is a Java Program which will provide the local sequence alignment of two DNAs

# Functions
LocalSequnceAlignment.java file contains some functions which can be used to check how this alignment works and perform tasks.

`Matrix()` function create suitable matrix according to input DNA strings.

fillMatrix() function create linkedMatrix which use to identify type of links each cell has (E.g: L - if Left Value is the largest). Likewise U,D,I tells upper value is the largest, Diagonal value is the largest and initial value (0) is the largest respectively.

findpath() function use to find the optimal path to identify local alignment.

runner() function can be called from the Main function after initialize the object correctly.
