package com.iljasstan.enums;

public enum TariffConditions {
    CONDITION0("Обслуживание счёта"),
    CONDITION1("Переводы юрлица и ИП"),
    CONDITION2("Переводы на счета физлиц"),
    CONDITION3("Переводы со счёта ИП на личный счёт в Альфа-Банке"),
    CONDITION4("Платежи по бумажным поручениям"),
    CONDITION5("Налоговые и бюджетные платежи");

    String desc;

    TariffConditions(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }
}
