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

@WebServlet("/commentServ")
public class CommentServlet extends HttpServlet{
    private final HashMap<String, String> commentsHistory =  new HashMap<>();
    @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
      
      String name = getParameter(request, "name", "");
      String comment = getParameter(request, "comment", "");
      commentsHistory.put(name, comment);

      //Datastore Steps
      Entity commEntity = new Entity("CommentTask");
      commEntity.setProperty("Name", name);
      commEntity.setProperty("Comment", comment);

      DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
      datastore.put(commEntity);

      response.sendRedirect("/comments.html");
  }
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
      response.setContentType("application/json");
      String jsonComment = new Gson().toJson(commentsHistory);
      response.getWriter().println(jsonComment);
  }

//   public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
//       Query query = new Query("CommentTask").addSort("Last", SortDirection.DESCENDING);
//       DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
//       PreparedQuery results = datastore.prepare(query);

//       ArrayList<Comment> comments_list = new ArrayList<>();
//       for (Entity entity : results.asIterable()) {
//           long id = entity.getKey().getId();
//           String name = (String) entity.getProperty("Name");
//           String comment = (String) entity.getProperty("Comment");

//           Comment newComment = new Comment(id, name, comment);
//           comments_list.add(newComment);
//       }
//       Gson gson = new Gson();
//       response.setContentType("application/json;");
//       response.getWriter().println(gson.toJson(comments_list));
//   }

  private String getParameter(HttpServletRequest request, String name, String defaultValue) {
      String value = request.getParameter(name);
      if(value == null) {
          return defaultValue;
      }
      return value;
  }

}