package dev.tildejustin.ghost_nether_fix.client;

import net.minecraft.core.SectionPos;
import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraft.world.level.levelgen.structure.StructureStart;

import java.util.stream.Stream;

public interface WorldGenLevelExtension {
    Stream<? extends StructureStart<?>> startsForFeature(SectionPos sectionPos, StructureFeature<?> structureFeature);
}
