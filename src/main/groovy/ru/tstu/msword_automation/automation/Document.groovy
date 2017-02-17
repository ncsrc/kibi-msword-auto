package ru.tstu.msword_automation.automation

import groovy.transform.PackageScope
import ru.tstu.msword_automation.automation.constants.FindStrategy
import ru.tstu.msword_automation.automation.constants.ReplacementStrategy
import ru.tstu.msword_automation.automation.constants.SaveFormat
import ru.tstu.msword_automation.automation.constants.SaveOptions

// This is created for scalability reasons.
// Thus you can create multiple kinds of Document object(e.g. WritableDocument) or use Decorator pattern, or else.

@PackageScope interface Document
{
    boolean find(String key, FindStrategy strategy)
    void replace(String key, String replacement, ReplacementStrategy strategy)
    void save()
    void saveAs(String location, String name, SaveFormat format)
    void close(SaveOptions options)

}