package lykrast.prodigytech.common.gui;

import lykrast.prodigytech.client.gui.*;
import lykrast.prodigytech.common.tileentity.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class ProdigyTechGuiHandler implements IGuiHandler {
	public static final int EXPLOSION_FURNACE = 0, AEROHEATER_SOLID = 1, INCINERATOR = 2, BLOWER_FURNACE = 3,
			ROTARY_GRINDER = 4, SOLDERER = 5, MAGNETIC_REASSEMBLER = 6, AEROHEATER_ENERGION = 7, EXTRACTOR = 8,
			HEAT_SAWMILL = 9, ATOMIC_RESHAPER = 10, PRIMORDIALIS_REACTOR = 12, AEROHEATER_TARTARIC = 13,
			CRYSTAL_CUTTER = 14, ZORRA_ALTAR = 16, ORE_REFINERY = 17, AEROHEATER_CAPACITOR = 11, CAPACITOR_CHARGER = 15,
			FUEL_PROCSESOR = 18;

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		switch (ID) {
			case EXPLOSION_FURNACE:
				return new ContainerExplosionFurnace(player.inventory, (TileExplosionFurnace)world.getTileEntity(new BlockPos(x, y, z)));
			case AEROHEATER_SOLID:
				return new ContainerAeroheaterSolid(player.inventory, (TileAeroheaterSolid)world.getTileEntity(new BlockPos(x, y, z)));
			case INCINERATOR:
				return new ContainerIncinerator(player.inventory, (TileIncinerator)world.getTileEntity(new BlockPos(x, y, z)));
			case BLOWER_FURNACE:
				return new ContainerBlowerFurnace(player.inventory, (TileBlowerFurnace)world.getTileEntity(new BlockPos(x, y, z)));
			case ROTARY_GRINDER:
				return new ContainerRotaryGrinder(player.inventory, (TileRotaryGrinder)world.getTileEntity(new BlockPos(x, y, z)));
			case SOLDERER:
				return new ContainerSolderer(player.inventory, (TileSolderer)world.getTileEntity(new BlockPos(x, y, z)));
			case MAGNETIC_REASSEMBLER:
				return new ContainerMagneticReassembler(player.inventory, (TileMagneticReassembler)world.getTileEntity(new BlockPos(x, y, z)));
			case AEROHEATER_ENERGION:
				return new ContainerAeroheaterEnergion(player.inventory, (TileAeroheaterEnergion)world.getTileEntity(new BlockPos(x, y, z)));
			case EXTRACTOR:
				return new ContainerExtractor(player.inventory, (TileExtractor)world.getTileEntity(new BlockPos(x, y, z)));
			case HEAT_SAWMILL:
				return new ContainerHeatSawmill(player.inventory, (TileHeatSawmill)world.getTileEntity(new BlockPos(x, y, z)));
			case ATOMIC_RESHAPER:
				return new ContainerAtomicReshaper(player.inventory, (TileAtomicReshaper)world.getTileEntity(new BlockPos(x, y, z)));
			case PRIMORDIALIS_REACTOR:
				return new ContainerPrimordialisReactor(player.inventory, (TilePrimordialisReactor)world.getTileEntity(new BlockPos(x, y, z)));
			case AEROHEATER_TARTARIC:
				return new ContainerAeroheaterTartaric(player.inventory, (TileAeroheaterTartaric)world.getTileEntity(new BlockPos(x, y, z)));
			case CRYSTAL_CUTTER:
				return new ContainerCrystalCutter(player.inventory, (TileCrystalCutter)world.getTileEntity(new BlockPos(x, y, z)));
			case ZORRA_ALTAR:
				return new ContainerZorraAltar(player.inventory, world, new BlockPos(x, y, z));
			case ORE_REFINERY:
				return new ContainerOreRefinery(player.inventory, (TileOreRefinery)world.getTileEntity(new BlockPos(x, y, z)));
			case AEROHEATER_CAPACITOR:
				return new ContainerAeroheaterCapacitor(player.inventory, (TileAeroheaterCapacitor)world.getTileEntity(new BlockPos(x, y, z)));
			case CAPACITOR_CHARGER:
				return new ContainerCapacitorCharger(player.inventory, (TileCapacitorCharger)world.getTileEntity(new BlockPos(x, y, z)));
			case FUEL_PROCSESOR:
				return new ContainerFuelProcessor(player.inventory, (TileFuelProcessor)world.getTileEntity(new BlockPos(x, y, z)));
		}
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		switch (ID) {
			case EXPLOSION_FURNACE:
				return new GuiExplosionFurnace(player.inventory, (TileExplosionFurnace)world.getTileEntity(new BlockPos(x, y, z)));
			case AEROHEATER_SOLID:
				return new GuiAeroheaterSolid(player.inventory, (TileAeroheaterSolid)world.getTileEntity(new BlockPos(x, y, z)));
			case INCINERATOR:
				return new GuiIncinerator(player.inventory, (TileIncinerator)world.getTileEntity(new BlockPos(x, y, z)));
			case BLOWER_FURNACE:
				return new GuiBlowerFurnace(player.inventory, (TileBlowerFurnace)world.getTileEntity(new BlockPos(x, y, z)));
			case ROTARY_GRINDER:
				return new GuiRotaryGrinder(player.inventory, (TileRotaryGrinder)world.getTileEntity(new BlockPos(x, y, z)));
			case SOLDERER:
				return new GuiSolderer(player.inventory, (TileSolderer)world.getTileEntity(new BlockPos(x, y, z)));
			case MAGNETIC_REASSEMBLER:
				return new GuiMagneticReassembler(player.inventory, (TileMagneticReassembler)world.getTileEntity(new BlockPos(x, y, z)));
			case AEROHEATER_ENERGION:
				return new GuiAeroheaterEnergion(player.inventory, (TileAeroheaterEnergion)world.getTileEntity(new BlockPos(x, y, z)));
			case EXTRACTOR:
				return new GuiExtractor(player.inventory, (TileExtractor)world.getTileEntity(new BlockPos(x, y, z)));
			case HEAT_SAWMILL:
				return new GuiHeatSawmill(player.inventory, (TileHeatSawmill)world.getTileEntity(new BlockPos(x, y, z)));
			case ATOMIC_RESHAPER:
				return new GuiAtomicReshaper(player.inventory, (TileAtomicReshaper)world.getTileEntity(new BlockPos(x, y, z)));
			case PRIMORDIALIS_REACTOR:
				return new GuiPrimordialisReactor(player.inventory, (TilePrimordialisReactor)world.getTileEntity(new BlockPos(x, y, z)));
			case AEROHEATER_TARTARIC:
				return new GuiAeroheaterTartaric(player.inventory, (TileAeroheaterTartaric)world.getTileEntity(new BlockPos(x, y, z)));
			case CRYSTAL_CUTTER:
				return new GuiCrystalCutter(player.inventory, (TileCrystalCutter)world.getTileEntity(new BlockPos(x, y, z)));
			case ZORRA_ALTAR:
				return new GuiZorraAltar(player.inventory, world);
			case ORE_REFINERY:
				return new GuiOreRefinery(player.inventory, (TileOreRefinery)world.getTileEntity(new BlockPos(x, y, z)));
			case AEROHEATER_CAPACITOR:
				return new GuiAeroheaterCapacitor(player.inventory, (TileAeroheaterCapacitor)world.getTileEntity(new BlockPos(x, y, z)));
			case CAPACITOR_CHARGER:
				return new GuiCapacitorCharger(player.inventory, (TileCapacitorCharger)world.getTileEntity(new BlockPos(x, y, z)));
			case FUEL_PROCSESOR:
				return new GuiFuelProcessor(player.inventory, (TileFuelProcessor)world.getTileEntity(new BlockPos(x, y, z)));
		}
		return null;
	}

}
