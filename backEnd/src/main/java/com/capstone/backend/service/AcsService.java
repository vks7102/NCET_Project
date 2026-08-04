package com.capstone.backend.service;

import com.capstone.backend.exception.ApiError;
import com.capstone.backend.model.Acs;
import com.capstone.backend.repository.AcsRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class AcsService {

    private final AcsRepository acsRepository;

    public AcsService(AcsRepository acsRepository) {
        this.acsRepository = acsRepository;
    }

    public List<Acs> getAcsByPcCode(String pcCode) {
        if (pcCode == null || pcCode.isBlank()) {
            throw new ApiError(400, "PC code is required");
        }
        List<Acs> acsList = acsRepository.findByPcCode(pcCode);
        if (acsList.isEmpty()) {
            throw new ApiError(404, "ACS not found for the given PC code");
        }
        return acsList;
    }

    public Map<String, Object> getAllAcs(Map<String, String> params) {
        int page = Integer.parseInt(params.getOrDefault("page", "1"));
        int limit = Integer.parseInt(params.getOrDefault("limit", "10"));
        Pageable pageable = PageRequest.of(page - 1, limit);
        Page<Acs> acsPage = acsRepository.findAll(pageable);
        return Map.of(
                "acs", acsPage.getContent(),
                "pagination", Map.of(
                        "page", page,
                        "limit", limit,
                        "total", acsPage.getTotalElements(),
                        "totalPages", acsPage.getTotalPages()
                )
        );
    }

    public Acs createAc(Acs acs) {
        if (acsRepository.existsByAssemblyCode(acs.getAssemblyCode())) {
            throw new ApiError(400, "AC with this code already exists");
        }
        return acsRepository.save(acs);
    }

    public Acs updateAc(String id, Acs acs) {
        Acs existing = acsRepository.findById(id)
                .orElseThrow(() -> new ApiError(404, "AC not found"));
        if (acs.getAssemblyName() != null) existing.setAssemblyName(acs.getAssemblyName());
        return acsRepository.save(existing);
    }

    public void deleteAc(String id) {
        Acs existing = acsRepository.findById(id)
                .orElseThrow(() -> new ApiError(404, "AC not found"));
        acsRepository.delete(existing);
    }

    public List<Acs> getAllAcsList() {
        return acsRepository.findAll();
    }
}
