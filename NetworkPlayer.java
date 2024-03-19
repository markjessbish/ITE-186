

package model;

public class NetworkPlayer extends Player {

	@Override
	public boolean isHuman() {
		return false;
	}

	/**
	 * This method does not actually update the game state for network players
	 * as it is updated when their client sends the updated game.
	 */
	@Override
	public void updateGame(Game game) {}

}
