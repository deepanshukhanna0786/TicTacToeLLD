package common.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import common.constants.Constants;
import model.Board;
import model.GameStateData;
import model.Player;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class GameStateManager {

    private final ObjectMapper objectMapper = new ObjectMapper();

    public void saveGameState(Board board) {
        try (FileWriter writer = new FileWriter(Constants.FILE_PATH, false)) {
            Map<String, Object> player1Data = new HashMap<>();
            player1Data.put("name", board.getPlayer1().getName());
            player1Data.put("mark", board.getPlayer1().getMark());
            player1Data.put("playerType", board.getPlayer1().getPlayerType());

            Map<String, Object> player2Data = new HashMap<>();
            player2Data.put("name", board.getPlayer2().getName());
            player2Data.put("mark", board.getPlayer2().getMark());
            player2Data.put("playerType", board.getPlayer2().getPlayerType());

            Map<String, Object> currentPlayerToStore = new HashMap<>();
            Player currentPlayer = board.getCurrentPlayer();
            Player oppositePlayer = (currentPlayer == board.getPlayer1()) ? board.getPlayer2() : board.getPlayer1();

            currentPlayerToStore.put("name", oppositePlayer.getName());
            currentPlayerToStore.put("mark", oppositePlayer.getMark());
            currentPlayerToStore.put("playerType", oppositePlayer.getPlayerType());

            Map<String, Object> gameStateToSave = new HashMap<>();
            gameStateToSave.put("board", board.getBoard());
            gameStateToSave.put("player1", player1Data);
            gameStateToSave.put("player2", player2Data);
            gameStateToSave.put("currentPlayer", currentPlayerToStore);
            gameStateToSave.put("gameOver", board.isGameOver());

            String jsonString = objectMapper.writeValueAsString(gameStateToSave);
            writer.write(jsonString);
            System.out.println("Successfully saved game state");

        } catch (IOException e) {
            System.err.println("An error occurred while saving game state: " + e.getMessage());
        }
    }

    public GameStateData loadGameState() {
        File file = new File(Constants.FILE_PATH);
        if (!file.exists() || file.length() == 0) {
            return new GameStateData();
        }
        try (FileReader reader = new FileReader(Constants.FILE_PATH)) {
            StringBuilder jsonStringBuilder = new StringBuilder();
            int character;
            while ((character = reader.read()) != -1) {
                jsonStringBuilder.append((char) character);
            }
            String jsonString = jsonStringBuilder.toString();
            return objectMapper.readValue(jsonString, GameStateData.class);
        } catch (IOException e) {
            System.err.println("An error occurred while loading game state: " + e.getMessage());
        }
        return new GameStateData();
    }

    public void clearGameState() {
        try (FileWriter writer = new FileWriter(Constants.FILE_PATH, false)) {
            writer.write("");
            System.out.println("Game state file cleared.");
        } catch (IOException e) {
            System.err.println("An error occurred while clearing game state file: " + e.getMessage());
        }
    }
}
