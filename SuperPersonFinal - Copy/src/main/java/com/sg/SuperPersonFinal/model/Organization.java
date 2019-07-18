/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.SuperPersonFinal.model;

import java.util.List;
import java.util.Objects;

/**
 *
 * @author fore8
 */
public class Organization {
    
    private int organizationId;
    
    private String orgName;
    
    private String description;
    
    private String contactInfo;
    
    private int locationId;
    
    private Location location;
    
    private List<SuperPerson> superPersonOrganization;

    public int getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(int organizationId) {
        this.organizationId = organizationId;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(String contactInfo) {
        this.contactInfo = contactInfo;
    }

    public int getLocationId() {
        return locationId;
    }

    public void setLocationId(int locationId) {
        this.locationId = locationId;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public List<SuperPerson> getSuperPersonOrganization() {
        return superPersonOrganization;
    }

    public void setSuperPersonOrganization(List<SuperPerson> superPersonOrganization) {
        this.superPersonOrganization = superPersonOrganization;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 41 * hash + this.organizationId;
        hash = 41 * hash + Objects.hashCode(this.orgName);
        hash = 41 * hash + Objects.hashCode(this.description);
        hash = 41 * hash + Objects.hashCode(this.contactInfo);
        hash = 41 * hash + this.locationId;
        hash = 41 * hash + Objects.hashCode(this.location);
        hash = 41 * hash + Objects.hashCode(this.superPersonOrganization);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Organization other = (Organization) obj;
        if (this.organizationId != other.organizationId) {
            return false;
        }
        if (this.locationId != other.locationId) {
            return false;
        }
        if (!Objects.equals(this.orgName, other.orgName)) {
            return false;
        }
        if (!Objects.equals(this.description, other.description)) {
            return false;
        }
        if (!Objects.equals(this.contactInfo, other.contactInfo)) {
            return false;
        }
        if (!Objects.equals(this.location, other.location)) {
            return false;
        }
        if (!Objects.equals(this.superPersonOrganization, other.superPersonOrganization)) {
            return false;
        }
        return true;
    }
    
    
    
    
}
