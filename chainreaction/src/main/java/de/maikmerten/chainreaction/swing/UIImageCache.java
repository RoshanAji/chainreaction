package de.maikmerten.chainreaction.swing;

import java.awt.Image;
import java.util.HashMap;
import java.util.Map;

import javax.swing.ImageIcon;


public class UIImageCache {
	private final static Map<String, Image> imageCache;
	
	static {
		imageCache = new HashMap<String, Image>(100);
	}

	public static Image loadImage(final String fileFN) {
		if(!imageCache.containsKey(fileFN)) {
			final ImageIcon curr = 
					new ImageIcon(UIImageCache.class.getResource(fileFN));
			imageCache.put(fileFN, curr.getImage());
		}
		return imageCache.get(fileFN); 
	}
}