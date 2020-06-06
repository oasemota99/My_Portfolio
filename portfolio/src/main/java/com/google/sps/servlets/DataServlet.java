// Copyright 2019 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     https://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.google.sps.servlets;

import java.io.IOException;
import java.util.*;
import com.google.gson.Gson;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/** Servlet that returns some example content. TODO: modify this file to handle comments data */
@WebServlet("/data")
public class DataServlet extends HttpServlet {
    private List<String> quotes;
    private ArrayList<String> messages;
    @Override
    public void init() {
        quotes = new ArrayList<>();
        quotes.add("The elevator to success is out of order. You'll have to use the stairs, one step at a time.");
        quotes.add("I always wanted to be somebody, but now I realise I should have been more specific.");
        quotes.add("I am so clever that sometimes I don't understand a single word of what I am saying");
        quotes.add("People say nothing is impossible, but I do nothing every day");
        quotes.add("You can't have everything. Where would you put it?");
        quotes.add("Well-behaved women seldom make history.");
    }
    public ArrayList create_messages() {
        messages = new ArrayList<>();
        messages.add("I am a message.");
        messages.add("Wow that last message is so self aware.");
        messages.add("Wait, you all can talk?");
        return m;
    }
    public String toJson(ArrayList<String> alist) {
        Gson gson = new Gson();
        String json = gson.toJson(alist);
        return json;
    }

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    String quote = quotes.get((int) (Math.random() * quotes.size()));
    ArrayList<String> msg_array = create_messages();
    String jsonMsg = toJson(msg_array);
    response.setContentType("application/json;");
    response.getWriter().println(jsonMsg);
  }
}
