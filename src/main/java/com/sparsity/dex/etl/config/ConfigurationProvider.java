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
package com.sparsity.dex.etl.config;

import com.sparsity.dex.etl.DexUtilsException;
import com.sparsity.dex.etl.config.bean.Configuration;

/**
 * Common interface for all {@link Configuration} providers.
 * 
 * @author Sparsity Technologies
 * 
 */
public interface ConfigurationProvider {

    /**
     * Sets the {@link Configuration} instance to be used.
     * <p>
     * The provider will update the given instance.
     * 
     * @param config
     */
    public void set(Configuration config);

    /**
     * Loads the given {@link Configuration} instance.
     * 
     * @throws DexUtilsException
     *             if there is an error.
     */
    public void load() throws DexUtilsException;
}
