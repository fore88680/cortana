/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.SuperPersonFinal.controller;

import com.sg.SuperPersonFinal.dao.LocationDao;
import com.sg.SuperPersonFinal.dao.OrganizationDao;
import com.sg.SuperPersonFinal.dao.SightingDao;
import com.sg.SuperPersonFinal.dao.SuperPersonDao;
import com.sg.SuperPersonFinal.model.Location;
import com.sg.SuperPersonFinal.model.Organization;
import com.sg.SuperPersonFinal.model.Sighting;
import com.sg.SuperPersonFinal.model.SuperPerson;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

/**
 *
 * @author fore8
 */
@Controller
public class MainController {

    @Autowired
    LocationDao locationDao;

    @Autowired
    OrganizationDao organizationDao;

    @Autowired
    SuperPersonDao superPersonDao;

    @Autowired
    SightingDao sightingDao;

    @GetMapping("superPersons")
    public String displaySuperPersons(Model model) {
        List<SuperPerson> superPersonList = superPersonDao.getAllSuperPersons();
        model.addAttribute("superPersons", superPersonList);
        return "superPersons";
    }

    @GetMapping("locations")
    public String displaylocations(Model model) {
        List<Location> locationList = locationDao.getAllLocations();
        model.addAttribute("locations", locationList);
        return "locations";
    }

    @GetMapping("organizations")
    public String displayingOrganizations(Model model) {

        List<SuperPerson> superPersonList = superPersonDao.getAllSuperPersons();
        model.addAttribute("superPersonList, superPersonList");
        List<Location> locationList = locationDao.getAllLocations();
        model.addAttribute("locationList", locationList);
        List<Organization> organizationList = organizationDao.getAllOrganizations();
        model.addAttribute("orgainzationList", organizationList);
        return "organizations";
    }

    @GetMapping("sightings")
    public String displaySightings(Model model) {
        List<SuperPerson> superPersonList = superPersonDao.getAllSuperPersons();
        model.addAttribute("superPersons", superPersonList);
        List<Location> locationList = locationDao.getAllLocations();
        model.addAttribute("locations", locationList);
        List<Sighting> sightingList = sightingDao.getAllSightings();
        model.addAttribute("sightings", sightingList);
        return "sightings";
    }

    @PostMapping("addSuperPerson")
    public String addSuperPerson(HttpServletRequest request) {
        String name = request.getParameter("name");
        String description = request.getParameter("description");
        String superPower = request.getParameter("superPower");
        String[] selectedOrganizations = request.getParameterValues("organizationList");
        List<Organization> organizationList = new ArrayList<>();

        return "redirect:/superPersons";
    }

    @PostMapping("addSighting")
    public String addSighting(Sighting sighting, HttpServletRequest request) {

        String locationId = request.getParameter("locationId");
        String[] superPersonIds = request.getParameterValues("superPersonId");

        sighting.setLocation(locationDao.getLocationById(Integer.parseInt(locationId)));

        List<SuperPerson> superPerson = new ArrayList<>();
        for (String superPersonId : superPersonIds) {
            superPerson.add(superPersonDao.getSuperPersonById(Integer.parseInt(superPersonId)));
        }

        sighting.setSuperPersons(superPerson);
        sightingDao.addSighting(sighting);

        return "redirect:/sightings";

    }

    @PostMapping("addLocation")
    public String addLocation(Location location, HttpServletRequest request) {
        
 
    String locationName = request.getParameter("locationName");
    String description  = request.getParameter("description");
    String state  = request.getParameter("state");
    String city  = request.getParameter("city");
    String street  = request.getParameter("street");
    String zip  = request.getParameter("zip");
    BigDecimal latitude  = new BigDecimal (request.getParameter("latitude"));
    BigDecimal longitude  = new BigDecimal (request.getParameter("longitude"));
    
    locationDao.addLocation(location);
    
    return "redirect:/locations";

    }
    
    @PostMapping("addOrganization")
    public String addOrganization(Organization organization, HttpServletRequest request) {
   
        String orgName  = request.getParameter("orgName");
        String description  = request.getParameter("description");  
        String contactInfo =  request.getParameter("contactInfo");
        String locationId  = request.getParameter("locationId");
        
        organization.setLocation(locationDao.getLocationById(Integer.parseInt(locationId)));
        
        organizationDao.addOrganization(organization);
        return "redirect:/organizations";
        
    
    }
    
    
    @GetMapping("deleteLocation")
    public String deleteLocation(Integer locationId) {
        
        locationDao.deleteLocation(locationId);
        return "redirect:/locations";
        
    }
    
    @GetMapping("editSighting")
    public String editSighting(Integer sightingId, Model model) {
        Sighting sighting = sightingDao.getSightingById(sightingId);
        List<SuperPerson> superPersonList = superPersonDao.getAllSuperPersons();
        List<Location> locationList = locationDao.getAllLocations();
        
        model.addAttribute("sightings", sighting);
        model.addAttribute("superPeople", superPersonList);
        model.addAttribute("locations", locationList);
        return"editSighting";
        
    }
    
    
    
    
    
    
}
