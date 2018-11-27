package com.enfiny.tokens.tokengenerator.model;

import com.enfiny.tokens.tokengenerator.enums.Status;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "app")
public class App implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String appName;
    private String domainName;
    @Enumerated(EnumType.STRING)
    private Status status;
    @ManyToOne(fetch =FetchType.LAZY)
  //  @LazyCollection(LazyCollectionOption.TRUE)
    @JoinColumn(name = "client_id")
    private Client client;
    @OneToMany(mappedBy = "app")
    //@Fetch(value = FetchMode.SUBSELECT)
    //@LazyCollection(LazyCollectionOption.FALSE)
    private List<GrantAccess> grantAccess;
    @OneToMany(mappedBy = "app")
  //  @LazyCollection(LazyCollectionOption.TRUE)
    private List<User> user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public List<GrantAccess> getGrantAccess() {
        return grantAccess;
    }

    public void setGrantAccess(List<GrantAccess> grantAccess) {
        this.grantAccess = grantAccess;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public List<User> getUser() {
        return user;
    }

    public void setUser(List<User> user) {
        this.user = user;
    }

    public String getDomainName() {
        return domainName;
    }

    public void setDomainName(String domainName) {
        this.domainName = domainName;
    }
}