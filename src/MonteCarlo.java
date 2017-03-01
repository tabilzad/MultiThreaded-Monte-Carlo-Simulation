import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MonteCarlo {

	public static final int threads_count = 8;
	public static final double probability = 0;
	public static final int Iterations = 1000000 / threads_count;

	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub
		long startTime = System.currentTimeMillis();
		List<Integer> gList = Collections
				.synchronizedList(new ArrayList<Integer>());
		// ArrayList<Integer> gList = new ArrayList<Integer>();
		ArrayList<Thread> threads = new ArrayList<Thread>(threads_count);

		for (int i = 1; i <= threads_count; i++) {
			threads.add(new Thread(new Runnable() {
				public void run() {
					Grid g = new Grid(11, Iterations, probability);

					g.run_sim();
					// g.show();
					gList.addAll(g.list);

				}
			}));

			threads.get(i - 1).start();

		}

		for (int i = 1; i <= threads_count; i++) {
			threads.get(i - 1).join();
		}

		display(gList);
		long endTime = System.currentTimeMillis();
		System.out.println("Time: " + (endTime - startTime) / 1000.0
				+ " seconds");
	}

	public static void display(List<Integer> list) {
		// System.out.println(list);
		System.out.println("Calculating averages...");
		double mean = mean2(list);
		System.out.println("Walk Length: " + mean);
		double sd = findDeviation(list, mean);
		System.out.println("SD: " + sd);
		double error = sd / Math.sqrt(list.size());
		System.out.println("Error: " + error * 100 + "%");

		System.out.println("(+/-) " + error * 1.96);
		System.out.println("Samples:" + list.size());
		System.out.println("-------------------------------");
	}

	public static double mean2(List<Integer> list) {
		double avg = 0;
		int t = 1;
		for (double x : list) {
			avg += (x - avg) / t;
			++t;
		}
		return avg;
	}

	public static double findDeviation(List<Integer> nums, double mean) {

		double squareSum = 0;

		for (int i = 0; i < nums.size(); i++) {

			squareSum += Math.pow((int) nums.get(i) - mean, 2);

		}

		return Math.sqrt((squareSum) / (nums.size() - 1));

	}

}
