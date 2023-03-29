package net.fishlulu.tutorialmod.event;

import net.fishlulu.tutorialmod.TutorialMod;
import net.fishlulu.tutorialmod.networking.ModMessages;
import net.fishlulu.tutorialmod.networking.packet.DrinkWaterC2SPacket;
import net.fishlulu.tutorialmod.networking.packet.ExampleC2SPacket;
import net.fishlulu.tutorialmod.util.KeyBinding;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

public class ClientEvents {
    @Mod.EventBusSubscriber(modid= TutorialMod.MOD_ID,value = Dist.CLIENT)
    public static class ClientForgeEvents
    {

        @SubscribeEvent
        public static void onKeyInput(InputEvent.Key event)
        {
            if(KeyBinding.DRINKING_KEY.consumeClick())
            {
//                Minecraft.getInstance().player.sendSystemMessage(Component.literal("Press a Key"));
//                ModMessages.sendToServer(new ExampleC2SPacket());//C2S
                ModMessages.sendToServer(new DrinkWaterC2SPacket());

            }

        }
    }
    @Mod.EventBusSubscriber(modid= TutorialMod.MOD_ID,value = Dist.CLIENT,bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ClientModBusEvents
    {
        @SubscribeEvent
        public static void onKeyRegister(RegisterKeyMappingsEvent event)
        {
            event.register(KeyBinding.DRINKING_KEY);
        }

    }
}
