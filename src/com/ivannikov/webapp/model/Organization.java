package com.ivannikov.webapp.model;

import com.ivannikov.webapp.util.DateUtil;
import com.ivannikov.webapp.util.LocalDateAdapter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static com.ivannikov.webapp.util.DateUtil.NOW;

@XmlAccessorType(XmlAccessType.FIELD)
public class Organization implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    public static final Organization EMPTY = new Organization("", "", Period.EMPTY);
    private String name;
    private String website;
    private List<Period> periods = new ArrayList<>();

    public Organization() {
    }
    public Organization(String name, String website, Period... periods) {
        this(name, website, Arrays.asList(periods));
    }
    public Organization(String name, String website, List<Period> periods) {
        this.name = name;
        this.website = website;
        this.periods = periods;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public List<Period> getPeriods() {
        return new ArrayList<>(periods);
    }

    public void setPeriods(List<Period> periods) {
        this.periods = periods;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Organization that = (Organization) o;

        if (!Objects.equals(name, that.name)) return false;
        if (!Objects.equals(website, that.website)) return false;
        return Objects.equals(periods, that.periods);
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (website != null ? website.hashCode() : 0);
        result = 31 * result + (periods != null ? periods.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Organization{" +
                "name='" + name + '\'' +
                ", website='" + website + '\'' +
                ", periods=" + periods +
                '}';
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    public static class Period implements Serializable {
        @Serial
        private static final long serialVersionUID = 1L;
        public static final Period EMPTY = new Period();
        private String name;
        private String description;
        @XmlJavaTypeAdapter(LocalDateAdapter.class)
        private LocalDate startDate;
        @XmlJavaTypeAdapter(LocalDateAdapter.class)
        private LocalDate endDate;

        public Period() {
        }

        public Period(String name, String description, int startYear, int startMonth) {
            this(name, description, DateUtil.of(startYear, startMonth), NOW);
        }

        public Period(String name, String description, int startYear, int startMonth, int endYear, int endMonth) {
            this(name, description, DateUtil.of(startYear, startMonth), DateUtil.of(endYear, endMonth));
        }

        public Period(String name, String description, LocalDate startDate, LocalDate endDate) {
            Objects.requireNonNull(name, "name must not be null");
            Objects.requireNonNull(startDate, "startDate must not be null");
            Objects.requireNonNull(endDate, "endDate must not be null");
            this.name = name;
            this.description = description;
            this.startDate = startDate;
            this.endDate = endDate;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public LocalDate getStartDate() {
            return startDate;
        }

        public void setStartDate(LocalDate startDate) {
            this.startDate = startDate;
        }

        public LocalDate getEndDate() {
            return endDate;
        }

        public void setEndDate(LocalDate endDate) {
            this.endDate = endDate;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Period period = (Period) o;

            if (!name.equals(period.name)) return false;
            if (!Objects.equals(description, period.description)) return false;
            if (!startDate.equals(period.startDate)) return false;
            return endDate.equals(period.endDate);
        }

        @Override
        public int hashCode() {
            int result = name.hashCode();
            result = 31 * result + (description != null ? description.hashCode() : 0);
            result = 31 * result + startDate.hashCode();
            result = 31 * result + endDate.hashCode();
            return result;
        }

        @Override
        public String toString() {
            return "Period{" +
                    "name='" + name + '\'' +
                    ", description='" + description + '\'' +
                    ", startDate=" + startDate +
                    ", endDate=" + endDate +
                    '}';
        }
    }
}
