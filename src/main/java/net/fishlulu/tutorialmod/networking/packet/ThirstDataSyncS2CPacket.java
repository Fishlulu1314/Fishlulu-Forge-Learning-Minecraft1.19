package net.fishlulu.tutorialmod.networking.packet;

import net.fishlulu.tutorialmod.client.ClientThirstData;
import net.fishlulu.tutorialmod.thirst.PlayerThirstProvider;
import net.minecraft.ChatFormatting;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.ForgeConfig;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class ThirstDataSyncS2CPacket {
    private final int thirst;
    public ThirstDataSyncS2CPacket(int thirst)
    {
        this.thirst = thirst;

    }
//    private static final String MESSAGE_DRINK_WATER = "message.tutorialmod.drink_water";
//    private static final String MESSAGE_NO_WATER = "message.tutorialmod.no_water";


    public ThirstDataSyncS2CPacket(FriendlyByteBuf buf)
    {
        this.thirst = buf.readInt();
    }
    public void toBytes(FriendlyByteBuf buf)
    {
        buf.writeInt((thirst));
    }
    public boolean handle(Supplier<NetworkEvent.Context> supplier)
    {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(()->{
            //HERE WE ARE ON THE CLIENT
            ClientThirstData.set(thirst);
            });

        return true;
    }

}

