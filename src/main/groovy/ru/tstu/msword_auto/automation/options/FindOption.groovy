package ru.tstu.msword_auto.automation.options


enum FindOption {

    // WdFindWrap
    STOP(0), // stops when all occurrences was found
    CONTINUE(1), // continues even when all occurrences was found, may result in endless loop
    ASK(2)


    private int value

    private FindOption(int value) {
        this.value = value
    }

    int value() {
        return this.value
    }

}