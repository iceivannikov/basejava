package com.ivannikov.webapp.storage;

import com.ivannikov.webapp.storage.serialization.FileSystemSaverTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        ArrayStorageTest.class,
        SortedArrayStorageTest.class,
        ListStorageTest.class,
        MapUuidStorageTest.class,
        MapResumeStorageTest.class,
        AbstractFileStorageTest.class,
        AbstractPathStorageTest.class,
        FileSystemSaverTest.class,
        ObjectStreamPathStorageTest.class,
        ObjectStreamFileStorageTest.class
})
public class AllStorageTest {
}
