
package Model;

/**
 *
 * @author bibek
 */
public class SigninModel {
    private int id;

     private String email;
    private String password;

   
    public SigninModel(String email,String password){

    this.email = email;
    this.password = password;

    }
    
    public String getEmail(){
        return email;
    }
        public void setEmail(String email){
        this.email=email;
    }
            public String getPassword(){
        return password;
    }
        public void setPassword(String password){
        this.password=password;
    }

        public int getId(){
        return id;
    }
        public void setId(Integer id){
        this.id= id;
        }
}

