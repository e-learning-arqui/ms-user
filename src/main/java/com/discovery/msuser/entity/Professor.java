package com.discovery.msuser.entity;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "PROFESSOR")
public class Professor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long professorId;

    @Column(name = "USERNAME", nullable = false)
    private String username;

    @Column(name = "EMAIL", nullable = false)
    private String email;

    @Column(name = "STATUS", nullable = false)
    private Boolean status;

    @Column(name = "KEYCLOAK_ID", nullable = false)
    private String keycloakId;

    @Column(name = "TX_USER", nullable = false)
    private String txUser;

    @Column(name = "TX_HOST", nullable = false)
    private String txHost;

    @Column(name = "TX_DATE", nullable = false)
    private Date txDate;

    public Professor() {
    }

    public Professor(long professorId, String username, String email, Boolean status, String keycloakId, String txUser, String txHost, Date txDate) {
        this.professorId = professorId;
        this.username = username;
        this.email = email;
        this.status = status;
        this.keycloakId = keycloakId;
        this.txUser = txUser;
        this.txHost = txHost;
        this.txDate = txDate;
    }

    public long getProfessorId() {
        return professorId;
    }

    public void setProfessorId(long professorId) {
        this.professorId = professorId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getKeycloakId() {
        return keycloakId;
    }

    public void setKeycloakId(String keycloakId) {
        this.keycloakId = keycloakId;
    }

    public String getTxUser() {
        return txUser;
    }

    public void setTxUser(String txUser) {
        this.txUser = txUser;
    }

    public String getTxHost() {
        return txHost;
    }

    public void setTxHost(String txHost) {
        this.txHost = txHost;
    }

    public Date getTxDate() {
        return txDate;
    }

    public void setTxDate(Date txDate) {
        this.txDate = txDate;
    }
}
