package com.google.sps.servlets;

import java.io.IOException;
import java.util.*;
import com.google.gson.Gson;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/quoteServ")
public class QuoteServlet extends HttpServlet {
    private List<String> quotes;
    public void init(){
        quotes = new ArrayList<>();
        quotes.add("The elevator to success is out of order. You'll have to use the stairs, one step at a time.");
        quotes.add("I always wanted to be somebody, but now I realise I should have been more specific.");
        quotes.add("I am so clever that sometimes I don't understand a single word of what I am saying");
        quotes.add("People say nothing is impossible, but I do nothing every day");
        quotes.add("You can't have everything. Where would you put it?");
        quotes.add("Well-behaved women seldom make history.");
    }
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String quote = quotes.get((int) (Math.random() * quotes.size()));
        response.setContentType("text/html;");
        response.getWriter().println(quote);
  }
}
