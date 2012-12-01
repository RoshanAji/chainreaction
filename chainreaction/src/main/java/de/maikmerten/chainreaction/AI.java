package de.maikmerten.chainreaction;

import java.util.Random;

/**
 *
 * @author maik
 */
public class AI {

	public int computeDanger(Field f, byte player, int x, int y) {
		int danger = 0;
		
		if (x > 0 && f.getOwner(x - 1, y) != player && f.isCritical(x - 1, y)) {
			++danger;
		}
		if( x < (f.getWidth()) - 1 && f.getOwner(x + 1, y) != player && f.isCritical(x + 1, y)) {
			++danger;
		}

		if (y > 0 && f.getOwner(x, y - 1) != player && f.isCritical(x, y - 1)) {
			++danger;
		}
		if( y < (f.getHeight() - 1) && f.getOwner(x, y + 1) != player && f.isCritical(x, y + 1)) {
			++danger;
		}

		return danger;
	}
	
	
	public int countEndangeredFields(Field f, byte player) {
		int endangered = 0;
		
		for(int x = 0; x < f.getWidth(); ++x) {
			for(int y = 0; y < f.getHeight(); ++y) {
				byte owner = f.getOwner(x, y);
				if(owner == player && computeDanger(f, player, x, y) > 0) {
					++endangered;
				}
			}
		}
		
		return endangered;
	}
	
	
	public int[] thinkAI(Field f, byte playerAI, byte playerOpposing) {
		Random r = new Random();
		int opposingAtoms = f.getPlayerAtoms(playerOpposing);
		int score = Integer.MIN_VALUE;
		int[] coords = new int[2];
		for(int x = 0; x < f.getWidth(); ++x) {
			for(int y = 0; y < f.getHeight(); ++y) {
				byte owner = f.getOwner(x, y);
				if(owner == 0 || owner == playerAI) {
					Field fieldAI = f.getCopy();
					fieldAI.putAtom(playerAI, x, y);
					fieldAI.react();
					int tmp = fieldAI.getPlayerFields(playerAI);
					tmp += fieldAI.getPlayerAtoms(playerAI);
					tmp += opposingAtoms - fieldAI.getPlayerAtoms(playerOpposing);
					tmp += ((x == 0 && x == fieldAI.getWidth() - 1) || (y == 0 && y == fieldAI.getHeight() - 1)) ? 1 : 0;
					tmp -= computeDanger(fieldAI, playerAI, x, y) * 4;
					tmp -= countEndangeredFields(fieldAI, playerAI);
					if(tmp > score || (r.nextBoolean() && tmp >= score)) {
						score = tmp;
						coords[0] = x;
						coords[1] = y;
					}
				}
			}
		}
		
		return coords;
	}
	
	
}
