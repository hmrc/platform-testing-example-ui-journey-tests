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

package uk.gov.hmrc.ui.utilities

import com.typesafe.config.{Config, ConfigFactory}

object TestConfiguration {

  private val config: Config        = ConfigFactory.load()
  private val env: String           = config.getString("environment")
  private val defaultConfig: Config = config.getConfig("local")
  private val envConfig: Config     = config.getConfig(env).withFallback(defaultConfig)

  def url(service: String): String = {
    val host = env match {
      case "local" => s"$serviceHost:${servicePort(service)}"
      case _       => s"${envConfig.getString("services.host")}"
    }

    s"$host${serviceRoute(service)}"
  }

  private def serviceHost: String = envConfig.getString("services.host")

  private def servicePort(service: String): String = envConfig.getString(s"services.$service.port")

  private def serviceRoute(service: String): String = envConfig.getString(s"services.$service.productionRoute")

}
