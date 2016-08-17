package talentboost.vehicle.assembly.validation.test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import talentboost.vehicle.assembly.common.EngineException;
import talentboost.vehicle.assembly.common.InvalidCommandException;
import talentboost.vehicle.assembly.common.VehicleException;
import talentboost.vehicle.assembly.parse.CreateInputParser;
import talentboost.vehicle.assembly.validation.CombustionEngineValidator;
/**
 * {@code} Class to test CombustionEngineValidator. Whether the validator returns true/false values for the given input tests
 * @author rados
 *
 */
public class CombustionEngineValidationTest {

	private CreateInputParser parser;
	private CombustionEngineValidator validation;

	private static final String TEST_1 = "suv model=Q1-hatchback engine=D-6L-T-euro3 transmission=Auto";
	private static final String TEST_2 = "car model=A1-sedan engine=P-T-euro3 transmission=Man";
	private static final String TEST_3 = "car model=A4-sedan engine=D-6L transmission=Auto-4";
	private static final String TEST_4 = "suv model=Q5 engine=D transmission=Auto-6";
	private static final String TEST_5 = "suv model=Q1-hatchback engine=D";
	private static final String TEST_6 = "suv model=Q1 engine=D-T transmission=Auto-6";


	@Test
	public void testAllButHP() throws InvalidCommandException, EngineException, VehicleException {
		parser = new CreateInputParser(TEST_1);
		validation = new CombustionEngineValidator(parser);
		assertEquals(true, validation.validateEmission());
		assertEquals(true, validation.validateDisplacement());
		assertEquals(false, validation.validateHP());
		assertEquals(true, validation.validateTurbo());
		assertEquals(true, validation.validateTransmission());
	}

	@Test
	public void testFourFieldsFilled() throws InvalidCommandException, EngineException, VehicleException {
		parser = new CreateInputParser(TEST_2);
		validation = new CombustionEngineValidator(parser);
		assertEquals(true, validation.validateEmission());
		assertEquals(false, validation.validateDisplacement());
		assertEquals(false, validation.validateHP());
		assertEquals(true, validation.validateTurbo());
		assertEquals(true, validation.validateTransmission());
	}

	@Test
	public void testThreeFieldsFilled() throws EngineException, InvalidCommandException, VehicleException {
		parser = new CreateInputParser(TEST_3);
		validation = new CombustionEngineValidator(parser);
		assertEquals(false, validation.validateEmission());
		assertEquals(true, validation.validateDisplacement());
		assertEquals(false, validation.validateHP());
		assertEquals(false, validation.validateTurbo());
		assertEquals(true, validation.validateTransmission());
	}

	@Test
	public void testTwoFieldsFilled() throws EngineException, InvalidCommandException, VehicleException {
		parser = new CreateInputParser(TEST_4);
		validation = new CombustionEngineValidator(parser);
		assertEquals(false, validation.validateEmission());
		assertEquals(false, validation.validateDisplacement());
		assertEquals(false, validation.validateHP());
		assertEquals(false, validation.validateTurbo());
		assertEquals(true, validation.validateTransmission());
	}

	@Test
	public void testOneFieldFilled() throws InvalidCommandException, EngineException, VehicleException {
		parser = new CreateInputParser(TEST_5);
		validation = new CombustionEngineValidator(parser);
		assertEquals(false, validation.validateEmission());
		assertEquals(false, validation.validateDisplacement());
		assertEquals(false, validation.validateHP());
		assertEquals(false, validation.validateTurbo());
		assertEquals(false, validation.validateTransmission());
	}

	@Test
	public void testTypeTurboTransmissionFilled() throws InvalidCommandException, EngineException, VehicleException {
		parser = new CreateInputParser(TEST_6);
		validation = new CombustionEngineValidator(parser);
		assertEquals(true, validation.validateTurbo());
		assertEquals(true, validation.validateTransmission());
		assertEquals(false, validation.validateEmission());
		assertEquals(false, validation.validateDisplacement());
		assertEquals(false, validation.validateHP());
	}
}
