public class ActorSprite extends SpriteConnector{

    
    public SpriteConnector(SpriteConnectorEnum spriteConnectorEnum, SpriteConnectorEnum highlightSprite, int x, int y) {
        this.spriteConnectorEnum = spriteConnectorEnum;
        this.highlightEnum = highlightSprite;
        this.x = x;
        this.y = y;
        this.actor = new Actor();
        ap = ActionPointSingleton.getInstance();
        actor.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                ap.addAction(getHighlight());
            }
                          });
    }

    private SpriteConnector getHighlight(){
        return new SpriteConnector(this.highlightEnum, this.x, this.y);
    }

    public SpriteConnectorEnum getSpriteConnectorEnum() {
        return spriteConnectorEnum;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Actor getActor() throws Exception{
        if (actor == null){
            throw new Exception("The sprite has no actor tied to it!");
        }
        return actor;
    }

    /**
     * Currently loads each texture seperately from memory each time this is called.
     * Should be changed to use TextureRegion to avoid this.
     */

    public Sprite prepareSprite(float tileScale) {
        Texture texture = new Texture(spriteConnectorEnum.getFilePath());
        Sprite sprite = new Sprite(texture);
        sprite.setSize(tileScale, tileScale);
        return sprite;
    }

}
