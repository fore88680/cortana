/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.SuperPersonFinal.dao;

import com.sg.SuperPersonFinal.model.Location;
import com.sg.SuperPersonFinal.model.Organization;
import com.sg.SuperPersonFinal.model.Sighting;
import com.sg.SuperPersonFinal.model.SuperPerson;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author fore8
 */
@Repository 
public class SightingDaoImpl implements SightingDao {
    
    @Autowired
    JdbcTemplate jdbc;
    
    
    //////////////////////////////////
    
    public static final String SQL_INSERT_SIGHTING
            = "insert into Sighting (LocationId, Date) values (?, ?)";

    public static final String SQL_DELETE_SIGHTING
            = "delete from Sighting where SightingId = ?";

    public static final String SQL_UPDATE_SIGHTING
            = "update Sighting set LocationId = ?, Date = ? where SightingId = ?";

    public static final String SQL_SELECT_SIGHTING
            = "SELECT * "
            + "FROM Location  "
            + "INNER JOIN Sighting ON Location.LocationId = Sighting.LocationId "
            + "WHERE SightingId = ?";

    public static final String SQL_SELECT_ALL_SIGHTINGS
            = "SELECT * "
            + "FROM Location  "
            + "INNER JOIN Sighting ON Location.LocationId = Sighting.LocationId ";

    public static final String SQL_REMOVE_LOCATION_FROM_SIGHTING
            = "update Sighting set LocationId = NULL where LocationId = ?";
    
    private static final String SQL_SELECT_SIGHTING_BY_LOCATIONID
            = "select * from Sighting where LocationId = ?";
    
    
    ///////////////////////////////////

      public static final String SQL_GET_SIGHTING_FOR_LOCATION
            = "select * from Sighting where LocationId = ?";

    public static final String SQL_GET_SIGHTING_FOR_SUPERPERSONS
            = "select * from Sighting where SuperPersonId = ?";
    
    public static final String SQL_GET_LAST_TEN_SIGHTINGS
            ="SELECT * "
            + "FROM Sighting "
            + "INNER JOIN Location on Location.LocationId = Sighting.LocationId "
            + "ORDER BY Date DESC "
            + "LIMIT 10";
    
     private static final String SQL_SELECT_SIGHTING_DATES
            = "Select * from Sighting ORDER BY Date DESC Limit 10";
    
        public static final String SQL_GET_LOACTION_FOR_SIGHTING
            = "SELECT * "
            + "FROM Location "
            + "INNER JOIN Sighting ON Sighting.LocationId = Location.LocationId "
            + "WHERE Sighting.SightingId = ?";
        
         private static final String SQL_SELECT_LOCATION_BY_SIGHTINGID
            = "SELECT * "
            + "From Location "
            + "JOIN Sighting  on Location.LocationId = Sighting.LocationId WHERE Sighting.SightingId= ?";
        
         public static final String SQL_GET_SUPERPERSONS_FOR_SIGHTING
            = "SELECT * "
            + "FROM SuperPerson "
            + "INNER JOIN SuperPersonSighting ON SuperPersonSighting.SuperPersonId = SuperPerson.SuperPersonId "
            + "INNER JOIN Sighting ON Sighting.SightingId = SuperPersonSighting.SightingId "
            + "WHERE Sighting.SightingId = ?";
    
         
          public static final String SQL_GET_LAST_TEN_ENTRIES
            = "SELECT * "
            + "FROM Sighting "
            + "INNER JOIN Location ON Location.LocationId = Sighting.LocationId "
            + "ORDER BY Date DESC "
            + "LIMIT 10";
          
          
          ///////////////////////////////////////////
    
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void addSighting(Sighting sighting) {
            
       
        jdbc.update(SQL_INSERT_SIGHTING,
                sighting.getLocation().getLocationId(),
                sighting.getDate().toString());
               

        sighting.setSightingId(jdbc.queryForObject("select LAST_INSERT_ID()",
                Integer.class));

        sighting.getSuperPersonSighting();
        insertSuperPersonSighting(sighting);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void deleteSightingById(int sightingId) {

        jdbc.update(SQL_DELETE_SIGHTING_SUPERPERSON, sightingId);
        jdbc.update(SQL_DELETE_SIGHTING, sightingId);

    }

    @Override
    public void updateSighting(Sighting sighting) {
            
        jdbc.update(SQL_UPDATE_SIGHTING,
                sighting.getLocation().getLocationId(),
                sighting.getDate().toString(),
    
                sighting.getSightingId());
        sighting.getSuperPersonSighting();

        jdbc.update(SQL_DELETE_SUPERPERSON_SIGHTING,
                sighting.getSightingId());
        insertSuperPersonSighting(sighting);

    }

    @Override
    public Sighting getSightingById(int sightingId) {

             try {
            Sighting sighting = jdbc.queryForObject(SQL_SELECT_SIGHTING,
                    new SightingMapper(),
                    sightingId);
            sighting.setSuperPersonSighting(getSuperPersonsForSighting(sighting));
            sighting.setLocation(getLocationForSighting(sighting));
            return sighting;
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }

    }

    @Override
    public List<Sighting> getAllSightings() {

        try {
            return jdbc.query(SQL_SELECT_ALL_SIGHTINGS,
                    new SightingMapper());
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
        
    }

    @Override
    public List<Sighting> getAllSightingsBySuperPersonId(int superPersonId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Sighting> getSightingsByLocationId(int locationId) {
        List<Sighting> sightingList = jdbc.query(SQL_SELECT_SIGHTING_BY_LOCATIONID,
                new SightingMapper(),
                locationId);
        return associateLocationWithSighting(sightingList);
    }

    @Override
    public List<Sighting> getSightingsDescendingDate() {
            
        return jdbc.query(SQL_SELECT_SIGHTING_DATES,
                new SightingMapper());


    }

    @Override
    public List<Sighting> getLastTenSightings() {
   
        try {
            return jdbc.query(SQL_GET_LAST_TEN_ENTRIES,
                    new SightingMapper());
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }
    
    ////////////////////////////////////////////////
    
    
    private void insertSuperPersonSighting(Sighting sighting) {
        final int sightingId = sighting.getSightingId();
        final List<SuperPerson> superPersonList = sighting.getSuperPersonSighting();

        for (SuperPerson currentSuperPerson : superPersonList) {
            jdbc.update(SQL_INSERT_SUPERPERSON_SIGHTING,
                    currentSuperPerson.getSuperPersonId(),
                    sightingId);
        }
    }
    
    
    private Location getLocationForSighting(Sighting sight) {
        return jdbc.queryForObject(SQL_GET_LOACTION_FOR_SIGHTING,
                new LocationMapper(),
                sight.getSightingId());
    }
    
    
    private List<SuperPerson> getSuperPersonsForSighting(Sighting sighting) {
        List<SuperPerson> superPersonList = jdbc.query(SQL_GET_SUPERPERSONS_FOR_SIGHTING,
                new SuperPersonMapper(),
                sighting.getSightingId());
       
        return superPersonList;
    }
    
     private List<Sighting> associateLocationWithSighting(List<Sighting> sightingList) {
        for (Sighting currentSighting : sightingList) {
            currentSighting.setLocation(findLocationForSighting(currentSighting));
        }
        return sightingList;
    }
     
      private Location findLocationForSighting(Sighting sighting) {
        return jdbc.queryForObject(SQL_SELECT_LOCATION_BY_SIGHTINGID,
                new LocationMapper(),
                sighting.getSightingId());
    }
    
    
   
    
    //////////////////////////////////////////////
    
    
    static final class SuperPersonMapper implements RowMapper<SuperPerson> {

        @Override
        public SuperPerson mapRow(ResultSet rs, int i) throws SQLException {
            SuperPerson sp = new SuperPerson();
            sp.setSuperPersonId(rs.getInt("SuperPersonId"));
            sp.setName(rs.getString("Name"));
            sp.setDescription(rs.getString("Description"));
            sp.setSuperPower("SuperPower");
            return sp;
        }
    }

    private static final class SightingMapper implements RowMapper<Sighting> {

        @Override
        public Sighting mapRow(ResultSet rs, int i) throws SQLException {
            Location location = new Location();
            location.setLocationId(rs.getInt("LocationId"));
            location.setLocationName(rs.getString("LocationName"));
            location.setDescription(rs.getString("Description"));
            location.setCity(rs.getString("City"));
            location.setState(rs.getString("State"));
            location.setStreet(rs.getString("Street"));
            location.setZip(rs.getString("Zip"));
            location.setLatitude(rs.getBigDecimal("Latitude"));
            location.setLongitude(rs.getBigDecimal("Longitude"));

            Sighting sighting = new Sighting();
            sighting.setDate(rs.getDate("Date").toLocalDate());
            sighting.setSightingId(rs.getInt("SightingId"));
            sighting.setLocation(location);
            return sighting;
        }
    }

    private static final class LocationMapper implements RowMapper<Location> {

        @Override
        public Location mapRow(ResultSet rs, int i) throws SQLException {

            Location location = new Location();
            location.setLocationId(rs.getInt("LocationId"));
            location.setLocationName(rs.getString("LocationName"));
            location.setDescription(rs.getString("Description"));
            location.setCity(rs.getString("City"));
            location.setState(rs.getString("State"));
            location.setStreet(rs.getString("Street"));
            location.setZip(rs.getString("Zip"));
            location.setLatitude(rs.getBigDecimal("Latitude"));
            location.setLongitude(rs.getBigDecimal("Longitude"));

            return location;

        }
    }

    private static final class OrganizationMapper implements RowMapper<Organization> {

        @Override
        public Organization mapRow(ResultSet rs, int i) throws SQLException {
            Location location = new Location();
            location.setLocationId(rs.getInt("LocationId"));
            location.setLocationName(rs.getString("LocationName"));
            location.setDescription(rs.getString("Description"));
            location.setCity(rs.getString("City"));
            location.setState(rs.getString("State"));
            location.setStreet(rs.getString("Street"));
            location.setZip(rs.getString("Zip"));
            location.setLatitude(rs.getBigDecimal("Latitude"));
            location.setLongitude(rs.getBigDecimal("Longitude"));

            Organization org = new Organization();
            org.setOrganizationId(rs.getInt("OrganizationId"));
            org.setOrgName(rs.getString("orgName"));
            org.setContactInfo(rs.getString("ContactInfo"));
            org.setDescription("Description");

            org.setLocation(location);

            return org;
        }

    }

    
    
     /////////////////////////////////////////
    
    
     public static final String SQL_INSERT_SUPERPERSON_ORGANIZATIONS
            = "insert into SuperPersonOrganization(SuperPersonId, OrganizationId) values (?, ?)";

    public static final String SQL_INSERT_SUPERPERSON_SIGHTING
            = "insert into SuperPersonSighting(SuperPersonId, SightingId) values (?, ?)";

    public static final String SQL_DELETE_SUPERPERSON_ORGANIZATION
            = "delete from SuperPersonOrganization where SuperPersonId = ?";

    public static final String SQL_DELETE_ORGANIZATION_SUPERPERSONS
            = "delete from SuperPersonOrganization where OrganizationId = ?";


    public static final String SQL_DELETE_SUPERPERSON_SIGHTING
            = "delete from SuperPersonSighting where SuperPersonId = ?";

    public static final String SQL_DELETE_SIGHTING_SUPERPERSON
            = "delete from SuperPersonSighting where SightingId = ?";
    
    
}
