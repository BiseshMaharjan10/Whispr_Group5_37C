/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import java.util.ArrayList;
import java.util.List;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 *
 * @author kanchanmaharjan
 */
public class ProfileModel {
    private int id;
    private String profileName;
    private List<ChangeListener> listeners = new ArrayList<>();

    
    public ProfileModel(String profileName){
        this.profileName = profileName;
    }
    
    public String getprofileName(){
        return profileName;
    }
    
    public void setprofileName(String profileName){
        this.profileName = profileName;
    }
    
    public void setName(String name) {
        this.profileName = name;
        notifyListeners();
    }
    
    public int getId(){
        return id;
    }
    
    public void addChangeListener(ChangeListener listener) {
        listeners.add(listener);
    }
    
    public void setId(Integer id){
        this.id= id;
        }
    
    private void notifyListeners() {
    for (ChangeListener listener : listeners) {
        listener.stateChanged(new ChangeEvent(this));
    }
}
}
