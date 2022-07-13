package com.growuphappily.theseus.world;

import com.growuphappily.theseus.Theseus;
import com.growuphappily.theseus.util.SerializeUtil;
import com.growuphappily.theseus.world.player.Player;
import com.growuphappily.theseus.world.time.Time;
import net.minecraft.nbt.ByteArrayNBT;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.world.World;
import net.minecraft.world.storage.WorldSavedData;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.LogManager;

import java.io.IOException;
import java.util.ArrayList;

@Mod.EventBusSubscriber(modid = Theseus.MOD_ID)
public class WorldData extends WorldSavedData {
    public long lastSave = 0;
    public static int SAVE_DELAY = 60;
    public static final String NAME = "TheseusWorldData";
    public Time time;
    public ArrayList<Player> playerList;
    public WorldData() {
        super(NAME);
    }

    @Override
    public void load(CompoundNBT nbt) {
        playerList = new ArrayList<>();
        ListNBT playerListNBT = (ListNBT) nbt.get("PlayerData");
        if (playerListNBT != null) {
            playerListNBT.forEach((pnbt)->{
                ByteArrayNBT bnbt = (ByteArrayNBT)pnbt;
                try {
                    playerList.add((Player) SerializeUtil.unserialize(bnbt.getAsByteArray()));
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
            });
        }
        try {
            time = (Time) SerializeUtil.unserialize(nbt.getByteArray("TimeData"));
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public CompoundNBT save(CompoundNBT nbt) {
        ListNBT playerListNBT = new ListNBT();
        this.playerList
                .forEach((p)->{
                    try {
                        ByteArrayNBT cnbt = new ByteArrayNBT(SerializeUtil.serialize(p));
                        playerListNBT.add(cnbt);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
        nbt.put("PlayerData", playerListNBT);
        try {
            ByteArrayNBT timeNBT = new ByteArrayNBT(SerializeUtil.serialize(time));
            nbt.put("TimeData", timeNBT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return nbt;
    }

    public void addPlayer(Player p){
        if(!playerList.contains(p)){
            playerList.add(p);
        }
        setDirty();
    }

    public static WorldData get(World world){
        if(world.isClientSide){
            throw new RuntimeException("Attempt to get world data in a client world");
        }
        WorldData worldData = world.getServer().getLevel(World.OVERWORLD).getDataStorage().get(WorldData::new,NAME);
        if(worldData == null){
            LogManager.getLogger().info("World Init");
            WorldData data = new WorldData();
            data.time = new Time();
            data.playerList = new ArrayList<>();
            data.setDirty();
            world.getServer().getLevel(World.OVERWORLD).getDataStorage().set(data);
            world.getServer().getLevel(World.OVERWORLD).getDataStorage().save();
            return data;
        }else{
            return worldData;
        }
    }

    @SubscribeEvent
    public static void tick(TickEvent.WorldTickEvent event){
        WorldData data = get(event.world);
        if(data.lastSave + (long)SAVE_DELAY * (long)1000 <= System.currentTimeMillis()){
            data.setDirty();
            data.lastSave = System.currentTimeMillis();
        }
    }
}
