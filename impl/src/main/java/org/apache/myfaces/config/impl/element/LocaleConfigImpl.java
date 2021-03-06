/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.apache.myfaces.config.impl.element;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author <a href="mailto:oliver@rossmueller.com">Oliver Rossmueller</a>
 */
public class LocaleConfigImpl extends org.apache.myfaces.config.element.LocaleConfig implements Serializable
{
    private String defaultLocale;
    private List<String> supportedLocales;

    public void setDefaultLocale(String defaultLocale)
    {
        this.defaultLocale = defaultLocale;
    }

    @Override
    public void addSupportedLocale(String locale)
    {
        if (supportedLocales == null)
        {
            supportedLocales = new ArrayList<>();
        }
        supportedLocales.add(locale);
    }

    @Override
    public String getDefaultLocale()
    {
        return defaultLocale;
    }

    @Override
    public List<String> getSupportedLocales()
    {
        if (supportedLocales == null)
        {
            return Collections.emptyList();
        }
        return supportedLocales;
    }
}
