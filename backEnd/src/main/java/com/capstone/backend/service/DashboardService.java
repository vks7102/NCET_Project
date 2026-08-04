package com.capstone.backend.service;

import com.capstone.backend.repository.*;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Service
public class DashboardService {

    private final OfficerRepository officerRepository;
    private final BoothRepository boothRepository;
    private final MobilityBoothRepository mobilityBoothRepository;
    private final AcsRepository acsRepository;
    private final PcsRepository pcsRepository;
    private final StateRepository stateRepository;
    private final VoterRepository voterRepository;

    public DashboardService(OfficerRepository officerRepository, BoothRepository boothRepository,
                            MobilityBoothRepository mobilityBoothRepository, AcsRepository acsRepository,
                            PcsRepository pcsRepository, StateRepository stateRepository,
                            VoterRepository voterRepository) {
        this.officerRepository = officerRepository;
        this.boothRepository = boothRepository;
        this.mobilityBoothRepository = mobilityBoothRepository;
        this.acsRepository = acsRepository;
        this.pcsRepository = pcsRepository;
        this.stateRepository = stateRepository;
        this.voterRepository = voterRepository;
    }

    public Map<String, Object> getEciStats() {
        return Map.of(
                "voters", voterRepository.countByIsDeletedFalse(),
                "ceos", officerRepository.countByRole("CEO"),
                "deos", officerRepository.countByRole("DEO"),
                "blos", officerRepository.countByRole("BLO"),
                "acs", acsRepository.count(),
                "pcs", pcsRepository.count(),
                "states", stateRepository.count(),
                "booths", boothRepository.count(),
                "mobilityBooths", mobilityBoothRepository.count()
        );
    }

    public Map<String, Object> getCeoStats(String state) {
        return Map.of(
                "voters", voterRepository.countByState(state),
                "deos", officerRepository.countByRoleAndPostingAddressState("DEO", state),
                "eros", officerRepository.countByRoleAndPostingAddressState("ERO", state),
                "blos", officerRepository.countByRoleAndPostingAddressState("BLO", state)
        );
    }

    public Map<String, Object> getDeoStats(String district, String state) {
        return Map.of(
                "eros", officerRepository.countByRoleAndPostingAddressStateAndPostingAddressDistrict("ERO", state, district),
                "blos", officerRepository.countByRoleAndPostingAddressStateAndPostingAddressDistrict("BLO", state, district),
                "booths", boothRepository.count(),
                "assemblies", acsRepository.count()
        );
    }

    public Map<String, Object> getEroStats(String assembly, String district, String state) {
        return Map.of(
                "blos", officerRepository.countByRoleAndPostingAddressStateAndPostingAddressDistrictAndPostingAddressAssembley("BLO", state, district, assembly),
                "booths", boothRepository.count()
        );
    }

    public Map<String, Object> getBloStats(String boothNumber, String assembly, String district, String state) {
        return Map.of(
                "voters", voterRepository.countByStateAndDistrictAndAssembleyAndBoothNumber(state, district, assembly, boothNumber),
                "pending", 0L
        );
    }
}
