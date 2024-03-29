package plus.dragons.createcommutenetwork;

import com.mojang.logging.LogUtils;
import com.simibubi.create.content.trains.graph.EdgePointType;
import com.simibubi.create.foundation.data.CreateRegistrate;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;
import plus.dragons.createcommutenetwork.content.commute.trains.commuteStation.CommuteStationEdgePoint;
import plus.dragons.createcommutenetwork.content.network.CommuteNetworkManager;
import plus.dragons.createcommutenetwork.entry.CcnBlockEntities;
import plus.dragons.createcommutenetwork.entry.CcnBlocks;
import plus.dragons.createcommutenetwork.entry.CcnPackets;
import plus.dragons.createcommutenetwork.foundation.config.CcnConfigs;
import plus.dragons.createdragonlib.init.SafeRegistrate;
import plus.dragons.createdragonlib.lang.Lang;

@Mod(CommuteNetwork.ID)
public class CommuteNetwork {
    private static final Logger LOGGER = LogUtils.getLogger();
    public static final String ID = "create_commute_network";
    public static final String NAME = "Create Commute Network";
    public static final CreateRegistrate REGISTRATE = new SafeRegistrate(ID);
    public static final Lang LANG = new Lang(ID);
    public static CommuteNetworkManager COMMUTE_NETWORK_MANAGER = new CommuteNetworkManager();
    public static final EdgePointType<CommuteStationEdgePoint> COMMUTE_STATION =
            EdgePointType.register(genRL("commute_station"), CommuteStationEdgePoint::new);


    public CommuteNetwork() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        IEventBus forgeEventBus = MinecraftForge.EVENT_BUS;

        CcnConfigs.register(ModLoadingContext.get());

        registerEntries(modEventBus);
        modEventBus.addListener(CommuteNetwork::setup);
        registerDTNetworkManagerEvent(forgeEventBus);
        DistExecutor.safeRunWhenOn(Dist.CLIENT, () -> CommuteNetworkClient::new);
    }

    private void registerEntries(IEventBus modEventBus) {
        REGISTRATE.registerEventListeners(modEventBus);
        CcnBlocks.register();
        CcnBlockEntities.register();
    }

    private void registerDTNetworkManagerEvent(IEventBus forgeEventBus) {
        forgeEventBus.addListener(CommuteNetworkManager::onPlayerLoggedIn);
        forgeEventBus.addListener(CommuteNetworkManager::onLoadWorld);
    }

    @SubscribeEvent
    public static void setup(final FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            CcnPackets.registerPackets();
        });
    }

    public static ResourceLocation genRL(String name) {
        return new ResourceLocation(ID, name);
    }
}
