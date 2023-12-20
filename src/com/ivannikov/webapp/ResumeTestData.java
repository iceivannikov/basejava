package com.ivannikov.webapp;

import com.ivannikov.webapp.model.*;

import java.util.*;

public class ResumeTestData {
    public static void main(String[] args) {
        Map<ContactType, String> contacts = getContacts();

        Section achievementsSection = new ListSection(getAchievementsList());
        Section qualificationSection = new ListSection(getQualificationList());

        Map<SectionType, Section> sections = new HashMap<>();
        sections.put(SectionType.OBJECTIVE, getPersonalQualities());
        sections.put(SectionType.PERSONAL, getPosition());
        sections.put(SectionType.ACHIEVEMENT, achievementsSection);
        sections.put(SectionType.QUALIFICATIONS, qualificationSection);


        Resume resume = new Resume("1", "Григорий Кислин", contacts, sections);
        printResume(resume);
    }

    private static void printResume(Resume resume) {
        String fullName = resume.getFullName();
        Map<ContactType, String> contacts = resume.getContacts();

        System.out.println(fullName);

        for (Map.Entry<ContactType, String> entry : contacts.entrySet()) {
            System.out.println(entry.getKey().getTitle() + ": " + entry.getValue());
        }

        int count = 0;
        Map<SectionType, Section> sections = resume.getSections();
        for (Map.Entry<SectionType, Section> entry : sections.entrySet()) {
            System.out.println(entry.getKey().getTitle());
            Section section = entry.getValue();
            section.print();

//            System.out.println(section);
        }

//        SectionType[] sectionTypes = SectionType.values();
//        for (SectionType type : sectionTypes) {
//            System.out.println(type.getTitle());
//
//        }

    }

    private static TextSection getPosition() {
        return new TextSection("Аналитический склад ума, сильная логика, креативность, инициативность. " +
                "Пурист кода и архитектуры.");
    }

    private static TextSection getPersonalQualities() {
        return new TextSection("Ведущий стажировок и корпоративного обучения по Java Web и Enterprise технологиям");
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

    private static List<String> getAchievementsList() {
        List<String> achievementsList = new ArrayList<>();
        achievementsList.add("Организация команды и успешная реализация Java проектов для сторонних заказчиков: приложения автопарк на стеке Spring Cloud/микросервисы, система мониторинга показателей спортсменов на Spring Boot, участие в проекте МЭШ на Play-2, многомодульный Spring Boot + Vaadin проект для комплексных DIY смет");
        achievementsList.add("С 2013 года: разработка проектов \"Разработка Web приложения\",\"Java Enterprise\", \"Многомодульный maven. Многопоточность. XML (JAXB/StAX). Веб сервисы (JAX-RS/SOAP). Удаленное взаимодействие (JMS/AKKA)\". Организация онлайн стажировок и ведение проектов. Более 3500 выпускников.");
        achievementsList.add("Реализация двухфакторной аутентификации для онлайн платформы управления проектами Wrike. Интеграция с Twilio, DuoSecurity, Google Authenticator, Jira, Zendesk.");
        achievementsList.add("Налаживание процесса разработки и непрерывной интеграции ERP системы River BPM. Интеграция с 1С, Bonita BPM, CMIS, LDAP. Разработка приложения управления окружением на стеке: Scala/Play/Anorm/JQuery. Разработка SSO аутентификации и авторизации различных ERP модулей, интеграция CIFS/SMB java сервера.");
        achievementsList.add("Реализация c нуля Rich Internet Application приложения на стеке технологий JPA, Spring, Spring-MVC, GWT, ExtGWT (GXT), Commet, HTML5, Highstock для алгоритмического трейдинга.");
        achievementsList.add("Создание JavaEE фреймворка для отказоустойчивого взаимодействия слабо-связанных сервисов (SOA-base архитектура, JAX-WS, JMS, AS Glassfish). Сбор статистики сервисов и информации о состоянии через систему мониторинга Nagios. Реализация онлайн клиента для администрирования и мониторинга системы по JMX (Jython/ Django).");
        achievementsList.add("Реализация протоколов по приему платежей всех основных платежных системы России (Cyberplat, Eport, Chronopay, Сбербанк), Белоруcсии(Erip, Osmp) и Никарагуа.");
        return achievementsList;
    }

    private static List<String> getQualificationList() {
        List<String> qualificationList = new ArrayList<>();
        qualificationList.add("JEE AS: GlassFish (v2.1, v3), OC4J, JBoss, Tomcat, Jetty, WebLogic, WSO2");
        qualificationList.add("Version control: Subversion, Git, Mercury, ClearCase, Perforce");
        qualificationList.add("DB: PostgreSQL(наследование, pgplsql, PL/Python), Redis (Jedis), H2, Oracle, MySQL, SQLite, MS SQL, HSQLDB");
        qualificationList.add("Languages: Java, Scala, Python/Jython/PL-Python, JavaScript, Groovy");
        qualificationList.add("XML/XSD/XSLT, SQL, C/C++, Unix shell scripts");
        qualificationList.add("Java Frameworks: Java 8 (Time API, Streams), Guava, Java Executor, MyBatis, Spring (MVC, Security, Data, Clouds, Boot), JPA (Hibernate, EclipseLink), Guice, GWT(SmartGWT, ExtGWT/GXT), Vaadin, Jasperreports, Apache Commons, Eclipse SWT, JUnit, Selenium (htmlelements).");
        qualificationList.add("Python: Django.");
        qualificationList.add("JavaScript: jQuery, ExtJS, Bootstrap.js, underscore.js");
        qualificationList.add("Scala: SBT, Play2, Specs2, Anorm, Spray, Akka");
        qualificationList.add("Технологии: Servlet, JSP/JSTL, JAX-WS, REST, EJB, RMI, JMS, JavaMail, JAXB, StAX, SAX, DOM, XSLT, MDB, JMX, JDBC, JPA, JNDI, JAAS, SOAP, AJAX, Commet, HTML5, ESB, CMIS, BPMN2, LDAP, OAuth1, OAuth2, JWT.");
        qualificationList.add("Инструменты: Maven + plugin development, Gradle, настройка Ngnix");
        qualificationList.add("администрирование Hudson/Jenkins, Ant + custom task, SoapUI, JPublisher, Flyway, Nagios, iReport, OpenCmis, Bonita, pgBouncer");
        qualificationList.add("Отличное знание и опыт применения концепций ООП, SOA, шаблонов проектрирования, архитектурных шаблонов, UML, функционального программирования.");
        qualificationList.add("Родной русский, английский \"upper intermediate\".");
        return qualificationList;
    }
}
