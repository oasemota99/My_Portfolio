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
  
  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
     DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
     List<Comment> loadComments = new ArrayList<>();

     Query query = new Query("Comment").addSort("TimeStamp", SortDirection.ASCENDING);
     PreparedQuery results = datastore.prepare(query);
     
     for (Entity commEntity : results.asIterable()) {
      
      long id = commEntity.getKey().getId();
      String name = (String) commEntity.getProperty("Name");
      String comment = (String) commEntity.getProperty("Comment");
      long timestamp = (long) commEntity.getProperty("TimeStamp");

      Comment commentObj = new Comment(id, name, comment, timestamp);

      loadComments.add(commentObj);
    }

    Gson gson = new Gson();
    response.setContentType("application/json");
    response.getWriter().println(gson.toJson(loadComments)); 

  }

  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
      
      String name = getParameter(request, "name", "");
      String comment = getParameter(request, "comment", "");
      long timestamp = System.currentTimeMillis();

      Entity commEntity = new Entity("Comment");
      commEntity.setProperty("Name", name);
      commEntity.setProperty("Comment", comment);
      commEntity.setProperty("TimeStamp", timestamp);

      DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
      datastore.put(commEntity);

      response.sendRedirect("/comments.html");
  }

  private String getParameter(HttpServletRequest request, String name, String defaultValue) {
      String value = request.getParameter(name);
      if(value == null) {
          return defaultValue;
      }
      return value;
  }

}

