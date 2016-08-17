package talentboost.vehicle.assembly.car;

import talentboost.vehicle.assembly.engine.Engine;
import talentboost.vehicle.assembly.enums.Model;
import talentboost.vehicle.assembly.enums.ModelType;
import talentboost.vehicle.assembly.vin.VIN;
/**
 * {@code} abstract vehicle class that holds fields both Car and SUV share
 * along with getters, and an overriden toString() method
 * @author rados
 *
 */
public abstract class Vehicle {
	protected VIN vin;
	protected Model model;
	protected Engine engine;
	protected Boolean status;
	protected ModelType type;

	public VIN getVin() {
		return vin;
	}

	public Model getModel() {
		return model;
	}

	public Engine getEngine() {
		return engine;
	}

	public Boolean getStatus() {
		return status;
	}

	public ModelType getType() {
		return type;
	}

	@Override
	public String toString() {
		return this.vin.getVIN() + " | " + this.model.toString() + "  |" + this.type.toString() + "| "
				+ this.engine.getEmission() + "  | " + this.engine.getEngineType();
	}
}
