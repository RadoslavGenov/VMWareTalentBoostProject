<b>Vehicle Assembly Final Task Documentation</b>
First I am going to explain one by one what each of the packages in my implementation does.

<b>talentboost.vehicle.assembly.car</b>
This package holds the information for the vehicles, whether it is a car or an suv. The vehicle class is an abstract class 
which focuses on holding fields which both the car and the suv class share. It also contains getters for the information which
are used in the different sections of the project. Both the car and the suv class contain an implementation of the builder
design pattern class to help with the building of the object and both have private contructors.

<b>talentboost.vehicle.assembly.commands</b>
This package contains the classes that implement the available commands, create, print, update, find, and disassemble, along 
with an interface ICommands and two helper classes to ease the work of the create command. Since all of the commands implement
the interface ICommands they are all obligated to implement a getName and execute method. The Create class focuses on building
the specific engine of the car and specific vehicle itself. The CreateEngine I use to obtain the engine of the vehicle being 
created whether it is a combustion engine or an electric engine. The class has a static getPower method which i use to obtain the
KW, HP, and displacement for the combustion engine. It is pretty straight-forward logic if you take a look at the method.
The method returns a Treble the arguments being displacement, horsepower, and KW respectively. The CreateVehicle class has similar
implementation and uses two methods to return a Vehicle object of either SUV or car. Both of the classes use the objects builders
to create them. Further, once the vehicle is created it is added to a database (which I will talk about later on). I will
not go into detail with the other commands in this package, since in their execute methods they use the available JDBC connection
to do their work, so the part that actual makes them work is the JDBC class.

<b>talentboost.vehicle.assembly.common</b>
This package contains classes that are common for the entire project. I have an EngineException to control exceptions that
arise from improper creation of the engine. Further I have a VehicleException to control exceptions that arise if a given problem with 
with a vehicle occurs. In addition, I also have an InvalidCommandException, which i believe is pretty self-explanitory.
In addition, here are the two classes Pair and Treble that I use to return multiple values wherever needed. Lastly, there is
a Ratios class which holds maps of the given ratios of HP, KW, and displacement available, I will show them here to let the
user know the restrictions of power that he has on creating a vehicle:
HP->KW (76, 74) (138, 134) (253, 245) (264, 253) (341, 331) (526, 510) (759, 736) KW->HP(Turbo) (96, 99) (174, 179) (318, 328) (329, 339) (430, 444) (663, 684) (957, 987)
KW->HP (74, 76) (134, 138) (245, 253) (253, 264) (331, 341) (510, 526) (736, 759) KW->L (74, 1) (134, 2) (245, 3) (253, 4) (331, 5) (510, 6) (736, 8) L->KW (1, 74) (2, 134) (3, 245) (4, 253) (5, 331) (6, 510) (8, 736)

<b>talentboost.vehicle.assembly.controls</b>
Here i have a ControlJDBC class which acts as a wrapper class to the JDBC class to protect it from any externals having direct contact with the
database. In addition I also have an Input class which obtains the input from the specified file and then splits it into
command and argument.

<b>talentboost.vehicle.assembly.engine</b>
This package holds the different features on the building of the engine. I have an abstract class Engine which both the 
CombustionEngine and the ElectricEngine extends. The Engine class holds mutual fields that both of the engines share and in addition
to that has getters. The interesting part about the getters is that if the current engine being created is an electric engine
ofcourse electric engines do not have displacement, so to handle this error i use <code>this instanceof ElectricEngine</code>
and if the object is not an instance of ElectricEngine i throw an exception, since the electric engine does not have these fields.
On the other hand if the current object is a CombustionEngine i let the getter do its job.

<b>talentboost.vehicle.assembly.enums</b>
In this package i hold three enumerations that encapsulate the available Models, Model Types, and Transmissions.

<b>talentboost.vehicle.assembly.jdbc</b>
Now this package, holds the heart of the entire project, the database connection. In the JDBC class, in the contructor i establish a connection
to my database, i use the file database.properties to get the mapped values for the user, url, password, and driver. To make the connection work
I use the database.script, in a database of your choice, (I used postgreSQL) to create the database vehicle_database, and the relation vehicles.
Next, let us examine the <code>addVehicle</code> method, this method uses a PreparedStatement to insert a vehicle into the database to store its 
information, once it is created. It also checks if the vehicle engine is electric or not and if it is, it places null for the values that the electric engine does not 
share with the combustion engine. The method <code>getAllVehicles</code> simply uses a PreparedStatement to obtain all of the vehicles from the relation and
store them in an ArrayList and return them. The method <code>getByVin</code> again uses the same approach, with a query to select all of the vehicles where the vin
matches the given one. The method <code>disassemble</code> again takes a similar approach to just execute a simple query to update the status of the vehicle. The
<code>find</code> method takes the exact same approach as the <code>getAllVehicles</code> method but with a slightly modified query to limit the return result to only
vehicles which have a certain emission. Lastly, the <code>updateEmission</code> method simply takes an emission and a vin, and updates the vehicles with the given vin
to the new emission given.

<b>talentboost.vehicle.assembly.parser</b>
In this package i have only one class the CreateInputParser. This is a helper class to the Input and Create classes to ease the transition of the user input into the 
creating of the vehicle. This class parses the given line of the file, and seperates it into the fields that are needed to create the vehicle and given engine.
The interesting part to note on this class is that if one or more of the optional fields are not given by the user the field is set to null. Later I will discuss how I 
use three other validator classes to validate these fields and pass them to the Create command.

<b>talentboost.vehicle.assembly.validation</b>
In this package I validate the input that is handed to me by the CreateInputParser, and check whether it is null or has a value. There are three different classes here, 
CombustionEngineValidator, ElectricEngineValidator, and VehicleValidator, each one does its job seperatly to fill in the given fields and pass them to the Create class.
Interesting to note here is that if a fields is not specified it is set to false, for easier understanding by the Create class.

<b>talentboost.vehicle.assembly.vin</b>
In this package i hold the vin class. In itself it contains a private constructor and a static builder to create the VIN object. <b><b>NOTE!</b></b> the user has the option to 
either directly enter the vin as a string or either have the builder contruct it himself by entering the ISO code and the Factory Number and letting the builder contruct the rest of the unique code.

<b><b>JUnit Tests!</b></b>

<b>talentboost.vehicle.assembly.builder.test</b>
In this package I have several tests for each of the builders I have implemented in my project. The CarBuilderTest tests the car builder to see if the object was, firstly the same class as the Car.class,
and then it tests if all of the fields are correct. The SUVBuilderTest does the exact same thing as the CarBuilderTest except that it tests to an SUV object. In the EngineBuilderTest i test the creation
of the electric and combustion engines and to see if the fields given where correct. I also test whether an EngineException is thrown when the user tries to access getters that do not correspond to the 
electric engine. In the VINBuilderTest i first test if the builder at all works correctly then i test the randomness and the uniqueness of the strings respectively.

<b>talentboost.vehicle.assembly.commands.test</b>
Here I test each of the commands seperately. In the CreateCarTest, I first test if the engine creationg is correct, I have one test for the electric engine and several for the combustion engine.
Next after that I test the creation of the SUV and Car object by the Create class and if all of the fields are proper. Further, in the DisassembleTest I test the disassemble command with valid and invalid vin 
<b><b>NOTE! TEST ONLY WORKS CORRECTLY IF THERE IS A VEHICLE IN THE DATABASE WITH THE CORRECT VIN OTHERWISE THERE IS NO VEHICLE THERE TO BE DISASSEMBLED BY THE COMMAND!</b></b> But the test works! The same goes for
the FindCarTest and the PrintCarTest, if there is no vehicle in the database with the vin number i am testing it will fail, but i have test them with the given vins and vehicles in the database that have them and they work!
For the UpdateCarTest, here i actually first put a vehicle into the database and then update the same vehicle, so the test works no matter if the database is empty.

<b>talentboost.vehicle.assembly.complete.test</b>
In the class CompleteTest i have implemented a sample run of the program. The program reads the commands from the CommandsFile.txt and writes the output into the file
SampleOutput.txt. The commands I have implemented are create, find and print.

<b>talentboost.vehicle.assembly.database.test</b>
Here I test the JDBC class and if it actually fills the database with the given values as input. Since i already have test most of the different parts of the JDBC in the commands.test here i only test the <code>getAllVehicles</code>
method just to see if it returns the same values as the ones actually given. In the second test I test if the database correctly fills all of the fields for the combustion engine.

<b>talentboost.vehicle.assembly.input.test</b>
Here I have two JUnit test one CreateInputParserTest which has 12 test cases/scenarios of what the input of the users might be, and then i check if it correctly distributes all of the fields with getters.
In the second class InputTest Here i simply test if the Input class parses the line correct as command and argument.

<b>talentboost.vehicle.assembly.validation.test</b>
Lastly in this package i test all of my validators with different given inputs by the user, if they work correctly. Firstly, I test the combustion engine validator with the CombustionEngineValidationTest, and here
I have 6 different scenarios that the user might input, which i test whether the validator returns true or false, (true if the field is marked false if it is not). In the ElectricEngineValidationTest i have 4 different 
scenarios/tests for user input and whether the electric engine validator works correctly. Lastly, in the VehicleValidationTest I have 4 more tests that test whether the correct Model and ModelType are validated by the 
vehicle validator.

