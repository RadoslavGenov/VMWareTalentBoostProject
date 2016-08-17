package talentboost.vehicle.assembly.commands;

import talentboost.vehicle.assembly.common.EngineException;
import talentboost.vehicle.assembly.common.InvalidCommandException;
import talentboost.vehicle.assembly.common.VehicleException;
import talentboost.vehicle.assembly.controls.ControlJDBC;
/**
 * {@code} Command to disassemble a car. Uses the database to 
 * update the status of the car to be disassebled to false
 * @author rados
 *
 */
public class DisassembleCar implements ICommands {
	private static final String NAME = "disassemble";
	private ControlJDBC control;

	public DisassembleCar(ControlJDBC control) {
		this.control = control;
	}

	@Override
	public String getName() {
		return NAME;
	}

	@Override
	public String execute(String args) throws InvalidCommandException, EngineException, VehicleException {
		if (args == null) {
			throw new InvalidCommandException("Arguements cannot be empty for disassemble command");
		}
		if (!control.disassemble(args)) {//call control to change fields of vehicle in database
			throw new VehicleException("The vehicle with this vin was not found");
		}
		return "";
	}

}
