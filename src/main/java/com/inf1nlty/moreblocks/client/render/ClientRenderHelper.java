package com.inf1nlty.moreblocks.client.render;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public class ClientRenderHelper {

    public static final ThreadLocal<Integer> CURRENT_RENDER_COLOR = new ThreadLocal<>();
    public static void setCurrentRenderColor(int color) { CURRENT_RENDER_COLOR.set(color); }
    public static void clearCurrentRenderColor() { CURRENT_RENDER_COLOR.remove(); }

}