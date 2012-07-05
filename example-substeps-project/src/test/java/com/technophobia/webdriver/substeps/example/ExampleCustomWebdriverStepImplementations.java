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

import java.util.List;
import java.util.Map;

import com.technophobia.substeps.model.SubSteps.Step;
import com.technophobia.substeps.model.SubSteps.StepImplementations;
import com.technophobia.substeps.model.SubSteps.StepParameter;
import com.technophobia.substeps.model.parameter.IntegerConverter;

/**
 * An Example of a class providing custom substep implementations.
 * 
 * @author ian
 *
 */
@StepImplementations
public class ExampleCustomWebdriverStepImplementations {

	/**
	 * An example substep implementation 
	 * 
	 * The commented annotations are used to process the javadocs to generate help and content regarding the step implementations used.
	 * 
	 * @example DoSomething with a parameter "fred"
	 * @section Custom
	 * @param param
	 */
    @Step("DoSomething with parameter \"([^\"]*)\"")
    public void exampleOne(final String param)  {
    	
    	// TODO - do something
    	
	/**
	 * perform whatever you need to..
	 * 
	 * Other StepImplementation classes can just be instantiated if you wish to re-use functionality, eg.
	 * 
	 * final FinderWebDriverSubStepImplementations finder = new FinderWebDriverSubStepImplementations();
	 * finder.findById("some_id");
	 * 
	 * The Webdriver instance is available via a static method:
	 * 
	 * com.technophobia.webdriver.substeps.runner.DefaultExecutionSetupTearDown.getThreadLocalWebDriver()
	 * 
	 * and the WebdriverContext is also available via another static method on the same class:
	 * 
	 * getThreadLocalWebDriverContext()
	 * 
	 * which provides access to the currentElement.
	 * 
	 * for neat code, statically import these methods, eg:
	 * 
	 * import static com.technophobia.webdriver.substeps.runner.DefaultExecutionSetupTearDown.getThreadLocalWebDriver;
	 * 
	 */
    }

	/**
	 * Another example substep implementation 
	 * 
	 * @example DoSomething with an int parameter "fred"
	 * @section Custom
	 * @param param
	 */
    @Step("DoSomething with an int parameter \"([^\"]*)\"")
    public void exampleTwo(@StepParameter(converter = IntegerConverter.class) final int param)  {
    	
    	/**
    	 * Parameters can be conveniently converted to other types, as above.  Other Converters are available.
    	 */
    	
    	// TODO - do something with the int parameter
    }
    
    
	/**
	 * Yet another example substep implementation 
	 * 
	 * @example DoSomething with a table parameter
	 * 				|col1     | col2    |  col3   |
	 * 				|row1-1   | row1-2  |  row1-3 |
	 *              |row2-1   | row2-2  |  row3-3 |
	 * @section Custom
	 * @param param
	 */
    @Step("DoSomething with a table parameter")
    public void exampleThree(final List<Map<String,String>> table)  {
    	
    	/**
    	 * Tables of data can be passed directly from a scenario into a step implementation.
    	 * Each element in the list represents a row of data, keyed by column name.
    	 * In the example above
    	 * 
    	 * table.get(0).get("col1") would return "row1-1"
    	 * 
    	 * NB. At present it is not possible to pass a table of data from a scenario step into a substep.
    	 */
    	
    	// TODO - do something with the table parameter
    }

}
