package please.tacticool;

public record GameBalance() {
    //Move
    public static int MoveActionCost = 1;

    //Rifle
    public static int RifleActionCost = 2;
    public static int RifleDamage = 100;

    //Bazooka
    public static int BazookaActionCost = 3;
    public static int BazookaDamage = 5;
    public static int BazookaRange = 3;
    public static int BazookaRadius = 3;

    //Player
    public static final int MaxActionPoints = 10;
    public static final int DefaultHealthPoints = 100;

    //Grid
    public static final int DefaultWidth = 10;
    public static final int DefaultHeigth = 10;

}
