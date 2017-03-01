import java.util.ArrayList;

import sun.misc.Contended;

import java.util.concurrent.*;

public class Grid {
	@Contended
	public  ArrayList<Integer> list = new ArrayList<Integer>();
	public  int Grid_size;
	public  int Iterations;
	long startTime;
	long stopTime;
	double pb;

	public Grid(int GZ, int iter, double p) {
		this.Grid_size = GZ;
		this.Iterations = iter;
		this.pb = p;
	}

	public void run_sim() {
		// TODO Auto-generated method stub
		
		//startTime = System.currentTimeMillis();
		//System.out.println("Started...");
		
		for (int i = 1; i <= Iterations; i++) {
		
			Particle p = new Particle(this.Grid_size);
			Particle p2 = new Particle(this.Grid_size);
			
			while (p.equals(p2)) {
				p.randomize(Grid_size);
				p2.randomize(Grid_size);
			}

			while (!p.equals(p2)) {
				
				Particle OLD_p = new Particle(p.x,p.y, p.z);
				Particle OLD_p2 = new Particle(p2.x,p2.y,p2.z);

				p.step();
				p.modulo(Grid_size);
				p2.step();
				p2.modulo(Grid_size);

				if(p2.equals(OLD_p) && p.equals(OLD_p2))
				{
					p.steps++; break;
				}
				p.steps++;

			}
			list.add(p.index());
			
		}
		//System.out.println(list);
		//stopTime = System.currentTimeMillis();
		//show();
	}

	public void show() {
		synchronized (System.out) {
			/*double mean = mean2(list);
			System.out.println("Walk Length: " + mean);
			double sd = findDeviation(list, mean);
			System.out.println("SD: " + sd);
			double error = sd / Math.sqrt(Iterations);
			System.out.println("Error: " + error * 100 + "%");*/

			System.out.println("Time: " + (stopTime - startTime) / 1000.0
					+ " seconds");
			/*System.out.println("(+/-) " + error * 1.96);*/
			System.out.println(list.size());

			System.out.println("-------------------------------");
		}

	}

	public boolean prob(double probabilityTrue) {
		return ThreadLocalRandom.current().nextDouble() >=1.0 - probabilityTrue;
		//return Math.random() >= 1.0 - probabilityTrue;
	}

	public double mean2(ArrayList<Integer> list) {
		double avg = 0;
		int t = 1;
		for (double x : list) {
			avg += (x - avg) / t;
			++t;
		}
		return avg;
	}

	public double findDeviation(ArrayList<Integer> nums, double mean) {

		double squareSum = 0;

		for (int i = 0; i < nums.size(); i++) {

			squareSum += Math.pow((int) nums.get(i) - mean, 2);

		}

		return Math.sqrt((squareSum) / (nums.size() - 1));

	} // End of double findDeviation(int[])

	

}
