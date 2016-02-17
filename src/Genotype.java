/**
 *  Implementation of CisRegulation
 * @author Deepthi Ravisankar(MT2010031)
 */

import java.util.*;

public class Genotype {

    public char[] loci;
//contains the loci with strings 1,0,x

    public char[] loci_type;
//contains the loci type i.e., r or s

    
    public int loci_size;
//indicates the length of the genotype

    public int structural_size;
    //indicates the length of the structural string

    public int regulatory_size;
    //indicates the length of the regulatory string

    double p1s,p0s,p1x,p1r,p0r;
    //probabilities- these probabilities are given as input by the user
    //based on these probabilities the genotype is created

     double ps,pr;
    //probability of the position to be structural or regulatory

    public double Wd,Wp,Wt;
    //fitness function

    public double max_cointoss_trials;

    public double toss_trials_left;

    char group;

    Genotype(int size)
    {
       loci_size = size;            //initialization of the genotype parameters
       loci = new char[size];
       loci_type = new char[size];
       //loci_after_ct = new char[size];
    }
    public void generate_parent()
    {
   
       int scount=0;
       int rcount=0;

       p0s=0.1;
       //0.2
       p1s= 0.1;
       //0.3
       p1x=0.8;
       //0.5
       p1r = 0.3;
       //0.3
       p0r = 0.7;
       //0.7
       ps = 0.75;
       //0.75
       pr=0.25;
       //0.25

       int m = (int) (100 * p1x);
       max_cointoss_trials = 2^m;
       
       Random randomGenerator = new Random();

       //System.out.println("Max_cointoss:"+max_cointoss_trials);

       for(int i=0;i<loci_size;i++)
       {
            double temp =randomGenerator.nextDouble();
            if(temp<0.75)
            {
                loci_type[i] = 's';
                scount++;
                temp= randomGenerator.nextDouble();
                if(temp<p0s)
                    loci[i]='0';
                else if((temp>p0s)&&(temp<p1s))
                    loci[i]='1';
                else
                    loci[i]='x';
            }
            else
            {
                loci_type[i] = 'r';
                rcount++;
                temp= randomGenerator.nextDouble();
                if(temp<p0r)
                    loci[i]='0';
                else
                    loci[i]='1';
            }

        }
        structural_size = scount;
        regulatory_size = rcount;

      }
    public void compute_probabilities()
    {
        double c_p0s=0,c_p1s=0,c_pxs=0,c_p0r=0,c_p1r=0;
        double temp_p0s=0,temp_p1s=0,temp_pxs=0,temp_p0r=0,temp_p1r=0;
        double scount=0;
        double rcount=0;
        for(int i=0;i<loci_size;i++)
        {
            if(loci_type[i]=='r')
            {
                rcount++;
                if(loci[i]=='1')
                        c_p1r++;
                else
                        c_p0r++;

            }
            else
            {
                scount++;
                if(loci[i]=='1')
                    c_p1s++;
                else if(loci[i]=='0')
                    c_p0s++;
                else
                    c_pxs++;
            }

        }
        p0s=c_p0s/scount;
        p1s=c_p1s/scount;
        p1x=c_pxs/scount;
        p1r=c_p1r/rcount;
        p0r=c_p0r/rcount;
        structural_size = (int)scount;
        regulatory_size = (int)rcount;
        int m = (int) (100 * p1x);
        max_cointoss_trials = 2^m;
    }

      public void DisplayGenotype()
        {
           System.out.println(loci);

           System.out.println(loci_type);
        //   System.out.println("Structural Size:" + structural_size);
         //  System.out.println("Regulatory Size:" + regulatory_size);
        }

      public void DisplayFitness()
      {
        System.out.println("Wp: " + Wp + " Wd: " + Wd + " Wt: " + Wt);
      }


    }
