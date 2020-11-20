package com.scrumoftheearth.springbootapi.repository;

import com.scrumoftheearth.springbootapi.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    @Override
    Optional<User> findById(Long id);

    @Override
    boolean existsById(Long id);

    Optional<User> findByUserName(String userName);

    boolean existsByUserName(String userName);

}
