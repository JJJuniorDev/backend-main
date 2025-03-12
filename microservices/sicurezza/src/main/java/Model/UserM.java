package Model;


import java.util.Collection;
import java.util.Collections;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.security.core.GrantedAuthority;

@Document(collection = "USER")
public class UserM implements ExtendedUserDetails{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
    private String id;

    @Field("USERNAME")
    private String username;

    @Field("PASSWORD")
    private String password;

    @Field("EMAIL")
    private String email;

    @Field("ruolo")
    private String ruolo;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList(); // o ritorna le autorità appropriate
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
	public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
	public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

	public void setRuolo(String ruolo) {
		this.ruolo = ruolo;
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

	    @Override
	    public String getRuolo() {
	        return this.ruolo;
	    }
}