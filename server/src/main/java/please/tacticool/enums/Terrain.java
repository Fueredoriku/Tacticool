package please.tacticool.enums;

public enum Terrain {
    GRASS(1, "input texture here"),
    FOREST(2, "input texture here"),
    MOUNTAIN(2, "input texture here"),
    ROCKY(1, "input texture here"),
    BROKEN(3, "input texture here");

    private final int value;
    private final String texture;

    private Terrain(final int value, final String texture) {
        this.value = value;
        this.texture = texture;
    }

    public int getMoveCost() {
        return value;
    }

    public String getTexture() {
        return texture;
    }
}
