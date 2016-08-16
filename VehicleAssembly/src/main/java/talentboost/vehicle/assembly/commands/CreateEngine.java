package talentboost.vehicle.assembly.commands;

import talentboost.vehicle.assembly.common.EngineException;
import talentboost.vehicle.assembly.common.InvalidCommandException;
import talentboost.vehicle.assembly.common.Ratios;
import talentboost.vehicle.assembly.common.Treble;
import talentboost.vehicle.assembly.engine.CombustionEngine;
import talentboost.vehicle.assembly.engine.ElectricEngine;
import talentboost.vehicle.assembly.engine.Engine;
import talentboost.vehicle.assembly.enums.Transmission;
import talentboost.vehicle.assembly.parse.CreateInputParser;
import talentboost.vehicle.assembly.validation.CombustionEngineValidator;
import talentboost.vehicle.assembly.validation.ElectricEngineValidator;
/**
 * 
 * @author rados
 *{@code} this class helpes with the creation of the engine. 
 *To ease the weight on the create class i have split the engine
 *creating and the vehicle creating into two classes
 */
public class CreateEngine {
	private static final String DEFAULT_EMISSION = "euro3";
	private static final Transmission DEFAULT_TRANS = Transmission.Man_4;
	private static final Integer DEFAULT_KW = 74;
	private static final Integer DEFAULT_CC = 1;
	private static final Integer DEFAULT_HP = 76;
	/**
	 * @return returns a created electric engine
	 */
	public Engine electricCreate(CreateInputParser parser) throws InvalidCommandException, EngineException {
		ElectricEngineValidator validator = new ElectricEngineValidator(parser);
		validator.validate();
		return new ElectricEngine.ElectricEngineBuilder(parser.getEngineType()).build();
	}
	/**
	 * {@code} Since the combustion engine needs more processing of input
	 * i have split the creating of the electric and combustion engines
	 * into two methods.
	 * @return return a created combustion engine.
	 */
	public Engine combustionCreate(CreateInputParser parser) throws InvalidCommandException, EngineException {
		CombustionEngineValidator validator = new CombustionEngineValidator(parser);
		Integer tempHP, tempKW, tempDisplacement;
		String tempEmission;
		Boolean tempTurbo;
		Transmission tempTrans;

		if (!validator.validateEmission()) {
			tempEmission = DEFAULT_EMISSION;
		} else {
			tempEmission = parser.getEmission();
		}
		// first=CC second=HP third=KW
		Treble<Integer, Integer, Integer> treble = getPower(validator, parser);
		tempDisplacement = treble.getFirstElement();
		if (!validator.validateTurbo()) {
			tempTurbo = false;
		} else {
			tempTurbo = true;
		}
		if (tempTurbo) {
			tempHP = (int) Math.floor(treble.getSecondElement() * 1.3);
			tempKW = (int) Math.floor(treble.getThirdElement() * 1.3);
		} else {
			tempHP = treble.getSecondElement();
			tempKW = treble.getThirdElement();
		}
		if (!validator.validateTransmission()) {
			tempTrans = DEFAULT_TRANS;
		} else {
			tempTrans = parser.getTransmission();
		}
		return new CombustionEngine.CombustionEngineBuilder(parser.getEngineType()).addTurbo(tempTurbo)
				.setEmission(tempEmission).setHP(tempHP).setEngineDisplacement(tempDisplacement).setPower(tempKW)
				.setTransmission(tempTrans).build();
	}
	
	/**
	 * 
	 * @param uses the validator class to validate all of the fields for the engine
	 * @param uses the parser to get the fields of the engine
	 * @return a Treble holding the HP, KW, and engine displacement
	 */
	public static Treble<Integer, Integer, Integer> getPower(CombustionEngineValidator validator, CreateInputParser parser)
			throws InvalidCommandException, EngineException {
		Integer tempHP, tempKW, tempDisplacement;
		if (validator.validateHP()) {
			if (Ratios.getHptokw().containsKey(parser.getDisplacement())) {
				tempKW = Ratios.getHptokw().get(parser.getDisplacement());
				tempHP = parser.getDisplacement();
				tempDisplacement = Ratios.getKwtocc().get(tempKW);
			} else if (Ratios.getHptokw().containsKey(parser.getDisplacement() + 1)) {//preciseness + 1
				tempKW = Ratios.getHptokw().get(parser.getDisplacement());
				tempHP = parser.getDisplacement();
				tempDisplacement = Ratios.getKwtocc().get(tempKW);
			} else if (Ratios.getHptokw().containsKey(parser.getDisplacement() - 1)) {//preciseness - 1
				tempKW = Ratios.getHptokw().get(parser.getDisplacement());
				tempHP = parser.getDisplacement();
				tempDisplacement = Ratios.getKwtocc().get(tempKW);
			} else {
				throw new InvalidCommandException("We do not have a vehicle with that power!");
			}
		} else if (validator.validateDisplacement()) {
			tempKW = Ratios.getCctokw().get(parser.getDisplacement());
			tempHP = Ratios.getKwtohp().get(tempKW);
			tempDisplacement = Ratios.getKwtocc().get(tempKW);
		} else {
			tempKW = DEFAULT_KW;
			tempDisplacement = DEFAULT_CC;   //else use the defaults, if no other values are given
			tempHP = DEFAULT_HP;
		}
		return new Treble<Integer, Integer, Integer>(tempDisplacement, tempHP, tempKW);
	}
}
