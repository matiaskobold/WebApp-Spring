package com.springboot.webApp.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import net.minidev.json.annotate.JsonIgnore;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Entity

public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String first_name;

    @NotNull
    private String last_name;

    @NotNull
    private String username;

    @NotNull
    private String mail_address;


    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ManyToOne(fetch= FetchType.LAZY, optional = false)
    @JoinColumn(name="clan_id", nullable = false)
    @OnDelete(action= OnDeleteAction.CASCADE)
    @JsonIgnore
    private Clan clan;

    public User() {
    }

    public User(@NotNull String first_name, @NotNull String last_name, @NotNull String username, @NotNull String mail_address) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.username = username;
        this.mail_address = mail_address;
    }
    public Clan getClan() {
        return clan;
    }

    public void setClan(Clan clan) {
        this.clan = clan;
    }
    public Long getId() {
        return id;
    }

    public void setId(Long idUsers) {
        this.id = idUsers;
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
        return getId().equals(user.getId()) &&
                getFirst_name().equals(user.getFirst_name()) &&
                getLast_name().equals(user.getLast_name()) &&
                getUsername().equals(user.getUsername()) &&
                getMail_address().equals(user.getMail_address());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, first_name, last_name, username, mail_address);
    }

    @Override
    public String toString() {
        return "User{" +
                "idUsers=" + id +
                ", first_name='" + first_name + '\'' +
                ", last_name='" + last_name + '\'' +
                ", username='" + username + '\'' +
                ", mail_address='" + mail_address + '\'' +
                '}';
    }
}
