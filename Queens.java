import java.lang.Math;
import java.util.*;

/* YOU NEED TO ADD YOUR CODE TO THIS CLASS, REMOVING ALL DUMMY CODE
 *
 * DO NOT CHANGE THE NAME OR SIGNATURE OF ANY OF THE EXISTING METHODS
 * (Signature includes parameter types, return types and being static)
 *
 * You can add private methods to this class if it makes your code cleaner,
 * but this class MUST work with the UNMODIFIED Tester.java class.
 *
 * This is the ONLY class that you can submit for your assignment.
 *
 * MH September 2022
 */
public class Queens
{
    private static int boardSize = 12;

    // creates a valid genotype with random values
    public static Integer[] createGeno()
    {
        Integer[] genotype = new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};

        // YOUR CODE GOES HERE

        //Using Fisher-Yates Shuffle Algorithm
        Random random = new Random();
        for (int i = genotype.length - 1; i > 0; i--)
        {
            int randomIndex = random.nextInt(i + 1);

            int a = genotype[randomIndex];
            genotype[randomIndex] = genotype[i];
            genotype[i] = a;
        }

        // END OF YOUR CODE

        return genotype;
    }

    // move a gene in the genotype
    // the move happens with probability p, so if p = 0.8
    // then 8 out of 10 times this method is called, a move happens
    public static Integer[] insertionMutate(Integer[] genotype, double p)
    {
        // YOUR CODE GOES HERE

        // Returns a random integer from 0 to 9:
        int random = (int) Math.floor(Math.random() * 10);

        String[] hat = new String[]{"cat", "cat", "rabbit", "cat", "cat", "cat", "cat", "cat", "rabbit", "cat"};

        //mutation takes place when random number is from 0 to 8 inclusive (80% probability)
        if (hat[random].equals("cat"))
        {
            int lowIndex;
            int highIndex;

            //creates temporary array duplicate of genotype with one extra element at the end
            int tempArray[] = new int[genotype.length + 1];
            for (int i = 0; i < genotype.length; i++)
            {
                tempArray[i] = genotype[i];
            }

            //random index from 0 to 11
            int secondAlleleIndex = (int) (Math.random() * 12);

            //random index from 0 to 11
            int firstAlleleIndex = (int) (Math.random() * 12);

            //ensure both indeces are not equal
            while (firstAlleleIndex == secondAlleleIndex)
            {
                firstAlleleIndex = (int) (Math.random() * 12);
            }

            //determine the lower and higher indeces
            if (firstAlleleIndex < secondAlleleIndex)
            {
                lowIndex = firstAlleleIndex;
                highIndex = secondAlleleIndex;
            }
            else
            {
                lowIndex = secondAlleleIndex;
                highIndex = firstAlleleIndex;
            }

            //loop n-times where n = (tempArray.length - (lowIndex+1)) to shift all elements after lowIndex to right.
            //element right after lowIndex is = to element at lowIndex
            for (int i = tempArray.length - 1; i > lowIndex; i--)
            {
                tempArray[i] = tempArray[i - 1];
            }

            //set element after lowIndex to element at highIndex (execute the mutation)
            tempArray[lowIndex + 1] = genotype[highIndex];

            //loop n-times where n = (tempArray.length - (highIndex+1)) to shift back all elements.
            for (int i = highIndex + 1; i < tempArray.length; i++)
            {
                if (i + 1 == tempArray.length)
                {
                    tempArray[i] = 0;
                }
                else
                {
                    tempArray[i] = tempArray[i + 1];
                }
            }

            //mutate genotype
            for (int i = 0; i < genotype.length; i++)
            {
                genotype[i] = tempArray[i];
            }
        }

        // END OF YOUR CODE
        return genotype;
    }

    // creates 2 child genotypes using the 'cut-and-crossfill' method
    public static Integer[][] crossover(Integer[] parent0, Integer[] parent1)
    {
        Integer[][] children = new Integer[2][boardSize];

        // YOUR CODE GOES HERE

        // fill children genotypes with the same values from their parents before the crossover point (6)
        System.arraycopy(parent0, 0, children[0], 0, 6);
        System.arraycopy(parent1, 0, children[1], 0, 6);

        // For First Child
        int valueAlreadyInChild0 = 0;
        int wrappingIndex = 6;
        for (int i = 0; i < 12; i++)
        {
            wrappingIndex = wrappingIndex % parent0.length;

            // loop over child0 from 0 to 11
            for (int j = 1; j <= 11; j++)
            {
                // if current child value is equal to parent value [i-1] break
                if (Objects.equals(parent1[wrappingIndex], children[0][j - 1]))
                {

                    valueAlreadyInChild0 = 1;
                    wrappingIndex = (wrappingIndex + 1) % parent1.length;
                    j = 0;
                }
                else
                {
                    valueAlreadyInChild0 = 0;
                }
            }

            if (valueAlreadyInChild0 == 0)
            {
                // loop till the first null value in the child array is found
                for (int x = 0; x < 12; x++)
                {
                    if (children[0][x] == null)
                    {
                        children[0][x] = parent1[wrappingIndex];
                        wrappingIndex = x + 1;
                        break;
                    }
                }
            }
        }

        // For Second Child
        int valueAlreadyInChild1 = 0;
        wrappingIndex = 6;
        for (int i = 0; i < 12; i++)
        {
            wrappingIndex = wrappingIndex % parent0.length;

            // loop over child1 from 0 to 11
            for (int j = 1; j <= 11; j++)
            {
                // if current child value is equal to parent value [i-1] break
                if (Objects.equals(parent0[wrappingIndex], children[1][j - 1]))
                {

                    valueAlreadyInChild1 = 1;
                    wrappingIndex = (wrappingIndex + 1) % parent0.length;
                    j = 0;
                }
                else
                {
                    valueAlreadyInChild1 = 0;
                }
            }

            if (valueAlreadyInChild1 == 0)
            {
                // loop till the first null value in the child array is found
                for (int x = 0; x < 12; x++)
                {
                    if (children[1][x] == null)
                    {
                        children[1][x] = parent0[wrappingIndex];
                        wrappingIndex = x + 1;
                        break;
                    }
                }
            }
        }

        // END OF YOUR CODE

        return children;
    }

    // calculates the fitness of an individual
    public static int fitness(Integer[] genotype)
    {
        /* The initial fitness is the maximum pairs of queens
         * that can be in check (all possible pairs in check).
         * So we are using it as the maximum fitness value.
         * We deduct 1 from this value for every pair of queens
         * found to be in check.
         * So, the lower the score, the lower the fitness.
         * For a 12x12 board the maximum fitness is 66 (no checks),
         * and the minimum fitness is 0 (all queens in a line).
         */

        int fitness = (int) (0.5 * boardSize * (boardSize - 1));

        // YOUR CODE GOES HERE

        int threats = 0;

        // for horizontal & vertical threats
        Set<Integer> set = new LinkedHashSet<Integer>(Arrays.asList(genotype));
        Object[] genotypeNoDuplicates = set.toArray();
        int horizontalVerticalThreats = Math.abs((genotype.length) - (genotypeNoDuplicates.length));
        threats += horizontalVerticalThreats;

        // for diagonal threats
        for (int x = 0; x < genotype.length; x++)
        {
            for (int y = 0; y < genotype.length; y++)
            {
                if (x != y)
                {
                    int d1 = Math.abs(x - y);
                    int d2 = Math.abs(genotype[x] - genotype[y]);

                    if (d1 == d2)
                    {
                        threats++;
                    }
                }
            }
        }

        fitness = 66 - (threats / 2);
        return fitness;
    }
}
