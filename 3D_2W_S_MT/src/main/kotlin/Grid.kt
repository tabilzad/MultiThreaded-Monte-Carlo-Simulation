import java.lang.Math.abs
import java.util.ArrayList
import java.util.concurrent.*

class Grid(var Grid_size: Int, var Iterations: Int, var pb: Double) {
    var list = mutableListOf<Int>()
    fun run_sim() {
        (1..Iterations).forEach {
            var st = 0
            val p = Particle(this.Grid_size)
            val p2 = Particle(this.Grid_size)
            while (p.equals(p2)) {
                p.randomize(Grid_size)
                p2.randomize(Grid_size)
            }
            while (!p.equals(p2)) {

                val OLD_p = Particle(p.x, p.y, p.z)
                val OLD_p2 = Particle(p2.x, p2.y, p2.z)

               st++
                if (!prob(pb)) {
                    p.step()
                    p.modulo(Grid_size)
                }else{
                    p.stop()
                }
                if (!prob(pb)) {
                     p2.step()
                     p2.modulo(Grid_size)
                 } else {
                     p2.stop()
                 }

                if(p.stopped && p2.stopped) break
                if (p2.equals(OLD_p) && p.equals(OLD_p2)) {
                    break
                }
            }
            list.add(st)
        }
    }

    fun run_sim2() {
        (1..Iterations).forEach {
            var st = 0
            val p = Particle(this.Grid_size)
            val p2 = Particle(this.Grid_size)
            while (p.equals(p2)) {
                p.randomize(Grid_size)
                p2.randomize(Grid_size)
            }
            while (!p.equals(p2)) {

                val old_P = Particle(p.x, p.y, p.z)
                val old_P2 = Particle(p2.x, p2.y, p2.z)

                st++
                if (!prob(pb)) {
                    p.step()
                    p.modulo(Grid_size)
                }else{
                    break
                }

                if(!prob(pb)){
                    p2.step()
                    p2.modulo(Grid_size)
                }else{
                    break
                }

                if (p2.equals(old_P) && p.equals(old_P2)) {
                    break
                }
            }
            list.add(st)
        }
    }

    fun prob(probabilityTrue: Double): Boolean = ThreadLocalRandom.current().nextDouble() >= 1.0 - probabilityTrue


}
