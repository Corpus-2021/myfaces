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
package org.apache.myfaces.view.jsp;

import java.util.LinkedList;
import java.util.StringTokenizer;
import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewDeclarationLanguage;
import org.apache.myfaces.config.MyfacesConfig;

import org.apache.myfaces.view.ViewDeclarationLanguageStrategy;

/**
 * @author Simon Lessard (latest modification by $Author$)
 * @version $Revision$ $Date$
 *
 * @since 2.0
 */
public class JspViewDeclarationLanguageStrategy implements ViewDeclarationLanguageStrategy
{
    private ViewDeclarationLanguage _language;
    private LinkedList<String> _suffixes;
    
    public JspViewDeclarationLanguageStrategy()
    {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        
        _suffixes = loadSuffixes (facesContext.getExternalContext());
        _language = new JspViewDeclarationLanguage(facesContext, this, _suffixes);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public ViewDeclarationLanguage getViewDeclarationLanguage()
    {
        return _language;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean handles(String viewId)
    {
        return true;
    }
    
    static LinkedList<String> loadSuffixes (ExternalContext context) 
    {
        LinkedList<String> result = new LinkedList<String>();
        String definedSuffixes = MyfacesConfig.getCurrentInstance(context).getJspSuffix();

        // This is a space-separated list of suffixes, so parse them out.
        StringTokenizer tokenizer = new StringTokenizer (definedSuffixes, " ");
        
        while (tokenizer.hasMoreTokens()) 
        {
            result.add (tokenizer.nextToken());
        }
        
        return result;
    }

    @Override
    public String getMinimalImplicitOutcome(String viewId)
    {
        for (String suffix : _suffixes) 
        {
            if (viewId != null && viewId.endsWith(suffix))
            {
                return viewId.substring(0, viewId.length()-suffix.length());
            }
        }
        return viewId;
    }
}
