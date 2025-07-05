package DTOs;

import lombok.Getter;
import lombok.Setter;


import java.io.Serializable;

@Getter
@Setter
public class PgnContentDTO implements Serializable {
    private String user1;
    private String user2;
    private String user3;
    private String gameContent;

}
