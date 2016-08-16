package talentboost.vehicle.assembly.commands.test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import talentboost.vehicle.assembly.car.Vehicle;
import talentboost.vehicle.assembly.commands.DisassembleCar;
import talentboost.vehicle.assembly.common.EngineException;
import talentboost.vehicle.assembly.common.InvalidCommandException;
import talentboost.vehicle.assembly.common.VehicleException;
import talentboost.vehicle.assembly.controls.ControlJDBC;
import talentboost.vehicle.assembly.jdbc.JDBC;

/**
 * {@code} Tests the disassemble command with valid and invalid vin
 * <b><b>WARNING! TEST ONLY WORKS CORRECTLY IF THERE IS A VEHICLE IN THE
 * DATABASE WITH THE CORRECT VIN OTHERWISE THERE IS NO VEHICLE THERE TO BE
 * DISASSEMBLED BY THE COMMAND!</b></b>
 * BUT IT WORKS!!!
 * @author rados
 */
public class DisassembleTest {
	private static final String EXISTING_VIN = "GB46KxVjWUyt2hlW2";
	private static final String NON_VALID_VIN = "GB46KxVjWUy2hlW2";
	private ControlJDBC control = new ControlJDBC(new JDBC());
	private DisassembleCar disassemble;

	@Test
	public void testValidVIN() throws InvalidCommandException, EngineException, VehicleException {
		disassemble = new DisassembleCar(control);
		disassemble.execute(EXISTING_VIN);
		Vehicle vehicle = control.getByVIN(EXISTING_VIN);
		assertEquals(false, vehicle.getStatus());
	}

	@Test(expected = VehicleException.class)
	public void testInvalidVIN() throws InvalidCommandException, EngineException, VehicleException {
		disassemble = new DisassembleCar(control);
		disassemble.execute(NON_VALID_VIN);
	}
}
