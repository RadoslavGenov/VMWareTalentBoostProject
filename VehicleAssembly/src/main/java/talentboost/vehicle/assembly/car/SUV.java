package talentboost.vehicle.assembly.car;

import talentboost.vehicle.assembly.engine.Engine;
import talentboost.vehicle.assembly.enums.Model;
import talentboost.vehicle.assembly.enums.ModelType;
import talentboost.vehicle.assembly.vin.VIN;
/**
 * {@code} The suv class creates an instance of the suv through an inner static builder class
 * @author rados
 *
 */
public class SUV extends Vehicle {
	// private constructor which is only accessible by the builder
	private SUV(SUVBuilder builder) {
		this.engine = builder.engine;
		this.model = builder.model;
		this.status = builder.status;
		this.type = builder.type;
		this.vin = builder.vin;
	}
	/**
	 * 
	 * @author rados
	 *{@code} builder that creates a Suv object and is the only type that can access
	 *the private SUV constructor
	 */
	public static class SUVBuilder {
		private VIN vin;
		private Model model;
		private Engine engine;
		private Boolean status = true;
		private ModelType type = ModelType.sedan;
		

		public SUVBuilder(Model model){
			this.model = model;
		}
		
		public SUVBuilder buildEngine(Engine engine){
			this.engine = engine;
			return this;
		}
		
		public SUVBuilder buildVINDirectly(String vin){
			this.vin = new VIN.VinBuilder().setDirectly(vin);
			return this;
		}
		
		public SUVBuilder buildVIN(String ISO, Integer factoryNum) {
			this.vin = new VIN.VinBuilder().addISO(ISO).addFactoryNum(factoryNum).build();
			return this;
		}
		
		public SUVBuilder buildStatus(Boolean status){
			this.status = status;
			return this;
		}
		
		public SUVBuilder buildType(ModelType type){
			this.type = type;
			return this;
		}
		
		public SUV build(){
			return new SUV(this);
		}
	}
}
