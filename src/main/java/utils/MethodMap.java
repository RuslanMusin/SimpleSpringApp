package utils;

import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

@Service
public class MethodMap {

    public String redirectReq(String methodName){
        return "redirect:" + MvcUriComponentsBuilder.fromMappingName(methodName).build();
    }

    public String getReq(String methodName){
        return MvcUriComponentsBuilder.fromMappingName(methodName).build();
    }
}
