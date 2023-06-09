package net.fishlulu.tutorialmod.networking.packet;

import net.fishlulu.tutorialmod.networking.ModMessages;
import net.fishlulu.tutorialmod.thirst.PlayerThirstProvider;
import net.minecraft.ChatFormatting;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.client.event.sound.SoundEvent;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class DrinkWaterC2SPacket {
    private static final String MESSAGE_DRINK_WATER = "message.tutorialmod.drink_water";
    private static final String MESSAGE_NO_WATER = "message.tutorialmod.no_water";

    public DrinkWaterC2SPacket()
    {

    }
    public DrinkWaterC2SPacket(FriendlyByteBuf buf)
    {

    }
    public void toBytes(FriendlyByteBuf buf)
    {

    }
    public boolean handle(Supplier<NetworkEvent.Context> supplier)
    {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(()->{
            //HERE WE ARE ON SERVER
            //check if player is near water
            ServerPlayer player = context.getSender();
            ServerLevel level = player.getLevel();
            if(hasWaterAround(player,level,2))
            {
                // Notify the player that water has benn drunk
                //increase the water level /thirst level of player
                player.sendSystemMessage(Component.translatable(MESSAGE_DRINK_WATER).withStyle(ChatFormatting.AQUA));
                level.playSound(null,player.getOnPos(), SoundEvents.GENERIC_DRINK, SoundSource.PLAYERS,0.5F,level.random.nextFloat()*0.1F+0.9F);

                //increase the water level / thirst level of player
                player.getCapability(PlayerThirstProvider.PLAYER_THIRST).ifPresent(
                        thirst->{thirst.addThirst(10);
                        player.sendSystemMessage(Component.literal("Current Thirst level "+thirst.getThirst()).withStyle(ChatFormatting.AQUA));
                            ModMessages.sendToPlayer(new ThirstDataSyncS2CPacket(thirst.getThirst()),player);
                        }

                );
                //output the current level



            }
            else {
                //notify there's no water around
                //output the current thirst level
                player.sendSystemMessage(Component.translatable(MESSAGE_NO_WATER).withStyle(ChatFormatting.RED));
                player.getCapability(PlayerThirstProvider.PLAYER_THIRST).ifPresent(
                        thirst->{
                            player.sendSystemMessage(Component.literal("Current Thirst level "+thirst.getThirst()).withStyle(ChatFormatting.AQUA));
                            ModMessages.sendToPlayer(new ThirstDataSyncS2CPacket(thirst.getThirst()),player);
                        }
                );
            }

        });
        return true;
    }

    private boolean hasWaterAround(ServerPlayer player, ServerLevel level,int size) {
        return level.getBlockStates(player.getBoundingBox().inflate(size)).filter(state->state.is(Blocks.WATER)).toArray().length>0;
    }
}
