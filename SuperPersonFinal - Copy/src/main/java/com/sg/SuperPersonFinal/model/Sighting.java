/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.SuperPersonFinal.model;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author fore8
 */
public class Sighting {
    
    private int sightingId;
    private int locationId;
    private Location location;
    private LocalDate date;
    private List<SuperPerson> superPersons;
    private List<SuperPerson> superPersonSighting;

    public int getSightingId() {
        return sightingId;
    }

    public void setSightingId(int sightingId) {
        this.sightingId = sightingId;
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

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public List<SuperPerson> getSuperPersons() {
        return superPersons;
    }

    public void setSuperPersons(List<SuperPerson> superPersons) {
        this.superPersons = superPersons;
    }

    public List<SuperPerson> getSuperPersonSighting() {
        return superPersonSighting;
    }

    public void setSuperPersonSighting(List<SuperPerson> superPersonSighting) {
        this.superPersonSighting = superPersonSighting;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + this.sightingId;
        hash = 29 * hash + this.locationId;
        hash = 29 * hash + Objects.hashCode(this.location);
        hash = 29 * hash + Objects.hashCode(this.date);
        hash = 29 * hash + Objects.hashCode(this.superPersons);
        hash = 29 * hash + Objects.hashCode(this.superPersonSighting);
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
        final Sighting other = (Sighting) obj;
        if (this.sightingId != other.sightingId) {
            return false;
        }
        if (this.locationId != other.locationId) {
            return false;
        }
        if (!Objects.equals(this.location, other.location)) {
            return false;
        }
        if (!Objects.equals(this.date, other.date)) {
            return false;
        }
        if (!Objects.equals(this.superPersons, other.superPersons)) {
            return false;
        }
        if (!Objects.equals(this.superPersonSighting, other.superPersonSighting)) {
            return false;
        }
        return true;
    }
    
    
    
}
