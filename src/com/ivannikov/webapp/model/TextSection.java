package com.ivannikov.webapp.model;

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
}
