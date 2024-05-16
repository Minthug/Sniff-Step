package SniffStep.common.config.oauth;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GoogleUser {


    public String id;
    public String email;
    public Boolean verifiedEmail;
    public String name;
    public String givenName;
    public String familyName;
    public String picture;
    public String locale;

}