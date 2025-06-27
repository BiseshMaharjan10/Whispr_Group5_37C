
package Controller;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import view.Profile;
import Dao.ChatClientDAO;
import Model.ProfileModel;


public class ProfileController{ //implements ActionListener {
    private final Profile userProfile;
    private String fullName;
    private ChatClientDAO dao;
    private String selectedUserEmail;
    private ProfileModel model;
    private String temp_name = "bisesh";
//    private ChatController controller;
    
    public ProfileController(Profile view, ChatController controller, ProfileModel model){
        this.dao = new ChatClientDAO();
        this.model = model;
        this.userProfile = view;
        
        this.selectedUserEmail = controller.getSelectedUserEmail();
        this.fullName = dao.getFirstnLastName(selectedUserEmail);
        
        System.out.println("currentUsername" + model.getprofileName());
        
//        userProfile.addProfileListener(new ProfileListener());
        

      //  this.controller = new ChatController();
        
        // Register listener: when model changes, update view
        model.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                view.updateName(model.getprofileName());
            }
        });

    
    
    }
    
//    public ProfileController(Profile view){
//        this.userProfile= view;
//    }
//    
//    public void openProfile(){
//        
//        this.userProfile.setVisible(true);
//    }
//
//    @Override
//    public void actionPerformed(ActionEvent e) {
//        
//    }
    
    
//    public void setupProfileListener(Profile view){
//        view.addProfileListener(new ProfileListener());
//    }
//    class ProfileListener implements ActionListener{
//
//        @Override
//        public void actionPerformed(ActionEvent e) {
//            System.out.println("Clicked");
//            Profile profile = new Profile();
//            new Profile
//        }
//        
//    }
    
}
