package talentboost.vehicle.assembly.vin;

import java.security.SecureRandom;
import java.util.HashSet;
/**
 * {@code}A Class to create a random VIN for the vehicle. <b>The user has the option
 * to either set the ISO code and the factory number code. To either set the vin directly through a given string</b>
 * @author rados
 */
public class VIN {
	private String VIN;

	private VIN(VinBuilder builder) {
		this.VIN = builder.vin;
	}

	public String getVIN() {
		return this.VIN;
	}

	public static class VinBuilder {
		private String vin;
		private StringBuilder ISO = new StringBuilder(2);
		private StringBuilder factoryNum = new StringBuilder(1);
		private HashSet<String> uniqueDigits = new HashSet<>(14);
		static SecureRandom rnd = new SecureRandom();
		private StringBuilder builder = new StringBuilder(14);

		// notice i leave out I,O,Q and i,o,q
		static final String AB = "0123456789ABCDEFGHJKLMNPRSTUVWXYZabcdefghjklmnprstuvwxyz";

		private String randomString(int len) {
			StringBuilder sb = new StringBuilder(len);
			for (int i = 0; i < len; i++)
				sb.append(AB.charAt(rnd.nextInt(AB.length())));
			return sb.toString();
		}

		public VinBuilder() {
			
		}
		
		public VIN setDirectly(String vin){
			this.vin = vin;
			return new VIN(this);
		}
		
		public VinBuilder addISO(String ISO) {
			this.ISO.append(ISO);
			return this;
		}

		public VinBuilder addFactoryNum(Integer factoryNum) {
			this.factoryNum.append(factoryNum);
			return this;
		}

		public VIN build() {
			String random = randomString(14);
			while (uniqueDigits.contains(random)) {
				random = randomString(14);
			}
			uniqueDigits.add(random);
			builder.append(ISO).append(factoryNum).append(random);
			vin = builder.toString();
			return new VIN(this);
		}
	}

	@Override
	public String toString() {
		return this.VIN;
	}
}
