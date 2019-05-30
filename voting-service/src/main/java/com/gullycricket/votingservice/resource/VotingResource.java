package com.gullycricket.votingservice.resource;

import com.gullycricket.votingservice.repository.VotingDetailsRepository;
import com.gullycricket.votingservice.model.VotingDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/gullyipl/votes")
public class VotingResource {

    @Autowired
    VotingDetailsRepository votingDetailsRepository;

    @PostMapping(value = "/add")
    public ResponseEntity<String> castVote(@RequestBody VotingDetails votingDetails){
        VotingDetails existingVote = votingDetailsRepository.findByUserIdAndMatchId(votingDetails.getUserId(),votingDetails.getMatchId());
        if (existingVote != null){
            existingVote.setTeamId(votingDetails.getTeamId());
            votingDetailsRepository.save(existingVote);
            return ResponseEntity.ok("Vote updated successfully");
        }
        else{
            votingDetailsRepository.save(votingDetails);
            return ResponseEntity.ok("Vote created successfully");
        }
    }

    @GetMapping(value = "/get/{user_id}")
    public List<VotingDetails> getUserVotes(@PathVariable("user_id")final int userId){
        System.out.println("here");
        return votingDetailsRepository.findByUserId(userId);
    }

    @GetMapping(value = "/{match_id}/{user_id}")
    public VotingDetails getVote(@PathVariable(name = "match_id") final int matchId, @PathVariable(name = "user_id") final int userId){
        System.out.println("Here");
        return votingDetailsRepository.findByUserIdAndMatchId(userId,matchId);
    }

}
