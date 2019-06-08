package com.gullycricket.votingservice.resource;

import com.gullycricket.votingservice.repository.VotingDetailsRepository;
import com.gullycricket.votingservice.model.VotingDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/gullycricket/votes")
public class VotingResource {

    @Autowired
    VotingDetailsRepository votingDetailsRepository;

    private final int MULTIPLIER = 1;
    private final String STATE_UPCOMING = "upcoming";
    private final String STATE_PREVIEW = "preview";

    @Value("${matchservice.url.matchstate}")
    private String matchStateUrl;

    @PostMapping(value = "/add")
    public ResponseEntity castVote(@RequestBody VotingDetails votingDetails){

        //check match state first

        RestTemplate restTemplate = new RestTemplate();
        String url = matchStateUrl.replace("{match_id}", String.valueOf(votingDetails.getMatchId()));
        String allowVoting = restTemplate.getForObject(url, String.class);
        System.out.println("allowVoting : " + allowVoting);

        if(!allowVoting.equals("YES"))
        {
            return new ResponseEntity("NO",HttpStatus.FORBIDDEN);
        }

        VotingDetails existingVote = votingDetailsRepository.findByUserIdAndMatchId(votingDetails.getUserId(),votingDetails.getMatchId());
        if (existingVote != null){
            existingVote.setTeamId(votingDetails.getTeamId());
            votingDetailsRepository.save(existingVote);
            return new ResponseEntity("Vote created successfully",HttpStatus.OK);
        }
        else{
            votingDetailsRepository.save(votingDetails);
            return new ResponseEntity("Vote created successfully",HttpStatus.OK);
        }
    }

    @GetMapping(value = {"/get", "/get/{user_id}"})
    public List<?> getUserVotes(@PathVariable("user_id")final Optional<Integer> userId){
        System.out.println("here");
        if(userId.isPresent())
            return votingDetailsRepository.findByUserId(userId.get());
        else
            return votingDetailsRepository.findUsersPoints();
    }

    @GetMapping(value = "/{match_id}/{user_id}")
    public VotingDetails getVote(@PathVariable(name = "match_id") final int matchId, @PathVariable(name = "user_id") final int userId){
        System.out.println("Here");
        return votingDetailsRepository.findByUserIdAndMatchId(userId,matchId);
    }

    @GetMapping(value = "/update/{match_id}/{win_team_id}")
    public double updateMatchVotes(@PathVariable(name = "match_id") final int matchId, @PathVariable(name = "win_team_id") final int winTeamId)
    {
        double winningPoints = 0;
        double losingPoints = 0;
        try {


            List<VotingDetails> allUsersWhoVoted = votingDetailsRepository.findByMatchId(matchId);

            System.out.println("Voting list : " + allUsersWhoVoted);

            Predicate<VotingDetails> win = s -> s.getTeamId() == winTeamId;
            Predicate<VotingDetails> lose = s -> s.getTeamId() != winTeamId;
            int winningCount = Math.toIntExact(allUsersWhoVoted.stream().filter(win).count());
            int losingCount = Math.toIntExact(allUsersWhoVoted.stream().filter(lose).count());

            System.out.println("winningCount : " + winningCount + " : " + losingCount);

            if(winningCount == 0 || losingCount == 0)
            {
                winningPoints = losingPoints = 0;
            }
            else if(winningCount != 0 && losingCount != 0)
            {
                winningPoints = ((double)losingCount/winningCount)*MULTIPLIER;
                losingPoints = -1*MULTIPLIER;
            }

            System.out.println("points : " + winningPoints + " : " + losingPoints);

            final double wonPoints = winningPoints;
            final double lostPoints = losingPoints;

            allUsersWhoVoted.stream().filter(win).peek(w -> w.setPoints(BigDecimal.valueOf(wonPoints))).collect(Collectors.toList());
            allUsersWhoVoted.stream().filter(lose).peek(w -> w.setPoints(BigDecimal.valueOf(lostPoints))).collect(Collectors.toList());

            System.out.println("Updated Voting list : " + allUsersWhoVoted);

            votingDetailsRepository.saveAll(allUsersWhoVoted);

        }
        catch (Exception e)
        {
            e.printStackTrace();
            winningPoints = -1;
        }

        return winningPoints;
    }

    @GetMapping(value = "/get")
    public List<VotingDetails> getVotesForMatches(@RequestParam List<Integer> matchIds){
        return votingDetailsRepository.findByMatchIdIn(matchIds);
    }

}
