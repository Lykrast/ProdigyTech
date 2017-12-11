package lykrast.prodigytech.common.capability;

/**
 * A default implementation that always returns 0.
 * @author Lykrast
 */
public class ThermionicOscillationNull implements IThermionicOscillation {

	@Override
	public int getOscillationPower() {
		return 0;
	}

}
