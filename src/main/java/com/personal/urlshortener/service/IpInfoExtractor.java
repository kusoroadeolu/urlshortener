package com.personal.urlshortener.service;

import com.personal.urlshortener.exception.UserAgentNotFoundException;
import com.personal.urlshortener.models.IpInfo;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua_parser.Client;
import ua_parser.Parser;

import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
public class IpInfoExtractor {

    private final Parser uaParser;


    public String extractUserAgent(HttpServletRequest request){
        return request.getHeader("User-Agent");
    }


    //Get a client's ip address
    public String getClientIpAddress(HttpServletRequest request){
            String forwarded = request.getHeader("X-Forwarded-For");

            //Get the first ip in the list
            if(forwarded != null && !forwarded.isEmpty()){
                System.out.println("Forwarded: " + forwarded);
               return forwarded.split(",")[0];
            }

            return request.getRemoteAddr();
    }

    public String getReferer(HttpServletRequest request){
        String referer = request.getHeader("Referrer");
        if(referer != null && !referer.isEmpty()){
            return referer;
        }
        Logger.getLogger(IpInfoExtractor.class.getName()).info("Referrer not found!");
        return "";
    }

    public String getCountry(String ipAddress){
        return "Nigeria";
    }

    public String getDeviceType(String userAgent){
//            Client client = uaParser.parse(userAgent);
//            return client.device.family;

        userAgent = userAgent.toLowerCase();

        if (userAgent.contains("mobile")) {
            return "Mobile";
        } else if (userAgent.contains("tablet")) {
            return "Tablet";
        } else if (userAgent.contains("smarttv") || userAgent.contains("smart-tv")) {
            return "SmartTV";
        } else if (userAgent.contains("console")) {
            return "Console";
        } else {
            return "Desktop"; // Default if none of the above match
        }
    }

    public String getBrowser(String userAgent){
            Client client = uaParser.parse(userAgent);
            return client.userAgent.family;

    }


    public String getUserOs(String userAgent){
        Client client = uaParser.parse(userAgent);
        return client.os.family;
    }


    public Boolean getBotStatus(HttpServletRequest request){
        return (Boolean)request.getAttribute("isBot");
    }

    //Get the necessary ip info from the user when they send a request to the server
    public IpInfo extractIpInfo(HttpServletRequest request){
        String userAgent = extractUserAgent(request);
        if(userAgent == null || userAgent.isEmpty()){
          throw new UserAgentNotFoundException("No Valid User Agent Found!");
        }

        String ipAddress = getClientIpAddress(request);
        String referrer = getReferer(request);
        String country = getCountry(ipAddress);
        String deviceType = getDeviceType(userAgent);
        String browser = getBrowser(userAgent);
        String os = getUserOs(userAgent);
        Boolean isBot = getBotStatus(request);

        return new IpInfo(
                ipAddress,
                referrer,
                country,
                deviceType,
                browser,
                os,
                isBot != null && isBot
        );
    }



}
