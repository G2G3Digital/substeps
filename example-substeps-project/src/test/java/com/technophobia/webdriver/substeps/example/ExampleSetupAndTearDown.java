/*
 *	Copyright Technophobia Ltd 2012
 *
 *   This file is part of Substeps.
 *
 *    Substeps is free software: you can redistribute it and/or modify
 *    it under the terms of the GNU Lesser General Public License as published by
 *    the Free Software Foundation, either version 3 of the License, or
 *    (at your option) any later version.
 *
 *    Substeps is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *    GNU Lesser General Public License for more details.
 *
 *    You should have received a copy of the GNU Lesser General Public License
 *    along with Substeps.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.technophobia.webdriver.substeps.example;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.technophobia.substeps.runner.setupteardown.Annotations.AfterEveryScenario;
import com.technophobia.substeps.runner.setupteardown.Annotations.BeforeAllFeatures;
import com.technophobia.substeps.runner.setupteardown.Annotations.BeforeEveryScenario;

/**
 * 
 * A class with examples of the setup and tear down methods
 * @author ian
 *
 */
public class ExampleSetupAndTearDown {

    private static final Logger logger = LoggerFactory.getLogger(ExampleSetupAndTearDown.class);

    /**
     * An example setup method that is called prior to running any tests within an execution configuration.  
     * 
     * Other setup points are (in order):
     * 
     * BeforeAllFeatures
     * BeforeEveryFeature
     * BeforeEveryScenario
     * AfterEveryScenario
     * AfterEveryFeature
     * AfterAllFeatures
     * 
     * Set up and tear down methods are symetric in the order in which they are invoked and respect class hierarchy, 
     * ie for setup an equivalent parent class setup method will be called before a child.  The reverse is true for tear down
     * where the child methods are called first.
     */
    @BeforeAllFeatures
    public void beforeAllTestsSetup() {

        logger.info("beforeAllTestsSetup");
        
        
    }


    @BeforeEveryScenario
    public void initialiseTestFramework() {

        logger.info("beforeEveryScenario");

    }


    @AfterEveryScenario
    public void afterTestRun() {

        logger.info("afterEveryScenario");
    }
}
