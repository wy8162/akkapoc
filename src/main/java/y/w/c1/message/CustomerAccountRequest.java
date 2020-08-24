package y.w.c1.message;

import lombok.AccessLevel;
import lombok.Setter;

@Setter(value = AccessLevel.NONE)
public class CustomerAccountRequest implements Command {
    final private String escid;
    final private String profileReferenceId;

    public CustomerAccountRequest(String escid, String profileReferenceId) {
        this.escid = escid;
        this.profileReferenceId = profileReferenceId;
    }
}
