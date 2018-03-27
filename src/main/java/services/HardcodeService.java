package services;

import org.springframework.context.annotation.Profile;

@org.springframework.stereotype.Service
@Profile("hardcode")
public class HardcodeService implements Service {

    public String getMessage(){
        return "Hello hardcode";
    }
}
