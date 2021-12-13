package model;

import java.util.Date;

public class Person {
    private String name;
    private int personId;
    private String email;
    private Date dob;

    public Person(String name, int personId) {
        this.name = name;
        this.personId = personId;
    }

    public Person(String name, int personId, String email, Date dob) {
        this.name = name;
        this.personId = personId;
        this.email = email;
        this.dob = dob;
    }

    public Person() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPersonId() {
        return personId;
    }

    public void setPersonId(int personId) {
        this.personId = personId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", personId=" + personId +
                ", email='" + email + '\'' +
                ", dob=" + dob +
                '}';
    }
}
