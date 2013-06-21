import sbt._
import play.Project._

object ApplicationBuild extends Build {

    val appName         = "baembelville-scala"
    val appVersion      = "1.0-SNAPSHOT"

    val appDependencies = Seq(
      jdbc, anorm
    )

    val main = play.Project(appName, appVersion, appDependencies).settings(
      // Add your own project settings here      
    )

}
