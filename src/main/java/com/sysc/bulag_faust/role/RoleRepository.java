package com.sysc.bulag_faust.role;

import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository {
    Optional<Role> findByName(String name);
}
