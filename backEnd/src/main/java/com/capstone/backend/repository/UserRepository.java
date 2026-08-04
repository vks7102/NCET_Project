package com.capstone.backend.repository;

import com.capstone.backend.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {
    Optional<User> findByAadharNumber(String aadharNumber);
    Optional<User> findByPhoneNumber(String phoneNumber);
    Optional<User> findByEmail(String email);

    @Query("{ '$or': [ { 'aadharNumber': ?0 }, { 'phoneNumber': ?1 }, { 'email': ?2 } ] }")
    Optional<User> findByAadharOrPhoneOrEmail(String aadharNumber, String phoneNumber, String email);

    @Query("{ 'verification': { '$elemMatch': { 'level': 'BLO', 'status': 'pending' } } }")
    List<User> findPendingBLO();

    @Query("{ 'verification': { '$all': [ { '$elemMatch': { 'level': 'BLO', 'status': 'verified' } }, { '$elemMatch': { 'level': 'ERO', 'status': 'pending' } } ] } }")
    List<User> findPendingERO();

    @Query("{ '$and': [ { 'verification': { '$all': [ { '$elemMatch': { 'level': 'BLO', 'status': 'verified' } }, { '$elemMatch': { 'level': 'ERO', 'status': 'verified' } }, { '$elemMatch': { 'level': 'DEO', 'status': 'pending' } } ] } }, { 'verification': { '$not': { '$elemMatch': { 'level': 'DEO', 'status': 'rejected' } } } } ] }")
    List<User> findPendingDEO();

    @Query("{ 'verification': { '$elemMatch': { 'level': 'DEO', 'status': 'verified' } } }")
    List<User> findDeoVerified();
}
