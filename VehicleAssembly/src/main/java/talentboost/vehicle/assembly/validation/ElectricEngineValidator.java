package talentboost.vehicle.assembly.validation;

import talentboost.vehicle.assembly.common.EngineException;
import talentboost.vehicle.assembly.common.InvalidCommandException;
import talentboost.vehicle.assembly.parse.CreateInputParser;

public class ElectricEngineValidator {
	private static final String ELECTRIC_ENGINE_EMISSION = "euro6";
	private static final Integer ELECTRIC_ENGINE_POWER = 535;
	private CreateInputParser parser;

	public ElectricEngineValidator(CreateInputParser parser) {
		this.parser = parser;
	}

	/**
	 * {@code} Validates whether there is emission and whether it is the correct euro6 standard
	 */
	public Boolean validateEmission() throws EngineException {
		if (parser.getEmission() == null) {
			return true;
		}
		if (parser.getEmission().equalsIgnoreCase(ELECTRIC_ENGINE_EMISSION)) {
			return true;
		} else {
			throw new EngineException("Electric engines have default euro-6 emission stadard!");
		}
	}

	/**
	 * {@code} Validates whether there is power given for the electric engine given and whether it is 
	 * the correct 535KW
	 */
	public Boolean validatePower() throws EngineException {
		if (parser.getDisplacement() == null) {
			return true;
		}
		if (parser.getDisplacement().equals(ELECTRIC_ENGINE_POWER)) {
			return true;
		} else {
			throw new EngineException("Electric engines have default 535kw power!");
		}
	}
	/**
	 * {@code}if transmission is given throws EngineException
	 */
	public Boolean validateTransmission() throws EngineException {
		if (parser.getTransmission() == null) {
			return true;
		}
		throw new EngineException("Electric engines cannot have transmission!");
	}
	/**
	 * {@code}if turbo is given throws EngineException
	 */
	public Boolean validateTurbo() throws EngineException {
		if (parser.getTurbo() == null) {
			return true;
		}
		throw new EngineException("Electric engines cannot have turbo!");
	}
	/**
	 * {@code}function to execute all validation methods together
	 */
	public void validate() throws EngineException {
		validateEmission();
		validatePower();
		validateTransmission();
		validateTurbo();
	}
}
