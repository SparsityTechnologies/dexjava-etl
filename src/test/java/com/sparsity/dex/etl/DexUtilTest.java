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
package com.sparsity.dex.etl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.sparsity.dex.etl.config.bean.Configuration;
import com.sparsity.dex.etl.config.bean.DatabaseConfiguration;
import com.sparsity.dex.etl.config.impl.XMLConfigurationProvider;
import com.sparsity.dex.gdb.Attribute;
import com.sparsity.dex.gdb.AttributeKind;
import com.sparsity.dex.gdb.DataType;
import com.sparsity.dex.gdb.Database;
import com.sparsity.dex.gdb.Dex;
import com.sparsity.dex.gdb.DexConfig;
import com.sparsity.dex.gdb.Graph;
import com.sparsity.dex.gdb.Session;
import com.sparsity.dex.gdb.Type;

/**
 * Unit test for the {@link DexUtil} class.
 * 
 * @author Sparsity Technologies
 * 
 */
public class DexUtilTest {

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
        DexUtil.shutdown();
        File f = new File(DexUtil.getPath());
        assertTrue(f.exists());
        assertTrue(f.delete());
    }

    @Test
    public void testFromDefaultXMLConfiguration() {
        assertTrue(DexUtil.getGraph() != null);
        assertEquals("FOO", DexUtil.getAlias());
        DexUtil.shutdown();
    }

    @Test
    public void testFromDynamicConfiguration() {
        DatabaseConfiguration dbConf = new DatabaseConfiguration();
        dbConf.setPath("MyGDB.dex");
        dbConf.setAlias("MyGDB");
        Configuration conf = new Configuration();
        conf.setDefaultDatabase(dbConf);

        DexUtil.setConfiguration(conf);
        assertTrue(DexUtil.getGraph() != null);
        assertEquals("MyGDB", DexUtil.getAlias());
        DexUtil.shutdown();
    }

    @Test
    public void testFromXMLConfiguration() {
        URL url = getClass().getClassLoader().getResource(
                "dex-etl-config-DexUtilTest.xml");
        assertTrue(url != null);
        XMLConfigurationProvider xmlProv = new XMLConfigurationProvider();
        xmlProv.setFile(new File(url.getFile()));

        DexUtil.setConfigurationProvider(xmlProv);
        assertTrue(DexUtil.getGraph() != null);
        assertEquals("FOO1", DexUtil.getAlias());
        assertEquals("GDB1.dex", DexUtil.getPath());
        DexUtil.shutdown();
    }

    @Test
    public void testIdentifiers() throws FileNotFoundException {
        //
        // Build temporary DB
        //
        final String ALIAS = DexUtilTest.class.getSimpleName();
        final File PATH = new File(ALIAS + ".dex");
        if (PATH.exists()) {
            PATH.delete();
        }
        Dex dex = new Dex(new DexConfig());
        Database db = dex.create(PATH.getPath(), ALIAS);
        Session sess = db.newSession();
        Graph g = sess.getGraph();
        g.newAttribute(g.newNodeType("NodeType"), "Attribute", DataType.String,
                AttributeKind.Basic);
        g.newAttribute(g.newEdgeType("EdgeType", true, true), "Attribute",
                DataType.String, AttributeKind.Basic);
        sess.close();
        db.close();
        dex.close();
        //
        // Create Configuration
        //
        DatabaseConfiguration dbConf = new DatabaseConfiguration();
        dbConf.setAlias(ALIAS);
        dbConf.setPath(PATH.getAbsolutePath());
        Configuration cfg = new Configuration();
        cfg.setDefaultDatabase(dbConf);
        DexUtil.setConfiguration(cfg);
        DexUtil.start();
        //
        // Test
        //
        assertTrue(Type.InvalidType != DexUtil.getTypeIdentifier("NodeType"));
        assertTrue(Type.InvalidType != DexUtil.getTypeIdentifier("EdgeType"));
        assertTrue(Type.InvalidType == DexUtil
                .getTypeIdentifier("Non-existing-type"));
        assertTrue(Attribute.InvalidAttribute != DexUtil
                .getAttributeIdentifier("NodeType", "Attribute"));
        assertTrue(Attribute.InvalidAttribute != DexUtil
                .getAttributeIdentifier("EdgeType", "Attribute"));
        assertTrue(Attribute.InvalidAttribute == DexUtil
                .getAttributeIdentifier("Non-existing-type", "Attribute"));
        assertTrue(Attribute.InvalidAttribute == DexUtil
                .getAttributeIdentifier("Non-existing-type", "Attribute"));
        assertTrue(Attribute.InvalidAttribute == DexUtil
                .getAttributeIdentifier("NodeType", "Non-existing-attribute"));
        assertTrue(Attribute.InvalidAttribute == DexUtil
                .getAttributeIdentifier("EdgeType", "Non-existing-attribute"));

        assertTrue(Attribute.InvalidAttribute != DexUtil
                .getAttributeIdentifier("NodeType.Attribute", '.'));
        assertTrue(Attribute.InvalidAttribute != DexUtil
                .getAttributeIdentifier("EdgeType.Attribute", '.'));
        assertTrue(Attribute.InvalidAttribute == DexUtil
                .getAttributeIdentifier("Non-existing-type.Attribute", '.'));
        assertTrue(Attribute.InvalidAttribute == DexUtil
                .getAttributeIdentifier("NodeType.Non-existing-attribute", '.'));
        assertTrue(Attribute.InvalidAttribute == DexUtil
                .getAttributeIdentifier("NodeType.", '.'));
        boolean excep = false;
        try {
            DexUtil.getAttributeIdentifier("Node.Type.Attribute", '.');
        } catch (Exception e) {
            excep = true;
        } finally {
            assertTrue(excep);
            excep = false;
        }
    }
}
