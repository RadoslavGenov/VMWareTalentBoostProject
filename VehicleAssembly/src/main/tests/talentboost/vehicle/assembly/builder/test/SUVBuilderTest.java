package talentboost.vehicle.assembly.builder.test;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import talentboost.vehicle.assembly.car.SUV;
import talentboost.vehicle.assembly.car.Vehicle;
import talentboost.vehicle.assembly.engine.CombustionEngine;
import talentboost.vehicle.assembly.enums.Model;
import talentboost.vehicle.assembly.enums.ModelType;
import talentboost.vehicle.assembly.enums.Transmission;
import talentboost.vehicle.assembly.vin.VIN;
/**
 * {@code}Tests whether the suv builder works correctly <b>There is no validator used here
 * so the fields are not checked whether they valid for our scenario. Only if the builder works correctly</b>
 * @author rados
 *
 */
public class SUVBuilderTest {
	
	private CombustionEngine mockEngine;
	private Vehicle testVehicle;
	private final VIN vin = new VIN.VinBuilder().addISO("BG").addFactoryNum(3).build();
	private static final ModelType type = ModelType.hatchback;
	private static final Model model = Model.Q1;
	private static final Transmission trans = Transmission.Auto_4;
	private static final Boolean status = true;
	
	@Before
	public void setUp(){
		mockEngine = new CombustionEngine.CombustionEngineBuilder("P")
				.setEmission("euro4")
				.setEngineDisplacement(2000)
				.setTransmission(trans)
				.setPower(400)
				.addTurbo(true)
				.build();
		
		testVehicle = new SUV.SUVBuilder(model)
				.buildEngine(mockEngine)
				.buildVINDirectly(vin.getVIN())
				.buildType(type)
				.buildStatus(status)
				.build();
		
	}
	
	@Test
	public void testSUVBuilder() {
		assertEquals(SUV.class, testVehicle.getClass());
		assertEquals(mockEngine, testVehicle.getEngine());
		assertEquals(model, testVehicle.getModel());
		assertEquals(status, testVehicle.getStatus());
		assertEquals(type, testVehicle.getType());
		assertEquals(vin.getVIN().trim(), testVehicle.getVin().toString().trim());
	}

}
