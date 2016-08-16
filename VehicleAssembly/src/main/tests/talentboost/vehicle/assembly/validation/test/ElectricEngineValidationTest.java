package talentboost.vehicle.assembly.validation.test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import talentboost.vehicle.assembly.common.EngineException;
import talentboost.vehicle.assembly.common.InvalidCommandException;
import talentboost.vehicle.assembly.common.VehicleException;
import talentboost.vehicle.assembly.parse.CreateInputParser;
import talentboost.vehicle.assembly.validation.ElectricEngineValidator;
/**
 * {@code} Class to test ElectricEngineValidator. Whether the validator returns true/false values for the given input tests
 * @author rados
 *
 */
public class ElectricEngineValidationTest {
	private CreateInputParser parser;
	private ElectricEngineValidator validation;

	private static final String TEST_1 = "suv model=Q1{-hatchback} engine=E-euro3 ";
	private static final String TEST_2 = "car model=A1{-sedan} engine=E {transmission=Auto{-4}}";
	private static final String TEST_3 = "car model=A4{-sedan} engine=D{-6L} {transmission=Auto{-4}}";
	private static final String TEST_4 = "car model=A4{-sedan} engine=E";

	@Test(expected = EngineException.class)
	public void testEmissionStandardFilled() throws InvalidCommandException, EngineException, VehicleException {
		parser = new CreateInputParser(TEST_1);
		validation = new ElectricEngineValidator(parser);
		assertEquals(true, validation.validateEmission());
	}

	@Test(expected = EngineException.class)
	public void testTransmissionFilled() throws InvalidCommandException, EngineException, VehicleException {
		parser = new CreateInputParser(TEST_2);
		validation = new ElectricEngineValidator(parser);
		assertEquals(false, validation.validateTransmission());
	}

	@Test(expected = EngineException.class)
	public void testIncorrectInput() throws InvalidCommandException, EngineException, VehicleException {
		parser = new CreateInputParser(TEST_3);
		validation = new ElectricEngineValidator(parser);
		assertEquals(true, validation.validateEmission());
		assertEquals(true, validation.validatePower());
	}

	@Test
	public void testCorrectInput() throws InvalidCommandException, EngineException, VehicleException {
		parser = new CreateInputParser(TEST_4);
		validation = new ElectricEngineValidator(parser);
		assertEquals(true, validation.validateEmission());
		assertEquals(true, validation.validatePower());
		assertEquals(true, validation.validateTurbo());
		assertEquals(true, validation.validateTransmission());
	}

}
