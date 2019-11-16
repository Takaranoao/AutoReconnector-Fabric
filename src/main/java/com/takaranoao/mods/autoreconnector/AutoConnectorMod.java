package com.takaranoao.mods.autoreconnector;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.event.client.ClientTickCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ConnectScreen;
import net.minecraft.client.gui.screen.DisconnectedScreen;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.options.ServerEntry;
import org.apache.logging.log4j.LogManager;
public class AutoConnectorMod implements ClientModInitializer {
	@Environment(EnvType.CLIENT)
	public static ServerEntry lastestServerEntry;

	public static int disconnectTick = 0;
	public static final int MAX_TICK = 20*15;
	@Override
	public void onInitializeClient() {
		ClientTickCallback.EVENT.register(minecraftClient->clientTick());
		LogManager.getLogger().info("Loading Auto Reconnect");
	}
	public static void clientTick(){
		MinecraftClient mc = MinecraftClient.getInstance();
		if(mc.world != null && mc.getCurrentServerEntry() != null){
			lastestServerEntry = mc.getCurrentServerEntry();
		}
		if(mc.currentScreen instanceof DisconnectedScreen){
			disconnectTick++;
			if(disconnectTick >= MAX_TICK && lastestServerEntry!=null){
				mc.disconnect();
				mc.openScreen(new ConnectScreen(new TitleScreen(), mc, lastestServerEntry));
			}
		}else{
			disconnectTick = 0;
		}
	}
}
