package com.esportarena.microservices.esportsarenaapi.services;

import com.esportarena.microservices.esportsarenaapi.clients.TheJackFolioDBClient;
import com.esportarena.microservices.esportsarenaapi.exceptions.DataBaseOperationException;
import com.esportarena.microservices.esportsarenaapi.exceptions.MapperException;
import com.esportarena.microservices.esportsarenaapi.exceptions.ValidationException;
import com.esportarena.microservices.esportsarenaapi.models.ProfileDetail;
import com.esportarena.microservices.esportsarenaapi.servicehelpers.ProfileServiceHelper;
import com.esportarena.microservices.esportsarenaapi.utilities.StringConstants;
import io.micrometer.common.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ProfileService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProfileService.class);
    @Autowired
    private ProfileServiceHelper validation;
    @Autowired
    private TheJackFolioDBClient dbClient;

    public ProfileDetail saveOrUpdateProfileDetails(ProfileDetail detail) throws ValidationException, DataBaseOperationException, MapperException {
        validation.checkProfileDetailsFromUI(detail);
        ResponseEntity<ProfileDetail> response = dbClient.saveOrUpdateProfileDetails(detail);
        ProfileDetail responseBody = response.getBody();
        validation.checkProfileDetailsFromDB(responseBody);
        if(responseBody.getMessage().equals(StringConstants.DATABASE_ERROR)){
            throw new DataBaseOperationException(responseBody.getMessage());
        }
        else if(responseBody.getMessage().equals(StringConstants.MAPPING_ERROR)){
            throw new MapperException(responseBody.getMessage());
        }
        return responseBody;
    }

    public ProfileDetail getProfileDetails(String email) throws ValidationException, DataBaseOperationException, MapperException {
        if(StringUtils.isBlank(email) || StringUtils.isEmpty(email)) {
            LOGGER.error("Validation failed in ProfileService.class : getProfileDetails for object: null");
            throw new ValidationException(StringConstants.VALIDATION_ERROR);
        } else {
            ResponseEntity<ProfileDetail> response = dbClient.getProfileDetails(email);
            ProfileDetail responseBody = response.getBody();
            validation.checkProfileDetailsFromDB(responseBody);
            if(responseBody.getMessage().equals(StringConstants.DATABASE_ERROR)){
                throw new DataBaseOperationException(responseBody.getMessage());
            }
            else if(responseBody.getMessage().equals(StringConstants.MAPPING_ERROR)){
                throw new MapperException(responseBody.getMessage());
            }
            return responseBody;
        }
    }
}
