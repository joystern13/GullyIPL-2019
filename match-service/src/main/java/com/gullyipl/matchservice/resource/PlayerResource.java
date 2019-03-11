package com.gullyipl.matchservice.resource;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.gullyipl.matchservice.domain.players.Player;
import com.gullyipl.matchservice.model.PlayerInfo;
import com.gullyipl.matchservice.repository.PlayerRepository;
import com.gullyipl.matchservice.repository.TeamRepository;
import com.gullyipl.matchservice.utilities.XmlUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
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
@RequestMapping(value = "/gullyipl/players")
public class PlayerResource {

    @Value("${cricbuzz.url.players}")
    private String playersUrl;

    @Autowired
    TeamRepository teamRepository;
    @Autowired
    PlayerRepository playerRepository;

    @GetMapping(value = "/reload")
    public String reloadAllPlayers() {


        playerRepository.deleteAll();
        List<PlayerInfo> playerInfos = new ArrayList<>();


        teamRepository.findAll().forEach(teamInfo -> {
            ObjectMapper mapper = new ObjectMapper();
            InputStream is = null;

            try {
                is = new URL(playersUrl.replace("{team_id}", String.valueOf(teamInfo.getTeamId()))).openStream();
                BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
                String jsonText = null;
                jsonText = XmlUtils.readAll(rd);
                mapper.configure(
                        DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                TypeFactory typeFactory = mapper.getTypeFactory();
                List<Player> players = mapper.readValue(jsonText, typeFactory.constructCollectionType(List.class, Player.class));

                players.forEach(player -> {
                    PlayerInfo playerInfo = new PlayerInfo();
                    playerInfo.setPlayerId(player.getId());
                    playerInfo.setFullName(player.getF_name());
                    playerInfo.setShortName(player.getName());
                    playerInfo.setAge(player.getAge());
                    playerInfo.setRole(player.getRole());
                    playerInfo.setStyle(player.getStyle());
                    playerInfo.setTeamId(teamInfo.getTeamId());
                    playerInfos.add(playerInfo);
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

        });

        playerRepository.saveAll(playerInfos);

        return "Done!";
    }


}
