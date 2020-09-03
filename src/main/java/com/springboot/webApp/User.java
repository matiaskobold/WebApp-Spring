package com.springboot.webApp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class User {
    private @Id @GeneratedValue Long idUsers;
    private String first_name;
    private String last_name;
    private String username;
    private String mail_address;

    public User() {
    }

    public User(String first_name, String last_name, String username, String mail_address) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.username = username;
        this.mail_address = mail_address;
    }


    public Long getIdUsers() {
        return idUsers;
    }

    public void setIdUsers(Long idUsers) {
        this.idUsers = idUsers;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMail_address() {
        return mail_address;
    }

    public void setMail_address(String mail_address) {
        this.mail_address = mail_address;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(idUsers, user.idUsers) &&
                Objects.equals(first_name, user.first_name) &&
                Objects.equals(last_name, user.last_name) &&
                Objects.equals(username, user.username) &&
                Objects.equals(mail_address, user.mail_address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idUsers, first_name, last_name, username, mail_address);
    }

    @Override
    public String toString() {
        return "User{" +
                "idUsers=" + idUsers +
                ", first_name='" + first_name + '\'' +
                ", last_name='" + last_name + '\'' +
                ", username='" + username + '\'' +
                ", mail_address='" + mail_address + '\'' +
                '}';
    }
}
