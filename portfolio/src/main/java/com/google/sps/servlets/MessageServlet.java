package com.google.sps.servlets;

import java.io.IOException;
import java.util.*;
import com.google.gson.Gson;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/messageServ")
public class MessageServlet extends HttpServlet {
    private ArrayList<String> messages;

    public ArrayList create_messages() {
        messages = new ArrayList<>();
        messages.add("I am a message.");
        messages.add("Wow that last message is so self aware.");
        messages.add("Wait, you all can talk?");
        return messages;
    }
    public String toJson(ArrayList<String> alist){
        Gson gson = new Gson();
        String json = gson.toJson(alist);
        return json;
    }
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    ArrayList<String> msg_array = create_messages();
    String jsonMsg = toJson(msg_array);
    response.setContentType("application/json;");
    response.getWriter().println(jsonMsg);
    }

}