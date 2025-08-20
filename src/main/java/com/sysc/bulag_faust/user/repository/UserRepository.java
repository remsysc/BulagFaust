package com.sysc.bulag_faust.user.repository;

import com.sysc.bulag_faust.user.entities.User;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {}
