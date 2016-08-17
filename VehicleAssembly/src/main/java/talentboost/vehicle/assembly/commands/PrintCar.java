package talentboost.vehicle.assembly.commands;

import java.util.ArrayList;

import talentboost.vehicle.assembly.car.Vehicle;
import talentboost.vehicle.assembly.common.EngineException;
import talentboost.vehicle.assembly.common.InvalidCommandException;
import talentboost.vehicle.assembly.common.VehicleException;
import talentboost.vehicle.assembly.controls.ControlJDBC;
/**
 * {@code}Class to execute the print command.
 * @author rados
 *
 */
public class PrintCar implements ICommands {
	private static final String NAME = "print";
	private static final String ALL = "all";
	private static final String OUTPUT = "vin	              |model| type| emission|  engine type \n";
	private ControlJDBC control;

	public PrintCar(ControlJDBC control) {
		this.control = control;
	}

	@Override
	public String getName() {
		return NAME;
	}
	
	@Override
	public String execute(String args) throws InvalidCommandException, EngineException, VehicleException {
		String result = "";
		if (args == null) {
			throw new InvalidCommandException("Arguments for print command cannot be empty!");
		}
		if (args.equalsIgnoreCase(ALL)) {
			result = printAll(control);
		} else {
			Vehicle vehicle = control.getByVIN(args);//if argument is not all, the vin is used to attain
			result = OUTPUT + vehicle.toString();    //the vehicle with the given vin
		}
		return result;
	}
	/**
	 * 
	 * @param control
	 * @return Returns a string of all the vehicles toString() in the database and with a given header
	 */
	private String printAll(ControlJDBC control) {
		ArrayList<Vehicle> vehicles = control.getAllVehicles();
		StringBuilder builder = new StringBuilder();
		for (Vehicle vehicle : vehicles) {
			builder.append(OUTPUT);
			builder.append(vehicle.toString() + "\n");
		}
		return builder.toString();
	}
}
