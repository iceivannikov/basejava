package com.ivannikov.webapp.web;

import com.ivannikov.webapp.Config;
import com.ivannikov.webapp.model.*;
import com.ivannikov.webapp.storage.Storage;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ResumeServlet extends HttpServlet {

    private Storage storage;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        storage = Config.getInstance().getStorage();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String uuid = req.getParameter("uuid");
        String action = req.getParameter("action");
        if (action == null) {
            req.setAttribute("resumes", storage.getAllSorted());
            req.getRequestDispatcher("/WEB-INF/jsp/list.jsp").forward(req, resp);
            return;
        }
        Resume resume = null;
        switch (action) {
            case "edit", "view" -> resume = storage.get(uuid);
            case "delete" -> {
                storage.delete(uuid);
                resp.sendRedirect("resume");
                return;
            }
            case "new" -> {
                req.setAttribute("resume", new Resume());
                req.getRequestDispatcher("/WEB-INF/jsp/new.jsp").forward(req, resp);
                return;
            }
        }
        req.setAttribute("resume", resume);
        req.getRequestDispatcher("view".equals(action) ? "/WEB-INF/jsp/view.jsp" : "/WEB-INF/jsp/edit.jsp")
                .forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.setCharacterEncoding("UTF-8");
        String uuid = req.getParameter("uuid");
        String fullName = req.getParameter("fullName");
        Resume resume;
        if (uuid == null) {
            resume = new Resume(fullName);
            storage.save(resume);
            uuid = resume.getUuid();
        }
        resume = storage.get(uuid);
        resume.setFullName(fullName);
        for (ContactType type : ContactType.values()) {
            String value = req.getParameter(type.name());
            if (value != null && !value.trim().isEmpty()) {
                resume.addContact(type, value);
            } else {
                resume.getContacts().remove(type);
            }
        }
        for (SectionType type : SectionType.values()) {
            String value = req.getParameter(type.name());
            String[] values = req.getParameterValues(type.name());
            if (value == null) {
                resume.getSections().remove(type);
            } else {
                switch (type) {
                    case PERSONAL, OBJECTIVE -> resume.setSection(type, new TextSection(value));
                    case ACHIEVEMENT, QUALIFICATIONS -> resume.setSection(type,
                                new ListSection(value.trim().split("\\n")));
                    case EDUCATION, EXPERIENCE -> {


//                        resume.addSection(type, section);
                    }
                }
            }
        }
        storage.update(resume);
        resp.sendRedirect("resume");
    }
}
