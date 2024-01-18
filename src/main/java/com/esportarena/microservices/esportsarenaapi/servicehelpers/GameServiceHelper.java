package com.esportarena.microservices.esportsarenaapi.servicehelpers;

import com.esportarena.microservices.esportsarenaapi.exceptions.ValidationException;
import com.esportarena.microservices.esportsarenaapi.models.Game;
import com.esportarena.microservices.esportsarenaapi.utilities.StringConstants;
import io.micrometer.common.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class GameServiceHelper {

    private static final Logger LOGGER = LoggerFactory.getLogger(GameServiceHelper.class);

    public void checkGameFromUI(Game game) throws ValidationException {
        if(game == null) {
            LOGGER.error("Validation failed in GameServiceHelper.class : checkGameFromUI for object: null");
            throw new ValidationException(StringConstants.VALIDATION_ERROR);
        }
        checkUIGame(game);
    }

    private void checkUIGame(Game game) throws ValidationException {
        if(StringUtils.isNotEmpty(game.getName()) && StringUtils.isNotBlank(game.getName()) && game.getStatus() != null ) {
            LOGGER.info(StringConstants.VALIDATION_PASSED_UI);
        } else {
            LOGGER.error("Validation failed in GameServiceHelper.class : checkUIGame for object: {}", game);
            throw new ValidationException(StringConstants.VALIDATION_ERROR);
        }
    }
}
