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
package org.apache.myfaces.spi.impl;

import jakarta.faces.context.ExternalContext;
import org.apache.myfaces.cdi.view.CDIViewScopeProviderImpl;
import org.apache.myfaces.spi.ViewScopeProvider;
import org.apache.myfaces.spi.ViewScopeProviderFactory;
import org.apache.myfaces.util.ExternalSpecifications;

/**
 *
 * @author Leonardo Uribe
 */
public class DefaultViewScopeProviderFactory extends ViewScopeProviderFactory
{
    
    public static final String VIEW_SCOPE_HANDLER = ViewScopeProvider.class.getName();
    public static final String VIEW_SCOPE_HANDLER_INSTANCE_KEY = VIEW_SCOPE_HANDLER + ".INSTANCE";

    @Override
    public ViewScopeProvider getViewScopeHandler(ExternalContext externalContext)
    {
        // check for cached instance
        ViewScopeProvider returnValue = (ViewScopeProvider)
                externalContext.getApplicationMap().get(VIEW_SCOPE_HANDLER_INSTANCE_KEY);

        if (returnValue == null)
        {
            if (ExternalSpecifications.isCDIAvailable(externalContext))
            {
                returnValue = new CDIViewScopeProviderImpl();
            }

            // cache the result on the ApplicationMap
            externalContext.getApplicationMap().put(VIEW_SCOPE_HANDLER_INSTANCE_KEY, returnValue);
        }

        return returnValue;
    }

    @Override
    public void setViewScopeHandler(ExternalContext externalContext, ViewScopeProvider viewScopeHandler)
    {
        externalContext.getApplicationMap().put(VIEW_SCOPE_HANDLER_INSTANCE_KEY, viewScopeHandler);
    }
}
