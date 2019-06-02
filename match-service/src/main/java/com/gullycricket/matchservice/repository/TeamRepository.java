package com.gullycricket.matchservice.repository;

import com.gullycricket.matchservice.model.TeamInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamRepository extends JpaRepository<TeamInfo, Integer> {

}
