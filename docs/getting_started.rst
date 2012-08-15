Getting started with Substeps
=============================

Substeps is not particularly difficult to set up manually, however the quickest route to learning how to use Substeps is to download the sample maven project and then tailor that to your needs.  See below on things you will need to configure for your own projects.

Sample project
--------------
The sample project can be downloaded from `here <https://github.com/downloads/technophobia/substeps/example-substeps-project-0.0.3.zip>`_ which contains all of the required configuration and some sample code to get you started. 


Manual Setup (Maven)
--------------------
These instructions are for building a maven based project.

1. Create an 'acceptance-test' project.
2. Include the substeps dependencies
    
    If you wish to run acceptance tests against a web application using selenium webdriver:
    
    .. code-block:: xml
    
        <dependency>
            <groupId>com.technophobia.substeps</groupId>
            <artifactId>webdriver-substeps</artifactId>
            <version>0.0.5</version>
        </dependency>
    
    The above dependency will also include substeps-core, however if you wish to create your own or use other step implementations then include

    .. code-block:: xml
    
        <dependency>
            <groupId>com.technophobia.substeps</groupId>
            <artifactId>substeps-core</artifactId>
            <version>0.0.7</version>
        </dependency>

    Check `Maven Central <http://search.maven.org/#search|ga|1|com.technophobia.substeps>`_ for the latest versions of these libraries.

3. Create a directory on the classpath to contain your feature files or sub-directories of feature files (eg ``src/test/resources/features``).
4. Create a directory on the classpath to contain substep files (eg ``src/test/resources/substeps``).
5. Create a junit class to run the tests in ‘development’ mode:

   .. code-block:: java
   
      @SubStepsConfiguration(featureFile = "./target/test-classes/features", 
                             subStepsFile = "./target/test-classes/substeps", 
                             stepImplementations = {BaseWebdriverSubStepImplementations.class })
      @RunWith(JunitFeatureRunner.class)
      public class AcceptanceTestsRunner {
          // no op
      }

   If you have bespoke step implementations then add to the list of step implementations above.  
      
   Additional properties can also be specified see <junitFeatureRunner details> for more details

6. create a configuration file ``localhost.properties`` and place in the classpath (``src/test/resources``).
   This property file will contain all of the environment settings for substeps.  
   Substep libraries might each include their own set of default properties, 
   the values in this file will override the defaults.

7. To run the tests:
   Simply run the class as a Junit test; in Eclipse select the ‘AcceptanceTestsRunner’ class, right click 
   and select Run As, junit test.

8. Continuous integration (using Maven)
   
   The substeps maven plugin can execute a number of configurations as part of an integration test.
   Each configuration can be tailored to use specific properties, sets of tests or alternate 
   initialisation classes and or step implementations.  The plugin adds all the dependencies of the project 
   to it's own set of dependencies to avoid duplication in the pom.
   
   .. code-block:: xml
   
        <plugin>
            <groupId>com.technophobia.substeps</groupId>
            <artifactId>substeps-runner</artifactId>
            <version>0.0.6</version>
    
            <executions>
                <execution>
                    <id>SubSteps Test</id>
                    <phase>integration-test</phase>
                    <goals>
                        <goal>run-features</goal>
                    </goals>
                </execution>
            </executions>
            <configuration>
            ...
   
     
   See #Substeps Maven Plugin for full details of the parameters and options available.
   
9. An Example pom    
   
   This example pom is taken from the sample project, feel free to copy and adapt.
   
   .. code-block:: xml
   
        <?xml version="1.0" encoding="UTF-8"?>
        <project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
            xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/maven-v4_0_0.xsd">
            <modelVersion>4.0.0</modelVersion>
        
            <!-- This is a sample pom configuration for building a suite of substep 
                acceptance tests based on the webdriver step implementations -->
        
            <!-- Change these values to something appropriate for your organisation 
                and project -->
            <groupId>com.technophobia.substeps</groupId>
            <artifactId>webdriver-substeps-example</artifactId>
            <version>0.0.1-SNAPSHOT</version>
        
            <packaging>jar</packaging>
            <name>Webdriver Substeps Example</name>
            <description>An example webdriver substeps project</description>
        
            <licenses>
                <license>
                    <name>LGPL 3.0 license</name>
                    <url>http://www.opensource.org/licenses/lgpl-3.0.html</url>
                    <distribution>manual</distribution>
                </license>
            </licenses>
        
        
            <properties>
                <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
                <project.reporting.outputEncoding>UTF-8</project.reporting.outputEncoding>
                <selenium.version>2.25.0</selenium.version>
                <hamcrest.version>1.3.RC2</hamcrest.version>
                <junit.version>4.10</junit.version>
        
                <substeps.runner.version>0.0.6</substeps.runner.version>
                <webdriver.substeps.version>0.0.5</webdriver.substeps.version>
        
            </properties>
        
            <dependencies>
        
                <!-- webdriver-substeps will also include substeps-core as a dependency -->
                <dependency>
                    <groupId>com.technophobia.substeps</groupId>
                    <artifactId>webdriver-substeps</artifactId>
                    <version>${webdriver.substeps.version}</version>
                </dependency>
        
                <dependency>
                    <groupId>org.seleniumhq.selenium</groupId>
                    <artifactId>selenium-java</artifactId>
                    <version>${selenium.version}</version>
                </dependency>
        
            </dependencies>
        
            <profiles>
        
                <!-- In this example, the running of the acceptance tests is defined within 
                    a profile, during the integration-test phase of the maven build.
                    See the substeps-runner documentation for more details. -->
        
                <profile>
                    <id>acceptance-tests</id>
                    <activation>
                        <activeByDefault>true</activeByDefault>
                    </activation>
                    <build>
                        <plugins>
                            <plugin>
                                <groupId>com.technophobia.substeps</groupId>
                                <artifactId>substeps-runner</artifactId>
                                <version>${substeps.runner.version}</version>
        
                                <executions>
                                    <execution>
                                        <id>SubSteps Test</id>
                                        <phase>integration-test</phase>
                                        <goals>
                                            <goal>run-features</goal>
                                        </goals>
                                    </execution>
                                </executions>
        
                                <configuration>
        
                                    <executionConfigs>
        
                                        <!-- One or more 'execution configurations', use multiple configs for different 
                                        test initialisation, phases of project, etc -->
        
                                        <executionConfig>

                                            <!-- This appears at the root of the results report -->
                                            <description>Self Test Features</description> 
                                                
                                            <!-- optional - If the feature or scenario has this tag, then it will be 
                                                included, otherwise it won't -->
                                            <tags>@all</tags>  

                                            <!-- optional - if true any parse errors will fail the build immediately, 
                                                rather than attempting to execute as much as possible and fail those tests 
                                                that can't be parsed -->
                                            <fastFailParseErrors>false</fastFailParseErrors> 

                                            <!-- path to the feature file, or directory containing the feature files -->
                                            <featureFile>${basedir}/target/test-classes/features</featureFile> 
                                            
                                            <!-- path to directory of substep files, or a single substep file -->
                                            <subStepsFileName>${basedir}/target/test-classes/substeps</subStepsFileName> 
        
                                            <!-- List of classes containing step implementations -->
                                            <stepImplementationClassNames>
                                                <param>com.technophobia.webdriver.substeps.impl.BaseWebdriverSubStepImplementations</param>
                                                <param>com.technophobia.webdriver.substeps.example.ExampleCustomWebdriverStepImplementations</param>
                                            </stepImplementationClassNames>
        
                                        </executionConfig>
                                    </executionConfigs>
        
                                    <!-- Default HTML report builder -->
                                    <executionReportBuilder
                                        implementation="com.technophobia.substeps.report.DefaultExecutionReportBuilder">
        
                                        <!-- The directory in which the test execution report will be written 
                                            to -->
                                        <outputDirectory>${project.build.testOutputDirectory}</outputDirectory>
                                    </executionReportBuilder>
        
                                </configuration>
                                <dependencies>
        
                                    <!-- NB. The plugin uses all test dependencies defined in this project, 
                                        as it's own so there is no need to list separately. The exception is an slf4j 
                                        logging implementation, which is required before other dependencies have 
                                        been added. This logger is included as an example, it can be replaced with 
                                        another slf4j logger of your choice. -->
        
                                    <dependency>
                                        <groupId>org.slf4j</groupId>
                                        <artifactId>slf4j-log4j12</artifactId>
                                        <version>1.6.4</version>
                                    </dependency>
        
                                </dependencies>
                            </plugin>
                        </plugins>
                    </build>
                </profile>
        
            </profiles>
        
            <!-- this section may be unnecessary depending on your maven configuration -->
            <build>
                <finalName>${project.artifactId}</finalName>
                <plugins>
        
                    <plugin>
                        <groupId>org.apache.maven.plugins</groupId>
                        <artifactId>maven-compiler-plugin</artifactId>
                        <configuration>
                            <source>1.5</source>
                            <target>1.5</target>
                            <inherit>true</inherit>
                            <encoding>${project.build.sourceEncoding}</encoding>
                        </configuration>
                    </plugin>
                </plugins>
            </build>
        
        </project>   

