/*Copyright (c) 2015-2016 wavemaker.com All Rights Reserved.
 This software is the confidential and proprietary information of wavemaker.com You shall not disclose such Confidential Information and shall use it only in accordance
 with the terms of the source code license agreement you entered into with wavemaker.com*/
package com.auto_anhhzklzfg.hrdb.service;

/*This is a Studio Managed File. DO NOT EDIT THIS FILE. Your changes may be reverted by Studio.*/

import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import com.wavemaker.runtime.data.dao.WMGenericDao;
import com.wavemaker.runtime.data.exception.EntityNotFoundException;
import com.wavemaker.runtime.data.export.DataExportOptions;
import com.wavemaker.runtime.data.export.ExportType;
import com.wavemaker.runtime.data.expression.QueryFilter;
import com.wavemaker.runtime.data.model.AggregationInfo;
import com.wavemaker.runtime.file.model.Downloadable;

import com.auto_anhhzklzfg.hrdb.User;


/**
 * ServiceImpl object for domain model class User.
 *
 * @see User
 */
@Service("hrdb.UserService")
@Validated
public class UserServiceImpl implements UserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);


    @Autowired
    @Qualifier("hrdb.UserDao")
    private WMGenericDao<User, Integer> wmGenericDao;

    public void setWMGenericDao(WMGenericDao<User, Integer> wmGenericDao) {
        this.wmGenericDao = wmGenericDao;
    }

    @Transactional(value = "hrdbTransactionManager")
    @Override
    public User create(User user) {
        LOGGER.debug("Creating a new User with information: {}", user);

        User userCreated = this.wmGenericDao.create(user);
        // reloading object from database to get database defined & server defined values.
        return this.wmGenericDao.refresh(userCreated);
    }

    @Transactional(readOnly = true, value = "hrdbTransactionManager")
    @Override
    public User getById(Integer userIdInstance) {
        LOGGER.debug("Finding User by id: {}", userIdInstance);
        return this.wmGenericDao.findById(userIdInstance);
    }

    @Transactional(readOnly = true, value = "hrdbTransactionManager")
    @Override
    public User findById(Integer userIdInstance) {
        LOGGER.debug("Finding User by id: {}", userIdInstance);
        try {
            return this.wmGenericDao.findById(userIdInstance);
        } catch (EntityNotFoundException ex) {
            LOGGER.debug("No User found with id: {}", userIdInstance, ex);
            return null;
        }
    }

    @Transactional(readOnly = true, value = "hrdbTransactionManager")
    @Override
    public List<User> findByMultipleIds(List<Integer> userIdInstances, boolean orderedReturn) {
        LOGGER.debug("Finding Users by ids: {}", userIdInstances);

        return this.wmGenericDao.findByMultipleIds(userIdInstances, orderedReturn);
    }


    @Transactional(rollbackFor = EntityNotFoundException.class, value = "hrdbTransactionManager")
    @Override
    public User update(User user) {
        LOGGER.debug("Updating User with information: {}", user);

        this.wmGenericDao.update(user);
        this.wmGenericDao.refresh(user);

        return user;
    }

    @Transactional(value = "hrdbTransactionManager")
    @Override
    public User delete(Integer userIdInstance) {
        LOGGER.debug("Deleting User with id: {}", userIdInstance);
        User deleted = this.wmGenericDao.findById(userIdInstance);
        if (deleted == null) {
            LOGGER.debug("No User found with id: {}", userIdInstance);
            throw new EntityNotFoundException(String.valueOf(userIdInstance));
        }
        this.wmGenericDao.delete(deleted);
        return deleted;
    }

    @Transactional(value = "hrdbTransactionManager")
    @Override
    public void delete(User user) {
        LOGGER.debug("Deleting User with {}", user);
        this.wmGenericDao.delete(user);
    }

    @Transactional(readOnly = true, value = "hrdbTransactionManager")
    @Override
    public Page<User> findAll(QueryFilter[] queryFilters, Pageable pageable) {
        LOGGER.debug("Finding all Users");
        return this.wmGenericDao.search(queryFilters, pageable);
    }

    @Transactional(readOnly = true, value = "hrdbTransactionManager")
    @Override
    public Page<User> findAll(String query, Pageable pageable) {
        LOGGER.debug("Finding all Users");
        return this.wmGenericDao.searchByQuery(query, pageable);
    }

    @Transactional(readOnly = true, value = "hrdbTransactionManager", timeout = 300)
    @Override
    public Downloadable export(ExportType exportType, String query, Pageable pageable) {
        LOGGER.debug("exporting data in the service hrdb for table User to {} format", exportType);
        return this.wmGenericDao.export(exportType, query, pageable);
    }

    @Transactional(readOnly = true, value = "hrdbTransactionManager", timeout = 300)
    @Override
    public void export(DataExportOptions options, Pageable pageable, OutputStream outputStream) {
        LOGGER.debug("exporting data in the service hrdb for table User to {} format", options.getExportType());
        this.wmGenericDao.export(options, pageable, outputStream);
    }

    @Transactional(readOnly = true, value = "hrdbTransactionManager")
    @Override
    public long count(String query) {
        return this.wmGenericDao.count(query);
    }

    @Transactional(readOnly = true, value = "hrdbTransactionManager")
    @Override
    public Page<Map<String, Object>> getAggregatedValues(AggregationInfo aggregationInfo, Pageable pageable) {
        return this.wmGenericDao.getAggregatedValues(aggregationInfo, pageable);
    }



}