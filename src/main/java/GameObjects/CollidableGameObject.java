package GameObjects;

/**
 * Created by Student on 14-Jul-17.
 */
public class CollidableGameObject {
    int x;
    int y;
    int width;
    int heigth;
    int radius = 0;


    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeigth() {
        return heigth;
    }

    public void setHeigth(int heigth) {
        this.heigth = heigth;
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public CollidableGameObject(int x, int y, int width, int heigth) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.heigth = heigth;
    }

    public CollidableGameObject(int x, int y, int radius) {
        this.x = x;
        this.y = y;
        this.radius = radius;
    }

    public boolean checkEllipseColligeRectangle(int p0[], int radius, int p1[], int rectWidth, int rectHeight) {

        double circleX = p0[0];
        double circleY = p0[1];

        double rectX = p1[0];
        double rectY = p1[1];

        double minTmp = 0;
        double maxTmp = 0;
        if (circleX <= rectX + rectWidth)
            minTmp = circleX;
        else
            minTmp = rectX + rectWidth;
        if (rectX >= minTmp)
            maxTmp = rectX;
        else
            maxTmp = minTmp;
        double deltaX = circleX - maxTmp;

        if (circleY <= rectY + rectHeight)
            minTmp = circleY;
        else
            minTmp = rectY + rectHeight;
        if (rectY >= minTmp)
            maxTmp = rectY;
        else
            maxTmp = minTmp;
        double deltaY = circleY - maxTmp;

        return (deltaX * deltaX + deltaY * deltaY) < (radius * radius);
    }

    public boolean checkEllipseColligeEllipse(int p0[], int radius0, int p1[], int radius1) {
        return (((p0[0] - p1[0])*(p0[0] - p1[0]) + (p0[1] - p1[1])*(p0[1] - p1[1])) <= (radius0 + radius1-10)*(radius0 + radius1- 10));
    }

    public boolean isCollide(CollidableGameObject other) {
        if (radius != 0) {
            if (other.getRadius() != 0) {
                return checkEllipseColligeEllipse(new int[] {x,y},radius,new int[] {other.getX(),other.getY()},other.getRadius());
            } else {
                return checkEllipseColligeRectangle(new int[]{x+radius, y+radius}, radius, new int[]{other.getX(), other.getY()}, other.getWidth(), other.getHeigth());
            }
        } else {
            return false;
        }
    }
}

/*ЗАПУСКАЕМ
░ЧАЙКУ░▄▀▀▄░РАБОТЯГИ░░
▄███▀░◐░░░▌░░░░░░░
░░░░▌░░░░░▐░░░░░░░
░░░░▐░░░░░▐░░░░░░░
░░░░▌░░░░░▐▄▄░░░░░
░░░░▌░░░░▄▀▒▒▀▀▀▀▄
░░░▐░░░░▐▒▒▒▒▒▒▒▒▀▀▄
░░░▐░░░░▐▄▒▒▒▒▒▒▒▒▒▒▀▄
░░░░▀▄░░░░▀▄▒▒▒▒▒▒▒▒▒▒▀▄
░░░░░░▀▄▄▄▄▄█▄▄▄▄▄▄▄▄▄▄▄▀▄
░░░░░░░░░░░▌▌░▌▌░░░░░
░░░░░░░░░░░▌▌░▌▌░░░░░
░░░░░░░░░▄▄▌▌▄▌▌░░░░░*/



                /*if (circleBySegment(dots[0], dots[1], new int[]{x, y}, radius) ||
                        circleBySegment(dots[1], dots[2], new int[]{x, y}, radius) ||
                        circleBySegment(dots[2], dots[3], new int[]{x, y}, radius) ||
                        circleBySegment(dots[3], dots[0], new int[]{x, y}, radius)) {
                    System.out.println("test2 true");
                    return true;
                }
                else return false;
            */
