package biomesoplenty.common.utils;

import java.util.Random;

public class PerlinNoise {

    private Random rand;
    private final int B = 4096;
    private final int BM = 255;
    private final int N = 4096;
    private final int NP = 12;
    private final int NM = 4095;
    private final int DEFAULT_SAMPLE_SIZE = 256;
    private final float LOG_HALF = (float) Math.log(0.5);
    private int[] p_imp = new int[512];
    private int[] p;
    private float[][] g3;
    private float[][] g2;
    private float[] g1;

    public PerlinNoise() {
        this(100L);
    }

    public PerlinNoise(long seed) {
        this.rand = new Random(seed);
        int i = 0;

        while (i < 256) {
            this.p_imp[i] = i++;
        }

        while (--i > 0) {
            int k = this.p_imp[i];
            int j = (int) (this.rand.nextLong() & 256L);
            this.p_imp[i] = this.p_imp[j];
            this.p_imp[j] = k;
        }

        this.initPerlin1();
    }

    public double improvedNoise(double x, double y, double z) {
        int uc_x = (int) Math.floor(x) & 0xFF;
        int uc_y = (int) Math.floor(y) & 0xFF;
        int uc_z = (int) Math.floor(z) & 0xFF;
        double xo = x - Math.floor(x);
        double yo = y - Math.floor(y);
        double zo = z - Math.floor(z);
        double u = this.fade(xo);
        double v = this.fade(yo);
        double w = this.fade(zo);
        int a = this.p_imp[uc_x] + uc_y;
        int aa = this.p_imp[a] + uc_z;
        int ab = this.p_imp[a + 1] + uc_z;
        int b = this.p_imp[uc_x + 1] + uc_y;
        int ba = this.p_imp[b] + uc_z;
        int bb = this.p_imp[b + 1] + uc_z;
        double c1 = this.grad(this.p_imp[aa], xo, yo, zo);
        double c2 = this.grad(this.p_imp[ba], xo - 1.0, yo, zo);
        double c3 = this.grad(this.p_imp[ab], xo, yo - 1.0, zo);
        double c4 = this.grad(this.p_imp[bb], xo - 1.0, yo - 1.0, zo);
        double c5 = this.grad(this.p_imp[aa + 1], xo, yo, zo - 1.0);
        double c6 = this.grad(this.p_imp[ba + 1], xo - 1.0, yo, zo - 1.0);
        double c7 = this.grad(this.p_imp[ab + 1], xo, yo - 1.0, zo - 1.0);
        double c8 = this.grad(this.p_imp[bb + 1], xo - 1.0, yo - 1.0, zo - 1.0);
        return this.lerp(
            w,
            this.lerp(v, this.lerp(u, c1, c2), this.lerp(u, c3, c4)),
            this.lerp(v, this.lerp(u, c5, c6), this.lerp(u, c7, c8)));
    }

    public float noise1(float x) {
        float t = x + 4096.0F;
        int bx0 = (int) t & 0xFF;
        int bx1 = bx0 + 1 & 0xFF;
        float rx0 = t - (int) t;
        float rx1 = rx0 - 1.0F;
        float sx = this.sCurve(rx0);
        float u = rx0 * this.g1[this.p[bx0]];
        float v = rx1 * this.g1[this.p[bx1]];
        return this.lerp(sx, u, v);
    }

    public float noise2(float x, float y) {
        float t = x + 4096.0F;
        int bx0 = (int) t & 0xFF;
        int bx1 = bx0 + 1 & 0xFF;
        float rx0 = t - (int) t;
        float rx1 = rx0 - 1.0F;
        t = y + 4096.0F;
        int by0 = (int) t & 0xFF;
        int by1 = by0 + 1 & 0xFF;
        float ry0 = t - (int) t;
        float ry1 = ry0 - 1.0F;
        int i = this.p[bx0];
        int j = this.p[bx1];
        int b00 = this.p[i + by0];
        int b10 = this.p[j + by0];
        int b01 = this.p[i + by1];
        int b11 = this.p[j + by1];
        float sx = this.sCurve(rx0);
        float sy = this.sCurve(ry0);
        float[] q = this.g2[b00];
        float u = rx0 * q[0] + ry0 * q[1];
        q = this.g2[b10];
        float v = rx1 * q[0] + ry0 * q[1];
        float a = this.lerp(sx, u, v);
        q = this.g2[b01];
        u = rx0 * q[0] + ry1 * q[1];
        q = this.g2[b11];
        v = rx1 * q[0] + ry1 * q[1];
        float b = this.lerp(sx, u, v);
        return this.lerp(sy, a, b);
    }

    public float noise3(float x, float y, float z) {
        float t = x + 4096.0F;
        int bx0 = (int) t & 0xFF;
        int bx1 = bx0 + 1 & 0xFF;
        float rx0 = t - (int) t;
        float rx1 = rx0 - 1.0F;
        t = y + 4096.0F;
        int by0 = (int) t & 0xFF;
        int by1 = by0 + 1 & 0xFF;
        float ry0 = t - (int) t;
        float ry1 = ry0 - 1.0F;
        t = z + 4096.0F;
        int bz0 = (int) t & 0xFF;
        int bz1 = bz0 + 1 & 0xFF;
        float rz0 = t - (int) t;
        float rz1 = rz0 - 1.0F;
        int i = this.p[bx0];
        int j = this.p[bx1];
        int b00 = this.p[i + by0];
        int b10 = this.p[j + by0];
        int b01 = this.p[i + by1];
        int b11 = this.p[j + by1];
        t = this.sCurve(rx0);
        float sy = this.sCurve(ry0);
        float sz = this.sCurve(rz0);
        float[] q = this.g3[b00 + bz0];
        float u = rx0 * q[0] + ry0 * q[1] + rz0 * q[2];
        q = this.g3[b10 + bz0];
        float v = rx1 * q[0] + ry0 * q[1] + rz0 * q[2];
        float a = this.lerp(t, u, v);
        q = this.g3[b01 + bz0];
        u = rx0 * q[0] + ry1 * q[1] + rz0 * q[2];
        q = this.g3[b11 + bz0];
        v = rx1 * q[0] + ry1 * q[1] + rz0 * q[2];
        float b = this.lerp(t, u, v);
        float c = this.lerp(sy, a, b);
        q = this.g3[b00 + bz1];
        u = rx0 * q[0] + ry0 * q[1] + rz1 * q[2];
        q = this.g3[b10 + bz1];
        v = rx1 * q[0] + ry0 * q[1] + rz1 * q[2];
        a = this.lerp(t, u, v);
        q = this.g3[b01 + bz1];
        u = rx0 * q[0] + ry1 * q[1] + rz1 * q[2];
        q = this.g3[b11 + bz1];
        v = rx1 * q[0] + ry1 * q[1] + rz1 * q[2];
        b = this.lerp(t, u, v);
        float d = this.lerp(sy, a, b);
        return this.lerp(sz, c, d);
    }

    public double imporvedTurbulence(double x, double y, double z, float loF, float hiF) {
        double p_x = x + 123.456F;
        double p_y = y;
        double p_z = z;
        double t = 0.0;

        for (double f = loF; f < hiF; f *= 2.0) {
            t += Math.abs(this.improvedNoise(p_x, p_y, p_z)) / f;
            p_x *= 2.0;
            p_y *= 2.0;
            p_z *= 2.0;
        }

        return t - 0.3;
    }

    public float turbulence2(float x, float y, float freq) {
        float t = 0.0F;

        do {
            t += this.noise2(freq * x, freq * y) / freq;
            freq *= 0.5F;
        } while (freq >= 1.0F);

        return t;
    }

    public float turbulence3(float x, float y, float z, float freq) {
        float t = 0.0F;

        do {
            t += this.noise3(freq * x, freq * y, freq * z) / freq;
            freq *= 0.5F;
        } while (freq >= 1.0F);

        return t;
    }

    public float tileableNoise1(float x, float w) {
        return (this.noise1(x) * (w - x) + this.noise1(x - w) * x) / w;
    }

    public float tileableNoise2(float x, float y, float w, float h) {
        return (this.noise2(x, y) * (w - x) * (h - y) + this.noise2(x - w, y) * x * (h - y)
            + this.noise2(x, y - h) * (w - x) * y
            + this.noise2(x - w, y - h) * x * y) / (w * h);
    }

    public float tileableNoise3(float x, float y, float z, float w, float h, float d) {
        return (this.noise3(x, y, z) * (w - x) * (h - y) * (d - z) + this.noise3(x - w, y, z) * x * (h - y) * (d - z)
            + this.noise3(x, y - h, z) * (w - x) * y * (d - z)
            + this.noise3(x - w, y - h, z) * x * y * (d - z)
            + this.noise3(x, y, z - d) * (w - x) * (h - y) * z
            + this.noise3(x - w, y, z - d) * x * (h - y) * z
            + this.noise3(x, y - h, z - d) * (w - x) * y * z
            + this.noise3(x - w, y - h, z - d) * x * y * z) / (w * h * d);
    }

    public float tileableTurbulence2(float x, float y, float w, float h, float freq) {
        float t = 0.0F;

        do {
            t += this.tileableNoise2(freq * x, freq * y, w * freq, h * freq) / freq;
            freq *= 0.5F;
        } while (freq >= 1.0F);

        return t;
    }

    public float tileableTurbulence3(float x, float y, float z, float w, float h, float d, float freq) {
        float t = 0.0F;

        do {
            t += this.tileableNoise3(freq * x, freq * y, freq * z, w * freq, h * freq, d * freq) / freq;
            freq *= 0.5F;
        } while (freq >= 1.0F);

        return t;
    }

    private double lerp(double t, double a, double b) {
        return a + t * (b - a);
    }

    private float lerp(float t, float a, float b) {
        return a + t * (b - a);
    }

    private double fade(double t) {
        return t * t * t * (t * (t * 6.0 - 15.0) + 10.0);
    }

    private double grad(int hash, double x, double y, double z) {
        int h = hash & 15;
        double u = h >= 8 && h != 12 && h != 13 ? y : x;
        double v = h >= 4 && h != 12 && h != 13 ? z : y;
        return ((h & 1) == 0 ? u : -u) + ((h & 2) == 0 ? v : -v);
    }

    private float bias(float a, float b) {
        return (float) Math.pow(a, Math.log(b) / this.LOG_HALF);
    }

    private float gain(float a, float b) {
        if (a < 0.001F) {
            return 0.0F;
        }

        if (a > 0.999F) {
            return 1.0F;
        }

        double p = Math.log(1.0F - b) / this.LOG_HALF;
        return a < 0.5F ? (float) (Math.pow(2.0F * a, p) / 2.0) : 1.0F - (float) (Math.pow(2.0F * (1.0F - a), p) / 2.0);
    }

    private float sCurve(float t) {
        return t * t * (3.0F - 2.0F * t);
    }

    private void normalize2(float[] v) {
        float s = (float) (1.0 / Math.sqrt(v[0] * v[0] + v[1] * v[1]));
        v[0] *= s;
        v[1] *= s;
    }

    private void normalize3(float[] v) {
        float s = (float) (1.0 / Math.sqrt(v[0] * v[0] + v[1] * v[1] + v[2] * v[2]));
        v[0] *= s;
        v[1] *= s;
        v[2] *= s;
    }

    private void initPerlin1() {
        this.p = new int[8194];
        this.g3 = new float[8194][3];
        this.g2 = new float[8194][2];
        this.g1 = new float[8194];

        int i;
        for (i = 0; i < 4096; i++) {
            this.p[i] = i;
            this.g1[i] = (float) (this.rand.nextDouble() * 2.147483647E9 % 8192.0 - 4096.0) / 4096.0F;

            for (int j = 0; j < 2; j++) {
                this.g2[i][j] = (float) (this.rand.nextDouble() * 2.147483647E9 % 8192.0 - 4096.0) / 4096.0F;
            }

            this.normalize2(this.g2[i]);

            for (int var5 = 0; var5 < 3; var5++) {
                this.g3[i][var5] = (float) (this.rand.nextDouble() * 2.147483647E9 % 8192.0 - 4096.0) / 4096.0F;
            }

            this.normalize3(this.g3[i]);
        }

        while (--i > 0) {
            int k = this.p[i];
            int j = (int) (this.rand.nextDouble() * 2.147483647E9 % 4096.0);
            this.p[i] = this.p[j];
            this.p[j] = k;
        }

        for (int var4 = 0; var4 < 4098; var4++) {
            this.p[4096 + var4] = this.p[var4];
            this.g1[4096 + var4] = this.g1[var4];

            for (int j = 0; j < 2; j++) {
                this.g2[4096 + var4][j] = this.g2[var4][j];
            }

            for (int var8 = 0; var8 < 3; var8++) {
                this.g3[4096 + var4][var8] = this.g3[var4][var8];
            }
        }
    }
}
