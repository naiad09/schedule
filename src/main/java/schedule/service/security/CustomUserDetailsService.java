package schedule.service.security;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import schedule.dao.PersonDAO;
import schedule.domain.persons.AuthData;
import schedule.domain.persons.EduDep;
import schedule.domain.persons.Person;


/**
 * Сервис, поставляющий системе безопасности Детали пользователя и роли. Связан
 * с DAO персон.
 */
@Service("customUserDetailsService")
public class CustomUserDetailsService implements UserDetailsService {
	
	@Autowired
	private PersonDAO personDAO;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		Person person = personDAO.find(username);
		if (person == null) throw new UsernameNotFoundException(username);
		
		// return null;
		
		AuthData authData = person.getAuthData();
		return new CustomUserDetails(authData.getLogin(), authData.getPassword(),
				getAuthorities(person), authData.isActive(), authData.getAuthUid());
	}
	
	private Collection<? extends GrantedAuthority> getAuthorities(Person person) {
		
		ArrayList<GrantedAuthority> ar = new ArrayList<GrantedAuthority>();
		ar.add(new SimpleGrantedAuthority("ROLE_" + person.getRole().toUpperCase()));
		
		if (person instanceof EduDep) {
			if (((EduDep) person).isAdmin()) ar.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
		}
		
		return ar;
		
	}
	
	public CustomUserDetailsService() {}
}
