package talentboost.vehicle.assembly.commands;

import java.util.ArrayList;
import java.util.Arrays;

import talentboost.vehicle.assembly.common.EngineException;
import talentboost.vehicle.assembly.common.InvalidCommandException;
import talentboost.vehicle.assembly.common.VehicleException;
import talentboost.vehicle.assembly.controls.ControlJDBC;
/**
 * {@code} Update command uses the database to update the emission of a given vehicle through its vin
 * @author rados
 *
 */
public class UpdateCar implements ICommands {
	private static final String NAME = "update";
	private ControlJDBC control;

	public UpdateCar(ControlJDBC control) {
		this.control = control;
	}

	@Override
	public String getName() {
		return NAME;
	}

	@Override
	public String execute(String args) throws InvalidCommandException, EngineException, VehicleException {
		if (args == null) {
			throw new InvalidCommandException("Arguments for update cannot be null");
		}
		ArrayList<String> splitArgs = new ArrayList<>(Arrays.asList(args.split("\\s|engine=|-")));
		splitArgs.removeAll(Arrays.asList("", null));
		String vin = splitArgs.get(0);
		String emission =  splitArgs.get(1);
		if (!control.updateEmission(emission, vin)) {//use control to update database
			throw new InvalidCommandException("There was a problem updating the vehicle with vin: " + vin);
		}
		return "";
	}

}
