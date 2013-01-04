Tags: @non-visual

Feature: A feature to self test the webdriver substeps implementations

# how do we test that these things fail, when we expect them to - this will test that they pass when
# expected, but not fail when expected

Scenario: a scenario
	Given I go to the self test page
	Then I can see 'Hello Self Test page'
	And if I click the 'click me' button
	Then I can see "Wahoo" message
	And I dont see "number one option" in select id select_id
	And I see "number two option" in select id select_id
	Then I choose "number three option" in select id select_id
	And I see "number three option" in select id select_id
	And if I click the Click by id button
	Then I can see another "Wahoo Two" message
	And if I click the 'delayed click' button
	Then I see "Delayed Text" after a pause
	Given I enter "some text" into the text field
	Then the text field will contain "some text"	
	And the field located beneath the heading has the text 'some child text'
	And I can click the link "a text link" and see "clicked a link"  

	Given radio button with text "radio - option 1" is checked
	Given radio button with text "radio - option 2" is not checked
	Then I can check radio button with text "radio - option 3"
	Then radio button with text "radio - option 3" is checked
	
	Given checkbox with text "a checkbox label" is not checked
	Then I can check checkbox with text "a checkbox label"
	And checkbox with text "a checkbox label" is checked
	
	# table
	And the table row 1, column 2 contains "Mrs Evil Headtecher"   	
	And find by child works
	
	And I can find the disabled text field

    And I dont see "number two option" in select id select_id
    And I select the second option by looking at the text it contains
	And I see "number two option" in select id select_id
	
	Then I find a row using column contents 
    And I can find and click the link "View" in the row
    
    # £ checking
    Given I enter "£1000000" into the text field
    Then the text field will contain "£1000000"
	
##################################################################	
	# these tests need to be last as they go off to google
	Given I go to an external page such as Google
	Then the page title is "Google"
    
    # Do not add more steps here
