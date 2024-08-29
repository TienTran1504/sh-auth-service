package com.sh.financial.auth;

import com.sh.financial.auth.entity.Role;
import com.sh.financial.auth.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application implements CommandLineRunner {
  public static void main(String[] args) {
    SpringApplication
    .run(Application.class, args)
    .start();
  }

  @Autowired
  private RoleRepository roleRepository;

  @Override
  public void run(String... args) throws Exception {
    // check if exist ROLE_ADMIN and ROLE_USER in database
    if (roleRepository.findByName("ROLE_ADMIN") == null ) {
      Role adminRole = new Role();
      adminRole.setName("ROLE_ADMIN");
      roleRepository.save(adminRole);
    }
    if(roleRepository.findByName("ROLE_USER") == null) {
      Role userRole = new Role();
      userRole.setName("ROLE_USER");
      roleRepository.save(userRole);
    }

  }
}
