package com.capstone.backend.repository;

import com.capstone.backend.model.Pcs;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface PcsRepository extends MongoRepository<Pcs, String> {
    List<Pcs> findByStateCode(String stateCode);
    boolean existsByPcCode(String pcCode);
}
