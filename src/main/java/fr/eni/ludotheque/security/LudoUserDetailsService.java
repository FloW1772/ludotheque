package fr.eni.ludotheque.security;

import fr.eni.ludotheque.bo.Utilisateur;
import fr.eni.ludotheque.dal.UtilisateurRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class LudoUserDetailsService implements UserDetailsService {
    private UtilisateurRepository utilisateurRepository;

    public LudoUserDetailsService(UtilisateurRepository utilisateurRepository) {
        this.utilisateurRepository = utilisateurRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Utilisateur user = utilisateurRepository.findByLogin(username);
        if(user ==null) {
            throw new UsernameNotFoundException(username);
        }
        UserDetails utilisateur = User
                .withUsername(username)
                .password(user.getPassword())
                .roles(user.getRole()).build();

        return utilisateur;
    }
}
