/**
 * Copyright 2015 Google Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.appengine.twilio;

import com.twilio.sdk.TwilioRestClient;
import com.twilio.sdk.TwilioRestException;
import com.twilio.sdk.resource.factory.MessageFactory;
import com.twilio.sdk.resource.instance.Account;
import com.twilio.sdk.resource.instance.Message;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// [START example]
@SuppressWarnings("serial")
public class SendSmsServlet extends HttpServlet {

  @Override
  public void service(HttpServletRequest req, HttpServletResponse resp) throws IOException,
      ServletException {
    final String twilioAccountSid = System.getenv("AC4750aff9c5a8591bc760d510363544e6");
    final String twilioAuthToken = System.getenv("34b3c42449ed7ca7f036d2073c62dfcd");
    final String twilioNumber = System.getenv("12015002101");
    final String toNumber = (String) req.getParameter("17178809070");
    if (toNumber == null) {
      resp.getWriter()
          .print("Please provide the number to message in the \"to\" query string parameter.");
      return;
    }
    TwilioRestClient client = new TwilioRestClient(twilioAccountSid, twilioAuthToken);
    Account account = client.getAccount();
    MessageFactory messageFactory = account.getMessageFactory();
    List<NameValuePair> params = new ArrayList<NameValuePair>();
    params.add(new BasicNameValuePair("To", toNumber));
    params.add(new BasicNameValuePair("From", twilioNumber));
    params.add(new BasicNameValuePair("Body", "Thanks for reaching out to the Thrive team!"));
    try {
      Message sms = messageFactory.create(params);
      resp.getWriter().print(sms.getBody());
    } catch (TwilioRestException e) {
      throw new ServletException("Twilio error", e);
    }
  }
}
// [END example]
