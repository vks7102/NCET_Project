package com.capstone.backend.repository;

import com.capstone.backend.model.PollingBoothOfficer;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;
import java.util.Optional;

public interface PollingBoothOfficerRepository extends MongoRepository<PollingBoothOfficer, String> {
    Optional<PollingBoothOfficer> findByEmail(String email);
    List<PollingBoothOfficer> findByEro(String eroId);
}
