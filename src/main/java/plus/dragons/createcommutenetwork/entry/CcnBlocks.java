package plus.dragons.createcommutenetwork.entry;

import com.simibubi.create.AllCreativeModeTabs;
import com.simibubi.create.content.trains.track.TrackTargetingBlockItem;
import com.simibubi.create.foundation.data.AssetLookup;
import com.simibubi.create.foundation.data.SharedProperties;
import com.simibubi.create.foundation.data.TagGen;
import com.tterrag.registrate.util.entry.BlockEntry;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.MaterialColor;
import plus.dragons.createcommutenetwork.DragonTransit;
import plus.dragons.createcommutenetwork.content.logistics.commute.train.platform.PlatformBlock;

import static com.simibubi.create.foundation.data.ModelGen.customItemModel;
import static plus.dragons.createcommutenetwork.DragonTransit.REGISTRATE;

public class CcnBlocks {

    static {
        REGISTRATE.creativeModeTab(() -> AllCreativeModeTabs.BASE_CREATIVE_TAB);
    }

    public static final BlockEntry<PlatformBlock> TRANSIT_STATION_PLATFORM = REGISTRATE
            .block("platform", PlatformBlock::new)
            .initialProperties(SharedProperties::stone)
            .properties(p -> p.color(MaterialColor.PODZOL))
            .properties(p -> p.sound(SoundType.NETHERITE_BLOCK))
            .transform(TagGen.pickaxeOnly())
            .blockstate((c, p) -> p.simpleBlock(c.get(), AssetLookup.partialBaseModel(c, p)))
            .item(TrackTargetingBlockItem.ofType(DragonTransit.PLATFORM))
            .transform(customItemModel())
            .register();

    public static void register() {}

}