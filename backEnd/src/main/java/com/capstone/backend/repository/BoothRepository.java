package com.capstone.backend.repository;

import com.capstone.backend.model.Booth;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;
import java.util.Optional;

public interface BoothRepository extends MongoRepository<Booth, String> {
    List<Booth> findByAcCode(String acCode);
    Optional<Booth> findByAcCodeAndBoothNo(String acCode, String boothNo);
    boolean existsByAcCodeAndBoothNo(String acCode, String boothNo);
}
