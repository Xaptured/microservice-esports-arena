package com.esportarena.microservices.esportsarenaapi.servicehelpers;

import com.esportarena.microservices.esportsarenaapi.exceptions.ValidationException;
import com.esportarena.microservices.esportsarenaapi.models.Partner;
import com.esportarena.microservices.esportsarenaapi.models.ProfileDetail;
import com.esportarena.microservices.esportsarenaapi.utilities.StringConstants;
import io.micrometer.common.util.StringUtils;
import org.apache.logging.log4j.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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
        LOGGER.info(StringConstants.VALIDATION_PASSED_DB);
    }

    public boolean isProfileComplete(ProfileDetail detail) {
        if(StringUtils.isNotBlank(detail.getName()) && StringUtils.isNotEmpty(detail.getName())
        && StringUtils.isNotBlank(detail.getName()) && StringUtils.isNotEmpty(detail.getName())
        && StringUtils.isNotBlank(detail.getName()) && StringUtils.isNotEmpty(detail.getName())) {
            return true;
        } else return false;
    }

    public void checkPartnerFromUI(Partner partner) throws ValidationException {
        if(Strings.isEmpty(partner.getEmail()) || Strings.isBlank(partner.getEmail()) || Strings.isEmpty(partner.getName()) || Strings.isBlank(partner.getName())) {
            LOGGER.error("Validation failed in ProfileServiceHelper.class : checkProfileDetailsFromUI for object: null");
            throw new ValidationException(StringConstants.VALIDATION_ERROR);
        }
        LOGGER.info(StringConstants.VALIDATION_PASSED_UI);
    }

    public void checkPartnerFromDB(Partner partner) throws ValidationException {
        if(partner == null) {
            LOGGER.error("Validation failed in ProfileServiceHelper.class : checkPartnerFromDB for object: null");
            throw new ValidationException(StringConstants.VALIDATION_ERROR);
        }
        LOGGER.info(StringConstants.VALIDATION_PASSED_DB);
    }

    public void checkPartnerDocsFromUI(MultipartFile image, MultipartFile doc, String email) throws ValidationException {
        if(image.isEmpty() || doc.isEmpty() || StringUtils.isBlank(email) || StringUtils.isEmpty(email)) {
            LOGGER.error("Validation failed in ProfileServiceHelper.class : checkPartnerDocsFromUI for object: null");
            throw new ValidationException(StringConstants.VALIDATION_ERROR);
        }
        LOGGER.info(StringConstants.VALIDATION_PASSED_UI);
    }
}
