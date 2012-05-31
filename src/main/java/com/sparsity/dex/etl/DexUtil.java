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

import java.io.File;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sparsity.dex.etl.config.ConfigurationProvider;
import com.sparsity.dex.etl.config.bean.Configuration;
import com.sparsity.dex.etl.config.impl.XMLConfigurationProvider;
import com.sparsity.dex.gdb.Graph;
import com.sparsity.dex.gdb.Session;

/**
 * Helper class to get a Dex database up and running.
 * <p>
 * It allows for starting and shutting down a Dex database specified by a
 * {@link Configuration} loaded at runtime.
 * <p>
 * Moreover, it automatically manages an exclusive {@link Session} for each
 * different calling thread.
 * 
 * @author Sparsity Technologies
 * 
 */
public class DexUtil {

    /**
     * {@link Configuration} instance.
     */
    private static Configuration config = null;

    /**
     * {@link ConfigurationProvider} instance.
     */
    private static ConfigurationProvider provider = null;

    /**
     * {@link Log} instance.
     */
    private static Log log = LogFactory.getLog(DexUtil.class);

    /**
     * Tries to load a default XML configuration.
     * <p>
     * If a 'dex-etl-config.xml' file exists in the classpath, it will be
     * loaded.
     */
    static {
        try {
            XMLConfigurationProvider xmlProv = new XMLConfigurationProvider();
            xmlProv.setFile(new File(DexUtil.class.getClassLoader()
                    .getResource("dex-etl-config.xml").getPath()));
            setConfigurationProvider(xmlProv);
        } catch (Exception e) {
            config = null;
            provider = null;
            log.error("Default XML configuration cannot be loaded", e);
        }
    }

    /**
     * Sets and starts the Database from the {@link Configuration} instance.
     * <p>
     * If a previous configuration had been loaded, it will be shutdown.
     * 
     * @param c
     *            {@link Configuration} instance.
     */
    public static void setConfiguration(Configuration c) {
        if (config != null) {
            shutdown();
        }

        config = c;

        try {
            config.execute();
        } catch (Exception e) {
            throw new DexUtilsException("Given configuration is not valid");
        }
        start();
    }

    /**
     * Sets the {@link ConfigurationProvider} instance to get a
     * {@link Configuration} instance from.
     * <p>
     * If a previous configuration had been loaded, it will be shutdown.
     * 
     * @param prov
     *            {@link ConfigurationProvider} instance.
     */
    public static void setConfigurationProvider(ConfigurationProvider prov) {
        if (config != null) {
            shutdown();
        }
        provider = prov;

        config = new Configuration();
        provider.set(config);
        try {
            provider.load();
            config.execute();
        } catch (Exception e) {
            throw new DexUtilsException(
                    "Given configuration provider is not valid", e);
        }
        start();
    }

    /**
     * Gets the alias of the database.
     * 
     * @return The alias of the database.
     */
    public static String getAlias() {
        return config.getDefaultDatabase().getAlias();
    }

    /**
     * Gets the path of the database.
     * 
     * @return The path of the database.
     */
    public static String getPath() {
        return config.getDefaultDatabase().getPath();
    }

    /**
     * Starts the database just in case it has not been started.
     */
    public static void start() {
        config.getDefaultDatabase().openDatabase();
    }

    /**
     * Shutdowns the database if it has been started.
     */
    public static void shutdown() {
        config.getDefaultDatabase().closeDatabase();
    }

    /**
     * Restarts the database.
     */
    public static void restart() {
        config.getDefaultDatabase().restartDatabase();
    }

    /**
     * Gets the Dex {@link Graph} instance from the working {@link Session} for
     * the calling thread.
     * 
     * @return The Dex {@link Graph} instance from the working {@link Session}
     *         for the calling thread.
     */
    public static Graph getGraph() {
        start();
        return config.getDefaultDatabase().getGraph();
    }

    /**
     * Gets the working {@link Session} for the calling thread.
     * 
     * @return The working {@link Session} for the calling thread.
     */
    public static Session getSession() {
        start();
        return config.getDefaultDatabase().getSession();
    }

    /**
     * Gets the Dex type identifier for the given type name.
     * 
     * @param name
     *            Node or edge type name.
     * @return The Dex type identifier for the given type name.
     */
    public static Integer getTypeIdentifier(String name) {
        start();
        return config.getDefaultDatabase().getTypeIdentifier(name);
    }

    /**
     * Gets the Dex attribute identifier for the given type and attribute names.
     * 
     * @param type
     *            Node or edge type name.
     * @param name
     *            Attribute name.
     * @return The Dex attribute identifier for the given type and attribute
     *         names.
     */
    public static Integer getAttributeIdentifier(String type, String name) {
        start();
        return config.getDefaultDatabase().getAttributeIdentifier(type, name);
    }

    /**
     * Gets the Dex attribute identifier for the given attribute name.
     * <p>
     * The attribute name corresponds to the concatenation of the type name, a
     * split character and the attribute name. For example:
     * <code>"person.name"</code>, where <code>"person"</code> is the type name,
     * <code>'.'</code> the split character and <code>"name"</code> the
     * attribute name.
     * <p>
     * The split character can just exist once.
     * 
     * @param attrname
     *            Type and attribute name concatenation.
     * @param split
     *            Split character.
     * @return The Dex attribute identifier for the given type and attribute
     *         names.
     */
    public static Integer getAttributeIdentifier(String attrname, char split) {
        int i = attrname.indexOf(split);
        if (i == -1 || attrname.lastIndexOf(split) != i
                || i >= attrname.length()) {
            throw new IllegalArgumentException("Attribute name '" + attrname
                    + "' cannot be splited as expected using '" + split + "'.");
        }
        return getAttributeIdentifier(attrname.substring(0, i),
                attrname.substring(i + 1));
    }

    /**
     * Drops the schema of the database.
     */
    public static void dropSchema() {
        start();
        config.getDefaultDatabase().dropSchema();
    }

    @Override
    protected void finalize() throws Throwable {
        shutdown();
    }
}
