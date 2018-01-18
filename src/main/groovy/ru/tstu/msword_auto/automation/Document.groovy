package ru.tstu.msword_auto.automation

import groovy.transform.PackageScope
import ru.tstu.msword_auto.automation.options.FindOption
import ru.tstu.msword_auto.automation.options.ReplacementOption
import ru.tstu.msword_auto.automation.options.SaveFormat
import ru.tstu.msword_auto.automation.options.SaveOption

// This is created for scalability reasons.
// Thus you can create multiple kinds of Document object(e.g. WritableDocument) or use Decorator pattern, or else.

@PackageScope interface Document {
    boolean find(String key)
    boolean find(String key, FindOption strategy)
    void replace(String key, String replacement)
    void replace(String key, String replacement, ReplacementOption strategy)
    void save()
    void saveAs(String location, String name, SaveFormat format)
    void close()
    void close(SaveOption options)

}