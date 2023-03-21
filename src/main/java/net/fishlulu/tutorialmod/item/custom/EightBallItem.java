package net.fishlulu.tutorialmod.item.custom;

import net.minecraft.network.chat.Component;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import net.minecraft.world.level.Level;



public class EightBallItem extends Item {

    public EightBallItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {

        if(!level.isClientSide()&&hand== InteractionHand.MAIN_HAND)
        {
            //outpt a random number
            //set a cooldown
            outputRandomNumber(player);
            player.getCooldowns().addCooldown(this,20);

        }

        return super.use(level, player, hand);
    }
    private void outputRandomNumber(Player player)
    {
        player.sendSystemMessage(Component.literal("Your Number is "+getRandomNumber(10)));
    }
    private int getRandomNumber(int range)
    {
        return RandomSource.createNewThreadLocalInstance().nextInt(range);
    }
}
