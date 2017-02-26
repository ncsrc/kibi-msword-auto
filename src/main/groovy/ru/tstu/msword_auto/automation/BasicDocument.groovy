package ru.tstu.msword_auto.automation

import groovy.transform.PackageScope
import org.codehaus.groovy.scriptom.Scriptom
import ru.tstu.msword_auto.automation.constants.FindStrategy
import ru.tstu.msword_auto.automation.constants.ReplacementStrategy
import ru.tstu.msword_auto.automation.constants.SaveFormat
import ru.tstu.msword_auto.automation.constants.SaveOptions

// todo javadocs
@PackageScope class BasicDocument implements Document {
    // protected access for testing extended class
    final def doc
    final def application


    BasicDocument(String path) {
        this.application = WordApplication.getApplication()
        if(path.startsWith("/")) {
            path = path.substring(1)
        }
        this.doc = application.Documents.Open(path)
    }


    void replace(String key, String replacement) {
        this.replace(key, replacement, ReplacementStrategy.REPLACE_ALL)
    }

    void replace(String key, String replacement, ReplacementStrategy strategy) {
        def find = getFindObject(key, FindStrategy.CONTINUE)
        setReplacementObject(replacement, find)
        executeReplacement(find, strategy)
    }

    boolean find(String key) {
        return this.find(key, FindStrategy.STOP)
    }

    boolean find(String key, FindStrategy strategy) {
        def find = getFindObject(key, strategy)
        return find.Execute()
    }

    void close() {
        this.close(SaveOptions.DO_NOT_SAVE)
    }

    void close(SaveOptions options) {
        doc.close(options.value())
    }


    void save() {
        doc.save() // TODO change save option to format with macro
    }

    void saveAs(String location, String name, SaveFormat format) {
        if(location.startsWith("/")) {
            location = location.substring(1)
        }
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
