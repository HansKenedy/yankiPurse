package com.proyecto.yankiPurse.repository;

import com.proyecto.yankiPurse.domain.Purse;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PurseRepository extends MongoRepository<Purse,String> {
}
