package talentboost.vehicle.assembly.input.test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import talentboost.vehicle.assembly.common.Pair;
import talentboost.vehicle.assembly.controls.Input;

/**
 * {@code} Tests if the input class parses the line into, first the command and
 * then into the argument
 * 
 * @author rados
 *
 */
public class InputTest {

	private final Input input = new Input();
	private String cmd = null;
	private String args = null;

	private static final String CREATE_CMD = "create";
	private static final String UPDATE_CMD = "update";
	private static final String UPDATE_ARGS = "BG3eurndkghhrngie engine=-euro3";
	private static final String UPDATE_FINAL = UPDATE_CMD + " " + UPDATE_ARGS;
	private static final String FIND_CMD = "find";
	private static final String EMISSION = "euro3";
	private static final String FIND_FINAL = FIND_CMD + " " + EMISSION;
	private static final String CREATE_ARGS = "car engine=E";
	private static final String CREATE_FINAL = CREATE_CMD + " " + CREATE_ARGS;
	private static final String TEST_CMD = "test";
	private static final String TEST_ARG = "test args";
	private static final String LINE = TEST_CMD + " " + TEST_ARG;
	private static final String PRINT = "print";
	private static final String VIN = "BG312345678910123";
	private static final String FINAL = PRINT + " " + VIN;

	@Test
	public void test() {
		Pair<String, String> pair = input.processLine(LINE);
		cmd = pair.getFirstElement();
		args = pair.getSecondElement();
		assertEquals(TEST_CMD, cmd);
		assertEquals(TEST_ARG, args);
	}

	@Test
	public void testPrintCommand() {
		Pair<String, String> pair = input.processLine(FINAL);
		cmd = pair.getFirstElement();
		args = pair.getSecondElement();
		assertEquals(PRINT, cmd);
		assertEquals(VIN, args);
	}

	@Test
	public void testCreateCommand() {
		Pair<String, String> pair = input.processLine(CREATE_FINAL);
		cmd = pair.getFirstElement();
		args = pair.getSecondElement();
		assertEquals(CREATE_CMD, cmd);
		assertEquals(CREATE_ARGS, args);
	}

	@Test
	public void testFindCommand() {
		Pair<String, String> pair = input.processLine(FIND_FINAL);
		cmd = pair.getFirstElement();
		args = pair.getSecondElement();
		assertEquals(FIND_CMD, cmd);
		assertEquals(EMISSION, args);
	}

	@Test
	public void testUpdateCommand() {
		Pair<String, String> pair = input.processLine(UPDATE_FINAL);
		cmd = pair.getFirstElement();
		args = pair.getSecondElement();
		assertEquals(UPDATE_CMD, cmd);
		assertEquals(UPDATE_ARGS, args);
	}

}
