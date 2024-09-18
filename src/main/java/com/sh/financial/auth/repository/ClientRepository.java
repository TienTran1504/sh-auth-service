package com.sh.financial.auth.repository;

import com.sh.financial.auth.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client, String> {
    Client findByClientId(String clientId);
}
