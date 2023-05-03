package item;

public class Furniture extends Item {
    // Attributes
    private int x, y;
    private String name;
    private String category;
    private String renderChar;
    private String action;
    private int priceValue;
    private int width;
    private int length;

    // Constructor
    public Furniture(String name, int x, int y) {
        this.x = x;
        this.y = y;

        this.name = name;
        this.category = "furniture";
        this.setPrice(name);
        this.setLength(name);
        this.setWidth(name);
        this.setRenderChar(name);
        this.setAction(name);
    }

    private void setAction(String name) {
        if (name.equals("kasur single")) {
            this.action = "sleep";
        } else if (name.equals("kasur queen size")) {
            this.action = "sleep";
        } else if (name.equals("kasur king size")) {
            this.action = "sleep";
        } else if (name.equals("toilet")) {
            this.action = "poop";
        } else if (name.equals("kompor gas")) {
            this.action = "cook";
        } else if (name.equals("kompor listrik")) {
            this.action = "cook";
        } else if (name.equals("meja dan kursi")) {
            this.action = "eat";
        } else if (name.equals("jam")) {
            this.action = "seetime";
        }
    }

    public int getX() {return x;}
    public void setX(int newX) {x = newX;}

    public int getY() {return y;}
    public void setY(int newY) {y = newY;}

    public String getAction() {return action;}

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public int getPriceValue() {
        return priceValue;
    }

    public int getWidth() {
        return width;
    }

    public int getLength() {
        return length;
    }

    public char getRenderChar() {
        return renderChar.charAt(0);
    }

    public void setPrice(String name) {
        if (name.equals("kasur single")) {
            this.priceValue = 50;
        } else if (name.equals("kasur queen size")) {
            this.priceValue = 100;
        } else if (name.equals("kasur king size")) {
            this.priceValue = 150;
        } else if (name.equals("toilet")) {
            this.priceValue = 50;
        } else if (name.equals("kompor gas")) {
            this.priceValue = 100;
        } else if (name.equals("kompor listrik")) {
            this.priceValue = 200;
        } else if (name.equals("meja dan kursi")) {
            this.priceValue = 50;
        } else if (name.equals("jam")) {
            this.priceValue = 10;
        }
    }

    public void setLength(String name) {
        if (name.equals("kasur single")) {
            this.length = 4;
        } else if (name.equals("kasur queen size")) {
            this.length = 4;
        } else if (name.equals("kasur king size")) {
            this.length = 5;
        } else if (name.equals("toilet")) {
            this.length = 1;
        } else if (name.equals("kompor gas")) {
            this.length = 2;
        } else if (name.equals("kompor listrik")) {
            this.length = 2;
        } else if (name.equals("meja dan kursi")) {
            this.length = 3;
        } else if (name.equals("jam")) {
            this.length = 1;
        }
    }

    public void setWidth(String name) {
        if (name.equals("kasur single")) {
            this.width = 1;
        } else if (name.equals("kasur queen size")) {
            this.width = 2;
        } else if (name.equals("kasur king size")) {
            this.width = 2;
        } else if (name.equals("toilet")) {
            this.width = 1;
        } else if (name.equals("kompor gas")) {
            this.width = 1;
        } else if (name.equals("kompor listrik")) {
            this.width = 1;
        } else if (name.equals("meja dan kursi")) {
            this.width = 3;
        } else if (name.equals("jam")) {
            this.width = 1;
        }
    }

    public void setRenderChar(String name) {
        if (name.equals("kasur single")) {
            this.renderChar = "b";
        } else if (name.equals("kasur queen size")) {
            this.renderChar = "b";
        } else if (name.equals("kasur king size")) {
            this.renderChar = "b";
        } else if (name.equals("toilet")) {
            this.renderChar = "t";
        } else if (name.equals("kompor gas")) {
            this.renderChar = "k";
        } else if (name.equals("kompor listrik")) {
            this.renderChar = "k";
        } else if (name.equals("meja dan kursi")) {
            this.renderChar = "m";
        } else if (name.equals("jam")) {
            this.renderChar = "j";
        }
    }
}