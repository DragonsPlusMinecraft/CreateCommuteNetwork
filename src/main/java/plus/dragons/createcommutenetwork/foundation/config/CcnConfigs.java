package plus.dragons.createcommutenetwork.foundation.config;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.config.ModConfigEvent;
import org.apache.commons.lang3.tuple.Pair;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class CcnConfigs {

    public static CcnServerConfig SERVER;
    public static ForgeConfigSpec SERVER_SPEC;

    public static void register(ModLoadingContext context) {
        Pair<CcnServerConfig, ForgeConfigSpec> serverConfigPair = new ForgeConfigSpec.Builder().configure(builder -> {
            CcnServerConfig config = new CcnServerConfig();
            config.registerAll(builder);
            return config;
        });
        SERVER = serverConfigPair.getKey();
        SERVER_SPEC = serverConfigPair.getValue();
        context.registerConfig(ModConfig.Type.SERVER, SERVER_SPEC);
    }

    @SubscribeEvent
    public static void onLoad(ModConfigEvent.Loading event) {
        if (SERVER_SPEC == event.getConfig().getSpec())
            SERVER.onLoad();
    }

    @SubscribeEvent
    public static void onReload(ModConfigEvent.Reloading event) {
        if (SERVER_SPEC == event.getConfig().getSpec())
            SERVER.onReload();
    }

}
