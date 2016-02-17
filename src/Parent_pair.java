
/**
 *  Implementation of CisRegulation
 * @author Deepthi Ravisankar (MT2010031)
 */

/*Each crossover generates 2 progencies. We have 10 crossoevers so we get 20 progencies.
 * This 20 is divided into Test and Control groups, 10 each
 * After this division, we need to compute the fitness function of both test and control
 */

import java.util.*;
public class Parent_pair {
    Genotype parent1;
    Genotype parent2;

    Genotype[] siblings= new Genotype[20];
    Genotype[] control = new Genotype[10];
    Genotype[] test = new Genotype[10];
    public char[] group= new char[20];
    // 'T' if the sibling belongs to Test
    // 'C'  if the sibling belings to Control

    double fitness_control;
    //fitness function for the control group

    double fitness_test;
    //fitness function for the test group

    Parent_pair(Genotype p1,Genotype p2)
    {
        parent1=p1;
        parent2=p2;
    }

    public void display_siblings()
    {
        for(int i=0;i<20;i++)
        {
            System.out.println("Sibling "+(i+1));
            siblings[i].DisplayGenotype();
            siblings[i].DisplayFitness();
            if(group[i]=='T')
                System.out.println("This belongs to Test group");
            else
                System.out.println("This belongs to Control group");
        }
        System.out.println("Fitness function of Test group"+fitness_test);
        System.out.println("Fitness function of Control group"+fitness_control);
    }

    public void assign_group()
    {
       int cc,ct;
        //cc - count control
        //ct - count test
        cc=0;
        ct=0;
        int i;
        Random randgen = new Random();
        for(i=0;i<20;i++)
        {
            double temp = randgen.nextDouble();
           // System.out.println(temp);
            if(temp<0.5)
            {
                group[i]='T';
                ct++;
                if(ct==10)
                    break;
            }
            else
            {
                group[i]='C';
                cc++;
                if(cc==10)
                    break;
            }
        }
        //System.out.println("i: "+i);
        if(i<20)
        {
            for(int j=i+1;j<20;j++)
            {
                if(ct==10)
                {
                    group[j] = 'C';
                    cc++;
                }
                else
                {
                    group[j] = 'T';
                    ct++;
                }
            }
        }
        //System.out.println("cc: "+cc);
        //System.out.println("ct: "+ct);
        /*for( i=0;i<10;i++)
            group[i]='C';
        for( i=10;i<20;i++)
            group[i]='T';
       *
       */
    }

    public void compute_fitness()
    {
        double fit_c=0;
        double fit_t=0;
        int ccount=0;
        int tcount=0;
        for(int i=0;i<20;i++)
        {
            if(group[i]=='T')
            {
                fit_t += siblings[i].Wt;
                control[ccount]=siblings[i];
                ccount++;
            }
            else
            {
                fit_c += siblings[i].Wt;
                test[tcount]=siblings[i];
                tcount++;
            }
        }
        fitness_test=fit_t/10;
        fitness_control=fit_c/10;

    }


}
