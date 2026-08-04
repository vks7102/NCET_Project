package com.capstone.backend.service;

import com.capstone.backend.exception.ApiError;
import com.capstone.backend.model.State;
import com.capstone.backend.repository.StateRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class StateService {

    private final StateRepository stateRepository;

    public StateService(StateRepository stateRepository) {
        this.stateRepository = stateRepository;
    }

    public List<State> getAllStates() {
        List<State> states = stateRepository.findAll();
        if (states.isEmpty()) throw new ApiError(404, "States not found");
        return states;
    }

    public Map<String, Object> getAllStatesPaginated(Map<String, String> params) {
        int page = Integer.parseInt(params.getOrDefault("page", "1"));
        int limit = Integer.parseInt(params.getOrDefault("limit", "10"));
        Pageable pageable = PageRequest.of(page - 1, limit);
        Page<State> statePage = stateRepository.findAll(pageable);
        return Map.of(
                "states", statePage.getContent(),
                "pagination", Map.of(
                        "page", page,
                        "limit", limit,
                        "total", statePage.getTotalElements(),
                        "totalPages", statePage.getTotalPages()
                )
        );
    }

    public State createState(State state) {
        if (stateRepository.findById(state.getStateCode()).isPresent()) {
            throw new ApiError(400, "State with this code already exists");
        }
        return stateRepository.save(state);
    }

    public State updateState(String id, State state) {
        State existing = stateRepository.findById(id)
                .orElseThrow(() -> new ApiError(404, "State not found"));
        if (state.getStateName() != null) existing.setStateName(state.getStateName());
        if (state.getStateType() != null) existing.setStateType(state.getStateType());
        return stateRepository.save(existing);
    }

    public void deleteState(String id) {
        State existing = stateRepository.findById(id)
                .orElseThrow(() -> new ApiError(404, "State not found"));
        stateRepository.delete(existing);
    }

    public long getStatesCount() {
        return stateRepository.count();
    }
}
