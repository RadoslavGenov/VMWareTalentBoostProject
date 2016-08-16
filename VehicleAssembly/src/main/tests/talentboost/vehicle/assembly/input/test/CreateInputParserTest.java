package talentboost.vehicle.assembly.input.test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import talentboost.vehicle.assembly.common.InvalidCommandException;
import talentboost.vehicle.assembly.common.VehicleException;
import talentboost.vehicle.assembly.enums.Model;
import talentboost.vehicle.assembly.enums.ModelType;
import talentboost.vehicle.assembly.enums.Transmission;
import talentboost.vehicle.assembly.parse.CreateInputParser;

/**
 * {@code} <b>This class has 12 different tests/scenarios for the
 * CreateInputParser. Where the given commands can be had.</b>
 * 
 * The CreateInputParser parses the fields if they exist else it leaves the
 * fields null.
 * 
 * It is the Validators job to fill the fields that are left null.
 * 
 * @see Validator classes CreateInputParser.class
 */
public class CreateInputParserTest {
	private static final String TEST = "car model=A5-sedan engine=E";
	private static final String TEST_1 = "suv model=Q1{-hatchback} engine=D{-6L{-T}-euro3} {transmission=Auto}";
	private static final String TEST_2 = "car model=A1{-sedan} engine=P{{-T}-euro3} {transmission=Man}";
	private static final String TEST_3 = "suv model=Q1 engine=E";
	private static final String TEST_4 = "car model=A4{-sedan} engine=D{-6L-euro4} {transmission=Auto{-4}}";
	private static final String TEST_5 = "suv model=Q5 engine=D {transmission=Auto{-6}}";
	private static final String TEST_6 = "suv model=Q1{-hatchback} engine=D{-6L{-T}-euro5} {transmission=Man{-5}}";
	private static final String TEST_7 = "suv model=Q1 engine=D{-6L{-T}-euro3} {transmission=Auto{-4}}";
	private static final String TEST_8 = "suv model=Q1{-kombi} engine=D{-100hp{-T}-euro3} {transmission=Auto{-7}}";
	private static final String TEST_9 = "car model=A3 engine=D{-4L{-T}-euro3} {transmission=Auto{-4}}";
	private static final String TEST_10 = "suv model=Q1{-hatchback} engine=D{-5L{-T}}";
	private static final String TEST_11 = "suv model=Q1{-hatchback} engine=D{-3L-euro5} {transmission=Auto{-4}}";

	private CreateInputParser parser;

	@Test
	public void test() throws InvalidCommandException, VehicleException {
		parser = new CreateInputParser(TEST);
		assertEquals("car", parser.getCar());
		assertEquals(null, parser.getDisplacement());
		assertEquals(null, parser.getEmission());
		assertEquals("E", parser.getEngineType());
		assertEquals(Model.A5, parser.getModel());
		assertEquals(null, parser.getTransmission());
		assertEquals(null, parser.getTurbo());
		assertEquals(ModelType.sedan, parser.getType());
	}

	@Test
	public void testOne() throws InvalidCommandException, VehicleException {
		parser = new CreateInputParser(TEST_1);
		assertEquals("suv", parser.getCar());
		assertEquals(6, parser.getDisplacement(), 0);
		assertEquals("euro3", parser.getEmission());
		assertEquals("D", parser.getEngineType());
		assertEquals(Model.Q1, parser.getModel());
		assertEquals(Transmission.Auto_4, parser.getTransmission());
		assertEquals(true, parser.getTurbo());
		assertEquals(ModelType.hatchback, parser.getType());
	}

	@Test
	public void testTwo() throws InvalidCommandException, VehicleException {
		parser = new CreateInputParser(TEST_2);
		assertEquals("car", parser.getCar());
		assertEquals(null, parser.getDisplacement());
		assertEquals("euro3", parser.getEmission());
		assertEquals("P", parser.getEngineType());
		assertEquals(Model.A1, parser.getModel());
		assertEquals(Transmission.Man_4, parser.getTransmission());
		assertEquals(true, parser.getTurbo());
		assertEquals(ModelType.sedan, parser.getType());
	}

	@Test
	public void testThree() throws InvalidCommandException, VehicleException {
		parser = new CreateInputParser(TEST_3);
		assertEquals("suv", parser.getCar());
		assertEquals(null, parser.getDisplacement());
		assertEquals(null, parser.getEmission());
		assertEquals("E", parser.getEngineType());
		assertEquals(Model.Q1, parser.getModel());
		assertEquals(null, parser.getTransmission());
		assertEquals(null, parser.getTurbo());
		assertEquals(null, parser.getType());
	}

	@Test
	public void testFour() throws InvalidCommandException, VehicleException {
		parser = new CreateInputParser(TEST_4);
		assertEquals("car", parser.getCar());
		assertEquals(6, parser.getDisplacement(), 0);
		assertEquals("euro4", parser.getEmission());
		assertEquals("D", parser.getEngineType());
		assertEquals(Model.A4, parser.getModel());
		assertEquals(Transmission.Auto_4, parser.getTransmission());
		assertEquals(null, parser.getTurbo());
		assertEquals(ModelType.sedan, parser.getType());
	}

	@Test
	public void testFive() throws InvalidCommandException, VehicleException {
		parser = new CreateInputParser(TEST_5);
		assertEquals("suv", parser.getCar());
		assertEquals(null, parser.getDisplacement());
		assertEquals(null, parser.getEmission());
		assertEquals("D", parser.getEngineType());
		assertEquals(Model.Q5, parser.getModel());
		assertEquals(Transmission.Auto_6, parser.getTransmission());
		assertEquals(null, parser.getTurbo());
		assertEquals(null, parser.getType());
	}

	@Test
	public void testSix() throws InvalidCommandException, VehicleException {
		parser = new CreateInputParser(TEST_6);
		assertEquals("suv", parser.getCar());
		assertEquals(6, parser.getDisplacement(), 0);
		assertEquals("euro5", parser.getEmission());
		assertEquals("D", parser.getEngineType());
		assertEquals(Model.Q1, parser.getModel());
		assertEquals(Transmission.Man_5, parser.getTransmission());
		assertEquals(true, parser.getTurbo());
		assertEquals(ModelType.hatchback, parser.getType());
	}

	@Test
	public void testSeven() throws InvalidCommandException, VehicleException {
		parser = new CreateInputParser(TEST_7);
		assertEquals("suv", parser.getCar());
		assertEquals(6, parser.getDisplacement(), 0);
		assertEquals("euro3", parser.getEmission());
		assertEquals("D", parser.getEngineType());
		assertEquals(Model.Q1, parser.getModel());
		assertEquals(Transmission.Auto_4, parser.getTransmission());
		assertEquals(true, parser.getTurbo());
		assertEquals(null, parser.getType());
	}

	@Test
	public void testEight() throws InvalidCommandException, VehicleException {
		parser = new CreateInputParser(TEST_8);
		assertEquals("suv", parser.getCar());
		assertEquals(100, parser.getDisplacement(), 0);
		assertEquals("euro3", parser.getEmission());
		assertEquals("D", parser.getEngineType());
		assertEquals(Model.Q1, parser.getModel());
		assertEquals(Transmission.Auto_7, parser.getTransmission());
		assertEquals(true, parser.getTurbo());
		assertEquals(ModelType.kombi, parser.getType());
	}

	@Test
	public void testNine() throws InvalidCommandException, VehicleException {
		parser = new CreateInputParser(TEST_9);
		assertEquals("car", parser.getCar());
		assertEquals(4, parser.getDisplacement(), 0);
		assertEquals("euro3", parser.getEmission());
		assertEquals("D", parser.getEngineType());
		assertEquals(Model.A3, parser.getModel());
		assertEquals(Transmission.Auto_4, parser.getTransmission());
		assertEquals(true, parser.getTurbo());
		assertEquals(null, parser.getType());
	}

	@Test
	public void testTen() throws InvalidCommandException, VehicleException {
		parser = new CreateInputParser(TEST_10);
		assertEquals("suv", parser.getCar());
		assertEquals(5, parser.getDisplacement(), 0);
		assertEquals(null, parser.getEmission());
		assertEquals("D", parser.getEngineType());
		assertEquals(Model.Q1, parser.getModel());
		assertEquals(null, parser.getTransmission());
		assertEquals(true, parser.getTurbo());
		assertEquals(ModelType.hatchback, parser.getType());
	}

	@Test
	public void testEleven() throws InvalidCommandException, VehicleException {
		parser = new CreateInputParser(TEST_11);
		assertEquals("suv", parser.getCar());
		assertEquals(3, parser.getDisplacement(), 0);
		assertEquals("euro5", parser.getEmission());
		assertEquals("D", parser.getEngineType());
		assertEquals(Model.Q1, parser.getModel());
		assertEquals(Transmission.Auto_4, parser.getTransmission());
		assertEquals(null, parser.getTurbo());
		assertEquals(ModelType.hatchback, parser.getType());
	}
}
