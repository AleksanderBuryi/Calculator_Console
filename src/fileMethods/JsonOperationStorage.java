package fileMethods;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import entity.Operation;
import interfaces.OperationStorage;

import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.List;

public class JsonOperationStorage implements OperationStorage {
    private final String path = "src/history.json";
    private final Gson gson = new GsonBuilder()
            .registerTypeAdapter(LocalDateTime.class, (JsonSerializer<LocalDateTime>) (value, type, jsonContext) ->
                            new JsonPrimitive(value.format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM))))
            .registerTypeAdapter(LocalDateTime.class, (JsonDeserializer<LocalDateTime>) (jsonElement, type, context) ->
                    LocalDateTime.parse(jsonElement.getAsJsonPrimitive().getAsString(), DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM))
            )
            .create();
    private final Type jsonListTypeToken = new TypeToken<List<Operation>>(){}.getType();

    @Override
    public void save(Operation operation) {
        List<Operation> fullHistory = findAll();
        if (fullHistory == null)
            fullHistory = new ArrayList<>();
        fullHistory.add(operation);
        String jsonData = gson.toJson(fullHistory);
        try (FileWriter fileWriter = new FileWriter(path, false)) {
            fileWriter.write(jsonData);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Operation> findAll() {
        String jsonOperationData;
        try {
            jsonOperationData = Files.readString(Path.of(path));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return gson.fromJson(jsonOperationData, jsonListTypeToken);
    }
}
