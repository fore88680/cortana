/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.SuperPersonFinal.dao;

import com.sg.SuperPersonFinal.model.Sighting;
import java.util.List;

/**
 *
 * @author fore8
 */
public interface SightingDao {
    public void addSighting(Sighting sighting);
    public void deleteSightingById(int sightingId);
    public void updateSighting(Sighting sighting);
    public Sighting getSightingById(int sightingId);
    public List<Sighting> getAllSightings();
    public List<Sighting> getAllSightingsBySuperPersonId(int superPersonId);
   public List<Sighting> getSightingsByLocationId(int locationId);
   public List<Sighting> getSightingsDescendingDate();
   public List<Sighting> getLastTenSightings();
    
}
