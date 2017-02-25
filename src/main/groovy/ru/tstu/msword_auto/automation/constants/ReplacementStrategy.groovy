package ru.tstu.msword_auto.automation.constants


enum ReplacementStrategy
{
    // WdReplace
    REPLACE_NONE(0),
    REPLACE_ONE(1),
    REPLACE_ALL(2),


    private int value;

    private ReplacementStrategy(int value)
    {
        this.value = value;
    }


    public int value()
    {
        return this.value;
    }
}