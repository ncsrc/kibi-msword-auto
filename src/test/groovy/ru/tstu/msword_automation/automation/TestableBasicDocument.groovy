package ru.tstu.msword_automation.automation

class TestableBasicDocument extends BasicDocument
{


    TestableBasicDocument(String name)
    {
        super(name)
    }

    String getName()
    {
        return doc.Name
    }

    String getFullPath()
    {
        return doc.Path+application.PathSeparator+this.getName()
    }

}
