package ru.tstu.msword_automation.automation

class TestableBasicDocument extends BasicDocument
{


    def TestableBasicDocument(String name)
    {
        super(name)
    }

    public String getName()
    {
        return doc.Name;
    }

    public String getFullPath()
    {
        return doc.Path+application.PathSeparator+this.getName()
    }

}
