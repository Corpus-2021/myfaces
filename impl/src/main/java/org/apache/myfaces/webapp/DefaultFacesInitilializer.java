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
package org.apache.myfaces.webapp;

import java.util.logging.Level;
import java.util.logging.Logger;
import jakarta.el.ExpressionFactory;
import jakarta.faces.FacesException;
import jakarta.faces.context.ExternalContext;
import jakarta.servlet.ServletContext;

/**
 * This initializer initializes only Facelets. Specially checks for
 * org.apache.myfaces.EXPRESSION_FACTORY parameter.
 * 
 * @author Martin Koci
 */
public class DefaultFacesInitilializer extends org.apache.myfaces.webapp.AbstractFacesInitializer
{
    private static final Logger log = Logger.getLogger(DefaultFacesInitilializer.class.getName());

    @Override
    protected void initContainerIntegration(ServletContext servletContext, ExternalContext externalContext)
    {
        ExpressionFactory expressionFactory = getUserDefinedExpressionFactory(externalContext);
        if (expressionFactory == null)
        {
            String[] candidates = new String[] { "org.apache.el.ExpressionFactoryImpl",
                "com.sun.el.ExpressionFactoryImpl", "de.odysseus.el.ExpressionFactoryImpl",
                "org.jboss.el.ExpressionFactoryImpl", "com.caucho.el.ExpressionFactoryImpl" };
            
            for (String candidate : candidates)
            {
                expressionFactory = loadExpressionFactory(candidate, false);
                if (expressionFactory != null)
                {
                    if (log.isLoggable(Level.FINE))
                    {
                        log.fine(ExpressionFactory.class.getName() + " implementation found: " + candidate);
                    }
                    break;
                }
            }
        }

        if (expressionFactory == null)
        {
            throw new FacesException("No " + ExpressionFactory.class.getName() + " implementation found. "
                    + "Please provide <context-param> in web.xml: org.apache.myfaces.EXPRESSION_FACTORY");
        }
        
        buildConfiguration(servletContext, externalContext, expressionFactory);
    }

}
