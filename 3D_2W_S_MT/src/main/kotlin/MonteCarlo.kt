import java.awt.BorderLayout
import java.awt.Font
import java.awt.Font.BOLD
import java.util.*
import javax.swing.JFrame
import javax.swing.JLabel
import javax.swing.JScrollPane
import javax.swing.JTable
import kotlin.system.measureTimeMillis

	val threads_count = 6
	val probability = 0.005
	val Iterations = 180000 / threads_count

	fun main(args: Array<String>) {
		//showMemory()
		val time = measureTimeMillis {
			val gList = Collections.synchronizedList(ArrayList<Int>())
			val threads = ArrayList<Thread>(threads_count)
			(1..threads_count).forEach { i ->
				threads.add(Thread(Runnable {
					val g = Grid(9, Iterations, probability)
					g.run_sim2()
					// g.show(
					// );
					gList.addAll(g.list)
				}))
				threads[i - 1].start()
			}

			(1..threads_count).forEach { i -> threads[i - 1].join() }
			display(list = gList)
		}
		println("Time: " + time / 1000.0
				+ " seconds")
	}

fun display(list: List<Int>) {
    println("Calculating averages...")
    val mean = list.average()
    println("Walk Length: " + mean)
    val sd = findDeviation(list, mean)
    //println("SD: " + sd)
    val error = sd / Math.sqrt(list.size.toDouble())
    //println("Error: " + error * 100 + "%")
    println("(+/-) " + error * 1.96)
    println("Samples:" + list.size)
   // createTable(mean, sd, (error*100), error*1.96, list.size)
    //println("-------------------------------")
}

private fun createTable(mean: Double, sd: Double, error: Double, limits: Double, samples: Int) {

    val columns = arrayOf("Walk Length <n>", "SD", "Error %", "+/-", "Sample amount")
    val data = arrayOf(arrayOf(mean,sd,error,limits,samples))
    val scrollPane = JScrollPane(JTable(data, columns).apply {
        fillsViewportHeight = true

    })

    val lblHeading = JLabel("Data").apply {
        font = Font("Arial", BOLD, 18)

    }

    JFrame("MonteCarlo Results").apply {
        contentPane.layout = BorderLayout()
        contentPane.add(lblHeading, BorderLayout.PAGE_START)
        contentPane.add(scrollPane, BorderLayout.CENTER)
        defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        setSize(1024, 250)
        isVisible = true
    }

}

private fun showMemory() {
	println("Total memory (bytes): "+ Runtime.getRuntime().totalMemory())
	println("Free memory (bytes): "+ Runtime.getRuntime().freeMemory())
	println("Max memory (bytes): "+ Runtime.getRuntime().maxMemory())
}

fun findDeviation(nums: List<Int>, mean: Double): Double {
	var squareSum = 0.0
	nums.indices.forEach { i -> squareSum += Math.pow(nums[i] - mean, 2.0) }
	return Math.sqrt(squareSum / (nums.size - 1))
}

fun mean2(list: List<Int>): Double {
	var avg = 0.0
	var t = 1
	for (x in list) {
		avg += (x - avg) / t
		++t
	}
	return avg
}


