package talentboost.vehicle.assembly.validation.test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import talentboost.vehicle.assembly.common.InvalidCommandException;
import talentboost.vehicle.assembly.common.VehicleException;
import talentboost.vehicle.assembly.parse.CreateInputParser;
import talentboost.vehicle.assembly.validation.VehicleValidator;

/**
 * {@code} Class to test VehicleValidator. Whether the validator returns
 * true/false values for the given input tests
 * 
 * @author rados
 *
 */
public class VehicleValidationTest {
	private static final String TEST_1 = "car model=A5-sedan engine=E";
	private static final String TEST_2 = "suv model=F4";
	private static final String TEST_3 = "car model=A1{-sedan} ";
	private static final String TEST_4 = "car model=A4";

	private CreateInputParser parser;
	private VehicleValidator validation;

	@Test
	public void testModel() throws InvalidCommandException, VehicleException {
		parser = new CreateInputParser(TEST_1);
		validation = new VehicleValidator(parser);
		assertEquals(true, validation.validateModel());
	}

	@Test(expected = VehicleException.class)
	public void testNoModel() throws InvalidCommandException, VehicleException {
		parser = new CreateInputParser(TEST_2);
		validation = new VehicleValidator(parser);
		assertEquals(false, validation.validateModel());
	}

	@Test
	public void testModelTypeFilled() throws InvalidCommandException, VehicleException {
		parser = new CreateInputParser(TEST_3);
		validation = new VehicleValidator(parser);
		assertEquals(true, validation.validateModel());
		assertEquals(true, validation.validateModelType());
	}

	@Test
	public void testModelTypeNotFilled() throws InvalidCommandException, VehicleException {
		parser = new CreateInputParser(TEST_4);
		validation = new VehicleValidator(parser);
		assertEquals(true, validation.validateModel());
		assertEquals(false, validation.validateModelType());
	}
}
