package please.tacticool.enums;

public enum Terrain {
    GRASS(0, 1, "input texture here"),
    FOREST(1, 2, "input texture here"),
    MOUNTAIN(2, 2, "input texture here"),
    ROCKY(3, 1, "input texture here"),
    BROKEN(4, 3, "input texture here");

    private final int id;
    private final int value;
    private final String texture;

    private Terrain(final int id, final int value, final String texture) {
        this.id = id;
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
