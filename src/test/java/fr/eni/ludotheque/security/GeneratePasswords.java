package fr.eni.ludotheque.security;

import fr.eni.ludotheque.dal.UtilisateurRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
public class GeneratePasswords {
    @Autowired
    private PasswordEncoder passwordEncoder;


    @Test
    public void generatePasswords() {
        System.out.println(passwordEncoder.encode("azerty"));
    }



}
