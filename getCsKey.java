import common.CommonUtils;

import java.util.Arrays;


public class getCsKey {
    public static byte[] cs_auth = new byte[1024];

    public static void getAuthKey() {
        try {
            javax.crypto.Cipher cipher = javax.crypto.Cipher.getInstance("RSA/ECB/PKCS1Padding");
            byte[] authkeyPubBytes = CommonUtils.readAll(CommonUtils.class.getClassLoader().getResourceAsStream("resources/authkey.pub"));
//            System.out.println(Arrays.toString(authkeyPubBytes));
            byte[] authkeyPub = CommonUtils.MD5(authkeyPubBytes);
            System.out.println("[*] authkey.pub =>> Md5= " + CommonUtils.toHex(authkeyPub));

            if (CommonUtils.class.getClassLoader().getResource("resources/cobaltstrike.auth") != null) {
                cs_auth = CommonUtils.readAll(CommonUtils.class.getClassLoader().getResourceAsStream("resources/authkey.auth"));
            } else {
                cs_auth = CommonUtils.readFile("./cobaltstrike.auth");
            }

            java.security.spec.X509EncodedKeySpec var3 = new java.security.spec.X509EncodedKeySpec(authkeyPubBytes);
            java.security.KeyFactory var4 = java.security.KeyFactory.getInstance("RSA");
            java.security.Key publicKey = var4.generatePublic(var3);
            cipher.init(javax.crypto.Cipher.DECRYPT_MODE, publicKey);
            byte[] rsaDecrypt = cipher.doFinal(cs_auth);

            System.out.println("[*] cs_key= " + Arrays.toString(rsaDecrypt));
//            System.out.println(new sun.misc.BASE64Encoder().encode(rsaDecrypt).replace("\n", ""));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        getAuthKey();
    }
}
