package as.books.app.persistance.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;
import org.apache.commons.text.StringEscapeUtils;

import java.util.Set;
import java.util.UUID;

public class UUIDSetConverter implements AttributeConverter<Set<UUID>, String> {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private static final String ERROR_MESSAGE = "Error while converting uuids";
    private static final TypeReference<Set<UUID>> TYPE_REFERENCE = new TypeReference<>() {};

    @Override
    public String convertToDatabaseColumn(final Set<UUID> uuids) {
        try {
            return uuids == null ? null : OBJECT_MAPPER.writeValueAsString(uuids);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(ERROR_MESSAGE, e);
        }
    }

    @Override
    public Set<UUID> convertToEntityAttribute(final String s) {
        try {
            if (s == null) {
                return Set.of();
            } else {
                final String parsed = s.replaceAll("^\"|\"$", "");
                return OBJECT_MAPPER.readValue(StringEscapeUtils.unescapeJson(parsed), TYPE_REFERENCE);
            }
        } catch (JsonProcessingException e) {
            throw new RuntimeException(ERROR_MESSAGE, e);
        }
    }
}
