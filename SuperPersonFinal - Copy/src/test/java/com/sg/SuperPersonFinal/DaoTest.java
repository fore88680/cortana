/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.SuperPersonFinal;

import com.sg.SuperPersonFinal.dao.LocationDao;
import com.sg.SuperPersonFinal.dao.OrganizationDao;
import com.sg.SuperPersonFinal.dao.SightingDao;
import com.sg.SuperPersonFinal.dao.SuperPersonDao;
import com.sg.SuperPersonFinal.model.Location;
import com.sg.SuperPersonFinal.model.Organization;
import com.sg.SuperPersonFinal.model.Sighting;
import com.sg.SuperPersonFinal.model.SuperPerson;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 *
 * @author fore8
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class DaoTest {

    @Autowired
    OrganizationDao organizationDao;

    @Autowired
    LocationDao locationDao;

    @Autowired
    SuperPersonDao superPersonDao;

    @Autowired
    SightingDao sightingDao;

    public DaoTest() {

    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {

        List<Sighting> sighting = sightingDao.getAllSightings();
        sighting.forEach((currentSighting) -> {
            sightingDao.deleteSightingById(currentSighting.getSightingId());
        });

        List<Organization> organization = organizationDao.getAllOrganizations();
        organization.forEach((currentOrganization) -> {
            organizationDao.deleteOrganization(currentOrganization.getOrganizationId());
        });

        List<SuperPerson> superPerson = superPersonDao.getAllSuperPersons();
        superPerson.forEach((currentSuperPerson) -> {
            superPersonDao.deleteSuperPerson(currentSuperPerson.getSuperPersonId());
        });

        List<Location> location = locationDao.getAllLocations();
        location.forEach((currentLocation) -> {
            locationDao.deleteLocation(currentLocation.getLocationId());
        });
    }

    @After
    public void tearDown() {
    }

    @Test
    public void addGetDeleteSuperPerson() {

        SuperPerson superPerson = new SuperPerson();
        superPerson.setName("Barnacle Boi");
        superPerson.setDescription("Top 4 greatest of all time");
        superPerson.setSuperPower("Sulfur Vision");

        superPersonDao.addSuperPerson(superPerson);

        SuperPerson fromDb = superPersonDao.getSuperPersonById(superPerson.getSuperPersonId());
        assertNotEquals(fromDb, superPerson);

        superPersonDao.deleteSuperPerson(superPerson.getSuperPersonId());

        assertNull(superPersonDao.getSuperPersonById(superPerson.getSuperPersonId()));
    }

    @Test
    public void testAddUpdateSuperPerson() {

        SuperPerson superPerson = new SuperPerson();
        superPerson.setName("Mermaid Man");
        superPerson.setDescription("Mermaid Man is a semi-retired superhero who has aged well past his prime.");
        superPerson.setSuperPower("High Speed Swimming");

        superPersonDao.addSuperPerson(superPerson);

        superPerson.setDescription("Sea creature controll");
        superPersonDao.updateSuperPersonById(superPerson);

        SuperPerson fromSuperPersonDao = superPersonDao.getSuperPersonById(superPerson.getSuperPersonId());

        assertNotEquals(fromSuperPersonDao, superPerson);
    }

    @Test
    public void testGetAllSuperPersons() {

        SuperPerson superPerson1 = new SuperPerson();
        superPerson1.setName("Mermaid Man");
        superPerson1.setDescription("Mermaid Man is a semi-retired superhero who has aged well past his prime.");
        superPerson1.setSuperPower("High Speed Swimming");
        superPersonDao.addSuperPerson(superPerson1);

        SuperPerson superPerson2 = new SuperPerson();
        superPerson2.setName("Barnacle Boi");
        superPerson2.setDescription("Top 4 greatest of all time");
        superPerson2.setSuperPower("Sulfur Vision");
        superPersonDao.addSuperPerson(superPerson2);

        List<SuperPerson> superPersonList = superPersonDao.getAllSuperPersons();
        assertEquals(superPersonList.size(), 2);
    }

    //////////////////////////////////////////
    @Test
    public void addGetDeleteSighting() {

        SuperPerson superPerson1 = new SuperPerson();
        superPerson1.setName("Mermaid Man");
        superPerson1.setDescription("Mermaid Man is a semi-retired superhero who has aged well past his prime.");
        superPerson1.setSuperPower("High Speed Swimming");
        superPersonDao.addSuperPerson(superPerson1);
        List<SuperPerson> superPersonList = superPersonDao.getAllSuperPersons();

        Location location1 = new Location();
        location1.setLocationName("Mississippi River");
        location1.setDescription("River");
        location1.setCity("Minneapolis");
        location1.setState("Minnesota");
        location1.setStreet("Colfax");
        location1.setZip("55409");
        location1.setLatitude(new BigDecimal("44.9778"));
        location1.setLongitude(new BigDecimal("93.2650"));
        
        locationDao.addLocation(location1);

        Sighting sighting = new Sighting();
        sighting.setLocation(location1);
        sighting.setDate(LocalDate.parse("2019-06-07", DateTimeFormatter.ISO_DATE));
        sighting.setSuperPersonSighting(superPersonList);
        sightingDao.addSighting(sighting);
        
        sighting.setLocation(location1);
        sightingDao.addSighting(sighting);
        Sighting fromDao = sightingDao.getSightingById(sighting.getSightingId());
        fromDao.setLocation(location1);
        assertEquals(fromDao, sighting);
        sightingDao.deleteSightingById(sighting.getSightingId());
        
      

        assertNull(sightingDao.getSightingById(sighting.getSightingId()));

    }

    @Test
    public void addUpdateSighting() {

        SuperPerson superPerson1 = new SuperPerson();
        superPerson1.setName("Mermaid Man");
        superPerson1.setDescription("Mermaid Man is a semi-retired superhero who has aged well past his prime.");
        superPerson1.setSuperPower("High Speed Swimming");
        superPersonDao.addSuperPerson(superPerson1);
        List<SuperPerson> superPersonList = new ArrayList<>();
        superPersonList.add(superPerson1);

        Location location1 = new Location();
        location1.setLocationName("Mississippi River");
        location1.setDescription("River");
        location1.setCity("Minneapolis");
        location1.setState("Minnesota");
        location1.setStreet("Colfax");
        location1.setZip("55409");
        location1.setLatitude(new BigDecimal("44.9778"));
        location1.setLongitude(new BigDecimal("93.2650"));
        locationDao.addLocation(location1);

        Sighting sighting = new Sighting();
        sighting.setLocation(location1);
        sighting.setDate(LocalDate.parse("2019-06-07", DateTimeFormatter.ISO_DATE));
        sighting.setSuperPersonSighting(superPersonList);
        sightingDao.addSighting(sighting);

        sighting.setDate(LocalDate.parse("2019-06-05", DateTimeFormatter.ISO_DATE));

        sightingDao.updateSighting(sighting);

        Sighting fromDb = sightingDao.getSightingById(sighting.getSightingId());
        assertNotEquals(sighting, fromDb);

    }

    @Test
    public void testGetAllSightings() {

        SuperPerson superPerson1 = new SuperPerson();
        superPerson1.setName("Mermaid Man");
        superPerson1.setDescription("Mermaid Man is a semi-retired superhero who has aged well past his prime.");
        superPerson1.setSuperPower("High Speed Swimming");
        superPersonDao.addSuperPerson(superPerson1);

        SuperPerson superPerson2 = new SuperPerson();
        superPerson2.setName("Barnacle Boi");
        superPerson2.setDescription("Top 4 greatest of all time");
        superPerson2.setSuperPower("Sulfur Vision");
        superPersonDao.addSuperPerson(superPerson2);

        List<SuperPerson> superPersonList = superPersonDao.getAllSuperPersons();

        Location location1 = new Location();
        location1.setLocationName("Mississippi River");
        location1.setDescription("River");
        location1.setCity("Minneapolis");
        location1.setState("Minnesota");
        location1.setStreet("Colfax");
        location1.setZip("55409");
        location1.setLatitude(new BigDecimal("44.9778"));
        location1.setLongitude(new BigDecimal("93.2650"));
        locationDao.addLocation(location1);

        Location location2 = new Location();
        location2.setLocationName("Brians");
        location2.setDescription("Brians house");
        location2.setCity("St. Paul ");
        location2.setState("Minnesota");
        location2.setStreet("Marshall Ave.");
        location2.setZip("55048");
        location2.setLatitude(new BigDecimal("99.7778"));
        location2.setLongitude(new BigDecimal("89.3350"));
        locationDao.addLocation(location2);

        Sighting sighting1 = new Sighting();
        sighting1.setLocation(location1);
        sighting1.setDate(LocalDate.parse("2019-06-07", DateTimeFormatter.ISO_DATE));
        sighting1.setSuperPersonSighting(superPersonList);
        sightingDao.addSighting(sighting1);

        Sighting sighting2 = new Sighting();
        sighting2.setLocation(location1);
        sighting2.setDate(LocalDate.parse("2019-06-02", DateTimeFormatter.ISO_DATE));
        sighting2.setSuperPersonSighting(superPersonList);
        sightingDao.addSighting(sighting2);

        List<Sighting> sightingList = sightingDao.getAllSightings();
        assertEquals(sightingList.size(), 2);

    }

    @Test
    public void addGetDeleteOrganization() {

        SuperPerson superPerson1 = new SuperPerson();
        superPerson1.setName("Mermaid Man");
        superPerson1.setDescription("Mermaid Man is a semi-retired superhero who has aged well past his prime.");
        superPerson1.setSuperPower("High Speed Swimming");
        superPersonDao.addSuperPerson(superPerson1);
        List<SuperPerson> superPersonList = superPersonDao.getAllSuperPersons();

        Location location1 = new Location();
        location1.setLocationName("Mississippi River");
        location1.setDescription("River");
        location1.setCity("Minneapolis");
        location1.setState("Minnesota");
        location1.setStreet("Colfax");
        location1.setZip("55409");
        location1.setLatitude(new BigDecimal("44.9778"));
        location1.setLongitude(new BigDecimal("93.2650"));
        locationDao.addLocation(location1);

        Organization organization = new Organization();
        organization.setOrgName("Good Guys");
        organization.setLocation(location1);
        organization.setContactInfo("123-234-5678, goodguys@gmail.com");
        organization.setDescription("The group of good guys");
        organization.setSuperPersonOrganization(superPersonList);
        organizationDao.addOrganization(organization);

        Organization fromDb = organizationDao.getOrganizationById(organization.getOrganizationId());
        assertEquals(organization, fromDb);
        organizationDao.deleteOrganization(organization.getOrganizationId());

        locationDao.deleteLocation(location1.getLocationId());

        assertNull(organizationDao.getOrganizationById(organization.getOrganizationId()));

    }

    public void addUpdateOrganization() {

        SuperPerson superPerson1 = new SuperPerson();
        superPerson1.setName("Mermaid Man");
        superPerson1.setDescription("Mermaid Man is a semi-retired superhero who has aged well past his prime.");
        superPerson1.setSuperPower("High Speed Swimming");
        superPersonDao.addSuperPerson(superPerson1);

        SuperPerson superPerson2 = new SuperPerson();
        superPerson2.setName("Barnacle Boi");
        superPerson2.setDescription("Top 4 greatest of all time");
        superPerson2.setSuperPower("Sulfur Vision");
        superPersonDao.addSuperPerson(superPerson2);

        List<SuperPerson> superPersonList = superPersonDao.getAllSuperPersons();

        Location location1 = new Location();
        location1.setLocationName("Mississippi River");
        location1.setDescription("River");
        location1.setCity("Minneapolis");
        location1.setState("Minnesota");
        location1.setStreet("Colfax");
        location1.setZip("55409");
        location1.setLatitude(new BigDecimal("44.9778"));
        location1.setLongitude(new BigDecimal("93.2650"));
        locationDao.addLocation(location1);

        Organization organization = new Organization();
        organization.setOrgName("Good Guys");
        organization.setLocation(location1);
        organization.setContactInfo("123-234-5678, goodguys@gmail.com");
        organization.setDescription("The group of good guys");
        organization.setSuperPersonOrganization(superPersonList);
        organizationDao.addOrganization(organization);

        organization.setDescription("The group of good guys run by Mermaid Man");
        organizationDao.updateOrganization(organization);

        Organization fromDb = organizationDao.getOrganizationById(organization.getOrganizationId());
        assertEquals(organization, fromDb);

    }

    @Test
    public void testGetAllOrganizations() {

        SuperPerson superPerson1 = new SuperPerson();
        superPerson1.setName("Mermaid Man");
        superPerson1.setDescription("Mermaid Man is a semi-retired superhero who has aged well past his prime.");
        superPerson1.setSuperPower("High Speed Swimming");
        superPersonDao.addSuperPerson(superPerson1);

        SuperPerson superPerson2 = new SuperPerson();
        superPerson2.setName("Barnacle Boi");
        superPerson2.setDescription("Top 4 greatest of all time");
        superPerson2.setSuperPower("Sulfur Vision");
        superPersonDao.addSuperPerson(superPerson2);

        List<SuperPerson> superPersonList = superPersonDao.getAllSuperPersons();

        Location location1 = new Location();
        location1.setLocationName("Mississippi River");
        location1.setDescription("River");
        location1.setCity("Minneapolis");
        location1.setState("Minnesota");
        location1.setStreet("Colfax");
        location1.setZip("55409");
        location1.setLatitude(new BigDecimal("44.9778"));
        location1.setLongitude(new BigDecimal("93.2650"));
        locationDao.addLocation(location1);

        Location location2 = new Location();
        location2.setLocationName("Brians");
        location2.setDescription("Brians house");
        location2.setCity("St. Paul ");
        location2.setState("Minnesota");
        location2.setStreet("Marshall Ave.");
        location2.setZip("55048");
        location2.setLatitude(new BigDecimal("99.7778"));
        location2.setLongitude(new BigDecimal("89.3350"));
        locationDao.addLocation(location2);

        Organization organization1 = new Organization();
        organization1.setOrgName("Good Guys");
        organization1.setLocation(location1);
        organization1.setContactInfo("123-234-5678, goodguys@gmail.com");
        organization1.setDescription("The group of good guys");
        organization1.setSuperPersonOrganization(superPersonList);
        organizationDao.addOrganization(organization1);

        Organization organization2 = new Organization();
        organization2.setOrgName("Bad Guys");
        organization2.setLocation(location2);
        organization2.setContactInfo("987-654-3210, badguys@gmail.com");
        organization2.setDescription("The group of bad guys");
        organization2.setSuperPersonOrganization(superPersonList);
        organizationDao.addOrganization(organization2);

        List<Organization> organizationList = organizationDao.getAllOrganizations();
        assertEquals(organizationList.size(), 2);

    }

    @Test
    public void addUpdateLocation() {

        Location location2 = new Location();
        location2.setLocationName("Brians");
        location2.setDescription("Brians house");
        location2.setCity("St. Paul ");
        location2.setState("Minnesota");
        location2.setStreet("Marshall Ave.");
        location2.setZip("55048");
        location2.setLatitude(new BigDecimal("99.7778"));
        location2.setLongitude(new BigDecimal("89.3350"));
        locationDao.addLocation(location2);

        location2.setStreet("Moore St");
        locationDao.updateLocation(location2);
       

        Location fromDb = locationDao.getLocationById(location2.getLocationId());
        assertEquals(fromDb, location2);
    }

    @Test
    public void testGetAllLocations() {

        Location location1 = new Location();
        location1.setLocationName("Mississippi River");
        location1.setDescription("River");
        location1.setCity("Minneapolis");
        location1.setState("Minnesota");
        location1.setStreet("Colfax");
        location1.setZip("55409");
        location1.setLatitude(new BigDecimal("44.9778"));
        location1.setLongitude(new BigDecimal("93.2650"));
        locationDao.addLocation(location1);

        Location location2 = new Location();
        location2.setLocationName("Brians");
        location2.setDescription("Brians house");
        location2.setCity("St. Paul ");
        location2.setState("Minnesota");
        location2.setStreet("Marshall Ave.");
        location2.setZip("55048");
        location2.setLatitude(new BigDecimal("99.7778"));
        location2.setLongitude(new BigDecimal("89.3350"));
        locationDao.addLocation(location2);
        
        
        
        List<Location> locationList = locationDao.getAllLocations();
        assertEquals(locationList.size(), 2);
        
        
    }
    
    
    
    
    
    
    
    
    
    

}
