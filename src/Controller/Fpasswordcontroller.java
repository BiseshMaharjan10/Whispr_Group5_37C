package Controller;

import Dao.otpDAO;
import Dao.UserDAO;
import Model.OTPModel;
import view.OTPs;
import javax.swing.JOptionPane;
import Controller.SignUPController;
import Controller.Fpasswordcontroller;
import java.awt.Color;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import utils.EmailSender;
import view.FPassword;


/**
 *
 * @author bibek
 */
public class Fpasswordcontroller {
    private UserDAO userDAO = new UserDAO();

    private static String userEmail; // Instance variable to store emaail

    public Fpasswordcontroller(FPassword signin) {
    }

    public Fpasswordcontroller() {
    }

//    public Fpasswordcontroller() {
//    }
//
//    public Fpasswordcontroller() {
//    }


    public String register(String email) {
  
        // Validate email format
        boolean isValid = email.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$" );
        
        if (!isValid ||email.equals("Enter your Email address")) {
            return "Enter a valid email";
        }
        
        // Check if email already exists in the database
        else if (!userDAO.CheckUser(email)) {
            return "Email doesn't exist";
        }

        
        // Save email to instance variable for use outside this method
        userEmail = email;
        return null;
       
    }
    
    //register created otp
    public String registerOtp(int otpCode, String purpose, Boolean isVerified, String email) {

        OTPModel otp = new OTPModel(otpCode, purpose, isVerified, email);

        otpDAO dao = new otpDAO(); // Create instance
        boolean success = dao.registerOtp(otp); // Call method properly

        return success ? "Opt sent to your email address" : "Registration failed!";
    }
    
    
    
    //compare otps
    public String Matchotps(int user_entered_otp){
        
     otpDAO temp_v = new otpDAO();
            Integer temp_vv = temp_v.getOTPCode(user_entered_otp);

            if (temp_vv == null) {
                return "OTP didn't match";
            } else if (temp_vv == user_entered_otp) {

                //to update isverified status
                Boolean isVerified = true;
                otpDAO temp_verification = new otpDAO();
                Boolean after_otp_update = temp_verification.updateIsVerified(temp_vv,isVerified);
                
                return "Here we go";

            } else {
                return "Invalid OTP";
            }
        }

    public String Emailsend(String email, String purpose, Boolean verified) {
       if (verified) {
           String result = EmailSender.Emailsend(email, purpose, verified);
           if (result.contains("OTP sent")) {
               return "OTP verification email sent successfully.";
           } else {
               return "Failed to send email.";
           }
       } else {
           return "Email not sent. OTP not verified.";
       }
   }


    //register created otp
    public String ResetPassword(String confirmPassword, String setPassword) {
        
        String temp_email = userEmail;
        UserDAO otps = new UserDAO();

        if (setPassword.equals("Set Password") || confirmPassword.equals("Confirm Password")) {
            return "please fill all the fields";
        }
        else if (!setPassword.equals(confirmPassword) ) {
            return "Confirm Password didn't Match";
        } 
        
        else {
        Boolean status = otps.UpdatePassword(temp_email,confirmPassword);
        return status ? "Rest password Successful" : "Can't reset the password";
    }
   }

    void open() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    
    
}



