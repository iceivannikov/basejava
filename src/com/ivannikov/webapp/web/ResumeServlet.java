package com.ivannikov.webapp.web;

import com.ivannikov.webapp.Config;
import com.ivannikov.webapp.model.Resume;
import com.ivannikov.webapp.storage.Storage;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class ResumeServlet extends HttpServlet {

    private Storage storage;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        storage = Config.getInstance().getStorage();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
//        SqlHelper helper = new SqlHelper("jdbc:postgresql://localhost:5432/resumes", "postgres", "postgres");
//        SqlStorage storage = new SqlStorage(helper);
        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();
        out.println("<html><head><title>Resume Table</title></head><body>");
        out.println("<h2>Resume Table</h2>");
        out.println("<table border='1'>");
        out.println("<tr><th>UUID</th><th>Full Name</th></tr>");
        List<Resume> allSorted = storage.getAllSorted();
        for (Resume resume : allSorted) {
            out.println("<tr>");
            out.println("<td>" + resume.getUuid() + "</td>");
            out.println("<td>" + resume.getFullName() + "</td>");
            out.println("</tr>");
        }
        out.println("</table>");
        out.println("</body></html>");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }
}
