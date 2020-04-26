package dbservice.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import dbservice.exceptions.InvalidJSONException;
import org.apache.commons.lang.StringUtils;

import java.io.IOException;
import java.util.Optional;

public class JsonUtil {

    public static<T> Optional<T> convertFromString(String jsonString, Class<T> c) throws InvalidJSONException{
        try {
            if(StringUtils.isBlank(jsonString)){
                return Optional.empty();
            }
            return Optional.of(new ObjectMapper().readValue(jsonString,c));
        }catch (IOException e){
            throw new InvalidJSONException("Invalid Json");
        }
    }
}
