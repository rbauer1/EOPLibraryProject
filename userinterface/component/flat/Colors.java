package userinterface.component.flat;

import java.awt.Color;

public class Colors {

	private static class ColorConfigHelper implements ColorConfig {

		private final Color basic, hover, pressed, progress;

		public ColorConfigHelper(Color basic, Color hover, Color progress, Color pressed) {
			this.basic = basic;
			this.hover = hover;
			this.progress = progress;
			this.pressed = pressed;
		}

		@Override
		public Color getColor() { return basic; }
		@Override
		public Color getHoverColor() { return hover; }
		@Override
		public Color getPressedColor() { return pressed; }
		@Override
		public Color getProgressColor() { return progress; }
	}

	private static final Color MAIN_COLOR = new Color(0x668D3C);
	public static ColorConfig BASIC_COLOR =
			new ColorConfigHelper(MAIN_COLOR, MAIN_COLOR.brighter(), MAIN_COLOR.brighter(), MAIN_COLOR.darker());
	private static final Color RED_COLOR = new Color(0xE74C3C);
	public static ColorConfig RED =
			new ColorConfigHelper(RED_COLOR, RED_COLOR.brighter(), RED_COLOR.brighter(), RED_COLOR.darker());
	
	private static final Color GREEN_SEA_COLOR = new Color(0x00553D);//0x16A085
	public static ColorConfig GREEN_SEA =
			new ColorConfigHelper(GREEN_SEA_COLOR, GREEN_SEA_COLOR.brighter(), GREEN_SEA_COLOR.brighter(), GREEN_SEA_COLOR.darker());
}
