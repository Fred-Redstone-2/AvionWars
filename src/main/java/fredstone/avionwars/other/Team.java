package fredstone.avionwars.other;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;
import java.util.List;

public class Team {
    protected List<EntityPlayer> players = new ArrayList<>();
    public BlockPos spawnpoint = new BlockPos(0, 80, 0);
    public int nbDeaths = 0, nbPoints = 0;
    public String name;

    public Team(String name, String spawnCoord) {
        String[] coords = spawnCoord.split(",");
        try {
            int x = Integer.parseInt(coords[0]);
            int y = Integer.parseInt(coords[1]);
            int z = Integer.parseInt(coords[2]);
            this.spawnpoint = new BlockPos(x, y, z);
        }
        catch (NumberFormatException e) {
            System.out.println("Team spawnpoint coordinate is invalid");
        }
        this.name = name;
    }

    public void reset() {
        this.nbDeaths = 0;
        this.nbPoints = 0;
        this.players.clear();
    }

    public void setSpawnpoint(double x, double y, double z) {
        spawnpoint = new BlockPos(x, y, z);
    }

    public void addPlayer(EntityPlayer player) {
        players.add(player);
    }

    public void removePlayer(EntityPlayer player) {
        players.remove(player);
    }

    public void addDeath() {
        nbDeaths++;
    }

    public void addPoint() {
        nbPoints++;
    }

    public List<EntityPlayer> getPlayers() {
        return players;
    }
}
