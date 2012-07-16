
Why?
----

We’ve tried a number of BDD frameworks and used Cucumber extensively on a number of projects, typically our QA team would write the features and scenarios based on project requirements.  Along with other people trying out BDD, our early efforts tended to result in features that were too low level in an attempt to minimise repetitive code.  In keeping with the best practices that we read about as a result, later projects aimed to keep features and scenarios at a higher level, more specification by example, with re-use of functionality implemented in code (Java in our case).

In the early stages of projects, the approach of our QA team writing features alongside the development team implementing code and implementing the step definitions would work ok, but as pressures on delivery of functional code increases, the implementation of step definitions ends up taking a lower priority.  Ultimately this leads to a significant number of unimplemented step definitions, an incomplete test suite and a frustrated QA team and as a result the potential for significant degradation in quality.

   *Substeps was created specifically to enable non-technical team members to write tests that could be executed directly, without having to be implemented by the development team.*