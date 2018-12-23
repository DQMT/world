package xyz.tincat.host.world.util;

public class CoolBytes {
    private byte[] hb;
    private int cap;
    private int offset;
    static final int DEFAULT_INITIAL_CAPACITY = 1 << 4; // aka 16

    public CoolBytes() {
        this.cap = DEFAULT_INITIAL_CAPACITY;
        hb = new byte[DEFAULT_INITIAL_CAPACITY];
        offset = 0;
    }

    public CoolBytes(int cap) {
        hb = new byte[cap];
        this.cap = cap;
        offset = 0;
    }

    public void add(byte b) {
        if (offset == cap) {
            hb = resize();
        }
        hb[offset] = b;
        offset++;
    }

    public String toString() {
        return new String(toArray());
    }

    public byte[] toArray() {
        byte[] newHb = new byte[offset];
        for (int i = 0; i < offset; i++) {
            newHb[i] = hb[i];
        }
        return newHb;
    }

    public int length() {
        return offset;
    }

    final byte[] resize() {
        byte[] oldHb = hb;
        byte[] newHb = new byte[cap << 1];
        for (int i = 0; i < oldHb.length; i++) {
            newHb[i] = oldHb[i];
        }
        cap = cap << 1;
        return newHb;
    }

    public static String bytesToHexString(byte[] src){
        StringBuilder stringBuilder = new StringBuilder("");
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }
    /**
     * Convert hex string to byte[]
     * @param hexString the hex string
     * @return byte[]
     */
    public static byte[] hexStringToBytes(String hexString) {
        if (hexString == null || hexString.equals("")) {
            return null;
        }
        hexString = hexString.toUpperCase();
        int length = hexString.length() / 2;
        char[] hexChars = hexString.toCharArray();
        byte[] d = new byte[length];
        for (int i = 0; i < length; i++) {
            int pos = i * 2;
            d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
        }
        return d;
    }
    /**
     * Convert char to byte
     * @param c char
     * @return byte
     */
    public static byte charToByte(char c) {
        return (byte) "0123456789ABCDEF".indexOf(c);
    }
}
