package tennis.score.board.model.matchstate.game.regulargame;

public enum Points {
    LOVE(0, "0"),
    FIFTEEN(1, "15"),
    THIRTY(2, "30"),
    FORTY(3, "40"),
    WIN_POINT(4, "");

    private final int rank;
    private final String displayValue;

    Points(int rank, String displayValue) {
        this.rank = rank;
        this.displayValue = displayValue;
    }

    public Points next() {
        return switch (this) {
            case LOVE -> FIFTEEN;
            case FIFTEEN -> THIRTY;
            case THIRTY -> FORTY;
            case FORTY -> WIN_POINT;
            case WIN_POINT -> throw new IllegalStateException("Нельзя вызывать next() для WIN_POINT");
        };
    }

    public int rank() {
        return this.rank;
    }

    public String displayValue() {
        if (this == Points.WIN_POINT) {
            throw new IllegalStateException("Нельзя вызывать displayValue() для WIN_POINT");
        }
        return this.displayValue;
    }

}
