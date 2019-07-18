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
public class LocationDaoImpl implements LocationDao {

    @Autowired
    JdbcTemplate jdbc;

    /////////////////////MAIN SQL/////////////////////////
    private static final String SQL_INSERT_LOCATION
            = "insert into Location (LocationName, Description, State, City, Street, Zip, Latitude, Longitude) values (?, ?, ?, ?, ?, ?, ?, ?)";

    private static final String SQL_DELETE_LOCATION
            = "delete from Location where LocationId = ?";

    private static final String SQL_UPDATE_LOCATION
            = "update Location set LocationName = ?, Description = ?, State = ?, City = ?, Street = ?, Zip = ?, Latitude = ?, Longitude = ? where LocationId = ?";

    private static final String SQL_SELECT_LOCATION
            = "select * from Location where LocationId = ?";

    private static final String SQL_SELECT_ALL_LOCATIONS
            = "select * from Location";

    //////////////////////SQL HELPERS///////////////////////
    public static final String SQL_GET_SUPERPERSON_FOR_SIGHTING
            = "SELECT * "
            + "FROM SuperPerson "
            + "INNER JOIN SuperPersonSighting ON SuperPersonSighting.SuperPersonId = SuperPerson.SuperPersonId "
            + "INNER JOIN Sighting ON Sighting.SightingId = SuperPersonSighting.SightingId "
            + "WHERE Sighting.SightingId = ?";

    public static final String SQL_SELECT_ALL_SUPERPERSON_LOCATIONS
            = "SELECT LocationName "
            + "FROM Location "
            + "INNER JOIN Sighting ON Sighting.LocationId = Location.LocationId "
            + "INNER JOIN SuperPersonSighting ON SuperPersonSighting.SightingId = Sighting.SightingId "
            + "INNER JOIN SuperPerson ON SuperPerson.SuperPersonId = SuperPersonSighting.SuperPersonId "
            + "WHERE SuperPerson.Name = ?";

    public static final String SQL_GET_LOCATION_FOR_SIGHTING
            = "SELECT * "
            + "FROM Location "
            + "INNER JOIN Sighting ON Sighting.LocationId = Location.LocationId "
            + "WHERE Sighting.SightingId = ?";

    public static final String SQL_GET_LOCATION_FOR_ORGANIZATION
            = "SELECT * "
            + "FROM Location "
            + "INNER JOIN Organization ON Organization.LocationId = Location.LocationId "
            + "WHERE Organization.OrganizationId = ?";

    //////////////////////MAINS///////////////////////////
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void addLocation(Location location) {

        jdbc.update(SQL_INSERT_LOCATION,
                location.getLocationName(),
                location.getDescription(),
                location.getCity(),
                location.getState(),
                location.getStreet(),
                location.getZip(),
                location.getLatitude(),
                location.getLongitude());

        int locationId = jdbc.queryForObject("select LAST_INSERT_ID()",
                Integer.class);
        location.setLocationId(locationId);

    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void deleteLocation(int locationId) {
        jdbc.update(SQL_REMOVE_LOCATION_FROM_ORGANIZATION, locationId);
        jdbc.update(SQL_REMOVE_LOCATION_FROM_SIGHTING, locationId);
        jdbc.update(SQL_DELETE_LOCATION, locationId);

    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void updateLocation(Location location) {

        jdbc.update(SQL_UPDATE_LOCATION,
                location.getLocationName(),
                location.getDescription(),
                location.getCity(),
                location.getState(),
                location.getStreet(),
                location.getZip(),
                location.getLatitude(),
                location.getLongitude());

    }

    @Override
    public Location getLocationById(int locationId) {

        try {
            return jdbc.queryForObject(SQL_SELECT_LOCATION,
                    new LocationMapper(),
                    locationId);
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }

    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public List<Location> getAllLocations() {
        try {
            return jdbc.query(SQL_SELECT_ALL_LOCATIONS,
                    new LocationMapper());
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

    ////////////////////ALL MAPPERS//////////////////////
    private static final class SuperPersonMapper implements RowMapper<SuperPerson> {

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

    /////////////////HELPERS////////////////////////
    private List<Sighting> getSightingForLocation(Location location) {
        return jdbc.query(SQL_GET_SIGHTING_FOR_LOCATION,
                new SightingMapper(),
                location.getLocationId());

    }

    //////////////////////MISC SQL///////////////////////////
    public static final String SQL_INSERT_SUPERPERSON_ORGANIZATIONS
            = "insert into SuperPersonOrganization(SuperPersonId, OrganizationId) values (?, ?)";

    public static final String SQL_INSERT_SUPERPERSON_SIGHTING
            = "insert into SuperPersonSighting(SuperPersonId, SightingId) values (?, ?)";

    public static final String SQL_DELETE_SUPERPERSON_ORGANIZATION
            = "delete from SuperPersonOrganization where SuperPersonId = ?";

    public static final String SQL_DELETE_ORGANIZATION_SUPERPERSONS
            = "delete from SuperPersonOrganization where OrganizationId = ?";

    public static final String SQL_REMOVE_LOCATION_FROM_ORGANIZATION
            = "update Organization set LocationId = NULL where LocationId = ?";

    public static final String SQL_REMOVE_LOCATION_FROM_SIGHTING
            = "update Sighting set LocationId = NULL where LocationId = ?";

    public static final String SQL_DELETE_SUPERPERSON_SIGHTING
            = "delete from SuperPersonSighting where SuperPersonId = ?";

    public static final String SQL_DELETE_SIGHTING_SUPERPERSON
            = "delete from SuperPersonSighting where SightingId = ?";

    public static final String SQL_GET_SIGHTING_FOR_LOCATION
            = "select * from Sighting where LocationId = ?";

}
