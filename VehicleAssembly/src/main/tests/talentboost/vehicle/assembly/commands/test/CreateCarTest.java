package talentboost.vehicle.assembly.commands.test;

import static org.junit.Assert.assertEquals;

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
import talentboost.vehicle.assembly.engine.ElectricEngine;
import talentboost.vehicle.assembly.engine.Engine;
import talentboost.vehicle.assembly.enums.Model;
import talentboost.vehicle.assembly.enums.ModelType;
import talentboost.vehicle.assembly.enums.Transmission;
import talentboost.vehicle.assembly.jdbc.JDBC;
import talentboost.vehicle.assembly.parse.CreateInputParser;
/**
 * {@code}Tests whether the creation of Cars, SUVs, CombustionEngine, and ElectricEngine all work together properly and precisley
 * @author rados
 *
 */
public class CreateCarTest {

	private static final String ELECTRIC_ENGINE = "car model=A5-sedan engine=E";
	private static final String COMBUSTION_ENGINE = " suv model=Q1-hatchback engine=D-6L-euro4 transmission=Auto";
	private static final String COMBUSTION_ENGINE_TWO = " car model=A1-sedan engine=P-euro3 transmission=Man";
	private static final String COMBUSTION_ENGINE_THREE = " suv model=Q1 engine=P-1L-euro3 transmission=Man-5";
	private static final String COMBUSTION_ENGINE_FOUR = "car model=A4-sedan engine=D-6L-euro4 transmission=Auto";
	private static final String SUV = " suv model=Q5 engine=D transmission=Auto-6";
	private static final String CAR = " car model=A5 engine=D transmission=Auto-5";
	
	private Create create;
	private Engine engine;
	private CreateInputParser parser;
	private Vehicle vehicle;
	private ControlJDBC control = new ControlJDBC(new JDBC());
	/**
	 * {@code} <b>The EngineException is thrown when the getHP method is called as it does not apply to an electric engine</b>
	 * @throws InvalidCommandException
	 * @throws EngineException
	 * @throws VehicleException
	 */
	@Test(expected = EngineException.class)
	public void testElectricEngineCreation() throws InvalidCommandException, EngineException, VehicleException {
		parser = new CreateInputParser(ELECTRIC_ENGINE);
		create = new Create(control);
		engine = new CreateEngine().electricCreate(parser);
		assertEquals(ElectricEngine.class, engine.getClass());
		assertEquals("euro6", engine.getEmission());
		assertEquals("E", engine.getEngineType());
		assertEquals(535, engine.getKW(), 0);
		assertEquals(535, engine.getHP(), 0);
	}
	/**
	 * {@code} Tests the creation of the combustion engine
	 * @throws InvalidCommandException
	 * @throws EngineException
	 * @throws VehicleException
	 */
	@Test
	public void testCombustionEngineCreation() throws InvalidCommandException, EngineException, VehicleException {
		parser = new CreateInputParser(COMBUSTION_ENGINE);
		create = new Create(control);
		engine = new CreateEngine().combustionCreate(parser);
		assertEquals(CombustionEngine.class, engine.getClass());
		assertEquals("euro4", engine.getEmission());
		assertEquals("D", engine.getEngineType());
		assertEquals(510, engine.getKW(), 1);
		assertEquals(526, engine.getHP(), 1);
	}
	
	@Test
	public void testCombustionEngineCreationDifferentParams()
			throws InvalidCommandException, EngineException, VehicleException {
		parser = new CreateInputParser(COMBUSTION_ENGINE_TWO);
		create = new Create(control);
		engine = new CreateEngine().combustionCreate(parser);
		assertEquals(CombustionEngine.class, engine.getClass());
		assertEquals("euro3", engine.getEmission());
		assertEquals("P", engine.getEngineType());
		assertEquals(74, engine.getKW(), 1);
		assertEquals(76, engine.getHP(), 1);
		assertEquals(2, engine.getDisplacement(), 1);
	}

	@Test
	public void testCombustionEngineCreationDifferentParamsTwo()
			throws InvalidCommandException, EngineException, VehicleException {
		parser = new CreateInputParser(COMBUSTION_ENGINE_THREE);
		create = new Create(control);
		engine = new CreateEngine().combustionCreate(parser);
		assertEquals(CombustionEngine.class, engine.getClass());
		assertEquals("euro3", engine.getEmission());
		assertEquals("P", engine.getEngineType());
		assertEquals(74, engine.getKW(), 1);
		assertEquals(76, engine.getHP(), 1);
		assertEquals(1, engine.getDisplacement(), 1);
		assertEquals(Transmission.Man_5, engine.getTransmission());
	}

	@Test
	public void testCombustionEngineCreationDifferentParamsThree()
			throws InvalidCommandException, EngineException, VehicleException {
		parser = new CreateInputParser(COMBUSTION_ENGINE_FOUR);
		create = new Create(control);
		engine = new CreateEngine().combustionCreate(parser);
		assertEquals(CombustionEngine.class, engine.getClass());
		assertEquals("euro4", engine.getEmission());
		assertEquals("D", engine.getEngineType());
		assertEquals(510, engine.getKW(), 1);
		assertEquals(526, engine.getHP(), 1);
		assertEquals(6, engine.getDisplacement(), 1);
		assertEquals(Transmission.Auto_4, engine.getTransmission());
	}
	/**
	 * {@code} Tests creation of SUV
	 * @throws InvalidCommandException
	 * @throws VehicleException
	 * @throws EngineException
	 */
	@Test
	public void testSUVCreation() throws InvalidCommandException, VehicleException, EngineException {
		parser = new CreateInputParser(SUV);
		create = new Create(control);
		engine = new CreateEngine().combustionCreate(parser);
		vehicle = new CreateVehicle().createSuv(parser, engine);
		assertEquals(CombustionEngine.class, vehicle.getEngine().getClass());
		assertEquals("euro3", vehicle.getEngine().getEmission());
		assertEquals("D", vehicle.getEngine().getEngineType());
		assertEquals(74, vehicle.getEngine().getKW(), 1);
		assertEquals(76, vehicle.getEngine().getHP(), 1);
		assertEquals(1, vehicle.getEngine().getDisplacement(), 0);
		assertEquals(Transmission.Auto_6, vehicle.getEngine().getTransmission());
		assertEquals(Model.Q5, vehicle.getModel());
		assertEquals(ModelType.sedan, vehicle.getType());
		assertEquals("SUV", vehicle.getClass().getSimpleName());
	}
	/**
	 * {@code} Tests creation of car
	 * @throws InvalidCommandException
	 * @throws VehicleException
	 * @throws EngineException
	 */
	@Test
	public void testCarCreation() throws InvalidCommandException, VehicleException, EngineException {
		parser = new CreateInputParser(CAR);
		create = new Create(control);
		engine = new CreateEngine().combustionCreate(parser);
		vehicle = new CreateVehicle().createCar(parser, engine);
		assertEquals(CombustionEngine.class, vehicle.getEngine().getClass());
		assertEquals("euro3", vehicle.getEngine().getEmission());
		assertEquals("D", vehicle.getEngine().getEngineType());
		assertEquals(74, vehicle.getEngine().getKW(), 1);
		assertEquals(76, vehicle.getEngine().getHP(), 1);
		assertEquals(1, vehicle.getEngine().getDisplacement(), 0);
		assertEquals(Transmission.Auto_5, vehicle.getEngine().getTransmission());
		assertEquals(Model.A5, vehicle.getModel());
		assertEquals(ModelType.sedan, vehicle.getType());
		assertEquals("Car", vehicle.getClass().getSimpleName());
	}
}
