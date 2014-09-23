package com.magg.wiki.analytics.servlet;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.magg.wiki.analytics.service.LinkService;


public class LinksServlet extends HttpServlet {

    private static final long serialVersionUID = -2694690334161871247L;
    
    private LinkService service;
    
    @Override
    public void init() throws ServletException {
        service = new LinkService();
    }
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse response) throws ServletException, IOException {
        String linkName = req.getParameter("q");
        Map<String, String> results = service.fetch(linkName);

        response.setContentType("application/json");

        Gson g = new Gson();
        g.toJson(results, response.getWriter());
        response.getWriter().flush();
    }

}
