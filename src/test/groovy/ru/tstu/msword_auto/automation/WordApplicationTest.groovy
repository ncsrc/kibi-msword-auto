package ru.tstu.msword_auto.automation

import com.jacob.com.ComFailException
import org.codehaus.groovy.scriptom.ActiveXObject

import static org.junit.Assert.*
import org.junit.Test


class WordApplicationTest {


    @Test
    void whenInitializedThenApplicationUpAndCorrectType() throws Exception {
        def app = WordApplication.getApplication()
        Class appClassActual = app.getClass()
        Class expected = ActiveXObject.class
        assertEquals(appClassActual, expected)
    }


    @Test(expected = ComFailException.class)
    void whenInvokesAppInternalsWhileClosedThenThrowsComException() throws Exception {
        def app = WordApplication.getApplication()
        WordApplication.close()
        app.Selection
    }


}




























