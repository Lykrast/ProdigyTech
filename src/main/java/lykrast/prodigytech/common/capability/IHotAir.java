package lykrast.prodigytech.common.capability;

public interface IHotAir {
	/**
	 * What air temperature comes out of this block. For machines this differs from their actual internal temperature
	 * since they use part of it to do their processing.
	 * @return output air temperature in °C
	 */
	public int getOutAirTemperature();
}
