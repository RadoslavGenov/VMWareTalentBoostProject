package talentboost.vehicle.assembly.engine;
/**
 * {@code} Electric engine, which inherits engine but has differences from the 
 * combustion engine
 * @author rados
 *
 */
public class ElectricEngine extends Engine {
	// private contructor to be used only by builder
	private ElectricEngine(ElectricEngineBuilder builder) {
		this.emission = builder.emission;
		this.engineType = builder.engineType;
		this.KW = builder.KW;
	}

	/**
	 * {@code}static inner class builder to build the electric engine
	 * @author rados
	 *
	 */
	public static class ElectricEngineBuilder {
		private final String emission = "euro6";
		private final Integer KW = 535;
		private String engineType;

		public ElectricEngineBuilder(String engineType) {
			this.engineType = engineType;
		}

		public ElectricEngine build() {
			return new ElectricEngine(this);
		}
	}
}
