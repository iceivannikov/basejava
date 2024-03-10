package com.ivannikov.webapp.web;

import com.ivannikov.webapp.Config;
import com.ivannikov.webapp.model.*;
import com.ivannikov.webapp.storage.Storage;
import com.ivannikov.webapp.util.DateUtil;
import com.ivannikov.webapp.util.HtmlUtil;
import com.ivannikov.webapp.util.ResumeUtil;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
            case "delete" -> {
                storage.delete(uuid);
                resp.sendRedirect("resume");
                return;
            }
            case "view" -> resume = storage.get(uuid);
            case "new" -> {
                resume = new Resume();
                ResumeUtil.setEmptySections(resume);
            }
            case "edit" -> {
                resume = storage.get(uuid);
                ResumeUtil.setEmptySections(resume);
                for (SectionType type : new SectionType[]{SectionType.EXPERIENCE, SectionType.EDUCATION}) {
                    OrganizationSection section = (OrganizationSection) resume.getSection(type);
                    List<Organization> emptyFirstOrganizations = new ArrayList<>();
                    emptyFirstOrganizations.add(Organization.EMPTY);
                    if (section != null) {
                        for (Organization org : section.getOrganizations()) {
                            List<Organization.Period> emptyFirstPositions = new ArrayList<>();
                            emptyFirstPositions.add(Organization.Period.EMPTY);
                            emptyFirstPositions.addAll(org.getPeriods());
                            emptyFirstOrganizations.add(new Organization(org.getName(), org.getWebsite(), emptyFirstPositions));
                        }
                    }
                    resume.setSection(type, new OrganizationSection(emptyFirstOrganizations));
                }
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

        boolean isExistResume = uuid != null && !uuid.isEmpty();
        Resume resume = isExistResume ? storage.get(uuid) : new Resume("");
        resume.setFullName(fullName);

        for (ContactType type : ContactType.values()) {
            String value = req.getParameter(type.name());
            if (HtmlUtil.isEmpty(value)) {
                resume.getContacts().remove(type);
            } else {
                resume.setContact(type, value);
            }
        }
        for (SectionType type : SectionType.values()) {
            String value = req.getParameter(type.name());
            String[] values = req.getParameterValues(type.name());
            if (HtmlUtil.isEmpty(value) && values.length < 2) {
                resume.getSections().remove(type);
            } else {
                switch (type) {
                    case PERSONAL, OBJECTIVE -> resume.setSection(type,
                            new TextSection(value.trim().replaceAll("\\r\\n", "")));
                    case ACHIEVEMENT, QUALIFICATIONS -> {
                        List<String> listSections = new ArrayList<>();
                        String[] split = value.split("\\n");
                        for (String s : split) {
                            if (!HtmlUtil.isEmpty(value)) {
                                String string = s.trim().replaceAll("\\r", "");
                                listSections.add(string);
                            }
                        }
                        resume.setSection(type, new ListSection(listSections));
                    }
                    case EDUCATION, EXPERIENCE -> {
                        List<Organization> organizations = new ArrayList<>();
                        String[] orgWebsites = req.getParameterValues(type.name() + "website");
                        for (int i = 0; i < values.length; i++) {
                            String orgName = values[i];
                            if (HtmlUtil.isEmpty(orgName)) {
                                String[] periodNames = req.getParameterValues(type.name() + "periodName" + i);
                                String[] periodDescriptions = req.getParameterValues(type.name() + "periodDescription" + i);
                                String[] periodStarts = req.getParameterValues(type.name() + "periodStart" + i);
                                String[] periodEnds = req.getParameterValues(type.name() + "periodEnd" + i);
                                List<Organization.Period> periods = new ArrayList<>();
                                for (int j = 0; j < periodNames.length; j++) {
                                    if (!HtmlUtil.isEmpty(periodDescriptions[j])) {
                                        periods.add(new Organization.Period(periodNames[j], periodDescriptions[j],
                                                DateUtil.parse(periodStarts[j]), DateUtil.parse(periodEnds[j])));
                                    }
                                }
                                organizations.add(new Organization(orgName, orgWebsites[i], periods));
                            }
                        }
                        resume.setSection(type, new OrganizationSection(organizations));
                    }
                }
            }
        }
        if (isExistResume) {
            storage.update(resume);
        } else {
            storage.save(resume);
        }
        resp.sendRedirect("resume");
    }
}
