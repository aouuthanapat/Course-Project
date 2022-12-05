package com.kakura.museum.repo;

import com.kakura.museum.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<User, Long> {
}
