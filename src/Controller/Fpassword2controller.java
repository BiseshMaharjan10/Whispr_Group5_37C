/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
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
    public Fpassword2Controller(Fpassword2 view) {
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
