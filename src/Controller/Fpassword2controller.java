package Controller;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import view.Fpassword2;
import view.Signin;

/**
 *
 * @author kanchanmaharjan
 */
public class Fpassword2controller {
 
 
    private Fpassword2 view;
    
    public Fpassword2controller(Fpassword2 view) {
        this.view = view;

        // Add listener to reset button
        this.view.addResetListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                reset();
            }
        });
        
        this.view.addConfirmPasswordDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) {
                validatePasswordMatch();
            }

            public void removeUpdate(DocumentEvent e) {
                validatePasswordMatch();
            }

            public void changedUpdate(DocumentEvent e) {
                validatePasswordMatch();
            }
            });
   
    }
   
    
        //for same password
    private void validatePasswordMatch() {
    String pass = String.valueOf(view.getSetPasswordField().getPassword()).trim();
    String confirm = String.valueOf(view.getConfirmPasswordField().getPassword()).trim();

    if (!confirm.equals(pass)) {
        view.setErrorLabelText("Passwords do not match");
        view.getConfirmPasswordField().setForeground(Color.RED);
    } else {
        view.setErrorLabelText("");
        view.getConfirmPasswordField().setForeground(Color.BLACK);
    }
}
   private void reset() {
    String password = String.valueOf(view.getSetPasswordField().getPassword()).trim();
    String confirmPassword = String.valueOf(view.getConfirmPasswordField().getPassword()).trim();

    // Optional: match check
    if (!password.equals(confirmPassword)) {
        view.setErrorLabelText("Passwords do not match");
        view.getConfirmPasswordField().setForeground(Color.RED);
        return;
    } else {
        view.setErrorLabelText("");
        view.getConfirmPasswordField().setForeground(Color.BLACK);
    }

    // Call the model
    Fpasswordcontroller controller = new Fpasswordcontroller();
    String result = controller.ResetPassword(confirmPassword, password);  // Now both variables are defined

    JOptionPane.showMessageDialog(view, result);

    if (result.equals("Rest password Successful")) {
        new Signin().setVisible(true);
        view.dispose();
    }
   }
}