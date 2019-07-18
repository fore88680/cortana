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
public interface OrganizationDao {
    
    public void  addOrganization(Organization organization);
    public void  deleteOrganization(int organizationId);
    public void  updateOrganization(Organization organization);
    public Organization getOrganizationById(int organizationId);
     public List<Organization> getAllOrganizations();
       public List<Organization> findOrganizationsForSuperPerson(SuperPerson superPerson);
    
}
