package Controller;

import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import view.Fpassword2;

public class fpassword2 extends JFrame {
    private JTextField emailField;
    private JButton sendOTPButton;
    private JTextField otpField;
    private JButton verifyOTPButton;
    private JPasswordField passwordField;
    private JPasswordField confirmPasswordField;
    private JButton resetPasswordButton;

    public fpassword2(Fpassword2 userView) {
        // Set up frame
        setTitle("Forgot Password");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        // Email field
        emailField = new JTextField("Enter your Email address");
        emailField.setBounds(100, 30, 200, 30);
        add(emailField);

        // Send OTP Button
        sendOTPButton = new JButton("Send OTP");
        sendOTPButton.setBounds(100, 70, 200, 30);
        add(sendOTPButton);

        // OTP Field
        otpField = new JTextField();
        otpField.setBounds(100, 110, 200, 30);
        add(otpField);

        // Verify OTP Button
        verifyOTPButton = new JButton("Verify OTP");
        verifyOTPButton.setBounds(100, 150, 200, 30);
        add(verifyOTPButton);

        // Password fields
        passwordField = new JPasswordField("Set Password");
        passwordField.setBounds(100, 190, 200, 30);
        add(passwordField);

        confirmPasswordField = new JPasswordField("Confirm Password");
        confirmPasswordField.setBounds(100, 230, 200, 30);
        add(confirmPasswordField);

        // Reset password
        resetPasswordButton = new JButton("Reset Password");
        resetPasswordButton.setBounds(100, 270, 200, 30);
        add(resetPasswordButton);

        // Attach ActionListeners
        sendOTPButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String email = emailField.getText();
                String validationMessage = controller.register(email);
                if (validationMessage != null) {
                    JOptionPane.showMessageDialog(null, validationMessage);
                    return;
                }

                int otp = (int)(Math.random() * 900000 + 100000); // Generate 6-digit OTP
                controller.registerOtp(otp, "reset", false, email);
                controller.Emailsend(email, "reset", true);
                JOptionPane.showMessageDialog(null, "OTP sent to your email");
            }
        });

        verifyOTPButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int enteredOtp = Integer.parseInt(otpField.getText());
                String result = controller.Matchotps(enteredOtp);
                JOptionPane.showMessageDialog(null, result);
            }
        });

        resetPasswordButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String pass1 = new String(passwordField.getPassword());
                String pass2 = new String(confirmPasswordField.getPassword());
                String result = controller.ResetPassword(pass2, pass1);
                JOptionPane.showMessageDialog(null, result);
            }
        });

        setVisible(true);
    }

    public static void main(String[] args) {
        new fpassword2();
    }
}
