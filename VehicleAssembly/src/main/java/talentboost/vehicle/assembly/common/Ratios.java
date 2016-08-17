package talentboost.vehicle.assembly.common;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
/**
 * {@code} Class to hold the given ratios between HP->KW,
 * CC->KW, KW->CC, KW->HP, and turboed KW->HP
 * All methods return an unmodifiable map
 * @author rados
 *
 */
public class Ratios {
	public static Map<Integer, Integer> getHptokw() {
		@SuppressWarnings("serial")
		final Map<Integer, Integer> HPToKw = new HashMap<Integer, Integer>() {
			{
				put(76, 74);
				put(138, 134);
				put(253, 245);
				put(264, 253);
				put(341, 331);
				put(526, 510);
				put(759, 736);
			}
		};
		return Collections.unmodifiableMap(HPToKw);
	}

	public static Map<Integer, Integer> getCctokw() {
		@SuppressWarnings("serial")
		final Map<Integer, Integer> CCToKW = new HashMap<Integer, Integer>() {
			{
				put(1, 74);
				put(2, 134);
				put(3, 245);
				put(4, 253);
				put(5, 331);
				put(6, 510);
				put(8, 736);
			}
		};
		return CCToKW;
	}

	public static Map<Integer, Integer> getKwtocc() {
		@SuppressWarnings("serial")
		final Map<Integer, Integer> KWtoCC = new HashMap<Integer, Integer>() {
			{
				put(74, 1);
				put(134, 2);
				put(245, 3);
				put(253, 4);
				put(331, 5);
				put(510, 6);
				put(736, 8);
			}
		};
		return Collections.unmodifiableMap(KWtoCC);
	}

	public static Map<Integer, Integer> getKwtohp() {
		@SuppressWarnings("serial")
		final Map<Integer, Integer> KWToHP = new HashMap<Integer, Integer>() {
			{
				put(74, 76);
				put(134, 138);
				put(245, 253);
				put(253, 264);
				put(331, 341);
				put(510, 526);
				put(736, 759);
			}
		};
		return Collections.unmodifiableMap(KWToHP);
	}

	public static Map<Integer, Integer> getKwtohpTurbo() {
		@SuppressWarnings("serial")
		final Map<Integer, Integer> KWToHPTurbo = new HashMap<Integer, Integer>() {
			{

				put(96, 99);
				put(174, 179);
				put(318, 328);
				put(329, 339);
				put(430, 444);
				put(663, 684);
				put(957, 987);
			}
		};
		return Collections.unmodifiableMap(KWToHPTurbo);
	}
}
