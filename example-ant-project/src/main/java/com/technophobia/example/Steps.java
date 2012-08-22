package com.technophobia.example;

import com.technophobia.substeps.model.SubSteps.Step;

public class Steps {

	@Step("TestStep")
	public void testStep() {
		System.out.println("Executing test step");
	}

}
