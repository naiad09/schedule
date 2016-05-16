package schedule.service;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import schedule.dao.PersonDao;
import schedule.domain.persons.HttpAuth;
import schedule.domain.persons.Person;


@Service
public class CustomUserDetailsService implements UserDetailsService {
	
	@Autowired
	private PersonDao personDao;
	
	@Override
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {
		
		Person person = personDao.read(username);
		if (person == null) throw new UsernameNotFoundException(username);
		
		// return null;
		
		HttpAuth httpAuth = person.getHttpAuth();
		return new CustomUserDetails(httpAuth.getLogin(),
				httpAuth.getPassword(), getAuthorities(person),
				httpAuth.isActive(), httpAuth.getAuthUid());
	}
	
	private Collection<? extends GrantedAuthority> getAuthorities(
			Person person) {
		
		ArrayList<GrantedAuthority> ar = new ArrayList<GrantedAuthority>();
		ar.add(new SimpleGrantedAuthority(
				"ROLE_" + person.getRole().toUpperCase()));
		return ar;
		
	}
	
}
