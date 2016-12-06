package ECS;

/**
 * Created by lenov on 05/12/2016.
 */

public enum BoxType {
    EMPTY((byte)0),
    FILL((byte)1),
    BIN((byte)2),
    TOTAL_BOXTYPE((byte)(BIN.Value_+1));

    BoxType()
    {
        Value_ = 0;
    }
    BoxType(byte zeVal)
    {
        Value_ = zeVal;
    }

    public byte Value_;
}
