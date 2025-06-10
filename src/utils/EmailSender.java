
package utils;

import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import java.util.Properties;
import jakarta.mail.Authenticator;
import java.util.Random;
import java.util.concurrent.*;
import Model.OTPModel;
import java.time.LocalDateTime;
import Controller.Fpasswordcontroller;
import javax.swing.JLabel;
import view.OTPs;


public class EmailSender {
    
    //to generate a 6 digit otp
    
     public static int generateOTP() {
        Random rand = new Random();
        return 100000 + rand.nextInt(900000);// Always returns a 6-digit OTP  
    }       

     
     //to send otp over email
     
    public static void sendEmail(String to, String subject, String body) {
         
        final String from = "biseshmhrz10@gmail.com";
        final String password = "yvuo rbhw bpeo vvlz"; // Use App Password if Gmail

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");


        Session session = Session.getInstance(props,
            new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(from, password);
                }
            });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.setRecipients(
                Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject(subject);
            message.setContent(body, "text/html; charset=utf-8");
            Transport.send(message);

        } catch (MessagingException e) {
            System.err.println("Failed to send email. Error: " + e.getMessage());
            e.printStackTrace();
        }
        
    }
    
    
    
    
    // Timer function that counts down every second

public static void startCountdownAsync(int seconds) {
    ExecutorService executor = Executors.newSingleThreadExecutor();

    executor.execute(() -> {
        for (int i = seconds; i >= 0; i--) {
            int minutes = i / 60;
            int sec = i % 60;
            System.out.printf("⏳ Time left: %02d:%02d\n", minutes, sec);
            try {
                Thread.sleep(1000); // Wait 1 second
            } catch (InterruptedException e) {
                System.err.println("Countdown interrupted");
                break;
            }
        }
        executor.shutdown();
    });
}


// New overloaded method – gives you full control
public static String Emailsend(String email, String purpose, boolean verified) {
    // Generate OTP
    int otp = EmailSender.generateOTP();

    // Register the OTP
    Fpasswordcontroller temp = new Fpasswordcontroller();
    String result = temp.registerOtp(otp, purpose, verified, email);
    System.out.println(result);

    // Email subject and body
    String body;
    String subject = "Verify Your Account";
    if(purpose.equals("signup")){
    body = "<h3>Your OTP is: " + otp + "</h3>"
                + "<p>This message was sent to verify your account while signing you up</p>";
    }else{
    body = "<h3>Your OTP is: " + otp + "</h3>"
                + "<p>This message was sent to verify your account coz you dumba** forgot your fcking password.</p>";
    }
    // Send the email
    sendEmail(email, subject, body);


    return "OTP sent to: " + email;
}
}