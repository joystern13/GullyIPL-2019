package com.gullycricket.votingservice.repository;

import com.gullycricket.votingservice.model.VotingDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VotingDetailsRepository extends JpaRepository<VotingDetails, Integer> {
    VotingDetails findByUserIdAndMatchId(int userId, int matchId);
    List<VotingDetails> findByUserId(int userId);
    List<VotingDetails> findByMatchId(int matchId);
    List<VotingDetails> findByMatchIdAndTeamId(int matchId, int teamId);
    List<VotingDetails> findByMatchIdIn(List<Integer> matchIds);
}
