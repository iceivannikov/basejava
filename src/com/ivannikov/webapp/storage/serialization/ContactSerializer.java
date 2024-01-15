package com.ivannikov.webapp.storage.serialization;

import java.io.DataOutputStream;
import java.io.IOException;

public class ContactSerializer implements Serializer<String> {
    @Override
    public void serialize(String str, DataOutputStream dos) throws IOException {
        dos.writeUTF(str);
    }
}
