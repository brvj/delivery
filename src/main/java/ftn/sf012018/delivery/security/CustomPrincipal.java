package ftn.sf012018.delivery.security;

import org.springframework.security.core.CredentialsContainer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.SpringSecurityCoreVersion;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.Assert;

import java.io.Serializable;
import java.util.*;

public class CustomPrincipal implements UserDetails, CredentialsContainer {
    private final String id;

    private final String password;

    private final String username;

    private final String firstname;

    private final String lastname;

    private final String ownerId;

    private final Set<GrantedAuthority> authorities;

    private final boolean accountNonExpired;

    private final boolean accountNonLocked;

    private final boolean credentialsNonExpired;

    private final boolean enabled;

    public CustomPrincipal(String id, String username, String password, String firstname, String lastname, String ownerId,
                           boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked,
                           Collection<? extends GrantedAuthority> authorities) {
        Assert.isTrue(!id.equals("") && username != null && !"".equals(username) && password != null,
                "Cannot pass null or empty values to constructor");
        this.id = id;
        this.username = username;
        this.firstname = firstname;
        this.lastname = lastname;
        this.password = password;
        this.ownerId = ownerId;
        this.enabled = enabled;
        this.accountNonExpired = accountNonExpired;
        this.credentialsNonExpired = credentialsNonExpired;
        this.accountNonLocked = accountNonLocked;
        this.authorities = Collections.unmodifiableSet(sortAuthorities(authorities));
    }

    public CustomPrincipal(String id, String username, String password, String firstname, String lastname, String ownerId,
                           Collection<? extends GrantedAuthority> authorities){
        this(id, username, password, firstname, lastname, ownerId, true, true,
                true, true, authorities);
    }

    @Override
    public void eraseCredentials() {

    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() { return this.authorities; }

    private final SimpleGrantedAuthority adminAuthority = new SimpleGrantedAuthority("ROLE_ADMIN");
    private final SimpleGrantedAuthority storeAuthority = new SimpleGrantedAuthority("ROLE_STORE");
    private final SimpleGrantedAuthority customerAuthority = new SimpleGrantedAuthority("ROLE_CUSTOMER");

    public boolean isAdmin() {
        if (this.authorities.contains(adminAuthority)) return true;
        return false;
    }

    public boolean isStore() {
        if (this.authorities.contains(storeAuthority)) return true;
        return false;
    }

    public boolean isCustomer() {
        if (this.authorities.contains(customerAuthority)) return true;
        return false;
    }

    public String getAdminId() {
        if (!this.isAdmin()) throw new RuntimeException("Principal is not admin hence you can not access adminId");
        return this.ownerId;
    }

    public String getStoreId() {
        if (!this.isStore()) throw new RuntimeException("Principal is not store hence you can not access storeId");
        return this.ownerId;
    }

    public String getCustomerId() {
        if (!this.isCustomer()) throw new RuntimeException("Principal is not customer hence you can not access customerId");
        return this.ownerId;
    }

    @Override
    public String getPassword() { return this.password; }

    @Override
    public String getUsername() { return this.username; }

    public String getId() { return this.id; }

    public String getFirstname() { return this.firstname; }

    public String getLastname() { return this.lastname; }

    public String getOwnerId() { return this.ownerId; }

    @Override
    public boolean isAccountNonExpired() { return this.accountNonExpired; }

    @Override
    public boolean isAccountNonLocked() { return this.accountNonLocked; }

    @Override
    public boolean isCredentialsNonExpired() { return this.credentialsNonExpired; }

    @Override
    public boolean isEnabled() { return this.enabled; }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (GrantedAuthority auth : this.authorities) sb.append(String.format("%s, ", auth.getAuthority()));
        sb.delete(sb.length()-2, sb.length());
        return String.format("id: %d\nusername: %d\nauthorities: %s", this.id, this.username, sb.toString());
    }

    private static SortedSet<GrantedAuthority> sortAuthorities(Collection<? extends GrantedAuthority> authorities) {
        Assert.notNull(authorities, "Cannot pass a null GrantedAuthority collection");
        // Ensure array iteration order is predictable (as per
        // UserDetails.getAuthorities() contract and SEC-717)
        SortedSet<GrantedAuthority> sortedAuthorities = new TreeSet<>(new AuthorityComparator());
        for (GrantedAuthority grantedAuthority : authorities) {
            Assert.notNull(grantedAuthority, "GrantedAuthority list cannot contain any null elements");
            sortedAuthorities.add(grantedAuthority);
        }
        return sortedAuthorities;
    }

    private static class AuthorityComparator implements Comparator<GrantedAuthority>, Serializable {

        private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;

        @Override
        public int compare(GrantedAuthority g1, GrantedAuthority g2) {
            // Neither should ever be null as each entry is checked before adding it to
            // the set. If the authority is null, it is a custom authority and should
            // precede others.
            if (g2.getAuthority() == null) {
                return -1;
            }
            if (g1.getAuthority() == null) {
                return 1;
            }
            return g1.getAuthority().compareTo(g2.getAuthority());
        }

    }
}
