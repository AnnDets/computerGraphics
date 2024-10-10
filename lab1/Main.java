public class Main {
    public static void main(String[] args) {
        float h = 210, s = 0.75f, v = 0.8f;
        float r = 51, g = 128, b = 204;
        float c = 0.75f, m = 0.37f, y = 0f, k = 0.2f;

        float[] hsvToRgb = Converter.HSV_to_RGB(h, s, v);
        System.out.println("HSV to RGB: " + hsvToRgb[0] + ", " + hsvToRgb[1] + ", " + hsvToRgb[2]);

        float[] rgbToHsv = Converter.RGB_to_HSV(r, g, b);
        System.out.println("RGB to HSV: " + rgbToHsv[0] + ", " + rgbToHsv[1] + ", " + rgbToHsv[2]);

        float[] rgbToCmyk = Converter.RGB_to_CMYK(r, g, b);
        System.out.println("RGB to CMYK: " + rgbToCmyk[0] + ", " + rgbToCmyk[1] + ", " + rgbToCmyk[2] + ", " + rgbToCmyk[3]);

        float[] cmykToRgb = Converter.CMYK_to_RGB(c, m, y, k);
        System.out.println("CMYK to RGB: " + cmykToRgb[0] + ", " + cmykToRgb[1] + ", " + cmykToRgb[2]);
    }
}