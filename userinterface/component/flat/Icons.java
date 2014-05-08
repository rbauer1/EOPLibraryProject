package userinterface.component.flat;

import javax.swing.ImageIcon;

import userinterface.utilities.Utils;

public class Icons {
	
	public static class IconConfigHelper implements IconConfig {
		
		private ImageIcon basic, pressed;

		public IconConfigHelper(String icon) {
			this.basic = new ImageIcon(Utils.getImagePath(icon));
			this.pressed = new ImageIcon(Utils.getImagePath(icon));
		}
		
		public IconConfigHelper(String basic, String pressed) {
			this.basic = new ImageIcon(Utils.getImagePath(basic));
			this.pressed = new ImageIcon(Utils.getImagePath(pressed));
		}
		
		public IconConfigHelper(String icon, int w, int h) {
			this.basic = Utils.scaleIcon(Utils.getImagePath(icon), w, h);
			this.pressed = Utils.scaleIcon(Utils.getImagePath(icon), w, h);
		}
		
		public IconConfigHelper(String basic, String pressed, int w, int h) {
			this.basic = Utils.scaleIcon(Utils.getImagePath(basic), w, h);
			this.pressed = Utils.scaleIcon(Utils.getImagePath(pressed), w, h);
		}
		
		public ImageIcon getIcon() { return basic; }
		public ImageIcon getPressedIcon() { return pressed; }
	}

	public static final IconConfigHelper LOGOUT = new IconConfigHelper("LogOff.png", "LogOff_Clear.png", 30, 30);
	public static final IconConfigHelper BOOK = new IconConfigHelper("Book_Clear.png", 30, 30);
	public static final IconConfigHelper WORKER = new IconConfigHelper("Programs/User_Worker.png", 30, 30);
	public static final IconConfigHelper BORROWER = new IconConfigHelper("Programs/User_Borrower.png", 30, 30);
	public static final IconConfigHelper RENT_BOOK = new IconConfigHelper("Book_Clear.png", 30, 30);
	public static final IconConfigHelper RETURN_BOOK = new IconConfigHelper("Book_Clear.png", 30, 30);
}
