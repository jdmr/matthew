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
package edu.swau.matthew.utils;

import edu.swau.matthew.dao.UserDao;
import edu.swau.matthew.model.Company;
import edu.swau.matthew.model.Organization;
import edu.swau.matthew.model.Role;
import edu.swau.matthew.model.User;
import edu.swau.matthew.model.Warehouse;
import edu.swau.matthew.service.OrganizationService;
import edu.swau.matthew.service.WarehouseService;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author J. David Mendoza <jdmendoza@swau.edu>
 */
@Component
@Transactional
public class Bootstrap implements ApplicationListener<ContextRefreshedEvent> {

    private static final Logger log = LoggerFactory.getLogger(Bootstrap.class);

    @Autowired
    private UserDao userDao;
    @Autowired
    private OrganizationService organizationService;
    @Autowired
    private WarehouseService warehouseService;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        log.info("Making initial check...");
        Long organizationCount = organizationService.count();
        if (organizationCount == 0) {
            Organization organization = new Organization("SWAU", "SWAU", "Southwestern Adventist University", "admin@swau.edu");
            organization = organizationService.create(organization);
            
            log.info("Validating Roles");
            Role adminRole = userDao.getRole("ROLE_ADMIN");
            if (adminRole == null) {
                adminRole = new Role("ROLE_ADMIN");
                adminRole = userDao.createRole(adminRole);
            }

            Role userRole = userDao.getRole("ROLE_USER");
            if (userRole == null) {
                userRole = new Role("ROLE_USER");
                userRole = userDao.createRole(userRole);
            }

            log.info("Validating Users");
            User admin = userDao.get("admin@swau.edu");
            if (admin == null) {
                admin = new User("admin@swau.edu", "admin", "Admin", "User");
                admin.addRole(adminRole);
                admin.addRole(userRole);
                
                organization = organizationService.refresh(organization);
                log.debug("Companies: {}", organization.getCompanies());
                Company company = organization.getCompanies().get(0);
                List<Warehouse> warehouses = warehouseService.list(company);
                Warehouse warehouse = warehouses.get(0);
                admin.setWarehouse(warehouse);
                
                userDao.create(admin);
            }
        }

        log.info("Done. Application is running!");
    }

}
