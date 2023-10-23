package dev.stevim.NBA;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class PlayerService {
    @Autowired
    private PlayerRepository playerRepository;
    @Autowired
    private MongoTemplate mongoTemplate;
    public List<Player> allPlayers() {
        return playerRepository.findAll();
    }
    public Optional<List<Player>> playerFirstName(String firstName) {
        return playerRepository.findAllByFirstNameContaining(firstName);
    }
    public Optional<Player> getPlayer(Integer playerId) {
        return playerRepository.findByPlayerId(playerId);
    }
    public Player savePlayer(Player player) {
        return this.playerRepository.save(player);
    }
    public void createPlayer(Player player) {
        Optional<Player> playerOptional = playerRepository
                .findByPlayerId(player.getPlayerId());
        if (playerOptional.isPresent()) {
            throw new IllegalStateException("playerId taken");
        }
        playerRepository.save(player);
    }
    public void deletePlayer(Integer playerId) {
        boolean exists = playerRepository.existsByPlayerId(playerId);
        if (!exists) {
            throw new IllegalStateException(("Player Id: " + playerId + " does not exist"));
        }
        playerRepository.deleteByPlayerId(playerId);
    }
//    public Player updatePlayer(Integer playerId, Player playerRequest) {
//        Optional<Player> existingPlayer = playerRepository.findByPlayerId(playerId);
//        existingPlayer.setTeamId(playerRequest.getTeamId());
//        return playerRepository.save(existingPlayer);
//    }

//    public Player updatePlayerByTeam(Integer playerId, Map<String, Object> fields) {
//        Optional<Player> existingPlayer = playerRepository.findByPlayerId(playerId);
//        if(existingPlayer.isPresent()) {
//            fields.forEach((key, value) -> {
//                Field field = ReflectionUtils.findField(Player.class, key);
//                field.setAccessible(true);
//                ReflectionUtils.setField(field, existingPlayer.get(), value);
//            });
//            return playerRepository.save(existingPlayer.get());
//        }
//        return null;
//    }
}
