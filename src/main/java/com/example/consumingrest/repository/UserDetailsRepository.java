package com.example.consumingrest.repository;

import com.example.consumingrest.core.UserDetails;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserDetailsRepository extends CrudRepository<UserDetails, Long> {

    List<UserDetails> findByLastName(String lastName);

    UserDetails findById(long id);
}
