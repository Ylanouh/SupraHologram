package fr.ylanouh.supraholograms;

import fr.ylanouh.supraholograms.builder.HologramBuilder;
import fr.ylanouh.supraholograms.commands.SHCmd;
import fr.ylanouh.supraholograms.commands.TestCmd;
import fr.ylanouh.supraholograms.listeners.JoinPlayer;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;

public class Main extends JavaPlugin {

    private static Main instance;

    @Override
    public void onEnable() {
        instance = this;

        getCommand("test").setExecutor(new TestCmd());
        getCommand("sh").setExecutor(new SHCmd());

        SupraHolograms.getInstance().setPlugin(this);

        getServer().getPluginManager().registerEvents(new JoinPlayer(), this);

        System.out.println("SupraHolograms was enabled");
        System.out.println("Unit test starting...");

        try {
            System.out.println("Starting test at 0, 150, 0");
            SupraHologramsTests.prepare(new Location(Bukkit.getWorld("world"), 0, 150, 0));
            Bukkit.getScheduler().runTaskAsynchronously(Main.getInstance(), ()->{
                new SupraHologramsTests().testAll();
                System.out.println("Tests success");
            });
        } catch (Exception e) {
            System.out.println("Tests failed with errors");
            System.out.println(Arrays.toString(e.getStackTrace()));
        }
    }

    @Override
    public void onDisable() {
        SupraHolograms.getInstance().removeAll();
    }

    public static Main getInstance(){
        return instance;
    }
}
