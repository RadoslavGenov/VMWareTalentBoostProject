package talentboost.vehicle.assembly.builder.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.junit.Test;

import talentboost.vehicle.assembly.vin.VIN;

/**
 * {@code}Tests if the vin builder works correctly
 * 
 * @author rados
 *
 */
public class VINBuilderTest {
	@Test
	public void testCreateVinByPassingFactoryFirst() {
		VIN vin = new VIN.VinBuilder().addFactoryNum(4).addISO("DE").build();
		assertTrue(vin.getVIN().startsWith("DE4"));
	}

	@Test
	public void testAssertCountOfUniqueStringsProduced() {
		List<String> testUniqueness = new ArrayList<>();
		for (int i = 0; i < 10000; i++) {
			VIN vin = new VIN.VinBuilder().addISO("BG").addFactoryNum(2).build();
			testUniqueness.add(vin.getVIN());
		}
		assertEquals(10000, testUniqueness.size());
	}
	/**
	 * {@code} tests for the randomness of the vin
	 */
	@Test
	public void testRandomnnessOfVIN() {
		HashSet<String> testRandom = new HashSet<>();
		String first = new VIN.VinBuilder().addISO("BG").addFactoryNum(3).build().getVIN();
		for (int i = 0; i < 1_000_000; i++) {
			testRandom.add(new VIN.VinBuilder().addISO("BG").addFactoryNum(3).build().getVIN());
		}

		assertFalse(testRandom.contains(first));
	}
}
