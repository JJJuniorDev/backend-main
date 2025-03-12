package Model;

import org.springframework.security.core.userdetails.UserDetails;

public interface ExtendedUserDetails extends UserDetails{
	String getRuolo();

	String getEmail();
	
	String getId();
}
