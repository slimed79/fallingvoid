package fr.gravenilvec.fallingvoid;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.generator.ChunkGenerator;

public class FallingGenerator extends ChunkGenerator {
	
	private FallingVoid main;

	public FallingGenerator(FallingVoid fallingVoid) { this.main = fallingVoid; }
	
	@Override
	public boolean canSpawn(World world, int x, int z) {
		return true;
	}
	
	@Override
	public byte[] generate(World world, Random random, int chunkX, int chunkZ) {
		
		byte[] result = new byte[32768];
		int minHeight = main.getConfig().getInt("blocks.minHeight");

		if((chunkX >= -2) && (chunkX <= 2) && (chunkZ >= 0) && (chunkZ < 5)){
			for(int x = 0; x < 16; x++){
				for(int z = 0; z < 16; z++){
					
					//socle
					result[toByte(x,minHeight,z)] = 7;
					main.chunkplateform.add(new FallingChunck(x,(minHeight),z,chunkX,chunkZ));
					
					//plateform
					int random1 = main.blocks.get(new Random().nextInt(main.blocks.size()));
					result[toByte(x,(minHeight + 1),z)] = (byte) random1;
					main.chunkplateform.add(new FallingChunck(x,(minHeight + 1),z,chunkX,chunkZ));
					
				}
			}
		}
		
		return result;
	}
	
	private int toByte(int x, int y, int z) {
		return (x * 16 + z) * 128 + y;
	}

	@SuppressWarnings("deprecation")
	@Override
	public byte[][] generateBlockSections(World world, Random random, int x, int z, BiomeGrid biomes) {
		// TODO Auto-generated method stub
		return super.generateBlockSections(world, random, x, z, biomes);
	}
	
	@Override
	public ChunkData generateChunkData(World world, Random random, int x, int z, BiomeGrid biome) {
		// TODO Auto-generated method stub
		return super.generateChunkData(world, random, x, z, biome);
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public short[][] generateExtBlockSections(World world, Random random, int x, int z, BiomeGrid biomes) {
		// TODO Auto-generated method stub
		return super.generateExtBlockSections(world, random, x, z, biomes);
	}
	
	@Override
	public List<BlockPopulator> getDefaultPopulators(World world) {
		// TODO Auto-generated method stub
		return Arrays.asList(new BlockPopulator[0]);
	}
	
	@Override
	public Location getFixedSpawnLocation(World world, Random random) {
		// TODO Auto-generated method stub
		return super.getFixedSpawnLocation(world, random);
	}

}
