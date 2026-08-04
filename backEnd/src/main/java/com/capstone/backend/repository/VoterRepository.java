package com.capstone.backend.repository;

import com.capstone.backend.model.Voter;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import java.util.List;
import java.util.Optional;

public interface VoterRepository extends MongoRepository<Voter, String> {
    Optional<Voter> findByAadharNumber(String aadharNumber);
    Optional<Voter> findByUniqueVoterId(String uniqueVoterId);
    long countByState(String state);
    long countByStateAndDistrictAndAssembleyAndBoothNumber(String state, String district, String assembley, String boothNumber);
    long countByIsDeletedFalse();
    long countByIsDeletedTrue();

    List<Voter> findByIsDeletedFalse();

    @Query("{ 'isDeleted': false }")
    List<Voter> findAllActive();

    @Query("{ 'isDeleted': true }")
    List<Voter> findAllDeleted();

    List<Voter> findByMobilityBoothIdAndIsDeletedFalse(String mobilityBoothId);
    List<Voter> findByBoothNumberAndIsDeletedFalse(String boothNumber);

    @Query("{ 'mobilityBoothId': { '$ne': null }, 'isVerifiedMobilityBoothId': false, 'isDeleted': false }")
    List<Voter> findMobilityBoothRequests();

    List<Voter> findByStateAndIsDeletedFalse(String state);
}
