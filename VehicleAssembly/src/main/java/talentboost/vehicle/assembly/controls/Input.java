package talentboost.vehicle.assembly.controls;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.NoSuchElementException;
import java.util.Scanner;

import talentboost.vehicle.assembly.commands.Create;
import talentboost.vehicle.assembly.commands.DisassembleCar;
import talentboost.vehicle.assembly.commands.FindCar;
import talentboost.vehicle.assembly.commands.ICommands;
import talentboost.vehicle.assembly.commands.PrintCar;
import talentboost.vehicle.assembly.commands.UpdateCar;
import talentboost.vehicle.assembly.common.EngineException;
import talentboost.vehicle.assembly.common.InvalidCommandException;
import talentboost.vehicle.assembly.common.Pair;
import talentboost.vehicle.assembly.common.VehicleException;
import talentboost.vehicle.assembly.jdbc.JDBC;
/**
 * {@code} This class parses the input into command and arguments
 * @author rados
 *
 */
public class Input {
	private static ControlJDBC control = new ControlJDBC(new JDBC());
	@SuppressWarnings("serial")  
	private Collection<ICommands> cmds = new ArrayList<ICommands>() {
		{   //collection of the commands
			add(new Create(control));
			add(new DisassembleCar(control));
			add(new PrintCar(control));
			add(new FindCar(control));
			add(new UpdateCar(control));
		}
	};
	/**
	 * {@code} Chooses which command to call by getting the name
	 * of each command and decides which one to be executed
	 */
	public void processInput(String path, String pathOutput)
			throws EngineException, VehicleException, FileNotFoundException, IOException {
		Pair<String, String> cmdParts = null;
		BufferedWriter bw = new BufferedWriter(new FileWriter(pathOutput));;
		try (BufferedReader br = new BufferedReader(new FileReader(path))) {
			String line = br.readLine();
			while (line != null) {

				cmdParts = processLine(line);
				String cmd = cmdParts.getFirstElement();
				String args = cmdParts.getSecondElement();

				// map to call the parsed command
				HashMap<String, ICommands> commandParts = new HashMap<String, ICommands>();
				for (ICommands command : cmds) {
					commandParts.put(command.getName(), command);
				}
				ICommands command = commandParts.get(cmd);
				try {//write to a file
					bw.write(command.execute(args) + "\n");
					bw.write("\n");
				} catch (InvalidCommandException e) {
					e.getMessage();
				}//read from a file
				line = br.readLine();
			}
		}
		finally{
			bw.close();
		}
	}
	/**
	 * {@code} Processes the input from the user and seperates it 
	 * into command and arguments. The input is taken from a text file
	 * that holds all of the commands
	 */
	public Pair<String, String> processLine(String line) {
		// Using a scanner as arguments may have spaces
		String trimmedLine = line.trim();
		Scanner linescan = new Scanner(trimmedLine);
		try {
			String command;
			command = linescan.next().trim();

			String cmdargs;
			try {
				cmdargs = linescan.nextLine().trim();
			} catch (NoSuchElementException e) {
				// no arguments
				cmdargs = "";
			}

			return new Pair<String, String>(command, cmdargs);
		} finally {
			linescan.close();
		}
	}
}
