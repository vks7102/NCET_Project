package com.capstone.backend.repository;

import com.capstone.backend.model.Acs;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface AcsRepository extends MongoRepository<Acs, String> {
    List<Acs> findByPcCode(String pcCode);
    List<Acs> findByStateCode(String stateCode);
    boolean existsByAssemblyCode(String assemblyCode);
    List<Acs> findByStateCodeAndPcCode(String stateCode, String pcCode);
}
