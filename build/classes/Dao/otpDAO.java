package Dao;

import Database.MySqlConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import Model.OTPModel;
import java.sql.Timestamp;



public class otpDAO {
    private final MySqlConnection db = new MySqlConnection();
    

    public boolean registerOtp(OTPModel user) {
        Connection conn = db.openConnection();
        try {
            String sql = "INSERT INTO otps (otp_code, purpose, is_verified, email) VALUES (?, ?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, user.getOtpCode());
            ps.setString(2, user.getPurpose());
            ps.setBoolean(3, user.getisVerified());
            ps.setString(4, user.getemail());

            
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (Exception e) {
            System.out.println("Registration error: " + e);
            return false;
        } finally {
            db.closeConnection(conn);
        }
    }
    
    
    public Integer getOTPCode(int User_entered_otp) {
        Connection conn = db.openConnection();
        try {
            String sql = "SELECT otp_code FROM otps WHERE otp_code=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, User_entered_otp);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt("otp_code"); // return the found OTP code
            } else {
                return null; // OTP not found
            }
        } catch (Exception e) {
            System.out.println(e);
            return null;
        } finally {
            db.closeConnection(conn);
        }
    }
    
    
    
    
    
    //to update verified account
    public boolean updateIsVerified(int otpCode, boolean isVerified) {
    Connection conn = db.openConnection();
    try {
        String sql = "UPDATE otps SET is_verified = ? WHERE otp_code = ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setBoolean(1, isVerified);
        ps.setInt(2, otpCode);

        int rowsAffected = ps.executeUpdate();
        return rowsAffected > 0; // returns true if at least one row was updated
    } catch (Exception e) {
        System.out.println("Update error: " + e);
        return false;
    } finally {
        db.closeConnection(conn);
    }
}
    
    
    public boolean deleteUnverifiedOtps() {
    Connection conn = db.openConnection();
    try {
        String sql = "DELETE FROM otps WHERE is_verified = FALSE";
        PreparedStatement ps = conn.prepareStatement(sql);

        int rowsAffected = ps.executeUpdate();
        return rowsAffected > 0; // returns true if any rows were deleted
    } catch (Exception e) {
        System.out.println("Deletion error: " + e);
        return false;
    } finally {
        db.closeConnection(conn);
    }
}


}