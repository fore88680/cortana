/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.SuperPersonFinal.dao;

import com.sg.SuperPersonFinal.model.Organization;
import com.sg.SuperPersonFinal.model.SuperPerson;
import java.util.List;

/**
 *
 * @author fore8
 */
public interface SuperPersonDao {

    public void addSuperPerson(SuperPerson superPerson);

    public void deleteSuperPerson(int superPersonId);

    public void updateSuperPersonById(SuperPerson superPerson);

    public SuperPerson getSuperPersonById(int superPersonId);

    public List<SuperPerson> getAllSuperPersons();

    public List<SuperPerson> getSuperPersonBySighting(int sightingId);

    public List<SuperPerson> getSuperPersonsByOrganizationId(int organizationId);

    public List<SuperPerson> getSuperPersonsByLocationId(int locationId);
    
    public List<SuperPerson> findSuperPersonForOrganization(Organization organization);
}
