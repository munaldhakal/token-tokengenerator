package com.enfiny.tokens.tokengenerator.model;

import com.enfiny.tokens.tokengenerator.enums.GrantType;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "grant_access")
public class GrantAccess implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private GrantType grantType;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "app_id")
    private App app;
    @OneToMany(mappedBy = "grantAccess")
    private List<Authority> authority;
    @OneToMany(mappedBy = "grantAccess")
    private List<User> user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public GrantType getGrantType() {
        return grantType;
    }

    public void setGrantType(GrantType grantType) {
        this.grantType = grantType;
    }

    public App getApp() {
        return app;
    }

    public void setApp(App app) {
        this.app = app;
    }

    public List<Authority> getAuthority() {
        return authority;
    }

    public void setAuthority(List<Authority> authority) {
        this.authority = authority;
    }

    public List<User> getUser() {
        return user;
    }

    public void setUser(List<User> user) {
        this.user = user;
    }
}
