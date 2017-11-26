package rakhya.gsu.edu.project;

/**
 * Created by prava on 11/19/2017.
 */

public class Product
{
    String name;
    int price;
    int image;
    boolean box;

    Product(int _price, String name)
    {
        this.name= name;
        price = _price;
    }
}
