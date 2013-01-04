Getting started with Substeps
=============================

Substeps is not particularly difficult to set up manually, however the quickest route to learning how to use Substeps is to download the sample maven project and then tailor that to your needs.  See below on things you will need to configure for your own projects.

Sample project
--------------
The sample project can be downloaded from `here <https://github.com/downloads/technophobia/substeps/example-substeps-project-1.0.1.zip>`_ which contains all of the required configuration and some sample code to get you started. 


Manual setup (Ant)
------------------
These instructionsa are for building an Ant-based project.

1. Create a project structure of your choice
2. Import the substeps namespace into the build.xml
3. Define an Ant taskdef to allow usage of the custom Ant task
4. Add a target to run your SubSteps configuration
5. Create a directory on the classpath to contain your feature files or sub-directories of feature files (eg ``src/test/resources/features``).
6. Create a directory on the classpath to contain substep files (eg ``src/test/resources/substeps``).
7. Create a junit class to run the tests in ‘development’ mode:

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

8. create a configuration file ``localhost.properties`` and place in the classpath (``src/test/resources``).
   This property file will contain all of the environment settings for substeps.  
   Substep libraries might each include their own set of default properties, 
   the values in this file will override the defaults.

9. To run the tests:
   Simply run the class as a Junit test; in Eclipse select the ‘AcceptanceTestsRunner’ class, right click 
   and select Run As, junit test.

10. An example build.xml

   .. code-block:: xml


	<project name="MyProject" default="run-substeps" basedir="." xmlns:substeps="antlib:com.technophobia.substeps">
		<description>
		Simple example build file to demonstrate using SubSteps with ANT
		</description>

		<property name="src" location="src/main/java" />
		<property name="build" location="build" />

		<path id="cp">
			<pathelement location="${user.home}/.m2/repository/com/technophobia/substeps/substeps-core/1.0.1/substeps-core-1.0.1.jar" />
			<pathelement location="${user.home}/.m2/repository/ch/qos/logback/logback-classic/1.0.6/logback-classic-1.0.6.jar" />
			<pathelement location="${user.home}/.m2/repository/ch/qos/logback/logback-core/1.0.6/logback-core-1.0.6.jar" />
			<pathelement location="${build}" />
		</path>

		<target name="clean">
			<delete dir="${build}"/>
		</target>

		<target name="compile" depends="init">
			<javac srcdir="${src}" destdir="${build}" classpathref="cp" />
		</target>

		<target name="init">
			<mkdir dir="${build}"/>
		</target>

		<target name="run-substeps" depends="compile">
		    <taskdef uri="antlib:com.technophobia.substeps"
		        resource="substeps.xml"
		        classpathref="cp" />

		        <substeps:substeps>
		                <substeps:executionConfig featureFile="${basedir}/features" subStepsFileName="${basedir}/substeps">
		                        <substeps:stepImplementationClassNames>
		                                <substeps:param>com.technophobia.example.Steps</substeps:param>
		                        </substeps:stepImplementationClassNames>

		                        <substeps:initialisationClass>
		                                <substeps:param>com.technophobia.example.Init</substeps:param>
		                        </substeps:initialisationClass>
		                </substeps:executionConfig>
		        </substeps:substeps>
		</target>
	</project>


Manual Setup (Maven)
--------------------
These instructions are for building a maven based project.

1. Create an 'acceptance-test' project.
2. Include the substeps dependencies
   
    Firstly add a dependency on the substeps bill of materials (bom)

    .. code-block:: xml
   
        <dependency>
          <groupId>com.technophobia.substeps</groupId>
          <artifactId>substeps-bom</artifactId>
          <version>1.1.0</version>
          <type>pom</type>
          <scope>test</scope>
        </dependency>

    This will include substeps core as well as compatible versions of substeps runners. 
 
    If you wish to run acceptance tests against a web application using selenium webdriver you'll need to add a further dependency:
    
    .. code-block:: xml
    
        <dependency>
            <groupId>com.technophobia.substeps</groupId>
            <artifactId>webdriver-substeps</artifactId>
            <version>1.1.0</version>
            <scope>test</scope>
        </dependency>
    
    If you wish to create your own or use other step implementations then include them as further test dependencies.

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
            <!-- Note there is no need to explicitly specify the version number if you have imported the bom as shown above. -->
    
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
   
     
   See the `Substeps Maven Site <http://technophobia.github.com/substeps-runner/>` for full details of the parameters and options available.
   
9. An Example pom    
   
   This example pom is adapted from the sample project, feel free to copy.
   
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
		<version>1.1.0</version>

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
			
			<substeps.bom.version>1.1.0-SNAPSHOT</substeps.bom.version>
			<substeps.webdriver.version>1.1.0-SNAPSHOT</substeps.webdriver.version>
		</properties>

		<dependencies>

	       <dependency>
		    <groupId>javax.activation</groupId>
		    <artifactId>activation</artifactId>
		    <version>1.1.1</version>
		</dependency>

			<!-- Adding a dependency on the substeps bom will bring in the substeps 
				core and everything else needed to build and run -->

			<dependency>
				<groupId>com.technophobia.substeps</groupId>
				<artifactId>substeps-bom</artifactId>
				<version>${substeps.bom.version}</version>
				<type>pom</type>
				<scope>test</scope>
			</dependency>

			<!-- You'll also need dependencies on any step implementations you're going 
				to use -->

			<dependency>
				<groupId>com.technophobia.substeps</groupId>
				<artifactId>webdriver-substeps</artifactId>
				<version>${substeps.webdriver.version}</version>
				<scope>test</scope>
			</dependency>
		
		</dependencies>

		<profiles>

			<!-- In this example, the running of the acceptance tests is defined within 
				a profile, during the integration-test phase of the maven build. See the 
				substeps-runner documentation for more details. -->

			<profile>
				<id>acceptance-tests</id>
				<activation>
					<activeByDefault>true</activeByDefault>
				</activation>
				<build>
					<plugins>
						<plugin>
							<groupId>com.technophobia.substeps</groupId>
							<artifactId>substeps-maven-plugin</artifactId>

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

                                                                <!-- Since 1.1.0 substeps executes tests in a forked jvm by default, set this parameter to false to instead execute within the jvm running maven  -->
								<runTestsInForkedVM>true</runTestsInForkedVM>

                                                                <!-- If executing in a forked jvm, the substeps runner will start that jvm with any arguments specified in the vmArgs parameter -->
                                                                <vmArgs>-Xmx1024m</vmArgs>

								<executionConfigs>

									<!-- One or more 'execution configurations', use multiple configs 
										for different test initialisation, phases of project, etc -->

									<executionConfig>
										<description>Self Test Features</description> <!-- This appears at the root of the results report -->

										<tags>@all</tags>  <!-- optional - If the feature or scenario has this tag, then it will be 
											included, otherwise it won't, tags are space separated -->

										<fastFailParseErrors>false</fastFailParseErrors> <!-- optional - if true any parse errors will fail the build immediately, 
											rather than attempting to execute as much as possible and fail those tests 
											that can't be parsed -->
										<featureFile>${basedir}/target/test-classes/features</featureFile> <!-- path to the feature file, or directory containing the feature files -->
										<subStepsFileName>${basedir}/target/test-classes/substeps</subStepsFileName> <!-- path to directory of substep files, or a single substep file -->


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
									<outputDirectory>${project.build.directory}</outputDirectory>
					<!-- report title is used in the HTML test report -->
									<reportTitle>Webdriver Substeps Example Report -
										${project.version}</reportTitle>
								</executionReportBuilder>

							</configuration>
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
