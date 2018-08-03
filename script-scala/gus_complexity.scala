import scala.util.Random
val r = new Random
def a(v:Long, acc:Double = 0 ):Double = {  if (v <= 1) acc else a(r.nextLong.abs % v, acc + 1d/v) }
def b(k:Long, threshold:Long = 1000000) = (0l until threshold).map(x=>a(k, 0)).reduce(_+_)/threshold
