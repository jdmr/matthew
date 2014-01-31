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

package edu.swau.matthew.service.impl;

import edu.swau.matthew.dao.CompanyDao;
import edu.swau.matthew.model.Company;
import edu.swau.matthew.model.Warehouse;
import edu.swau.matthew.service.BaseService;
import edu.swau.matthew.service.CompanyService;
import edu.swau.matthew.service.WarehouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author J. David Mendoza <jdmendoza@swau.edu>
 */
@Service
@Transactional
public class CompanyServiceImpl extends BaseService implements CompanyService {

    @Autowired
    private CompanyDao companyDao;
    @Autowired
    private WarehouseService warehouseService;
    
    @Override
    public Company create(Company company) {
        company = companyDao.create(company);
        
        Warehouse warehouse = new Warehouse("MAIN", "MAIN", company.getCreator(), company);
        warehouseService.create(warehouse);
        
        return company;
    }
    
}
