package talentboost.vehicle.assembly.complete.test;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.junit.Test;

import talentboost.vehicle.assembly.common.EngineException;
import talentboost.vehicle.assembly.common.VehicleException;
import talentboost.vehicle.assembly.controls.Input;

public class CompleteTest {
	private static final String PATH = "src\\main\\tests\\talentboost\\vehicle\\assembly\\complete\\test\\CommandsFile.txt";
	private static final String SAMPE_OUTPUT_PATH = "src\\main\\tests\\talentboost\\vehicle\\assembly\\complete\\test\\SampleOutput.txt";

	@Test
	public void test() throws FileNotFoundException, EngineException, VehicleException, IOException {
		Input test = new Input();
		test.processInput(PATH, SAMPE_OUTPUT_PATH);
	}
}
