package talentboost.vehicle.assembly.commands;

import talentboost.vehicle.assembly.common.EngineException;
import talentboost.vehicle.assembly.common.InvalidCommandException;
import talentboost.vehicle.assembly.common.VehicleException;
/**
 * {@code} Command interface with a contractual agreement to have methods getName and execute
 * @author rados
 *
 */
public interface ICommands {
	String getName();

	String execute(String args) throws InvalidCommandException, EngineException, VehicleException;
}
