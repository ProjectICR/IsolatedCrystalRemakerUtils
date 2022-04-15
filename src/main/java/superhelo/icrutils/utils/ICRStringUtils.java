package superhelo.icrutils.utils;

import java.util.Arrays;
import org.apache.commons.lang3.StringUtils;

public class ICRStringUtils {

    public static String toUpperCamelCase(String str) {
        StringBuilder result = new StringBuilder();
        if (StringUtils.isBlank(str)) {
            return str;
        } else if (!str.contains("_")) {
            return str.substring(0, 1).toUpperCase() + str.substring(1);
        }

        Arrays.stream(str.split("_")).forEach(camel -> {
            result.append(camel.substring(0, 1).toUpperCase());
            result.append(camel.substring(1).toLowerCase());
        });

        return result.toString();
    }

}
