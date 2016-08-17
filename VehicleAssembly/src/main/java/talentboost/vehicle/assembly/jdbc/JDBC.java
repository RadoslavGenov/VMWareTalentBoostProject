package talentboost.vehicle.assembly.jdbc;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Properties;

import talentboost.vehicle.assembly.car.Car;
import talentboost.vehicle.assembly.car.SUV;
import talentboost.vehicle.assembly.car.Vehicle;
import talentboost.vehicle.assembly.common.EngineException;
import talentboost.vehicle.assembly.common.VehicleException;
import talentboost.vehicle.assembly.engine.CombustionEngine;
import talentboost.vehicle.assembly.engine.ElectricEngine;
import talentboost.vehicle.assembly.engine.Engine;
import talentboost.vehicle.assembly.enums.Model;
import talentboost.vehicle.assembly.enums.ModelType;
import talentboost.vehicle.assembly.enums.Transmission;
/**
 * {@code} Database connection to implement almost, if not all of the commands available.
 * I used the PostgreSQL Database to run the sql code
 * @author rados
 *
 */
public class JDBC {
	private Connection connection;
	private static final String INSERT_VEHICLE = "INSERT INTO VEHICLES VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	private static final String SELECT_ALL_VEHICLES = "SELECT * FROM VEHICLES";
	private static final String SELECT_BY_VIN = "SELECT * FROM VEHICLES WHERE VIN=";
	private static final String DISASSEMBLE = "UPDATE VEHICLES SET STATUS=FALSE WHERE VIN=";
	private static final String FIND = "SELECT * FROM VEHICLES WHERE ENGINEEMISSION=";
	private static final String UPDATE_EMISSION = "UPDATE VEHICLES SET ENGINEEMISSION=";
	private static final String WHERE_VIN = "WHERE VIN=";
	/**
	 * initialize the database and make the connection
	 * @see database.properties
	 */
	public JDBC() {
		try {
			Properties props = new Properties();
			FileInputStream stream = new FileInputStream(
					"src/main/java/talentboost/vehicle/assembly/jdbc/database.properties");
			props.load(stream);
			String pass = props.getProperty("db.pass");
			String url = props.getProperty("db.url");
			String user = props.getProperty("db.user");
			String driver = props.getProperty("db.driver");
			Class.forName(driver);
			connection = DriverManager.getConnection(url, user, pass);
		} catch (IOException | ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 
	 * {@code} Add vehicle is a method that adds a given vehicle to the database,
	 * I have a check to see if the engine is electric and if it is I add null values
	 * since the electric engine does not have some of the fields a combustion engine does
	 * @param vehicle Adds the given vehicle to the database
	 * @throws EngineException
	 */
	public void addVehicle(Vehicle vehicle) throws EngineException {
		try {
			PreparedStatement statement = connection.prepareStatement(INSERT_VEHICLE);
			statement.setString(1, vehicle.getVin().toString());
			statement.setString(2, vehicle.getModel().toString());
			statement.setString(3, vehicle.getType().toString());
			statement.setString(4, vehicle.getClass().getSimpleName());
			statement.setBoolean(5, true);
			statement.setString(6, vehicle.getEngine().getEngineType());
			statement.setString(7, vehicle.getEngine().getEmission());
			statement.setInt(9, vehicle.getEngine().getKW());

			if (!vehicle.getEngine().getEngineType().equalsIgnoreCase("E")) {
				statement.setInt(8, vehicle.getEngine().getDisplacement());
				statement.setString(10, vehicle.getEngine().getTransmission().toString());
				statement.setBoolean(11, vehicle.getEngine().getTurbo());
				statement.setInt(12, vehicle.getEngine().getHP());
			} else {
				statement.setNull(8, Types.INTEGER);
				statement.setString(10, null);
				statement.setNull(11, Types.BOOLEAN);
				statement.setNull(12, Types.INTEGER);
			}

			statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	/**
	 * {@code} While there is data in the database I get each field one by one and re-create the
	 * given car/suv and electric/combustion engine and then I return the ArrayList
	 * @return Returns an ArrayList of all of the current vehicles in the database
	 */
	public ArrayList<Vehicle> getAllVehicles() {
		ArrayList<Vehicle> vehicles = new ArrayList<>();
		try {
			PreparedStatement statement = connection.prepareStatement(SELECT_ALL_VEHICLES);
			ResultSet results = statement.executeQuery();

			while (results.next()) {
				String vin = results.getString("vin");
				String model = results.getString("model");
				String modelType = results.getString("modelType");
				String vehicleType = results.getString("vehicleType");
				Boolean status = results.getBoolean("status");
				String engineType = results.getString("engineType");
				String engineEmission = results.getString("engineEmission");
				Integer engineDisplacement = results.getInt("engineDisplacement");
				Integer enginePower = results.getInt("enginePower");
				String transmission = results.getString("engineTransmission");
				Boolean turbo = results.getBoolean("engineTurbo");
				Integer powerHP = results.getInt("engineHP");

				Vehicle vehicle = null;
				Engine engine = null;
				if (engineType.equalsIgnoreCase("E")) {
					engine = new ElectricEngine.ElectricEngineBuilder(engineType).build();
				} else {
					engine = new CombustionEngine.CombustionEngineBuilder(engineType).addTurbo(turbo)
							.setEmission(engineEmission).setEngineDisplacement(engineDisplacement).setHP(powerHP)
							.setPower(enginePower).setTransmission(Transmission.valueOf(transmission)).build();
				}
				if (vehicleType.equalsIgnoreCase("SUV")) {
					vehicle = new SUV.SUVBuilder(Model.valueOf(model)).buildEngine(engine).buildStatus(status)
							.buildType(ModelType.valueOf(modelType)).buildVINDirectly(vin).build();
				} else {
					vehicle = new Car.CarBuilder(Model.valueOf(model)).buildEngine(engine).buildStatus(status)
							.buildType(ModelType.valueOf(modelType)).buildVINDirectly(vin).build();
				}

				vehicles.add(vehicle);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return vehicles;
	}
	/**
	 * {@code} I use query to find the car with the given vin and extract it from the DB
	 * @param vin Uses the cars vin to find the car with the same vin
	 * @return Returns the car with the given vin
	 * @throws VehicleException
	 */
	public Vehicle getByVIN(String vin) throws VehicleException {
		Vehicle vehicle = null;
		try {
			PreparedStatement statement = connection.prepareStatement(SELECT_BY_VIN + "'" + vin + "'");
			ResultSet results = statement.executeQuery();

			while (results.next()) {
				vin = results.getString("vin");
				String model = results.getString("model");
				String modelType = results.getString("modelType");
				String vehicleType = results.getString("vehicleType");
				Boolean status = results.getBoolean("status");
				String engineType = results.getString("engineType");
				String engineEmission = results.getString("engineEmission");
				Integer engineDisplacement = results.getInt("engineDisplacement");
				Integer enginePower = results.getInt("enginePower");
				String transmission = results.getString("engineTransmission");
				Boolean turbo = results.getBoolean("engineTurbo");
				Integer powerHP = results.getInt("engineHP");

				Engine engine = null;
				if (engineType.equalsIgnoreCase("E")) {
					engine = new ElectricEngine.ElectricEngineBuilder(engineType).build();
				} else {
					engine = new CombustionEngine.CombustionEngineBuilder(engineType).addTurbo(turbo)
							.setEmission(engineEmission).setEngineDisplacement(engineDisplacement).setHP(powerHP)
							.setPower(enginePower).setTransmission(Transmission.valueOf(transmission)).build();
				}
				if (vehicleType.equalsIgnoreCase("SUV")) {
					vehicle = new SUV.SUVBuilder(Model.valueOf(model)).buildEngine(engine).buildStatus(status)
							.buildType(ModelType.valueOf(modelType)).buildVINDirectly(vin).build();
				} else {
					vehicle = new Car.CarBuilder(Model.valueOf(model)).buildEngine(engine).buildStatus(status)
							.buildType(ModelType.valueOf(modelType)).buildVINDirectly(vin).build();
				}
			}
			return vehicle;
		} catch (SQLException e) {
			e.getStackTrace();
			throw new VehicleException("There is a problem finding vehicle with vin " + vin);
		}
	}
	/**
	 * {@code} I update the vehicle with the given vin and set the status to False, (which means that it is disassembled)
	 * @param vin Use the vin to find the vehicle to disassemble with an sql query
	 * @return Boolean
	 * @throws VehicleException
	 */
	public Boolean disassemble(String vin) throws VehicleException {
		if (getByVIN(vin) == null) {
			throw new VehicleException("No vehicle with vin: " + vin + " exists!");
		}
		try {
			PreparedStatement statement = connection.prepareStatement(DISASSEMBLE + "'" + vin + "'");
			statement.executeUpdate();
			return true;
		} catch (SQLException e) {
			return false;
		}
	}
	/**
	 * {@code} I use a sql query again to find the vehicle with the given emission
	 * @param emission Use the given emission string to find the car/s with the same emission
	 * @return Returns an ArrayList of the vehicle with the corresponding emission
	 * @throws VehicleException
	 */
	public ArrayList<Vehicle> find(String emission) throws VehicleException {
		ArrayList<Vehicle> vehicles = new ArrayList<>();
		try {
			PreparedStatement statement = connection.prepareStatement(FIND + "'" + emission + "'");
			ResultSet results = statement.executeQuery();
			while (results.next()) {
				String vin = results.getString("vin");
				String model = results.getString("model");
				String modelType = results.getString("modelType");
				String vehicleType = results.getString("vehicleType");
				Boolean status = results.getBoolean("status");
				String engineType = results.getString("engineType");
				String engineEmission = results.getString("engineEmission");
				Integer engineDisplacement = results.getInt("engineDisplacement");
				Integer enginePower = results.getInt("enginePower");
				String transmission = results.getString("engineTransmission");
				Boolean turbo = results.getBoolean("engineTurbo");
				Integer powerHP = results.getInt("engineHP");

				Vehicle vehicle = null;
				Engine engine = null;
				if (engineType.equalsIgnoreCase("E")) {
					engine = new ElectricEngine.ElectricEngineBuilder(engineType).build();
				} else {
					engine = new CombustionEngine.CombustionEngineBuilder(engineType).addTurbo(turbo)
							.setEmission(engineEmission).setEngineDisplacement(engineDisplacement).setHP(powerHP)
							.setPower(enginePower).setTransmission(Transmission.valueOf(transmission)).build();
				}
				if (vehicleType.equalsIgnoreCase("SUV")) {
					vehicle = new SUV.SUVBuilder(Model.valueOf(model)).buildEngine(engine).buildStatus(status)
							.buildType(ModelType.valueOf(modelType)).buildVINDirectly(vin).build();
				} else {
					vehicle = new Car.CarBuilder(Model.valueOf(model)).buildEngine(engine).buildStatus(status)
							.buildType(ModelType.valueOf(modelType)).buildVINDirectly(vin).build();
				}

				vehicles.add(vehicle);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new VehicleException("There was a problem finding vehice/s with emission: " + emission);
		}
		return vehicles;
	}
	/**
	 * {@code} I use a sql query to find the vehicle with the given vin and update its emission
	 * @param emission Use the given vin to find the car and update its emission 
	 * @return Boolean
	 * @throws VehicleException
	 */
	public Boolean updateEmission(String emission, String vin) throws VehicleException{
		if(getByVIN(vin) == null) {
			throw new VehicleException("No vehicle with vin " + vin + " exists");
		}
		try {
			PreparedStatement statement = connection.prepareStatement(UPDATE_EMISSION + "'" + emission + "'" + WHERE_VIN + "'" + vin + "'");
			statement.executeUpdate();
			return true;
		} catch (SQLException e) {
			return false;
		}
		
	}
}
