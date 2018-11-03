package com.fmi.mpr.com.fmi.mpr.e_02.client;

import java.util.List;

public class Util {

    public static int findCarriageReturnIndex(byte[] requestBytes) {

        byte[] carriageReturn = new byte[] { 13, 10, 13, 10 };
        int result = -1;
        for (int i = 0; i < requestBytes.length; i++) {
            if (requestBytes[i] == carriageReturn[0]) {
                boolean hasCarriage = true;
                for (int j = 1; j < carriageReturn.length; j++) {
                    if (requestBytes[i + j] != carriageReturn[j]) {
                        hasCarriage = false;
                    }
                }
                if (hasCarriage) {
                    result = i + 4;
                    break;
                }
            }
        }
        return result;
    }
}
