package com.gullyipl.matchservice.resource;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gullyipl.matchservice.domain.schedule.Match;
import com.gullyipl.matchservice.domain.schedule.Schedule;
import com.gullyipl.matchservice.model.MatchInfo;
import com.gullyipl.matchservice.repository.MatchRepository;
import com.gullyipl.matchservice.utilities.XmlUtils;
import org.apache.http.client.utils.URIBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;


@RestController
@RequestMapping(value = "/gullyipl/matches")
public class MatchResource {

    @Value("${cricbuzz.url.series}")
    private String seriesUrl;

    @Value("${cricbuzz.url.match}")
    private String matchUrl;

    @Autowired
    private MatchRepository matchRepository;

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
    public List<MatchInfo> getMatches(){
        return matchRepository.findAll();
    }

    @GetMapping(value = "live")
    public List<MatchInfo> getLiveMatches(){
        return matchRepository.findByMatchState("inprogress");
    }

    @GetMapping(value = "upcoming")
    public List<MatchInfo> getUpcomingMatches(){
        return matchRepository.findByMatchStateInOrderByStartTimeAsc(Arrays.asList("upcomming","preview"));
    }
}
