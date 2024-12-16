package org.jonatancarbonellmartinez.model.entities;

public class Authority implements Entity {
    private int authoritySk;
    private String authorityName;
    private String authorityShort;

    @Override
    public int getSk() {
        return authoritySk;
    }

    @Override
    public String toString() {
        return String.valueOf(authorityName);
    }

    // Getters and Setters
    public void setAuthoritySk(int authoritySk) {
        this.authoritySk = authoritySk;
    }

    public String getAuthorityName() {
        return authorityName;
    }

    public void setAuthorityName(String authorityName) {
        this.authorityName = authorityName;
    }

    public String getAuthorityShort() {
        return authorityShort;
    }

    public void setAuthorityShort(String authorityShort) {
        this.authorityShort = authorityShort;
    }
}


