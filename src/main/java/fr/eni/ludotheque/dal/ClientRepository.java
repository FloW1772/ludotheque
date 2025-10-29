package fr.eni.ludotheque.dal;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.eni.ludotheque.bo.Client;

public interface ClientRepository extends JpaRepository<Client, Integer>{

}
