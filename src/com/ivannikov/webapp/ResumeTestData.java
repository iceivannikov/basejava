package com.ivannikov.webapp;

import com.ivannikov.webapp.model.*;
import com.ivannikov.webapp.util.DateUtil;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ResumeTestData {

    private ResumeTestData() {
    }

    public static void main(String[] args) {

        Resume resume = new Resume("1", "Григорий Кислин");
        printResume(resume);
    }

    public static Resume newResume(String uuid, String name) {
        return new Resume(uuid, name);
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
            System.out.println(section);
        }
    }

    public static void getContacts(Resume resume) {
//        Map<ContactType, String> contacts = new HashMap<>();
        resume.addContact(ContactType.TELEPHONE, "+7(921) 855-0482");
        resume.addContact(ContactType.SKYPE, "skype:grigory.kislin");
        resume.addContact(ContactType.EMAIL, "gkislin@yandex.ru");
        resume.addContact(ContactType.PROFILE_LINKEDIN, "https://www.linkedin.com/in/gkislin");
        resume.addContact(ContactType.PROFILE_GITHUB, "https://github.com/gkislin");
        resume.addContact(ContactType.PROFILE_STACKOVERFLOW, "https://stackoverflow.com/users/548473");
        resume.addContact(ContactType.WEBSITE, "https://gkislin.ru/");
    }

    private static Map<SectionType, Section> getSections() {
        Map<SectionType, Section> sections = new HashMap<>();
        sections.put(SectionType.OBJECTIVE, getPersonalQualities());
        sections.put(SectionType.PERSONAL, getPosition());
        sections.put(SectionType.ACHIEVEMENT, getAchievementsList());
        sections.put(SectionType.QUALIFICATIONS, getQualificationList());
        sections.put(SectionType.EXPERIENCE, getOrganizationsJob());
        sections.put(SectionType.EDUCATION, getOrganizationsStudies());
        return sections;
    }

    public static TextSection getPersonalQualities() {
        return new TextSection("Ведущий стажировок и корпоративного обучения по Java Web и Enterprise технологиям");
    }

    public static TextSection getPosition() {
        return new TextSection("Аналитический склад ума, сильная логика, креативность, инициативность. Пурист кода и архитектуры.");
    }

    public static ListSection getAchievementsList() {
        List<String> list = new ArrayList<>();
        list.add("Организация команды и успешная реализация Java проектов для сторонних заказчиков: приложения " +
                "автопарк на стеке Spring Cloud/микросервисы, система мониторинга показателей спортсменов на " +
                "Spring Boot, участие в проекте МЭШ на Play-2, многомодульный Spring Boot + Vaadin " +
                "проект для комплексных DIY смет");
        list.add("С 2013 года: разработка проектов \"Разработка Web приложения\",\"Java Enterprise\", " +
                "\"Многомодульный maven. Многопоточность. XML (JAXB/StAX). Веб сервисы (JAX-RS/SOAP). " +
                "Удаленное взаимодействие (JMS/AKKA)\". Организация онлайн стажировок и ведение проектов. " +
                "Более 3500 выпускников.");
        list.add("Налаживание процесса разработки и непрерывной интеграции ERP системы River BPM. Интеграция с 1С, " +
                "Bonita BPM, CMIS, LDAP. Разработка приложения управления окружением на стеке: " +
                "Scala/Play/Anorm/JQuery. Разработка SSO аутентификации и авторизации различных ERP модулей, " +
                "интеграция CIFS/SMB java сервера.");
        list.add("Реализация c нуля Rich Internet Application приложения на стеке технологий JPA, Spring, Spring-MVC, " +
                "GWT, ExtGWT (GXT), Commet, HTML5, Highstock для алгоритмического трейдинга.");
        list.add("Реализация c нуля Rich Internet Application приложения на стеке технологий JPA, Spring, Spring-MVC, " +
                "GWT, ExtGWT (GXT), Commet, HTML5, Highstock для алгоритмического трейдинга.");
        list.add("Создание JavaEE фреймворка для отказоустойчивого взаимодействия слабо-связанных сервисов " +
                "(SOA-base архитектура, JAX-WS, JMS, AS Glassfish). Сбор статистики сервисов и информации о " +
                "состоянии через систему мониторинга Nagios. Реализация онлайн клиента для администрирования и " +
                "мониторинга системы по JMX (Jython/ Django).");
        list.add("Реализация протоколов по приему платежей всех основных платежных системы России (Cyberplat, " +
                "Eport, Chronopay, Сбербанк), Белоруcсии(Erip, Osmp) и Никарагуа.");
        return new ListSection(list);
    }

    public static ListSection getQualificationList() {
        List<String> list = new ArrayList<>();
        list.add("JEE AS: GlassFish (v2.1, v3), OC4J, JBoss, Tomcat, Jetty, WebLogic, WSO2");
        list.add("Version control: Subversion, Git, Mercury, ClearCase, Perforce");
        list.add("DB: PostgreSQL(наследование, pgplsql, PL/Python), Redis (Jedis), H2, Oracle, MySQL, SQLite, MS SQL, HSQLDB");
        list.add("Languages: Java, Scala, Python/Jython/PL-Python, JavaScript, Groovy");
        list.add("XML/XSD/XSLT, SQL, C/C++, Unix shell scripts");
        list.add("Java Frameworks: Java 8 (Time API, Streams), Guava, Java Executor, MyBatis, Spring (MVC, " +
                "Security, Data, Clouds, Boot), JPA (Hibernate, EclipseLink), Guice, GWT(SmartGWT, ExtGWT/GXT), " +
                "Vaadin, Jasperreports, Apache Commons, Eclipse SWT, JUnit, Selenium (htmlelements).");
        list.add("Python: Django.");
        list.add("JavaScript: jQuery, ExtJS, Bootstrap.js, underscore.js");
        list.add("Scala: SBT, Play2, Specs2, Anorm, Spray, Akka");
        list.add("Технологии: Servlet, JSP/JSTL, JAX-WS, REST, EJB, RMI, JMS, JavaMail, JAXB, StAX, " +
                "SAX, DOM, XSLT, MDB, JMX, JDBC, JPA, JNDI, JAAS, SOAP, AJAX, Commet, HTML5, ESB, CMIS, " +
                "BPMN2,LDAP, OAuth1, OAuth2, JWT.");
        list.add("Инструменты: Maven + plugin development, Gradle, настройка Nginx");
        list.add("администрирование Hudson/Jenkins, Ant + custom task, SoapUI, JPublisher, Flyway, " +
                "Nagios, iReport, OpenCmis, Bonita, pgBouncer");
        list.add("Отличное знание и опыт применения концепций ООП, SOA, шаблонов проектирования, архитектурных " +
                "шаблонов, UML, функционального программирования.");
        list.add("Родной русский, английский \"upper intermediate\"");

        return new ListSection(list);
    }

    public static OrganizationSection getOrganizationsJob() {
        Organization JavaOnlineProjects = new Organization(
                "Java Online Projects",
                "https://javaops.ru/",
                List.of(new Organization.Period(
                        "Автор проекта.",
                        "Создание, организация и проведение Java онлайн проектов и стажировок.",
                        DateUtil.of(2013, 10),
                        LocalDate.now())));
        Organization Wrike = new Organization(
                "Wrike",
                "https://www.wrike.com/",
                List.of(new Organization.Period(
                        "Старший разработчик (backend)",
                        "Проектирование и разработка онлайн платформы управления проектами Wrike (Java 8 API, " +
                                "Maven, Spring, MyBatis, Guava, Vaadin, PostgreSQL, Redis). Двухфакторная " +
                                "аутентификация, авторизация по OAuth1, OAuth2, JWT SSO.",
                        DateUtil.of(2014, 10),
                        DateUtil.of(2016, 1))));
        Organization RITCenter = new Organization(
                "RIT Center",
                " ",
                List.of(new Organization.Period(
                        "Java архитектор",
                        "Организация процесса разработки системы ERP для разных окружений: релизная политика, " +
                                "версионирование, ведение CI (Jenkins), миграция базы (кастомизация Flyway), " +
                                "конфигурирование системы (pgBoucer, Nginx), AAA via SSO. Архитектура БД и серверной " +
                                "части системы. Разработка интеграционных сервисов: CMIS, BPMN2, 1C (WebServices), " +
                                "сервисов общего назначения (почта, экспорт в pdf, doc, html). Интеграция Alfresco " +
                                "JLAN для online редактирование из браузера документов MS Office. Maven + plugin " +
                                "development, Ant, Apache Commons, Spring security, Spring MVC, Tomcat,WSO2, xcmis, " +
                                "OpenCmis, Bonita, Python scripting, Unix shell remote scripting via ssh tunnels, PL/Python",
                        DateUtil.of(2012, 4),
                        DateUtil.of(2014, 10))));
        List<Organization> organizations = new ArrayList<>();
        organizations.add(JavaOnlineProjects);
        organizations.add(RITCenter);
        organizations.add(Wrike);
        return new OrganizationSection(organizations);
    }

    public static OrganizationSection getOrganizationsStudies() {
        Organization Coursera = new Organization(
                "Coursera",
                "https://www.coursera.org/course/progfun",
                List.of(new Organization.Period(
                        "",
                        "'Functional Programming Principles in Scala' by Martin Odersky",
                        DateUtil.of(2013, 3),
                        DateUtil.of(2013, 5))));
        Organization Luxoft = new Organization(
                "Luxoft",
                "https://www.luxoft-training.ru/training/catalog/course.html?ID=22366",
                List.of(new Organization.Period(
                        "",
                        "Курс 'Объектно-ориентированный анализ ИС. Концептуальное моделирование на UML.'",
                        DateUtil.of(2011, 3),
                        DateUtil.of(2011, 4))));
        Organization SiemensAG = new Organization(
                "Siemens AG",
                "https://www.siemens.ru/",
                List.of(new Organization.Period(
                        "",
                        "3 месяца обучения мобильным IN сетям (Берлин)",
                        DateUtil.of(2005, 1),
                        DateUtil.of(2005, 4))));
        List<Organization> organizations = new ArrayList<>();
        organizations.add(Coursera);
        organizations.add(Luxoft);
        organizations.add(SiemensAG);
        return new OrganizationSection(organizations);
    }
}
