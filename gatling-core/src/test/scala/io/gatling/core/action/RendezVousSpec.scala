/**
 * Copyright 2011-2015 eBusiness Information, Groupe Excilys (www.ebusinessinformation.fr)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * 		http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.gatling.core.action

import akka.testkit._
import io.gatling.AkkaSpec

import io.gatling.core.result.message.End
import io.gatling.core.result.writer.UserMessage
import io.gatling.core.session.Session

class RendezVousSpec extends AkkaSpec {

  "RendezVous" should "block the specified number of sessions until they have all reached it" in {
    val rendezVous = TestActorRef(RendezVous.props(3, self))

    val session = Session("scenario", "userId")

    rendezVous ! session
    expectNoMsg()

    rendezVous ! session
    expectNoMsg()

    rendezVous ! session
    expectMsgAllOf(session, session, session)

    rendezVous ! session
    expectMsg(session)
  }
}

