package sooriya.gdrive.viewdocsapi;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class ViewDocsController {

    public static final String CE_URL = "https://staging.cloud-elements.com/elements/api-v2";
    @RequestMapping(value = "/contents",method = RequestMethod.GET)
    public String viewFolders(@RequestParam String path){


        // 1. Hit Cloud elements /folders/contents with authorization header and path query parameter

        //curl -X GET "https://staging.cloud-elements.com/elements/api-v2/folders/contents?path=%2FMyDir"
        // -H "accept: application/json" -H
        // "Authorization: User 7awuPTubPWe+aiO2CcHfA/4+InEfnEx8SWAFLoTVoaI=, Organization 339c4fd6d608536ba69532ee256b9018, Element afwSgFLa01IPVpJFoCM62N2bYy+tXOKmTxrRDnw2Gis="
        try {

            Map<String, String> headers = new HashMap<>();

            headers.put("Authorization","User 7awuPTubPWe+aiO2CcHfA/4+InEfnEx8SWAFLoTVoaI=, Organization 339c4fd6d608536ba69532ee256b9018, Element afwSgFLa01IPVpJFoCM62N2bYy+tXOKmTxrRDnw2Gis=");
            headers.put("Accept", "application/json");
            headers.put("Content-Type", "application/json");

            HttpResponse<String> response = Unirest.get(CE_URL + "/folders/contents")
                    .headers(headers)
                    .queryString("path", path)
                    .asString();
            int statusCode = response.getStatus();

            if (statusCode < 200 || statusCode >= 300) {
                throw new RuntimeException(response.getBody());
            }

            return response.getBody();
        } catch (UnirestException ue) {
            //To-do
        }

        return "Failed";
    }
}
