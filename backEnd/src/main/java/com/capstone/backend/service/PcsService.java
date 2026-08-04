package com.capstone.backend.service;

import com.capstone.backend.exception.ApiError;
import com.capstone.backend.model.Pcs;
import com.capstone.backend.repository.PcsRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class PcsService {

    private final PcsRepository pcsRepository;

    public PcsService(PcsRepository pcsRepository) {
        this.pcsRepository = pcsRepository;
    }

    public List<Pcs> getPcsByStateCode(String stateCode) {
        if (stateCode == null || stateCode.isBlank()) {
            throw new ApiError(400, "State code is required");
        }
        List<Pcs> pcsList = pcsRepository.findByStateCode(stateCode);
        if (pcsList.isEmpty()) {
            throw new ApiError(404, "PCS not found for the given state code");
        }
        return pcsList;
    }

    public Map<String, Object> getAllPcs(Map<String, String> params) {
        int page = Integer.parseInt(params.getOrDefault("page", "1"));
        int limit = Integer.parseInt(params.getOrDefault("limit", "10"));

        List<Pcs> allPcs;
        if (params.containsKey("state_code")) {
            allPcs = pcsRepository.findByStateCode(params.get("state_code"));
        } else {
            allPcs = pcsRepository.findAll();
        }

        int total = allPcs.size();
        int start = (page - 1) * limit;
        int end = Math.min(start + limit, total);
        List<Pcs> paged = start < total ? allPcs.subList(start, end) : List.of();

        return Map.of(
                "pcs", paged,
                "pagination", Map.of(
                        "page", page,
                        "limit", limit,
                        "total", total,
                        "totalPages", (int) Math.ceil((double) total / limit)
                )
        );
    }

    public Pcs createPc(Pcs pcs) {
        if (pcsRepository.existsByPcCode(pcs.getPcCode())) {
            throw new ApiError(400, "PC with this code already exists");
        }
        return pcsRepository.save(pcs);
    }

    public Pcs updatePc(String id, Pcs pcs) {
        Pcs existing = pcsRepository.findById(id)
                .orElseThrow(() -> new ApiError(404, "PC not found"));
        if (pcs.getPcName() != null) existing.setPcName(pcs.getPcName());
        return pcsRepository.save(existing);
    }

    public void deletePc(String id) {
        Pcs existing = pcsRepository.findById(id)
                .orElseThrow(() -> new ApiError(404, "PC not found"));
        pcsRepository.delete(existing);
    }

    public List<Pcs> getAllPcsList() {
        return pcsRepository.findAll();
    }
}
