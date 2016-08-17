package talentboost.vehicle.assembly.commands.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import talentboost.vehicle.assembly.commands.PrintCar;
import talentboost.vehicle.assembly.common.EngineException;
import talentboost.vehicle.assembly.common.InvalidCommandException;
import talentboost.vehicle.assembly.common.VehicleException;
import talentboost.vehicle.assembly.controls.ControlJDBC;
import talentboost.vehicle.assembly.jdbc.JDBC;

/**
 * {@code} Tests the print command with valid and invalid vin
 * <b><b>WARNING! TEST ONLY WORKS CORRECTLY IF THERE IS A VEHICLE IN THE
 * DATABASE WITH THE CORRECT VIN OTHERWISE THERE IS NO VEHICLE THERE TO BE
 * PRINTED BY THE COMMAND!</b></b> BUT IT WORKS!!!
 * 
 * @author rados
 */
public class PrintCarTest {
	private static final String ALL = "all";
	private static final String VIN = "'GB4g7dkFtXEs7LgeR'";
	private static final String OUTPUT = "vin	          |model|type |emission|engine type\n";
	private static final String VEHICLE_WITH_TEST_VIN = "GB4g7dkFtXEs7LgeR | A5  |sedan| euro6  | E";
	private ControlJDBC control = new ControlJDBC(new JDBC());
	private PrintCar print;

	@Test
	public void testPrintAll() throws InvalidCommandException, EngineException, VehicleException {
		print = new PrintCar(control);
		assertNotNull(print.execute(ALL));
	}

	@Test
	public void testPrintByVIN() throws InvalidCommandException, EngineException, VehicleException {
		print = new PrintCar(control);
		assertEquals(OUTPUT + VEHICLE_WITH_TEST_VIN, print.execute(VIN));
	}

}
