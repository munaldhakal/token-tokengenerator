package com.enfiny.tokens.tokengenerator.enums;

public enum GrantType {
    SUPERADMIN,
    ADMIN,
    USER,
    CLIENT;
    private String grantType;

    public String getGrantType() {
        return grantType;
    }

    public void setGrantType(String grantType) {
        this.grantType = grantType;
    }
}
