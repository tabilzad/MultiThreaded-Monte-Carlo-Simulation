import java.util.concurrent.ThreadLocalRandom;


public class Particle {
	public  int x;
	public  int y;
	public  int z;
	public  int steps = 0;
	public 	int Grid_size;

	Particle(int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	Particle(int GZ) {
		this.Grid_size = GZ;
	}

	public void show() {
		System.out.println("{" + x + "," + y + "}: STEP:" + steps);
	}

	public void modulo(int m) {

		x = Math.floorMod(x, m);
		y = Math.floorMod(y, m);
		z = Math.floorMod(z, m);

	}

	public int index() {
		return steps;

	}

	public boolean equals(int x, int y, int z) {
		if (this.x == x && this.y == y && this.z == z) {
			return true;
		} else
			return false;

	}
	
	public boolean equals(Particle p) {
		if (this.x == p.x && this.y == p.y && this.z == p.z) {
			return true;
		} else
			return false;

	}
	public  void step() {
		
		int r = ThreadLocalRandom.current().nextInt(1, 120);

		if (r <= (20))
			x--;
		else if (r <= 40)
			x++;
		else if (r <= 60)
			y--;
		else if (r <= 80)
			y++;
		else if (r <= 100)
			z--;
		else if (r <= 120)
			z++;


	}
	
	public void randomize(int G) {

		this.x = ThreadLocalRandom.current().nextInt(0, this.Grid_size );
		this.y = ThreadLocalRandom.current().nextInt(0, this.Grid_size );
		this.z = ThreadLocalRandom.current().nextInt(0, this.Grid_size );
	}

}
