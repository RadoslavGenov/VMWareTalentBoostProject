package talentboost.vehicle.assembly.parse;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import talentboost.vehicle.assembly.common.InvalidCommandException;
import talentboost.vehicle.assembly.common.VehicleException;
import talentboost.vehicle.assembly.enums.Model;
import talentboost.vehicle.assembly.enums.ModelType;
import talentboost.vehicle.assembly.enums.Transmission;

/**
 * {@code}This is a helper class which parses the user input for the create
 * command into different fields so that they are easily accessed by the
 * Validator and the CreateCar class. Also contains getter for the given fields
 * 
 * @author rados
 * @see CreateCar.class VehicleValidator.class CombustionEngineValidator.class
 *      ElectricEngineValidator.class
 */
public class CreateInputParser {
	private List<ModelType> types = new ArrayList<ModelType>() {
		private static final long serialVersionUID = 1L;
		// holds the available models
		{
			add(ModelType.hatchback);
			add(ModelType.kombi);
			add(ModelType.sedan);
		}
	};
	private List<Transmission> transmissions = new ArrayList<Transmission>() {
		private static final long serialVersionUID = 1L;
		// holds the available transmissions
		{
			add(Transmission.Auto_4);
			add(Transmission.Auto_5);
			add(Transmission.Auto_6);
			add(Transmission.Auto_7);
			add(Transmission.Auto_8);
			add(Transmission.Man_4);
			add(Transmission.Man_5);
			add(Transmission.Man_6);
		}
	};
	// fields to parse the input from the users
	private String engineType;
	private Integer displacement;
	private Boolean turbo;
	private String emission;
	private Transmission transmission;
	private ModelType type;
	private Model model;
	private String car;

	private String input;

	public CreateInputParser(String input) throws InvalidCommandException, VehicleException {
		this.input = input;
		parse(this.input);
	}

	/**
	 * {@code}If the field is filled from the user then it is set in the given
	 * fields above.<b>If the field is empty or not set it is then filled with
	 * null, it is up to the validators to validate the default input for the
	 * given null fields</b>
	 * @param input The input from the user
	 * @throws InvalidCommandException
	 * @throws VehicleException
	 */
	private void parse(String input) throws InvalidCommandException, VehicleException {
		ArrayList<String> args = new ArrayList<>(Arrays.asList(input.split("\\s|\\{|\\}|-|model=")));
		args.removeAll(Arrays.asList("", null));
		this.car = args.get(0); // the first two elements are expected
		try {
			this.model = Model.valueOf(args.get(1));// to be the car and model
		} catch (IllegalArgumentException e) {
			throw new VehicleException("Cannot have this as a model! ");
		}
		findModelType(args);// build options
		findPower(args);
		findTurbo(args);
		findEmission(args);
		findTransmission(args);
		findEngineType(args);
	}

	/**
	 * {@code}checks if a model exists
	 * @param args
	 * @return
	 */
	private Boolean findModelType(ArrayList<String> args) {
		for (String s : args) {
			for (ModelType type : types) {
				if (type.toString().equalsIgnoreCase(s)) {
					this.type = type;
					return true;
				}
			}
		}
		this.type = null;
		return false;
	}

	/**
	 * {@code}checks if an engine type exists
	 * @param args
	 * @return
	 */
	private Boolean findEngineType(ArrayList<String> args) {
		for (String s : args) {
			if (s.toLowerCase().contains("engine")) {
				this.engineType = s.substring(s.length() - 1);
				return true;
			}
		}
		this.engineType = null;
		return false;
	}
	/**
	 * {@code} Checks if power is given as input
	 * @param args
	 * @return
	 */
	private Boolean findPower(ArrayList<String> args) {
		for (String s : args) {
			if (s.toLowerCase().contains("l")) {
				this.displacement = Integer.parseInt(s.substring(0, s.length() - 1));
				return true;
			}
			if (s.toLowerCase().contains("hp")) {
				this.displacement = Integer.parseInt(s.substring(0, s.length() - 2));
				return true;
			}
		}
		this.displacement = null;
		return false;
	}
	/**
	 * {@code}checks if a turbo is given
	 * @param args
	 * @return
	 */
	private Boolean findTurbo(ArrayList<String> args) {
		for (String s : args) {
			if (s.equalsIgnoreCase("T")) {
				this.turbo = true;
				return true;
			}
		}
		this.turbo = null;
		return false;
	}

	/**
	 * {@code}checks if a emission exists
	 * @param args
	 * @return
	 */
	private Boolean findEmission(ArrayList<String> args) {
		for (String s : args) {
			if (s.toLowerCase().contains("euro")) {
				this.emission = s;
				return true;
			}
		}
		this.emission = null;
		return false;
	}
	/**
	 * {@code}checks if a transmission exists <b>If only either auto/man are given
	 * adds on a 4 speed transmission to either the auto or manual transmission</b>
	 * @param args
	 * @return
	 */
	private Boolean findTransmission(ArrayList<String> args) {
		Integer speeds;
		StringBuilder trans = new StringBuilder();
		this.transmission = Transmission.Man_4;
		for (String s : args) {
			if (s.toLowerCase().contains("transmission")) {
				trans.append(s.substring(13, s.length()));
				break;
			}
		}
		boolean flag = false;
		for (String s : args) {
			flag = tryParse(s);
			if (flag) {
				speeds = Integer.parseInt(s);
				trans.append("_");
				trans.append(s);
				break;
			}
		}
		if (!flag) {
			if (trans.toString().equalsIgnoreCase("auto")) {
				this.transmission = Transmission.Auto_4;
				return true;
			} else if (trans.toString().equalsIgnoreCase("man")) {
				this.transmission = Transmission.Man_4;
				return true;
			}
		}
		for (Transmission s : transmissions) {
			if (s.toString().equalsIgnoreCase(trans.toString())) {
				this.transmission = s;
				return true;
			}
		}
		this.transmission = null;
		return false;
	}

	private Boolean tryParse(String str) {
		try {
			Integer.parseInt(str);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	public ModelType getType() {
		return type;
	}

	public Model getModel() {
		return model;
	}

	public String getCar() {
		return car;
	}

	public String getEngineType() {
		return engineType;
	}

	public Integer getDisplacement() {
		return displacement;
	}

	public Boolean getTurbo() {
		return turbo;
	}

	public String getEmission() {
		return emission;
	}

	public Transmission getTransmission() {
		return transmission;
	}
}
