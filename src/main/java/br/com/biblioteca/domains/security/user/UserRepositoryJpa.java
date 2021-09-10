package br.com.biblioteca.domains.security.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

interface UserRepositoryJpa extends JpaRepository<User, UUID> {

}
