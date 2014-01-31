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
import edu.swau.matthew.dao.CompanyDao;
import edu.swau.matthew.model.Company;
import edu.swau.matthew.model.XCompany;
import edu.swau.matthew.utils.Constants;
import java.util.Date;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author J. David Mendoza <jdmendoza@swau.edu>
 */
@Repository
@Transactional
public class CompanyDaoHibernate extends BaseDao implements CompanyDao {

    @Override
    public Company create(Company company) {
        Date date = new Date();
        company.setDateCreated(date);
        company.setLastUpdated(date);
        currentSession().save(company);
        
        audit(company, Constants.CREATE);
        
        return company;
    }
    
    private void audit(Company company, String action) {
        XCompany xcompany = new XCompany();
        BeanUtils.copyProperties(company, xcompany, new String[] {"id", "version"});
        xcompany.setOrganizationId(company.getOrganization().getId());
        xcompany.setCompanyId(company.getId());
        xcompany.setAction(action);
        currentSession().save(xcompany);
    }
    
}
