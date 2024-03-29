package com.gullycricket.matchservice.repository;

import com.gullycricket.matchservice.model.MatchInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Repository
public interface MatchRepository extends JpaRepository<MatchInfo,Integer> {
    List<MatchInfo> findByMatchState(String state);

    List<MatchInfo> findByMatchStateNotInOrderByMatchIdDesc(List<String> states);

	List<MatchInfo> findByMatchStateInOrderByStartTimeAsc(List<String> states);

    List<MatchInfo> findByMatchStateNotInAndStartTimeLessThan(List<String> states, long startTime);

    Optional<MatchInfo> findByMatchIdAndStartTimeGreaterThan(int matchId, long startTime);
}
