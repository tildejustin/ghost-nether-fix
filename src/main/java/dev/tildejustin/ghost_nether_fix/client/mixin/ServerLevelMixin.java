package dev.tildejustin.ghost_nether_fix.client.mixin;

import dev.tildejustin.ghost_nether_fix.client.WorldGenLevelExtension;
import net.minecraft.core.SectionPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.StructureFeatureManager;
import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraft.world.level.levelgen.structure.StructureStart;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.stream.Stream;

@Mixin(ServerLevel.class)
public abstract class ServerLevelMixin implements WorldGenLevelExtension {
    @Shadow public abstract StructureFeatureManager structureFeatureManager();

    @Override
    public Stream<? extends StructureStart<?>> startsForFeature(SectionPos sectionPos, StructureFeature<?> structureFeature) {
        return this.structureFeatureManager().startsForFeature(sectionPos, structureFeature);
    }
}
