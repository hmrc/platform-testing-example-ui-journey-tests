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

import uk.gov.hmrc.ui.pages.{CostOfGoods, Result, Turnover, VATReturnPeriod}

class CheckVATFlatRateSpec extends BaseSpec {

  val vatReturnPeriod: VATReturnPeriod = new VATReturnPeriod
  val turnover: Turnover               = new Turnover
  val costOfGoods: CostOfGoods         = new CostOfGoods
  val result: Result                   = new Result

  Feature("Check VAT flat rate") {

    Scenario("User pays annually and is a limited cost business") {

      Given("my VAT return period is annual")
      go to vatReturnPeriod
      radioButtonGroup(vatReturnPeriod.radioButtonGroup).value = "annually"
      submit()

      And("my turnover for the year is £1000")
      numberField(turnover.input).value = "1000"
      submit()

      When("my cost of goods for the year is £999")
      numberField(costOfGoods.input).value = "999"
      submit()

      Then("I can use the 16.5% VAT flat rate")
      id(result.outcome).element.text should be(result.useSetVATFlatRate)

    }

    Scenario("User pays annually and is not a limited cost business") {

      Given("my VAT return period is annual")
      go to vatReturnPeriod
      radioButtonGroup(vatReturnPeriod.radioButtonGroup).value = "annually"
      submit()

      And("my turnover for the year is £1000")
      numberField(turnover.input).value = "1000"
      submit()

      When("my cost of goods for the year is £1000")
      numberField(costOfGoods.input).value = "1000"
      submit()

      Then("I can use the VAT flat rate")
      id(result.outcome).element.text should be(result.useUniqueVATFlatRate)

    }

    Scenario("User pays quarterly and is a limited cost business") {

      Given("my VAT return period is quarterly")
      go to vatReturnPeriod
      radioButtonGroup(vatReturnPeriod.radioButtonGroup).value = "quarterly"
      submit()

      And("my turnover for the quarter is £1000")
      numberField(turnover.input).value = "1000"
      submit()

      When("my cost of goods for the quarter is £249")
      numberField(costOfGoods.input).value = "249"
      submit()

      Then("I can use the 16.5% VAT flat rate")
      id(result.outcome).element.text should be(result.useSetVATFlatRate)

    }

    Scenario("User pays quarterly and is not a limited cost business") {

      Given("my VAT return period is quarterly")
      go to vatReturnPeriod
      radioButtonGroup(vatReturnPeriod.radioButtonGroup).value = "quarterly"
      submit()

      And("my turnover for the quarter is £1000")
      numberField(turnover.input).value = "1000"
      submit()

      When("my cost of goods for the quarter is £250")
      numberField(costOfGoods.input).value = "250"
      submit()

      Then("I can use the VAT flat rate")
      id(result.outcome).element.text should be(result.useUniqueVATFlatRate)

    }

  }

}
