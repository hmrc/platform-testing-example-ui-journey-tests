/*
 * Copyright 2022 HM Revenue & Customs
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package uk.gov.hmrc.ui.specs

import org.openqa.selenium.remote.RemoteWebDriver
import org.scalatest.featurespec.AnyFeatureSpec
import org.scalatest.matchers.should.Matchers
import org.scalatest.time.{Seconds, Span}
import org.scalatest.{BeforeAndAfterEach, GivenWhenThen, Outcome}
import org.scalatestplus.selenium.WebBrowser
import uk.gov.hmrc.webdriver.DriverFactory

trait BaseSpec extends AnyFeatureSpec with BeforeAndAfterEach with GivenWhenThen with Matchers with WebBrowser {

  implicit var driver: RemoteWebDriver = _

  override def beforeEach(): Unit = {
    driver = new DriverFactory().initialise()
    implicitlyWait(Span(3, Seconds))
  }

  override def afterEach(): Unit =
    quit()

  override def withFixture(test: NoArgTest): Outcome = {
    val outcome = test()
    if (outcome.isExceptional) {
      val filename = test.name.replaceAll(" ", "_").replaceAll(":", "")
      setCaptureDir("./target/test-reports/html-report/screenshots/")
      capture to filename
      markup(s"<img src='screenshots/$filename.png' />")
    }
    outcome
  }

}
