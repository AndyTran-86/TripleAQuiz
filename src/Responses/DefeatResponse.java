package Responses;

import java.io.Serializable;

public class DefeatResponse extends Response implements Serializable {
    DefeatType defeatType;
    public DefeatResponse(DefeatType defeatType) {
        this.defeatType = defeatType;
    }

    public DefeatType getDefeatType() {
        return defeatType;
    }
}
