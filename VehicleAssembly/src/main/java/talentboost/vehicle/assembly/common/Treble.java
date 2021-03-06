package talentboost.vehicle.assembly.common;
/**
 * <code>Treble</code> is a parameterized immutable structure of three elements
 * with specified types.<br>
 * 
 * This class is typically used as a substitute for multiple return values (due
 * to their lack in Java)
 * 
 */
public class Treble<FirstType, SecondType, ThirdType> {
    private FirstType firstElement;
    private SecondType secondElement;
    private ThirdType thirdElement;
    
    public Treble(FirstType firstElement, SecondType secondElement, ThirdType thirdElement) {
        super();
        this.firstElement = firstElement;
        this.secondElement = secondElement;
        this.thirdElement = thirdElement;
    }

    public FirstType getFirstElement() {
        return firstElement;
    }

    public SecondType getSecondElement() {
        return secondElement;
    }

    public ThirdType getThirdElement() {
        return thirdElement;
    }

    // hashCode(), equals(), and toString() generated by Eclipse (right click/Source/Generate ...)
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((firstElement == null) ? 0 : firstElement.hashCode());
        result = prime * result + ((secondElement == null) ? 0 : secondElement.hashCode());
        result = prime * result + ((thirdElement == null) ? 0 : thirdElement.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof Treble)) {
            return false;
        }
        Treble other = (Treble) obj;
        if (firstElement == null) {
            if (other.firstElement != null) {
                return false;
            }
        } else if (!firstElement.equals(other.firstElement)) {
            return false;
        }
        if (secondElement == null) {
            if (other.secondElement != null) {
                return false;
            }
        } else if (!secondElement.equals(other.secondElement)) {
            return false;
        }
        if (thirdElement == null) {
            if (other.thirdElement != null) {
                return false;
            }
        } else if (!thirdElement.equals(other.thirdElement)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Treble [firstElement=" + firstElement + ", secondElement=" + secondElement + ", thirdElement=" + thirdElement + "]";
    }
    
}
