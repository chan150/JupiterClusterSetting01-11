  def createDatafile(filename: String, logScaleAxis: String, multipleData: Array[String]*) {
    import java.io._
    multipleData.zipWithIndex.foreach {
      case (data, index) =>
        val writer = new PrintWriter(new FileOutputStream(s"${filename}.${index}.dat", false))
        data.foreach(x =>
          writer.println(x)
        )
        writer.close()
    }
  }

  def createScript(filename: String, logScaleAxis: String, multipleData: Array[String]*) {
    import java.io._
    val writer = new PrintWriter(new FileOutputStream(s"${filename}.plt", false))
    val plotLineInScript = multipleData.zipWithIndex.map {
      case (data, index) =>
        val dataFilename = s"${filename}.${index}.dat"
        s""""${dataFilename}" using 1:2 with points pointtype 5 pointsize 0.5"""
    }.mkString(", ")
    val formatInPlot = if (logScaleAxis != "none") s"""set format ${logScaleAxis} "10^{%L}""" else ""
    val logscaleInPlot = if (logScaleAxis != "none") s"""set logscale ${logScaleAxis}""" else ""
    val script =
      s"""
set terminal postscript eps enhanced  color size 3in,1.5in
set output '${filename}.eps'
set nokey
set yrange [0.75:*]
${formatInPlot}
${logscaleInPlot}
plot ${plotLineInScript}
      """.split("\\n+")
    script.foreach(x =>
      writer.println(x)
    )
    writer.close()
  }
  
  def plotDegree(filename: String, logScaleAxis: String, multipleData: Array[String]*) {
    import scala.sys.process._
    createDatafile(s"${filename}", logScaleAxis, multipleData: _*)
    createScript(s"${filename}", logScaleAxis, multipleData: _*)
    s"gnuplot ${filename}.plt" !;
  }
  
  val file = sc.textFile("hdfs://jupiter01:9000/user/himchan/Twitter")
  
  val edges = file.filter(x => !x.startsWith("#")).map{x=> val s = x.split("\\s+"); (s(0).toInt, s(1).toInt)}
  
  val d = edges.map(x=>(x._1, 1)).reduceByKey(_+_).map(x=>(x._2, 1)).reduceByKey(_+_).map(x=>x._1+"\t"+x._2).collect
  
  plotDegree("realDegreePlot/Twitter", "xy", d)
