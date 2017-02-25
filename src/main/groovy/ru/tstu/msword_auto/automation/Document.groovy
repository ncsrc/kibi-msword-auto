package ru.tstu.msword_auto.automation

import groovy.transform.PackageScope
import ru.tstu.msword_auto.automation.constants.FindStrategy
import ru.tstu.msword_auto.automation.constants.ReplacementStrategy
import ru.tstu.msword_auto.automation.constants.SaveFormat
import ru.tstu.msword_auto.automation.constants.SaveOptions

// This is created for scalability reasons.
// Thus you can create multiple kinds of Document object(e.g. WritableDocument) or use Decorator pattern, or else.

@PackageScope interface Document {
    boolean find(String key)
    boolean find(String key, FindStrategy strategy)
    void replace(String key, String replacement)
    void replace(String key, String replacement, ReplacementStrategy strategy)
    void save()
    void saveAs(String location, String name, SaveFormat format)
    void close()
    void close(SaveOptions options)

}