package ru.tstu.msword_auto.automation.constants

enum SaveOptions
{
    // WdSaveOptions
    DO_NOT_SAVE(0),
    SAVE(-1),
    PROMPT_TO_SAVE(-2)

    private int value

    private SaveOptions(int value)
    {
        this.value = value
    }

    public int value()
    {
        return this.value;
    }

}