import sbt._

object Dependencies {

  val test: Seq[ModuleID] = Seq(
    "com.typesafe"         % "config"         % "1.4.2"    % Test,
    "com.vladsch.flexmark" % "flexmark-all"   % "0.62.2"   % Test,
    "org.scalatest"       %% "scalatest"      % "3.2.13"   % Test,
    "org.scalatestplus"   %% "selenium-4-2"   % "3.2.13.0" % Test,
    "org.slf4j"            % "slf4j-simple"   % "2.0.0"    % Test,
    "uk.gov.hmrc"         %% "webdriver-hmrc" % "0.+"      % Test // Do NOT use .+ notation in test repositories
  )

}
