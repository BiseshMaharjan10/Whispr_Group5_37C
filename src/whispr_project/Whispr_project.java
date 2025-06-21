
package whispr_project;

import Controller.SignUPController;
import view.Signup;

public class Whispr_project {

  
    public static void main(String[] args) {
       Signup signupForm = new Signup();
       SignUPController signupController = new SignUPController(signupForm);
       signupController.open();
    }
    
}
