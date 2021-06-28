package rmc.mixins.botania_guard;

import java.util.UUID;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.mojang.authlib.GameProfile;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import rmc.libs.event_factory.EventFactory;
import rmc.libs.tile_ownership.TileOwnership;

/**
 * Developed by RMC Team, 2021
 * @author KR33PY
 */
public abstract class BotaniaGuard {

    public static final GameProfile DRUM_HORN_FAKE = new GameProfile(UUID.fromString("B20D6895-559D-43DE-9B54-97D42F10F2C7"), "[BotaniaDrumHorn]");
    public static final GameProfile MANA_BURST_FAKE = new GameProfile(UUID.fromString("DFE69DC4-CB91-4A73-A977-E297EEB6FD54"), "[BotaniaManaBurst]");
    public static final GameProfile FUNCTIONAL_FAKE = new GameProfile(UUID.fromString("EB4CA725-A478-49FD-B635-EF692DA6E5D9"), "[BotaniaFunctional]");
    public static final GameProfile TINY_PLANET_FAKE = new GameProfile(UUID.fromString("932AB458-35E6-41FA-8731-F7723013CF20"), "[BotaniaTinyPlanet]");

    public static boolean validateTilePositioning(@Nonnull TileEntity tile, @Nonnull BlockPos pos, @Nullable GameProfile fake, int rangeHor, int rangeVer) {
        if (tile == null || pos == null) {
            throw new IllegalArgumentException("tile/pos must not be null!");
        }
        if (!TileOwnership.isTrustworthy(TileOwnership.convert(tile))) {
            for (int i = 0; i < 7; i++) {
                BlockPos offset = null;
                switch (i) {
                    case 0: offset = pos.offset(rangeHor, rangeVer, rangeHor); break;
                    case 1: offset = pos.offset(rangeHor, -rangeVer, rangeHor); break;
                    case 2: offset = pos.offset(-rangeHor, rangeVer, -rangeHor); break;
                    case 3: offset = pos.offset(-rangeHor, -rangeVer, -rangeHor); break;
                    case 4: offset = pos.offset(-rangeHor, rangeVer, rangeHor); break;
                    case 5: offset = pos.offset(-rangeHor, -rangeVer, rangeHor); break;
                    case 6: offset = pos.offset(rangeHor, rangeVer, -rangeHor); break;
                    case 7: offset = pos.offset(rangeHor, -rangeVer, -rangeHor); break;
                }
                if (!EventFactory.testBlockBreak(EventFactory.convertFake(tile.getLevel(), TileOwnership.loadOwner(TileOwnership.convert(tile))), tile.getLevel(), offset, fake)) {
                    tile.getLevel().destroyBlock(tile.getBlockPos(), true);
                    return false;
                }
            }
            TileOwnership.grantTrust(TileOwnership.convert(tile));
            return true;
        }
        return true;
    }

}