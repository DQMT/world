package xyz.tincat.host.world.util;

import java.util.Random;

public class StringUtil {

    private static char[] chars = {'0','1','2','3','4','5','6','7','8','9','a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z','A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'};

    public static String newRandomString(int length) {
        char[] c = new char[length];
        for (int i = 0; i < length; i++) {
            c[i] = newRandomChar();
        }
        return new String(c);
    }

    public static char newRandomChar() {
        int length =chars.length;
        Random random = new Random();
        return chars[random.nextInt(length)];
    }
}
