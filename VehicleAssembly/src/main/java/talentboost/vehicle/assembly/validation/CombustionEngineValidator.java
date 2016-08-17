package talentboost.vehicle.assembly.validation;

import java.util.Arrays;

import talentboost.vehicle.assembly.common.EngineException;
import talentboost.vehicle.assembly.enums.Transmission;
import talentboost.vehicle.assembly.parse.CreateInputParser;
/**
 * {@code}Validates the given input by the CreateInputParser to ease the 
 * process of creating a car
 * @author rados
 */
public class CombustionEngineValidator {
	private static final String[] COMBUSTION_ENGINE_EMISSION = { "euro6", "euro5", "euro4", "euro3" };
	private static final Integer[] COMBUSTION_ENGINE_POWER_HP = { 76, 99, 138, 179, 253, 264, 328, 341, 526, 759, 339,
			444, 684, 987 };
	private static final Integer[] COMBUSTION_ENGINE_DISPLACEMENT = { 1, 2, 3, 4, 5, 6, 8 };
	private static final Transmission[] TRANSMISSIONS = { Transmission.Auto_4, Transmission.Auto_5, Transmission.Auto_6,
			Transmission.Auto_7, Transmission.Auto_8, Transmission.Man_4, Transmission.Man_5, Transmission.Man_6 };
	private CreateInputParser parser;

	public CombustionEngineValidator(CreateInputParser parser) {
		this.parser = parser;
	}
	/**
	 * {@code} Validates whether there is emission
	 */
	public Boolean validateEmission() throws EngineException {
		if (parser.getEmission() == null) {
			return false;
		}
		for (String EMISSION : COMBUSTION_ENGINE_EMISSION) {
			if (parser.getEmission().equalsIgnoreCase(EMISSION)) {
				return true;
			}
		}
		throw new EngineException("Incorrect engine emission for electric engine!");
	}
	/**
	 * {@code} Validates whether there is displacement given
	 */
	public Boolean validateDisplacement() throws EngineException {
		if (parser.getDisplacement() == null) {
			return false;
		}
		if (Arrays.asList(COMBUSTION_ENGINE_POWER_HP).contains(parser.getDisplacement())) {
			return false;
		}
		for (Integer DISPLACEMENT : COMBUSTION_ENGINE_DISPLACEMENT) {
			if (parser.getDisplacement().equals(DISPLACEMENT)) {
				return true;
			}
		}
		throw new EngineException("Incorrect engine power for combustion engine!");
	}
	/**
	 * {@code} Validates whether there is HP given
	 */
	public Boolean validateHP() throws EngineException {
		if (parser.getDisplacement() == null) {
			return false;
		}
		if (Arrays.asList(COMBUSTION_ENGINE_DISPLACEMENT).contains(parser.getDisplacement())) {
			return false;
		}
		for (Integer HP : COMBUSTION_ENGINE_POWER_HP) {
			if (parser.getDisplacement().equals(HP)) {
				return true;
			}
		}
		throw new EngineException("Incorrect engine power for combustion engine!");
	}
	/**
	 * {@code} Validates whether there is transmission given
	 */
	public Boolean validateTransmission() throws EngineException {
		if (parser.getTransmission() == null) {
			return false;
		}
		for (Transmission TRANS : TRANSMISSIONS) {
			if (parser.getTransmission().equals(TRANS)) {
				return true;
			}
		}
		throw new EngineException("Incorrect transmission for combustion engine!");
	}

	public Boolean validateTurbo() {
		if (parser.getTurbo() == null) {
			return false;
		} else if (parser.getTurbo()) {
			return true;
		} else {
			return false;
		}
	}
}