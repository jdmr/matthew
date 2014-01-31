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
import edu.swau.matthew.dao.WarehouseDao;
import edu.swau.matthew.model.Warehouse;
import edu.swau.matthew.model.XWarehouse;
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
public class WarehouseDaoHibernate extends BaseDao implements WarehouseDao {

    @Override
    public Warehouse create(Warehouse warehouse) {
        Date date = new Date();
        warehouse.setDateCreated(date);
        warehouse.setLastUpdated(date);
        currentSession().save(warehouse);
        
        audit(warehouse, Constants.CREATE);
        
        return warehouse;
    }
    
    private void audit(Warehouse warehouse, String action) {
        XWarehouse xwarehouse = new XWarehouse();
        BeanUtils.copyProperties(warehouse, xwarehouse, new String[] {"id", "version"});
        xwarehouse.setCompanyId(warehouse.getCompany().getId());
        xwarehouse.setWarehouseId(warehouse.getId());
        xwarehouse.setAction(action);
        currentSession().save(xwarehouse);
    }
}
