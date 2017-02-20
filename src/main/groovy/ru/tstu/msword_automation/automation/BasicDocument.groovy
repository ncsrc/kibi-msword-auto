package ru.tstu.msword_automation.automation

import groovy.transform.PackageScope
import org.codehaus.groovy.scriptom.ActiveXObject
import org.codehaus.groovy.scriptom.Scriptom
import ru.tstu.msword_automation.automation.constants.FindStrategy
import ru.tstu.msword_automation.automation.constants.ReplacementStrategy
import ru.tstu.msword_automation.automation.constants.SaveFormat
import ru.tstu.msword_automation.automation.constants.SaveOptions

// todo javadocs
@PackageScope class BasicDocument implements Document  {
    // package access for testing extended class
    @PackageScope final def doc
    @PackageScope final def application


    BasicDocument(String path) {
        this.application = WordApplication.getApplication()
        this.doc = application.Documents.Open(path)
    }


    // todo add test ?
    void replace(String key, String replacement) {
        this.replace(key, replacement, ReplacementStrategy.REPLACE_ALL)
    }

    // todo another replace with default strategy
    void replace(String key, String replacement, ReplacementStrategy strategy) {
        def find = getFindObject(key, FindStrategy.CONTINUE)
        setReplacementObject(replacement, find)
        executeReplacement(find, strategy)
    }

    boolean find(String key, FindStrategy strategy) {
        def find = getFindObject(key, strategy)
        return find.Execute()
    }

    void close(SaveOptions options) {
        application.quit(options.value())   // todo change to closing document
    }

    void save() {
        doc.save() // TODO change save option to format with macro
    }

    void saveAs(String location, String name, SaveFormat format) {
        application.ChangeFileOpenDirectory(location)
        doc.SaveAs(name, format.value())
    }


    private def getFindObject(String key, FindStrategy findStrategy) {
        def find = application.Selection.Find
        find.ClearFormatting()
        find.Text = key
        find.Forward = true
        find.Wrap = findStrategy.value()
        return find
    }

    private void setReplacementObject(String replacement, def findObject) {
        def replacementObject = findObject.Replacement
        replacementObject.ClearFormatting()
        replacementObject.Text = replacement
    }

    private void executeReplacement(def findObject, ReplacementStrategy strategy) {
        findObject.Execute(Scriptom.MISSING, Scriptom.MISSING, Scriptom.MISSING,
                Scriptom.MISSING, Scriptom.MISSING, Scriptom.MISSING, Scriptom.MISSING,
                Scriptom.MISSING, Scriptom.MISSING, Scriptom.MISSING, strategy.value())
    }




    // TODO replace names in constants to human-readable, place links to ms docs, opt - brief description






}
