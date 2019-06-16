package sooriya.gdrive.viewdocsapi;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashape.unirest.request.body.MultipartBody;
import org.apache.commons.io.IOUtils;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import sooriya.gdrive.viewdocsapi.exception.ViewDocsException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

@RestController
@Scope(value = "prototype")
public class ViewDocsController {

    public static final String CE_URL = "https://staging.cloud-elements.com/elements/api-v2";
    //    private static final String authHeader = "User qEDaQpt4I8VMiNqO5Alv/02/jJopo34wONOB4+3xbK0=, Organization 43552db6de425ad3c390d61b2fd95458, " +
    //            "Element 0GyyX1o84praIZQHJfa0kcnDCvsdNbjhQdHDH/udoH4=";

    @RequestMapping(value = "/contents", method = RequestMethod.GET)
    public String viewFolders(@RequestParam String path, @RequestHeader(value = "Authorization") String authHeader) {


        // 1. Hit Cloud elements /folders/contents with authorization header and path query parameter

        //curl -X GET "https://staging.cloud-elements.com/elements/api-v2/folders/contents?path=%2FMyDir"
        // -H "accept: application/json" -H
        // "Authorization: User 7awuPTubPWe+aiO2CcHfA/4+InEfnEx8SWAFLoTVoaI=, Organization 339c4fd6d608536ba69532ee256b9018, Element afwSgFLa01IPVpJFoCM62N2bYy+tXOKmTxrRDnw2Gis="
        try {

            Map<String, String> headers = new HashMap<>();

            headers.put("Authorization", authHeader);
            headers.put("Accept", "application/json");
            headers.put("Content-Type", "application/json");

            HttpResponse<String> response = Unirest.get(CE_URL + "/folders/contents")
                    .headers(headers)
                    .queryString("path", path)
                    .asString();

            if (response == null) {
                throw new ViewDocsException(HttpStatus.BAD_REQUEST, "Failed to fetch the specified folder contents");
            }

            int statusCode = response.getStatus();

            if (statusCode < 200 || statusCode >= 300) {
                throw new ViewDocsException(HttpStatus.valueOf(statusCode), response.getStatusText());
            }

            return response.getBody();
        } catch (UnirestException ue) {
            throw new ViewDocsException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to fetch the specified folder contents");
        }


    }

    @RequestMapping(value = "/fetchFiles", method = RequestMethod.GET)
    public void fetchFile(@RequestParam String path, HttpServletResponse httpServletResponse, @RequestHeader(value = "Authorization") String authHeader) {

        // 2.
        //curl -X GET "https://staging.cloud-elements.com/elements/api-v2/files/fdff"

        Map<String, String> headers = new HashMap<>();

        headers.put("Authorization", authHeader);
        headers.put("Accept", "application/json");
        headers.put("Content-Type", "application/json");

        try {
            HttpResponse<InputStream> response = Unirest.get(CE_URL + "/files")
                    .queryString("path", path)
                    .headers(headers)
                    .asBinary();
            if (response == null) {
                throw new ViewDocsException(HttpStatus.BAD_REQUEST, "Failed to fetch the specified folder contents");
            }

            int statusCode = response.getStatus();

            if (statusCode < 200 || statusCode >= 300) {
                throw new ViewDocsException(HttpStatus.valueOf(statusCode), response.getStatusText());
            }

            IOUtils.copyLarge(response.getBody(), httpServletResponse.getOutputStream());

        } catch (UnirestException | IOException e) {
            throw new ViewDocsException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to fetch the specified folder contents");
        }


    }

    @RequestMapping(value = "/postFiles", method = RequestMethod.POST)
    public String postFile(@RequestParam String path, HttpServletRequest request, @RequestHeader(value = "Authorization") String authHeader) {


        Map<String, String> headers = new HashMap();
        headers.put("Authorization", authHeader);
        headers.put("Accept", "application/json");
        try {
            Part part = request.getPart("file");
            MultipartBody multiPartRequestBody = null;

            multiPartRequestBody =
                    Unirest.post(CE_URL + "/files")
                            .queryString("path", path)
                            .headers(headers)
                            .field("file",
                                    part.getInputStream(),
                                    ContentType.parse(part.getContentType()),
                                    part.getName());

            multiPartRequestBody.mode(HttpMultipartMode.BROWSER_COMPATIBLE.toString());

            HttpResponse<String> response = multiPartRequestBody.asString();

            if (response == null) {
                throw new ViewDocsException(HttpStatus.BAD_REQUEST, "Failed to fetch the specified folder contents");
            }

            int statusCode = response.getStatus();

            if (statusCode < 200 || statusCode >= 300) {
                throw new ViewDocsException(HttpStatus.valueOf(statusCode), response.getStatusText());
            }


            System.out.println(response);

            return response.getBody();

        } catch (Exception ue) {

            throw new ViewDocsException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to upload file");
        }


    }


}
