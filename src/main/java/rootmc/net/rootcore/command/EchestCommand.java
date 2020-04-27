package rootmc.net.rootcore.command;

import cn.nukkit.Player;
import cn.nukkit.block.Block;
import cn.nukkit.block.BlockEnderChest;
import cn.nukkit.blockentity.BlockEntity;
import cn.nukkit.blockentity.BlockEntityEnderChest;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandExecutor;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.PluginCommand;
import cn.nukkit.level.Position;
import cn.nukkit.math.Vector3;
import cn.nukkit.nbt.tag.CompoundTag;
import cn.nukkit.plugin.Plugin;

public class EchestCommand extends PluginCommand<Plugin> implements CommandExecutor {

    public EchestCommand(Plugin owner) {
        super("echest", owner);
        setExecutor(this);
        setDescription("Lệnh cơ bản");
        setPermission("rootcore.command.echest");
        getCommandParameters().clear();
    }


    @Override
    public boolean testPermission(CommandSender target) {
        if (this.testPermissionSilent(target)) {
            return true;
        } else {
            target.sendMessage("◊ §l§eBạn phải là VIP để sử dụng lệnh shop này");
            return false;
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] strings) {
        if (!(sender instanceof Player)) return true;
            sender.sendMessage("Lệnh đang được fix");
        //sendEnderChestInventory((Player)sender);
        return true;
    }

    private void sendEnderChestInventory(Player player) {
        Block block = Block.get(Block.ENDER_CHEST);
        player.getLevel().setBlock(new Vector3(player.x, player.y - 2, player.z), block, true, true);
        Position pos = new Position( (int) Math.floor(player.x), (int) Math.floor(player.y) - 2, (int) Math.floor(player.z),player.getLevel());
        CompoundTag nbt = BlockEntity.getDefaultCompound(pos, BlockEntity.ENDER_CHEST);
        BlockEntityEnderChest chest;
        chest = new BlockEntityEnderChest(player.getLevel().getChunk((int) player.x >> 4, (int) player.z >> 4), nbt);
        player.setViewingEnderChest((BlockEnderChest) block);
        player.addWindow(player.getEnderChestInventory());
    }
}
