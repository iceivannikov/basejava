package com.ivannikov.webapp;

import com.ivannikov.webapp.model.*;

import java.time.LocalDate;
import java.util.*;

public class ResumeTestData {
    public static void main(String[] args) {
        Map<ContactType, String> contacts = getContacts();

        Section achievementsSection = new ListSection(getAchievementsList());
        Section qualificationSection = new ListSection(getQualificationList());
        OrganizationSection organizationJob = new OrganizationSection(getOrganizationsJob());
        OrganizationSection organizationStudies = new OrganizationSection(getOrganizationsStudies());


        Map<SectionType, Section> sections = new HashMap<>();
        sections.put(SectionType.OBJECTIVE, getPersonalQualities());
        sections.put(SectionType.PERSONAL, getPosition());
        sections.put(SectionType.ACHIEVEMENT, achievementsSection);
        sections.put(SectionType.QUALIFICATIONS, qualificationSection);
        sections.put(SectionType.EXPERIENCE, organizationJob);
        sections.put(SectionType.EDUCATION, organizationStudies);


        Resume resume = new Resume("1", "Григорий Кислин", contacts, sections);
        printResume(resume);
    }

    private static void printResume(Resume resume) {
        String fullName = resume.getFullName();
        System.out.println(fullName);

        Map<ContactType, String> contacts = resume.getContacts();
        ContactType[] contactTypes = ContactType.values();
        for (ContactType contactType : contactTypes) {
            System.out.printf("%s: ", contactType.getTitle());
            String contact = contacts.get(contactType);
            System.out.println(contact);
        }

        Map<SectionType, Section> sections = resume.getSections();
        SectionType[] sectionTypes = SectionType.values();
        for (SectionType sectionType : sectionTypes) {
            System.out.println(sectionType.getTitle());
            Section section = sections.get(sectionType);
            section.print();
        }
    }

    private static Map<ContactType, String> getContacts() {
        Map<ContactType, String> contacts = new HashMap<>();
        contacts.put(ContactType.TELEPHONE, "+7(921) 855-0482");
        contacts.put(ContactType.SKYPE, "skype:grigory.kislin");
        contacts.put(ContactType.EMAIL, "gkislin@yandex.ru");
        contacts.put(ContactType.PROFILE_LINKEDIN, "https://www.linkedin.com/in/gkislin");
        contacts.put(ContactType.PROFILE_GITHUB, "https://github.com/gkislin");
        contacts.put(ContactType.PROFILE_STACKOVERFLOW, "https://stackoverflow.com/users/548473");
        contacts.put(ContactType.WEBSITE, "http://gkislin.ru/");
        return contacts;
    }

    private static TextSection getPosition() {
        return new TextSection("Аналитический склад ума, сильная логика, креативность, инициативность. " +
                "Пурист кода и архитектуры.");
    }

    private static TextSection getPersonalQualities() {
        return new TextSection("Ведущий стажировок и корпоративного обучения по Java Web и Enterprise технологиям");
    }

    private static List<String> getAchievementsList() {
        List<String> achievementsList = new ArrayList<>();
        achievementsList.add("Организация команды и успешная реализация Java проектов для сторонних заказчиков: " +
                "приложения автопарк на стеке Spring Cloud/микросервисы, система мониторинга показателей " +
                "спортсменов на Spring Boot, участие в проекте МЭШ на Play-2, многомодульный Spring Boot + Vaadin " +
                "проект для комплексных DIY смет");
        achievementsList.add("С 2013 года: разработка проектов \"Разработка Web приложения\",\"Java Enterprise\", " +
                "\"Многомодульный maven. Многопоточность. XML (JAXB/StAX). Веб сервисы (JAX-RS/SOAP). " +
                "Удаленное взаимодействие (JMS/AKKA)\". Организация онлайн стажировок и ведение проектов. " +
                "Более 3500 выпускников.");
        achievementsList.add("Реализация двухфакторной аутентификации для онлайн платформы управления проектами " +
                "Wrike. Интеграция с Twilio, DuoSecurity, Google Authenticator, Jira, Zendesk.");
        achievementsList.add("Налаживание процесса разработки и непрерывной интеграции ERP системы River BPM. " +
                "Интеграция с 1С, Bonita BPM, CMIS, LDAP. Разработка приложения управления окружением на стеке: " +
                "Scala/Play/Anorm/JQuery. Разработка SSO аутентификации и авторизации различных ERP модулей, " +
                "интеграция CIFS/SMB java сервера.");
        achievementsList.add("Реализация c нуля Rich Internet Application приложения на стеке технологий JPA, " +
                "Spring, Spring-MVC, GWT, ExtGWT (GXT), Commet, HTML5, Highstock для алгоритмического трейдинга.");
        achievementsList.add("Создание JavaEE фреймворка для отказоустойчивого взаимодействия слабо-связанных " +
                "сервисов (SOA-base архитектура, JAX-WS, JMS, AS Glassfish). Сбор статистики сервисов и информации " +
                "о состоянии через систему мониторинга Nagios. Реализация онлайн клиента для администрирования " +
                "и мониторинга системы по JMX (Jython/ Django).");
        achievementsList.add("Реализация протоколов по приему платежей всех основных платежных системы России " +
                "(Cyberplat, Eport, Chronopay, Сбербанк), Белоруcсии(Erip, Osmp) и Никарагуа.");
        return achievementsList;
    }

    private static List<String> getQualificationList() {
        List<String> qualificationList = new ArrayList<>();
        qualificationList.add("JEE AS: GlassFish (v2.1, v3), OC4J, JBoss, Tomcat, Jetty, WebLogic, WSO2");
        qualificationList.add("Version control: Subversion, Git, Mercury, ClearCase, Perforce");
        qualificationList.add("DB: PostgreSQL(наследование, pgplsql, PL/Python), Redis (Jedis), H2, Oracle, MySQL, " +
                "SQLite, MS SQL, HSQLDB");
        qualificationList.add("Languages: Java, Scala, Python/Jython/PL-Python, JavaScript, Groovy");
        qualificationList.add("XML/XSD/XSLT, SQL, C/C++, Unix shell scripts");
        qualificationList.add("Java Frameworks: Java 8 (Time API, Streams), Guava, Java Executor, MyBatis, Spring " +
                "(MVC, Security, Data, Clouds, Boot), JPA (Hibernate, EclipseLink), Guice, GWT(SmartGWT, " +
                "ExtGWT/GXT), Vaadin, Jasperreports, Apache Commons, Eclipse SWT, JUnit, Selenium (htmlelements).");
        qualificationList.add("Python: Django.");
        qualificationList.add("JavaScript: jQuery, ExtJS, Bootstrap.js, underscore.js");
        qualificationList.add("Scala: SBT, Play2, Specs2, Anorm, Spray, Akka");
        qualificationList.add("Технологии: Servlet, JSP/JSTL, JAX-WS, REST, EJB, RMI, JMS, JavaMail, JAXB, StAX, " +
                "SAX, DOM, XSLT, MDB, JMX, JDBC, JPA, JNDI, JAAS, SOAP, AJAX, Commet, HTML5, ESB, CMIS, BPMN2, " +
                "LDAP, OAuth1, OAuth2, JWT.");
        qualificationList.add("Инструменты: Maven + plugin development, Gradle, настройка Ngnix");
        qualificationList.add("администрирование Hudson/Jenkins, Ant + custom task, SoapUI, JPublisher, Flyway, " +
                "Nagios, iReport, OpenCmis, Bonita, pgBouncer");
        qualificationList.add("Отличное знание и опыт применения концепций ООП, SOA, шаблонов проектрирования, " +
                "архитектурных шаблонов, UML, функционального программирования.");
        qualificationList.add("Родной русский, английский \"upper intermediate\".");
        return qualificationList;
    }

    private static List<Organization> getOrganizationsJob() {
        Organization JavaOnlineProjects = new Organization(
                "Java Online Projects",
                "http://javaops.ru/",
                List.of(new Period(
                        "Автор проекта.",
                        "Создание, организация и проведение Java онлайн проектов и стажировок.",
                        LocalDate.of(2013, 10, 1),
                        LocalDate.now())));
        Organization Wrike = new Organization(
                "Wrike",
                "https://www.wrike.com/",
                List.of(new Period(
                        "Старший разработчик (backend)",
                        "Проектирование и разработка онлайн платформы управления проектами Wrike " +
                                "(Java 8 API, Maven, Spring, MyBatis, Guava, Vaadin, PostgreSQL, Redis). " +
                                "Двухфакторная аутентификация, авторизация по OAuth1, OAuth2, JWT SSO.",
                        LocalDate.of(2014, 10, 1),
                        LocalDate.of(2016, 1, 1))));
        Organization RITCenter = new Organization(
                "RIT Center",
                " ",
                List.of(new Period(
                        "Java архитектор",
                        "Организация процесса разработки системы ERP для разных окружений: релизная " +
                                "политика, версионирование, ведение CI (Jenkins), миграция базы (кастомизация " +
                                "Flyway), конфигурирование системы (pgBoucer, Nginx), AAA via SSO. Архитектура БД и " +
                                "серверной части системы. Разработка интергационных сервисов: CMIS, BPMN2, 1C " +
                                "(WebServices), сервисов общего назначения (почта, экспорт в pdf, doc, html). " +
                                "Интеграция Alfresco JLAN для online редактирование из браузера документов MS " +
                                "Office. Maven + plugin development, Ant, Apache Commons, Spring security, " +
                                "Spring MVC, Tomcat,WSO2, xcmis, OpenCmis, Bonita, Python scripting, Unix shell " +
                                "remote scripting via ssh tunnels, PL/Python",
                        LocalDate.of(2012, 4, 1),
                        LocalDate.of(2014, 10, 1))));
        List<Organization> organizations = new ArrayList<>();
        organizations.add(RITCenter);
        organizations.add(Wrike);
        organizations.add(JavaOnlineProjects);
        return organizations;
    }

    private static List<Organization> getOrganizationsStudies() {
        Organization Coursera = new Organization(
                "Coursera",
                "https://www.coursera.org/course/progfun",
                List.of(new Period(
                        "",
                        "'Functional Programming Principles in Scala' by Martin Odersky",
                        LocalDate.of(2013, 3, 1),
                        LocalDate.of(2013, 5, 1))));
        Organization Luxoft = new Organization(
                "Luxoft",
                "http://www.luxoft-training.ru/training/catalog/course.html?ID=22366",
                List.of(new Period(
                        "",
                        "Курс 'Объектно-ориентированный анализ ИС. Концептуальное моделирование на UML.'",
                        LocalDate.of(2011, 3, 1),
                        LocalDate.of(2011, 4, 1))));
        Organization SiemensAG = new Organization(
                "Siemens AG",
                "http://www.siemens.ru/",
                List.of(new Period(
                        "",
                        "3 месяца обучения мобильным IN сетям (Берлин)",
                        LocalDate.of(2005, 1, 1),
                        LocalDate.of(2005, 4, 1))));
        List<Organization> organizations = new ArrayList<>();
        organizations.add(SiemensAG);
        organizations.add(Luxoft);
        organizations.add(Coursera);
        return organizations;
    }
}
