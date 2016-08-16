package talentboost.vehicle.assembly.builder.test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import talentboost.vehicle.assembly.common.EngineException;
import talentboost.vehicle.assembly.engine.CombustionEngine;
import talentboost.vehicle.assembly.engine.ElectricEngine;
import talentboost.vehicle.assembly.enums.Transmission;

/**
 * {@code}Tests whether the engine builder works correctly <b>There is no
 * validator used here so the fields are not checked whether they valid for our
 * scenario. Only if the builder works correctly</b>
 * 
 * @author rados
 */
public class EngineBuilderTest {
	private CombustionEngine combustionEngine;
	private ElectricEngine electricEngine;
	private static final Transmission trans = Transmission.Auto_4;

	@Test
	public void testCombustionEngineBuilder() throws EngineException {
		combustionEngine = new CombustionEngine.CombustionEngineBuilder("P").setEmission("euro4").setEngineDisplacement(2000)
				.setTransmission(trans).setPower(400).addTurbo(true).build();
		assertEquals(CombustionEngine.class, combustionEngine.getClass());
		assertEquals(trans, combustionEngine.getTransmission());
		assertEquals("euro4", combustionEngine.getEmission());
		assertEquals(400, combustionEngine.getKW(), 0);
		assertEquals(true, combustionEngine.getTurbo());
	}
	/**
	 * 
	 * @throws EngineException <b>If a getter is called for an electric engine which is not applicable
	 * an EngineException is thrown</b>
	 */
	@Test(expected = EngineException.class)
	public void testElectricEngineBuilder() throws EngineException {
		electricEngine = new ElectricEngine.ElectricEngineBuilder("E").build();
		assertEquals(ElectricEngine.class, electricEngine.getClass());
		assertEquals("euro6", electricEngine.getEmission());
		assertEquals(535, electricEngine.getKW(), 0);
		//this is where the exception is caused \/\/
		assertEquals(trans, electricEngine.getTransmission());
	}
}
