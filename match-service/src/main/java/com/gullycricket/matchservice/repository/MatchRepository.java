package com.gullycricket.matchservice.repository;

import com.gullycricket.matchservice.model.MatchInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MatchRepository extends JpaRepository<MatchInfo,Integer> {
    List<MatchInfo> findByMatchState(String state);

	List<MatchInfo> findByMatchStateInOrderByStartTimeAsc(List<String> states);

    List<MatchInfo> findByMatchStateInAndStartTimeLessThan(List<String> states, long startTime);
}
