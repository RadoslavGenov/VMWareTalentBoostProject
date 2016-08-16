package talentboost.vehicle.assembly.controls;

import java.util.ArrayList;

import talentboost.vehicle.assembly.car.Vehicle;
import talentboost.vehicle.assembly.common.EngineException;
import talentboost.vehicle.assembly.common.VehicleException;
import talentboost.vehicle.assembly.jdbc.JDBC;

/**
 * @author rados
 * {@code} this class is a holder for the JDBC
 * as I was afraid that with all the other classes
 * touching it it might hit a nullptr exception somewhere
 */
public class ControlJDBC {
	private JDBC database;
	
	public ControlJDBC(JDBC database) {
		this.database = database;
	}

	public ArrayList<Vehicle> getAllVehicles(){
		return database.getAllVehicles();
	}
	
	public void addVehicle(Vehicle vehicle) throws EngineException{
		database.addVehicle(vehicle);
	}
	
	public Vehicle getByVIN(String vin) throws VehicleException{
		return database.getByVIN(vin);
	}
	
	public Boolean disassemble(String vin) throws VehicleException{
		return database.disassemble(vin);
	}
	
	public ArrayList<Vehicle> findByEmission(String emission) throws VehicleException{
		return database.find(emission);
	}
	
	public Boolean updateEmission(String emission, String vin) throws VehicleException{
		return database.updateEmission(emission, vin);
	}
}
