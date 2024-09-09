import java.awt.*;

public class WindowSize {
    private static double width, height;

    WindowSize(){
        GraphicsDevice device = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        width = device.getDisplayMode().getWidth() * 0.78;
        height = device.getDisplayMode().getHeight() * 0.79;
    }

    public static double getWidth(){
        return width;
    }

    public static double getHeight() {
        return height;
    }

    public  double getWidths(){
        return width;
    }

    public  double getHeights(){
        return height;
    }
}
