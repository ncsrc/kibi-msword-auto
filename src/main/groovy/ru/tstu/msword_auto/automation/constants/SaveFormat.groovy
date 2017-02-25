package ru.tstu.msword_auto.automation.constants

enum SaveFormat
{
    // WdSaveFormat
    // https://msdn.microsoft.com/ru-ru/library/office/ff839952.aspx

    RTF(6),
    DOC_97(0),
    DOC_DEFAULT(16),
    PDF(17),
    DOCX(12),
    DOCX_WITH_MACRO(13)

    // test last two, see if there is any difference

    private int value;

    private SaveFormat(int value)
    {
        this.value = value
    }


    public int value()
    {
        return this.value;
    }


}