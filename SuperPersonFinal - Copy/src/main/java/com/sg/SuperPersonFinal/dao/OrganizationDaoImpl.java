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
public class OrganizationDaoImpl implements OrganizationDao {

    @Autowired
    JdbcTemplate jdbc;

    ///////////////////////////////////////////////
    private static final String SQL_INSERT_ORGANIZATION
            = "insert into Organization (LocationId, orgName, Description, contactInfo) values (?, ?, ?, ?)";

    private static final String SQL_DELETE_ORGANIZATION
            = "delete from Organization where OrganizationId = ?";

    private static final String SQL_UPDATE_ORGANIZATION
            = "update organization set orgName = ?, Description = ?, contactInfo = ?, "
            + "locationId = ? where organizationId = ?";

    private static final String SQL_SELECT_ORGANIZATIONS
            = "select * from Organization where OrganizationId = ?";

    private static final String SQL_SELECT_ALL_ORGANIZATION
            = "select * from Organization";

    public static final String SQL_REMOVE_LOCATION_FROM_ORGANIZATION
            = "update Organization set LocationId = NULL where LocationId = ?";

    public static final String SQL_INSERT_SUPERPERSON_ORGANIZATION
            = "insert into SuperPersonOrganization(SuperPersonId, OrganizationId) values (?, ?)";

    ///////////////////////////////////////////////////
    public static final String SQL_GET_ALL_ORGANIZATION_MEMBERS
            = "SELECT * "
            + "FROM SUPERPERSON"
            + "INNER JOIN SuperPersonOrganization ON SuperPersonOrganization.SuperPersonId = SuperPerson.SuperPersonId "
            + "INNER JOIN Organization ON Organization.OrganizationId = SuperPersonOrganization.OrganizationId "
            + "WHERE Organization.OrganizationId = ?";

    public static final String SQL_GET_LOCATION_FOR_ORGANIZATION
            = "SELECT * "
            + "FROM Location "
            + "INNER JOIN Organization ON Organization.LocationId = Location.LocationId "
            + "WHERE Organization.OrganizationId = ?";

    public static final String SQL_SELECT_ALL_ORGANIZATIONS
            = "SELECT * "
            + "FROM Location  "
            + "INNER JOIN Organization ON Location.LocationId = Organization.LocationId ";

    private static final String SQL_SELECT_ORGANIZATION_BY_SUPERPERSONID
            = "SELECT organization.OrganizationId, orgainzation.orgName, organization.Description, organization.ContactInfo"
            + "FROM Organization"
            + "Join SuperPersonOrganization ON organizatoin.OrganizationId = SuperPersonOrganization.OrganizationId"
            + "WHERE SuperPersonOrganization.SuperPersonId = ?";

    //////////////////////////////////////////////////////
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void addOrganization(Organization organization) {
        jdbc.update(SQL_INSERT_ORGANIZATION,
                organization.getLocation().getLocationId(),
                organization.getOrgName(),
                organization.getContactInfo(),
                organization.getDescription());
        
        organization.setOrganizationId(jdbc.queryForObject("select LAST_INSERT_ID()",
                Integer.class));
        
        organization.getSuperPersonOrganization();
        insertSuperPersonOrganization(organization);

    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void deleteOrganization(int organizationId) {
        jdbc.update(SQL_DELETE_ORGANIZATION_SUPERPERSONS, organizationId);
        jdbc.update(SQL_DELETE_ORGANIZATION, organizationId);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void updateOrganization(Organization organization) {
        jdbc.update(SQL_UPDATE_ORGANIZATION,
                organization.getLocation().getLocationId(),
                organization.getOrgName(),
                organization.getDescription(),
                organization.getContactInfo(),
                organization.getOrganizationId());
        organization.getSuperPersonOrganization();

        jdbc.update(SQL_DELETE_ORGANIZATION_SUPERPERSONS,
                organization.getOrganizationId());
        insertSuperPersonOrganization(organization);
    }

    @Override
    public Organization getOrganizationById(int organizationId) {

        try {
            Organization organization = jdbc.queryForObject(SQL_SELECT_ORGANIZATIONS,
                    new OrganizationMapper(),
                    organizationId);
            organization.setSuperPersonOrganization(getSuperPersonsForOrganization(organization));
            organization.setLocation(getLocationForOrganization(organization));
            return organization;
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }

    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public List<Organization> getAllOrganizations() {
        try {
            return jdbc.query(SQL_SELECT_ALL_ORGANIZATIONS,
                    new OrganizationMapper());
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }

    }

    @Override
    public List<Organization> findOrganizationsForSuperPerson(SuperPerson superPerson) {
        return jdbc.query(SQL_SELECT_ORGANIZATION_BY_SUPERPERSONID,
                new OrganizationMapper(),
                superPerson.getSuperPersonId());
    }

    //////////////////HELPERS////////////////////////
    private void insertSuperPersonOrganization(Organization organization) {
        final int organizationId = organization.getOrganizationId();
        final List<SuperPerson> superPersonList = organization.getSuperPersonOrganization();

        for (SuperPerson currentSuperPerson : superPersonList) {
            jdbc.update(SQL_INSERT_SUPERPERSON_ORGANIZATION,
                    currentSuperPerson.getSuperPersonId(),
                    organizationId);
        }

    }

    private List<SuperPerson> getSuperPersonsForOrganization(Organization organization) {
        List<SuperPerson> superPersonList = jdbc.query(SQL_GET_ALL_ORGANIZATION_MEMBERS,
                new SuperPersonMapper(),
                organization.getOrganizationId());

        return superPersonList;
    }

    private Location getLocationForOrganization(Organization organization) {
        return jdbc.queryForObject(SQL_GET_LOCATION_FOR_ORGANIZATION,
                new LocationMapper(),
                organization.getOrganizationId());

    }

    /////////////////MAPPERS/////////////////////////
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

    /////////////////////BRIDGES////////////////////
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
