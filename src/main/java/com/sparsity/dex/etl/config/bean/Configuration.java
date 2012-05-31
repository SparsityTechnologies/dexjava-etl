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
package com.sparsity.dex.etl.config.bean;

import java.util.ArrayList;
import java.util.Collection;

import com.sparsity.dex.etl.DexUtil;
import com.sparsity.dex.etl.DexUtilsException;

/**
 * {@link DexUtil} configuration.
 * <p>
 * It can manage a {@link DatabaseConfiguration} instance collection, but there
 * is an special one, the default.
 * 
 * @author Sparsity Technologies
 * 
 */
public class Configuration {

    /**
     * {@link DatabaseConfiguration} instance collection.
     */
    private Collection<DatabaseConfiguration> databases = new ArrayList<DatabaseConfiguration>();

    /**
     * Default {@link DatabaseConfiguration} instance.
     */
    private DatabaseConfiguration defDatabase = null;

    /**
     * Creates a new instance.
     */
    public Configuration() {
    }

    /**
     * Sets the default {@link DatabaseConfiguration} instance.
     * 
     * @param def
     *            Default {@link DatabaseConfiguration} instance.
     */
    public void setDefaultDatabase(DatabaseConfiguration def) {
        this.defDatabase = def;
    }

    /**
     * Gets the default {@link DatabaseConfiguration} instance.
     * 
     * @return The default {@link DatabaseConfiguration} instance.
     */
    public DatabaseConfiguration getDefaultDatabase() {
        return defDatabase;
    }

    /**
     * Adds a {@link DatabaseConfiguration} instance to the
     * {@link DatabaseConfiguration} instance collection.
     * 
     * @param dbConf
     *            {@link DatabaseConfiguration} instance.
     * @return <code>true</code> if added, <code>false</code> otherwise.
     */
    public boolean addDatabaseConfiguration(DatabaseConfiguration dbConf) {
        dbConf.setConfiguration(this);
        return databases.add(dbConf);
    }

    /**
     * Sets the {@link DatabaseConfiguration} instance collection.
     * <p>
     * It clears the previous collection and the sets the default
     * {@link DatabaseConfiguration} instance to <code>null</code>.
     * 
     * @param dbConfs
     *            {@link DatabaseConfiguration} instance collection.
     */
    public void setDatabases(Collection<DatabaseConfiguration> dbConfs) {
        databases.clear();
        for (DatabaseConfiguration dbConf : dbConfs) {
            addDatabaseConfiguration(dbConf);
        }
    }

    /**
     * Gets the {@link DatabaseConfiguration} instance collection.
     * 
     * @return The {@link DatabaseConfiguration} instance collection.
     */
    public Collection<DatabaseConfiguration> getDatabases() {
        return databases;
    }

    /**
     * Executes the configuration.
     * <p>
     * Some elements of the configuration may require to be executed, this will
     * execute all of them.
     * 
     * @throws DexUtilsException
     */
    public void execute() throws DexUtilsException {
        // try {
        // } catch {
        // } finally {
        // }
    }
}
