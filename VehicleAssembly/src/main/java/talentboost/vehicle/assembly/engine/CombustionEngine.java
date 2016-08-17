package talentboost.vehicle.assembly.engine;

import talentboost.vehicle.assembly.enums.Transmission;
/**
 * {@code} Combustion engine, which inherits engine but has some differences from the 
 * electric engine
 * @author rados
 *
 */
public class CombustionEngine extends Engine {

	// a private constructor only to be used by the builder below
	private CombustionEngine(CombustionEngineBuilder builder) {
		this.HP = builder.HP;
		this.KW = builder.KW;
		this.engineType = builder.engineType;
		this.displacement = builder.displacement;
		this.turbo = builder.turbo;
		this.emission = builder.emission;
		this.transmission = builder.transmission;
	}

	/**
	 * {@code}static engine builder class to builder the engine and set parameters
	 * @author rados
	 *@
	 */
	public static class CombustionEngineBuilder {
		private Integer HP;
		private Integer KW;
		private String engineType;
		private Integer displacement;
		private Boolean turbo;
		private String emission;
		private Transmission transmission;

		// first set the engine type in the constructor
		public CombustionEngineBuilder(String engineType) {
			this.engineType = engineType;
		}

		public CombustionEngineBuilder setHP(Integer HP) {
			this.HP = HP;
			return this;
		}

		// set power
		public CombustionEngineBuilder setPower(Integer KW) {
			this.KW = KW;
			return this;
		}

		// set engine displacement
		public CombustionEngineBuilder setEngineDisplacement(Integer engineDisplacement) {
			this.displacement = engineDisplacement;
			return this;
		}

		// set turbo
		public CombustionEngineBuilder addTurbo(Boolean turbo) {
			this.turbo = turbo;
			return this;
		}

		// set emission
		public CombustionEngineBuilder setEmission(String emission) {
			this.emission = emission;
			return this;
		}

		// set trans
		public CombustionEngineBuilder setTransmission(Transmission transmission) {
			this.transmission = transmission;
			return this;
		}

		// finally build the engine by injecting engine builder in the
		// constructor
		public CombustionEngine build() {
			return new CombustionEngine(this);
		}
	}
}
