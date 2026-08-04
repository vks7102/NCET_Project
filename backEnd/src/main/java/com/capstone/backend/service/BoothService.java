package com.capstone.backend.service;

import com.capstone.backend.exception.ApiError;
import com.capstone.backend.model.Acs;
import com.capstone.backend.model.Booth;
import com.capstone.backend.repository.AcsRepository;
import com.capstone.backend.repository.BoothRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class BoothService {

    private final BoothRepository boothRepository;
    private final AcsRepository acsRepository;

    public BoothService(BoothRepository boothRepository, AcsRepository acsRepository) {
        this.boothRepository = boothRepository;
        this.acsRepository = acsRepository;
    }

    public List<Booth> getBoothsByAcCode(String acCode) {
        if (acCode == null || acCode.isBlank()) {
            throw new ApiError(400, "AC code is required");
        }
        List<Booth> booths = boothRepository.findByAcCode(acCode.toUpperCase());
        if (booths.isEmpty()) {
            throw new ApiError(404, "Booths not found for the given AC code");
        }
        return booths;
    }

    @SuppressWarnings("unchecked")
    public Map<String, Object> getAllBooths(int page, int limit, Map<String, String> filter) {
        Pageable pageable = PageRequest.of(page - 1, limit);

        List<String> acCodes = null;
        if (filter.containsKey("state_code") || filter.containsKey("pc_code")) {
            List<Acs> acsList;
            if (filter.containsKey("pc_code")) {
                acsList = acsRepository.findByPcCode(filter.get("pc_code"));
            } else {
                acsList = acsRepository.findByStateCode(filter.get("state_code"));
            }
            List<String> initialCodes = acsList.stream().map(Acs::getAssemblyCode).collect(Collectors.toList());

            if (filter.containsKey("ac_code")) {
                String targetAc = filter.get("ac_code");
                acCodes = initialCodes.stream()
                        .filter(code -> code.equals(targetAc))
                        .collect(Collectors.toList());
            } else {
                acCodes = initialCodes;
            }

            if (acCodes.isEmpty()) {
                return Map.of(
                        "booths", List.of(),
                        "pagination", Map.of("page", page, "limit", limit, "total", 0, "totalPages", 0)
                );
            }
        }

        final List<String> finalAcCodes = acCodes;
        Page<Booth> boothPage;
        if (finalAcCodes != null && !finalAcCodes.isEmpty()) {
            List<Booth> allBooths = boothRepository.findAll();
            List<Booth> filtered = allBooths.stream()
                    .filter(b -> finalAcCodes.contains(b.getAcCode()))
                    .collect(Collectors.toList());
            int total = filtered.size();
            int start = (page - 1) * limit;
            int end = Math.min(start + limit, total);
            List<Booth> pageContent = start < total ? filtered.subList(start, end) : List.of();

            return Map.of(
                    "booths", pageContent,
                    "pagination", Map.of("page", page, "limit", limit, "total", total, "totalPages", (int) Math.ceil((double) total / limit))
            );
        }

        boothPage = boothRepository.findAll(pageable);
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

    public Booth createBooth(Booth booth) {
        if (booth.getAcCode() == null || booth.getBoothNo() == null || booth.getBoothName() == null) {
            throw new ApiError(400, "ac_code, booth_no, and booth_name are required");
        }
        if (boothRepository.existsByAcCodeAndBoothNo(booth.getAcCode(), booth.getBoothNo())) {
            throw new ApiError(409, "Booth with this number already exists in this assembly constituency");
        }
        return boothRepository.save(booth);
    }

    public Booth updateBooth(String id, Booth booth) {
        if (id == null) throw new ApiError(400, "Booth ID is required");
        Booth existing = boothRepository.findById(id)
                .orElseThrow(() -> new ApiError(404, "Booth not found"));

        if (booth.getBoothNo() != null) existing.setBoothNo(booth.getBoothNo());
        if (booth.getBoothName() != null) existing.setBoothName(booth.getBoothName());

        return boothRepository.save(existing);
    }

    public Booth deleteBooth(String id) {
        if (id == null) throw new ApiError(400, "Booth ID is required");
        Booth existing = boothRepository.findById(id)
                .orElseThrow(() -> new ApiError(404, "Booth not found"));
        boothRepository.delete(existing);
        return existing;
    }
}
