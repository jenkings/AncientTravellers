package Entity.Collectibles;

public enum Items
{

    APPLE("STOP"), GREEN("GO"), ORANGE("SLOW DOWN");


    private String action;

    public String getAction()
    {
        return this.action;
    }


    private Items(String action)
    {
        this.action = action;
    }
}