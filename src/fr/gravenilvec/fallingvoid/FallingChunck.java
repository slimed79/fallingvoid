package fr.gravenilvec.fallingvoid;

public class FallingChunck {
	
	private int x,y,z;
	private int chunkX, chunkZ;

	public FallingChunck(int x, int y, int z, int chunkX, int chunkZ) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.chunkX = chunkX;
		this.chunkZ = chunkZ;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getZ() {
		return z;
	}

	public void setZ(int z) {
		this.z = z;
	}

	public int getChunkX() {
		return chunkX;
	}

	public void setChunkX(int chunkX) {
		this.chunkX = chunkX;
	}

	public int getChunkZ() {
		return chunkZ;
	}

	public void setChunkZ(int chunkZ) {
		this.chunkZ = chunkZ;
	}

}
