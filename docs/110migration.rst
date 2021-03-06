Migrating your maven build to version 1.1.0
===========================================

In version 1.1.0, Substeps was restructured which means there are a couple of changes you will need to make.

* Substeps was split into smaller sub projects therefore in order to keep project configuration simple a bill of materials (BOM) was introduced
* In order to comply with maven standards the artifactId of the maven plugin has been changed
* Substeps tests can now be launched in a separate VM, this may be apparent in the level of logging output during test execution, which will now use 
  logging.properties defined in YOUR project rather than the ones included in one of the substeps libraries (we've now removed these).
  
.. Note::
   `*` In 1.1.0, to run tests in the forked VM, your logging properties must set com.technophobia.substeps.jmx.SubstepsJMXServer to debug level.  This will not be necessary in future releases.

So if your projects pom is currently set up to use a 1.0.x version follow the steps below to migrate to 1.1.0.

1. Add a dependency on the BOM

  This will bring in the correct versions of various substep dependencies you need

  .. code-block:: xml
  
     <dependency>
       <groupId>com.technophobia.substeps</groupId>
       <artifactId>substeps-bom</artifactId>
       <version>1.1.0</version>
       <type>pom</type>
       <scope>test</scope>
     </dependency>

2. If you have dependency directly on substeps-core you can remove this.  

3. Webdriver changes

  * If you are using the webdriver project you may have an explicit dependency on selenium, this can be removed.
  * Update the version of webdriver-substeps to 1.1.0

4. Update the plugin configuration

  * Previously the maven plugin was named 'substeps-runner' change this to 'substeps-maven-plugin'
  * Also remove the version number from the pom (the dependency you added on the bom above will bring in the correct version of this plugin)
