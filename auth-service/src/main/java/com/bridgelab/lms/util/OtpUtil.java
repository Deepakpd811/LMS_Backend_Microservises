package com.bridgelab.lms.util;


import java.time.LocalDateTime;
import java.util.Random;

import org.springframework.stereotype.Component;

@Component
public class OtpUtil {
    public static String generateOtp() {
        Random random = new Random();
        return String.format("%06d", random.nextInt(999999));
    }

    public static boolean validateOtp(String userOtp, LocalDateTime time , String reqOtp){

        if(userOtp.equals(reqOtp)){
            return  true;
        }
        return  false;
    }

}


