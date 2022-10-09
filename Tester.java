/*
 *  DO NOT SUBMIT THIS CLASS WITH YOUR ASSIGNMENT
 *
 *  This class is only provided so that you can test your code.
 *
 *  ALL your code MUST be contained in the Queens.java class.
 *
 *  MH September 2022
 */
public class Tester
{
    // we are using a 12x12 board size...
    static int boardSize = 12;

    public static void main(String[] args)
    {
        Integer[] geno = new Integer[boardSize];

//        // test 1: create 10 random genotypes
//        System.out.println("1. Testing Random Genotype Creator: ");
//        for (int count = 0; count < 10; count ++)
//        {
//            geno = Queens.createGeno();
//            printGenotype( geno);
//        }

//        // test 2: try to mutate a genotype 10 times in succession, with 80% chance
//        double rate = 0.8;
//        geno = new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};
//        System.out.println("\n2. Testing Mutation:\nOriginal:");
//        printGenotype(geno);
//        System.out.println("Mutations:");
//        for (int count = 0; count < 10; count++)
//        {
//            geno = Queens.insertionMutate(geno, rate);
//            printGenotype(geno);
//        }

//        // test 3: perform recombination to create 2 new children
//        Integer[] parent1 = new Integer[]{5, 4, 2, 11, 6, 8, 12, 9, 3, 1, 7, 10};
//        Integer[] parent2 = new Integer[]{6, 12, 2, 5, 7, 3, 9, 1, 10, 4, 11, 8};
//        System.out.println("\n3. Testing Crossover:\nParents:");
//        printGenotype(parent1);
//        printGenotype(parent2);
//
//        Integer[][] children = Queens.crossover(parent1, parent2);
//        Integer[] correct1 = new Integer[]{5, 4, 2, 11, 6, 8, 9, 1, 10, 12, 7, 3};
//        Integer[] correct2 = new Integer[]{6, 12, 2, 5, 7, 3, 9, 1, 10, 4, 11, 8};
//
//        System.out.print("\nChild 1:\nExpected: ");
//        printGenotype(correct1);
//        System.out.print("Produced: ");
//        printGenotype(children[0]);
//
//
//        System.out.print("\nChild 2:\nExpected: ");
//        printGenotype(correct2);
//        System.out.print("Produced: ");
//        printGenotype(children[1]);

        // test 4: measure the fitness of a set of individuals
        Integer[][] genos = new Integer[5][10];
        genos[0] = new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};
        genos[1] = new Integer[]{9, 5, 6, 11, 10, 8, 7, 12, 1, 3, 2, 4};
        genos[2] = new Integer[]{9, 4, 3, 1, 2, 11, 5, 10, 12, 7, 8, 6};
        genos[3] = new Integer[]{7, 12, 8, 9, 1, 10, 2, 3, 4, 5, 6, 11};
        genos[4] = new Integer[]{10, 6, 4, 2, 8, 11, 5, 12, 9, 1, 3, 7};

        // what the fitness values should be
        Integer[] fitnesses = new Integer[5];
        fitnesses[0] = 0; // worst possible, all queens aligned
        fitnesses[1] = 60;
        fitnesses[2] = 56;
        fitnesses[3] = 53;
        fitnesses[4] = 63; // very close to maximum fitness (66)

        System.out.println("\n4. Testing Fitness Measure:");
        for (int index = 0; index < 5; index++)
        {
            int fitness = Queens.fitness(genos[index]);
            System.out.println("geno " + index + ": Expected: "
                    + fitnesses[index] + ", Produced: "
                    + fitness);
        }
    }

    // a very convenient worker method
    public static void printGenotype(Integer[] genotype)
    {
        for (int index = 0; index < boardSize; index++)
        {
            System.out.print(" " + genotype[index]);
        }
        System.out.println();
    }
}
