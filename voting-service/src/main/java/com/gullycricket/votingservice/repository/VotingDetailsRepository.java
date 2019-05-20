package com.gullycricket.votingservice.repository;

import com.gullycricket.votingservice.model.VotingDetails;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VotingDetailsRepository extends JpaRepository<VotingDetails, Integer> {
    VotingDetails findByUserIdAndMatchId(int userId, int matchId);
    List<VotingDetails> findByUserId(int userId);
}