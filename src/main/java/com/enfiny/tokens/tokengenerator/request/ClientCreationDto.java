package com.enfiny.tokens.tokengenerator.request;

import javax.validation.constraints.NotNull;

public class ClientCreationDto {
    @NotNull(message = "Username cannot be null")
    private String username;
    @NotNull(message = "Secret cannot be null")
    private String secret;
    @NotNull( message = "DomainName cannot be null")
    private String domainName;
    private String email;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDomainName() {
        return domainName;
    }

    public void setDomainName(String domainName) {
        this.domainName = domainName;
    }
}
