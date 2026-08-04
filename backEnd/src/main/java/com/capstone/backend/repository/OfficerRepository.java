package com.capstone.backend.repository;

import com.capstone.backend.model.Officer;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;
import java.util.Optional;

public interface OfficerRepository extends MongoRepository<Officer, String> {
    Optional<Officer> findByEmailAndRole(String email, String role);
    List<Officer> findByRole(String role);
    long countByRole(String role);
    long countByRoleAndPostingAddressState(String role, String state);
    long countByRoleAndPostingAddressStateAndPostingAddressDistrict(String role, String state, String district);
    long countByRoleAndPostingAddressStateAndPostingAddressDistrictAndPostingAddressAssembley(
            String role, String state, String district, String assembley);
}
