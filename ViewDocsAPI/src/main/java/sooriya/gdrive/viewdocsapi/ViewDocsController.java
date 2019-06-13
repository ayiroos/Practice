package sooriya.gdrive.viewdocsapi;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashape.unirest.request.HttpRequestWithBody;
import org.apache.commons.io.IOUtils;
import org.apache.http.entity.ContentType;
import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.apache.tomcat.util.http.fileupload.servlet.ServletRequestContext;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.StandardMultipartHttpServletRequest;
import sooriya.gdrive.viewdocsapi.exception.ViewDocsException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Scope(value = "prototype")
public class ViewDocsController {

    public static final String CE_URL = "https://staging.cloud-elements.com/elements/api-v2";

    @RequestMapping(value = "/contents", method = RequestMethod.GET)
    public String viewFolders(@RequestParam String path) {


        // 1. Hit Cloud elements /folders/contents with authorization header and path query parameter

        //curl -X GET "https://staging.cloud-elements.com/elements/api-v2/folders/contents?path=%2FMyDir"
        // -H "accept: application/json" -H
        // "Authorization: User 7awuPTubPWe+aiO2CcHfA/4+InEfnEx8SWAFLoTVoaI=, Organization 339c4fd6d608536ba69532ee256b9018, Element afwSgFLa01IPVpJFoCM62N2bYy+tXOKmTxrRDnw2Gis="
        try {

            Map<String, String> headers = new HashMap<>();

            headers.put("Authorization", "User 7awuPTubPWe+aiO2CcHfA/4+InEfnEx8SWAFLoTVoaI=, Organization 339c4fd6d608536ba69532ee256b9018, Element afwSgFLa01IPVpJFoCM62N2bYy+tXOKmTxrRDnw2Gis=");
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

    @RequestMapping(value = "/fetchFiles/{id}", method = RequestMethod.GET)
    public void fetchFile(@PathVariable String id, HttpServletResponse httpServletResponse) {

        // 2.
        //curl -X GET "https://staging.cloud-elements.com/elements/api-v2/files/fdff"

        Map<String, String> headers = new HashMap<>();

        headers.put("Authorization", "User 7awuPTubPWe+aiO2CcHfA/4+InEfnEx8SWAFLoTVoaI=, Organization 339c4fd6d608536ba69532ee256b9018, Element afwSgFLa01IPVpJFoCM62N2bYy+tXOKmTxrRDnw2Gis=");
        headers.put("Accept", "application/json");
        headers.put("Content-Type", "application/json");

        try {
            HttpResponse<InputStream> response = Unirest.get(CE_URL + "/files/" + id)
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


    @RequestMapping(value = "/uploadFiles", method = RequestMethod.POST)
    public String uploadFiles(@RequestParam String path, HttpServletRequest httpServletRequest) {

        // 2.
        //curl -X GET "https://staging.cloud-elements.com/elements/api-v2/files/fdff"

        Map<String, String> headers = new HashMap<>();

        headers.put("Authorization", "User 7awuPTubPWe+aiO2CcHfA/4+InEfnEx8SWAFLoTVoaI=, Organization 339c4fd6d608536ba69532ee256b9018, Element afwSgFLa01IPVpJFoCM62N2bYy+tXOKmTxrRDnw2Gis=");
        headers.put("Accept", "application/json");
        headers.put("Content-Type", "multipart/form-data");

        try {

            if (!ServletFileUpload.isMultipartContent(httpServletRequest)) {
                throw new ViewDocsException(HttpStatus.BAD_REQUEST, "Failed to upload the file, please provide the multipart content");
            }

//            DiskFileItemFactory diskFileItemFactory = new DiskFileItemFactory();
//            ServletFileUpload servletFileUpload = new ServletFileUpload(diskFileItemFactory);
//            List<FileItem> fileItems = servletFileUpload.parseRequest(new ServletRequestContext(httpServletRequest));

            MultipartFile file = ((StandardMultipartHttpServletRequest) httpServletRequest).getFile("file");

            if (file == null) {
                throw new ViewDocsException(HttpStatus.BAD_REQUEST, "Please provide file as a Multipart content");
            }






            // Unirest Post request need to be added here.

            if (response == null) {
                throw new ViewDocsException(HttpStatus.BAD_REQUEST, "Failed to fetch the specified folder contents");
            }

            int statusCode = response.getStatus();

            if (statusCode < 200 || statusCode >= 300) {
                throw new ViewDocsException(HttpStatus.valueOf(statusCode), response.getStatusText());
            }

            return response.getBody();

        } catch (UnirestException e) {
            throw new ViewDocsException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to fetch the specified folder contents");
        } catch (IOException e) {
            throw new ViewDocsException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to fetch the specified folder contents");
        }

    }

}
