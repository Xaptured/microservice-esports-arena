package com.esportarena.microservices.esportsarenaapi.servicehelpers;

import com.esportarena.microservices.esportsarenaapi.exceptions.ValidationException;
import com.esportarena.microservices.esportsarenaapi.models.ProfileDetail;
import com.esportarena.microservices.esportsarenaapi.utilities.StringConstants;
import org.apache.logging.log4j.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class ProfileServiceHelper {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProfileServiceHelper.class);

    public void checkProfileDetailsFromUI(ProfileDetail detail) throws ValidationException {
        if(detail == null) {
            LOGGER.error("Validation failed in ProfileServiceHelper.class : checkProfileDetailsFromUI for object: null");
            throw new ValidationException(StringConstants.VALIDATION_ERROR);
        }
        checkUIProfileDetails(detail);
    }

    private void checkUIProfileDetails(ProfileDetail detail) throws ValidationException {
        if(Strings.isNotEmpty(detail.getEmail()) && Strings.isNotBlank(detail.getEmail())){
            LOGGER.info(StringConstants.VALIDATION_PASSED_UI);
        } else {
            LOGGER.error("Validation failed in ProfileServiceHelper.class : checkUIProfileDetails for object: {}", detail);
            throw new ValidationException(StringConstants.VALIDATION_ERROR);
        }
    }

    public void checkProfileDetailsFromDB(ProfileDetail detail) throws ValidationException {
        if(detail == null) {
            LOGGER.error("Validation failed in ProfileServiceHelper.class : checkProfileDetailsFromDB for object: null");
            throw new ValidationException(StringConstants.VALIDATION_ERROR);
        }
    }
}
