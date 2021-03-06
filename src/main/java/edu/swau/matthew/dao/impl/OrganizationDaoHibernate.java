/*
 * The MIT License
 *
 * Copyright 2014 Southwestern Adventist University.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package edu.swau.matthew.dao.impl;

import edu.swau.matthew.dao.BaseDao;
import edu.swau.matthew.dao.OrganizationDao;
import edu.swau.matthew.model.Organization;
import edu.swau.matthew.model.XOrganization;
import edu.swau.matthew.utils.Constants;
import java.util.Date;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author J. David Mendoza <jdmendoza@swau.edu>
 */
@Repository
@Transactional
public class OrganizationDaoHibernate extends BaseDao implements OrganizationDao {

    @Override
    @Transactional(readOnly = true)
    public Long count() {
        Criteria criteria = currentSession().createCriteria(Organization.class);
        criteria.setProjection(Projections.rowCount());
        List<Long> results = criteria.list();
        Long organizationCount = results.get(0);
        return organizationCount;
    }

    @Override
    public Organization create(Organization organization) {
        Date date = new Date();
        organization.setDateCreated(date);
        organization.setLastUpdated(date);
        currentSession().save(organization);

        audit(organization, Constants.CREATE);
        
        return organization;
    }
    
    private void audit(Organization organization, String action) {
        XOrganization xorganization = new XOrganization();
        BeanUtils.copyProperties(organization, xorganization, new String[] {"id","version"});
        xorganization.setAction(action);
        xorganization.setOrganizationId(organization.getId());
        currentSession().save(xorganization);
    }

    @Override
    public Organization refresh(Organization organization) {
        currentSession().refresh(organization);
        return organization;
    }
    
}
