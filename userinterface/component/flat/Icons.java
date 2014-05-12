package userinterface.component.flat;

import javax.swing.ImageIcon;

import userinterface.utilities.Utils;

public class Icons {
	
	public static class IconConfigHelper implements IconConfig {
		
		private ImageIcon basic, hover, pressed;

		public IconConfigHelper(String icon) {
			this.basic = new ImageIcon(Utils.getImagePath(icon));
			this.hover = new ImageIcon(Utils.getImagePath(icon));
			this.pressed = new ImageIcon(Utils.getImagePath(icon));
		}
		
		public IconConfigHelper(String basic, String pressed) {
			this.basic = new ImageIcon(Utils.getImagePath(basic));
			this.hover = new ImageIcon(Utils.getImagePath(pressed));
			this.pressed = new ImageIcon(Utils.getImagePath(pressed));
		}
		
		public IconConfigHelper(String basic, String hover, String pressed) {
			this.basic = new ImageIcon(Utils.getImagePath(basic));
			this.hover = new ImageIcon(Utils.getImagePath(hover));
			this.pressed = new ImageIcon(Utils.getImagePath(pressed));
		}
		
		public IconConfigHelper(String icon, int w, int h) {
			this.basic = Utils.scaleIcon(Utils.getImagePath(icon), w, h);
			this.hover = Utils.scaleIcon(Utils.getImagePath(icon), w, h);
			this.pressed = Utils.scaleIcon(Utils.getImagePath(icon), w, h);
			System.out.println(icon);
		}
		
		public IconConfigHelper(String basic, String pressed, int w, int h) {
			this.basic = Utils.scaleIcon(Utils.getImagePath(basic), w, h);
			this.hover = Utils.scaleIcon(Utils.getImagePath(pressed), w, h);
			this.pressed = Utils.scaleIcon(Utils.getImagePath(pressed), w, h);
		}
		
		public IconConfigHelper(String basic, String hover, String pressed, int w, int h) {
			this.basic = Utils.scaleIcon(Utils.getImagePath(basic), w, h);
			this.hover = Utils.scaleIcon(Utils.getImagePath(hover), w, h);
			this.pressed = Utils.scaleIcon(Utils.getImagePath(pressed), w, h);
		}
		
		@Override
		public ImageIcon getIcon() { return basic; }
		
		@Override
		public ImageIcon getHoverIcon() { return hover; }
		
		@Override
		public ImageIcon getPressedIcon() { return pressed; }

	}

	public static final IconConfigHelper LOGOUT = new IconConfigHelper("LogOff_Clear.png", "LogOff_Clear_Hover.png", "LogOff_Clear_Pressed.png", 30, 30);
	public static final IconConfigHelper GEAR = new IconConfigHelper("Gear_Clear.png", 30, 30);
	public static final IconConfigHelper BOOK = new IconConfigHelper("Book_Clear.png", 30, 30);
	public static final IconConfigHelper WORKER = new IconConfigHelper("Programs/Worker.png", 30, 30);
	public static final IconConfigHelper BORROWER = new IconConfigHelper("Programs/Borrower.png", 30, 30);
	public static final IconConfigHelper RENT_BOOK = new IconConfigHelper("Book_Clear.png", 30, 30);
	public static final IconConfigHelper RETURN_BOOK = new IconConfigHelper("Book_Clear.png", 30, 30);
}
