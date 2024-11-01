package org.mrshoffen.dto.request;

public enum Order {
    ID_ASC,
    ID_DESC,

    PLAYER_ASC,
    PLAYER_DESC,

    MATCHES_PLAYED_ASC,
    MATCHES_PLAYED_DESC;

    public static Order getOrderFromParameter(String order) {
        return switch (order) {
            case "id_desc" -> ID_DESC;

            case "player" -> PLAYER_ASC;
            case "player_desc" -> PLAYER_DESC;

            case "matches" -> MATCHES_PLAYED_ASC;
            case "matches_desc" -> MATCHES_PLAYED_DESC;


            default -> ID_ASC;

        };
    }


}
