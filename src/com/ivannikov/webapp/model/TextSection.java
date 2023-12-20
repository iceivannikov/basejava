package com.ivannikov.webapp.model;

import java.util.Objects;

public class TextSection extends Section {
    private String textSection;

    public TextSection(String textSection) {
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

        return Objects.equals(textSection, that.textSection);
    }

    @Override
    public int hashCode() {
        return textSection != null ? textSection.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "TextSection{" +
                "textSection='" + textSection + '\'' +
                '}';
    }

    @Override
    public void print() {
        System.out.println(textSection);
    }
}
