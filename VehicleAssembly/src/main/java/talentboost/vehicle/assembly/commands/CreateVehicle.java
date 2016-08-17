package talentboost.vehicle.assembly.commands;

import talentboost.vehicle.assembly.car.Car;
import talentboost.vehicle.assembly.car.SUV;
import talentboost.vehicle.assembly.car.Vehicle;
import talentboost.vehicle.assembly.common.VehicleException;
import talentboost.vehicle.assembly.engine.Engine;
import talentboost.vehicle.assembly.enums.Model;
import talentboost.vehicle.assembly.enums.ModelType;
import talentboost.vehicle.assembly.parse.CreateInputParser;
import talentboost.vehicle.assembly.validation.VehicleValidator;
/**
 * {@code} This class eases the weight of work for the create class, and the create command
 * along with the CreateEngine class
 * @author rados
 *
 */
public class CreateVehicle {
	private static final ModelType DEFAULT_MODEL_TYPE = ModelType.sedan;
	private static final String ISO = "GB";
	private static final Integer FACTORY_NUMBER = 4;
	/**
	 * @param parser Uses the parser to get the fields of the Car to be built
	 * @param engine Takes an engine as input
	 * @return return Returns a newly created Car
	 */
	public Vehicle createCar(CreateInputParser parser, Engine engine) throws VehicleException {
		VehicleValidator validator = new VehicleValidator(parser);
		Model model;
		ModelType modelType;
		validator.validateModel(); //use validator to validate model
		model = parser.getModel();
		if (!validator.validateModelType()) {//validate the modelType
			modelType = DEFAULT_MODEL_TYPE;
		} else {
			modelType = parser.getType();
		}
		return new Car.CarBuilder(model).buildEngine(engine).buildStatus(true).buildType(modelType)
				.buildVIN(ISO, FACTORY_NUMBER).build();
	}
	/**
	 * 
	 * @param parser Uses the parser to get the fields of the Car to be built
	 * @param engine Takes an engine as input
	 * @return Returns a newly created SUV
	 */
	public Vehicle createSuv(CreateInputParser parser, Engine engine) throws VehicleException {
		VehicleValidator validator = new VehicleValidator(parser);
		Model model;
		ModelType modelType;
		validator.validateModel(); //validate the model
		model = parser.getModel();
		if (!validator.validateModelType()) {//validate the modelType
			modelType = DEFAULT_MODEL_TYPE;
		} else {
			modelType = parser.getType();
		}
		return new SUV.SUVBuilder(model).buildEngine(engine).buildStatus(true).buildType(modelType)
				.buildVIN(ISO, FACTORY_NUMBER).build();
	}
}
