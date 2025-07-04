package Controller;

import Dao.ChatClientDAO;
import Dao.UserDAO;
import Model.MessageModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import utils.Main;
import view.ClientGui;
import view.Signin;
//
//public class SigninController implements ActionListener{
//    private final UserDAO userDAO = new UserDAO();
//    private final Signin userView;
//    private  String currentUserEmail;
//    private ChatClientDAO chatdao;
//    private String email;
//    private String password;
//    
//    public SigninController (Signin userView){
//        this.userView = userView;
//        this.email = userView.getEmailField();
//        this.password = userView.getPasswordField();
//        this.chatdao = new ChatClientDAO();
////        this.signin.getSigninButton().addActionListener(new LoginListener());
//    }
//    
//    
//        @Override
//        public void actionPerformed(ActionEvent e) {
//            
//        }
//
//
//            public String loginUser(String email, String password) {
//                // Validate email format
//                boolean isValid = email.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$");
//                if (!isValid) {
//                    return "Enter a valid email";
//                }
//
//                // Check credentials in database
//                boolean success = userDAO.Logincredentials(email, password);
//                this.currentUserEmail = email;
//
//
//                if(success){
//                    String fullname = chatdao.getFirstnLastName(email);
//
//                    SwingUtilities.invokeLater(() -> {
//                        try {
//
//                            ClientHandler handler = new ClientHandler();
//                            // Create GUI
//                            ClientGui gui = new ClientGui(fullname);
//
//                            // Create Controller and wire its
//                            ChatController controller = new ChatController(gui, email);
//
//                            //model 
//                            MessageModel msg = new MessageModel();
//
//                            // Set contact list (assuming you want to preload users here)
//                            controller.updateContactList("");
//        //                    gui.setContactListData(contactNames); // use a setter method inside ClientGui
//
//                            Map<String, String> imageMap = controller.getUserImageMap();
//                            gui.setContactListRenderer(imageMap); // new method you'll define in ClientGui
//
//                            // Connect Listeners
//                            gui.addSendButtonListener(controller.getSendActionListener());
//                            gui.addMessageInputListener(controller.getSendActionListener());
//
//                            gui.addContactListSelectionListener(e -> {
//                                String csv = utils.GlobalState.onlineUsersCsv;
//                                String selected = gui.getSelectedContact();
//
//                                if (selected != null) {
//                                    controller.showMessages(selected);
//                                    controller.loadChatHistory(selected);
//                                    gui.getBottomPanel().setVisible(true);
//                                    List<String> OnlineUsers = msg.getOnlineUsers();
//
//                                    if (selected != null && csv != null && Arrays.asList(csv.split(",")).contains(selected)) {
//                                        gui.showOnlineStatus();
//                                    } else {
//                                        gui.hideOnlineStatus();
//                                        System.out.println("he isn't online " + csv + " " + selected);
//                                    }
//                                    gui.showProfileButton();
//                                    ChatController temp_controller = new ChatController(selected, gui, "nexbitt@gmail.com");
//                                }
//                            });
//                            gui.addSearchButtonListener(e -> gui.toggleSearchPanel(true));
//                            gui.addSearchFieldListener(new javax.swing.event.DocumentListener() {
//                                @Override
//                                public void insertUpdate(javax.swing.event.DocumentEvent e) {
//                                    controller.highlightMessages();
//                                }
//
//                                @Override
//                                public void removeUpdate(javax.swing.event.DocumentEvent e) {
//                                    controller.highlightMessages();
//                                }
//
//                                @Override
//                                public void changedUpdate(javax.swing.event.DocumentEvent e) {
//                                    controller.highlightMessages();
//                                }
//                            });
//
//                            // Show GUI
//                            gui.setVisible(true);
//                        } catch (IOException ex) {
//                            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
//                        }
//                    });
//
//                }
//                return null;
//            }
//
//            void open() {
//                throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
//            }
//        
//    
//}
//
//
//








public class SigninController implements ActionListener {
    
    private final UserDAO userDAO = new UserDAO();
    private final Signin userView;
    private  String currentUserEmail;
    private ChatClientDAO chatdao;
    private String email;
    private String password;

    public SigninController(Signin userView) {
        this.userView = userView;
        this.email = userView.getEmailField();
        this.password = userView.getPasswordField();
        this.chatdao = new ChatClientDAO();
    }
    
    public String loginUser(String email, String password) {
        // Validate email format
        boolean isValid = email.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$");
        if (!isValid) {
            return "Enter a valid email";
        }

        // Check credentials in database
        boolean success = userDAO.Logincredentials(email, password);
        this.currentUserEmail = email;

        if (success) {
            String fullname = chatdao.getFirstnLastName(email);

            SwingUtilities.invokeLater(() -> {
                try {
                    ClientGui gui = new ClientGui(fullname);
                    ChatController controller = new ChatController(gui, email);
                    MessageModel msg = new MessageModel();

                    // Setup GUI and listeners (your existing code here)
                    gui.setVisible(true);
                } catch (IOException ex) {
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
            return null;
        } else {
            return "Invalid credentials";
        }
    }
    
    
    
    

    @Override
    public void actionPerformed(ActionEvent e) {
        // You can get email and password here from userView
        String email = userView.getEmailField();
        String password = userView.getPasswordField();

        String result = loginUser(email, password);
        if (result != null) {
            JOptionPane.showMessageDialog(null, result);
        }
    }



    public void open() {
        this.userView.setVisible(true);
    }
}
