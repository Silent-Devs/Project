package logic;

public class EncryptDecrypt {
    public static String encryptDecrypt(String s) {
        char xorKey = 'B';
        StringBuilder output = new StringBuilder();
        int len = s.length();
        for (int i = 0; i < len; i++) {
            output.append((char) (s.charAt(i) ^ xorKey));
        }
        return output.toString();
    }

    public static void main(String[] args) {
        System.out.println(encryptDecrypt("12345"));
    }
}
