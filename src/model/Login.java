package model;

import com.mysql.cj.log.Log;

public class Login {
    private String password;
    private int userId;
    private String personType;

    public Login(){
        //does nothing
    }

    public Login(int userId,String password, String personType) {
        this.password = password;
        this.userId = userId;
        this.personType = personType;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getPersonType() {
        return personType;
    }

    public void setPersonType(String personType) {
        this.personType = personType;
    }
}
