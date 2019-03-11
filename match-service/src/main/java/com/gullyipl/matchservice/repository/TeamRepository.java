package com.gullyipl.matchservice.repository;

import com.gullyipl.matchservice.model.TeamInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamRepository extends JpaRepository<TeamInfo, Integer> {

}
