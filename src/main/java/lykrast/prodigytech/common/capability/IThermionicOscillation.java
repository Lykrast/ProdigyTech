package lykrast.prodigytech.common.capability;

public interface IThermionicOscillation {
	/**
	 * The amount of thermionic oscillations coming from this machine. This is similar to the progress bar on machines
	 * such that 10 = normal speed, 20 = 2x as fast and so on.
	 * @return output thermionic oscillation power
	 */
	public int getOscillationPower();
}
