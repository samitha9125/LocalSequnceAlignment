
package sequence.alignment;

public class LocalSequenceAlignment 
{
    private String dna1;
    private String dna2;
    private String [][] matrix;
    private String [][] links;
    private int numOfRows = 0;
    private int numOfCols = 0;
    private final int match;
    private final int mismatch;
    private final int gap;
    public LocalSequenceAlignment(String one, String two,int matchInc, int mismatchInc,int gapInc)
    {
        dna1 = one;
        dna2 = two;
        match = matchInc;
        mismatch = mismatchInc;
        gap = gapInc;
        numOfRows = one.length()+2;
        numOfCols = two.length()+2;
        matrix = new String [numOfRows][numOfCols]; // One for letter and other for value (0).
        links = new String [numOfRows][numOfCols]; // To identify links (U - upper , L - left, D - Diagonal)
    }
    
    public void matrix()
    {
        for(int i=0;i<2;i++)
        {
            for(int j=0;j<2;j++)
            {
                matrix[i][j]="-";
                links[i][j]="-";
            }
        }
        
        for(int i=0;i<dna1.length();i++)
        {
            for(int j=0;j<dna2.length();j++)
            {
                matrix[0][j+2]=String.valueOf(dna2.charAt(j));
                links[0][j+2]=String.valueOf(dna2.charAt(j));
            }
            matrix[i+2][0]=String.valueOf(dna1.charAt(i));
            links[i+2][0]=String.valueOf(dna1.charAt(i));
        }
        
        for(int i=1;i<numOfRows;i++)
        {
            for(int j=1;j<numOfCols;j++)
            {
                matrix[1][j]="0";
                links[1][j] = "0";
            }
            matrix[i][1]="0";
            links[i][1]="0";
        }

    }
    
    public void printMatrix()
    {
        
        for(int i=0;i<numOfRows;i++)
        {
            for(int j=0;j<numOfCols;j++)
            {
                System.out.print(matrix[i][j]+" , ");
            }
            System.out.println();
        }
    }
    
    public void printLinks()
    {
        
        for(int i=0;i<numOfRows;i++)
        {
            for(int j=0;j<numOfCols;j++)
            {
                System.out.print(links[i][j]+" , ");
            }
            System.out.println();
        }
    }
    
    public void fillMatrix()
    {
        for(int i=2;i<numOfRows;i++)
        {
            for(int j=2;j<numOfCols;j++)
            {
                int diagonalVal;
                int initialZero = 0;
                int upperValue;
                int leftValue;
                int largestVal;
               // System.out.println(matrix[i][0]+ " "+matrix[0][j]);
                if(matrix[i][0].equals(matrix[0][j]))
                    diagonalVal = Integer.parseInt(matrix[i-1][j-1])+ match;
                else
                    diagonalVal = Integer.parseInt(matrix[i-1][j-1])+ mismatch;
                
                upperValue = Integer.parseInt(matrix[i-1][j]) + gap;
                leftValue = Integer.parseInt(matrix[i][j-1]) + gap;
                
                if(upperValue > leftValue)
                {
                    if(upperValue > initialZero)
                    {
                        if(upperValue > diagonalVal)
                        {
                            largestVal = upperValue;
                            links[i][j] = "U";
                        }
                        else
                        {
                            largestVal = diagonalVal;
                            links[i][j] = "D";
                        }
                            
                    }
                    else
                    {
                        if(initialZero > diagonalVal)
                        {
                            largestVal = initialZero;
                            links[i][j] = "I";
                        }
                        else
                        {
                            largestVal = diagonalVal;
                            links[i][j] = "D";
                        }
                    }
                }
                else
                {
                    if(leftValue > initialZero)
                    {
                        if(leftValue > diagonalVal)
                        {
                            largestVal = leftValue;
                            links[i][j] = "L";
                        }
                        else
                        {
                            largestVal = diagonalVal;
                            links[i][j] = "D";
                        }
                            
                    }
                    else
                    {
                        if(initialZero > diagonalVal)
                        {
                            largestVal = initialZero;
                            links[i][j] = "I";
                        }
                        else
                        {
                            largestVal = diagonalVal;
                            links[i][j] = "D";
                        }
                    }
                }
                
                if(upperValue == initialZero && largestVal == upperValue)
                {
                    links[i][j] = "U";
                }
                
                if(leftValue == initialZero && largestVal == leftValue)
                {
                    links[i][j] = "L";
                }
                
                if(diagonalVal == initialZero && largestVal == diagonalVal)
                {
                    links[i][j] = "D";
                }
                
                matrix[i][j] = String.valueOf(largestVal).toString();
            }
        }
    }
    
    public int[] maxValIndexOfMatrix()
    {
        int maxVal = -99999;
        int[] answerArray = new int[3];
        for(int row = 2; row < matrix.length; row++)
        {
            for(int col = 2; col < matrix[row].length; col++)
            {
                if(Integer.parseInt(matrix[row][col]) > maxVal)
                {
                    maxVal = Integer.parseInt(matrix[row][col]);
                    answerArray[0] = row;
                    answerArray[1] = col;
                    answerArray[2] = maxVal;
                }
            }
        }
        return answerArray;
    }
    
    public void findPath()   
    {
        boolean shouldPerformFindingTheEndRecursion = true;
        int[] maxValIndex = maxValIndexOfMatrix();
        String DNASeqHorizontal = "";
        String DNASeqVertical = "";
        String linkVal;
        String nextPositionMatrixVal; // Current position of the backtracking.
        linkVal=links[maxValIndex[0]][maxValIndex[1]]; // Get link details of maximum value position.
        while(shouldPerformFindingTheEndRecursion)
        {
            if(linkVal == "D")
            {
                DNASeqVertical = matrix[0][maxValIndex[1]] + DNASeqVertical;
                DNASeqHorizontal = matrix[maxValIndex[0]][0] + DNASeqHorizontal;
                maxValIndex[0] = maxValIndex[0]-1; // Next Position Row Index
                maxValIndex[1] = maxValIndex[1]-1; // Next Position Column Index
                nextPositionMatrixVal = matrix[maxValIndex[0]][maxValIndex[1]];
                if(Integer.parseInt(nextPositionMatrixVal) == 0) // if an end found.
                {
                    shouldPerformFindingTheEndRecursion = false;
                }
                else // Otherwise link to next position.
                {
                    linkVal=links[maxValIndex[0]][maxValIndex[1]];
                    
                }
            }
            else if(linkVal == "U")
            {
                DNASeqVertical = matrix[0][maxValIndex[1]] + DNASeqVertical;
                DNASeqHorizontal = matrix[maxValIndex[0]][0] + DNASeqHorizontal;
                maxValIndex[0] = maxValIndex[0]-1; // Next Position Row Index
                maxValIndex[1] = maxValIndex[1]; // Next Position Column Index
                nextPositionMatrixVal = matrix[maxValIndex[0]][maxValIndex[1]];
                if(Integer.parseInt(nextPositionMatrixVal) == 0) // if an end found.
                {
                    shouldPerformFindingTheEndRecursion = false;
                }
                else // Otherwise link to next position.
                {
                    linkVal=links[maxValIndex[0]][maxValIndex[1]];
                }
            }
            else if(linkVal == "L")
            {
                DNASeqVertical = matrix[0][maxValIndex[1]] + DNASeqVertical;
                DNASeqHorizontal = matrix[maxValIndex[0]][0] + DNASeqHorizontal;
                maxValIndex[0] = maxValIndex[0]; // Next Position Row Index
                maxValIndex[1] = maxValIndex[1]-1; // Next Position Column Index
                nextPositionMatrixVal = matrix[maxValIndex[0]][maxValIndex[1]];
                if(Integer.parseInt(nextPositionMatrixVal) == 0) // if an end found.
                {
                    shouldPerformFindingTheEndRecursion = false;
                }
                else // Otherwise link to next position.
                {
                    linkVal=links[maxValIndex[0]][maxValIndex[1]];
                }
            }
            else
            {
                DNASeqVertical = matrix[0][maxValIndex[1]] + DNASeqVertical;
                DNASeqHorizontal = matrix[maxValIndex[0]][0] + DNASeqHorizontal;
                shouldPerformFindingTheEndRecursion = false;
            }
        }
        
        System.out.println("Locally Aligned DNA Sequence 1 : "+DNASeqVertical);
        System.out.println("Locally Aligned DNA Sequence 2 : "+DNASeqHorizontal);
    }
           
    public void runner()
    {
        matrix();
        fillMatrix();
        maxValIndexOfMatrix();
        // --printMatrix();  use to print out the matrix values
        // --printLinks();   use to print out the matrix link values (Up, Left,Diag,0 - intermediate)
        System.out.println("\n---- Local Sequence Alignment ----\n");
        findPath();
    }
            
}
