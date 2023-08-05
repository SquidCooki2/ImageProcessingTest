package com.squidcookie.shinysensor;

public class RGB {
    public int a;
    public int r;
    public int g;
    public int b;

    public RGB(int rGB) {
        a = (rGB >> 24) & 0xff;
        r = (rGB >> 16) & 0xff;
        g = (rGB >> 8) & 0xff;
        b = rGB & 0xff;
    }
}
