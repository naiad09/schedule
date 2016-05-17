package schedule.service;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;


public class CustomUserDetails extends User {
	private static final long serialVersionUID = 1L;
	
	private final Integer uid;
	
	public CustomUserDetails(String username, String password,
			Collection<? extends GrantedAuthority> authorities, boolean active,
			Integer uid) {
		super(username, password, active, active, active, active, authorities);
		this.uid = uid;
	}
	
	public Integer getUid() {
		return uid;
	}
	
}