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

/**
 * General purpose exception.
 * 
 * @author Sparsity Technologies
 * 
 */
public class DexUtilsException extends RuntimeException {

    /**
     * Creates a new instance.
     */
    public DexUtilsException() {
        super();
    }

    /**
     * Creates a new instance.
     * 
     * @param s
     *            The detail message.
     */
    public DexUtilsException(String s) {
        super(s);
    }

    /**
     * Creates a new instance.
     * 
     * @param s
     *            The detail message.
     * @param cause
     *            The cause (it could be <code>null</code>).
     */
    public DexUtilsException(String s, Throwable cause) {
        super(s, cause);
    }
}
