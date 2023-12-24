package com.ivannikov.webapp.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ListSection extends Section {
    private List<String> listSections;

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
}
