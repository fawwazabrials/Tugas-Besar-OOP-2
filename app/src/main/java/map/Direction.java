package map;

public enum Direction {
    NORTH,
    EAST,
    SOUTH,
    WEST;

    public static Direction strToDirection(String dirString) {
        if (dirString.length() != 1) {
            throw new IllegalArgumentException("Input harus karakter saja!");
        }
        char dirChar = dirString.charAt(0);
        switch (dirChar) {
            case 'N':
                return NORTH;
            case 'E':
                return EAST;
            case 'S':
                return SOUTH;
            case 'W':
                return WEST;
            default:
                throw new IllegalArgumentException("Arah tidak valid!");
        }
    }
}