package com.gullyipl.votingservice.resource;

import com.gullyipl.votingservice.model.VotingDetails;
import com.gullyipl.votingservice.repository.VotingDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/gullyipl/votes")
public class VotingResource {

    @Autowired
    VotingDetailsRepository votingDetailsRepository;

    @PostMapping(value = "/add")
    public String castVote(VotingDetails votingDetails){
        VotingDetails existingVote = votingDetailsRepository.findByUserIdAndMatchId(votingDetails.getUserId(),votingDetails.getMatchId());
        if (existingVote != null){
            existingVote.setTeamId(votingDetails.getTeamId());
            votingDetailsRepository.save(existingVote);
            return "Vote updated successfully";
        }
        else{
            votingDetailsRepository.save(votingDetails);
            return "Vote created successfully";
        }
    }

    @GetMapping(value = "/get/{user_id}")
    public List<VotingDetails> getUserVotes(@PathVariable(name = "user_id")final int userId){
        return votingDetailsRepository.findByUserId(userId);
    }

    @GetMapping(value = "/{match_id}/{user_id}")
    public VotingDetails getVote(@PathVariable(name = "match_id") final int matchId, @PathVariable(name = "user_id") final int userId){
        return votingDetailsRepository.findByUserIdAndMatchId(userId,matchId);
    }

}
