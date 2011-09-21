package me.iffa.economy;

import com.iConomy.iConomy;
import com.iConomy.system.Holdings;
import me.iffa.bananaspace.BananaSpace;
import me.iffa.bananaspace.config.SpaceConfig;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

/**
 *
 * @author kitskub
 */
public class Economy {
    private iConomy iConomy = null;
    private BananaSpace plugin;
    private boolean use;

    /**
     * currently only supports iconomy
     * @param plugin
     */
    public Economy(BananaSpace plugin) {
        this.plugin = plugin;
        use=true;
        if(iConomy==null){
            Plugin iConomy2 = plugin.getServer().getPluginManager().getPlugin("iConomy");
            if (iConomy2 != null) {
            if (iConomy2.isEnabled() && iConomy2.getClass().getName().equals("com.iConomy.iConomy")) {
                iConomy = (iConomy)iConomy2;
                BananaSpace.debugLog("Hooked into iConomy.");
            }
            }
        }
    }
    /**
     * 
     */
    public Economy(){
        use=false;
    }
    public static boolean checkEconomy(BananaSpace plugin){
            if(SpaceConfig.getConfig().getBoolean("economy.enabled", true)){
                Plugin iConomy2 = plugin.getServer().getPluginManager().getPlugin("iConomy");
                if (iConomy2 != null) {
                if (iConomy2.isEnabled() && iConomy2.getClass().getName().equals("com.iConomy.iConomy")) {
                    return true;
                }
                }
            }
        return false;
    }
    public boolean enter(Player player) {
        if (use==false) return true;
            if(iConomy.hasAccount(player.getName())) {
                int amount =SpaceConfig.getConfig().getInt("economy.entercost",20);
                return subtract(player,amount);
            } else {
                return false;
            }   
            
            
         
    }
    public boolean exit(Player player){
        if (use==false) return true;
            if(iConomy.hasAccount(player.getName())) {
                int amount =SpaceConfig.getConfig().getInt("economy.exitcost",20);
                return subtract(player,amount);
            } else {
                return false;
            }   
        
    }
    public boolean enterCommand(Player player){
        if (use==false) return true;
            if(iConomy.hasAccount(player.getName())) {
                int amount =SpaceConfig.getConfig().getInt("economy.entercommandcost",20);
                return subtract(player,amount);
            } else {
                return false;
            }   
         
    }
    public boolean exitCommand(Player player){
        if (use==false) return true;
            if(iConomy.hasAccount(player.getName())) {
                int amount =SpaceConfig.getConfig().getInt("economy.exitcommandcost",20);
                return subtract(player,amount);
            } else {
                return false;
            }   
    }

    private boolean subtract(Player player, int amount) {
        if(BananaSpace.getPlayerHandler().hasPermission("bananspace.economy.exempt",player)) return true;
        Holdings balance = iConomy.getAccount(player.getName()).getHoldings();
        if(!balance.hasEnough(amount)) return false;
        balance.subtract(amount);
        return true;
    }
}