/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author kanchanmaharjan
 */
public class ProfileModel {
    private int id;
    private String profileName;
    
    public ProfileModel(String profileName){
        this.profileName = profileName;
    }
    
    public String getprofileName(){
        return profileName;
    }
    
    public void setprofileName(String profileName){
        this.profileName = profileName;
    }
    
    public int getId(){
        return id;
    }
    public void setId(Integer id){
        this.id= id;
        }
}
