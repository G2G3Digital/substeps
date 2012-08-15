Extending and Customising Substeps
==================================

Substeps is designed to be extensible, many projects are subtly different and the step implementations 
required to test the application also have to vary.  Substeps can easily be extended or customised using new step implementations, 
either as simple classes, or packaged inside a jar as a library.

.. Note::
   This section is aimed at developers, implementing new steps or libraries of step implementations.

.. contents::

Step Implementations
--------------------
Custom step implementations can be implemented by annotating a class with ``@StepImplementations`` and 
including the class in the run configurations described above.

If the step implementation class requires specific setup and tear down, use the ``requiredInitialisationClasses`` 
attribute on the ``@StepImplementations`` annotation to list, in order, the initialisation classes.
Setup and tear down methods will need to have the appropriate annotation, eg ``@BeforeAllFeatures`` etc
to signify when in the lifecycle to call that method. 
See the javadoc for com.technophobia.substeps.runner.setupteardown.Annotations for more details.

  
Each step implemenation method should be annotated with ``@Step("<expression>")`` where <expression> 
is a java regular expression that is used to match on the strings from feature and substep files.
Capture groups can be used to map to parameters eg:

.. code-block:: java
   
   @StepImplementations (requiredInitialisationClasses=MySetupAndTearDown.class)
   public MyStepImplementations {

      @Step("DoSomething name=\"([^\"]*)\", value=\"([^\"]*)\"")
      public void doSomething(final String name, final String value){
      ...

Parameters can be converted to other simple types using the @StepParameter annotation and 
an appropriate Converter, eg:

.. code-block:: java

   @Step("DoSomethingElse checked=\"([^\"]*)\"")
   public void doSomethingElse( @StepParameter(converter = BooleanConverter.class) final Boolean checked) {
      ...
   
The actual implementation can do whatever is necessary, if you need to use functionality from 
other StepImplementations classes or methods, it is possible to simply instantiate the class 
and call directly.

Context
-------

An ExecutionContext is maintained throughout the testing cycle, bound to the current Thread.  
This allows data to be placed into a scoped context for subsequent use, scopes include suite, 
feature, scenario, etc. See Scope javadocs for more details.  At the end of the scope, the 
framework will remove the objects in that scope.  This functionality is useful for passing 
data between steps for example, eg one step checks that a particular function worked and 
returned a value, a subsequent step can then use that value.  
See ExecutionContext javadocs for more details

Usage:

.. code-block:: java

   ExecutionContext.put(Scope.SCENARIO, “new_user_id”, user_id);
   Object object = ExecutionContext.get(Scope.SCENARIO, “new_user_id”);

Properties
----------

It is possible a new library of substep implementations will need to make use of various 
environment specific properties.  To avoid a profusion of properties files that need to be 
maintained for each environment and substeps libraries, a single mechanism is used.  
By default localhost.properties is used, however this can be overriden by 
specifying -Denvironment=test_server for example to use test_server.properties instead.

If you are creating a new substep implementation library and require new configuration, 
create an enum to hold your configuration values and initialise in a static initialiser:

.. code-block:: java

   public enum MyNewConfiguration{
       private static final int MAX_COUNT;
       ...
       static {
         final URL defaultURL = MyNewConfiguration.class.getResource("/default-my-new-substeps-library.properties");
         Assert.assertNotNull(defaultURL);
          
         Configuration.INSTANCE.addDefaultProperties(defaultURL, "default-my-new-substeps-library");       
         
         MAX_COUNT = Configuration.INSTANCE.getInt("new.library.max.count");
         ...
         }

Default values can be provided in the specified properties file packaged with the 
library and overridden in the same substeps property file used for other settings.

Documentation
-------------
A fundamental part of writing new step implementations is the documentation that 
provides understanding to users of the new implementations, without understanding, usage might 
not be as efficient or correct as you, the implementor would like.  The substeps framework 
provides some help in this area, simply use the following annotations within the javadoc 
for the method eg.

.. code-block:: java

   /**
    * Asserts a value of a radio button
    *
    * @example AssertRadioButton name="radio_btn_name", text="text",
    *          checked="true"
    * @section Forms
    * @param name
    *            the name
    * @param text
    *            text value
    * @param checked
    *            true or false to indicate wether the checkbox is checked or
    *            not
    */
    @Step("AssertRadioButton name=\"([^\"]*)\", text=\"([^\"]*)\", checked=\"([^\"]*)\"")
    public void assertRadioButton ....

The custom javadoc tags ``@example``, ``@section``, along with the standard description and parameter names 
are parsed by a bespoke javadoc doclet and can be extracted for 
publishing in a wiki or within the context sensitive help of the eclipse plugin.

see substep implementation publisher

If you are writing a set of step implementations that you wish to distribute, then 
it’s a good idea to include the susbteps-meta-inf.xml in the jar file that you distribute.  
This will enable the ecplipse plugin and maven publisher plugins to have access to the 
custom javadoc annotations and parameter names, which in turn will help users of your library.