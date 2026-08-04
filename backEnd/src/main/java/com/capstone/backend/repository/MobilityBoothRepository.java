package com.capstone.backend.repository;

import com.capstone.backend.model.MobilityBooth;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import java.util.List;
import java.util.Optional;

public interface MobilityBoothRepository extends MongoRepository<MobilityBooth, String> {
    Optional<MobilityBooth> findByBoothId(String boothId);

    @Query("{ 'location': { $near: { $geometry: { type: 'Point', coordinates: [?1, ?0] }, $maxDistance: ?2 } } }")
    List<MobilityBooth> findNearby(double latitude, double longitude, long maxDistance);
}
