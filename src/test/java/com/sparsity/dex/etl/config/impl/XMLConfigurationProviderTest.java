/*
 * Copyright (c) 2012 Sparsity Technologies www.sparsity-technologies.com
 * 
 * This file is part of 'dexjava-etl'.
 * 
 * Licensed under the GNU Lesser General Public License (LGPL) v3, (the
 * "License"). You may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.sparsity.dex.etl.config.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.sparsity.dex.etl.config.bean.Configuration;
import com.sparsity.dex.etl.config.bean.DatabaseConfiguration;

/**
 * Unit test for the {@link XMLConfigurationProvider} class.
 * 
 * @author Sparsity Technologies
 * 
 */
public class XMLConfigurationProviderTest {

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testDatabase() {
        XMLConfigurationProvider xmlProv = new XMLConfigurationProvider();
        xmlProv.setFile(new File(XMLConfigurationProviderTest.class
                .getClassLoader()
                .getResource("dex-etl-config-XMLConfProvTest.xml").getFile()));
        Configuration conf = new Configuration();
        xmlProv.set(conf);
        xmlProv.load();

        List<String> names = Arrays.asList("foo0", "foo1", "foo2");
        for (DatabaseConfiguration dbConf : conf.getDatabases()) {
            assertTrue(names.contains(dbConf.getName()));
            if (dbConf.getName().equals(names.get(0))) {
                assertEquals(dbConf.getAlias(), "FOO0");
                assertEquals(dbConf.getPath(), "gdb0.dex");
                assertNull(dbConf.getDexConfiguration());
            } else if (dbConf.getName().equals(names.get(1))) {
                assertEquals(dbConf.getAlias(), "FOO1");
                assertEquals(dbConf.getPath(), "gdb1.dex");
                assertEquals(dbConf.getDexConfiguration(), "gdb1.cfg");
            } else if (dbConf.getName().equals(names.get(2))) {
                assertEquals(dbConf.getAlias(), "FOO2");
                assertEquals(dbConf.getPath(), "gdb2.dex");
                assertNull(dbConf.getDexConfiguration());
            } else {
                assertTrue(false);
            }
        }
        assertTrue(conf.getDatabases().contains(conf.getDefaultDatabase()));
    }
}
