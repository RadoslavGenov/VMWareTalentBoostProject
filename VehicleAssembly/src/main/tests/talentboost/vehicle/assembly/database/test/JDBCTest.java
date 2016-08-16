package talentboost.vehicle.assembly.database.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;

import org.junit.Test;

import talentboost.vehicle.assembly.car.Vehicle;
import talentboost.vehicle.assembly.commands.Create;
import talentboost.vehicle.assembly.commands.CreateEngine;
import talentboost.vehicle.assembly.commands.CreateVehicle;
import talentboost.vehicle.assembly.common.EngineException;
import talentboost.vehicle.assembly.common.InvalidCommandException;
import talentboost.vehicle.assembly.common.VehicleException;
import talentboost.vehicle.assembly.controls.ControlJDBC;
import talentboost.vehicle.assembly.engine.CombustionEngine;
import talentboost.vehicle.assembly.engine.Engine;
import talentboost.vehicle.assembly.enums.Model;
import talentboost.vehicle.assembly.enums.ModelType;
import talentboost.vehicle.assembly.jdbc.JDBC;
import talentboost.vehicle.assembly.parse.CreateInputParser;

/**
 * {@code} Tests whether the java database connection works correctly
 * 
 * @author rados
 *
 */
public class JDBCTest {

	private static final String CREATE_CAR = "car model=A5-sedan engine=E";
	private static final String CREATE_SUV = " suv model=Q1-hatchback engine=D-6L-euro3 transmission=Auto";

	private JDBC database;
	private Create create;
	private Engine engine;
	private CreateInputParser parser;
	private Vehicle vehicle;
	private ControlJDBC control = new ControlJDBC(new JDBC());

	@Test
	public void testElectricEngineFillingDataBaseAndGettingValues()
			throws EngineException, InvalidCommandException, VehicleException {
		parser = new CreateInputParser(CREATE_CAR);
		create = new Create(control);
		engine = new CreateEngine().electricCreate(parser);
		vehicle = new CreateVehicle().createSuv(parser, engine);
		control.addVehicle(vehicle);
		ArrayList<Vehicle> vehicles = control.getAllVehicles();
		Vehicle test = vehicles.get(0);
		assertEquals(Model.A5, test.getModel());
		assertEquals("euro6", test.getEngine().getEmission());
		assertEquals("E", test.getEngine().getEngineType());
		assertEquals(535, test.getEngine().getKW(), 0);
		assertEquals(ModelType.sedan, test.getType());
	}

	@Test
	public void testCombustionEngineFillingDataBaseAndGettingValues()
			throws EngineException, InvalidCommandException, VehicleException {
		parser = new CreateInputParser(CREATE_SUV);
		create = new Create(control);
		engine = new CreateEngine().combustionCreate(parser);
		vehicle = new CreateVehicle().createSuv(parser, engine);
		control.addVehicle(vehicle);
		ArrayList<Vehicle> vehicles = control.getAllVehicles();
		for (Vehicle test : vehicles) {
			assertNotNull(test.getModel());
			assertNotNull(test.getEngine().getEmission());
			assertNotNull(test.getEngine().getEngineType());
			assertNotNull(test.getEngine().getKW());
			assertNotNull(test.getType());
			if (test.getEngine() instanceof CombustionEngine) {
				assertNotNull(test.getEngine().getTransmission());
				assertNotNull(test.getEngine().getTurbo());
				assertNotNull(test.getEngine().getDisplacement());
				assertNotNull(test.getEngine().getHP());
			}
		}

	}
}
