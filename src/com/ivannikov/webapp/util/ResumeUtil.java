package com.ivannikov.webapp.util;

import com.ivannikov.webapp.model.*;

public class ResumeUtil {
    private ResumeUtil() {
    }

    public static void setEmptySections(Resume resume) {
        SectionType[] values = SectionType.values();
        for (SectionType type : values) {
            Section section = resume.getSection(type);
            if (section == null) {
                switch (type) {
                    case PERSONAL, OBJECTIVE -> resume.addSection(type, new TextSection(""));
                    case ACHIEVEMENT, QUALIFICATIONS -> resume.addSection(type, new ListSection(""));
                    case EDUCATION, EXPERIENCE -> resume.addSection(type,
                            new OrganizationSection(
                                    new Organization("", "",
                                            new Organization.
                                                    Period("", "", 1, 1, 1, 1))));
                }
            }
        }
    }
}
