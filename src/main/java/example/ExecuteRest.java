package example;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

public class ExecuteRest {

    public static String URL = "http://api.openweathermap.org/data/2.5/weather?q=_,es&appid=a234cc8068f26d1541e82e5e98667a99";

    /**
     * Consulta el servicio API REST para hacer un GET de los datos atmosféricos
     * y cargar la colección de objetos City para su posterior carga en la UI.
     *
     * @param cities Un array de String con los nombres de las ciudades a
     * consultar.
     * @return Un número entero igual al número de objetos City instanciados y
     * cargados en la colección.
     */
    public static int execute(String[] cities) {
        RestTemplate restTemplate = new RestTemplate();
        int count = 0;
        for (String c : cities) {
            String newURL = URL.replace("_", c);
            try {
                ResponseEntity result = restTemplate.getForEntity(newURL, String.class);
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode node = objectMapper.readTree(result.getBody().toString()).get("main");
                City.addCity(new City(c, node.get("temp").floatValue(), node.get("humidity").asInt(), node.get("pressure").asInt(), node.get("temp_min").floatValue(), node.get("temp_max").floatValue()));
                count++;
            } catch (HttpClientErrorException e) {
                System.out.println("ERROR: No se encontró el recurso solicitado.");
            } catch (HttpServerErrorException e) {
                System.out.println("ERROR: El servidor no responde.");
            } catch (IOException ex) {
                System.out.println("ERROR: Error en la lectura del nodo JSON.");
            }
        }
        return count;
    }
}
