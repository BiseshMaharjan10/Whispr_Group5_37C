package Model;

public class OTPModel {
    private int otpCode;
    private String purpose;
    private boolean isVerified;
    private String email;


    public OTPModel(int OtpCode, String purpose, Boolean isVerified, String email) {
    this.otpCode = OtpCode;
    this.purpose = purpose;
    this.isVerified = isVerified;
    this.email = email;
    }
    
    // Getters and Setters
    public int getOtpCode() {
        return otpCode;
    }

    public void setOtpCode(int otpCode) {
        this.otpCode = otpCode;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }
    public boolean getisVerified() {
        return isVerified;
    }

    public void setVerified(boolean isVerified) {
        this.isVerified = isVerified;
    }
    
        // Getters and Setters
    public String getemail() {
        return email;
    }

    public String setemail(String email) {
        this.email = email;
        
        
    return null;
    }

}