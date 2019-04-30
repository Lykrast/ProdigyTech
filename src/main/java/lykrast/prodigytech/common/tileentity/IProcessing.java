package lykrast.prodigytech.common.tileentity;

public interface IProcessing {
	boolean isProcessing();
	int getProgressLeft();
	int getMaxProgress();
	
	default boolean invertDisplay() {
		return false;
	}
}
