package net.mukundshyam.farmspeedtester.item.custom;

import net.minecraft.block.entity.BarrelBlockEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.block.entity.EnderChestBlockEntity;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class TesterItem extends Item {
    public TesterItem(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context)  {
        if (!context.getWorld().isClient()){
            BlockPos positionClicked = context.getBlockPos();
            PlayerEntity player = context.getPlayer();
            BlockEntity type = context.getWorld().getBlockEntity(positionClicked);
            assert player != null;
            if (type instanceof ChestBlockEntity) {
                int total1 = 0;
                final int[] total2 = {0};
                for (int num = 0; num <= 26; num++) {
                    int count = getChestStacks((ChestBlockEntity) type, num);
                    total1 = total1 + count;
                }
                final String[] totalOne = {String.valueOf(total1)};
                player.sendMessage(Text.literal("Counting... Total items = " + totalOne[0]), true);
                Timer timer = new Timer();
                int finalTotal = total1;
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        for (int num = 0; num <= 26; num++) {
                            int count = getChestStacks((ChestBlockEntity) type, num);
                            total2[0] = total2[0] + count;
                        }
                        String totalTwo = String.valueOf(total2[0]);
                        player.sendMessage(Text.literal("Counting... Total items = " + totalTwo), true);
                        int speed = (total2[0] - finalTotal)* (60/5);
                        String spd  = String.valueOf(speed);
                        player.sendMessage(Text.literal(spd + " items per hour") , false);
                    }

                }, 300000);
            }
            else if (type instanceof BarrelBlockEntity) {
                int total1 = 0;
                final int[] total2 = {0};
                for (int num = 0; num <= 26; num++) {
                    int count = getBarrelStacks((BarrelBlockEntity) type, num);
                    total1 = total1 + count;
                }
                final String[] totalOne = {String.valueOf(total1)};
                player.sendMessage(Text.literal("Counting... Total items = " + totalOne[0]), true);
                Timer timer = new Timer();
                int finalTotal = total1;
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        for (int num = 0; num <= 26; num++) {
                            int count = getBarrelStacks((BarrelBlockEntity) type, num);
                            total2[0] = total2[0] + count;
                        }
                        String totalTwo = String.valueOf(total2[0]);
                        player.sendMessage(Text.literal("Counting... Total items = " + totalTwo), true);
                        int speed = (total2[0] - finalTotal)* (60/5);
                        String spd  = String.valueOf(speed);
                        player.sendMessage(Text.literal("Speed: " + spd + " items per hour") , true);
                    }

                }, 300000);

            }
            else if (type instanceof EnderChestBlockEntity) {
                player.sendMessage(Text.literal("Doesn't work with ender chests."), true);
            }
            else {
                player.sendMessage(Text.literal("Doesn't work with this block."), true);
            }
        }
        return ActionResult.SUCCESS;
    }
    public int getChestStacks(ChestBlockEntity chest, int i){
        ItemStack itemStack = chest.getStack(i);
        return itemStack.getCount();
    }
    public int getBarrelStacks(BarrelBlockEntity barrel, int i){
        ItemStack itemStack = barrel.getStack(i);
        return itemStack.getCount();
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        tooltip.add(Text.translatable("tooltip.farm-speed-tester.tester.tooltip"));
        super.appendTooltip(stack, world, tooltip, context);
    }
}
