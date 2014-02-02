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
package edu.swau.matthew.service;

import edu.swau.matthew.config.ComponentConfig;
import edu.swau.matthew.config.DataConfig;
import edu.swau.matthew.config.MailConfig;
import edu.swau.matthew.config.SecurityConfig;
import edu.swau.matthew.model.Organization;
import edu.swau.matthew.test.BaseServiceTest;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author J. David Mendoza <jdmendoza@swau.edu>
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {SecurityConfig.class, DataConfig.class, MailConfig.class, ComponentConfig.class})
@Transactional
public class OrganizationServiceTest extends BaseServiceTest {

    @Autowired
    private OrganizationService organizationService;

    @Test
    public void shouldCreateOrganization() {
        Organization organization = new Organization("TST-01", "TST-01", "TST-01", "admin@swau.edu");
        organization = organizationService.create(organization);
        Assert.assertNotNull("Should return an instance of organization", organization);
        Assert.assertNotNull("The organization instance should have an ID", organization.getId());
        Assert.assertNotNull("The organization instance should have a list of companies", organization.getCompanies());
    }
    
}
