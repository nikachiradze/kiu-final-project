package DTOs;

import lombok.EqualsAndHashCode;
import lombok.Value;

import java.io.Serializable;
import java.util.Objects;

@Value
public class Position implements Serializable {
    int x;
    int y;

    public boolean isValid() {
        return x >= 0 && x < 8 && y >= 0 && y < 8;
    }

    public String toAlgebraic() {
        return "" + (char) ('a' + x) + (8 - y);  // Convert y correctly as 1-based index
    }

    @Override
    public int hashCode() {
        return Objects.hash(x,y);
    }
}
