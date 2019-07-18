/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.SuperPersonFinal.dao;

import static com.sg.SuperPersonFinal.dao.LocationDaoImpl.SQL_DELETE_SUPERPERSON_ORGANIZATION;
import static com.sg.SuperPersonFinal.dao.LocationDaoImpl.SQL_DELETE_SUPERPERSON_SIGHTING;
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
public class SuperPersonDaoImpl implements SuperPersonDao {

    @Autowired
    JdbcTemplate jdbc;

    /////////////////////////////////////
    public static final String SQL_INSERT_SUPERPERSON
            = "insert into SuperPerson (Name, Description,SuperPower) values (?, ?, ?)";

    public static final String SQL_DELETE_SUPERPERSON
            = "delete from SuperPerson where SuperPersonId = ?";

    public static final String SQL_UPDATE_SUPERPERSON
            = "update SuperPerson set Name = ?, Description = ?, SuperPower = ? where SuperPersonId = ?";

    public static final String SQL_SELECT_SUPERPERSON
            = "select * from SuperPerson where SuperPersonId = ?";

    public static final String SQL_SELECT_ALL_SUPERPERSONS
            = "select * from SuperPerson";

    /////////////////////////////////////////
    public static final String SQL_SELECT_SUPERPEOPLE_BY_LOCATION
            = "SELECT Name "
            + "FROM SuperPerson "
            + "INNER JOIN SuperPersonSighting ON SuperPerson.SuperPersonId = SuperPersonSighting.SuperPersonId "
            + "INNER JOIN Sighting ON SuperPersonSighting.SightingId = Sighting.SightingId "
            + "INNER JOIN Location ON Sighting.LocationId = Location.LocationId "
            + "WHERE Location.LocationName = ?";

    public static final String SQL_SELECT_ALL_SUPERPEOPLE_LOCATIONS
            = "SELECT LocationName"
            + "FROM Location "
            + "INNER JOIN Sighting ON Sighting.LocationId = Location.LocationId "
            + "INNER JOIN SuperPersonSighting ON SuperPersonSighting.SightingId = Sighting.SightingId "
            + "INNER JOIN SuperPerson ON SuperPerson.SuperPersonId = SuperPersonSighting.SuperPersonId "
            + "WHERE SuperPerson.Name = ?";

    public static final String SQL_GET_SUPERPERSON_AND_LOCATION_BY_DATE
            = "SELECT Name, LocationName "
            + "FROM SuperPerson "
            + "INNER JOIN SuperPersonSighting ON SuperPerson.SuperPersonId = SuperPersonSighting.SuperPersonId "
            + "INNER JOIN Sighting ON SuperPersonSighting.SightingId = Sighting.SightingId "
            + "INNER JOIN Location ON Sighting.LocationId = Location.LocationId "
            + "WHERE Date = ?";

    public static final String SQL_GET_ALL_SUPERPERSON_ORGANIZATIONS
            = "SELECT orgName "
            + "FROM Organization "
            + "INNER JOIN SuperPersonOrganization ON Organization.OrganizationId = SuperPersonOrganization.OrganizationId "
            + "INNER JOIN SuperPerson ON SuperPersonOrganization.SuperPersonId = SuperPerson.SuperPersonId "
            + "WHERE SuperPerson.Name = ?";

    public static final String SQL_GET_SUPERPERSONS_FOR_SIGHTING
            = "SELECT * "
            + "FROM SuperPerson "
            + "INNER JOIN SuperPersonSighting ON SuperPersonSighting.SuperPersonId = SuperPerson.SuperPersonId "
            + "INNER JOIN Sighting ON Sighting.SightingId = SuperPersonSighting.SightingId "
            + "WHERE Sighting.SightingId = ?";

    ///////////////////MAINS////////////////////////
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void addSuperPerson(SuperPerson superPerson) {
        jdbc.update(SQL_INSERT_SUPERPERSON,
                superPerson.getName(),
                superPerson.getDescription(),
                superPerson.getSuperPower());

        superPerson.setSuperPersonId(jdbc.queryForObject("select LAST_INSERT_ID()",
                Integer.class));

    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void deleteSuperPerson(int superPersonId) {

        jdbc.update(SQL_DELETE_SUPERPERSON_ORGANIZATION, superPersonId);
        jdbc.update(SQL_DELETE_SUPERPERSON_SIGHTING, superPersonId);
        jdbc.update(SQL_DELETE_SUPERPERSON, superPersonId);

    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void updateSuperPersonById(SuperPerson superPerson) {
        jdbc.update(SQL_UPDATE_SUPERPERSON,
                superPerson.getName(),
                superPerson.getDescription(),
                superPerson.getSuperPower(),
                superPerson.getSuperPersonId());

    }

    @Override
    public SuperPerson getSuperPersonById(int superPersonId) {
        try {
            SuperPerson superPerson = jdbc.queryForObject(SQL_SELECT_SUPERPERSON,
                    new SuperPersonMapper(),
                    superPersonId);
            return superPerson;

        } catch (EmptyResultDataAccessException ex) {
            return null;
        }

    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public List<SuperPerson> getAllSuperPersons() {
        List<SuperPerson> superPersonList = jdbc.query(SQL_SELECT_ALL_SUPERPERSONS,
                new SuperPersonMapper());

        return associateTablesWithSuperPersons(superPersonList);
    }

    @Override
    public List<SuperPerson> getSuperPersonBySighting(int sightingId) {
        List<SuperPerson> superPersonList
                = jdbc.query(SQL_GET_SUPERPERSONS_FOR_SIGHTING,
                        new SuperPersonMapper(),
                        sightingId);

        return associateTablesWithSuperPersons(superPersonList);

    }

    @Override
    public List<SuperPerson> getSuperPersonsByOrganizationId(int organizationId) {
        List<SuperPerson> superPersonList
                = jdbc.query(SQL_SELECT_SUPERPERSON_BY_ORGANIZATIONID,
                        new SuperPersonMapper(),
                        organizationId);

        return associateTablesWithSuperPersons(superPersonList);
    }

    @Override
    public List<SuperPerson> getSuperPersonsByLocationId(int locationId) {
        List<SuperPerson> superPersonList
                = jdbc.query(SQL_SELECT_SUPERPERSON_BY_LOCATIONID,
                        new SuperPersonMapper(),
                        locationId);
        return associateTablesWithSuperPersons(superPersonList);
    }

    @Override
    public List<SuperPerson> findSuperPersonForOrganization(Organization organization) {
        return jdbc.query(SQL_SELECT_SUPERPERSON_BY_ORGANIZATIONID,
                new SuperPersonMapper(),
                organization.getOrganizationId());
    }

    //////////////////HELPERS////////////////////////
    private List<SuperPerson> associateTablesWithSuperPersons(List<SuperPerson> superPersonList) {
        for (SuperPerson currentSuperPerson : superPersonList) {

            currentSuperPerson.setSightings(findSightingForSuperPerson(currentSuperPerson));
            currentSuperPerson.setOrganizations(findOrganizationsForSuperPerson(currentSuperPerson));
        }

        return superPersonList;

    }

    public List<Organization> findOrganizationsForSuperPerson(SuperPerson superPerson) {
        return jdbc.query(SQL_SELECT_ORGANIZATION_BY_SUPERPERSONID,
                new OrganizationMapper(),
                superPerson.getSuperPersonId());
    }

    public List<Sighting> findSightingForSuperPerson(SuperPerson superPerson) {
        return jdbc.query(SQL_SELECT_SIGHTING_BY_SUPERPERSONID,
                new SightingMapper(),
                superPerson.getSuperPersonId());
    }

    //////////////////SQL HELP///////////////////////
    //  private static final String SQL_SELECT_SIGHTING_BY_SUPERPERSONID
    //      = "SELECT Sighting.SightingId, Sighting.LocationId, Sighting.Date "
    //    + "FROM Sighting "
    //   + "JOIN SuperPersonSighting ON SuperPersonId "
    //   + "WHERE Sighting.SightingId = SuperPersonSighting.SightingId "
    //   + "AND SuperPersonSighting.SuperPersonId = ?";
    private static final String SQL_SELECT_SIGHTING_BY_SUPERPERSONID
            = "SELECT Sighting.SightingId, Sighting.Date "
            + "FROM Sighting JOIN SuperPersonSighting ON SuperPersonId "
            + "Where Sighting.SightingId =  SuperPersonSighting.SightingId "
            + "and SuperPersonSighting.SuperPersonId = ?";

    private static final String SQL_SELECT_ORGANIZATION_BY_SUPERPERSONID
            = "SELECT * "
            + "FROM Organization "
            + "INNER JOIN SuperPersonOrganization ON Organization.OrganizationId = SuperPersonOrganization.OrganizationId "
            + "WHERE SuperPersonOrganization.SuperPersonId = ?";

    private static final String SQL_SELECT_SUPERPERSON_BY_ORGANIZATIONID
            = "SELECT SuperPerson.SuperPersonId, SuperPerson.Name, SuperPerson.Description "
            + "FROM SuperPerson "
            + "JOIN SuperPersonOrganization on SuperPerson.SuperPersonId = SuperPersonOrganization.SuperPersonId "
            + "WHERE SuperPersonOrganization.OrganizationId = ?";

    private static final String SQL_SELECT_SUPERPERSON_BY_LOCATIONID
            = "SELECT * "
            + "FROM SuperPerson "
            + "INNER JOIN SuperPersonSighting on SuperPerson.SuperPersonId = SuperPersonSighting.SuperPersonId "
            + "INNER JOIN Sighting on Sighting.SightingId = SuperPersonSighting.SightingId "
            + "INNER JOIN Location on Sighting.LocationId = Location.LocationId "
            + "WHERE Location.LocationId = ?";

    /////////////////ALL MAPS/////////////////////
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

    /////////////////////MISC SQL////////////////////
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
