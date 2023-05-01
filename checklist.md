## Common mistakes (jv-fruit-shop-tests)

#### Don't begin class or method implementation with empty line. 
Remove all redundant empty lines, be careful :)

#### Remember about naming of test methods.
There are a lot of ways to name your test methods. The main point is that 
they should have informative names and be consistent along with other developers in your team. 
For this task use such convention: `<methodUnderTest>_<state>_<expectedBehavior>`. 
For example, if we are testing the method `register` with a `null` user's age 
the test method name should be `register_nullAge_notOk`. `notOk` is because 
the test expects the register method to throw an exception.

#### Strive to have most of the functionality covered with tests
Ensure you have covered at least 80% of code with tests. That will reduce the chances of getting unexpected behaviour 
after software release and during maintenance stage.
You can check this requirement by `running your tests with coverage` (see this [tutorial](https://www.loom.com/share/85886cc0b3c9458a8b5c0d5af9bf4720))
or running `mvn clean verify` in the terminal.

#### Don't keep all tests in a single class
Create a corresponding test class for each service you test. Do not test logic of whole program in one test class.
That's important for code readability.

Example:  
```java  
    CsvFileService -> CsvFileServiceTest  
    FruitService -> FruitServiceTest  
    ...  
```  
Same goes for files that you use in tests, **let's put them into this folder:** `src/test/resources/[your-files.csv]`   

#### Try to cover different scenarios in tests
Your task is to include edge cases apart from regular method use case.

#### Don't test Main class
We want to test only business logic, so there is no need to cover `Main` class with tests. 
You can exclude Main class with `main()` method from being checked for code coverage in `pom.xml`.   

__Example__: find the following code in the `pom.xml` and change `Main` according to your 
    class naming where you have your `main()` method.  
    
```java
    <configuration>  
        <excludes>  
            <exclude>**/Main*</exclude>  
        </excludes>  
    </configuration>  
```  

#### Don't use any other version of JUnit
Use JUnit5 that is already present in your `pom.xml`.
#### Ensure that you test your services and their independently
If you are testing FruitService behaviour - don't use FileReader or any other service in your tests.

Same, when you are testing the method of FruitService that returns all information about the fruits in the storage -
there is no need to use the other FruitService method that puts the fruit into the storage.

REMEMBER: you can access the storage directly instead - just remember to clear it after each test.

Example:
```java
@Test
public void getReport_Ok() {
    Storage.storage.put(...); // put fruits directly to the storage
    String expected = "your expected result here";
    String actual = fruitService.getReport();
    Assert.assertEquals(expected, actual);
}

@AfterEach
public void afterEachTest() {
    Storage.storage.clear();
}
```  
#### Unit testing is <ins>isolated</ins> testing
Keep your strategy, handler, and service tests separate from each other. Each of them needs a separate test class with their corresponding test cases.

Don't test your strategy in service (e.g. `FruitService`). It's enough to create a map with only one handler (e.g. `Balance`).
