
import java.util.Timer;
import java.util.TimerTask;

//package Dao;
//
//import Dao.otpDAO;
//import Model.OTPModel;
//
//import java.time.LocalDateTime;
//
//public class Temp {
//    public static void main(String[] args) {
//        
//
//        otpDAO dao = new otpDAO();
//
//        // Call register method and check result
//        boolean success = dao.OTPExpired(734014);
//        
////        // Create OTPModel test object
////        int otpCode = 123456;  // Or generate dynamically
////        String purpose = "password_reset";
////        String status = "active";
////        LocalDateTime createdAt = LocalDateTime.now();
////        boolean isVerified = false;
////        int userId = 3;  // Use a valid user_id from your DB
////
////        OTPModel otpModel = new OTPModel(otpCode, purpose, status, createdAt, isVerified, userId);
////
////        // Create DAO object
////        otpDAO dao = new otpDAO();
////
////        // Call register method and check result
//        boolean success = dao.registerOtp(otpModel);
//
//        if (success) {
//            System.out.println("OTP registered successfully.");
//        } else {
//            System.out.println("Failed to register OTP.");
//        }
//    }
//}


//
//
//public class Temp {
//    public void startCountdown() {
//        Timer timer = new Timer();
//
//        timer.schedule(new TimerTask() {
//            @Override
//            public void run() {
//                return false;
//                timer.cancel(); // Stops the timer
//            }
//        }, 60000); // 60000 ms = 1 minute
//    }
//
//    public static void main(String[] args) {
//        Temp otpTimer = new Temp();
//        otpTimer.startCountdown(); // Starts the 1-minute timer
//        System.out.println("Timer started... waiting 1 minute...");
//    }}