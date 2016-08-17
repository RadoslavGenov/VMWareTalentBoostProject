package talentboost.vehicle.assembly.car;

import talentboost.vehicle.assembly.engine.Engine;
import talentboost.vehicle.assembly.enums.Model;
import talentboost.vehicle.assembly.enums.ModelType;
import talentboost.vehicle.assembly.vin.VIN;
/**
 * {@code} The car class creates an instance of the car through an inner static builder class
 * @author rados
 *
 */
public class Car extends Vehicle {
	// private constructor which is only accessible by the builder
	private Car(CarBuilder builder) {
		this.engine = builder.engine;
		this.model = builder.model;
		this.status = builder.status;
		this.type = builder.type;
		this.vin = builder.vin;
	}

	/**
	 * 
	 * @author rados
	 *{@code} builder that creates a Car object and is the only type that can access
	 *the private Car constructor
	 */
	public static class CarBuilder {
		private VIN vin;
		private Model model;
		private Engine engine;
		private Boolean status = true;
		private ModelType type = ModelType.sedan;

		public CarBuilder(Model model) {
			this.model = model;
		}

		public CarBuilder buildEngine(Engine engine) {
			this.engine = engine;
			return this;
		}

		public CarBuilder buildVINDirectly(String vin) {
			this.vin = new VIN.VinBuilder().setDirectly(vin);
			return this;
		}

		public CarBuilder buildVIN(String ISO, Integer factoryNum) {
			this.vin = new VIN.VinBuilder().addISO(ISO).addFactoryNum(factoryNum).build();
			return this;
		}

		public CarBuilder buildStatus(Boolean status) {
			this.status = status;
			return this;
		}

		public CarBuilder buildType(ModelType type) {
			this.type = type;
			return this;
		}

		public Car build() {
			return new Car(this);
		}
	}
}
