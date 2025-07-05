package DTOs;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class MoveDTO implements Serializable {
    private String from; // e2
    private String to; // e4
}
