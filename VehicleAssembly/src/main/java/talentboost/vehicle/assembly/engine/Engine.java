package talentboost.vehicle.assembly.engine;

import talentboost.vehicle.assembly.common.EngineException;
import talentboost.vehicle.assembly.enums.Transmission;
/**
 * {@code} Abstract engine class to hold the fields of the engines,
 * and corresponding getters.
 * @author rados
 *
 */
public abstract class Engine {
	protected Integer KW;
	protected String engineType;
	protected String emission;
	protected Integer displacement;
	protected Integer HP;
	protected Boolean turbo;
	protected Transmission transmission ;

	public Integer getKW() {
		return KW;
	}
	/**
	 * 
	 * {@code}First checks if the current object is an electric engine
	 * and if it is throw an EngineException since electric engines do not have displacement
	 * @throws EngineException
	 */
	public Integer getDisplacement() throws EngineException {
		if (this instanceof ElectricEngine) {
			throw new EngineException("Electric engine does not have displacement!");
		}
		return displacement;
	}
	/**
	 * 
	 * {@code}First checks if the current object is an electric engine
	 * and if it is throw an EngineException since electric engines do not have HP
	 * @throws EngineException
	 */
	public Integer getHP() throws EngineException {
		if (this instanceof ElectricEngine) {
			throw new EngineException("Electric engine does not have transmission!");
		}
		return HP;
	}
	/**
	 * 
	 * {@code}First checks if the current object is an electric engine
	 * and if it is throw an EngineException since electric engines do not have turbo
	 * @throws EngineException
	 */
	public Boolean getTurbo() throws EngineException {
		if (this instanceof ElectricEngine) {
			throw new EngineException("Electric engine does not have turbo!");
		}
		return turbo;
	}
	/**
	 * 
	 * {@code}First checks if the current object is an electric engine
	 * and if it is throw an EngineException since electric engines do not have transmissions
	 * @throws EngineException
	 */
	public Transmission getTransmission() throws EngineException {
		if (this instanceof ElectricEngine) {
			throw new EngineException("Electric engine does not have transmission!");
		}
		return transmission;
	}

	public String getEngineType() {
		return engineType;
	}

	public String getEmission() {
		return emission;
	}

}
