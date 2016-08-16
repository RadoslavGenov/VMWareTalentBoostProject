package talentboost.vehicle.assembly.commands.test;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import talentboost.vehicle.assembly.commands.FindCar;
import talentboost.vehicle.assembly.common.EngineException;
import talentboost.vehicle.assembly.common.InvalidCommandException;
import talentboost.vehicle.assembly.common.VehicleException;
import talentboost.vehicle.assembly.controls.ControlJDBC;
import talentboost.vehicle.assembly.jdbc.JDBC;

/**
 * {@code} Tests the find command with valid and invalid vin
 * <b><b>WARNING! TEST ONLY WORKS CORRECTLY IF THERE IS A VEHICLE IN THE
 * DATABASE WITH THE CORRECT EMISSION OTHERWISE THERE IS NO VEHICLE THERE TO BE
 * FOUND BY THE COMMAND!</b></b> BUT IT WORKS!!!
 * 
 * @author rados
 */
public class FindCarTest {
	private static final String VALID_EMISSION = "euro3";
	private static final String NON_VALID_EMISSION = "euro9";
	private ControlJDBC control = new ControlJDBC(new JDBC());
	private FindCar find;

	@Test
	public void testFindVehiclesWithValidEmission() throws InvalidCommandException, EngineException, VehicleException {
		find = new FindCar(control);
		assertNotNull(find.execute(VALID_EMISSION));
	}

	@Test(expected = InvalidCommandException.class)
	public void testFindVehiclesWithInvalidEmission()
			throws InvalidCommandException, EngineException, VehicleException {
		find = new FindCar(control);
		find.execute(NON_VALID_EMISSION);
	}
}
