package utils;

public class EmailSenderTest {
    public static void main(String[] args) {
        // Change this to the email address you want to send to
        String recipientEmail = "recipient@example.com";

        // Generate OTP
        int otp = EmailSender.generateOTP();

        // Email subject and body
        String subject = "Your OTP Code";
        String body = "<h3>Your OTP is: " + otp + "</h3>"
                    + "<p>Please use this code to verify your account.</p>";

        // Send the email
        EmailSender.sendEmail(recipientEmail, subject, body);

        System.out.println("OTP sent to: " + recipientEmail);
    }
}