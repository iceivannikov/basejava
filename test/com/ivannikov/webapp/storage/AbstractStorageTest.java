package com.ivannikov.webapp.storage;

import com.ivannikov.webapp.Config;
import com.ivannikov.webapp.ResumeTestData;
import com.ivannikov.webapp.exception.ExistStorageException;
import com.ivannikov.webapp.exception.NotExistStorageException;
import com.ivannikov.webapp.model.ContactType;
import com.ivannikov.webapp.model.Resume;
import com.ivannikov.webapp.model.SectionType;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.assertEquals;

public abstract class AbstractStorageTest {
    protected static final String STORAGE_DIR = Config.getInstance().getStorageDir().toString();

    protected final Storage storage;

    private final static String UUID_1 = UUID.randomUUID().toString();
    private final static String UUID_2 = UUID.randomUUID().toString();
    private final static String UUID_3 = UUID.randomUUID().toString();
    private final static String UUID_4 = UUID.randomUUID().toString();

    private final static String NAME_1 = "name1";
    private final static String NAME_2 = "name2";
    private final static String NAME_3 = "name3";
    private final static String NAME_4 = "name4";

    private final static Resume RESUME_1 = new Resume(UUID_1, NAME_1);
    private final static Resume RESUME_2 = new Resume(UUID_2, NAME_2);
    private final static Resume RESUME_3 = new Resume(UUID_3, NAME_3);
    private final static Resume RESUME_4 = new Resume(UUID_4, NAME_4);

    static {
        RESUME_1.addContact(ContactType.TELEPHONE, "+7(921) 855-0482");
        RESUME_1.addContact(ContactType.SKYPE, "skype:grigory.kislin");
        RESUME_1.addSection(SectionType.OBJECTIVE, ResumeTestData.getPersonalQualities());
        RESUME_1.addSection(SectionType.PERSONAL, ResumeTestData.getPosition());
        RESUME_1.addSection(SectionType.ACHIEVEMENT, ResumeTestData.getAchievementsList());
        RESUME_1.addSection(SectionType.QUALIFICATIONS, ResumeTestData.getQualificationList());
        RESUME_1.addSection(SectionType.EXPERIENCE, ResumeTestData.getOrganizationsJob());
        RESUME_1.addSection(SectionType.EDUCATION, ResumeTestData.getOrganizationsStudies());

        RESUME_2.addContact(ContactType.TELEPHONE, "+7(921) 855-0482");
        RESUME_2.addContact(ContactType.SKYPE, "skype:grigory.kislin");
        RESUME_2.addSection(SectionType.OBJECTIVE, ResumeTestData.getPersonalQualities());
        RESUME_2.addSection(SectionType.PERSONAL, ResumeTestData.getPosition());
        RESUME_2.addSection(SectionType.ACHIEVEMENT, ResumeTestData.getAchievementsList());
        RESUME_2.addSection(SectionType.QUALIFICATIONS, ResumeTestData.getQualificationList());
        RESUME_2.addSection(SectionType.EXPERIENCE, ResumeTestData.getOrganizationsJob());
        RESUME_2.addSection(SectionType.EDUCATION, ResumeTestData.getOrganizationsStudies());

        RESUME_3.addContact(ContactType.TELEPHONE, "+7(921) 855-0482");
        RESUME_3.addContact(ContactType.SKYPE, "skype:grigory.kislin");
        RESUME_3.addSection(SectionType.OBJECTIVE, ResumeTestData.getPersonalQualities());
        RESUME_3.addSection(SectionType.PERSONAL, ResumeTestData.getPosition());
        RESUME_3.addSection(SectionType.ACHIEVEMENT, ResumeTestData.getAchievementsList());
        RESUME_3.addSection(SectionType.QUALIFICATIONS, ResumeTestData.getQualificationList());
        RESUME_3.addSection(SectionType.EXPERIENCE, ResumeTestData.getOrganizationsJob());
        RESUME_3.addSection(SectionType.EDUCATION, ResumeTestData.getOrganizationsStudies());

        RESUME_4.addContact(ContactType.TELEPHONE, "+7(921) 855-0482");
        RESUME_4.addContact(ContactType.SKYPE, "skype:grigory.kislin");
    }

    protected AbstractStorageTest(Storage storage) {
        this.storage = storage;
    }

    @Before
    public void init() {
        storage.clear();
        storage.save(RESUME_1);
        storage.save(RESUME_2);
        storage.save(RESUME_3);
    }

    @Test
    public void clear() {
        storage.clear();
        assertSize(0);
    }

    @Test(expected = ExistStorageException.class)
    public void saveExist() {
        storage.save(RESUME_1);
    }

    @Test
    public void save() {
        Resume resume = new Resume(UUID_4, NAME_4);
        storage.save(resume);
        assertSize(4);
        assertGet(resume);
    }

    @Test
    public void update() {
        Resume expected = new Resume(UUID_1, "new name");
        RESUME_1.addContact(ContactType.SKYPE, "new skype");
        RESUME_1.addContact(ContactType.TELEPHONE, "+7(999) 999-9999");
        RESUME_1.addContact(ContactType.EMAIL, "new_email@gmail.com");
        storage.update(expected);
        Resume actual = storage.get(UUID_1);
        assertEquals(expected, actual);
    }

    @Test(expected = NotExistStorageException.class)
    public void updateNotExist() {
        storage.update(RESUME_4);
    }

    @Test
    public void get() {
        assertGet(RESUME_1);
        assertGet(RESUME_2);
        assertGet(RESUME_3);
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() {
        storage.get(UUID_4);
    }

    @Test(expected = NotExistStorageException.class)
    public void delete() {
        storage.delete(UUID_2);
        assertSize(2);
        storage.get(UUID_2);
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteNotExist() {
        storage.delete(UUID_4);
    }

    @Test
    public void getAllSorted() {
        List<Resume> actual = storage.getAllSorted();
        assertEquals(3, storage.size());
        List<Resume> expected = Arrays.asList(RESUME_1, RESUME_2, RESUME_3);
        expected.sort(Comparator.comparing(Resume::getFullName).thenComparing(Resume::getUuid));
        assertEquals(expected, actual);
    }

    @Test
    public void size() {
        assertSize(3);
    }

    private void assertGet(Resume resume) {
        assertEquals(resume, storage.get(resume.getUuid()));
    }

    private void assertSize(int size) {
        assertEquals(size, storage.size());
    }
}