Substep Test Exceution Report
=============================

A default test execution report builder is included with Substeps and can be enabled with the following 
fragment in the pom.xml:

.. code-block:: xml

     <plugin>
        <groupId>com.technophobia.substeps</groupId>
        <artifactId>substeps-runner</artifactId>

        <configuration>
        ...
            <!-- Default HTML report builder -->
            <executionReportBuilder
                implementation="com.technophobia.substeps.report.DefaultExecutionReportBuilder">

                <!-- The directory in which the test execution report will be written to -->
                <outputDirectory>${project.build.testOutputDirectory}</outputDirectory>
                
                <!-- report title is used in the HTML test report -->
                <reportTitle>Substeps Report - ${project.version}</reportTitle>
                
            </executionReportBuilder>

        </configuration>

.. Note::
   If this configuration is omitted, no report will be produced.


Example Report
--------------

An example of the report can be found `here <example_substeps_report/report_frame.html>`_

The example uses the output from running the tests contained within the 
example susbteps project.  Javascript is required, however the report does not need to be served from
a webserver, it will work standalone.

