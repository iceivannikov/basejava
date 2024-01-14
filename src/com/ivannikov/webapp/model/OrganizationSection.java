package com.ivannikov.webapp.model;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.Serial;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class OrganizationSection extends Section {
    @Serial
    private static final long serialVersionUID = 1L;
    private List<Organization> organizations;

    public OrganizationSection() {
    }

    public OrganizationSection(Organization... organizations) {
        this(Arrays.asList(organizations));
    }

    public OrganizationSection(List<Organization> organizations) {
        Objects.requireNonNull(organizations, "organizations must not be null");
        this.organizations = organizations;
    }

    public List<Organization> getOrganizations() {
        return new ArrayList<>(organizations);
    }

    public void setOrganizations(List<Organization> organizations) {
        this.organizations = organizations;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrganizationSection that = (OrganizationSection) o;

        return organizations.equals(that.organizations);
    }

    @Override
    public int hashCode() {
        return organizations.hashCode();
    }

    @Override
    public String toString() {
        return organizations.toString();
    }

    @Override
    public void serialize(DataOutputStream dos) throws IOException {
        dos.writeInt(organizations.size());
        for (Organization organization : organizations) {
            dos.writeUTF(organization.getName());
            dos.writeUTF(organization.getWebsite());
            dos.writeInt(organization.getPeriods().size());
            List<Organization.Period> periods = organization.getPeriods();
            for (Organization.Period period : periods) {
                dos.writeUTF(period.getName());
                dos.writeUTF(period.getDescription());
                dos.writeUTF(period.getStartDate().toString());
                dos.writeUTF(period.getEndDate().toString());
            }
        }
    }

    @Override
    public OrganizationSection deserialize(DataInputStream dis) throws IOException {
        int sizeOrganizationSection = dis.readInt();
        organizations = new ArrayList<>();
        List<Organization.Period> periods = null;
        Organization.Period period;
        for (int i = 0; i < sizeOrganizationSection; i++) {
            String name = dis.readUTF();
            String website = dis.readUTF();
            int sizePeriod = dis.readInt();
            for (int j = 0; j < sizePeriod; j++) {
                String periodName = dis.readUTF();
                String description = dis.readUTF();
                String startDate = dis.readUTF();
                String endDate = dis.readUTF();
                LocalDate start = LocalDate.parse(startDate);
                LocalDate end = LocalDate.parse(endDate);
                period = new Organization.Period(periodName, description, start, end);
                periods = new ArrayList<>();
                periods.add(period);
            }
            Organization organization = new Organization(name, website, periods);
            organizations.add(organization);
        }
        return new OrganizationSection(organizations);
    }
}
