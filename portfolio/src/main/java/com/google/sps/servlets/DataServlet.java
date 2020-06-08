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

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.SortDirection;
import com.google.sps.data.Comment;
import com.google.gson.Gson;
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
  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
      
      //HTML Form Steps
      String first = getParameter(request, "fname", "");
      String last = getParameter(request, "lname", "");
      String firstLast = String.format("%s %s",first,last);
      String comment = getParameter(request, "comment", "");

      HashMap<String, String> name_comment = new HashMap<String, String>();
      name_comment.put(firstLast, comment);

      //Datastore Steps
      Entity CommTaskEntity = new Entity("CommentTask");
      CommTaskEntity.setProperty("First", first);
      CommTaskEntity.setProperty("Last", last);
      CommTaskEntity.setProperty("Comment", comment);

      DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
      datastore.put(CommTaskEntity);

      response.setContentType("text/html");
      response.getWriter().println(name_comment);

      response.sendRedirect("/index.html");
  }

// BLOCKED: (explanation for commented lines 23 & 66 - 85)
// Getting Compling error: "/DataServlet.java:[23,27] package com.google.sps.data does not exist"
// not sure why, but wanted to go ahead and submit for a PR to stay on track
// The folowing lines are step 8 on the Datastore tutorial,
// steps 1 - 7 are are complete and compile fine

  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
      Query query = new Query("CommentTask").addSort("Last", SortDirection.DESCENDING);
      DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
      PreparedQuery results = datastore.prepare(query);

      ArrayList<Comment> tasks_list = new ArrayList<>();
      for (Entity entity : results.asIterable()) {
          long id = entity.getKey().getId();
          String first = (String) entity.getProperty("First");
          String last = (String) entity.getProperty("Last");
          String comment = (String) entity.getProperty("Comment");

          Comment commtask = new Comment(id,first,last,comment);
          tasks_list.add(commtask);
      }
      Gson gson = new Gson();
      response.setContentType("application/json;");
      response.getWriter().println(gson.toJson(tasks_list));
  }

  private String getParameter(HttpServletRequest request, String name, String defaultValue) {
      String value = request.getParameter(name);
      if(value == null) {
          return defaultValue;
      }
      return value;
  }

}
