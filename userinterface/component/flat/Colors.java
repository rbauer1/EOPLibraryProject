package userinterface.component.flat;

import java.awt.Color;

public class Colors {
	
	private static class ColorConfigHelper implements ColorConfig {
		
		private Color basic, hover, pressed, progress;

		public ColorConfigHelper(Color basic, Color hover, Color progress, Color pressed) {
			this.basic = basic;
			this.hover = hover;
			this.progress = progress;
			this.pressed = pressed;
		}

		public ColorConfigHelper(int basic, int hover, int progress, int pressed) {
			this.basic = new Color(basic);
			this.hover = new Color(hover);
			this.progress = new Color(progress);
			this.pressed = new Color(pressed);
		}

		public Color getColor() { return basic; }
		public Color getHoverColor() { return hover; }
		public Color getPressedColor() { return pressed; }
		public Color getProgressColor() { return progress; }
	}

	private static final Color MAIN_COLOR = new Color(0x668D3C);
	public static ColorConfig BASIC_COLOR =
			new ColorConfigHelper(MAIN_COLOR, MAIN_COLOR.brighter(), MAIN_COLOR.brighter(), MAIN_COLOR.darker());

	private static final Color RED_COLOR = new Color(0xE74C3C);
	public static ColorConfig RED =
			new ColorConfigHelper(RED_COLOR, RED_COLOR.brighter(), RED_COLOR.brighter(), RED_COLOR.darker());
}
