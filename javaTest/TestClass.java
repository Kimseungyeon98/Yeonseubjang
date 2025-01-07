package javaTest;

public class TestClass implements TestInterface{
    @Override
    public void test() {
        System.out.println("test");
    }

    String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
