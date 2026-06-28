package com.capstone.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;

@Component
public class JwtUtil {

    private static final String HMAC_ALGORITHM = "HmacSHA256";

    @Value("${app.jwt.secret}")
    private String secret;

    @Value("${app.jwt.expiration-ms}")
    private Long expirationMs;

    public String generateToken(String email) {
        long now = System.currentTimeMillis();
        long expiry = now + expirationMs;

        String headerJson = "{\"alg\":\"HS256\",\"typ\":\"JWT\"}";
        String payloadJson = "{\"sub\":\"" + email + "\",\"iat\":" + now + ",\"exp\":" + expiry + "}";

        String header = base64UrlEncode(headerJson);
        String payload = base64UrlEncode(payloadJson);
        String signature = sign(header + "." + payload);

        return header + "." + payload + "." + signature;
    }

    public String getEmailFromToken(String token) {
        String payloadJson = decodePayload(token);
        return getJsonValue(payloadJson, "sub");
    }

    public boolean validateToken(String token) {
        try {
            String[] parts = token.split("\\.");

            if (parts.length != 3) {
                return false;
            }

            String expectedSignature = sign(parts[0] + "." + parts[1]);

            if (!expectedSignature.equals(parts[2])) {
                return false;
            }

            String payloadJson = decodePayload(token);
            String expiryValue = getJsonValue(payloadJson, "exp");
            long expiryTime = Long.parseLong(expiryValue);

            return new Date().getTime() < expiryTime;
        } catch (Exception exception) {
            return false;
        }
    }

    private String decodePayload(String token) {
        String[] parts = token.split("\\.");
        byte[] decodedBytes = Base64.getUrlDecoder().decode(parts[1]);
        return new String(decodedBytes, StandardCharsets.UTF_8);
    }

    private String base64UrlEncode(String value) {
        return Base64.getUrlEncoder()
                .withoutPadding()
                .encodeToString(value.getBytes(StandardCharsets.UTF_8));
    }

    private String sign(String data) {
        try {
            Mac mac = Mac.getInstance(HMAC_ALGORITHM);
            SecretKeySpec secretKeySpec = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), HMAC_ALGORITHM);
            mac.init(secretKeySpec);
            byte[] signatureBytes = mac.doFinal(data.getBytes(StandardCharsets.UTF_8));
            return Base64.getUrlEncoder().withoutPadding().encodeToString(signatureBytes);
        } catch (Exception exception) {
            throw new IllegalArgumentException("Unable to create JWT token");
        }
    }

    private String getJsonValue(String json, String key) {
        String searchText = "\"" + key + "\":";
        int keyStart = json.indexOf(searchText);

        if (keyStart == -1) {
            return "";
        }

        int valueStart = keyStart + searchText.length();

        if (json.charAt(valueStart) == '"') {
            int stringStart = valueStart + 1;
            int stringEnd = json.indexOf("\"", stringStart);
            return json.substring(stringStart, stringEnd);
        }

        int valueEnd = json.indexOf(",", valueStart);

        if (valueEnd == -1) {
            valueEnd = json.indexOf("}", valueStart);
        }

        return json.substring(valueStart, valueEnd);
    }
}
