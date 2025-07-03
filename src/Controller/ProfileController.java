package Controller;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import view.Profile;
import Dao.ChatClientDAO;
import Model.MessageModel;
import Model.ProfileModel;


public class ProfileController{ //implements ActionListener {
    private final Profile userProfile;
    private String fullName;
    private ChatClientDAO dao;
    private String selectedUserName;
    private ProfileModel model;
    
    
    
    public ProfileController(Profile view, ChatController controller){
        this.dao = new ChatClientDAO();
        this.selectedUserName= controller.selectedUserName;
        MessageModel model2 = new MessageModel();
        this.selectedUserName = model2.getCurrentUserName();
        this.userProfile = view;
        
        System.out.println("currentUsername " + (controller.selectedUserName));

//        model.addChangeListener(new ChangeListener() {
//            @Override
//            public void stateChanged(ChangeEvent e) {
//                view.updateName(controller.selectedUserName);
//            }
//        });
    }   
}