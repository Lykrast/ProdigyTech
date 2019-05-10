package lykrast.prodigytech.common.gui;

import java.util.List;
import java.util.Map;
import java.util.Random;

import lykrast.prodigytech.common.init.ModBlocks;
import lykrast.prodigytech.common.init.ModItems;
import lykrast.prodigytech.common.item.IZorrasteelEquipment;
import lykrast.prodigytech.common.recipe.ZorraAltarManager;
import lykrast.prodigytech.common.util.Config;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreDictionary;

public class ContainerZorraAltar extends Container {
	//Mostly copied from the Enchanting Table
	
    /** SlotEnchantmentTable object with ItemStack to be enchanted */
    public IInventory tableInventory;
    /** current world (for bookshelf counting) */
    private final World worldPointer;
    private final BlockPos position;
    private final Random rand;
    public int xpSeed;
    /** 3-member array storing the enchantment levels of each slot */
    public int[] enchantCost;
    public int[] enchantId;
    public int[] enchantLvl;

    @SideOnly(Side.CLIENT)
    public ContainerZorraAltar(InventoryPlayer playerInv, World worldIn)
    {
        this(playerInv, worldIn, BlockPos.ORIGIN);
    }

    public ContainerZorraAltar(InventoryPlayer playerInv, World worldIn, BlockPos pos)
    {
        this.tableInventory = new InventoryBasic("Enchant", true, 2)
        {
            /**
             * Returns the maximum stack size for a inventory slot. Seems to always be 64, possibly will be extended.
             */
            @Override
			public int getInventoryStackLimit()
            {
                return 64;
            }
            /**
             * For tile entities, ensures the chunk containing the tile entity is saved to disk later - the game won't
             * think it hasn't changed and skip it.
             */
            @Override
			public void markDirty()
            {
                super.markDirty();
                ContainerZorraAltar.this.onCraftMatrixChanged(this);
            }
        };
        this.rand = new Random();
        this.enchantCost = new int[3];
        this.enchantId = new int[] { -1, -1, -1};
        this.enchantLvl = new int[] { -1, -1, -1};
        this.worldPointer = worldIn;
        this.position = pos;
        this.xpSeed = playerInv.player.getXPSeed();
        this.addSlotToContainer(new Slot(this.tableInventory, 0, 15, 47)
        {
            /**
             * Check if the stack is allowed to be placed in this slot, used for armor slots as well as furnace fuel.
             */
            @Override
			public boolean isItemValid(ItemStack stack)
            {
                return true;
            }
            /**
             * Returns the maximum stack size for a given slot (usually the same as getInventoryStackLimit(), but 1 in
             * the case of armor slots)
             */
            @Override
			public int getSlotStackLimit()
            {
                return 1;
            }
        });
        this.addSlotToContainer(new Slot(this.tableInventory, 1, 35, 47)
        {
            List<ItemStack> ores = OreDictionary.getOres("leafZorra");
            /**
             * Check if the stack is allowed to be placed in this slot, used for armor slots as well as furnace fuel.
             */
            @Override
			public boolean isItemValid(ItemStack stack)
            {
                for (ItemStack ore : ores)
                    if (net.minecraftforge.oredict.OreDictionary.itemMatches(ore, stack, false)) return true;
                return false;
            }
        });

        for (int i = 0; i < 3; ++i)
        {
            for (int j = 0; j < 9; ++j)
            {
                this.addSlotToContainer(new Slot(playerInv, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }

        for (int k = 0; k < 9; ++k)
        {
            this.addSlotToContainer(new Slot(playerInv, k, 8 + k * 18, 142));
        }
    }

    protected void broadcastData(IContainerListener crafting)
    {
        crafting.sendWindowProperty(this, 0, this.enchantCost[0]);
        crafting.sendWindowProperty(this, 1, this.enchantCost[1]);
        crafting.sendWindowProperty(this, 2, this.enchantCost[2]);
        crafting.sendWindowProperty(this, 3, this.xpSeed & -16);
        crafting.sendWindowProperty(this, 4, this.enchantId[0]);
        crafting.sendWindowProperty(this, 5, this.enchantId[1]);
        crafting.sendWindowProperty(this, 6, this.enchantId[2]);
        crafting.sendWindowProperty(this, 7, this.enchantLvl[0]);
        crafting.sendWindowProperty(this, 8, this.enchantLvl[1]);
        crafting.sendWindowProperty(this, 9, this.enchantLvl[2]);
    }

    @Override
	public void addListener(IContainerListener listener)
    {
        super.addListener(listener);
        this.broadcastData(listener);
    }

    /**
     * Looks for changes made in the container, sends them to every listener.
     */
    @Override
	public void detectAndSendChanges()
    {
        super.detectAndSendChanges();

        for (int i = 0; i < this.listeners.size(); ++i)
        {
            IContainerListener icontainerlistener = this.listeners.get(i);
            this.broadcastData(icontainerlistener);
        }
    }

    @Override
	@SideOnly(Side.CLIENT)
    public void updateProgressBar(int id, int data)
    {
        if (id >= 0 && id <= 2)
        {
            this.enchantCost[id] = data;
        }
        else if (id == 3)
        {
            this.xpSeed = data;
        }
        else if (id >= 4 && id <= 6)
        {
            this.enchantId[id - 4] = data;
        }
        else if (id >= 7 && id <= 9)
        {
            this.enchantLvl[id - 7] = data;
        }
        else
        {
            super.updateProgressBar(id, data);
        }
    }

    /**
     * Callback for when the crafting matrix is changed.
     */
    @Override
	public void onCraftMatrixChanged(IInventory inventoryIn)
    {
        if (inventoryIn == tableInventory)
        {
            ItemStack target = inventoryIn.getStackInSlot(0);

            if (!target.isEmpty() && target.getItem() instanceof IZorrasteelEquipment)
            {
                if (!worldPointer.isRemote)
                {
                	ZorraAltarManager manager = ((IZorrasteelEquipment)target.getItem()).getManager();
                    rand.setSeed(xpSeed);
                	EnchantmentData[] enchants = manager.getRandomEnchants(target, rand);
                	
                	for (int i = 0; i < 3; i++)
                	{
                		if (enchants[i] == null)
                		{
                			enchantCost[i] = 0;
                			enchantId[i] = -1;
                			enchantLvl[i] = -1;
                		}
                		else
                		{
                			enchantCost[i] = manager.getRandomLevelCost(enchants[i], rand);
                			//3rd is hidden but costs less
                			if (i == 2) enchantCost[i] = Math.max(1, (int)(enchantCost[i] * Config.altarUnknownMult));
                            enchantId[i] = Enchantment.getEnchantmentID(enchants[i].enchantment);
                            enchantLvl[i] = enchants[i].enchantmentLevel;
                		}
                	}

                    detectAndSendChanges();
                }
            }
            else
            {
                for (int i = 0; i < 3; ++i)
                {
                    enchantCost[i] = 0;
                    enchantId[i] = -1;
                    enchantLvl[i] = -1;
                }
            }
        }
    }

    /**
     * Handles the given Button-click on the server, currently only used by enchanting. Name is for legacy.
     */
    @Override
	public boolean enchantItem(EntityPlayer playerIn, int id)
    {
        ItemStack target = this.tableInventory.getStackInSlot(0);
        ItemStack leaves = this.tableInventory.getStackInSlot(1);
        int leafCost = id == 2 ? 3 : enchantLvl[id];

        if ((leaves.isEmpty() || leaves.getCount() < leafCost) && !playerIn.capabilities.isCreativeMode)
        {
            return false;
        }
        else if (enchantCost[id] > 0 && !target.isEmpty() && (playerIn.experienceLevel >= enchantCost[id] && playerIn.experienceLevel >= enchantCost[id] || playerIn.capabilities.isCreativeMode))
        {
            if (!this.worldPointer.isRemote)
            {
                if (enchantId[id] != -1 && enchantLvl[id] != -1)
                {
                    playerIn.onEnchant(target, enchantCost[id]);
                    
                    Map<Enchantment,Integer> map = EnchantmentHelper.getEnchantments(target);
                    Enchantment toApply = Enchantment.getEnchantmentByID(enchantId[id]);
                    Integer lvl = map.get(toApply);
                    
                    if (lvl != null)
                    {
                    	map.put(toApply, enchantLvl[id]);
                    	EnchantmentHelper.setEnchantments(map, target);
                    }
                    else target.addEnchantment(toApply, enchantLvl[id]);

                    if (!playerIn.capabilities.isCreativeMode)
                    {
                        leaves.shrink(leafCost);

                        if (leaves.isEmpty())
                        {
                            this.tableInventory.setInventorySlotContents(1, ItemStack.EMPTY);
                        }
                    }

                    playerIn.addStat(StatList.ITEM_ENCHANTED);

                    if (playerIn instanceof EntityPlayerMP)
                    {
                        CriteriaTriggers.ENCHANTED_ITEM.trigger((EntityPlayerMP)playerIn, target, enchantCost[id]);
                    }

                    this.tableInventory.markDirty();
                    this.xpSeed = playerIn.getXPSeed();
                    this.onCraftMatrixChanged(this.tableInventory);
                    this.worldPointer.playSound((EntityPlayer)null, this.position, SoundEvents.BLOCK_ENCHANTMENT_TABLE_USE, SoundCategory.BLOCKS, 1.0F, this.worldPointer.rand.nextFloat() * 0.1F + 0.9F);
                }
            }

            return true;
        }
        else
        {
            return false;
        }
    }

    public int getLeafAmount()
    {
        ItemStack itemstack = tableInventory.getStackInSlot(1);
        return itemstack.isEmpty() ? 0 : itemstack.getCount();
    }

    /**
     * Called when the container is closed.
     */
    @Override
	public void onContainerClosed(EntityPlayer playerIn)
    {
        super.onContainerClosed(playerIn);

        if (!this.worldPointer.isRemote)
        {
            this.clearContainer(playerIn, playerIn.world, this.tableInventory);
        }
    }

    /**
     * Determines whether supplied player can use this container
     */
    @Override
	public boolean canInteractWith(EntityPlayer playerIn)
    {
        if (this.worldPointer.getBlockState(this.position).getBlock() != ModBlocks.zorraAltar)
        {
            return false;
        }
        else
        {
            return playerIn.getDistanceSq((double)this.position.getX() + 0.5D, (double)this.position.getY() + 0.5D, (double)this.position.getZ() + 0.5D) <= 64.0D;
        }
    }

    /**
     * Handle when the stack in slot {@code index} is shift-clicked. Normally this moves the stack between the player
     * inventory and the other inventory(s).
     */
    @Override
	public ItemStack transferStackInSlot(EntityPlayer playerIn, int index)
    {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.inventorySlots.get(index);

        if (slot != null && slot.getHasStack())
        {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();

            if (index == 0)
            {
                if (!this.mergeItemStack(itemstack1, 2, 38, true))
                {
                    return ItemStack.EMPTY;
                }
            }
            else if (index == 1)
            {
                if (!this.mergeItemStack(itemstack1, 2, 38, true))
                {
                    return ItemStack.EMPTY;
                }
            }
            else if (itemstack1.getItem() == ModItems.zorraLeaf)
            {
                if (!this.mergeItemStack(itemstack1, 1, 2, true))
                {
                    return ItemStack.EMPTY;
                }
            }
            else
            {
                if (((Slot)this.inventorySlots.get(0)).getHasStack() || !((Slot)this.inventorySlots.get(0)).isItemValid(itemstack1))
                {
                    return ItemStack.EMPTY;
                }

                if (itemstack1.hasTagCompound())// Forge: Fix MC-17431
                {
                    ((Slot)this.inventorySlots.get(0)).putStack(itemstack1.splitStack(1));
                }
                else if (!itemstack1.isEmpty())
                {
                    ((Slot)this.inventorySlots.get(0)).putStack(new ItemStack(itemstack1.getItem(), 1, itemstack1.getMetadata()));
                    itemstack1.shrink(1);
                }
            }

            if (itemstack1.isEmpty())
            {
                slot.putStack(ItemStack.EMPTY);
            }
            else
            {
                slot.onSlotChanged();
            }

            if (itemstack1.getCount() == itemstack.getCount())
            {
                return ItemStack.EMPTY;
            }

            slot.onTake(playerIn, itemstack1);
        }

        return itemstack;
    }
}
