package com.gullyipl.matchservice.repository;

import com.gullyipl.matchservice.model.MatchInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MatchRepository extends JpaRepository<MatchInfo,Integer> {
    List<MatchInfo> findByMatchState(String state);
}
