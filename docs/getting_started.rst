Getting started with Substeps
=============================

Substeps is not particularly difficult to set up manually, however the quickest route to learning how to use Substeps is to download the sample maven project and then tailor that to your needs.  See below on things you will need to configure for your own projects.

Sample project
--------------
Some details to go here


Manual Setup
------------

1. Create an 'acceptance-test' project.
2. Download the jars from this page, you will need substeps-core as a minimum.
   
   and in addition:
      - substeps-runner if you wish to execute your tests as part of a maven build.
      - webdriver-substeps if you wish to run acceptance tests against a web application (based on webdriver).
      - see related projects for other libraries and utilities.

3. Create a directory on the classpath to contain your feature files or sub-directories of feature files (eg ``src/test/resources/features``).
4. Create a directory on the classpath to contain substep files (eg ``src/test/resources/substeps``).
5. Create a junit class to run the tests in ‘development’ mode:

   .. code-block:: java
   
      @SubStepsConfiguration(featureFile = "./target/test-classes/features", 
                             subStepsFile = "./target/test-classes/substeps", 
                             stepImplementations = {BaseWebdriverSubStepImplementations.class },
                              beforeAndAfterImplementations ={DefaultExecutionSetupTearDown.class})
      @RunWith(JunitFeatureRunner.class)
      public class AcceptanceTestsRunner {
          // no op
      }

   If you have bespoke step implementations then add to the list of step implementations above, 
   likewise if you have specific setup and tear down functionality add this to the 
   ``beforeAndAfterImplementations`` parameter.  
   Setup and tear down methods will need to have the appropriate annotation, eg ``@BeforeAllFeatures`` etc
   to signify when in the lifecycle to call that method. 
   See the javadoc for com.technophobia.substeps.runner.setupteardown.Annotations for more details.
   
   Additional properties can also be specified see <junitFeatureRunner details> for more details

6. create a configuration file ``localhost.properties`` and place in the classpath (``src/test/resources``).
   This property file will contain all of the environment settings for substeps.  
   Substep libraries might each include their own set of default properties, 
   the values in this file will override the defaults.

7. To run the tests:
   Simply run the class as a Junit test; in Eclipse select the ‘AcceptanceTestsRunner’ class, right click 
   and select Run As, junit test.

8. Continuous integration (using Maven)
   There is a substeps maven plugin which can execute a number of configurations as part of an integration test.
   Each configuration can be tailored to use specific properties, sets of tests or alternate 
   initialisation classes and or step implementations.  
   See #Substeps Maven Plugin for details of the parameters and options available.

