package schedule.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import schedule.dao.PersonDao;
import schedule.domain.persons.HttpAuth;


@Service
public class CustomUserDetailsService implements UserDetailsService {
	
	@Autowired
	private PersonDao personDao;
	
	@Override
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {
		
		HttpAuth httpAuth = personDao.find(username);
		if (httpAuth == null) throw new UsernameNotFoundException(username);
		
		return httpAuth;
	}
	
}
