package com.esportarena.microservices.esportsarenaapi.services;

import com.esportarena.microservices.esportsarenaapi.clients.TheJackFolioDBClient;
import com.esportarena.microservices.esportsarenaapi.exceptions.DataBaseOperationException;
import com.esportarena.microservices.esportsarenaapi.exceptions.MapperException;
import com.esportarena.microservices.esportsarenaapi.exceptions.ValidationException;
import com.esportarena.microservices.esportsarenaapi.models.Game;
import com.esportarena.microservices.esportsarenaapi.models.InterestedGame;
import com.esportarena.microservices.esportsarenaapi.servicehelpers.GameServiceHelper;
import com.esportarena.microservices.esportsarenaapi.utilities.StringConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GameService {

    private static final Logger LOGGER = LoggerFactory.getLogger(GameService.class);

    @Autowired
    private GameServiceHelper validation;
    @Autowired
    private TheJackFolioDBClient dbClient;

    public Game saveOrUpdateGame(Game game) throws ValidationException, DataBaseOperationException, MapperException {
        validation.checkGameFromUI(game);
        ResponseEntity<Game> response = dbClient.saveOrUpdateGame(game);
        Game responseBody = response.getBody();
        if(responseBody.getMessage().equals(StringConstants.DATABASE_ERROR)){
            throw new DataBaseOperationException(responseBody.getMessage());
        }
        else if(responseBody.getMessage().equals(StringConstants.MAPPING_ERROR)){
            throw new MapperException(responseBody.getMessage());
        }
        return responseBody;
    }

    public List<Game> findAllActiveGames() throws MapperException, DataBaseOperationException {
        ResponseEntity<List<Game>> response = dbClient.findAllActiveGames();
        List<Game> responseBody = response.getBody();
        if(responseBody != null && responseBody.size() == 1) {
            if(responseBody.get(0).getMessage().equals(StringConstants.DATABASE_ERROR)) {
                throw new DataBaseOperationException(responseBody.get(0).getMessage());
            } else if(responseBody.get(0).getMessage().equals(StringConstants.MAPPING_ERROR)) {
                throw new MapperException(responseBody.get(0).getMessage());
            }
        }
        return responseBody;
    }

    public List<InterestedGame> findInterestedGamesForUser(String email) {
        ResponseEntity<List<InterestedGame>> response = dbClient.findAllInterestedGamesForUser(email);
        List<InterestedGame> interestedGames = response.getBody();
        return interestedGames;
    }
}
