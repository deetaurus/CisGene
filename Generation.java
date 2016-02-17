/**
 *  Implementation of CisRegulation
 * @author Deepthi Ravisankar (MT2010031)
 */

/*Each generation consists of 900 genotypes, ie. 2 parents=20 siblings
 *  Therefore, 45 parent-pairs will give 900 siblings
 * We'll have 45 parent-pairs and 900 siblings in each generation
 */
public class Generation {

        Parent_pair[] pp_gen = new Parent_pair[45];
       //all the parent pairs along with their siblings that belong to this generation

        double generation_fitness_test;
        //fitness function of all the test groups in this generation

        double generation_fitness_control;
        //fitness function of all the control groups in this generation

        Genotype[] next_generation = new Genotype[10];
        //genotypes for next generation

        double fit_max;

        double average_fit;
        public void execute_generation_next()
        {
            for(int i =0;i<45;i++)
            {
                Crossover m = new Crossover(pp_gen[i].parent1,pp_gen[i].parent2);
                for(int j=0;j<20;j++)
                    pp_gen[i].siblings[j]=m.siblings[j];
                pp_gen[i].assign_group();
                pp_gen[i].compute_fitness();
//                pp_gen[i].display_siblings();

            }
        }
        public void execute_generation_one()
        {
            for(int i =0;i<45;i++)
            {
                    //System.out.println("Parent1:");
                Genotype parent1 = new Genotype(60);
                parent1.generate_parent();
                //parent1.DisplayGenotype();
                //System.out.println("Parent2:");
                Genotype parent2 = new Genotype(60);
                parent2.generate_parent();
                //parent2.DisplayGenotype();

                //A single crossover gives rise to 2 siblings
                //After 10 crossover's we should get 20 siblings
                //System.out.println("Siblings after Crossover:");

                Crossover m = new Crossover(parent1,parent2);
                pp_gen[i] = new Parent_pair(parent1,parent2);
                for(int j=0;j<20;j++)
                    pp_gen[i].siblings[j]=m.siblings[j];
                pp_gen[i].assign_group();
                pp_gen[i].compute_fitness();
//                pp_gen[i].display_siblings();

            }
          }

        public void compute_generation_fitness()
        {
            double fit_c=0;
            double fit_t=0;
            for(int i=0;i<45;i++)
            {
                 fit_c+=pp_gen[i].fitness_control;
                 fit_t+=pp_gen[i].fitness_test;
            }
            generation_fitness_test=fit_t/45;
            generation_fitness_control=fit_c/45;
            average_fit = generation_fitness_control;
         }

        public void compute_next_generation_genotype()
        {
            int group_id=0;
            for(int i=0;i<45;i++)
            {
                 if(fit_max<pp_gen[i].fitness_control)
                 {
                    fit_max = pp_gen[i].fitness_control;
                    group_id=i;
                 }
            }
            next_generation=pp_gen[group_id].test;
            System.out.println("Max fitness: " + fit_max);
           // System.out.println("Group id: "+group_id);
            //for(int i=0;i<10;i++)
              //  next_generation[i].DisplayGenotype();
        }

        public void generate_parent_pair()
        {
            int count=0;
            for(int i=0;i<10;i++)
            {
                for(int j=i+1;j<10;j++)
                    pp_gen[count++]=new Parent_pair(next_generation[i],next_generation[j]);
            }
         //   System.out.println("Count "+count);
        }

        public void Display_generation_fitness()
        {
            System.out.println("Fitness of Control in first generation "+generation_fitness_control);
            System.out.println("Fitness of Test in first generation "+generation_fitness_test);
        }

       public static int compute_percent(double prev, double next)
        {
            double temp;
            if(prev<next)
                   temp=next-prev;
            else
                temp=prev-next;
            temp=temp/prev;
            
            if((temp*100)<0.02)
                return 1;
            else
                return 0;
        }

        public static void main(String[] args)
        {
            Generation trial = new Generation();
            System.out.println("------------Generation 1---------------------");
            trial.execute_generation_one();
            trial.compute_generation_fitness();
            trial.Display_generation_fitness();
            trial.compute_next_generation_genotype();
            trial.generate_parent_pair();
            double prev_fitness=trial.average_fit;
            double next_fitness=0;
            int repeat=0;
            int repeat_fitness=0;
            int loop_count=1;
            while(repeat_fitness<20)
            {
                System.out.println("----------------"+"Generation"+(loop_count+1)+"----------------");
                loop_count++;
                trial.execute_generation_next();
                trial.compute_generation_fitness();
                trial.Display_generation_fitness();
                trial.compute_next_generation_genotype();
                trial.generate_parent_pair();
                next_fitness=trial.average_fit;
                repeat = compute_percent(prev_fitness,next_fitness);
                if(repeat==1)
                       repeat_fitness++;
                else
                       repeat_fitness=0;
                prev_fitness=next_fitness;
                next_fitness=0;

            }
        }
}
