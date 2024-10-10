import static java.lang.Math.max;

public class Converter {
    public static float[] RGB_to_CMYK(float r, float g, float b){
        float rTemp = r / 255f,
                gTemp = g / 255f,
                bTemp = b / 255f;

        float k = 1 - Math.max(Math.max(rTemp, gTemp), bTemp);
        float c, m, y;

        if(k < 1) {
            c = (1 - rTemp - k) / (1 - k);
            m = (1 - gTemp - k) / (1 - k);
            y = (1 - bTemp - k) / (1 - k);
        } else {
            c = 0;
            m = 0;
            y = 0;
        }
        return new float[]{c, m, y, k};
    }

    public static float[] CMYK_to_RGB(float c, float m, float y, float k){
        float r = (float) ((1 - Math.min(1, c * (1 - k) + k)) * 255);
        float g = (float) ((1 - Math.min(1, m * (1 - k) + k)) * 255);
        float b = (float) ((1 - Math.min(1, y * (1 - k) + k)) * 255);
        return new float[] {r, g, b};
    }

    public static float[] HSV_to_RGB(float h, float s, float v) {
        float[] rgb = new float[3];
        float r, g, b;

        h = h % 360;
        s = Math.max(0, Math.min(1, s));
        v = Math.max(0, Math.min(1, v));

        if (s == 0) {
            r = g = b = v;
        } else {
            float c = v * s;
            float x = c * (1 - Math.abs((h / 60) % 2 - 1));
            float m = v - c;

            if (h >= 0 && h < 60) {
                r = c;
                g = x;
                b = 0;
            } else if (h >= 60 && h < 120) {
                r = x;
                g = c;
                b = 0;
            } else if (h >= 120 && h < 180) {
                r = 0;
                g = c;
                b = x;
            } else if (h >= 180 && h < 240) {
                r = 0;
                g = x;
                b = c;
            } else if (h >= 240 && h < 300) {
                r = x;
                g = 0;
                b = c;
            } else {
                r = c;
                g = 0;
                b = x;
            }

            r = (r + m) * 255;
            g = (g + m) * 255;
            b = (b + m) * 255;
        }

        rgb[0] = r;
        rgb[1] = g;
        rgb[2] = b;

        return rgb;
    }

    public  static float[] RGB_to_HSV(float R, float G, float B){ // right
        float[] hsv = new float[3];

        float rNorm = R / 255f;
        float gNorm = G / 255f;
        float bNorm = B / 255f;

        float max = Math.max(rNorm, Math.max(gNorm, bNorm));
        float min = Math.min(rNorm, Math.min(gNorm, bNorm));
        float delta = max - min;

        hsv[2] = max;

        if (max == 0) {
            hsv[1] = 0;
        } else {
            hsv[1] = delta / max;
        }

        if (delta == 0) {
            hsv[0] = 0;
        } else {
            if (max == rNorm) {
                hsv[0] = ((gNorm - bNorm) / delta) % 6;
            } else if (max == gNorm) {
                hsv[0] = ((bNorm - rNorm) / delta) + 2;
            } else {
                hsv[0] = ((rNorm - gNorm) / delta) + 4;
            }
            hsv[0] *= 60;
            if (hsv[0] < 0) {
                hsv[0] += 360;
            }
        }
        return hsv;
    }
}
