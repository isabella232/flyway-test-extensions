/**
 * Copyright (C) 2011-2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.flywaydb.sample.test.spring.boot.flywaytest;

import org.flywaydb.test.annotation.FlywayTest;
import org.flywaydb.test.junit.FlywayTestExecutionListener;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.flywaydb.sample.test.spring.boot.SampleFlywayApplication;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = SampleFlywayApplication.class)
@TransactionConfiguration
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class, FlywayTestExecutionListener.class })
@FlywayTest
/**
 * Show a spring-boot test configuration with {@link FlywayTest} annotation.
 *
 * @since 3.2.1.1
 */
public class FlywayTestApplicationTest {

    @Autowired
    private JdbcTemplate template;

    @FlywayTest(invokeCleanDB = true)
    @Test
    public void singleLocation() throws Exception {
        assertEquals(new Integer(3), this.template.queryForObject(
                "SELECT COUNT(*) from PERSON", Integer.class));
    }

    @FlywayTest(locationsForMigrate = {"/db/migration", "/db/migration2"})
    @Test
    public void twoLocations() throws Exception {
        assertEquals(new Integer(5), this.template.queryForObject(
                "SELECT COUNT(*) from PERSON", Integer.class));
    }

}
