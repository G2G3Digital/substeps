Usage
=====

Simple Example
--------------

The example below illustrates the use of Substeps to test a web application, so for this particular case we would use the webdriver-substeps step defintitions.  

.. raw:: html 
   
   <div class="admonition">
      <span style="color:#69BDCB;">#  A comment</span><br/>
      <span style="color:Green;">Tags:</span>phase1<br/><br/>
      <span style="color:Red;">Feature:</span> A Feature to demonstrate Substeps<br/><br/>
      <span style="color:Blue;">Scenario:</span> A simple high level scenario<br/>
      <div style="position:relative;left:5em">
      Given a user is logged in to the application<br/>
      Then they can edit their profile<br/>
      And post messages to their friends<br/>
      </div>
   </div>  

   
An Example Substep definitions file
-----------------------------------

.. raw:: html 

   <div class="admonition">
   <span style="color:#69BDCB;"># Substep definitions for ....</span><br/>

   <span style="color:Blue;">Define:</span> Given a user is logged in to the application<br/>
      <div style="position:relative;left:5em">
      NavigateTo /index.html<br/>
      ClickLink "Login"<br/>
      WaitForPageTitle "Login in to your account"<br/>
      ClearAndSendKeys "testuser1" to id login_id<br/>
      ClearAndSendKeys "password" to id password_id<br/>
      FindByTagAndAttributes tag="input" attributes=[type="submit",value="Login"]<br/>
      WaitForPageTitle "your Profile"<br/>
      </div>
   <br/>
   
   <span style="color:Blue;">Define:</span> Then they can edit their profile<br/>
      <div style="position:relative;left:5em">
      ...<br/>
      </div>
   <br/>
   
   <span style="color:Blue;">Define:</span> And post messages to their friends<br/>
      <div style="position:relative;left:5em">
      ...<br/>
      </div>
   </div>
   

- In the simple example above, you can see how steps within a scenario can be broken down into a series of Substeps.
- The feature and scenario remain descriptive, whilst the substep definitions contain the details of how that particular step is implemented
- Substep definitions can call each other, and other scenarios too. 


A complex example
-----------------

A more elaborate example is illustrated below, again with a web application under test.  
Additional step definition libraries and custom implementations can be used alongside to provide extra functionality.

.. raw:: html 
   
   <div class="admonition">
      <span style="color:Red;">Feature:</span> A more complex feature<br/><br/>
      
      <span style="color:#69BDCB;"># Background steps are defined once per feature and executed before each scenario</span><br/>
      <span style="color:Blue;">Background:</span> <br/>
       <div style="position:relative;left:5em">
      Given the new user doesn't exist <br/>
      </div><br/>
      <span style="color:Blue;">Scenario:</span> As an administrator I can 'do stuff'<br/>
      <div style="position:relative;left:5em">
      Given I can log in as "administrator"<br/>
      Then I can create a new user<br/>
      </div>
      <br/>
      
      <span style="color:#69BDCB;"># Outline scenarios are executed repeatedly, once for each row of the Examples table following the scenario.</span><br/>
      <span style="color:#69BDCB;"># Variables from the table are substituted.</span><br/>
      <span style="color:Blue;">Scenario Outline:</span> I can log in with different users<br/>
      <div style="position:relative;left:5em">
      Given I can log in as "&lt;username&gt;"<br/>
      <span style="color:#69BDCB;position:relative;left:2em;"># at runtime, the username token will be replaced with a value from the table below</span><br/>
      Then I am greeted by my "&lt;name&gt;"<br/>
      And my role is displayed as "&lt;role_name&gt;"<br/>
      </div>
      <br/>

      <span style="color:Blue;">Examples:</span><br/>
      <div style="position:relative;left:5em; width:50%"><pre>
      |username |name       |role_name     |
      |admin    |Andy Admin |Administrator |
      |boss     |Sue Super  |Supervisor    |
      |user     |Bob Smith  |User          |  
      </pre></div>
            
   </div>  
 
   
   <div class="admonition">
   <span style="color:#69BDCB;"># Substep definitions for a complex feature ....</span><br/>

   <span style="color:Blue;">Define:</span> Given I can log in as "&lt;user_name&gt;"<br/>
   <span style="color:#69BDCB;position:relative;left:2em;"># This substep is passed a parameter which we can refer to by its name, &lt;user_name&gt; in this case</span><br/>
   
      <div style="position:relative;left:5em">
      NavigateTo /index.html<br/>
      ClickLink "Login"<br/>
      WaitForPageTitle "Login in to your account"<br/>
      ClearAndSendKeys "&lt;user_name&gt;" to id login_id<br/>
      ClearAndSendKeys "password" to id password_id<br/>
      FindByTagAndAttributes tag="input" attributes=[type="submit",value="Login"]<br/>
      WaitForPageTitle "your Profile"<br/>
      </div>
   <br/>
   
   <span style="color:Blue;">Define:</span> Then I am greeted by my "&lt;name&gt;"<br/>
      <div style="position:relative;left:5em">
      FindById welcome-div<br/>
      AssertCurrentElement text contains "&lt;name&gt;"<br/>
      </div>
   <br/>

   <span style="color:Blue;">Define:</span> Given the new user doesn't exist<br/>
      <div style="position:relative;left:5em">
      DeleteUserFromDB "Nev Newbie" &nbsp;<span style="color:#69BDCB;"># a custom step implementation for this project</span><br/>
      ...<br/>
      </div>
   <br/>

   <span style="color:Blue;">Define:</span> And my role is displayed as "&lt;role_name&gt;"<br/>
      <div style="position:relative;left:5em">
      ...<br/>
      </div>
   <br/>
   
   <span style="color:Blue;">Define:</span> Then I can create users<br/>
      <div style="position:relative;left:5em">
      <span style="color:#69BDCB;"># substeps to actually create a user</span><br/>
      ...<br/>
      </div>
   </div>   

Substep definitions can also call other substeps, not just step implementations.

Implementing bespoke step implementations is straight forward too, see `here <http://technophobia.github.com/substeps/extending.html#step-implementations>`_ for more details

  
.. code-block:: java

   @StepImplementations
   public class MyProjectStepImplementations {

      @Step("DeleteUserFromDB \"([^\"]*)\")
      public void deleteUserFromDB(final String name){
         // get connection
         // execute SQL
         ...

