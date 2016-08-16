package talentboost.vehicle.assembly.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import talentboost.vehicle.assembly.car.Vehicle;
import talentboost.vehicle.assembly.common.EngineException;
import talentboost.vehicle.assembly.common.InvalidCommandException;
import talentboost.vehicle.assembly.common.VehicleException;
import talentboost.vehicle.assembly.controls.ControlJDBC;
/**
 * {@code} Class to execute the find command and finds a vehicle with a given emission as argument
 * @author rados
 *
 */
public class FindCar implements ICommands {
	private static final String NAME = "find";
	private static final String OUTPUT = "vin	              |model| type| emission|  engine type \n";
	private static final List<String> EMISSIONS = Arrays.asList(new String[]{ "euro3", "euro4", "euro5", "euro6" });
	private ControlJDBC control;

	public FindCar(ControlJDBC control) {
		this.control = control;
	}

	@Override
	public String getName() {
		return NAME;
	}

	@Override
	public String execute(String args) throws InvalidCommandException, EngineException, VehicleException {
		if (args == null) {
			throw new InvalidCommandException("Arguments for find command cannot be empty!");
		}
		if (!EMISSIONS.contains(args)) {//check if emission is valid
			throw new InvalidCommandException("Not valid emission for vehicles! ");
		}
		StringBuilder builder = new StringBuilder();
		ArrayList<Vehicle> vehicles = control.findByEmission(args);//use control to get vehicles with given emission from database
		for (Vehicle vehicle : vehicles) {
			builder.append(OUTPUT);
			builder.append(vehicle.toString() + "\n");
		}
		return builder.toString();
	}
}
