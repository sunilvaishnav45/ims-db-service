package dbservice.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import dbservice.controller.ProductController;
import dbservice.exceptions.InvalidJSONException;
import org.apache.commons.lang.StringUtils;
import org.jboss.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Optional;
import java.util.stream.Collectors;

public class JsonUtil {

    private static final Logger LOGGER = Logger.getLogger(JsonUtil.class);

    public static<T> Optional<T> convertFromString(String jsonString, Class<T> c) throws InvalidJSONException{
        try {
            LOGGER.info(jsonString);
            if(StringUtils.isBlank(jsonString)){
                return Optional.empty();
            }
            return Optional.of(new ObjectMapper().readValue(jsonString,c));
        }catch (IOException e){
            throw new InvalidJSONException("Invalid Json");
        }
    }

    public static String getRequestBody(HttpServletRequest request) throws IOException{
        return request.getReader().lines().collect(Collectors.joining());
    }
}
