package talentboost.vehicle.assembly.commands;

import talentboost.vehicle.assembly.car.Vehicle;
import talentboost.vehicle.assembly.common.EngineException;
import talentboost.vehicle.assembly.common.InvalidCommandException;
import talentboost.vehicle.assembly.common.Ratios;
import talentboost.vehicle.assembly.common.Treble;
import talentboost.vehicle.assembly.common.VehicleException;
import talentboost.vehicle.assembly.controls.ControlJDBC;
import talentboost.vehicle.assembly.engine.Engine;
import talentboost.vehicle.assembly.parse.CreateInputParser;
import talentboost.vehicle.assembly.validation.CombustionEngineValidator;

/**
 * @author rados
 *{@code} This class controls the create command and implements
 *the logic for it
 */
public class Create implements ICommands {
	private static final String NAME = "create";
	private static final String[] ENGINE_TYPES = { "P", "D", "E" };
	private static final String CAR = "CAR";
	private static final String ELECTRIC_TYPE = "E";
	private static final String OUTPUT = "vin	              |model| type| emission|  engine type";
	
	private CreateInputParser parser;
	private Engine engine = null;
	private Vehicle vehicle = null;
	private ControlJDBC control;
	
	@Override
	public String getName() {
		return NAME;
	}

	public Create(ControlJDBC control) {
		this.control = control;
	}
	
	/**
	 * @return returns the ouput headers and the vehicle toString()
	 */
	@Override
	public String execute(String args) throws InvalidCommandException, EngineException, VehicleException {
		parser = new CreateInputParser(args);
		String engineType = getEngineType();
		if (engineType.equalsIgnoreCase(ELECTRIC_TYPE)) {
			engine = new CreateEngine().electricCreate(parser);
		} else {
			engine = new CreateEngine().combustionCreate(parser);
		}
		if (parser.getCar().equalsIgnoreCase(CAR)) {
			vehicle = new CreateVehicle().createCar(parser, engine);
		} else {
			vehicle = new CreateVehicle().createSuv(parser, engine);
		}
		control.addVehicle(vehicle);
		return OUTPUT + " \n" + vehicle.toString();
	}
	/**
	 * @return return the type of the engine E, D, or P
	 */
	private String getEngineType() throws InvalidCommandException {
		if (parser.getEngineType() == null) {
			throw new InvalidCommandException("Engine type cannot be empty!");
		}
		for (String TYPE : ENGINE_TYPES) {
			if (parser.getEngineType().equalsIgnoreCase(TYPE)) {
				return TYPE;
			}
		}
		throw new InvalidCommandException("Incorrect engine type for combustion engine!");
	}
}
