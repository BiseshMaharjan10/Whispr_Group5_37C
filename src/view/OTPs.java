package view;

import Controller.SignUPController;
import Controller.Fpasswordcontroller;
import Controller.OtpController;
import java.awt.Color;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import utils.EmailSender;
import Dao.otpDAO;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import Dao.UserDAO;
import java.awt.event.ActionListener;

/**
 *
 * @author bibek
 */
public class OTPs extends javax.swing.JFrame {
    private String purpose;

    public OTPs(String purpose) {
        initComponents();
        
         startCountdownAsync();

        this.purpose = purpose;
        
        OtpController controller = new OtpController();
        confirm.addActionListener(controller.getOtpConfirmActionListener(this, purpose));
        
       
    }
    

    /**
     * Creates new form OTPs
     */
    public OTPs() {
        initComponents();
        
       
        
    }
    
    public String getEnteredOtp(){
        return enterotp.getText().trim();
    }

    
    public interface OTPAction {
    void onValidOTP(JFrame currentFrame);
}
     
        
        // Method to start the countdown
            public void startCountdownAsync() {
        new Thread(() -> {
            try {
                Thread.sleep(5);
            } catch (InterruptedException e) {
                System.out.println("Initial delay interrupted.");
                return;
            }

            for (int i = 180; i >= 0; i--) {
                int minutes = i / 60;
                int seconds = i % 60;

                SwingUtilities.invokeLater(() -> {
                    System.out.println("Counting every second");
                    timer.setText(String.format("%02d:%02d", minutes, seconds));
                    otpwillexpire.setText(String.format("OTP will Expire  in "));
                });

                try {
                    Thread.sleep(1000); 
                } catch (InterruptedException e) {
                    System.out.println("Countdown interrupted.");
                    break;
                }

                if (i == 0 ) {
                    OtpController controller = new OtpController();
                    boolean success =controller.expireUnverifiedOtps();
                   
                    SwingUtilities.invokeLater(() -> {
                            otpexpire.setText(String.format("OTP Expired"));
                            timer.setText(String.format(""));
                            otpwillexpire.setText(String.format(""));
                    });
                }
            }
        }).start();
    }
        
                    
            



    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        enterotp = new javax.swing.JTextField();
        confirm = new javax.swing.JButton();
        otpwillexpire = new javax.swing.JLabel();
        timer = new javax.swing.JLabel();
        otpexpire = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(29, 61, 130));

        jPanel2.setBackground(new java.awt.Color(252, 251, 244));

        jLabel4.setFont(new java.awt.Font("Kohinoor Bangla", 1, 21)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(29, 61, 130));
        jLabel4.setText("Verification");

        jLabel3.setFont(new java.awt.Font("Helvetica Neue", 0, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(29, 61, 130));
        jLabel3.setText("OTP Code has been sent to your E-mail");

        enterotp.setText("Enter OTP here");
        enterotp.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                enterotpFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                enterotpFocusLost(evt);
            }
        });
        enterotp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                enterotpActionPerformed(evt);
            }
        });

        confirm.setBackground(new java.awt.Color(29, 61, 130));
        confirm.setFont(new java.awt.Font("Helvetica Neue", 1, 18)); // NOI18N
        confirm.setForeground(new java.awt.Color(252, 251, 244));
        confirm.setText("Confirm");
        confirm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                confirmActionPerformed(evt);
            }
        });

        otpwillexpire.setFont(new java.awt.Font("Al Bayan", 0, 12)); // NOI18N
        otpwillexpire.setForeground(new java.awt.Color(29, 61, 130));

        timer.setFont(new java.awt.Font("Al Bayan", 1, 12)); // NOI18N
        timer.setForeground(new java.awt.Color(142, 142, 142));

        otpexpire.setFont(new java.awt.Font("Bangla Sangam MN", 1, 12)); // NOI18N
        otpexpire.setForeground(new java.awt.Color(255, 0, 0));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap(64, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 310, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(enterotp, javax.swing.GroupLayout.DEFAULT_SIZE, 279, Short.MAX_VALUE)
                            .addComponent(jLabel4)
                            .addComponent(confirm, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(otpwillexpire)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(timer, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(105, 105, 105))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(otpexpire, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(135, 135, 135))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(173, 173, 173)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3)
                .addGap(18, 18, 18)
                .addComponent(enterotp, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(23, 23, 23)
                .addComponent(confirm, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(otpwillexpire, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(timer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 17, Short.MAX_VALUE)
                .addComponent(otpexpire, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(127, 127, 127))
        );

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setBackground(new java.awt.Color(255, 255, 255));
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/otps.jpg"))); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 392, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(65, 65, 65))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 483, Short.MAX_VALUE)
                .addGap(36, 36, 36))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 418, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void enterotpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_enterotpActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_enterotpActionPerformed

    private void confirmActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_confirmActionPerformed
            // TODO add your handling code here:
            OtpController Otp = new OtpController();
            boolean check = Otp.confirm();
        System.out.println(check);
    }//GEN-LAST:event_confirmActionPerformed

    private void enterotpFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_enterotpFocusGained
  if (enterotp.getText().trim().equals("Enter OTP here")) {
        enterotp.setText("");
        enterotp.setForeground(Color.BLACK);        // TODO add your handling code here:
    }//GEN-LAST:event_enterotpFocusGained
    }
    
    private void enterotpFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_enterotpFocusLost
   if (enterotp.getText().trim().equals("")) {
        enterotp.setText("Enter OTP here");
        enterotp.setForeground(Color.GRAY);        // TODO add your handling code here:
    }//GEN-LAST:event_enterotpFocusLost
    }
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
            java.util.logging.Logger.getLogger(OTPs.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(OTPs.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(OTPs.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(OTPs.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                
                new OTPs().setVisible(true);
            }
        });
        
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton confirm;
    private javax.swing.JTextField enterotp;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JLabel otpexpire;
    private javax.swing.JLabel otpwillexpire;
    private javax.swing.JLabel timer;
    // End of variables declaration//GEN-END:variables
    
    public void addConfirmListener(ActionListener listener){
      confirm.addActionListener(listener);      
    }
    
    public javax.swing.JTextField getotp(){
        return enterotp;
    }
    
    
}
