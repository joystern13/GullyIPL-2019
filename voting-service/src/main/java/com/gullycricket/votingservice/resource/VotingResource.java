package com.gullycricket.votingservice.resource;

import com.gullycricket.votingservice.repository.VotingDetailsRepository;
import com.gullycricket.votingservice.model.VotingDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.List;
import java.util.function.Predicate;

@RestController
@RequestMapping(value = "/gullycricket/votes")
public class VotingResource {

    @Autowired
    VotingDetailsRepository votingDetailsRepository;

    private final int MULTIPLIER = 1;

    @Value("${userservice.url.activeusers}")
    private String userServiceUrl;

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

    @GetMapping(value = "/update/{match_id}/{win_team_id}")
    public long updateMatchVotes(@PathVariable(name = "match_id") final int matchId, @PathVariable(name = "win_team_id") final int winTeamId)
    {
        long winningPoints = 0;
        long losingPoints = 0;
        try {


            List<VotingDetails> winningVotingDetails = votingDetailsRepository.findByMatchIdAndTeamId(matchId, winTeamId);
            List<VotingDetails> allTeamsWhoVoted = votingDetailsRepository.findByMatchId(matchId);

            System.out.println("Voting list : " + winningVotingDetails);

            RestTemplate restTemplate = new RestTemplate();
            Long activeUsersCount = restTemplate.getForObject(userServiceUrl, Long.class);
            System.out.println("Active Users Count : " + activeUsersCount);

            Predicate<VotingDetails> win = s -> s.getTeamId() == winTeamId;
            Predicate<VotingDetails> lose = s -> s.getTeamId() != winTeamId;
            long winningCount = allTeamsWhoVoted.stream().filter(win).count();
            long losingCount = allTeamsWhoVoted.stream().filter(lose).count();



            if(winningCount != 0 && losingCount != 0)
            {
                winningPoints = (losingCount/winningCount)*MULTIPLIER;
                losingPoints = -1*MULTIPLIER;
            }

            final long wonPoints = winningPoints;
            final long lostPoints = losingPoints;

            allTeamsWhoVoted.stream().filter(win).peek(w -> w.setPoints(BigDecimal.valueOf(wonPoints)));
            allTeamsWhoVoted.stream().filter(lose).peek(w -> w.setPoints(BigDecimal.valueOf(lostPoints)));

            votingDetailsRepository.saveAll(allTeamsWhoVoted);

        }
        catch (Exception e)
        {
            e.printStackTrace();
            winningPoints = -1;
        }

        return winningPoints;
    }

}
