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

import java.io.File;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.sparsity.dex.etl.DexUtilsException;
import com.sparsity.dex.etl.config.ConfigurationProvider;
import com.sparsity.dex.etl.config.bean.Configuration;
import com.sparsity.dex.etl.config.bean.DatabaseConfiguration;

/**
 * Loads a {@link Configuration} from an XML file.
 * 
 * @author Sparsity Technologies
 * 
 */
public class XMLConfigurationProvider implements ConfigurationProvider {
    /**
     * {@link Log} instance.
     */
    private static Log log = LogFactory.getLog(XMLConfigurationProvider.class);

    /**
     * XML handler to load a {@link Configuration} from a XML file.
     * 
     * @author Sparsity Technologies
     * 
     */
    public class DexUtilsHandler extends DefaultHandler {

        /**
         * {@link Configuration} instance to be used.
         */
        private Configuration conf;

        /**
         * Default {@link DatabaseConfiguration} alias.
         */
        private String defaultDatabase;

        /**
         * Creates a new instance.
         * 
         * @param conf
         *            {@link Configuration} instance to be used.
         */
        public DexUtilsHandler(Configuration conf) {
            this.conf = conf;
        }

        @Override
        public void startDocument() throws SAXException {
            log.debug("Start document.");
        }

        @Override
        public void endDocument() throws SAXException {
            if (conf.getDefaultDatabase() == null) {
                throw new DexUtilsException("There is no default database.");
            }
            log.debug("End document.");
        }

        @Override
        public void startElement(String uri, String localName, String qName,
                Attributes attrs) throws SAXException {

            if (qName.equals("config")) {

                log.debug("Processing 'config' xml tag.");

            } else if (qName.equals("databases")) {

                log.debug("Processing 'databases' xml tag.");
                defaultDatabase = attrs.getValue("default");

            } else if (qName.equals("database")) {

                log.debug("Processing 'database' xml tag.");
                DatabaseConfiguration dbConfig = new DatabaseConfiguration();
                String name = attrs.getValue("name");
                String alias = attrs.getValue("alias");
                String path = attrs.getValue("path");
                if (name == null || alias == null || path == null) {
                    throw new DexUtilsException(
                            "'name', 'alias' and 'path' attribute are required for 'database' xml tag.");
                }
                dbConfig.setName(name);
                dbConfig.setAlias(alias);
                dbConfig.setPath(path);
                String dexConf = attrs.getValue("conf");
                if (dexConf != null) {
                    dbConfig.setDexConfiguration(dexConf);
                }
                conf.addDatabaseConfiguration(dbConfig);
                if (name.equals(defaultDatabase)) {
                    conf.setDefaultDatabase(dbConfig);
                }

            } else {
                throw new DexUtilsException("Unexpected xml tag '" + qName
                        + "'.");
            }
        }

        @Override
        public void endElement(String uri, String localName, String qName)
                throws SAXException {
        }
    }

    /**
     * {@link Configuration} instance.
     */
    private Configuration config = null;

    /**
     * XML file.
     */
    private File xml;

    /**
     * Creates a new instance.
     */
    public XMLConfigurationProvider() {
    }

    /**
     * Sets the {@link File} to be loaded.
     * 
     * @param f
     *            {@link File} to be loaded.
     */
    public void setFile(File f) {
        xml = f;
    }

    public void set(Configuration config) {
        this.config = config;
    }

    public void load() throws DexUtilsException {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        factory.setValidating(true);
        factory.setNamespaceAware(true);

        try {
            SAXParser parser = factory.newSAXParser();
            DexUtilsHandler handler = new DexUtilsHandler(config);
            parser.parse(xml.getAbsolutePath(), handler);
        } catch (Exception e) {
            String msg = new String("Parsing error.");
            log.error(msg, e);
            throw new DexUtilsException(msg, e);
        }
        log.info("Loaded configuration from " + xml.getAbsolutePath());
    }
}
