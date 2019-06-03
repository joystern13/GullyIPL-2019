package com.gullycricket.matchservice.resource;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gullycricket.matchservice.model.MatchInfo;
import com.gullycricket.matchservice.domain.schedule.Match;
import com.gullycricket.matchservice.domain.schedule.Schedule;
import com.gullycricket.matchservice.repository.MatchRepository;
import com.gullycricket.matchservice.utilities.XmlUtils;
import org.apache.http.client.utils.URIBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.io.*;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.Charset;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;


@RestController
@RequestMapping(value = "/gullycricket/matches")
public class MatchResource {

    @Value("${cricbuzz.url.series}")
    private String seriesUrl;

    @Value("${cricbuzz.url.match}")
    private String matchUrl;

    @Value("${votingservice.url.updateresult}")
    private String votingServiceUrl;

    @Autowired
    private MatchRepository matchRepository;

    private final String COMPLETE = "complete";
    private final String MOM = "mom";
    private final String ABANDON = "abandon";

    @GetMapping(value = "/reload")
    public String loadMatchInfo() {


        ObjectMapper mapper = new ObjectMapper();
        InputStream is = null;

        matchRepository.deleteAll();

        try {
            is = new URL(seriesUrl).openStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = XmlUtils.readAll(rd);
            mapper.configure(
                    DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            Schedule schedule = mapper.readValue(jsonText, Schedule.class);

            schedule.getMatches().stream().forEach(match -> {
                MatchInfo matchInfo = new MatchInfo();
                matchInfo.setMatchId(match.getMatch_id());
                matchInfo.setMatchDescription(match.getHeader().getMatch_desc());
                matchInfo.setStartTime(match.getHeader().getStart_time());
                matchInfo.setEndTime(match.getHeader().getEnd_time());
                matchInfo.setDayNightFlag(match.getHeader().getDn());
                matchInfo.setVenueName(match.getVenue().getName());
                matchInfo.setVenueLocation(match.getVenue().getLocation());
                matchInfo.setMatchState(match.getHeader().getState());
                matchInfo.setResultDesc(match.getHeader().getStatus());
                //matchInfo.setTeam1Info(match.getTeam1().getId());
                //matchInfo.setTeam2Id(match.getTeam2().getId());

                try {

                    URIBuilder uriBuilder = new URIBuilder();
                    uriBuilder.setPath(matchUrl.replace("{match_id}", String.valueOf(matchInfo.getMatchId())));
                    URL url = uriBuilder.build().toURL();
                    InputStream inputStream = url.openStream();

                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, Charset.forName("UTF-8")));
                    String matchStr = XmlUtils.readAll(bufferedReader);
                    System.out.println(matchStr);
                    ObjectMapper mapper1 = new ObjectMapper();
                    mapper1.configure(
                            DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

                    Match matchDetails = mapper1.readValue(matchStr, Match.class);
                    //matchInfo.setWinnerTeamId(match.getWinning_team_id());

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                matchRepository.save(matchInfo);


            });

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        return "Matches reloaded successfully!";
    }

    @GetMapping(value = "")
    public List<MatchInfo> getMatches() {
        return matchRepository.findAll();
    }

    @GetMapping(value = "live")
    public List<MatchInfo> getLiveMatches() {
        return matchRepository.findByMatchState("inprogress");
    }

    @GetMapping(value = "upcoming")
    public List<MatchInfo> getUpcomingMatches() {
        return matchRepository.findByMatchStateInOrderByStartTimeAsc(Arrays.asList("upcoming", "preview"));
    }

    @GetMapping(value = "update")
    public ResponseEntity<?> updateMatches() {
        long now = Instant.now().getEpochSecond();
        System.out.println("current time : " + now);
        List<MatchInfo> matches = matchRepository.findByMatchStateInAndStartTimeLessThan(Arrays.asList("upcoming", "preview"), now);
        System.out.println("matches : " + matches);
        URIBuilder uriBuilder = new URIBuilder();
        matches.forEach(match -> {

            try {

                uriBuilder.setPath(matchUrl.replace("{match_id}", String.valueOf(match.getMatchId())));
                URL url = uriBuilder.build().toURL();
                InputStream is = url.openStream();
                ObjectMapper mapper = new ObjectMapper();

                BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
                String jsonText = XmlUtils.readAll(rd);

                mapper.configure(
                        DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                Match matchDetails = mapper.readValue(jsonText, Match.class);

                System.out.println("MAtch details : " + matchDetails);

                if (matchDetails.getHeader().getState().equals(COMPLETE)
                        || matchDetails.getHeader().getState().equals(MOM)
                        || matchDetails.getHeader().getState().equals(ABANDON))
                {
                    int matchId = matchDetails.getMatch_id();
                    int winnerTeamId = matchDetails.getHeader().getWinning_team_id();
                    String matchStatus = matchDetails.getHeader().getStatus();

                    double winningPoints = updateMatchResult(matchId, winnerTeamId);

                    if(winningPoints != -1)//success
                    {
                        match.setMatchState(matchDetails.getHeader().getState());
                        match.setWinnerTeamId(winnerTeamId);
                        match.setPoints(BigDecimal.valueOf(winningPoints));
                        match.setResultDesc(matchStatus);

                        matchRepository.save(match);
                    }
                    else
                        System.out.println("Error in updating Matchid : " + matchId);
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (URISyntaxException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        });

        return ResponseEntity.ok("SUCCESS");
    }

    private double updateMatchResult(int matchId, int winnerTeamId) {

        String url = votingServiceUrl.replace("{match_id}", String.valueOf(matchId)).replace("{win_team_id}", String.valueOf(winnerTeamId));

        return new RestTemplate().getForObject(url, Double.class);
    }

}
