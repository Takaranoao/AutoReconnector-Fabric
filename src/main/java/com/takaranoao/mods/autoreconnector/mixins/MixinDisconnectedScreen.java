package com.takaranoao.mods.autoreconnector.mixins;

import com.takaranoao.mods.autoreconnector.AutoConnectorMod;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.screen.DisconnectedScreen;
import net.minecraft.client.resource.language.I18n;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(DisconnectedScreen.class)
public class MixinDisconnectedScreen {
    private static final int FONT_HEIGHT = 9;
    @Shadow
    private int reasonHeight;
    @Inject(method = "render", at = @At("RETURN"))
    private void onRender (int p_render_1_, int p_render_2_, float p_render_3_, CallbackInfo ci){
        if(AutoConnectorMod.lastestServerEntry == null)return;
        MinecraftClient mc = MinecraftClient.getInstance();
        TextRenderer textRenderer = mc.textRenderer;
        if( mc.currentScreen == null) return;
        int width = mc.currentScreen.width;
        int height = mc.currentScreen.height;
        mc.currentScreen.drawCenteredString(
                textRenderer,
                I18n.translate("autoReconnector.waitingTime", (AutoConnectorMod.MAX_TICK - AutoConnectorMod.disconnectTick)/20),
                width/2,
                height - this.reasonHeight / 2 - FONT_HEIGHT * 2,
                0xffffff);
    }
}
