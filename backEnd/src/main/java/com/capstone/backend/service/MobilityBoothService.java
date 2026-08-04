package com.capstone.backend.service;

import com.capstone.backend.exception.ApiError;
import com.capstone.backend.model.MobilityBooth;
import com.capstone.backend.repository.MobilityBoothRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class MobilityBoothService {

    private final MobilityBoothRepository mobilityBoothRepository;

    public MobilityBoothService(MobilityBoothRepository mobilityBoothRepository) {
        this.mobilityBoothRepository = mobilityBoothRepository;
    }

    public List<MobilityBooth> getNearestMobilityBooths(double latitude, double longitude) {
        if (Double.isNaN(latitude) || Double.isNaN(longitude)) {
            throw new ApiError(400, "Valid latitude and longitude are required");
        }
        List<MobilityBooth> booths = mobilityBoothRepository.findNearby(latitude, longitude, 50000);
        return booths;
    }

    public Map<String, Object> getAllMobilityBooths(Map<String, String> params) {
        int page = Integer.parseInt(params.getOrDefault("page", "1"));
        int limit = Integer.parseInt(params.getOrDefault("limit", "10"));
        PageRequest pageable = PageRequest.of(page - 1, limit);
        Page<MobilityBooth> boothPage = mobilityBoothRepository.findAll(pageable);

        return Map.of(
                "booths", boothPage.getContent(),
                "pagination", Map.of(
                        "page", page,
                        "limit", limit,
                        "total", boothPage.getTotalElements(),
                        "totalPages", boothPage.getTotalPages()
                )
        );
    }

    public MobilityBooth createMobilityBooth(MobilityBooth booth) {
        if (mobilityBoothRepository.findByBoothId(booth.getBoothId()).isPresent()) {
            throw new ApiError(400, "Mobility booth with this ID already exists");
        }
        booth.setCurrentQueue(0);
        booth.setIsActive(true);
        return mobilityBoothRepository.save(booth);
    }

    public MobilityBooth updateMobilityBooth(String id, MobilityBooth booth) {
        MobilityBooth existing = mobilityBoothRepository.findById(id)
                .orElseThrow(() -> new ApiError(404, "Mobility booth not found"));
        if (booth.getBoothName() != null) existing.setBoothName(booth.getBoothName());
        if (booth.getAreaName() != null) existing.setAreaName(booth.getAreaName());
        if (booth.getAddress() != null) existing.setAddress(booth.getAddress());
        if (booth.getContactPerson() != null) existing.setContactPerson(booth.getContactPerson());
        if (booth.getContactPhone() != null) existing.setContactPhone(booth.getContactPhone());
        if (booth.getLocation() != null) existing.setLocation(booth.getLocation());
        if (booth.getTotalCapacity() != null) existing.setTotalCapacity(booth.getTotalCapacity());
        if (booth.getCurrentQueue() != null) existing.setCurrentQueue(booth.getCurrentQueue());
        if (booth.getIsActive() != null) existing.setIsActive(booth.getIsActive());
        return mobilityBoothRepository.save(existing);
    }

    public void deleteMobilityBooth(String id) {
        MobilityBooth existing = mobilityBoothRepository.findById(id)
                .orElseThrow(() -> new ApiError(404, "Mobility booth not found"));
        mobilityBoothRepository.delete(existing);
    }
}
