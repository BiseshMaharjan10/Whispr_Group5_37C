package view;

import Controller.Fpassword2Controller;
import Controller.Fpasswordcontroller;
import java.awt.Color;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;


/**
 *
 * @author bibek
 */
public class Fpassword2 extends javax.swing.JFrame {
    
    private boolean isPasswordVisible = false;
    private javax.swing.JLabel errorLabelPassword;

    /**
     * Creates new form SignUp
     */
    public Fpassword2() {
        initComponents();

    errorLabelPassword = new javax.swing.JLabel();
    errorLabelPassword.setForeground(Color.RED);
    errorLabelPassword.setText("");
    this.add(errorLabelPassword);
    errorLabelPassword.setBounds(250, 230, 250, 20); // for position for error  
        //for show password checkbox

    setpassword.setText("Set Password");
    setpassword.setForeground(Color.GRAY);
    setpassword.setEchoChar((char) 0);  // show as plain text initially

    confirmpassword.setText("Confirm Password");
    confirmpassword.setForeground(Color.GRAY);
    confirmpassword.setEchoChar((char) 0);
        }   
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        reset = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        confirmpassword = new javax.swing.JPasswordField();
        setpassword = new javax.swing.JPasswordField();
        showpassword = new javax.swing.JCheckBox();
        jLabel5 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(252, 251, 244));

        jLabel2.setFont(new java.awt.Font("Helvetica Neue", 1, 24)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(29, 61, 130));
        jLabel2.setText("Set a new Password");

        jLabel3.setFont(new java.awt.Font("Helvetica Neue", 0, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(29, 61, 130));
        jLabel3.setText("Must be atleast 8 Characters");

        reset.setBackground(new java.awt.Color(29, 61, 130));
        reset.setFont(new java.awt.Font("Helvetica Neue", 1, 14)); // NOI18N
        reset.setForeground(new java.awt.Color(252, 251, 244));
        reset.setText("RESET PASSWORD");
        reset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                resetActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Helvetica Neue", 0, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(29, 61, 130));
        jLabel4.setText("Able to reacall ?");

        confirmpassword.setText("Confirm Password");
        confirmpassword.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                confirmpasswordFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                confirmpasswordFocusLost(evt);
            }
        });
        confirmpassword.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                confirmpasswordActionPerformed(evt);
            }
        });

        setpassword.setText("Set Password");
        setpassword.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                setpasswordFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                setpasswordFocusLost(evt);
            }
        });
        setpassword.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                setpasswordActionPerformed(evt);
            }
        });

        showpassword.setText("show password");
        showpassword.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                showpasswordActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Helvetica Neue", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(29, 61, 130));
        jLabel5.setText("<html><u>Sign in</html></u>");
        jLabel5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel5MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(160, Short.MAX_VALUE)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(144, 144, 144))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(58, 58, 58)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(showpassword)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jLabel2)
                        .addComponent(reset, javax.swing.GroupLayout.DEFAULT_SIZE, 337, Short.MAX_VALUE)
                        .addComponent(jLabel3)
                        .addComponent(confirmpassword)
                        .addComponent(setpassword)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(145, 145, 145)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel3)
                .addGap(22, 22, 22)
                .addComponent(setpassword, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(confirmpassword, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addComponent(showpassword)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(reset, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(9, 9, 9)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(149, Short.MAX_VALUE))
        );

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/fpass_1.png"))); // NOI18N

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 575, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void resetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_resetActionPerformed
// TODO add your handling code here:
    }//GEN-LAST:event_resetActionPerformed

    private void confirmpasswordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_confirmpasswordActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_confirmpasswordActionPerformed

    private void showpasswordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_showpasswordActionPerformed
        if (showpassword.isSelected()) {
            setpassword.setEchoChar((char) 0); // Show password as plain text
            confirmpassword.setEchoChar((char)0);
        } else {
            if (!String.valueOf(setpassword.getPassword()).equals("Set Password")) {
                setpassword.setEchoChar('*'); // Mask password
            }if (!String.valueOf(confirmpassword.getPassword()).equals("Confirm Password")) {
                confirmpassword.setEchoChar('*'); // Mask password
            }
        }         // TODO add your handling code here:
    }//GEN-LAST:event_showpasswordActionPerformed

    private void setpasswordFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_setpasswordFocusGained
  if (String.valueOf(setpassword.getPassword()).equals("Set Password")) {
        setpassword.setText("");
        setpassword.setForeground(Color.BLACK);
        setpassword.setEchoChar('*'); 
  }        // TODO add your handling code here:
    }//GEN-LAST:event_setpasswordFocusGained

    private void confirmpasswordFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_confirmpasswordFocusGained
  if (String.valueOf(confirmpassword.getPassword()).equals("Confirm Password")) {
        confirmpassword.setText("");
        confirmpassword.setForeground(Color.BLACK);
        confirmpassword.setEchoChar('*'); }        // TODO add your handling code here:
    }//GEN-LAST:event_confirmpasswordFocusGained

    private void setpasswordFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_setpasswordFocusLost
    if (String.valueOf(setpassword.getPassword()).trim().equals("")) {
        setpassword.setText("Set Password");
        setpassword.setForeground(Color.GRAY);
        setpassword.setEchoChar((char) 0);
    }        // TODO add your handling code here:
    }//GEN-LAST:event_setpasswordFocusLost

    private void confirmpasswordFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_confirmpasswordFocusLost
    if (String.valueOf(confirmpassword.getPassword()).trim().equals("")) {
        confirmpassword.setText("Confirm Password");
        confirmpassword.setForeground(Color.GRAY);
        confirmpassword.setEchoChar((char) 0);
    }        // TODO add your handling code here:
    }//GEN-LAST:event_confirmpasswordFocusLost

    private void setpasswordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_setpasswordActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_setpasswordActionPerformed

    private void jLabel5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel5MouseClicked
        Signin signinwindow = new Signin();

        // Show the other window
        signinwindow.setVisible(true);

        // Optionally, close the current window
        this.dispose();    // TODO add your handling code here:
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel5MouseClicked
 
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Fpassword2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Fpassword2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Fpassword2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Fpassword2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                Fpassword2 view = new Fpassword2();
                new Fpassword2Controller(view);
                new Fpassword2().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPasswordField confirmpassword;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JButton reset;
    private javax.swing.JPasswordField setpassword;
    private javax.swing.JCheckBox showpassword;
    // End of variables declaration//GEN-END:variables
    
    public void addResetListener(ActionListener listener){
      reset.addActionListener(listener);      
    }
    public void addConfirmPasswordDocumentListener(DocumentListener listener) {
        confirmpassword.getDocument().addDocumentListener(listener);
    }
    public javax.swing.JPasswordField getSetPasswordField() {
        return setpassword;
    }

    public javax.swing.JPasswordField getConfirmPasswordField() {
        return confirmpassword;
    }
    
    public void setErrorLabelText(String text) {
        errorLabelPassword.setText(text);
    }
}
