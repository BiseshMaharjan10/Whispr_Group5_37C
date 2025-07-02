/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import Dao.UserDAO;
import Dao.otpDAO;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import view.Fpassword2;
import view.OTPs;
import view.Signin;


/**
 *
 * @author kanchanmaharjan
 */


public class OtpController {
    
    private OTPs otp;
    
        //to redirect to sigin
    public class SignupOTPAction implements OTPs.OTPAction {
    @Override
    public void onValidOTP(JFrame currentFrame) {
        // Redirect to Signin Frame
        Signin signinFrame = new Signin();
        signinFrame.setVisible(true);
        currentFrame.dispose();
    }
}
    //to redirect to signup
    public class ForgotPasswordOTPAction implements OTPs.OTPAction {
    @Override
    public void onValidOTP(JFrame currentFrame) {
        // Redirect to Fpassword2
        Fpassword2 resetWindow = new Fpassword2();
        resetWindow.setVisible(true);
        currentFrame.dispose();
    }
}
    
    
    public boolean expireUnverifiedOtps(){
        otpDAO dao = new otpDAO();
        return dao.deleteUnverifiedOtps();
    }
    
    
   // MAIN VERIFICATION METHOD
    public void verifyOTP(int user_entered_otp, String purpose, JFrame currentFrame) {
        Fpasswordcontroller controller = new Fpasswordcontroller();
        String result = controller.Matchotps(user_entered_otp);

        if ("Invalid OTP".equals(result) || "OTP didn't match".equals(result)) {
            JOptionPane.showMessageDialog(currentFrame, result);
            return;
        }

        JOptionPane.showMessageDialog(currentFrame, "OTP Verified!");

        OTPs.OTPAction action = "signup".equalsIgnoreCase(purpose)
            ? new SignupOTPAction()
            : new ForgotPasswordOTPAction();

        action.onValidOTP(currentFrame);
    }

    //THIS METHOD RETURNS THE ACTION LISTENER
    public ActionListener getOtpConfirmActionListener(OTPs otpFrame, String purpose) {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                String otpText = otpFrame.getEnteredOtp();

                if (otpText.isEmpty() || otpText.equals("Enter OTP here")) {
                    JOptionPane.showMessageDialog(otpFrame, "OTP field cannot be empty");
                    return;
                }

                int userOtp;
                try {
                    userOtp = Integer.parseInt(otpText);
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(otpFrame, "OTP must be a number");
                    return;
                }

                verifyOTP(userOtp, purpose, otpFrame);
            }
        };
    }
    
    public boolean confirm(){
        Boolean verified = false;
        
        
        String user_entered_otp_str = otp.getotp().getText().trim();

        if (user_entered_otp_str.isEmpty() || user_entered_otp_str.equals("Enter OTP here")) {
            JOptionPane.showMessageDialog(otp, "OTP field cannot be empty");
            return false;
        }

        int user_entered_otp;
        try {
            user_entered_otp = Integer.parseInt(user_entered_otp_str);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(otp, "OTP must be a number.");
            return false;
        }   
        return false;
    }
}   
 
