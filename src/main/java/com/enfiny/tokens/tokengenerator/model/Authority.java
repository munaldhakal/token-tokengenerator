package com.enfiny.tokens.tokengenerator.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "authority")
public class Authority implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String authority;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "grantAccess_id")
    private GrantAccess grantAccess;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    public GrantAccess getGrantAccess() {
        return grantAccess;
    }

    public void setGrantAccess(GrantAccess grantAccess) {
        this.grantAccess = grantAccess;
    }

}
