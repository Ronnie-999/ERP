package de.buw.se;

public class Company {
    private String login;
    private String password;
    private String companyName;
    private String phoneNumber;

    public Company(String login, String password, String companyName, String phoneNumber) {
        this.login = login;
        this.password = password;
        this.companyName = companyName;
        this.phoneNumber = phoneNumber;
    }

    public String getLogin() {
        return login;
    }

    // Setter for login
    public void setLogin(String login) {
        this.login = login;
    }

    // Getter for password
    public String getPassword() {
        return password;
    }

    // Setter for password
    public void setPassword(String password) {
        this.password = password;
    }

    // Getter for companyName
    public String getCompanyName() {
        return companyName;
    }

    // Setter for companyName
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    // Getter for phoneNumber
    public String getPhoneNumber() {
        return phoneNumber;
    }

    // Setter for phoneNumber
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
