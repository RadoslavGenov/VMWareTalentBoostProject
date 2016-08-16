package talentboost.vehicle.assembly.commands.test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import talentboost.vehicle.assembly.car.Vehicle;
import talentboost.vehicle.assembly.commands.Create;
import talentboost.vehicle.assembly.commands.CreateEngine;
import talentboost.vehicle.assembly.commands.CreateVehicle;
import talentboost.vehicle.assembly.commands.UpdateCar;
import talentboost.vehicle.assembly.common.EngineException;
import talentboost.vehicle.assembly.common.InvalidCommandException;
import talentboost.vehicle.assembly.common.VehicleException;
import talentboost.vehicle.assembly.controls.ControlJDBC;
import talentboost.vehicle.assembly.engine.Engine;
import talentboost.vehicle.assembly.jdbc.JDBC;
import talentboost.vehicle.assembly.parse.CreateInputParser;

/**
 * {@code}updates the given emission of the car by a vin <b><b>In the test i
 * create a car, put it in the database, and the find it by the vin and THEN i
 * update it. So, there is not need for the car to be in the database
 * already!</b></b>
 * 
 * @author rados
 *
 */
public class UpdateCarTest {
	private static final String test5 = "suv model=Q5 engine=D-euro3 transmission=Auto-6";
	private static final String CMDS = " engine=euro5";
	private Create create;
	private Engine engine;
	private CreateInputParser parser;
	private Vehicle vehicle;
	private UpdateCar update;
	private ControlJDBC control = new ControlJDBC(new JDBC());

	@Test
	public void testUpdateCommand() throws InvalidCommandException, VehicleException, EngineException {
		parser = new CreateInputParser(test5);
		create = new Create(control);
		engine = new CreateEngine().combustionCreate(parser);
		vehicle = new CreateVehicle().createSuv(parser, engine);
		control.addVehicle(vehicle);
		String vin = vehicle.getVin().getVIN();
		assertEquals("euro3", vehicle.getEngine().getEmission());
		update = new UpdateCar(control);
		String args = vehicle.getVin().getVIN() + CMDS;
		update.execute(args);
		vehicle = control.getByVIN(vehicle.getVin().getVIN());
		assertEquals("euro5", vehicle.getEngine().getEmission());
	}

}
