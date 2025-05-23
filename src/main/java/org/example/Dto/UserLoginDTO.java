package org.example.Dto;

public class UserLoginDTO {
    private String phone;
    private String password;

    public UserLoginDTO(String phone, String password) {
        this.phone = phone;
        this.password = password;
    }

    public UserLoginDTO() {
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
