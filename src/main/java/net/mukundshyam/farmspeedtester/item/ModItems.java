package net.mukundshyam.farmspeedtester.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.mukundshyam.farmspeedtester.FarmSpeedTester;
import net.mukundshyam.farmspeedtester.item.custom.TesterItem;

public class ModItems {
    public static final Item Tester = registerItem("tester", new TesterItem(new FabricItemSettings()));

    private static void addItemsToIngredientItemGroup(FabricItemGroupEntries entries) {
        entries.add(Tester);
    }
    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, new Identifier(FarmSpeedTester.MOD_ID, name), item);
    }

    public static void registerModItems() {
        FarmSpeedTester.LOGGER.info("Registering Mod items for" + FarmSpeedTester.MOD_ID);

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS).register(ModItems::addItemsToIngredientItemGroup);
    }
}
