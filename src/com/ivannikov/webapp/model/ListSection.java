package com.ivannikov.webapp.model;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.Serial;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class ListSection extends Section {
    @Serial
    private static final long serialVersionUID = 1L;
    private List<String> listSections;

    public ListSection() {
    }

    public ListSection(String... items) {
        this(Arrays.asList(items));
    }

    public ListSection(List<String> listSections) {
        Objects.requireNonNull(listSections, "listSections must not be null");
        this.listSections = listSections;
    }

    public List<String> getListSections() {
        return new ArrayList<>(listSections);
    }

    public void setListSections(List<String> listSections) {
        this.listSections = listSections;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ListSection that = (ListSection) o;

        return listSections.equals(that.listSections);
    }

    @Override
    public int hashCode() {
        return listSections.hashCode();
    }

    @Override
    public String toString() {
        return listSections.toString();
    }

    @Override
    public void serialize(DataOutputStream dos) throws IOException {
        dos.writeInt(listSections.size());
        for (String section : listSections) {
            dos.writeUTF(section);
        }
    }

    @Override
    public ListSection deserialize(DataInputStream dis) throws IOException {
        int size = dis.readInt();
        listSections = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            listSections.add(dis.readUTF());
        }
        return new ListSection(listSections);
    }
}
