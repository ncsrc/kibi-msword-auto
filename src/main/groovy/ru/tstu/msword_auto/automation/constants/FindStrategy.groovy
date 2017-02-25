package ru.tstu.msword_auto.automation.constants


enum FindStrategy
{
    // WdFindWrap
    STOP(0), // stops when all occurrences was found
    CONTINUE(1), // continues even when all occurrences was found, may result in endless loop
    ASK(2)

    private int value;

    private FindStrategy(int value)
    {
        this.value = value;
    }

    public int value()
    {
        return this.value;
    }
}