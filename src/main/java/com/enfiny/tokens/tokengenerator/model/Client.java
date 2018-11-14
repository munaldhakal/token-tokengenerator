package com.enfiny.tokens.tokengenerator.model;

import com.enfiny.tokens.tokengenerator.enums.Status;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.provider.ClientDetails;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "client")
public class Client implements ClientDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    @JsonIgnore
    private String secret;
    private String email;
    @Enumerated(EnumType.STRING)
    private Status status;
    private Date createdDate;
    private Date modifiedDate;
    @OneToMany(mappedBy = "client")
    private List<App> app;
    @OneToMany(mappedBy = "client")
    private List<User> user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @JsonIgnore
    public String getSecret() {
        return secret;
    }

    @JsonProperty
    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Date modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public List<App> getApp() {
        return app;
    }

    public void setApp(List<App> app) {
        this.app = app;
    }

    public List<User> getUser() {
        return user;
    }

    public void setUser(List<User> user) {
        this.user = user;
    }

    @Override
    public String getClientId() {
        return username;
    }

    @Override
    public Set<String> getResourceIds() {
        Set<String> resourceIds = new HashSet<>();
        app.forEach(app1 -> resourceIds.add(app1.getId().toString()));
        return resourceIds;
    }

    @Override
    public boolean isSecretRequired() {
        return true;
    }

    @Override
    @JsonIgnore
    public String getClientSecret() {
        return secret;
    }

    @Override
    public boolean isScoped() {
        return true;
    }

    @Override
    public Set<String> getScope() {
        Set<String> resourceIds = new HashSet<>();
        resourceIds.add("AUTHENTICATION");
        return resourceIds;
    }

    @Override
    public Set<String> getAuthorizedGrantTypes() {
        Set<String> resourceIds = new HashSet<>();
        resourceIds.add("password");
        resourceIds.add("authorization_code");
        resourceIds.add("refresh_token");
        resourceIds.add("implicit");
        return resourceIds;
    }

    @Override
    public Set<String> getRegisteredRedirectUri() {
        return null;
    }

    @Override
    public Collection<GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        app.forEach(app1 -> app1.getGrantAccess().forEach(grantAccess -> grantAccess.getAuthority().forEach(authority -> grantedAuthorities.add(new SimpleGrantedAuthority(authority.getAuthority())))));
        return grantedAuthorities;
    }

    @Override
    public Integer getAccessTokenValiditySeconds() {
        return 900;
    }

    @Override
    public Integer getRefreshTokenValiditySeconds() {
        return 3600;
    }

    @Override
    public boolean isAutoApprove(String scope) {
        return false;
    }

    @Override
    public Map<String, Object> getAdditionalInformation() {
        return null;
    }
}
