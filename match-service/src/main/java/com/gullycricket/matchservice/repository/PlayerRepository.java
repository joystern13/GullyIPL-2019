package com.gullycricket.matchservice.repository;

import com.gullycricket.matchservice.model.PlayerInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlayerRepository extends JpaRepository<PlayerInfo,Integer> {
    void deleteByTeamId(int teamId);
    List<PlayerInfo> findByTeamId(int teamId);
}
