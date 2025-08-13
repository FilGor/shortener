package com.shortener.models;


import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
public class ShortenerUser implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    String name;
    @Column(unique = true)
    String email;

    String password;

    @Enumerated(EnumType.STRING)
    ShortenerUserRole role;

    @OneToMany(
            mappedBy = "owner",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    private List<ShortUrl> urls = new ArrayList<>();


    public ShortenerUser(String name, String email, String password, ShortenerUserRole role) {
        this.name = name;
        this.email = email;
        this.password = password;

        this.role = role;
    }

    public ShortenerUser() {

    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.getAuthority()));
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ShortenerUserRole getRole() {
        return role;
    }

    public void setRole(ShortenerUserRole role) {
        this.role = role;
    }

    public List<ShortUrl> getUrls() {
        return urls;
    }

    public void setUrls(List<ShortUrl> urls) {
        this.urls = urls;
    }
}