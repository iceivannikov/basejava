package com.ivannikov.webapp.model;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.Serial;
import java.util.Objects;

public class TextSection extends Section {
    @Serial
    private static final long serialVersionUID = 1L;
    private String textSection;

    public TextSection() {
    }

    public TextSection(String textSection) {
        Objects.requireNonNull(textSection, "textSection must not be null");
        this.textSection = textSection;
    }

    public String getTextSection() {
        return textSection;
    }

    public void setTextSection(String textSection) {
        this.textSection = textSection;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TextSection that = (TextSection) o;

        return textSection.equals(that.textSection);
    }

    @Override
    public int hashCode() {
        return textSection.hashCode();
    }

    @Override
    public String toString() {
        return textSection;
    }

    @Override
    public void serialize(DataOutputStream dos) throws IOException {
        dos.writeUTF(textSection);
    }

    @Override
    public TextSection deserialize(DataInputStream dis) throws IOException {
        return new TextSection(dis.readUTF());
    }
}
