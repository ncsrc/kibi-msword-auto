package ru.tstu.msword_auto.automation.options


enum ReplacementOption {

    // WdReplace
    REPLACE_NONE(0),
    REPLACE_ONE(1),
    REPLACE_ALL(2),


    private int value

    private ReplacementOption(int value) {
        this.value = value
    }


    int value() {
        return this.value
    }

}