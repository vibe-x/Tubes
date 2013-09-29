package schmoller.tubes.logic;

import schmoller.tubes.TubeItem;

public abstract class TubeLogic
{
	public static final int NO_DESTINATION = -1;
	
	/**
	 * Gets what faces tubes can connect to.
	 * @return An Integer where each bit from 0-5 represents a side
	 */
	public int getConnectableMask() { return 63; }
	
	/**
	 * Checks if the specified item can enter this tube. 
	 * Tube connectivity checks are already done by this point
	 */
	public boolean canItemEnter(TubeItem item, int side) { return true; }

	/**
	 * If true, items can find a destination passing through this tube 
	 */
	public boolean canPathThrough() { return true; }

	/**
	 * Gets how to weight this tube when routing.
	 * Must be 0 or greater
	 */
	public int getRouteWeight() { return 1; }
	
	/**
	 * Called when an item enters this tube
	 */
	public void onItemEnter(TubeItem item) {}
	
	/**
	 * Tubes can have custom routing if this is set. 
	 */
	public boolean hasCustomRouting() { return false; }
	
	/**
	 * Called when an item reaches a junction if hasCustomRouting() is true
	 * 
	 * @param item
	 * @return The direction (0-5) to go. NO_DESTINATION Should be returned if no destination is found
	 */
	public int onDetermineDestination(TubeItem item) { return NO_DESTINATION; }
}
