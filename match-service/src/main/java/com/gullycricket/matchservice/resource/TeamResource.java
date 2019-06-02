package com.gullycricket.matchservice.resource;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.gullycricket.matchservice.domain.players.Player;
import com.gullycricket.matchservice.domain.teams.Teams;
import com.gullycricket.matchservice.model.PlayerInfo;
import com.gullycricket.matchservice.model.TeamInfo;
import com.gullycricket.matchservice.repository.PlayerRepository;
import com.gullycricket.matchservice.repository.TeamRepository;
import com.gullycricket.matchservice.utilities.XmlUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/gullycricket/teams")
public class TeamResource {

    @Value("${cricbuzz.url.team}")
    private String teamsUrl;

    @Value("${cricbuzz.url.players}")
    private String playersUrl;

    @Autowired
    TeamRepository teamRepository;

    @Autowired
    PlayerRepository playerRepository;

    @GetMapping(value = "/reload")
    public void reloadTeams() {
        ObjectMapper mapper = new ObjectMapper();
        InputStream is = null;

        teamRepository.deleteAll();

        try {
            is = new URL(teamsUrl).openStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = XmlUtils.readAll(rd);
            mapper.configure(
                    DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            Teams teams = mapper.readValue(jsonText, Teams.class);
            List<TeamInfo> teamInfos = new ArrayList<>();
            teams.getTeams().stream().forEach(team -> {
                TeamInfo teamInfo = new TeamInfo();
                teamInfo.setTeamId(team.getId());
                teamInfo.setTeamCode(team.getS_name());
                teamInfo.setTeamName(team.getName());
                teamInfo.setCaptain(team.getCaptain());
                teamInfos.add(teamInfo);
            });
            teamRepository.saveAll(teamInfos);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @GetMapping(value = "/{team_id}/players/reload")
    public String reloadPlayers(@PathVariable(name = "team_id") int teamId) {
        ObjectMapper mapper = new ObjectMapper();
        InputStream is = null;

        playerRepository.deleteByTeamId(teamId);

        try {
            is = new URL(playersUrl.replace("{team_id}", String.valueOf(teamId))).openStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = XmlUtils.readAll(rd);
            mapper.configure(
                    DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            TypeFactory typeFactory = mapper.getTypeFactory();
            List<Player> players = mapper.readValue(jsonText, typeFactory.constructCollectionType(List.class, Player.class));
            System.out.println(players);
            List<PlayerInfo> playerInfos = new ArrayList<>();
            players.forEach(player -> {
                PlayerInfo playerInfo = new PlayerInfo();
                playerInfo.setPlayerId(player.getId());
                playerInfo.setFullName(player.getF_name());
                playerInfo.setShortName(player.getName());
                playerInfo.setAge(player.getAge());
                playerInfo.setRole(player.getRole());
                playerInfo.setStyle(player.getStyle());
                playerInfo.setTeamId(teamId);
                playerInfos.add(playerInfo);
            });

            playerRepository.saveAll(playerInfos);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return "Done!";
    }

    @GetMapping(value = "")
    public List<TeamInfo> getTeams() {
        return teamRepository.findAll();
    }

    @GetMapping(value = "{team_id}")
    public TeamInfo getTeam(@PathVariable(name = "team_id") int teamId) {
        if (teamRepository.findById(teamId).isPresent())
            return teamRepository.findById(teamId).get();
        else
            return null;
    }

    @GetMapping(value = "{team_id}/players")
    public List<PlayerInfo> getPlayersInTeam(@PathVariable(name = "team_id") int teamId) {
        return playerRepository.findByTeamId(teamId);
    }
}
