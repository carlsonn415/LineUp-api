package com.example.lineup_api.repositories;

import com.example.lineup_api.entities.Profile;
import org.springframework.data.repository.CrudRepository;

public interface ProfileRepository extends CrudRepository<Profile, Long> {
}