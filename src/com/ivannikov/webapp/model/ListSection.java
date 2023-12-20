package com.ivannikov.webapp.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ListSection extends Section {
    private List<String> listSections;

    public ListSection(List<String> listSections) {
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

        return Objects.equals(listSections, that.listSections);
    }

    @Override
    public int hashCode() {
        return listSections != null ? listSections.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "ListSection{" +
                "listSections=" + listSections +
                '}';
    }

    @Override
    public void print() {
        for (String section : listSections) {
            System.out.println(section);
        }
    }
}
