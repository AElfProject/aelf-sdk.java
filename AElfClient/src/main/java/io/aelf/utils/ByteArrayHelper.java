package io.aelf.utils;

@SuppressWarnings("unused")
public class ByteArrayHelper {


    /**
     * The hex string turns into a byte array.
     *
     * @param inHex Hex string to be converted
     * @return The converted byte array results
     */
    public static byte[] hexToByteArray(String inHex) {
        int hexLen = inHex.length();
        byte[] result;
        if (hexLen % 2 == 1) {
            hexLen++;
            result = new byte[(hexLen / 2)];
            inHex = "0" + inHex;
        } else {
            result = new byte[(hexLen / 2)];
        }
        int j = 0;
        for (int i = 0; i < hexLen; i += 2) {
            result[j] = hexToByte(inHex.substring(i, i + 2));
            j++;
        }
        return result;
    }

    /**
     * Byte arrays to hexadecimal.
     *
     * @param bytes Byte array to convert
     * @return Converted Hex string
     */
    public static String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte aByte : bytes) {
            String hex = Integer.toHexString(aByte & 0xFF);
            if (hex.length() < 2) {
                sb.append(0);
            }
            sb.append(hex);
        }
        return sb.toString();
    }

    /**
     * The Hex string turns to byte.
     *
     * @param inHex Hex string to be converted
     * @return Converted byte
     */
    private static byte hexToByte(String inHex) {
        return (byte) Integer.parseInt(inHex, 16);
    }
}
