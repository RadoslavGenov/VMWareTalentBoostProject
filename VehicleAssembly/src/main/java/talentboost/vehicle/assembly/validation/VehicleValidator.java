package talentboost.vehicle.assembly.validation;

import talentboost.vehicle.assembly.common.VehicleException;
import talentboost.vehicle.assembly.enums.Model;
import talentboost.vehicle.assembly.enums.ModelType;
import talentboost.vehicle.assembly.parse.CreateInputParser;
/**
 * {@code}Validates if the given inputs for the vehicle are correct
 * @author rados
 *
 */
public class VehicleValidator {
	private static final Model[] MODELS = { Model.A1, Model.A2, Model.A3, Model.A4, Model.A5, Model.A6, Model.A7,
			Model.A8, Model.Q1, Model.Q2, Model.Q3, Model.Q4, Model.Q5, Model.Q6, Model.Q7, Model.Q8 };
	private static final ModelType[] MODEL_TYPES = { ModelType.hatchback, ModelType.kombi, ModelType.sedan };

	private CreateInputParser parser;

	public VehicleValidator(CreateInputParser parser) {
		this.parser = parser;
	}
	/**
	 * {@code}Validates if the model is the correct and applicable one
	 */
	public Boolean validateModel() throws VehicleException {
		if (parser.getModel() == null) {
			throw new VehicleException("Vehicle model cannot be empty!");
		}
		for(Model MODEL : MODELS){
			if (MODEL.equals(parser.getModel())) {
				return true;
			}
		}
		throw new VehicleException("That is not a correct vehicle model!");
	}
	/**
	 * {@code}Validates if the model type is the correct and applicable one
	 */
	public Boolean validateModelType() throws VehicleException{
		if (parser.getType() == null) {
			return false;
		}
		for(ModelType TYPE : MODEL_TYPES){
			if (TYPE.equals(parser.getType())) {
				return true;
			}
		}
		throw new VehicleException("That is not a correct vehicle model type!");
	}
}
