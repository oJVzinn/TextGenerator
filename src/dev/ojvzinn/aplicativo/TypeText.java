package dev.ojvzinn.aplicativo;

public enum TypeText {
    OBFUSCATION,
    FLASHNICKS,
    NICKS,
    BALBNICKS;

    public static TypeText getFromName(String type) {
        switch (type) {
            case "OBFUSCATION": {
                return OBFUSCATION;
            }
            case "FLASHNICKS": {
                return FLASHNICKS;
            }
            case "BALBNICKS": {
                return BALBNICKS;
            }
            case "NICKS": {
                return NICKS;
            }
        }
        return null;
    }

    public static String transformToString(TypeText typeText) {
        switch (typeText) {
            case OBFUSCATION: {
                return "Obfuscation";
            }
            case FLASHNICKS: {
                return "Flashnicks";
            }
            case BALBNICKS: {
                return "Balbnicks";
            }
            case NICKS: {
                return "Nicks";
            }
        }
        return null;
    }
}
