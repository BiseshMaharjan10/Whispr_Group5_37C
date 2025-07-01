
package view;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class Logout extends javax.swing.JFrame {

    public Logout() {
        initComponents();
        setLocationRelativeTo(null);
    }



    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        profilePicLabel = new javax.swing.JLabel();
        loggedInUserLabel = new javax.swing.JLabel();
        logoutButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);

        loggedInUserLabel.setText("loggedInUserLabel");

        logoutButton.setText("Log out");
        logoutButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                logoutButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(120, 120, 120)
                .addComponent(profilePicLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 130, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(loggedInUserLabel)
                        .addGap(140, 140, 140))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(logoutButton)
                        .addGap(154, 154, 154))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addComponent(profilePicLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(loggedInUserLabel)
                .addGap(33, 33, 33)
                .addComponent(logoutButton)
                .addGap(28, 28, 28))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void logoutButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_logoutButtonActionPerformed

    }//GEN-LAST:event_logoutButtonActionPerformed


    public static void main(String args[]) {

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Logout().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel loggedInUserLabel;
    private javax.swing.JButton logoutButton;
    private javax.swing.JLabel profilePicLabel;
    // End of variables declaration//GEN-END:variables

    public void updateName(String currentUsername) {
        loggedInUserLabel.setText(currentUsername);
    }

    public void updateProfilePic(String imagePath) {
        if (imagePath != null && !imagePath.isEmpty()) {
            File imageFile = new File(imagePath);
            if (imageFile.exists()) {
                try {
                    BufferedImage originalImage = ImageIO.read(imageFile);

                    int originalWidth = originalImage.getWidth();
                    int originalHeight = originalImage.getHeight();

                    // Max display area size (e.g. 150x150)
                    int maxWidth = 150;
                    int maxHeight = 150;

                    // Calculate scaled size while keeping aspect ratio
                    double widthRatio = (double) maxWidth / originalWidth;
                    double heightRatio = (double) maxHeight / originalHeight;
                    double scaleFactor = Math.min(widthRatio, heightRatio);

                    int scaledWidth = (int) (originalWidth * scaleFactor);
                    int scaledHeight = (int) (originalHeight * scaleFactor);

                    // Scale the image
                    Image scaledImage = originalImage.getScaledInstance(scaledWidth, scaledHeight, Image.SCALE_SMOOTH);
                    ImageIcon scaledIcon = new ImageIcon(scaledImage);

                    // Resize the label and panel
                    profilePicLabel.setIcon(scaledIcon);
                    profilePicLabel.setPreferredSize(new Dimension(scaledWidth, scaledHeight));
                    profilePicLabel.setSize(new Dimension(scaledWidth, scaledHeight));
                    profilePicLabel.revalidate();

                    // Optionally resize parent panel too
                    if (profilePicLabel.getParent() != null) {
                        profilePicLabel.getParent().setPreferredSize(new Dimension(scaledWidth, scaledHeight));
                        profilePicLabel.getParent().revalidate();
                    }

                } catch (IOException e) {
                    System.err.println("Error reading image: " + e.getMessage());
                }
            } else {
                System.err.println("Image file not found: " + imagePath);
            }
        } else {
            System.out.println("Image path is null or empty.");
        }
    }


    
    public void logoutButtonListener(ActionListener listener) {
        logoutButton.addActionListener(listener);
    }



}



