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

import org.junit.runner.RunWith;

import com.technophobia.substeps.runner.JunitFeatureRunner;
import com.technophobia.substeps.runner.JunitFeatureRunner.SubStepsConfiguration;
import com.technophobia.webdriver.substeps.impl.BaseWebdriverSubStepImplementations;

/**
 * A class which can be run as a junit test inside an IDE. Useful for writing
 * debugging tests. Use the maven runner plugin for continuous integration as
 * that provides better reporting.
 * 
 * Tags can be space separated and a feature or scenario will need all of the
 * specified tags in order to be included.
 * 
 * @author imoore
 */
@SubStepsConfiguration(featureFile = "./target/test-classes/features", subStepsFile = "./target/test-classes/substeps", stepImplementations = {
        BaseWebdriverSubStepImplementations.class,
        ExampleCustomWebdriverStepImplementations.class }, tagList = "@non-visual")
@RunWith(JunitFeatureRunner.class)
public class FeatureFileRunner {
    // no op
}
